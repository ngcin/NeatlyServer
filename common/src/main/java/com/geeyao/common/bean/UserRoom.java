package com.geeyao.common.bean;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserRoom {

    private String userId;
    private String roomId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
