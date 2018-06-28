package com.geeyao.neatly.compare.simple;

import com.geeyao.neatly.compare.CardSet;
import com.geeyao.neatly.compare.CompareCtrl;
import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.logic.Card;
import com.geeyao.neatly.util.HelpUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TieZhiTest {

    CompareCtrl compareCtrl = new CompareCtrl();

    public TieZhiTest() {
        compareCtrl.init();
    }

    @Test //铁支
    public void test() throws Exception {
        String[] cardArray1 = {"♦2", "♦1", "♣1", "♠1", "♥1"};
        List<Card> cList1 = HelpUtil.converCardArrayToCardList(cardArray1);
        int result1 = compareCtrl.calc(cList1);
        Assert.assertEquals(PokerDefine.TIE_ZHI, result1 >> CardSet.PLACE_6);

        String[] cardArray2 = {"♦2", "♦13", "♣13", "♠13", "♥13"};
        List<Card> cList2 = HelpUtil.converCardArrayToCardList(cardArray2);
        int result2 = compareCtrl.calc(cList2);
        Assert.assertEquals(PokerDefine.TIE_ZHI, result2 >> CardSet.PLACE_6);

        Assert.assertTrue(result1 > result2);

        String[] cardArray3 = {"♦6", "♦10", "♣10", "♠10", "♥10"};
        List<Card> cList3 = HelpUtil.converCardArrayToCardList(cardArray3);
        int result3 = compareCtrl.calc(cList3);
        Assert.assertEquals(PokerDefine.TIE_ZHI, result3 >> CardSet.PLACE_6);

        Assert.assertTrue(result3 < result2);
        Assert.assertTrue(result3 < result1);
    }

}
