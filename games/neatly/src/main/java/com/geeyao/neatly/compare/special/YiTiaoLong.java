package com.geeyao.neatly.compare.special;

import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.compare.CardSet;
import com.geeyao.neatly.logic.Card;

import java.util.List;

public class YiTiaoLong extends CardSet {

    @Override
    public int getWeight(List<Card> cardList) {
        if (cardList.size() != CARD_COUNT_13) return PokerDefine.NULL;
        if (checkSequence(cardList)){
            return PokerDefine.YI_TIAO_LONG;
        }
        return PokerDefine.NULL;
    }
}
