package com.zwd.crm.HomePage.login.Module;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by asus-pc on 2017/4/20.
 */

public class SubjectDepartment implements Serializable {
    @SerializedName("departmentid")
    private int departmentid;
    @SerializedName("departmentname")
    private String departmentname;
    @SerializedName("usersdepartmentrole")
    private String usersdepartmentrole;
    @SerializedName("subjectdepartment")
    private List<SubjectDepartment> subjectdepartment;

    public int getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(int departmentid) {
        this.departmentid = departmentid;
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }

    public String getUsersdepartmentrole() {
        return usersdepartmentrole;
    }

    public void setUsersdepartmentrole(String usersdepartmentrole) {
        this.usersdepartmentrole = usersdepartmentrole;
    }

    public List<SubjectDepartment> getSubjectdepartment() {
        return subjectdepartment;
    }

    public void setSubjectdepartment(List<SubjectDepartment> subjectdepartment) {
        this.subjectdepartment = subjectdepartment;
    }
}
