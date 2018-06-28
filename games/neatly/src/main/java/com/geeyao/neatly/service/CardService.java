package com.geeyao.neatly.service;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.SocketIOClient;
import com.geeyao.common.bean.RoomState;
import com.geeyao.common.log.Log;
import com.geeyao.neatly.bean.MessageBean;
import com.geeyao.neatly.bean.NeatlyConfig;
import com.geeyao.neatly.bean.NeatlyPlayer;
import com.geeyao.neatly.compare.CompareCtrl;
import com.geeyao.neatly.config.NeatlyConstant;
import com.geeyao.neatly.logic.Card;
import com.geeyao.neatly.logic.CardConfig;
import com.geeyao.neatly.logic.CardDealer;
import com.geeyao.common.message.ClientEventName;
import com.geeyao.common.bean.Room;
import com.geeyao.common.service.UserService;
import com.geeyao.neatly.logic.CardHand;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CardService {
    @Log
    private Logger log;
    @Autowired
    private UserService onlineDataStore;
    @Autowired
    private CompareCtrl compareCtrl;

    public void beginGame(Room<NeatlyPlayer> room, SocketIOClient client) {
        NeatlyConfig roomConfig = room.getRoomConfig();
        //所有发牌
        List<NeatlyPlayer> players = room.getPlayers();
        NeatlyConfig config = room.getRoomConfig();
        config.setRoundStartDate(new Date());
        new CardDealer(config.getPeople()).deal(players);
        for (NeatlyPlayer neatlyPlayer : players) {
            List<Card> cardList = neatlyPlayer.getOriginalCards();
            int type = compareCtrl.calcSpecial(cardList);
            List<CardHand> cardHands = compareCtrl.getBestCardHand(cardList);
            neatlyPlayer.setOriginalCards(cardList);
            neatlyPlayer.setSpecial(type);
            neatlyPlayer.setCardHands(cardHands);
            neatlyPlayer.setPlaying(true);
        }
    }

    public void finishGroup(Room<NeatlyPlayer> room, SocketIOClient client, String userMd5Uid, ArrayList<Card> cards, int special) {
        List<NeatlyPlayer> players = room.getPlayers();
        for (NeatlyPlayer neatlyPlayer : players) {
            if(neatlyPlayer.getPlayerMD5Uid().equals(userMd5Uid)){
                neatlyPlayer.setPlayerSpecial(special);
                neatlyPlayer.setSortedCards(cards);
                neatlyPlayer.setFinishGroup(true);
            }
            BroadcastOperations roomOperations = client.getNamespace().getRoomOperations(room.getRoomId());
            SocketIOClient targetClient = findTargetClient(roomOperations, neatlyPlayer);
            if (targetClient != null) {
                targetClient.sendEvent(NeatlyClientEventName.CARD_GROUPED, userMd5Uid);
            }
        }
    }

    public void startCompare(Room<NeatlyPlayer> room, SocketIOClient client) {
        //所有玩家的牌发送给客户端
        List<NeatlyPlayer> players = room.getPlayers();
        List<MessageBean> mbList = new ArrayList<MessageBean>();
        //组装消息格式
        for (int i = 0; i < players.size(); i++) {
            NeatlyPlayer player = players.get(i);
            MessageBean mb = new MessageBean();
            mb.setPlayer(player);
            mb.setCards(new ArrayList<>(player.getSortedCards()));
            mbList.add(mb);
        }
        for (NeatlyPlayer neatlyPlayer : players) {
            BroadcastOperations roomOperations = client.getNamespace().getRoomOperations(room.getRoomId());
            SocketIOClient targetClient = findTargetClient(roomOperations, neatlyPlayer);
            if (targetClient != null) {
                targetClient.sendEvent(NeatlyClientEventName.START_COMPARE, mbList);
            }
        }
    }

    public void roundResult(Room<NeatlyPlayer> room) {
        List<NeatlyPlayer> neatlyPlayingInfoList = room.getPlayers();
//        room.setRoomState(RoomState.Over);
        //算出所有类型
        for (int i = 0; i < neatlyPlayingInfoList.size(); i++) {
            NeatlyPlayer ru = neatlyPlayingInfoList.get(i);
            //特殊牌型跳过
            if (ru.getPlayerSpecial() == 1) continue;
            int topResult = compareCtrl.calc(ru.topCards());
            ru.setTopResult(topResult);

            int centerResult = compareCtrl.calc(ru.centralCards());
            ru.setCenterResult(centerResult);

            int bottomResult = compareCtrl.calc(ru.bottomCards());
            ru.setBottomResult(bottomResult);
        }

        NeatlyConfig roomConfig = room.getRoomConfig();
        if (roomConfig.getBankerMode() != NeatlyConstant.BANKERMODE_NORMAL) {
            NeatlyPlayer ownerNeatlyPlayer = room.getOwner();
            for (int i = 0; i < neatlyPlayingInfoList.size(); i++) {
                NeatlyPlayer player = neatlyPlayingInfoList.get(i);
                if (player == ownerNeatlyPlayer) {
                    continue;
                }
                compare(ownerNeatlyPlayer, player);
            }
        } else {
            //按人头 一个一个比
            for (int i = 0; i < neatlyPlayingInfoList.size() - 1; i++) {
                for (int j = i + 1; j < neatlyPlayingInfoList.size(); j++) {
                    //根据类型比较
                    NeatlyPlayer ru1 = neatlyPlayingInfoList.get(i);
                    NeatlyPlayer ru2 = neatlyPlayingInfoList.get(j);
                    compare(ru1, ru2);
                }
            }
            //    全垒打 x2倍：比牌结束后，如果一个玩家全垒打，算上原来打枪的分数外，赢家还要每家分数乘2倍（房间满4人才会出现）
            if(neatlyPlayingInfoList.size() > 4){
                for (int i = 0; i < neatlyPlayingInfoList.size() - 1; i++) {
                    NeatlyPlayer ru = neatlyPlayingInfoList.get(i);
                    //所有对手都打枪
                    if(ru.getDaQiang().size() == neatlyPlayingInfoList.size() - 1){
                        for (int j = i + 1; j < neatlyPlayingInfoList.size(); j++) {
                            NeatlyPlayer ru2 = neatlyPlayingInfoList.get(j);
                            if(!ru.getPlayerMD5Uid().equals(ru2.getPlayerMD5Uid())){
                                int score = ru.getDaQiang().get(ru2.getPlayerMD5Uid());
                                ru.addLstScore(score);
                                ru2.addLstScore(-score);
                            }
                        }
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < neatlyPlayingInfoList.size(); i++) {
            neatlyPlayingInfoList.get(i).clearing();
        }
        for (NeatlyPlayer ru : neatlyPlayingInfoList) {
            ru.setReady(false);
        }
    }

    public void compare(NeatlyPlayer ru1, NeatlyPlayer ru2) {
        //特殊牌型判断
        if(ru1.getPlayerSpecial() == 1 && ru2.getPlayerSpecial() == 1){
            if (ru1.getSpecial() > ru2.getSpecial()) {
                int score = CardConfig.getSpecialMultiple(ru1.getSpecial());
                ru1.setSpecialScore(score);
                ru1.addLstScore(score);
                ru2.addLstScore(-score);
            } else if (ru1.getSpecial() < ru2.getSpecial()) {
                int score = CardConfig.getSpecialMultiple(ru2.getSpecial());
                ru2.setSpecialScore(score);
                ru2.addLstScore(score);
                ru1.addLstScore(-score);
            }
            return;
        }else if(ru1.getPlayerSpecial() == 1){
            int score = CardConfig.getSpecialMultiple(ru1.getSpecial());
            ru1.setSpecialScore(score);
            ru1.addLstScore(score);
            ru2.addLstScore(-score);
            return;
        }else if(ru2.getPlayerSpecial() == 1){
            int score = CardConfig.getSpecialMultiple(ru2.getSpecial());
            ru2.setSpecialScore(score);
            ru2.addLstScore(score);
            ru1.addLstScore(-score);
            return;
        }

        int topScore;
        int topSpecialScore = 0;
        int centerScore;
        int centerSpecialScore = 0;
        int bottomScore;
        int bottomSpecialScore = 0;
        if (ru1.getTopResult() > ru2.getTopResult()) {
            topScore = CardConfig.getMultiple(ru1.getTopResult(), 1);
            if(topScore > 1){
                topSpecialScore = topScore - 1;
                topScore = 1;
            }
        } else if (ru1.getTopResult() == ru2.getTopResult()) {
            topScore = 0;
        } else {
            topScore = -CardConfig.getMultiple(ru2.getTopResult(), 1);
            if(topScore < -1) {
                topSpecialScore = topScore + 1;
                topScore = -1;
            }
        }
        ru1.addTopScore(topScore);
        ru1.addTopSpecialScore(topSpecialScore);
        ru2.addTopScore(-topScore);
        ru2.addTopSpecialScore(-topSpecialScore);

        if (ru1.getCenterResult() > ru2.getCenterResult()) {
            centerScore = CardConfig.getMultiple(ru1.getCenterResult(), 2);
            if(centerScore > 1){
                centerSpecialScore = centerScore - 1;
                centerScore = 1;
            }
        } else if (ru1.getCenterResult() == ru2.getCenterResult()) {
            centerScore = 0;
        } else {
            centerScore = -CardConfig.getMultiple(ru2.getCenterResult(), 2);
            if(centerScore < -1){
                centerSpecialScore = centerScore + 1;
                centerScore = -1;
            }
        }
        ru1.addCenterScore(centerScore);
        ru1.addCenterSpecialScore(centerSpecialScore);
        ru2.addCenterScore(-centerScore);
        ru2.addCenterSpecialScore(-centerSpecialScore);

        if (ru1.getBottomResult() > ru2.getBottomResult()) {
            bottomScore = CardConfig.getMultiple(ru1.getBottomResult(), 3);
            if(bottomScore > 1){
                bottomSpecialScore = bottomScore - 1;
                bottomScore = 1;
            }
        } else if (ru1.getBottomResult() == ru2.getBottomResult()) {
            bottomScore = 0;
        } else {
            bottomScore = -CardConfig.getMultiple(ru2.getBottomResult(), 3);
            if(bottomScore < -1){
                bottomSpecialScore = bottomScore + 1;
                bottomScore = -1;
            }
        }
        ru1.addBottomScore(bottomScore);
        ru1.addBottomSpecialScore(bottomSpecialScore);
        ru2.addBottomScore(-bottomScore);
        ru2.addBottomSpecialScore(-bottomSpecialScore);

        //    赢一道 +1分：同一道大于其他玩家
        //    输一道 -1分：同一道小于其他玩家
        //    打和  +0分：同一道和其他玩家相同
        //    打枪  x2倍：比牌结束后，若果其中一位玩家三道牌全部胜过另外一位玩家，称为打枪。赢家获得双倍分数，输家则扣相应的分数。
        //    碾过 = 打枪：比牌结束后，如果一位玩家和另外一位玩家相比，1胜2平或2胜1平称谓碾过，视同打枪。
        if (topScore > 0 && centerScore > 0 && bottomScore > 0){
            //左边枪毙右边
            int all = Math.abs(topScore + centerScore + bottomScore);
            all += Math.abs(topSpecialScore + centerSpecialScore + bottomSpecialScore);
            ru1.getDaQiang().put(ru2.getPlayerMD5Uid(), all);
            ru1.addLstScore(all);
            ru2.addLstScore(-all);
        }
        if (topScore < 0 && centerScore < 0 && bottomScore < 0) {
            //右边枪毙左边
            int all = Math.abs(topScore + centerScore + bottomScore);
            all += Math.abs(topSpecialScore + centerSpecialScore + bottomSpecialScore);
            ru2.getDaQiang().put(ru1.getPlayerMD5Uid(), all);
            ru2.addLstScore(all);
            ru1.addLstScore(-all);
        }
    }

    private SocketIOClient findTargetClient(BroadcastOperations operations, NeatlyPlayer neatlyPlayer) {
        Collection<SocketIOClient> clients = operations.getClients();
        for (SocketIOClient client : clients) {
            if (Objects.equals(client.getSessionId().toString(), neatlyPlayer.getClientSessionId())) {
                return client;
            }
        }
        return null;
    }

}
