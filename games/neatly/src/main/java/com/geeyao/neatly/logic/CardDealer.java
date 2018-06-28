package com.geeyao.neatly.logic;

import com.geeyao.neatly.bean.NeatlyPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class CardDealer {
    ArrayList<Card> allCards = new ArrayList<>();

    private int[] addColorArr = {PokerSuitEnum.SPADE.getValue(), PokerSuitEnum.HEART.getValue(), PokerSuitEnum.CLUB.getValue()};

    public CardDealer(int people) {
        for (PokerSuitEnum pokerSuit : PokerSuitEnum.values()) {
            for (int i = 1; i < 14; i++) {
                allCards.add(new Card(i, pokerSuit.getValue()));
            }
        }
        if(people > 4){
            for (int i = 0; i < people - 4; i++) {
                for (int j = 1; j < 14; j++) {
                    allCards.add(new Card(j, addColorArr[i]));
                }
            }
        }
    }

    public void deal(List<NeatlyPlayer> players) {
//        特殊牌型
        String[] cardArray1 = {"♦3", "♠3", "♣4", "♦4", "♠5", "♥5", "♠6", "♣6", "♦7", "♠7", "♠8", "♠8", "♦12"};
        List<Card> cardList = HelpUtil.converCardArrayToCardList(cardArray1);
        players.get(0).setOriginalCards(cardList);
        String[] cardArray2 = {"♦3", "♠2", "♣4", "♦1", "♠5", "♥5", "♠6", "♣6", "♦7", "♠7", "♠8", "♠9", "♦12"};
        cardList = HelpUtil.converCardArrayToCardList(cardArray2);
        players.get(1).setOriginalCards(cardList);
//        打枪
//        String[] cardArray1 = {"♦3", "♠3", "♣3", "♦5", "♠5", "♦5", "♠6", "♣6", "♦7", "♠7", "♠7", "♠12", "♦12"};
//        List<Card> cardList = HelpUtil.converCardArrayToCardList(cardArray1);
//        players.get(0).setOriginalCards(cardList);
//        String[] cardArray2 = {"♦4", "♠4", "♣4", "♦6", "♠6", "♦6", "♠9", "♣9", "♦11", "♠11", "♠11", "♠11", "♦12"};
//        cardList = HelpUtil.converCardArrayToCardList(cardArray2);
//        players.get(1).setOriginalCards(cardList);
//        正常抽牌
//        if (players.size() > 4) {
//            throw new IllegalArgumentException("玩家数不能超过4个");
//        }
//        ArrayList<Card> availableCards = new ArrayList(allCards);
//        Random random = new Random();
//        for (NeatlyPlayer player : players) {
//            for (int i = 1; i < 14; i++) {
//                int index = random.nextInt(availableCards.size());
//                Card card = availableCards.get(index);
//                availableCards.remove(index);
//                player.getOriginalCards().add(card);
//            }
//        }
    }
}
