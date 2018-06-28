package com.geeyao.neatly.bean;

import com.geeyao.common.bean.Room;

import java.util.List;

public class AckDataBean {

    private List<MessageBean> mbList;
    private Room room;
    private String userMD5Uid;

    public List<MessageBean> getMbList() {
        return mbList;
    }

    public void setMbList(List<MessageBean> mbList) {
        this.mbList = mbList;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getUserMD5Uid() {
        return userMD5Uid;
    }

    public void setUserMD5Uid(String userMD5Uid) {
        this.userMD5Uid = userMD5Uid;
    }
}
