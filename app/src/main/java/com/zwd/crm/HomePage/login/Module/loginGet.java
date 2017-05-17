package com.zwd.crm.HomePage.login.Module;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by asus-pc on 2017/4/9.
 */

public class loginGet implements Serializable{
    @SerializedName("name")
    private String nickname;//用户姓名
    @SerializedName("wechat")
    private String wechat;//微信号
    @SerializedName("alipay")
    private String alipay;//支付宝账号
    @SerializedName("email")
    private String email;//邮箱
    @SerializedName("usertag")
    private String usertag;//个人标签
    @SerializedName("password")
    private String password;//原密码
    @SerializedName("headimg")
    private String heading;//头像
    @SerializedName("id")
    private int id;//id
    @SerializedName("phone")
    private String phone;
    @SerializedName("usersorganization")
    private UserOrganization userOrganization;

    public UserOrganization getUserOrganization() {
        return userOrganization;
    }

    public void setUserOrganization(UserOrganization userOrganization) {
        this.userOrganization = userOrganization;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsertag() {
        return usertag;
    }

    public void setUsertag(String usertag) {
        this.usertag = usertag;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
