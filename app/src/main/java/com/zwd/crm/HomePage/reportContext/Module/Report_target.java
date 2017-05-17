package com.zwd.crm.HomePage.reportContext.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/5/7.
 */

public class Report_target {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
