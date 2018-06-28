package com.geeyao.neatly.compare.special;

import com.geeyao.neatly.compare.CompareCtrl;
import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.logic.Card;
import com.geeyao.neatly.util.HelpUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class WuDuiChongSanTest {

    CompareCtrl compareCtrl = new CompareCtrl();

    public WuDuiChongSanTest() {
        compareCtrl.init();
    }

    @Test //五对冲三
    public void testA() throws Exception {
        String[] cardArray = {"♣3", "♦3", "♣5", "♠5", "♦8", "♠8", "♦9", "♠9", "♥10", "♦10", "♥10", "♣12", "♥12"};
        List<Card> cList = HelpUtil.converCardArrayToCardList(cardArray);
        int result = compareCtrl.calcSpecial(cList);
        Assert.assertEquals(PokerDefine.WU_DUI_CHONG_SAN, result);
    }

    @Test //
    public void testB() throws Exception {
        String[] cardArray = {"♣3", "♦3", "♣5", "♠5", "♦8", "♠8", "♦9", "♠9", "♥10", "♦10", "♥10", "♣12", "♥13"};
        List<Card> cList = HelpUtil.converCardArrayToCardList(cardArray);
        int result = compareCtrl.calcSpecial(cList);
        Assert.assertEquals(PokerDefine.NULL, result);
    }

    @Test //
    public void testC() throws Exception {
        String[] cardArray = {"♣3", "♦3", "♣5", "♠5", "♦8", "♠8", "♦9", "♠9", "♣9", "♥9", "♣10", "♦10", "♥10"};
        List<Card> cList = HelpUtil.converCardArrayToCardList(cardArray);
        int result = compareCtrl.calcSpecial(cList);
        Assert.assertEquals(PokerDefine.NULL, result);
    }
}
