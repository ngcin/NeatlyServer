package com.geeyao.neatly.compare.special;

import com.geeyao.neatly.compare.CompareCtrl;
import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.logic.Card;
import com.geeyao.neatly.util.HelpUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ShiErHuangZuTest {

    CompareCtrl compareCtrl = new CompareCtrl();

    public ShiErHuangZuTest() {
        compareCtrl.init();
    }

    @Test //十二皇族
    public void testA() throws Exception {
        String[] cardArray = {"♦1", "♠1", "♣1", "♠13", "♣13", "♦12", "♠12", "♣12", "♥12", "♦11", "♠11", "♣11", "♥11"};
        List<Card> cList = HelpUtil.converCardArrayToCardList(cardArray);
        int result = compareCtrl.calcSpecial(cList);
        Assert.assertEquals(PokerDefine.SHI_ER_HUANG_ZU, result);
    }

    @Test //含有10，不为十二皇族，为全大
    public void testB() throws Exception {
        String[] cardArray = {"♦1", "♠1", "♣1", "♠13", "♣13", "♦12", "♠12", "♣12", "♥12", "♦11", "♠11", "♣11", "♥10"};
        List<Card> cList = HelpUtil.converCardArrayToCardList(cardArray);
        int result = compareCtrl.calcSpecial(cList);
        Assert.assertEquals(PokerDefine.QUAN_DA, result);
    }
}
