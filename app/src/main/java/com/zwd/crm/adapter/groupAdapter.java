package com.zwd.crm.adapter;

import android.content.Context;

import com.zwd.crm.HomePage.group.Controller.groupItemDelegate;
import com.zwd.crm.HomePage.inform.Module.subjectdepartmentElement;

import java.util.List;

/**
 * Created by asus-pc on 2017/3/8.
 */

public class groupAdapter extends MultiItemTypeAdapter<subjectdepartmentElement> {
    public groupAdapter(Context context, List<subjectdepartmentElement> datas) {
        super(context, datas);
        addItemViewDelegate(new groupItemDelegate());
    }
}
