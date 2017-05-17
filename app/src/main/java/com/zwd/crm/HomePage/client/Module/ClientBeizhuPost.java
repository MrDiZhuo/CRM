package com.zwd.crm.HomePage.client.Module;

/**
 * Created by asus-pc on 2017/5/6.
 */

public class ClientBeizhuPost {
    private int customid;
    private int operationerid;
    private String backupcontent;
    private int sharestatus;//0代表初始状态，1代表分享
    private String datetime;

    public int getCustomid() {
        return customid;
    }

    public void setCustomid(int customid) {
        this.customid = customid;
    }

    public int getOperationerid() {
        return operationerid;
    }

    public void setOperationerid(int operationerid) {
        this.operationerid = operationerid;
    }

    public String getBackupcontent() {
        return backupcontent;
    }

    public void setBackupcontent(String backupcontent) {
        this.backupcontent = backupcontent;
    }

    public int getSharestatus() {
        return sharestatus;
    }

    public void setSharestatus(int sharestatus) {
        this.sharestatus = sharestatus;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
