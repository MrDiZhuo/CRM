package com.zwd.crm.adapter;

import android.content.Context;

import com.zwd.crm.HomePage.inform.Controller.informItemDelegate;
import com.zwd.crm.HomePage.inform.Module.InformListGet;

import java.util.List;

/**
 * Created by asus-pc on 2017/3/8.
 */

public class informAdapter extends MultiItemTypeAdapter<InformListGet> {
    private informItemDelegate.ButtonInterface buttonInterface;
    public informAdapter(Context context, List<InformListGet> datas) {
        super(context, datas);
    }

    public void buttonSetOnclick(informItemDelegate.ButtonInterface buttonInterface){
        this.buttonInterface = buttonInterface;
        informItemDelegate clientItemDelegate = new informItemDelegate();
        addItemViewDelegate(clientItemDelegate.setButtonInterface(buttonInterface));
    }
}
