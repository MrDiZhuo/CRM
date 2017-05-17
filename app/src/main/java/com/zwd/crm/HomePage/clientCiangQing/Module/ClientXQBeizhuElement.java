package com.zwd.crm.HomePage.clientCiangQing.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/5/6.
 */

public class ClientXQBeizhuElement {
    @SerializedName("headimg")
    private String heading;
    @SerializedName("operationername")
    private String name;
    @SerializedName("updatedate")
    private String date;
    @SerializedName("backupcontent")
    private String txt;

    public ClientXQBeizhuElement(String heading, String name, String date, String txt) {
        this.heading = heading;
        this.name = name;
        this.date = date;
        this.txt = txt;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
