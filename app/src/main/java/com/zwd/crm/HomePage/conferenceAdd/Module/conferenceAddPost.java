package com.zwd.crm.HomePage.conferenceAdd.Module;

import java.io.Serializable;

/**
 * Created by asus-pc on 2017/4/16.
 */

public class conferenceAddPost implements Serializable{
    private String name;
    private String startdate;
    private String enddate;
    private String address;
    private String description;
    private int state;
    private int usersgroupid;

    public int getUsersgroupid() {
        return usersgroupid;
    }

    public void setUsersgroupid(int usersgroupid) {
        this.usersgroupid = usersgroupid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
