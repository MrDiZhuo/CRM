package com.zwd.crm.HomePage.clientState.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/3/22.
 */

public class Client_StateGet {
    @SerializedName("name")
    private String name;
    @SerializedName("customcontractstatus")
    private int state;
    private String statename;

    public String getStatename() {
        return statename;
    }

    public void setStatename(String statename) {
        this.statename = statename;
    }

    public Client_StateGet(String name, String statename) {
        this.name = name;
        this.statename = statename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
