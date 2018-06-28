package com.geeyao.neatly.compare.simple;

import com.geeyao.neatly.compare.CardSet;
import com.geeyao.neatly.compare.CompareCtrl;
import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.logic.Card;
import com.geeyao.neatly.util.HelpUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TongHuaShunTest {

    CompareCtrl compareCtrl = new CompareCtrl();

    public TongHuaShunTest() {
        compareCtrl.init();
    }

    @Test //同花顺
    public void test() throws Exception {
        String[] cardArray1 = {"♦1", "♦2", "♦3", "♦4", "♦5"};
        List<Card> cList1 = HelpUtil.converCardArrayToCardList(cardArray1);
        int result1 = compareCtrl.calc(cList1);
        Assert.assertEquals(PokerDefine.TONG_HUA_SHUN, result1 >> CardSet.PLACE_6);

        String[] cardArray2 = {"♦9", "♦10", "♦11", "♦12", "♦13"};
        List<Card> cList2 = HelpUtil.converCardArrayToCardList(cardArray2);
        int result2 = compareCtrl.calc(cList2);
        Assert.assertEquals(PokerDefine.TONG_HUA_SHUN, result2 >> CardSet.PLACE_6);

        Assert.assertTrue(result1 > result2);

        String[] cardArray3 = {"♦10", "♦11", "♦12", "♦13", "♦1"};
        List<Card> cList3 = HelpUtil.converCardArrayToCardList(cardArray3);
        int result3 = compareCtrl.calc(cList3);
        Assert.assertEquals(PokerDefine.TONG_HUA_SHUN, result3 >> CardSet.PLACE_6);

        Assert.assertTrue(result1 < result3);
    }

    @Test //同花顺 比大小
    public void testB() throws Exception {

    }

}
