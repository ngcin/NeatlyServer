package com.geeyao.neatly.compare.simple;

import com.geeyao.neatly.compare.CardSet;
import com.geeyao.neatly.compare.CompareCtrl;
import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.logic.Card;
import com.geeyao.neatly.util.HelpUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class LiangDuiTest {

    CompareCtrl compareCtrl = new CompareCtrl();

    public LiangDuiTest() {
        compareCtrl.init();
    }

    @Test //两对
    public void test() throws Exception {
        String[] cardArray1 = {"♦1", "♠12", "♣12", "♠11", "♥11"};
        List<Card> cList1 = HelpUtil.converCardArrayToCardList(cardArray1);
        int result1 = compareCtrl.calc(cList1);
        Assert.assertEquals(PokerDefine.LIANG_DUI, result1 >> CardSet.PLACE_6);

        String[] cardArray2 = {"♦12", "♠13", "♣13", "♠11", "♥11"};
        List<Card> cList2 = HelpUtil.converCardArrayToCardList(cardArray2);
        int result2 = compareCtrl.calc(cList2);
        Assert.assertEquals(PokerDefine.LIANG_DUI, result2 >> CardSet.PLACE_6);

        Assert.assertTrue(result1 < result2);

        String[] cardArray3 = {"♦1", "♠1", "♣13", "♠11", "♥11"};
        List<Card> cList3 = HelpUtil.converCardArrayToCardList(cardArray3);
        int result3 = compareCtrl.calc(cList3);
        Assert.assertEquals(PokerDefine.LIANG_DUI, result3 >> CardSet.PLACE_6);

        Assert.assertTrue(result2 < result3);

        String[] cardArray4 = {"♦1", "♠13", "♣13", "♠11", "♥11"};
        List<Card> cList4 = HelpUtil.converCardArrayToCardList(cardArray4);
        int result4 = compareCtrl.calc(cList4);
        Assert.assertEquals(PokerDefine.LIANG_DUI, result4 >> CardSet.PLACE_6);

        Assert.assertTrue(result2 < result4);

        String[] cardArray5 = {"♦10", "♠13", "♣13", "♠11", "♥11"};
        List<Card> cList5 = HelpUtil.converCardArrayToCardList(cardArray5);
        int result5 = compareCtrl.calc(cList5);
        Assert.assertEquals(PokerDefine.LIANG_DUI, result5 >> CardSet.PLACE_6);

        Assert.assertTrue(result2 > result5);
    }

}
