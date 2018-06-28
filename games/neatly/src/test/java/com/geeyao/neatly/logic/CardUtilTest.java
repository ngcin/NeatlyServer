package com.geeyao.neatly.logic;

import com.geeyao.neatly.compare.CardSet;
import com.geeyao.neatly.util.HelpUtil;
import org.junit.Test;

import java.util.List;

public class CardUtilTest {


    @Test //测试牌型推荐
    public void test() throws Exception {
        String[] cardArray1 = {"♦2", "♦3", "♦4", "♥5", "♥6", "♥7", "♥8", "♥9", "♠8", "♠9", "♠10", "♠11", "♠12"};
        List<Card> cList = HelpUtil.converCardArrayToCardList(cardArray1);
        CardSet.sortCards(cList);
    }
}
