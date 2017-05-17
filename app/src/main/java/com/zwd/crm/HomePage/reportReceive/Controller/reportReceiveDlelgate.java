package com.zwd.crm.HomePage.reportReceive.Controller;

import com.bumptech.glide.Glide;
import com.zwd.crm.HomePage.reportReceive.Module.Report_receiveGet;
import com.zwd.crm.R;
import com.zwd.crm.base.BaseAppManager;
import com.zwd.crm.interfaze.ItemViewDelegate;
import com.zwd.crm.widget.ViewHolder;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by asus-pc on 2017/3/12.
 */

public class reportReceiveDlelgate implements ItemViewDelegate<Report_receiveGet> {
    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.item_report_receive;
    }

    @Override
    public boolean isForViewType(Report_receiveGet item, int position)
    {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, Report_receiveGet party, int position)
    {
        CircleImageView imageView = (CircleImageView) holder.getView(R.id.item_report_receive_img);
        if(party.getResourceId()!=null){
            Glide.with(BaseAppManager.getInstance().getForwardActivity())
                    .load("http://opoecp2mn.bkt.clouddn.com/"+party.getResourceId())
                    .into(imageView);
        }
        holder.setText(R.id.item_report_receive_name,party.getName());
        holder.setText(R.id.item_report_receive_time,party.getTime().replace("T"," "));
        holder.setText(R.id.item_report_receive_vary,party.getState()+"报");
        holder.setText(R.id.item_report_receive_text,party.getTxt());
    }
}
