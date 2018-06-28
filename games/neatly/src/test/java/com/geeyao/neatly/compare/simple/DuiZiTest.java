package com.geeyao.neatly.compare.simple;

import com.geeyao.neatly.compare.CardSet;
import com.geeyao.neatly.compare.CompareCtrl;
import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.logic.Card;
import com.geeyao.neatly.util.HelpUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DuiZiTest {

    CompareCtrl compareCtrl = new CompareCtrl();

    public DuiZiTest() {
        compareCtrl.init();
    }

    @Test
    public void test() throws Exception {
        String[] cardArray1 = {"♦10", "♠12", "♣13", "♠11", "♥11"};
        List<Card> cList1 = HelpUtil.converCardArrayToCardList(cardArray1);
        int result1 = compareCtrl.calc(cList1);
        Assert.assertEquals(PokerDefine.DUI_ZI, result1 >> CardSet.PLACE_6);

        String[] cardArray2 = {"♦1", "♠12", "♣13", "♠11", "♥11"};
        List<Card> cList12 = HelpUtil.converCardArrayToCardList(cardArray2);
        int result2 = compareCtrl.calc(cList12);
        Assert.assertEquals(PokerDefine.DUI_ZI, result2 >> CardSet.PLACE_6);

        String[] cardArray3 = {"♦1", "♠1", "♣3", "♠12", "♥13"};
        List<Card> cList3 = HelpUtil.converCardArrayToCardList(cardArray3);
        int result3 = compareCtrl.calc(cList3);
        Assert.assertEquals(PokerDefine.DUI_ZI, result3 >> CardSet.PLACE_6);

        String[] cardArray4 = {"♦1", "♠1", "♣4", "♠12", "♥13"};
        List<Card> cList4 = HelpUtil.converCardArrayToCardList(cardArray4);
        int result4 = compareCtrl.calc(cList4);
        Assert.assertEquals(PokerDefine.DUI_ZI, result4 >> CardSet.PLACE_6);

        Assert.assertTrue(result1 < result2);
        Assert.assertTrue(result2 < result3);
        Assert.assertTrue(result3 < result4);
    }
}
