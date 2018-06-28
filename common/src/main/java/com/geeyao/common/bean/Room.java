package com.geeyao.common.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Document
public class Room<T extends RoomPlayer> {
    boolean forceDissolve = true;
    List<T> players;
    private RoomConfig roomConfig;
    @Id
    private String roomId;
    private long roomNum;
    private RoomState roomState;
    private T owner;
    @Version
    private Long version;
    private Date createdDate = new Date();

    public Room() {
    }

    public Room(RoomConfig roomConfiguration, T ownerUser) {
        this.players = new ArrayList<>();
        this.roomId = UUID.randomUUID().toString();
        this.roomConfig = roomConfiguration;
        T roomPlayer = addPlayer(ownerUser);
        this.owner = roomPlayer;
        roomState = RoomState.Ready;
        forceDissolve = true;
    }

    public T addPlayer(T roomPlayer) {
        roomPlayer.setRoomId(roomId);
        roomPlayer.setSeatPosition(roomConfig.allocSeat(this));
        roomPlayer.setOnline(true);
        players.add(roomPlayer);
        return roomPlayer;
    }

    public void reset(){
        this.roomState = RoomState.Ready;
        for (T player : players) {
            player.clear();
        }
    }

    public void over(){
        this.roomState = RoomState.Over;
        for (T player : players) {
            player.clear();
        }
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void removePlayer(T roomPlayer) {
        players.remove(roomPlayer);
    }

    public <K extends RoomConfig> K getRoomConfig() {
        return (K) roomConfig;
    }

    public void setRoomConfig(RoomConfig roomConfig) {
        this.roomConfig = roomConfig;
    }

    public boolean stateEquals(RoomState roomState) {
        return roomState == this.roomState;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public boolean isForceDissolve() {
        return forceDissolve;
    }

    public void setForceDissolve(boolean forceDissolve) {
        this.forceDissolve = forceDissolve;
    }

    public RoomState getRoomState() {
        return roomState;
    }

    public void setRoomState(RoomState roomState) {
        this.roomState = roomState;
    }

    public T getOwner() {
        return owner;
    }

    public void setOwner(T owner) {
        this.owner = owner;
    }

    public T getPlayer(String playerMD5Uid) {
        for (T player : players) {
            if (player.is(playerMD5Uid)) {
                return player;
            }
        }
        return null;
    }

    public List<T> getPlayers() {
        return players;
    }

    public void setPlayers(List<T> players) {
        this.players = players;
    }

    public int getPlayerCount() {
        return players.size();
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public long getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(long roomNum) {
        this.roomNum = roomNum;
    }
}
