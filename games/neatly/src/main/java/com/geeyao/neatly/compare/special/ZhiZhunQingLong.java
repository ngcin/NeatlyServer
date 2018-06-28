package com.geeyao.neatly.compare.special;

import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.logic.Card;
import com.geeyao.neatly.compare.CardSet;

import java.util.List;

public class ZhiZhunQingLong extends CardSet {

    @Override
    public int getWeight(List<Card> cardList) {
        if (cardList.size() != CARD_COUNT_13) return PokerDefine.NULL;
        if (checkSequence(cardList) && checkSameSuit(cardList)) {
            return PokerDefine.ZHI_ZUN_QING_LONG;
        }
        return PokerDefine.NULL;
    }
}
