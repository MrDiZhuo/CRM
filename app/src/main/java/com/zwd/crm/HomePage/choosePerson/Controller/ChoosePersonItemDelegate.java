package com.zwd.crm.HomePage.choosePerson.Controller;

import com.zwd.crm.HomePage.choosePerson.Module.ChoosePersonElement;
import com.zwd.crm.HomePage.choosePerson.Module.ChoosePersonGet;
import com.zwd.crm.R;
import com.zwd.crm.interfaze.ItemViewDelegate;
import com.zwd.crm.widget.ViewHolder;

/**
 * Created by asus-pc on 2017/3/11.
 */

public class ChoosePersonItemDelegate implements ItemViewDelegate<ChoosePersonGet> {
    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.item_chengyuan;
    }

    @Override
    public boolean isForViewType(ChoosePersonGet item, int position)
    {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, ChoosePersonGet party, int position)
    {
        holder.setText(R.id.tvCity,party.getName());
    }
}
