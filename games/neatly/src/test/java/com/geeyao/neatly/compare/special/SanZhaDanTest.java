package com.geeyao.neatly.compare.special;

import com.geeyao.neatly.compare.CompareCtrl;
import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.logic.Card;
import com.geeyao.neatly.util.HelpUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SanZhaDanTest {

    CompareCtrl compareCtrl = new CompareCtrl();

    public SanZhaDanTest() {
        compareCtrl.init();
    }

    @Test
    public void testA() throws Exception {
        String[] cardArray = {"♣1", "♦1", "♥1", "♠1", "♦2", "♠2", "♥2", "♣2", "♥8", "♦8", "♠8", "♣8", "♥7"};
        List<Card> cList = HelpUtil.converCardArrayToCardList(cardArray);
        int result = compareCtrl.calcSpecial(cList);
        Assert.assertEquals(PokerDefine.SAN_ZHA_DAN, result);
    }

    @Test
    public void testB() throws Exception {
        String[] cardArray = {"♣1", "♦1", "♥1", "♠1", "♦2", "♠2", "♥2", "♣2", "♥8", "♦8", "♠8", "♣7", "♥7"};
        List<Card> cList = HelpUtil.converCardArrayToCardList(cardArray);
        int result = compareCtrl.calcSpecial(cList);
        Assert.assertEquals(PokerDefine.NULL, result);
    }
}
