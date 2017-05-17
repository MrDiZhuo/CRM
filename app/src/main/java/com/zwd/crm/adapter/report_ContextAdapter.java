package com.zwd.crm.adapter;

import android.content.Context;

import com.zwd.crm.HomePage.addAffair.Module.Addaffair_person;
import com.zwd.crm.HomePage.reportContext.Controller.reportContextDelegate;
import com.zwd.crm.HomePage.reportContext.Module.Report_CotentGet;
import com.zwd.crm.HomePage.reportContext.Module.Report_target;

import java.util.List;

/**
 * Created by asus-pc on 2017/3/12.
 */

public class report_ContextAdapter extends MultiItemTypeAdapter<Addaffair_person> {
    public report_ContextAdapter(Context context, List<Addaffair_person> datas) {
        super(context, datas);
        addItemViewDelegate(new reportContextDelegate());
    }
}
