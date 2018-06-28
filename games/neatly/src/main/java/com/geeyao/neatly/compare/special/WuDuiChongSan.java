package com.geeyao.neatly.compare.special;

import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.compare.CardSet;
import com.geeyao.neatly.logic.Card;

import java.util.List;

public class WuDuiChongSan extends CardSet {

    @Override
    public int getWeight(List<Card> cardList) {
        if (cardList.size() != CARD_COUNT_13) return PokerDefine.NULL;
        int[] numArray = new int[13];
        for (Card card : cardList) {
            numArray[card.getNumber() - 1]++;
        }
        int duiCount = 0;
        int sanCount = 0;
        for (int i = 0; i < numArray.length; i++) {
            if (numArray[i] == 2) {
                duiCount++;
            } else if (numArray[i] == 3) {
                sanCount++;
            }
        }
        if (sanCount == 1 && duiCount == 5) {
            return PokerDefine.WU_DUI_CHONG_SAN;
        }
        return PokerDefine.NULL;
    }
}