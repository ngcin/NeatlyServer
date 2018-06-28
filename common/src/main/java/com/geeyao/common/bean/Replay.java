package com.geeyao.common.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Replay<T> {
    @Id
    private String replayId;
    //房间号
    private String roomId;
    //局数
    private int round;
    //结果
    private List<T> items;

    public String getReplayId() {
        return replayId;
    }

    public void setReplayId(String replayId) {
        this.replayId = replayId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
