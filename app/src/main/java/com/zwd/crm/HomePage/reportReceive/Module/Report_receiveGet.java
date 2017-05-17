package com.zwd.crm.HomePage.reportReceive.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/3/12.
 */

public class Report_receiveGet {
    @SerializedName("id")
    private int id;
    @SerializedName("heading")
    private String resourceId;
    @SerializedName("name")
    private String name;
    @SerializedName("reportdate")
    private String time;
    @SerializedName("reporttype")
    private String state;
    @SerializedName("reportcontent")
    private String txt;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
