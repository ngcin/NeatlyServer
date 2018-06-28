package com.geeyao.neatly.compare.special;

import com.geeyao.neatly.compare.CompareCtrl;
import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.logic.Card;
import com.geeyao.neatly.util.HelpUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SanTongHuaShunTest {

    CompareCtrl compareCtrl = new CompareCtrl();

    public SanTongHuaShunTest() {
        compareCtrl.init();
    }

    @Test //三同花顺
    public void testA() throws Exception {
        String[] cardArray1 = {"♦2", "♦3", "♦4", "♥5", "♥6", "♥7", "♥8", "♥9", "♠8", "♠9", "♠10", "♠11", "♠12"};
        List<Card> cList1 = HelpUtil.converCardArrayToCardList(cardArray1);
        int result1 = compareCtrl.calcSpecial(cList1);
        Assert.assertEquals(PokerDefine.SAN_TONG_HUA_SHUN, result1);

        String[] cardArray2 = {"♦1", "♦2", "♦3", "♥1", "♥2", "♥3", "♥4", "♥5", "♠10", "♠11", "♠12", "♠13", "♠1"};
        List<Card> cList2 = HelpUtil.converCardArrayToCardList(cardArray2);
        int result2 = compareCtrl.calcSpecial(cList2);
        Assert.assertEquals(PokerDefine.SAN_TONG_HUA_SHUN, result2);

        String[] cardArray3 = {"♦1", "♦2", "♦3", "♥1", "♥2", "♥3", "♥4", "♥5", "♠10", "♠11", "♠12", "♠13", "♠9"};
        List<Card> cList3 = HelpUtil.converCardArrayToCardList(cardArray3);
        int result3 = compareCtrl.calcSpecial(cList3);
        Assert.assertEquals(PokerDefine.SAN_TONG_HUA_SHUN, result3);

        String[] cardArray4 = {"♦1", "♦2", "♦3", "♦1", "♥2", "♥3", "♥4", "♥5", "♠10", "♠11", "♠12", "♠13", "♠9"};
        List<Card> cList4 = HelpUtil.converCardArrayToCardList(cardArray4);
        int result4 = compareCtrl.calcSpecial(cList4);
        Assert.assertEquals(PokerDefine.SAN_SHUN_ZI, result4);

        String[] cardArray5 = {"♦1", "♦2", "♦3", "♥2", "♥3", "♥4", "♥5", "♥6", "♠10", "♠11", "♠12", "♠13", "♠1"};
        List<Card> cList5 = HelpUtil.converCardArrayToCardList(cardArray5);
        int result5 = compareCtrl.calcSpecial(cList5);
        Assert.assertEquals(PokerDefine.SAN_TONG_HUA_SHUN, result5);
    }

}
