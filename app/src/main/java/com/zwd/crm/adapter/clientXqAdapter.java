package com.zwd.crm.adapter;

import android.content.Context;

import com.zwd.crm.HomePage.clientCiangQing.Controller.clientItemDelegate;
import com.zwd.crm.HomePage.clientCiangQing.Module.ClientXQBeizhuElement;

import java.util.List;

/**
 * Created by asus-pc on 2017/5/6.
 */

public class clientXqAdapter extends MultiItemTypeAdapter<ClientXQBeizhuElement> {
    public clientXqAdapter(Context context, List<ClientXQBeizhuElement> datas) {
        super(context, datas);
        addItemViewDelegate(new clientItemDelegate());
    }
}
