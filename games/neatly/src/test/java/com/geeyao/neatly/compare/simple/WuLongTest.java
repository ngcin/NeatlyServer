package com.geeyao.neatly.compare.simple;

import com.geeyao.neatly.compare.CardSet;
import com.geeyao.neatly.compare.CompareCtrl;
import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.logic.Card;
import com.geeyao.neatly.util.HelpUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class WuLongTest {

    CompareCtrl compareCtrl = new CompareCtrl();

    public WuLongTest() {
        compareCtrl.init();
    }

    @Test //乌龙
    public void test() throws Exception {
        String[] cardArray1 = {"♦10", "♠12", "♣13", "♠11", "♥6"};
        List<Card> cList1 = HelpUtil.converCardArrayToCardList(cardArray1);
        int result1 = compareCtrl.calc(cList1);
        Assert.assertEquals(PokerDefine.WU_LONG, result1 >> CardSet.PLACE_6);

        String[] cardArray2 = {"♦1", "♠12", "♣13", "♠11", "♥6"};
        List<Card> cList2 = HelpUtil.converCardArrayToCardList(cardArray2);
        int result2 = compareCtrl.calc(cList2);
        Assert.assertEquals(PokerDefine.WU_LONG, result2 >> CardSet.PLACE_6);

        Assert.assertTrue(result1 < result2);

        String[] cardArray3 = {"♦9", "♠12", "♣13", "♠11", "♥6"};
        List<Card> cList3 = HelpUtil.converCardArrayToCardList(cardArray3);
        int result3 = compareCtrl.calc(cList3);
        Assert.assertEquals(PokerDefine.WU_LONG, result3 >> CardSet.PLACE_6);

        Assert.assertTrue(result1 > result3);

    }

}
