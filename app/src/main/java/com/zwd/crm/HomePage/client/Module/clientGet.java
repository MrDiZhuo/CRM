package com.zwd.crm.HomePage.client.Module;

import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.Streams;

import java.io.Serializable;

/**
 * Created by asus-pc on 2017/4/27.
 */

public class clientGet implements Serializable{
    @SerializedName("usersgroupid")
    private int usersgroupid;
    @SerializedName("id")
    private int id;//id
    @SerializedName("contractname")
    private String contractname;//公司名字
    @SerializedName("customname")
    private String customname;//联系人
    @SerializedName("custompostion")
    private String custompostion;//职位
    @SerializedName("usersname")
    private String usersname;///创建者
    @SerializedName("tasklanchername")
    private String tasklanchername;//客户任务执行者
    @SerializedName("customcontractstatus")
    private int customcontractstatus;//客户任务状态
    @SerializedName("customphone1")
    private String customphone1;//联系电话
    @SerializedName("customemail")
    private String customemail;//客户邮箱
    @SerializedName("customaddress")
    private String customaddress;///客户地址
    @SerializedName("customstatus")
    private String customstatus;//客户状态

    public int getUsersgroupid() {
        return usersgroupid;
    }

    public void setUsersgroupid(int usersgroupid) {
        this.usersgroupid = usersgroupid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContractname() {
        return contractname;
    }

    public void setContractname(String contractname) {
        this.contractname = contractname;
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

    public String getUsersname() {
        return usersname;
    }

    public void setUsersname(String usersname) {
        this.usersname = usersname;
    }

    public String getTasklanchername() {
        return tasklanchername;
    }

    public void setTasklanchername(String tasklanchername) {
        this.tasklanchername = tasklanchername;
    }

    public int getCustomcontractstatus() {
        return customcontractstatus;
    }

    public void setCustomcontractstatus(int customcontractstatus) {
        this.customcontractstatus = customcontractstatus;
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

    public String getCustomstatus() {
        return customstatus;
    }

    public void setCustomstatus(String customstatus) {
        this.customstatus = customstatus;
    }
}
