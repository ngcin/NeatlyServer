package com.geeyao.neatly.compare.special;

import com.geeyao.neatly.compare.PokerDefine;
import com.geeyao.neatly.logic.Card;
import com.geeyao.neatly.compare.CardSet;

import java.util.List;

public class LiuDuiBan extends CardSet {

    @Override
    public int getWeight(List<Card> cardList) {
        if (cardList.size() != CARD_COUNT_13) return PokerDefine.NULL;
        int[] numArray = new int[13];
        for (Card card : cardList) {
            numArray[card.getNumber() - 1]++;
        }
        int duiCount = 0;
        for (int i = 0; i < numArray.length; i++) {
            if (numArray[i] == 2) {
                duiCount++;
            }
        }
        if (duiCount == 6) {
            return PokerDefine.LIU_DUI_BAN;
        }
        return PokerDefine.NULL;
    }
}
