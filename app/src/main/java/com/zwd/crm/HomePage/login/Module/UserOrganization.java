package com.zwd.crm.HomePage.login.Module;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by asus-pc on 2017/4/20.
 */

public class UserOrganization implements Serializable{
    @SerializedName("usersgroupid")
    private int usersgroupid;
    @SerializedName("usersgroupname")
    private String usersgroupname;
    @SerializedName("subjectdepartment")
    private List<SubjectDepartment> subjectdepartment;

    public int getUsersgroupid() {
        return usersgroupid;
    }

    public void setUsersgroupid(int usersgroupid) {
        this.usersgroupid = usersgroupid;
    }

    public String getUsersgroupname() {
        return usersgroupname;
    }

    public void setUsersgroupname(String usersgroupname) {
        this.usersgroupname = usersgroupname;
    }

    public List<SubjectDepartment> getSubjectdepartment() {
        return subjectdepartment;
    }

    public void setSubjectdepartment(List<SubjectDepartment> subjectdepartment) {
        this.subjectdepartment = subjectdepartment;
    }
}
