package com.geeyao.neatly.compare.special;

import com.geeyao.neatly.compare.CompareCtrl;
import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.logic.Card;
import com.geeyao.neatly.util.HelpUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SanTongHuaTest {

    CompareCtrl compareCtrl = new CompareCtrl();

    public SanTongHuaTest() {
        compareCtrl.init();
    }

    @Test //三同花
    public void testA() throws Exception {
        String[] cardArray = {"♦1", "♦3", "♦5", "♠2", "♠5", "♠7", "♠9", "♠10", "♥6", "♥8", "♥10", "♥12", "♥13"};
        List<Card> cardList = HelpUtil.converCardArrayToCardList(cardArray);
        int result = compareCtrl.calcSpecial(cardList);
        Assert.assertEquals(PokerDefine.SAN_TONG_HUA, result);
    }

    @Test //8黑桃（3黑桃+5黑桃），5红桃：不能判定为三同花
    public void testB() throws Exception {
        String[] cardArray = {"♠1", "♠3", "♠5", "♠2", "♠5", "♠7", "♠9", "♠10", "♥6", "♥8", "♥10", "♥12", "♥13"};
        List<Card> cardList = HelpUtil.converCardArrayToCardList(cardArray);
        int result = compareCtrl.calcSpecial(cardList);
        Assert.assertEquals(PokerDefine.NULL, result);
    }
}
