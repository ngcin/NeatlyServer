package com.geeyao.neatly.compare.special;

import com.geeyao.neatly.compare.CompareCtrl;
import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.logic.Card;
import com.geeyao.neatly.util.HelpUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class YiTiaoLongTest {

    CompareCtrl compareCtrl = new CompareCtrl();

    public YiTiaoLongTest() {
        compareCtrl.init();
    }

    @Test
    public void testA() throws Exception {
        String[] cardArray = {"♣1", "♦2", "♣3", "♠4", "♦5", "♠6", "♦7", "♠8", "♥9", "♦10", "♥11", "♣12", "♥13"};
        List<Card> cList = HelpUtil.converCardArrayToCardList(cardArray);
        int result = compareCtrl.calcSpecial(cList);
        Assert.assertEquals(PokerDefine.YI_TIAO_LONG, result);
    }

    @Test
    public void testB() throws Exception {
        String[] cardArray = {"♣1", "♦2", "♣3", "♠4", "♦5", "♠6", "♦7", "♠8", "♥9", "♦10", "♥11", "♣12", "♥12"};
        List<Card> cList = HelpUtil.converCardArrayToCardList(cardArray);
        int result = compareCtrl.calcSpecial(cList);
        Assert.assertEquals(PokerDefine.NULL, result);
    }
}
