package com.geeyao.neatly.compare.special;

import com.geeyao.neatly.compare.CompareCtrl;
import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.logic.Card;
import com.geeyao.neatly.util.HelpUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class QuanXiaoTest {

    CompareCtrl compareCtrl = new CompareCtrl();

    public QuanXiaoTest() {
        compareCtrl.init();
    }

    @Test //全小
    public void testA() throws Exception {
        String[] cardArray = {"♦2", "♠2", "♣2", "♦3", "♦4", "♥4", "♦5", "♠5", "♥5", "♥6", "♥7", "♥8", "♣8"};
        List<Card> cList = HelpUtil.converCardArrayToCardList(cardArray);
        int result = compareCtrl.calcSpecial(cList);
        Assert.assertEquals(PokerDefine.QUAN_XIAO, result);
    }

    @Test //含A，不为全小
    public void testB() throws Exception {
        String[] cardArray = {"♦1", "♠2", "♣2", "♦3", "♦4", "♥4", "♦5", "♠5", "♥5", "♥6", "♥7", "♥8", "♣8"};
        List<Card> cList = HelpUtil.converCardArrayToCardList(cardArray);
        int result = compareCtrl.calcSpecial(cList);
        Assert.assertEquals(PokerDefine.NULL, result);
    }
}
