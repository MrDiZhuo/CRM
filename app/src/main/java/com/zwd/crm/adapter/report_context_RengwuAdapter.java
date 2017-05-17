package com.zwd.crm.adapter;

import android.content.Context;

import com.zwd.crm.HomePage.reportContextRengwu.Controller.report_context_RengwuDelegate;
import com.zwd.crm.HomePage.reportContextRengwu.Module.Report_context_RengwuGet;

import java.util.List;

/**
 * Created by asus-pc on 2017/3/12.
 */

public class report_context_RengwuAdapter extends MultiItemTypeAdapter<Report_context_RengwuGet> {
    public report_context_RengwuAdapter(Context context, List<Report_context_RengwuGet> datas) {
        super(context, datas);
        addItemViewDelegate(new report_context_RengwuDelegate());
    }
}
