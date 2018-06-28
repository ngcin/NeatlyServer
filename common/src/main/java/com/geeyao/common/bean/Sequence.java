package com.geeyao.common.bean;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Sequence {
    @Id
    private String name;
    private long next;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNext() {
        return next;
    }

    public void setNext(long next) {
        this.next = next;
    }
}
