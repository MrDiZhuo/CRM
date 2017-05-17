package com.zwd.crm.adapter;

import android.content.Context;

import com.zwd.crm.HomePage.choosePerson.Controller.ChoosePersonItemDelegate;
import com.zwd.crm.HomePage.choosePerson.Module.ChoosePersonGet;

import java.util.List;

/**
 * Created by asus-pc on 2017/3/11.
 */

public class ChoosePersonAdapter extends MultiItemTypeAdapter<ChoosePersonGet> {
    public ChoosePersonAdapter(Context context, List<ChoosePersonGet> datas) {
        super(context, datas);
        addItemViewDelegate(new ChoosePersonItemDelegate());
    }
}
