package com.zwd.crm.HomePage.affairXiangQing.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/4/29.
 */

public class noticeanswerlistElement {
    @SerializedName("usersname")
    private String usersname;
    @SerializedName("headimg")
    private String headimg;
    @SerializedName("id")
    private int id;
    @SerializedName("answercontent")
    private String answercontent;
    @SerializedName("answerdate")
    private String answerdate;
    @SerializedName("answerusersid")
    private int answerusersid;
    @SerializedName("parrentid")
    private int parrentid;
    @SerializedName("answerstatus")
    private int answerstatus;
    @SerializedName("noticeid")
    private int noticeid;


    public int getParrentid() {
        return parrentid;
    }

    public void setParrentid(int parrentid) {
        this.parrentid = parrentid;
    }

    public int getAnswerstatus() {
        return answerstatus;
    }

    public void setAnswerstatus(int answerstatus) {
        this.answerstatus = answerstatus;
    }

    public int getNoticeid() {
        return noticeid;
    }

    public void setNoticeid(int noticeid) {
        this.noticeid = noticeid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getUsersname() {
        return usersname;
    }

    public void setUsersname(String usersname) {
        this.usersname = usersname;
    }

    public int getAnswerusersid() {
        return answerusersid;
    }

    public void setAnswerusersid(int answerusersid) {
        this.answerusersid = answerusersid;
    }

    public String getAnswerdate() {
        return answerdate;
    }

    public void setAnswerdate(String answerdate) {
        this.answerdate = answerdate;
    }

    public String getAnswercontent() {
        return answercontent;
    }

    public void setAnswercontent(String answercontent) {
        this.answercontent = answercontent;
    }
}
