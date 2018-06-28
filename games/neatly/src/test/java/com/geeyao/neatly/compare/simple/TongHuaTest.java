package com.geeyao.neatly.compare.simple;

import com.geeyao.neatly.compare.CardSet;
import com.geeyao.neatly.compare.CompareCtrl;
import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.logic.Card;
import com.geeyao.neatly.util.HelpUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TongHuaTest {

    CompareCtrl compareCtrl = new CompareCtrl();

    public TongHuaTest() {
        compareCtrl.init();
    }

    @Test //同花
    public void test() throws Exception {
        String[] cardArray1 = {"♦2", "♦5", "♦6", "♦9", "♦11"};
        List<Card> cList1 = HelpUtil.converCardArrayToCardList(cardArray1);
        int result1 = compareCtrl.calc(cList1);
        Assert.assertEquals(PokerDefine.TONG_HUA, result1 >> CardSet.PLACE_6);

        String[] cardArray2 = {"♦2", "♦5", "♦6", "♦10", "♦11"};
        List<Card> cList2 = HelpUtil.converCardArrayToCardList(cardArray2);
        int result2 = compareCtrl.calc(cList2);
        Assert.assertEquals(PokerDefine.TONG_HUA, result2 >> CardSet.PLACE_6);

        String[] cardArray3 = {"♦1", "♦5", "♦6", "♦10", "♦11"};
        List<Card> cList3 = HelpUtil.converCardArrayToCardList(cardArray3);
        int result3 = compareCtrl.calc(cList3);
        Assert.assertEquals(PokerDefine.TONG_HUA, result3 >> CardSet.PLACE_6);

        Assert.assertTrue(result1 < result2);
        Assert.assertTrue(result2 < result3);

    }
}
