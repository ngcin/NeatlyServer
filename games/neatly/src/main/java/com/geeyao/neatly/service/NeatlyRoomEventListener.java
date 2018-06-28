package com.geeyao.neatly.service;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.geeyao.common.annotation.Retry;
import com.geeyao.common.bean.*;
import com.geeyao.common.log.Log;
import com.geeyao.common.message.Constant;
import com.geeyao.common.message.ServerEventName;
import com.geeyao.common.service.*;
import com.geeyao.neatly.bean.*;
import com.geeyao.neatly.compare.CompareCtrl;
import com.geeyao.neatly.logic.Card;
import com.geeyao.neatly.logic.CardHand;
import com.geeyao.neatly.util.DateUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Component
public class NeatlyRoomEventListener {
    @Log
    private Logger log;
    @Autowired
    private CompareCtrl compareCtrl;
    @Autowired
    private CardService cardService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private UserRoomService userRoomService;
    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private ReplayService replayService;

    //server–>namespace–room
    @OnEvent(value = NeatlyServerEventName.CREATE_NEATLY_ROOM)
    @Retry(times = 10, on = org.springframework.dao.OptimisticLockingFailureException.class)
    public void createRoom(SocketIOClient client, AckRequest request, NeatlyConfig neatlyConfig) {
        User user = userService.getUser(client);
        Room<RoomPlayer> newRoom = new Room<>(neatlyConfig, new NeatlyPlayer(user));
        newRoom.setRoomNum(sequenceService.getNextSequence(Room.class.getSimpleName(), 300000));
        roomService.saveRoom(newRoom);
        String roomId = newRoom.getRoomId();
        user.setCurrentRoomId(roomId);
        userService.saveUser(user);
        //保存用户房间信息
        UserRoom ur = new UserRoom();
        ur.setUserId(user.getUnionId());
        ur.setRoomId(roomId);
        userRoomService.save(ur);
        client.joinRoom(roomId);
        request.sendAckData(newRoom);
        log.info("[房间管理器] 创建房间成功 id:{}", roomId);
    }

    @OnEvent(value = NeatlyServerEventName.JOIN_NEATLY_ROOM)
    @Retry(times = 10, on = org.springframework.dao.OptimisticLockingFailureException.class)
    public void joinRoom(SocketIOClient client, AckRequest request, String roomNum) {
        User user = userService.getUser(client);
        Room<NeatlyPlayer> room = roomService.findByRoomNum(Long.parseLong(roomNum));
        if(room == null){
            request.sendAckData("房间号不存在");
            return;
        }
        if(room.getRoomState().equals(RoomState.Over)){
            request.sendAckData("房间已解散。");
        }

        log.info("还没加入,现在人数" + room.getPlayerCount());
        String playerMD5Uid = DigestUtils.md5DigestAsHex(user.getUnionId().getBytes());
        NeatlyPlayer player = room.getPlayer(playerMD5Uid);
        if (player != null) {//玩家本来就在房间里
            user.setCurrentRoomId(room.getRoomId());
            userService.saveUser(user);
            roomService.saveRoom(room);//房间信息返回去并且释放锁
            request.sendAckData(room);//加把房间信息发给用户
        } else {
            NeatlyConfig config = room.getRoomConfig();
            if(room.getPlayerCount() < config.getPeople()){
                NeatlyPlayer roomPlayer = roomService.getRoomPlayer(client);
                if (roomPlayer == null) {
                    roomPlayer = new NeatlyPlayer(user);
                }
                user.setCurrentRoomId(room.getRoomId());
                userService.saveUser(user);

                //保存用户房间信息
                UserRoom ur = new UserRoom();
                ur.setUserId(user.getUnionId());
                ur.setRoomId(room.getRoomId());
                userRoomService.save(ur);
                //分配座位
                room.addPlayer(roomPlayer);
                roomService.saveRoom(room);
                log.info("加人了，现在人数" + room.getPlayerCount());
                client.joinRoom(room.getRoomId());
                request.sendAckData(room);
                sendRoomEvent(NeatlyClientEventName.PLAYER_JOIN, room, client, client, roomPlayer);
            }else{
                request.sendAckData("房间人员已满");//false代表不能加入
            }
        }
    }

    @OnEvent(value = NeatlyServerEventName.NEATLY_GET_HALL_USER_INFO)
    public void getHallUserInfo(SocketIOClient client, AckRequest request) {
        User user = userService.getUser(client);
        if(user != null){
            request.sendAckData(user, DigestUtils.md5DigestAsHex(user.getUnionId().getBytes()));
        }
    }

    @OnEvent(value = NeatlyServerEventName.NEATLY_GET_PLAYER_ROOM_INFO)
    public void getCurrentPlayer(SocketIOClient client, AckRequest request) {
        User user = userService.getUser(client);
        if(user != null && StringUtils.hasText(user.getCurrentRoomId())){
            String userMD5Uid = DigestUtils.md5DigestAsHex(user.getUnionId().getBytes());
            Room<NeatlyPlayer> room = roomService.getRoom(user.getCurrentRoomId());
            if(room != null){
                //把房间信息发送给用户
                AckDataBean acb = new AckDataBean();
                acb.setRoom(room);
                acb.setUserMD5Uid(userMD5Uid);
                if(room.getRoomState() == RoomState.Ready){//准备阶段

                }else if(room.getRoomState() == RoomState.InGame){//组牌阶段
                    List<MessageBean> mbList = new ArrayList<MessageBean>();
                    List<NeatlyPlayer> players = room.getPlayers();
                    for (NeatlyPlayer ru : players){
                        MessageBean mb = new MessageBean();
                        mb.setPlayer(ru);
                        //如果自己已参加这局
                        if(ru.getPlayerMD5Uid().equals(userMD5Uid)){
                            //玩家已经组好牌
                            if(ru.isFinishGroup()){
                                mb.setCards(new ArrayList<>(ru.getSortedCards()));
                            }else{
                                mb.setCards(new ArrayList<>(ru.getOriginalCards()));
                                mb.setCardHands(ru.getCardHands());
                                //设置剩余倒计时时间
                                NeatlyConfig config = room.getRoomConfig();
                                int passTime = (int)DateUtils.timeDiff(config.getRoundStartDate(), new Date());
                                int timeleft = config.getRoundTime() - passTime;
                                mb.setCountDown((config.getRoundTime()>timeleft)?timeleft:config.getRoundTime());
                            }
                        }
                        mbList.add(mb);
                    }
                    acb.setMbList(mbList);
                }else if(room.getRoomState() == RoomState.Compareing){//比牌阶段
                    List<MessageBean> mbList = new ArrayList<MessageBean>();
                    for (NeatlyPlayer ru : room.getPlayers()){
                        MessageBean mb = new MessageBean();
                        mb.setPlayer(ru);
                        mb.setCards(new ArrayList<>(ru.getSortedCards()));
                        mbList.add(mb);
                    }
                    acb.setMbList(mbList);
                }
                request.sendAckData(acb);
            }
        }
    }

    @OnEvent(value = ServerEventName.REMOVE_PLAYER)
    @Retry(times = 10, on = org.springframework.dao.OptimisticLockingFailureException.class)
    public void removePlayer(SocketIOClient client, AckRequest request, MessageBean bean) {
        Room<RoomPlayer> room = roomService.getRoom(bean.getRoomId());
        RoomPlayer player = room.getPlayer(bean.getPlayerMD5Uid());
        if(!player.is(room.getOwner().getPlayerMD5Uid())){
            room.getPlayers().remove(player);
            roomService.saveRoom(room);

            List<User> userList = userService.getByCurrentRoomId(player.getRoomId());
            for (int i = 0; i < userList.size(); i++) {
                User user = userList.get(i);
                String userMD5Code = DigestUtils.md5DigestAsHex(user.getUnionId().getBytes());
                if(userMD5Code.equals(bean.getPlayerMD5Uid())){
                    user.setCurrentRoomId(null);
                    userService.saveUser(user);
                    UserRoom ur = new UserRoom();
                    ur.setUserId(user.getUnionId());
                    ur.setRoomId(room.getRoomId());
                    userRoomService.delete(ur);
                    break;
                }
            }
            sendRoomEvent(NeatlyClientEventName.PLAYER_REMOVED, room, client, client, bean.getPlayerMD5Uid());
        }
    }

    @OnEvent(value = NeatlyServerEventName.NEATLY_PLAYER_STATE_CHANGE)
    @Retry(times = 10, on = org.springframework.dao.OptimisticLockingFailureException.class)
    public void playerReady(SocketIOClient client, AckRequest request, MessageBean bean) {
        Room<NeatlyPlayer> room = roomService.getRoom(bean.getRoomId());
        if(bean.getPlayerState().equals(Constant.PLAYER_STATE_READY)){
            prepare(room, bean.getPlayerMD5Uid());
        }
        roomService.saveRoom(room);
        //所有玩家都准备
        if(isAllPlayersReady(room)){
            startGame(client, request, bean);
        }else{
            int count = 0;
            for (NeatlyPlayer player : room.getPlayers()) {
                if(player.isReady()){
                    count++;
                }
            }
            MessageBean mb = new MessageBean();
            mb.setPlayerMD5Uid(bean.getPlayerMD5Uid());
            mb.setStartCount(count);
            sendRoomEvent(NeatlyClientEventName.PLAYER_READY, room, client, mb);
        }
    }

    @OnEvent(value = NeatlyServerEventName.NEATLY_START_GAME)
    @Retry(times = 10, on = org.springframework.dao.OptimisticLockingFailureException.class)
    public void startGame(SocketIOClient client, AckRequest request, MessageBean bean) {
        Room<NeatlyPlayer> room = roomService.getRoom(bean.getRoomId());
        if(room.getRoomState() != RoomState.InGame){
            if(room.getPlayers().size() < 2){
                request.sendAckData("请等待玩家加入游戏");
                return;
            }
            setGameBeginState(room);
            cardService.beginGame(room, client);
            roomService.saveRoom(room);
            //给每个玩家发自己的牌
            for (NeatlyPlayer neatlyPlayer : room.getPlayers()) {
                BroadcastOperations roomOperations = client.getNamespace().getRoomOperations(room.getRoomId());
                SocketIOClient targetClient = findTargetClient(roomOperations, neatlyPlayer);
                if (targetClient != null) {
                    MessageBean mb = new MessageBean();
                    List<Card> cl = neatlyPlayer.getOriginalCards();
                    mb.setCards(new ArrayList<Card>(cl));
                    mb.setPlayer(neatlyPlayer);
                    mb.setCardHands(neatlyPlayer.getCardHands());
                    NeatlyConfig config = room.getRoomConfig();
                    mb.setCountDown(config.getRoundTime());
                    targetClient.sendEvent(NeatlyClientEventName.CARD_ASSIGNED, mb);
                }
            }
        }
    }

    @OnEvent(value = NeatlyServerEventName.NEATLY_CHECK_CARD_VALUE)
    public void checkCardValue(SocketIOClient client, AckRequest request, MessageBean bean){
        StringBuffer sb = new StringBuffer();
        boolean check = checkCardValue(bean.getCards(), sb);
        request.sendAckData(check, sb.toString());
    }

    @OnEvent(value = NeatlyServerEventName.NEATLY_PLAYER_FINISH_GROUP)
    @Retry(times = 10, on = org.springframework.dao.OptimisticLockingFailureException.class)
    public void playerFinishGroup(SocketIOClient client, AckRequest request, MessageBean bean) {
        StringBuffer sb = new StringBuffer();
        boolean check = checkCardValue(bean.getCards(), sb);
        //特殊牌型不检查
        if(bean.getSpecial() > 0){
            check = true;
        }
        if(check){
            Room<NeatlyPlayer> room = roomService.getRoom(bean.getRoomId());
            cardService.finishGroup(room, client, bean.getPlayerMD5Uid(), bean.getCards(), bean.getSpecial());

            //判断是否所有玩家完成组牌,返回true则开始比牌
            if(isAllPlayersFinishGroup(room)){
                room.setRoomState(RoomState.Compareing);
                //后台计算得分结果
                cardService.roundResult(room);
                roomService.saveRoom(room);
                cardService.startCompare(room, client);
            }else{
                roomService.saveRoom(room);
            }
        }else{
            request.sendAckData(sb.toString());
        }
    }
    /*
        自动完成超时组牌
     */
    @OnEvent(value = NeatlyServerEventName.NEATLY_ALL_PLAYER_FINISH_GROUP)
    @Retry(times = 10, on = org.springframework.dao.OptimisticLockingFailureException.class)
    public void allPlayerFinishGroup(SocketIOClient client, AckRequest request, MessageBean bean) {
        Room<NeatlyPlayer> room = roomService.getRoom(bean.getRoomId());
        //不是在比牌阶段，则设置比牌规则
        if(room.getRoomState() != RoomState.Compareing){
            for (NeatlyPlayer neatlyPlayer : room.getPlayers()) {
                //某个玩家组牌倒计时完成，如果房间其他离线玩家没有完成组牌，自动完成组牌。
                if(!neatlyPlayer.isFinishGroup() && neatlyPlayer.isPlaying() && !neatlyPlayer.isOnline()){
                    if(neatlyPlayer.getCardHands().size() > 0){
                        CardHand ch = neatlyPlayer.getCardHands().get(0);
                        //是否有特殊牌型
                        if(neatlyPlayer.getSpecial() > 0){
                            bean.setSpecial(1);
                        }
                        cardService.finishGroup(room, client, neatlyPlayer.getPlayerMD5Uid(), new ArrayList<>(ch.getCardList()), bean.getSpecial());
                    }
                }
            }
            //判断是否所有玩家完成组牌,返回true则开始比牌
            if(isAllPlayersFinishGroup(room)){
                room.setRoomState(RoomState.Compareing);
                //后台计算得分结果
                cardService.roundResult(room);
                roomService.saveRoom(room);
                cardService.startCompare(room, client);
            }else{
                roomService.saveRoom(room);
            }
        }
    }

    @OnEvent(value = NeatlyServerEventName.NEATLY_PLAYER_FINISH_COMPARE)
    @Retry(times = 10, on = org.springframework.dao.OptimisticLockingFailureException.class)
    public void playerFinishCompare(SocketIOClient client, AckRequest request, MessageBean bean) {
        User user = userService.getUser(client);
        Room<NeatlyPlayer> room = roomService.getRoom(user.getCurrentRoomId());
        String playerMD5Uid = DigestUtils.md5DigestAsHex(user.getUnionId().getBytes());
        NeatlyPlayer player = room.getPlayer(playerMD5Uid);
        //玩家比牌结束标识
        player.setFinishCompare(true);
        //如果所有玩家完成比牌
        if(isAllPlayerCompared(room)){
            NeatlyConfig config = room.getRoomConfig();
            if(config.getCurrentRound() == config.getTotalRound()){
                // 保存回放记录
                saveReplayRecord(room);
                log.info("所有牌局完成，结束游戏。");
                room.over();
                roomService.saveRoom(room);
                sendRoomEvent(NeatlyClientEventName.OVER_GAME, room, client, room);
            }else{
                // 保存回放记录
                saveReplayRecord(room);
                log.info("牌局完成，重置游戏。");
                room.reset();
                config.setCurrentRound(config.getCurrentRound()+1);
                roomService.saveRoom(room);
                sendRoomEvent(NeatlyClientEventName.RESET_GAME, room, client, config.getCurrentRound());
            }
        }else{
            roomService.saveRoom(room);
        }
    }

    public void saveReplayRecord(Room<NeatlyPlayer> room) {
        Replay replay = new Replay();
        replay.setRoomId(room.getRoomId());
        NeatlyConfig config = room.getRoomConfig();
        replay.setRound(config.getCurrentRound());
        List<ReplayItem> itemList = new ArrayList<ReplayItem>();
        for (NeatlyPlayer player : room.getPlayers()){
            ReplayItem item = new ReplayItem(player);
            itemList.add(item);
        }
        replay.setItems(itemList);
        replayService.saveReplay(replay);
    }

    public boolean isAllPlayerCompared(Room<NeatlyPlayer> room) {
        List<NeatlyPlayer> neatlyPlayingInfoList = room.getPlayers();
        for (NeatlyPlayer ru : neatlyPlayingInfoList) {
            if (!ru.isFinishCompare() && ru.isOnline() && ru.isPlaying()) {
                return false;
            }
        }
        return true;
    }

    public void prepare(Room<NeatlyPlayer> room, String playerMD5Uid) {
        NeatlyPlayer user = room.getPlayer(playerMD5Uid);
        user.setReady(true);
    }

    public boolean isAllPlayersFinishGroup(Room<NeatlyPlayer> room){
        List<NeatlyPlayer> neatlyPlayingInfoList = room.getPlayers();
        for (NeatlyPlayer ru : neatlyPlayingInfoList) {
            if (!ru.isFinishGroup()) {
                return false;
            }
        }
        return true;
    }

    public boolean isAllPlayersReady(Room<NeatlyPlayer> room) {
        List<NeatlyPlayer> neatlyPlayingInfoList = room.getPlayers();
        NeatlyPlayer owner = room.getOwner();
        for (NeatlyPlayer ru : neatlyPlayingInfoList) {
            if (!ru.isReady()) {
                return false;
            }
        }
        return true;
    }

    public void setGameBeginState(Room<NeatlyPlayer> room) {
        room.setRoomState(RoomState.InGame);
        for (NeatlyPlayer ru : room.getPlayers()) {
            ru.clear();
        }
    }

    public boolean checkCardValue(List<Card> cards, StringBuffer sb){
        if(cards.size() != 13){
            sb.append("完成牌必须是十三张");
            return false;
        }
        int topScore = compareCtrl.calc(cards.subList(0, 3));
        int centerScore = compareCtrl.calc(cards.subList(3, 8));
        int bottomScore = compareCtrl.calc(cards.subList(8, 13));
        if(topScore > centerScore || centerScore > bottomScore){
            sb.append("组牌不符合十三水规则");
            return false;
        }
        return true;
    }

    public void sendRoomEvent(String eventName, Room room, SocketIOClient client, Object... data){
        List<NeatlyPlayer> players = room.getPlayers();
        BroadcastOperations roomOperations = client.getNamespace().getRoomOperations(room.getRoomId());
        for (NeatlyPlayer neatlyPlayer : players) {
            SocketIOClient targetClient = findTargetClient(roomOperations, neatlyPlayer);
            if (targetClient != null) {
                targetClient.sendEvent(eventName, data);
            }
        }
    }

    public void sendRoomEvent(String eventName, Room room, SocketIOClient client, SocketIOClient excludedClient, Object... data){
        List<NeatlyPlayer> players = room.getPlayers();
        BroadcastOperations roomOperations = client.getNamespace().getRoomOperations(room.getRoomId());
        for (NeatlyPlayer neatlyPlayer : players) {
            SocketIOClient targetClient = findTargetClient(roomOperations, neatlyPlayer);
            if (targetClient != null && (excludedClient == null || !targetClient.getSessionId().equals(excludedClient.getSessionId()))) {
                targetClient.sendEvent(eventName, data);
            }
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
