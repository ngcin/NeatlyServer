package com.geeyao.neatly.compare.simple;

import com.geeyao.neatly.compare.CardSet;
import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.logic.Card;

import java.util.List;

public class TongHua extends CardSet {
    int typeVal = PokerDefine.TONG_HUA << PLACE_6;

    /**
     * 判断中道和尾道（5张）
     */
    @Override
    public int getWeight(List<Card> cardList) {
        if (cardList.size() != CARD_COUNT_5) return PokerDefine.NULL;
        if (checkSameSuit(cardList)) {
            cardList = sortCardByEndA(cardList);
            return typeVal + getCardListWeight(cardList);
        }
        return PokerDefine.NULL;
    }
}
