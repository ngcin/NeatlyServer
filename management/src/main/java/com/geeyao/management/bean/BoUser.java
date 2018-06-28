package com.geeyao.management.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BoUser {
    @Id
    private String userName;
    @Indexed(unique = true)
    private String csid;

    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCsid() {
        return csid;
    }

    public void setCsid(String csid) {
        this.csid = csid;
    }
}
