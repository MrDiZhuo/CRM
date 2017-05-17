package com.zwd.crm.HomePage.taskAdd.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/5/8.
 */

public class AddTask_Conference {
    @SerializedName("id")
    private int id;
    @SerializedName("exhibitionname")
    private String exhibitionname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExhibitionname() {
        return exhibitionname;
    }

    public void setExhibitionname(String exhibitionname) {
        this.exhibitionname = exhibitionname;
    }
}
