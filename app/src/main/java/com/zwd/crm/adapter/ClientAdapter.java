package com.zwd.crm.adapter;

import android.content.Context;
import android.view.View;

import com.zwd.crm.HomePage.client.Controller.ClientItemDelegate;
import com.zwd.crm.HomePage.client.Module.ClientElement;
import com.zwd.crm.HomePage.client.Module.clientGet;

import java.util.List;

/**
 * Created by asus-pc on 2017/3/16.
 */

public class ClientAdapter extends MultiItemTypeAdapter<clientGet> {
    private ClientItemDelegate.ButtonInterface buttonInterface;

    public ClientAdapter(Context context, List<clientGet> datas) {
        super(context, datas);
    }

    public void buttonSetOnclick(ClientItemDelegate.ButtonInterface buttonInterface){
        this.buttonInterface = buttonInterface;
        ClientItemDelegate clientItemDelegate = new ClientItemDelegate();
        addItemViewDelegate(clientItemDelegate.setButtonInterface(buttonInterface));
    }
}
