package com.geeyao.neatly.bean;

import com.geeyao.neatly.logic.Card;

import java.io.Serializable;
import java.util.List;

public class ReplayItem implements Serializable {
    private String playerId;
    private long number;
    private String nickName;
    private List<Card> cards;
    private int topScore;
    private int topSpecialScore;
    private int centerScore;
    private int centerSpecialScore;
    private int bottomScore;
    private int bottomSpecialScore;
    private int score;
    private String headImgUrl;
    private int special = 0;

    public ReplayItem(){
    }

    public ReplayItem(NeatlyPlayer player){
        this.playerId = player.getPlayerMD5Uid();
        this.number = player.getNumber();
        this.cards = player.getSortedCards();
        this.topScore = player.getTopScore();
        this.topSpecialScore = player.getTopSpecialScore();
        this.centerScore = player.getCenterScore();
        this.centerSpecialScore = player.getCenterSpecialScore();
        this.bottomScore = player.getBottomScore();
        this.bottomSpecialScore = player.getBottomSpecialScore();
        this.score = player.getLastScore();
        this.headImgUrl = player.getHeadImgUrl();
        this.nickName = player.getNickName();
        if(player.getPlayerSpecial() > 0){
            this.special = player.getSpecial();
        }
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTopScore() {
        return topScore;
    }

    public void setTopScore(int topScore) {
        this.topScore = topScore;
    }

    public int getCenterScore() {
        return centerScore;
    }

    public void setCenterScore(int centerScore) {
        this.centerScore = centerScore;
    }

    public int getBottomScore() {
        return bottomScore;
    }

    public void setBottomScore(int bottomScore) {
        this.bottomScore = bottomScore;
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

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public int getSpecial() {
        return special;
    }

    public void setSpecial(int special) {
        this.special = special;
    }
}
