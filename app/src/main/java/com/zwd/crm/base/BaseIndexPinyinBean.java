package com.zwd.crm.base;

import com.zwd.crm.interfaze.IIndexTargetInterface;

/**
 * Created by asus-pc on 2017/3/8.
 */

public abstract class BaseIndexPinyinBean extends BaseIndexTagBean implements IIndexTargetInterface {
    private String pyCity;//城市的拼音



    public String getPyCity() {

        return pyCity;

    }



    public void setPyCity(String pyCity) {

        this.pyCity = pyCity;

    }
}
