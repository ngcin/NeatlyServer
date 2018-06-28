package com.geeyao.common.bean;


import org.springframework.util.DigestUtils;

import java.util.Objects;

public abstract class RoomPlayer {
    private String playerMD5Uid;
    private String clientSessionId;
    private String roomId;
    private long number;
    private String nickName;
    private String headImgUrl;
    private boolean online;

    private int seatPosition = 0;
    private boolean dissolve = false;

    public RoomPlayer() {
    }

    public RoomPlayer(User user) {
        this.playerMD5Uid = DigestUtils.md5DigestAsHex(user.getUnionId().getBytes());
        this.clientSessionId = user.getClientSessionId();
        this.roomId = user.getCurrentRoomId();
        this.nickName = user.getNickName();
        this.headImgUrl = user.getHeadImgUrl();
        this.number = user.getNumber();
    }

    public abstract void clear();

    public boolean is(String gamePlayerId) {
        return Objects.equals(gamePlayerId, this.playerMD5Uid);
    }

    public String getPlayerMD5Uid() {
        return playerMD5Uid;
    }

    public void setPlayerMD5Uid(String playerMD5Uid) {
        this.playerMD5Uid = playerMD5Uid;
    }

    public int getSeatPosition() {
        return seatPosition;
    }

    public void setSeatPosition(int seatPosition) {
        this.seatPosition = seatPosition;
    }

    public boolean isDissolve() {
        return dissolve;
    }

    public void setDissolve(boolean dissolve) {
        this.dissolve = dissolve;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getClientSessionId() {
        return clientSessionId;
    }

    public void setClientSessionId(String clientSessionId) {
        this.clientSessionId = clientSessionId;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }
}
