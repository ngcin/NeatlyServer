package com.geeyao.common.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WxUserInfo {
    @JsonProperty("nickname")
    private String nickName;
    @JsonProperty("sex")
    private int sex;
    @JsonProperty("province")
    private String province;
    @JsonProperty("openid")
    private String openId;
    @JsonProperty("country")
    private String country;
    @JsonProperty("headimgurl")
    private String headImgUrl;
    @JsonProperty("privilege")
    private String[] privileges;
    @JsonProperty("unionid")
    private String unionId;

    public WxUserInfo() {
    }

    public WxUserInfo(String unionId, String nickName, String headImgUrl) {
        this.nickName = nickName;
        this.headImgUrl = headImgUrl;
        this.unionId = unionId;
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

    public String[] getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String[] privileges) {
        this.privileges = privileges;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}
