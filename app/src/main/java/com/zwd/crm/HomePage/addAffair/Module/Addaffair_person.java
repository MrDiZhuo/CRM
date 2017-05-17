package com.zwd.crm.HomePage.addAffair.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/5/7.
 */

public class Addaffair_person {
    private int img;
    private String title;

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Addaffair_person(String title, String name, int img) {
        this.title = title;
        this.name = name;
        this.img = img;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public Addaffair_person(String title, String name) {
        this.title = title;
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
