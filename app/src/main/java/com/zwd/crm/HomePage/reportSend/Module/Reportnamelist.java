package com.zwd.crm.HomePage.reportSend.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/5/7.
 */

public class Reportnamelist {
    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
