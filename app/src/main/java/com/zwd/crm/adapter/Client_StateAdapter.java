package com.zwd.crm.adapter;

import android.content.Context;

import com.zwd.crm.HomePage.clientState.Controller.clientStateItemDelegate;
import com.zwd.crm.HomePage.clientState.Module.Client_StateGet;

import java.util.List;

/**
 * Created by asus-pc on 2017/3/22.
 */

public class Client_StateAdapter extends MultiItemTypeAdapter<Client_StateGet> {
    public Client_StateAdapter(Context context, List<Client_StateGet> datas) {
        super(context, datas);
        addItemViewDelegate(new clientStateItemDelegate());
    }
}
