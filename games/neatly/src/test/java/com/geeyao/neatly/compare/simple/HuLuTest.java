package com.geeyao.neatly.compare.simple;

import com.geeyao.neatly.compare.CardSet;
import com.geeyao.neatly.compare.CompareCtrl;
import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.logic.Card;
import com.geeyao.neatly.util.HelpUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class HuLuTest {

    CompareCtrl compareCtrl = new CompareCtrl();

    public HuLuTest() {
        compareCtrl.init();
    }

    @Test //葫芦
    public void test() throws Exception {
        String[] cardArray1 = {"♦2", "♠2", "♣11", "♠11", "♥11"};
        List<Card> cList1 = HelpUtil.converCardArrayToCardList(cardArray1);
        int result1 = compareCtrl.calc(cList1);
        Assert.assertEquals(PokerDefine.HU_LU, result1 >> CardSet.PLACE_6);

        String[] cardArray2 = {"♦3", "♠3", "♣10", "♠10", "♥10"};
        List<Card> cList2 = HelpUtil.converCardArrayToCardList(cardArray2);
        int result2 = compareCtrl.calc(cList2);
        Assert.assertEquals(PokerDefine.HU_LU, result2 >> CardSet.PLACE_6);

        String[] cardArray3 = {"♦1", "♠1", "♣11", "♠11", "♥11"};
        List<Card> cList3 = HelpUtil.converCardArrayToCardList(cardArray3);
        int result3 = compareCtrl.calc(cList3);
        Assert.assertEquals(PokerDefine.HU_LU, result3 >> CardSet.PLACE_6);

        String[] cardArray4 = {"♦3", "♠3", "♣11", "♠11", "♥11"};
        List<Card> cList4 = HelpUtil.converCardArrayToCardList(cardArray4);
        int result4 = compareCtrl.calc(cList4);
        Assert.assertEquals(PokerDefine.HU_LU, result4 >> CardSet.PLACE_6);

        Assert.assertTrue(result1 > result2);
        Assert.assertTrue(result2 < result3);
        Assert.assertTrue(result1 < result3);
        Assert.assertTrue(result1 < result4);


    }

}
