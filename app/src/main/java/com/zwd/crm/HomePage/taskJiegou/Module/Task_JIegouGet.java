package com.zwd.crm.HomePage.taskJiegou.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/3/15.
 */

public class Task_JIegouGet {
    @SerializedName("id")
    private int id;
    @SerializedName("tasktitle")
    private String tasktitle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTasktitle() {
        return tasktitle;
    }

    public void setTasktitle(String tasktitle) {
        this.tasktitle = tasktitle;
    }

}
