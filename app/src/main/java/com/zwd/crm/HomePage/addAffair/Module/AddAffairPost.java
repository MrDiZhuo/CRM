package com.zwd.crm.HomePage.addAffair.Module;

import java.util.List;

/**
 * Created by asus-pc on 2017/4/23.
 */

public class AddAffairPost {
    private String title;//null
    private String noticeContent;
    private String noticeDate;
    private int createId;
    private String noticeEnddate;//null
    private int noticestatus;//null
    private String noticeType;
    private List<PersonElement> personElementList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(String noticeDate) {
        this.noticeDate = noticeDate;
    }

    public int getCreateId() {
        return createId;
    }

    public void setCreateId(int createId) {
        this.createId = createId;
    }

    public String getNoticeEnddate() {
        return noticeEnddate;
    }

    public void setNoticeEnddate(String noticeEnddate) {
        this.noticeEnddate = noticeEnddate;
    }

    public int getNoticestatus() {
        return noticestatus;
    }

    public void setNoticestatus(int noticestatus) {
        this.noticestatus = noticestatus;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public List<PersonElement> getPersonElementList() {
        return personElementList;
    }

    public void setPersonElementList(List<PersonElement> personElementList) {
        this.personElementList = personElementList;
    }
}
