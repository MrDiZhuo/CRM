package com.zwd.crm.HomePage.adjust.Module;

/**
 * Created by asus-pc on 2017/5/4.
 */

public class AdjustBumenPost {
    private int userid;
    private int departmentid;
    private int departmentoldid;
    private String role;
    private String roletype;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(int departmentid) {
        this.departmentid = departmentid;
    }

    public int getDepartmentoldid() {
        return departmentoldid;
    }

    public void setDepartmentoldid(int departmentoldid) {
        this.departmentoldid = departmentoldid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoletype() {
        return roletype;
    }

    public void setRoletype(String roletype) {
        this.roletype = roletype;
    }
}
