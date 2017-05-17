package com.zwd.crm.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by asus-pc on 2017/3/17.
 */

/**
 * 禁止recyclerview滑动
 */
public class CustomLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled =true;

    public CustomLinearLayoutManager(Context context) {
        super(context);
    }
    public void setScrollEnabled(boolean flag){
        this.isScrollEnabled =flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled&&super.canScrollVertically();
    }
}
