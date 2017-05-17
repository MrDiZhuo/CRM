package com.zwd.crm.HomePage.inform.Module;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by asus-pc on 2017/4/25.
 */

public class subjectdepartmentElement implements Serializable {
    @SerializedName("departmentid")
    private int departmentid;
    @SerializedName("departmentname")
    private String departmentname;
    @SerializedName("subjectdepartment")
    private List<subjectdepartmentElement> subdepartment;

    public subjectdepartmentElement(int departmentid, String departmentname, List<subjectdepartmentElement> subdepartment) {
        this.departmentid = departmentid;
        this.departmentname = departmentname;
        this.subdepartment = subdepartment;
    }

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

    public List<subjectdepartmentElement> getSubdepartment() {
        return subdepartment;
    }

    public void setSubdepartment(List<subjectdepartmentElement> subdepartment) {
        this.subdepartment = subdepartment;
    }
}
