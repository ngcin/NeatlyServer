package com.geeyao.neatly.compare.simple;

import com.geeyao.neatly.compare.CardSet;
import com.geeyao.neatly.compare.CompareCtrl;
import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.logic.Card;
import com.geeyao.neatly.util.HelpUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SanTiaoTest {

    CompareCtrl compareCtrl = new CompareCtrl();

    public SanTiaoTest() {
        compareCtrl.init();
    }


    @Test//冲三
    public void testA() throws Exception {
        String[] cardArray1 = {"♦2", "♠2", "♣2"};
        List<Card> cList1 = HelpUtil.converCardArrayToCardList(cardArray1);
        int result1 = compareCtrl.calc(cList1);
        Assert.assertEquals(PokerDefine.SAN_TIAO, result1 >> CardSet.PLACE_6);

        String[] cardArray2 = {"♦8", "♠8", "♣8"};
        List<Card> cList2 = HelpUtil.converCardArrayToCardList(cardArray2);
        int result2 = compareCtrl.calc(cList2);
        Assert.assertEquals(PokerDefine.SAN_TIAO, result2 >> CardSet.PLACE_6);

        String[] cardArray3 = {"♦13", "♠13", "♣13"};
        List<Card> cList3 = HelpUtil.converCardArrayToCardList(cardArray3);
        int result3 = compareCtrl.calc(cList3);
        Assert.assertEquals(PokerDefine.SAN_TIAO, result3 >> CardSet.PLACE_6);

        String[] cardArray4 = {"♦1", "♠1", "♣1"};
        List<Card> cList4 = HelpUtil.converCardArrayToCardList(cardArray4);
        int result4 = compareCtrl.calc(cList4);
        Assert.assertEquals(PokerDefine.SAN_TIAO, result4 >> CardSet.PLACE_6);

        Assert.assertTrue(result1 < result2);
        Assert.assertTrue(result2 < result3);
        Assert.assertTrue(result3 < result4);

    }

    @Test //三条
    public void testB() throws Exception {
        String[] cardArray = {"♦13", "♠1", "♣12", "♠12", "♥12"};
        List<Card> cList = HelpUtil.converCardArrayToCardList(cardArray);
        int result = compareCtrl.calc(cList);
        Assert.assertEquals(PokerDefine.SAN_TIAO, result >> CardSet.PLACE_6);

        String[] cardArray1 = {"♦1", "♠12", "♣11", "♠11", "♥11"};
        List<Card> cList1 = HelpUtil.converCardArrayToCardList(cardArray1);
        int result1 = compareCtrl.calc(cList1);
        Assert.assertEquals(PokerDefine.SAN_TIAO, result1 >> CardSet.PLACE_6);

        String[] cardArray2 = {"♦12", "♠13", "♣11", "♠11", "♥11"};
        List<Card> cList2 = HelpUtil.converCardArrayToCardList(cardArray2);
        int result2 = compareCtrl.calc(cList2);
        Assert.assertEquals(PokerDefine.SAN_TIAO, result2 >> CardSet.PLACE_6);

        Assert.assertTrue(result1 > result2);

        String[] cardArray3 = {"♦13", "♠1", "♣11", "♠11", "♥11"};
        List<Card> cList3 = HelpUtil.converCardArrayToCardList(cardArray3);
        int result3 = compareCtrl.calc(cList3);
        Assert.assertEquals(PokerDefine.SAN_TIAO, result3 >> CardSet.PLACE_6);

        Assert.assertTrue(result1 < result3);

        String[] cardArray4 = {"♦13", "♠1", "♣12", "♠12", "♥12"};
        List<Card> cList4 = HelpUtil.converCardArrayToCardList(cardArray4);
        int result4 = compareCtrl.calc(cList4);
        Assert.assertEquals(PokerDefine.SAN_TIAO, result4 >> CardSet.PLACE_6);

        Assert.assertTrue(result3 < result4);

        String[] cardArray5 = {"♦13", "♠12", "♣1", "♠1", "♥1"};
        List<Card> cList5 = HelpUtil.converCardArrayToCardList(cardArray5);
        int result5 = compareCtrl.calc(cList5);
        Assert.assertEquals(PokerDefine.SAN_TIAO, result5 >> CardSet.PLACE_6);

        Assert.assertTrue(result4 < result5);

        String[] cardArray6 = {"♦13", "♠12", "♣2", "♠2", "♥2"};
        List<Card> cList6 = HelpUtil.converCardArrayToCardList(cardArray6);
        int result6 = compareCtrl.calc(cList6);
        Assert.assertEquals(PokerDefine.SAN_TIAO, result6 >> CardSet.PLACE_6);

        Assert.assertTrue(result5 > result6);
    }

}
