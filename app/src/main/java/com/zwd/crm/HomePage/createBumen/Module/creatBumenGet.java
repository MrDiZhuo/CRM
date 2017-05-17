package com.zwd.crm.HomePage.createBumen.Module;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by asus-pc on 2017/4/27.
 */

public class creatBumenGet implements Serializable{
    @SerializedName("id")
    private int id;
    @SerializedName("departmentname")
    private String departmentname;
    @SerializedName("usersgroupid")
    private int usersgroupid;
    @SerializedName("parentid")
    private int parentid;
    @SerializedName("departmentintro")
    private String departmentintro;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }

    public int getUsersgroupid() {
        return usersgroupid;
    }

    public void setUsersgroupid(int usersgroupid) {
        this.usersgroupid = usersgroupid;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public String getDepartmentintro() {
        return departmentintro;
    }

    public void setDepartmentintro(String departmentintro) {
        this.departmentintro = departmentintro;
    }
}
