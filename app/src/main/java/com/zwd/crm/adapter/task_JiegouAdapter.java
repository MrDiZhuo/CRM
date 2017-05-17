package com.zwd.crm.adapter;

import android.content.Context;

import com.zwd.crm.HomePage.taskJiegou.Controller.taskjiegouItemDelegate;
import com.zwd.crm.HomePage.taskJiegou.Module.Task_JIegouGet;

import java.util.List;

/**
 * Created by asus-pc on 2017/3/15.
 */

public class task_JiegouAdapter extends MultiItemTypeAdapter<Task_JIegouGet> {
    public task_JiegouAdapter(Context context, List<Task_JIegouGet> datas) {
        super(context, datas);
        addItemViewDelegate(new taskjiegouItemDelegate());
    }
}
