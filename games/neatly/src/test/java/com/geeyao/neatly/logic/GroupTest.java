package com.geeyao.neatly.logic;

import com.geeyao.neatly.compare.CardSet;
import com.geeyao.neatly.compare.CompareCtrl;
import com.geeyao.neatly.util.HelpUtil;
import org.junit.Test;

import java.util.List;

public class GroupTest {

    CompareCtrl compareCtrl = new CompareCtrl();

    public GroupTest() {
        compareCtrl.init();
    }

    @Test //测试牌型推荐
    public void test() throws Exception {
        //String[] cardArray = {"♦1", "♣1", "♠1", "♣2", "♦2", "♦7", "♠7", "♣7", "♠9", "♦9", "♣11", "♠11", "♦11"};
        //String[] cardArray = {"♠1", "♦1", "♦2", "♣2", "♦6", "♥6", "♣6", "♠7", "♠8", "♣9", "♦10", "♦13", "♠13"};
        String[] cardArray = {"♦1", "♥2", "♣2", "♥3", "♣4", "♣6", "♦6", "♣8", "♥8", "♠9", "♠10", "♦11", "♥12"};
        //"♣3", "♣5", "♦5", "♠5", "♥7", "♣7", "♦7", "♠9", "♥10", "♣11", "♥12", "♠12", "♥13"
        List<Card> cardList = HelpUtil.converCardArrayToCardList(cardArray);
        //for (int n = 0; n < 100000; n++) {
        //List<Card> cardList = HelpUtil.getRandomCardList();
//            int w = compareCtrl.calcSpecial(cardList);
//            if (w > 0) {
//                System.out.println("特殊牌型：" + w);
//                continue;
//            }
        CardSet.sortCards(cardList);
        //System.out.println(HelpUtil.printCardList(cardList));
        Long a = System.currentTimeMillis();
        List<CardHand> cardHandList = compareCtrl.getBestCardHand(cardList);
        Long b = System.currentTimeMillis();
        if (cardHandList.size() < 1) {
            System.out.println("ERROR!!!");
        }
        if (b - a > 20) {
            System.out.println("耗时：" + (b - a) + "ms,推荐牌型数目：" + cardHandList.size());
            System.out.println(HelpUtil.printCardList(cardList));
        }
        for (int i = 0; i < cardHandList.size() - 1; i++) {
            for (int j = i + 1; j < cardHandList.size(); j++) {
                CardHand cardPattern1 = cardHandList.get(i);
                CardHand cardPattern2 = cardHandList.get(j);
                if (cardPattern1.getType() < cardPattern2.getType()) {
                    cardHandList.set(j, cardPattern1);
                    cardHandList.set(i, cardPattern2);
                } else if (cardPattern1.getType() == cardPattern2.getType() && cardPattern1.getWeight() < cardPattern2.getWeight()) {
                    cardHandList.set(j, cardPattern1);
                    cardHandList.set(i, cardPattern2);
                }
            }
        }
        for (CardHand c : cardHandList) {
            System.out.println(c.getType() + "->(" + c.getFirstGroup().getType() + "," + c.getMiddleGroup().getType() + ","
                    + c.getEndGroup().getType() + ")->" + c.getWeight() + "->" + HelpUtil.printCardList(c.getCardList()));
        }
        System.out.println();
    }


}
