package com.example.greetingserver.pojo;

import java.util.Date;

public class Message {
    public static final String MSGTYPE_CHAT = "chat";
    public static final String MSGTYPE_HELLO = "hello";
    public static final String MSGTYPE_BYE = "bye";
    public static final String MSGTYPE_LISTUSER = "listUser";


    int id;
    String info;
    java.util.Date time;
    String stringTime;


    String sender;
    String recver;
    String zoneID;

    //目前包含
    //“chat”
    //“hello”

    String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStringTime() {
        return stringTime;
    }

    public void setStringTime(String stringTime) {
        this.stringTime = stringTime;
    }
}
