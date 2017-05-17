package com.zwd.crm.HomePage.chengyuan.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/5/5.
 */

public class DepartmentSimple {
    @SerializedName("departmentname")
    private String departmentname;
    @SerializedName("usersrole")
    private String usersrole;
    @SerializedName("roletype")
    private String roletype;
    @SerializedName("sonlist")
    private DepartmentSimple sonlist;

    public String getRoletype() {
        return roletype;
    }

    public void setRoletype(String roletype) {
        this.roletype = roletype;
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }

    public String getUsersrole() {
        return usersrole;
    }

    public void setUsersrole(String usersrole) {
        this.usersrole = usersrole;
    }

    public DepartmentSimple getSonlist() {
        return sonlist;
    }

    public void setSonlist(DepartmentSimple sonlist) {
        this.sonlist = sonlist;
    }
}
