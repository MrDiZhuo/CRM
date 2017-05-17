package com.zwd.crm.HomePage.choosePerson.Module;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.zwd.crm.base.BaseIndexPinyinBean;

import java.io.Serializable;

/**
 * Created by asus-pc on 2017/4/23.
 */

public class ChoosePersonGet extends BaseIndexPinyinBean implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;

    public ChoosePersonGet(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override

    public String getTarget() {

        return name;

    }
}
