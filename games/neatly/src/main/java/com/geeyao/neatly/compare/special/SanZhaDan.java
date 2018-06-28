package com.geeyao.neatly.compare.special;

import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.compare.CardSet;
import com.geeyao.neatly.logic.Card;

import java.util.List;

public class SanZhaDan extends CardSet {

    @Override
    public int getWeight(List<Card> cardList) {
        if (cardList.size() != CARD_COUNT_13) return PokerDefine.NULL;
        int[] numArray = new int[13];
        for (Card card : cardList) {
            numArray[card.getNumber() - 1]++;
        }
        int siCount = 0;
        for (int i = 0; i < numArray.length; i++) {
            if (numArray[i] == 4) {
                siCount++;
            }
        }
        if (siCount == 3) {
            return PokerDefine.SAN_ZHA_DAN;
        }
        return PokerDefine.NULL;
    }
}
