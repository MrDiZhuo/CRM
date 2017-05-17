package com.zwd.crm.HomePage.reportContext.Controller;

import com.zwd.crm.HomePage.addAffair.Module.Addaffair_person;
import com.zwd.crm.HomePage.reportContext.Module.Report_CotentGet;
import com.zwd.crm.HomePage.reportContext.Module.Report_target;
import com.zwd.crm.R;
import com.zwd.crm.interfaze.ItemViewDelegate;
import com.zwd.crm.widget.ViewHolder;

/**
 * Created by asus-pc on 2017/3/12.
 */

public class reportContextDelegate implements ItemViewDelegate<Addaffair_person> {
    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.item_report_context;
    }

    @Override
    public boolean isForViewType(Addaffair_person item, int position)
    {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, Addaffair_person party, int position)
    {
        holder.setImageResource(R.id.item_report_context_img,party.getImg());
        holder.setText(R.id.item_report_context_txt,party.getTitle());
        holder.setText(R.id.item_report_context_name,party.getName());
    }

}
