package com.zwd.crm.adapter;

import android.content.Context;

import com.zwd.crm.HomePage.affair.Controller.affairItemDelegate;
import com.zwd.crm.HomePage.affair.Module.affairGet;

import java.util.List;

/**
 * Created by asus-pc on 2017/3/7.
 */

public class affairAdapter extends MultiItemTypeAdapter<affairGet> {
    public affairAdapter(Context context, List<affairGet> datas) {
        super(context, datas);
        addItemViewDelegate(new affairItemDelegate());
    }
}
