package com.geeyao.neatly.compare.simple;

import com.geeyao.neatly.compare.CardSet;
import com.geeyao.neatly.compare.CompareCtrl;
import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.logic.Card;
import com.geeyao.neatly.util.HelpUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class WuTongTest {

    CompareCtrl compareCtrl = new CompareCtrl();

    public WuTongTest() {
        compareCtrl.init();
    }

    @Test //五同
    public void testA() throws Exception {
        String[] cardArray = {"♦1", "♦1", "♣1", "♠1", "♥1"};
        List<Card> cList = HelpUtil.converCardArrayToCardList(cardArray);
        int result = compareCtrl.calc(cList);
        Assert.assertEquals(PokerDefine.WU_TONG, result >> CardSet.PLACE_6);

        String[] cardArray1 = {"♦2", "♦2", "♣2", "♠2", "♥2"};
        List<Card> cList1 = HelpUtil.converCardArrayToCardList(cardArray1);
        int result1 = compareCtrl.calc(cList1);
        Assert.assertEquals(PokerDefine.WU_TONG, result1 >> CardSet.PLACE_6);

        Assert.assertTrue(result > result1);

    }

}
