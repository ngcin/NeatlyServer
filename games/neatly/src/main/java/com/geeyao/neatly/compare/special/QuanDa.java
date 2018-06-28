package com.geeyao.neatly.compare.special;

import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.compare.CardSet;
import com.geeyao.neatly.logic.Card;

import java.util.List;

public class QuanDa extends CardSet {

    @Override
    public int getWeight(List<Card> cardList) {
        if (cardList.size() != CARD_COUNT_13) return PokerDefine.NULL;
        for (int i = 0; i < cardList.size(); i++) {
            int num = cardList.get(i).getNumber();
            if (num < CARD_8 && num != CARD_A) {
                return PokerDefine.NULL;
            }
        }
        return PokerDefine.QUAN_DA;
    }
}
