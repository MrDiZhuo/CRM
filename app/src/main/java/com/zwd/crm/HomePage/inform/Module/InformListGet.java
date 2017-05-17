package com.zwd.crm.HomePage.inform.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/5/6.
 */

public class InformListGet {
    @SerializedName("headimg")
    private String heading;
    @SerializedName("name")
    private String name;
    @SerializedName("updatetime")
    private String datetime;
    @SerializedName("usersname")
    private String phone;
    @SerializedName("id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
