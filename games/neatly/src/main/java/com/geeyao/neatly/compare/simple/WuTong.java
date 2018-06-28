package com.geeyao.neatly.compare.simple;

import com.geeyao.neatly.compare.CardSet;
import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.logic.Card;

import java.util.List;

public class WuTong extends CardSet {
    int typeVal = PokerDefine.WU_TONG << PLACE_6;

    @Override
    public int getWeight(List<Card> cardList) {
        if (cardList.size() != CARD_COUNT_5) return PokerDefine.NULL;
        //判断5个一样
        int first = cardList.get(0).getNumber();
        for (int i = 1; i < cardList.size(); i++) {
            if (first != cardList.get(i).getNumber()) {
                return PokerDefine.NULL;
            }
        }
        return typeVal + (getCardValue(first) << PLACE_1);
    }
}
