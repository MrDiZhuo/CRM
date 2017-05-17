package com.zwd.crm.HomePage.chengyuan.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/5/5.
 */

public class ChengYuInfoGet {
    @SerializedName("headimg")
    private String headimg;
    @SerializedName("name")
    private String name;
    @SerializedName("phone")
    private String phone;
    @SerializedName("email")
    private String email;

    @SerializedName("DepartmentSimple")
    private DepartmentSimple departmentSimple;

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public DepartmentSimple getDepartmentSimple() {
        return departmentSimple;
    }

    public void setDepartmentSimple(DepartmentSimple departmentSimple) {
        this.departmentSimple = departmentSimple;
    }
}
