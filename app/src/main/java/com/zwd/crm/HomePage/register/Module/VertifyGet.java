package com.zwd.crm.HomePage.register.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/5/9.
 */

public class VertifyGet {
    @SerializedName("data")
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
