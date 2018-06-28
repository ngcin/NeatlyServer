package com.geeyao.neatly.logic;

import com.geeyao.neatly.compare.CardSet;
import com.geeyao.neatly.compare.CompareCtrl;
import com.geeyao.neatly.util.HelpUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompareTest {

    CompareCtrl compareCtrl = new CompareCtrl();

    public CompareTest() {
        compareCtrl.init();
    }

    @Test //测试特殊牌型随机出现频率
    public void testSpecial() throws Exception {
        Map<String, Integer> m = new HashMap();
        for (int k = 0; k < 100; k++) {
            List<Card> cardList = HelpUtil.getRandomCardList();
            int result = compareCtrl.calcSpecial(cardList);
            if (result > 0) {
                int resultCount = 0;
                if (m.get("" + result) != null) {
                    resultCount = m.get("" + result);
                }
                resultCount++;
                m.put("" + result, resultCount);
                String cardString = "";
                for (Card c : cardList) {
                    cardString += " " + HelpUtil.getSuitStr(c.getSuit()) + c.getNumber() + " ";
                }
                //System.out.println(cardString);
            }
        }
        for (String r : m.keySet()) {
            System.out.println(r + "->" + m.get(r));
        }
    }

    @Test
    public void testCardHand(){
//        "♦" "♠" "♣" "♥"
//        String[] cardArray1 = {"♦1", "♦2", "♦3", "♦4", "♦5", "♦6", "♦7", "♦8", "♦9", "♦10", "♦11", "♦12", "♦13"};
//        String[] cardArray1 = {"♦1", "♠1", "♣1", "♦4", "♠4", "♦6", "♠6", "♣6", "♦9", "♠9", "♠11", "♠12", "♦13"};
//        List<Card> cardList = HelpUtil.converCardArrayToCardList(cardArray1);
//        int weight = compareCtrl.calcSpecial(cardList);
//        System.out.println(weight);
//        compareCtrl.calcSpecial(cardList);
//        for (Card card: cardList) {
//
//        }
//        List<CardHand> cardHand = compareCtrl.getBestCardHand(cardList);
//        System.out.println(cardHand.size());
//        for (CardHand ch : cardHand ) {
//            System.out.println(ch.getFirstGroup());
//            System.out.println(ch.getMiddleGroup());
//            System.out.println(ch.getEndGroup());
//        }

        //单道比较
        String[] cardArray = {"♦10", "♠11", "♣12"};
        List<Card> cardList = HelpUtil.converCardArrayToCardList(cardArray);
        int weight = CardSet.getCardListWeight(cardList);
        int cal = compareCtrl.calc(cardList);
        String[] cardArray2 = {"♦6", "♠12", "♣1"};
        List<Card> cardList2 = HelpUtil.converCardArrayToCardList(cardArray2);
        int weight2 = CardSet.getCardListWeight(cardList2);
        int cal2 = compareCtrl.calc(cardList2);
        System.out.println(weight + "," + weight2);
        System.out.println(cal + "," + cal2);
    }

}
