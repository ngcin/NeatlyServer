package com.geeyao.neatly.logic;

import com.geeyao.common.bean.User;
import com.geeyao.neatly.bean.NeatlyPlayer;
import com.geeyao.neatly.util.HelpUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CardDealerTest {
//    CardDealer cardDealer = new CardDealer(config.getPeople(), c1, c2, c3);
//
//    @Test
//    public void testDealWith3Players() throws Exception {
//        testDeal(3);
//    }
//
//    @Test
//    public void testDealWith4Players() throws Exception {
//        testDeal(4);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testDealWith5Players() throws Exception {
//        testDeal(5);
//    }
//
//    public void testDeal(int playerCount) throws Exception {
//        List<NeatlyPlayer> players = new ArrayList<>();
//        for (int i = 0; i < playerCount; i++) {
//            User user = new User();
//            user.setUnionId("session id");
//            players.add(new NeatlyPlayer(user));
//        }
//        cardDealer.deal(players);
//        for (NeatlyPlayer neatlyPlayer : players) {
//            List<String> cardStrings = new ArrayList<>();
//            List<Card> originalCards = neatlyPlayer.getOriginalCards();
//            Assert.assertEquals(13, originalCards.size());
//            for (Card originalCard : originalCards) {
//                String cardString = " " + HelpUtil.getSuitStr(originalCard.getSuit()) + originalCard.getNumber() + " ";
//                if (cardStrings.contains(cardString)) {
//                    throw new AssertionError("重复发牌");
//                }
//                cardStrings.add(cardString);
//            }
//            Assert.assertEquals(13, cardStrings.size());
//            System.out.println(cardStrings.toString());
//
//        }
//        Assert.assertEquals(52, cardDealer.allCards.size());
//    }

}