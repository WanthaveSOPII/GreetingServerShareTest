package com.example.greetingserver.pojo;

import java.util.Date;

public class Message {
    int id;
    String info;
    Date time;
    String sender;
    String recver;
    String zoneID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecver() {
        return recver;
    }

    public void setRecver(String recver) {
        this.recver = recver;
    }

    public String getZoneID() {
        return zoneID;
    }

    public void setZoneID(String zoneID) {this.zoneID = zoneID; }
}
