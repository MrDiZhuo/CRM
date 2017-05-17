package com.zwd.crm.HomePage.reportSend.Controller;

import com.bumptech.glide.Glide;
import com.zwd.crm.HomePage.reportSend.Module.Report_SendGet;
import com.zwd.crm.R;
import com.zwd.crm.base.BaseAppManager;
import com.zwd.crm.interfaze.ItemViewDelegate;
import com.zwd.crm.widget.ViewHolder;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by asus-pc on 2017/3/12.
 */

public class sendReceiveDlelgate implements ItemViewDelegate<Report_SendGet> {
    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.item_report_send;
    }

    @Override
    public boolean isForViewType(Report_SendGet item, int position)
    {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, Report_SendGet party, int position)
    {
        CircleImageView imageView = (CircleImageView) holder.getView(R.id.item_report_send_img);
        if(party.getResourceId()!=null){
            Glide.with(BaseAppManager.getInstance().getForwardActivity())
                    .load("http://opoecp2mn.bkt.clouddn.com/"+party.getResourceId())
                    .into(imageView);
        }

        if(party.getReportnamelists()!=null){
            String name="";
            for(int i=0;i<party.getReportnamelists().size();i++){
                name=name+" "+party.getReportnamelists().get(i).getName();
            }
            holder.setText(R.id.item_report_send_name,"汇报给"+name);
        }

        holder.setText(R.id.item_report_send_time,party.getReportdate().replace("T"," "));
        holder.setText(R.id.item_report_send_vary,party.getReporttype()+"报");
    }
}

