package com.zwd.crm.HomePage.affairXiangQing.Module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by asus-pc on 2017/4/29.
 */

public class affairXiangQingGet {
    @SerializedName("confirmuserslist")
    List<confirmuserslistElement> confirmuserslist;
    @SerializedName("noconfirmuserslist")
    List<confirmuserslistElement> noconfirmuserslist;
    @SerializedName("noticeanswerlist")
    List<noticeanswerlistElement> noticeanswerlist;

    public List<confirmuserslistElement> getNoconfirmuserslist() {
        return noconfirmuserslist;
    }

    public void setNoconfirmuserslist(List<confirmuserslistElement> noconfirmuserslist) {
        this.noconfirmuserslist = noconfirmuserslist;
    }

    public List<confirmuserslistElement> getConfirmuserslist() {
        return confirmuserslist;
    }

    public void setConfirmuserslist(List<confirmuserslistElement> confirmuserslist) {
        this.confirmuserslist = confirmuserslist;
    }

    public List<noticeanswerlistElement> getNoticeanswerlist() {
        return noticeanswerlist;
    }

    public void setNoticeanswerlist(List<noticeanswerlistElement> noticeanswerlist) {
        this.noticeanswerlist = noticeanswerlist;
    }
}
