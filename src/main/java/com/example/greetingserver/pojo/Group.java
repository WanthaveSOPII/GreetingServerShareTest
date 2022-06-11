package com.example.greetingserver.pojo;

import java.util.Date;

public class Group {
    String id;
    String name;
    java.util.Date createTime;
    String stringCreateTime;
    String groupOwnerid;
    String active;
    String type;
    String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStringCreateTime() {
        return stringCreateTime;
    }

    public void setStringCreateTime(String stringCreateTime) {
        this.stringCreateTime = stringCreateTime;
    }

    public String getGroupOwnerid() {
        return groupOwnerid;
    }

    public void setGroupOwnerid(String groupOwnerid) {
        this.groupOwnerid = groupOwnerid;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
