package com.zwd.crm.HomePage.addAffair.Module;

/**
 * Created by asus-pc on 2017/4/23.
 */

public class PersonElement {
    private int userId;
    private String noticeusersdate;//null
    private int noticeusersstatus;//null

    public PersonElement(int userId, String noticeusersdate, int noticeusersstatus) {
        this.userId = userId;
        this.noticeusersdate = noticeusersdate;
        this.noticeusersstatus = noticeusersstatus;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNoticeusersdate() {
        return noticeusersdate;
    }

    public void setNoticeusersdate(String noticeusersdate) {
        this.noticeusersdate = noticeusersdate;
    }

    public int getNoticeusersstatus() {
        return noticeusersstatus;
    }

    public void setNoticeusersstatus(int noticeusersstatus) {
        this.noticeusersstatus = noticeusersstatus;
    }
}
