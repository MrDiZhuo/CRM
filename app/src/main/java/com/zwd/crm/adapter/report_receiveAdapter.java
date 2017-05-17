package com.zwd.crm.adapter;

import android.content.Context;

import com.zwd.crm.HomePage.reportReceive.Controller.reportReceiveDlelgate;
import com.zwd.crm.HomePage.reportReceive.Module.Report_receiveGet;

import java.util.List;

/**
 * Created by asus-pc on 2017/3/12.
 */

public class report_receiveAdapter extends MultiItemTypeAdapter<Report_receiveGet> {
    public report_receiveAdapter(Context context, List<Report_receiveGet> datas) {
        super(context, datas);
        addItemViewDelegate(new reportReceiveDlelgate());
    }
}
