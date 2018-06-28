package com.geeyao.neatly.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeyao.common.bean.RoomPlayer;
import com.geeyao.common.bean.User;
import com.geeyao.neatly.logic.Card;
import com.geeyao.neatly.logic.CardHand;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NeatlyPlayer extends RoomPlayer implements Serializable {
    private boolean ready = false;
    private boolean playing = false;
    private boolean finishGroup = false;
    private boolean finishCompare = false;
    private int times = 1;
    private int vip = 1;
    private int pos = 0;
    private int mark = 0;   //总分

    //TODO: 这2个属性必须移出去，不然每次把Room数据返回的时候，玩家可以通过监听http数据看到其他玩家的牌
    @JsonIgnore
    private List<Card> originalCards = new ArrayList();
    @JsonIgnore
    private List<Card> sortedCards = new ArrayList();
    private int special = 0;
    private int playerSpecial = 0;
    @JsonIgnore
    private List<CardHand> cardHands;

    private int lastScore;

    private int topScore;
    private int topSpecialScore;
    private int topResult;
    private int centerScore;
    private int centerSpecialScore;
    private int centerResult;
    private int bottomScore;
    private int bottomSpecialScore;
    private int bottomResult;

//    @JsonIgnore
    private Map<String, Integer> daQiang = new HashMap<String, Integer>();
//    @JsonIgnore
    private int specialScore;

    public NeatlyPlayer() {
    }

    public NeatlyPlayer(User user) {
        super(user);
    }

    @Override
    public void clear() {
        ready = false;
        playing = false;
        finishGroup = false;
        finishCompare = false;
        topScore = 0;
        topSpecialScore = 0;
        topResult = 0;
        centerScore = 0;
        centerSpecialScore = 0;
        centerResult = 0;
        bottomScore = 0;
        bottomSpecialScore = 0;
        bottomResult = 0;
        daQiang.clear();
        specialScore = 0;
        originalCards.clear();
        sortedCards.clear();
        lastScore = 0;
    }

    public void addTopScore(int score) {
        topScore += score;
        addLstScore(score);
    }

    public void addTopSpecialScore(int score) {
        topSpecialScore += score;
        addLstScore(score);
    }

    public void addCenterScore(int score) {
        centerScore += score;
        addLstScore(score);
    }

    public void addCenterSpecialScore(int score) {
        centerSpecialScore += score;
        addLstScore(score);
    }

    public void addBottomScore(int score) {
        bottomScore += score;
        addLstScore(score);
    }

    public void addBottomSpecialScore(int score) {
        bottomSpecialScore += score;
        addLstScore(score);
    }

    public List<Card> topCards() {
        List<Card> cardList = new ArrayList();
        cardList = sortedCards.subList(0, 3);
        return cardList;
    }

    public List<Card> centralCards() {
        List<Card> cardList = new ArrayList();
        cardList = sortedCards.subList(3, 8);
        return cardList;
    }

    public List<Card> bottomCards() {
        List<Card> cardList = new ArrayList();
        cardList = sortedCards.subList(8, 13);
        return cardList;
    }

    public List<Card> getOriginalCards() {
        return originalCards;
    }

    public void setOriginalCards(List<Card> originalCards) {
        this.originalCards = originalCards;
    }

    public List<Card> getSortedCards() {
        return sortedCards;
    }

    public void setSortedCards(ArrayList<Card> sortedCards) {
        this.sortedCards = sortedCards;
    }

    public void addLstScore(int score) {
        lastScore += score;
    }

    public void clearing() {
        mark += lastScore;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public int getSpecial() {
        return special;
    }

    public void setSpecial(int special) {
        this.special = special;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getLastScore() {
        return lastScore;
    }

    public void setLastScore(int lastScore) {
        this.lastScore = lastScore;
    }

    public int getTopScore() {
        return topScore;
    }

    public void setTopScore(int topScore) {
        this.topScore = topScore;
    }

    public int getTopResult() {
        return topResult;
    }

    public void setTopResult(int topResult) {
        this.topResult = topResult;
    }

    public int getCenterScore() {
        return centerScore;
    }

    public void setCenterScore(int centerScore) {
        this.centerScore = centerScore;
    }

    public int getCenterResult() {
        return centerResult;
    }

    public void setCenterResult(int centerResult) {
        this.centerResult = centerResult;
    }

    public int getBottomScore() {
        return bottomScore;
    }

    public void setBottomScore(int bottomScore) {
        this.bottomScore = bottomScore;
    }

    public int getBottomResult() {
        return bottomResult;
    }

    public void setBottomResult(int bottomResult) {
        this.bottomResult = bottomResult;
    }

    public Map<String, Integer> getDaQiang() {
        return daQiang;
    }

    public void setDaQiang(Map<String, Integer> daQiang) {
        this.daQiang = daQiang;
    }

    public List<CardHand> getCardHands() {
        return cardHands;
    }

    public void setCardHands(List<CardHand> cardHands) {
        this.cardHands = cardHands;
    }

    public boolean isFinishGroup() {
        return finishGroup;
    }

    public void setFinishGroup(boolean finishGroup) {
        this.finishGroup = finishGroup;
    }

    public int getTopSpecialScore() {
        return topSpecialScore;
    }

    public void setTopSpecialScore(int topSpecialScore) {
        this.topSpecialScore = topSpecialScore;
    }

    public int getCenterSpecialScore() {
        return centerSpecialScore;
    }

    public void setCenterSpecialScore(int centerSpecialScore) {
        this.centerSpecialScore = centerSpecialScore;
    }

    public int getBottomSpecialScore() {
        return bottomSpecialScore;
    }

    public void setBottomSpecialScore(int bottomSpecialScore) {
        this.bottomSpecialScore = bottomSpecialScore;
    }

    public boolean isFinishCompare() {
        return finishCompare;
    }

    public void setFinishCompare(boolean finishCompare) {
        this.finishCompare = finishCompare;
    }

    public int getPlayerSpecial() {
        return playerSpecial;
    }

    public void setPlayerSpecial(int playerSpecial) {
        this.playerSpecial = playerSpecial;
    }

    public int getSpecialScore() {
        return specialScore;
    }

    public void setSpecialScore(int specialScore) {
        this.specialScore = specialScore;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
}
