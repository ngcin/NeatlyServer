package com.geeyao.neatly.compare.special;

import com.geeyao.neatly.compare.CompareCtrl;
import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.logic.Card;
import com.geeyao.neatly.util.HelpUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SanShunZiTest {

    CompareCtrl compareCtrl = new CompareCtrl();

    public SanShunZiTest() {
        compareCtrl.init();
    }

    @Test
    public void testA() throws Exception {
        String[] cardArray1 = {"♣2", "♦3", "♣4", "♠5", "♦6", "♦5", "♠6", "♥7", "♦8", "♥9", "♣12", "♥13", "♥11"};
        List<Card> cardList1 = HelpUtil.converCardArrayToCardList(cardArray1);
        int result1 = compareCtrl.calcSpecial(cardList1);
        Assert.assertEquals(PokerDefine.SAN_SHUN_ZI, result1);

        String[] cardArray2 = {"♣2", "♦3", "♣4", "♠5", "♦6", "♠2", "♦3", "♠4", "♥5", "♦6", "♥11", "♣12", "♥13"};
        List<Card> cardList2 = HelpUtil.converCardArrayToCardList(cardArray2);
        int result2 = compareCtrl.calcSpecial(cardList2);
        Assert.assertEquals(PokerDefine.SAN_SHUN_ZI, result2);

        String[] cardArray3 = {"♣2", "♦3", "♣4", "♠5", "♦6", "♠2", "♦3", "♠4", "♥5", "♦6", "♥1", "♣12", "♥13"};
        List<Card> cardList3 = HelpUtil.converCardArrayToCardList(cardArray3);
        int result3 = compareCtrl.calcSpecial(cardList3);
        Assert.assertEquals(PokerDefine.SAN_SHUN_ZI, result3);

        String[] cardArray4 = {"♣1", "♠2", "♦3", "♦3", "♣4", "♠4", "♠5", "♥5", "♦6", "♦10", "♥11", "♣12", "♥13"};
        List<Card> cardList4 = HelpUtil.converCardArrayToCardList(cardArray4);
        int result4 = compareCtrl.calcSpecial(cardList4);
        Assert.assertEquals(PokerDefine.SAN_SHUN_ZI, result4);

        String[] cardArray5 = {"♣1", "♦2", "♠3", "♠1", "♠4", "♦2", "♥4", "♦5", "♣3", "♦5", "♣12", "♥11", "♥13"};
        List<Card> cardList5 = HelpUtil.converCardArrayToCardList(cardArray5);
        int result5 = compareCtrl.calcSpecial(cardList5);
        Assert.assertEquals(PokerDefine.SAN_SHUN_ZI, result5);

        String[] cardArray6 = {"♣1", "♠2", "♦3", "♦4", "♣5", "♠1", "♠2", "♥3", "♦4", "♦10", "♥11", "♣12", "♥13"};
        List<Card> cardList6 = HelpUtil.converCardArrayToCardList(cardArray6);
        int result6 = compareCtrl.calcSpecial(cardList6);
        Assert.assertEquals(PokerDefine.SAN_SHUN_ZI, result6);

    }
}
