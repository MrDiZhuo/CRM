package com.zwd.crm.adapter;

import android.content.Context;

import com.zwd.crm.HomePage.taskIntroduce.Controller.taskIntroduceItemDelegate;
import com.zwd.crm.HomePage.taskIntroduce.Module.taskIntroduceElement;

import java.util.List;

/**
 * Created by asus-pc on 2017/3/10.
 */

public class taskIntroduceAdapter extends MultiItemTypeAdapter<taskIntroduceElement> {
    public taskIntroduceAdapter(Context context, List<taskIntroduceElement> datas) {
        super(context, datas);
        addItemViewDelegate(new taskIntroduceItemDelegate());
    }
}
