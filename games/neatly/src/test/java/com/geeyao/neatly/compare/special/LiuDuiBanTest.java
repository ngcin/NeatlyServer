package com.geeyao.neatly.compare.special;

import com.geeyao.neatly.compare.CompareCtrl;
import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.logic.Card;
import com.geeyao.neatly.util.HelpUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class LiuDuiBanTest {

    CompareCtrl compareCtrl = new CompareCtrl();

    public LiuDuiBanTest() {
        compareCtrl.init();
    }

    @Test //六对半
    public void testA() throws Exception {
        String[] cardArray = {"♣1", "♦2", "♣2", "♠3", "♦3", "♠4", "♦4", "♠5", "♥5", "♦6", "♥6", "♣7", "♥7"};
        List<Card> cList = HelpUtil.converCardArrayToCardList(cardArray);
        int result = compareCtrl.calcSpecial(cList);
        Assert.assertEquals(PokerDefine.LIU_DUI_BAN, result);
    }

    @Test //3张三，不算两对，非六对半
    public void testB() throws Exception {
        String[] cardArray = {"♣3", "♦3", "♣3", "♠3", "♦4", "♠4", "♦5", "♠5", "♥6", "♦6", "♥9", "♣9", "♥12"};
        List<Card> cList = HelpUtil.converCardArrayToCardList(cardArray);
        int result = compareCtrl.calcSpecial(cList);
        Assert.assertEquals(PokerDefine.NULL, result);
    }

    @Test //有三张单张，只有五对，非六对半
    public void testC() throws Exception {
        String[] cardArray = {"♣1", "♦2", "♣3", "♠3", "♦4", "♠4", "♦5", "♠5", "♥6", "♦6", "♥9", "♣9", "♥12"};
        List<Card> cList = HelpUtil.converCardArrayToCardList(cardArray);
        int result = compareCtrl.calcSpecial(cList);
        Assert.assertEquals(PokerDefine.NULL, result);
    }

    @Test //优先判定为五对冲三
    public void testD() throws Exception {
        String[] cardArray = {"♣3", "♦3", "♣5", "♠5", "♦8", "♠8", "♦9", "♠9", "♥10", "♦10", "♥10", "♣12", "♥12"};
        List<Card> cList = HelpUtil.converCardArrayToCardList(cardArray);
        int result = compareCtrl.calcSpecial(cList);
        Assert.assertEquals(PokerDefine.WU_DUI_CHONG_SAN, result);
    }
}
