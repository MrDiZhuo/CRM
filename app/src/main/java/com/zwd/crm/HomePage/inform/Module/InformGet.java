package com.zwd.crm.HomePage.inform.Module;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus-pc on 2017/4/25.
 */

public class InformGet {
    @SerializedName("subjectdepartment")
    private ArrayList<subjectdepartmentElement> subjectdepartment;

    public ArrayList<subjectdepartmentElement> getSubjectdepartment() {
        return subjectdepartment;
    }

    public void setSubjectdepartment(ArrayList<subjectdepartmentElement> subjectdepartment) {
        this.subjectdepartment = subjectdepartment;
    }
}
