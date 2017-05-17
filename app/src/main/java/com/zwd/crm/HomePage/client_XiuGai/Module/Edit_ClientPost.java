package com.zwd.crm.HomePage.client_XiuGai.Module;

import java.io.Serializable;

/**
 * Created by asus-pc on 2017/5/7.
 */

public class Edit_ClientPost implements Serializable{
    private int id;
    private String customname;
    private String contractname;
    private String custompostion;
    private String customphone1;
    private String customemail;
    private String customaddress;

    private int groupid;

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomname() {
        return customname;
    }

    public void setCustomname(String customname) {
        this.customname = customname;
    }

    public String getCustompostion() {
        return custompostion;
    }

    public void setCustompostion(String custompostion) {
        this.custompostion = custompostion;
    }

    public String getContractname() {
        return contractname;
    }

    public void setContractname(String contractname) {
        this.contractname = contractname;
    }

    public String getCustomphone1() {
        return customphone1;
    }

    public void setCustomphone1(String customphone1) {
        this.customphone1 = customphone1;
    }

    public String getCustomemail() {
        return customemail;
    }

    public void setCustomemail(String customemail) {
        this.customemail = customemail;
    }

    public String getCustomaddress() {
        return customaddress;
    }

    public void setCustomaddress(String customaddress) {
        this.customaddress = customaddress;
    }
}
