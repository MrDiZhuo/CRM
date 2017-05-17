package com.zwd.crm.HomePage.choosePerson.Module;

import com.zwd.crm.base.BaseIndexPinyinBean;

/**
 * Created by asus-pc on 2017/3/11.
 */

public class ChoosePersonElement extends BaseIndexPinyinBean {
    private String city;//城市名字



    public ChoosePersonElement() {

    }

    public ChoosePersonElement(String city) {

        this.city = city;

    }



    public String getCity() {

        return city;

    }



    public void setCity(String city) {

        this.city = city;

    }



    @Override

    public String getTarget() {

        return city;

    }
}
