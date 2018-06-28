package com.geeyao.neatly.compare.special;

import com.geeyao.neatly.compare.CompareCtrl;
import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.logic.Card;
import com.geeyao.neatly.util.HelpUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SiTaoSanTiaoTest {

    CompareCtrl compareCtrl = new CompareCtrl();

    public SiTaoSanTiaoTest() {
        compareCtrl.init();
    }

    @Test //四套三条
    public void testA() throws Exception {
        String[] cardArray = {"♣2", "♦2", "♠2", "♣8", "♦8", "♠8", "♦10", "♠10", "♥10", "♦12", "♥12", "♣12", "♥13"};
        List<Card> cList = HelpUtil.converCardArrayToCardList(cardArray);
        int result = compareCtrl.calcSpecial(cList);
        Assert.assertEquals(PokerDefine.SI_TAO_SAN_TIAO, result);
    }

    @Test //三套三条+一炸弹，不能作为四套三条
    public void testB() throws Exception {
        String[] cardArray = {"♣2", "♦2", "♠2", "♣8", "♦8", "♠8", "♦10", "♠10", "♥10", "♦12", "♥12", "♣12", "♠12"};
        List<Card> cList = HelpUtil.converCardArrayToCardList(cardArray);
        int result = compareCtrl.calcSpecial(cList);
        Assert.assertEquals(PokerDefine.NULL, result);
    }

}
