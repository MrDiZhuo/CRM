package com.zwd.crm.HomePage.reportContextRengwu.Module;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/3/12.
 */

public class Report_context_RengwuGet {
    @SerializedName("tasktitle")
    String title;
    @SerializedName("taskpublishtime")
    String time;
    @SerializedName("taskcontent")
    String txt;
    @SerializedName("leadername")
    String name;
    @SerializedName("tasksettime")
    String end;
    @SerializedName("answercount")
    int reply;
    @SerializedName("sontaskcount")
    int num;
    @SerializedName("id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getReply() {
        return reply;
    }

    public void setReply(int reply) {
        this.reply = reply;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Report_context_RengwuGet(String title, String time, String txt, String name, String end, int reply, int num) {
        this.title = title;
        this.time = time;
        this.txt = txt;
        this.name = name;
        this.end = end;
        this.reply = reply;
        this.num = num;
    }
}
