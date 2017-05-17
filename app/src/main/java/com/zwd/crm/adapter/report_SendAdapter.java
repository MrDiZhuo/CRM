package com.zwd.crm.adapter;

import android.content.Context;

import com.zwd.crm.HomePage.reportSend.Controller.sendReceiveDlelgate;
import com.zwd.crm.HomePage.reportSend.Module.Report_SendGet;

import java.util.List;

/**
 * Created by asus-pc on 2017/3/12.
 */

public class report_SendAdapter extends MultiItemTypeAdapter<Report_SendGet> {
    public report_SendAdapter(Context context, List<Report_SendGet> datas) {
        super(context, datas);
        addItemViewDelegate(new sendReceiveDlelgate());
    }
}
