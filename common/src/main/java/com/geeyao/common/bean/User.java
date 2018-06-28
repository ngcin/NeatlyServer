package com.geeyao.common.bean;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
    @Indexed(unique = true)
    private String unionId;
    @Id
    private String userId;
    private long number;//编号
    private String nickName;
    private int sex;
    private String province;
    private String openId;
    private String country;
    private String headImgUrl;
    private String privilege;
    private String currentRoomId;

    private long roomCredits = 0;
    private int agentNum = 0;
    private boolean agent = false;
    private String lastGetShare = "2017-01-1 01:01:01";
    private String creditAgent = "";
    private String agentId = "";
    private String ip;
    private String clientSessionId;

    public User() {
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getCurrentRoomId() {
        return currentRoomId;
    }

    public void setCurrentRoomId(String currentRoomId) {
        this.currentRoomId = currentRoomId;
    }

    public long getRoomCredits() {
        return roomCredits;
    }

    public void setRoomCredits(long roomCredits) {
        this.roomCredits = roomCredits;
    }

    public int getAgentNum() {
        return agentNum;
    }

    public void setAgentNum(int agentNum) {
        this.agentNum = agentNum;
    }

    public boolean isAgent() {
        return agent;
    }

    public void setAgent(boolean agent) {
        this.agent = agent;
    }

    public String getLastGetShare() {
        return lastGetShare;
    }

    public void setLastGetShare(String lastGetShare) {
        this.lastGetShare = lastGetShare;
    }

    public String getCreditAgent() {
        return creditAgent;
    }

    public void setCreditAgent(String creditAgent) {
        this.creditAgent = creditAgent;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClientSessionId() {
        return clientSessionId;
    }

    public void setClientSessionId(String clientSessionId) {
        this.clientSessionId = clientSessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
