package com.zwd.crm.HomePage.affairXiangQing.Module;

/**
 * Created by asus-pc on 2017/3/18.
 */

public class affair_XiangQingElement {
    private String ResourceId;
    private String name;
    private String date;
    private String txt;

    public affair_XiangQingElement(String resourceId, String name, String date, String txt) {
        ResourceId = resourceId;
        this.name = name;
        this.date = date;
        this.txt = txt;
    }

    public String getResourceId() {
        return ResourceId;
    }

    public void setResourceId(String resourceId) {
        ResourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
