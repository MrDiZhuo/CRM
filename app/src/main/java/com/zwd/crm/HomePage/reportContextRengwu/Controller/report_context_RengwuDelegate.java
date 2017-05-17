package com.zwd.crm.HomePage.reportContextRengwu.Controller;

import com.zwd.crm.HomePage.reportContextRengwu.Module.Report_context_RengwuGet;
import com.zwd.crm.R;
import com.zwd.crm.interfaze.ItemViewDelegate;
import com.zwd.crm.widget.ViewHolder;

/**
 * Created by asus-pc on 2017/3/12.
 */

public class report_context_RengwuDelegate implements ItemViewDelegate<Report_context_RengwuGet> {
    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.item_reportcontext_rengwu;
    }

    @Override
    public boolean isForViewType(Report_context_RengwuGet item, int position)
    {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, Report_context_RengwuGet party, int position)
    {
        holder.setText(R.id.item_reportcontext_rengwu_title,party.getTitle());
        if(party.getTime()!=null){
            holder.setText(R.id.item_reportcontext_rengwu_time,party.getTime().replace("T"," "));
        }
        holder.setText(R.id.item_reportcontext_rengwu_txt,party.getTxt());
        holder.setText(R.id.item_reportcontext_rengwu_name,party.getName());
        if(party.getEnd()!=null){
            holder.setText(R.id.item_reportcontext_rengwu_end,party.getEnd().replace("T"," ")+" "+"截止");

        }
        holder.setText(R.id.item_reportcontext_rengwu_reply,party.getReply()+"条回复");
        holder.setText(R.id.item_reportcontext_rengwu_num,party.getNum()+"个子任务");



    }
}
