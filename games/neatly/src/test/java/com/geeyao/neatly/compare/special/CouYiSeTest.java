package com.geeyao.neatly.compare.special;

import com.geeyao.neatly.compare.CompareCtrl;
import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.logic.Card;
import com.geeyao.neatly.util.HelpUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class CouYiSeTest {

    CompareCtrl compareCtrl = new CompareCtrl();

    public CouYiSeTest() {
        compareCtrl.init();
    }

    @Test //凑一色
    public void testA() throws Exception {
        String[] cardArray = {"♦1", "♦3", "♦9", "♦11", "♦13", "♥4", "♥5", "♥7", "♥8", "♥10", "♥11", "♥12", "♥13"};
        List<Card> cList = HelpUtil.converCardArrayToCardList(cardArray);
        int result = compareCtrl.calcSpecial(cList);
        Assert.assertEquals(PokerDefine.COU_YI_SE, result);
    }

    @Test //凑一色
    public void testB() throws Exception {
        String[] cardArray = {"♣1", "♣3", "♠9", "♠11", "♠13", "♠4", "♠5", "♠7", "♠8", "♠10", "♠11", "♠12", "♠13"};
        List<Card> cList = HelpUtil.converCardArrayToCardList(cardArray);
        int result = compareCtrl.calcSpecial(cList);
        Assert.assertEquals(PokerDefine.COU_YI_SE, result);
    }

    @Test //黑红各一色，不为凑一色
    public void testC() throws Exception {
        String[] cardArray = {"♦1", "♦3", "♠9", "♠11", "♠13", "♠4", "♠5", "♠7", "♠8", "♠10", "♠11", "♠12", "♠13"};
        List<Card> cList = HelpUtil.converCardArrayToCardList(cardArray);
        int result = compareCtrl.calcSpecial(cList);
        Assert.assertEquals(PokerDefine.NULL, result);
    }
}
