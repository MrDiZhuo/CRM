package com.zwd.crm.HomePage.affair.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/4/22.
 */

public class affairGet {
    @SerializedName("headimg")
    private String heading;
    @SerializedName("usersname")
    private String name;
    @SerializedName("noticedate")
    private String date;
    @SerializedName("content")
    private String content;
    @SerializedName("confirmcount")
    private int confirmcount;
    @SerializedName("referencecount")
    private int referencecount;
    @SerializedName("noticetype")
    private String noticetype;
    @SerializedName("noticeenddate")
    private String noticeenddate;
    @SerializedName("answercount")
    private int answercount;
    @SerializedName("answerstatus")
    private int answerstatus;
    @SerializedName("id")///每一项id
    private int id;
    @SerializedName("creatid")//创建人id
    private int creatid;

    public int getCreatid() {
        return creatid;
    }

    public void setCreatid(int creatid) {
        this.creatid = creatid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnswerstatus() {
        return answerstatus;
    }

    public void setAnswerstatus(int answerstatus) {
        this.answerstatus = answerstatus;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getConfirmcount() {
        return confirmcount;
    }

    public void setConfirmcount(int confirmcount) {
        this.confirmcount = confirmcount;
    }

    public int getReferencecount() {
        return referencecount;
    }

    public void setReferencecount(int referencecount) {
        this.referencecount = referencecount;
    }

    public String getNoticetype() {
        return noticetype;
    }

    public void setNoticetype(String noticetype) {
        this.noticetype = noticetype;
    }

    public String getNoticeenddate() {
        return noticeenddate;
    }

    public void setNoticeenddate(String noticeenddate) {
        this.noticeenddate = noticeenddate;
    }

    public int getAnswercount() {
        return answercount;
    }

    public void setAnswercount(int answercount) {
        this.answercount = answercount;
    }
}
