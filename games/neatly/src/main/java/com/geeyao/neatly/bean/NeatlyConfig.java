package com.geeyao.neatly.bean;

import com.geeyao.common.bean.Room;
import com.geeyao.common.bean.RoomConfig;
import com.geeyao.common.bean.RoomPlayer;
import com.geeyao.neatly.logic.PokerSuitEnum;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class NeatlyConfig implements RoomConfig, Serializable {
    //房间人数
    private int people;
    //组牌时间
    private int roundTime;
    //总局数
    private int totalRound;
    //当前局数
    private int currentRound;
    //坐庄
    private int bankerMode;
    //房卡
    private int costMode;
    //倍率
    private int multi;
    //当前局开始时间
    private Date roundStartDate = new Date();

    private int times;
    private int games;
    private int suit;
    private boolean suitMode;
    private int clientType;

    @Override
    public int allocSeat(Room room) {
        List<RoomPlayer> players = room.getPlayers();
        for (int i = 0; i < people; i++) {
            int seatId = i + 1;
            for (int j = 0; j < players.size(); j++) {
                if (players.get(j).getSeatPosition() == seatId) {
                    seatId = -1;
                    break;
                }
            }
            if (seatId != -1) {
                return seatId;
            }
        }
        return -1;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public int getRoundTime() {
        return roundTime;
    }

    public void setRoundTime(int roundTime) {
        this.roundTime = roundTime;
    }

    public int getCostMode() {
        return costMode;
    }

    public void setCostMode(int costMode) {
        this.costMode = costMode;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public int getSuit() {
        return suit;
    }

    public void setSuit(int suit) {
        this.suit = suit;
    }

    public boolean isSuitMode() {
        return suitMode;
    }

    public void setSuitMode(boolean suitMode) {
        this.suitMode = suitMode;
    }

    public int getClientType() {
        return clientType;
    }

    public void setClientType(int clientType) {
        this.clientType = clientType;
    }

    public int getTotalRound() {
        return totalRound;
    }

    public void setTotalRound(int totalRound) {
        this.totalRound = totalRound;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public int getBankerMode() {
        return bankerMode;
    }

    public void setBankerMode(int bankerMode) {
        this.bankerMode = bankerMode;
    }

    public int getMulti() {
        return multi;
    }

    public void setMulti(int multi) {
        this.multi = multi;
    }

    public Date getRoundStartDate() {
        return roundStartDate;
    }

    public void setRoundStartDate(Date roundStartDate) {
        this.roundStartDate = roundStartDate;
    }
}
