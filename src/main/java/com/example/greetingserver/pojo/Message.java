package com.example.greetingserver.pojo;

import org.apache.tomcat.util.codec.binary.Base64;

import java.util.Date;

public class Message {
    public static final String MSGTYPE_CHAT = "chat";
    public static final String MSGTYPE_HELLO = "hello";
    public static final String MSGTYPE_BYE = "bye";
    public static final String MSGTYPE_LISTUSER = "listUser";
    public static final String MSGTYPE_GETGROUPMEMBER = "getGroupMember";
    public static final String MSGTYPE_LEFTGROUP = "leftGroup";
    public static final String MSGTYPE_PICTURE = "picture";


    int id;
    String info;

    byte[] picture;
    String base64pic;
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

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
        processPicture();
    }

    public String getBase64pic() {
        return base64pic;
    }

    public void setBase64pic(String base64pic) {
        this.base64pic = base64pic;
    }

    public boolean processPicture(){
        if(this.picture == null)
            return false;
        byte[] encodeBase64 = Base64.encodeBase64(this.picture);
        try {
            this.base64pic = new String(encodeBase64, "UTF-8");
        } catch(Exception e){

        }
        return true;
    }
}
