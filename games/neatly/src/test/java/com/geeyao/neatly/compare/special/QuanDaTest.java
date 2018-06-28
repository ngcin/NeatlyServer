package com.geeyao.neatly.compare.special;

import com.geeyao.neatly.compare.CompareCtrl;
import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.logic.Card;
import com.geeyao.neatly.util.HelpUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class QuanDaTest {

    CompareCtrl compareCtrl = new CompareCtrl();

    public QuanDaTest() {
        compareCtrl.init();
    }

    @Test
    public void testA() throws Exception {
        String[] cardArray = {"♦1", "♠1", "♣13", "♦13", "♦12", "♥11", "♦11", "♠11", "♥10", "♥9", "♥9", "♥8", "♣8"};
        List<Card> cList = HelpUtil.converCardArrayToCardList(cardArray);
        int result = compareCtrl.calcSpecial(cList);
        Assert.assertEquals(PokerDefine.QUAN_DA, result);
    }

    @Test //含七，不为全大
    public void test() throws Exception {
        String[] cardArray = {"♦1", "♠1", "♣13", "♦13", "♦12", "♥11", "♦11", "♠11", "♥10", "♥9", "♥9", "♥8", "♣7"};
        List<Card> cList = HelpUtil.converCardArrayToCardList(cardArray);
        int result = compareCtrl.calcSpecial(cList);
        Assert.assertEquals(PokerDefine.NULL, result);
    }
}
