package com.geeyao.neatly.bean;

import com.geeyao.neatly.logic.Card;
import com.geeyao.neatly.logic.CardHand;

import java.util.ArrayList;
import java.util.List;

public class MessageBean {

    private String roomId;
    private String playerMD5Uid;
    private String playerState;
    private ArrayList<Card> cards;
    private int special;
    private int startCount;
    private int round;

    private NeatlyPlayer player;
    private List<CardHand> cardHands;
    private int countDown;
    private List<MessageBean> mbList;

    public String getPlayerState() {
        return playerState;
    }

    public void setPlayerState(String playerState) {
        this.playerState = playerState;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getPlayerMD5Uid() {
        return playerMD5Uid;
    }

    public void setPlayerMD5Uid(String playerMD5Uid) {
        this.playerMD5Uid = playerMD5Uid;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public int getSpecial() {
        return special;
    }

    public void setSpecial(int special) {
        this.special = special;
    }

    public NeatlyPlayer getPlayer() {
        return player;
    }

    public void setPlayer(NeatlyPlayer player) {
        this.player = player;
    }

    public List<CardHand> getCardHands() {
        return cardHands;
    }

    public void setCardHands(List<CardHand> cardHands) {
        this.cardHands = cardHands;
    }

    public int getCountDown() {
        return countDown;
    }

    public void setCountDown(int countDown) {
        this.countDown = countDown;
    }

    public int getStartCount() {
        return startCount;
    }

    public void setStartCount(int startCount) {
        this.startCount = startCount;
    }

    public List<MessageBean> getMbList() {
        return mbList;
    }

    public void setMbList(List<MessageBean> mbList) {
        this.mbList = mbList;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

}
