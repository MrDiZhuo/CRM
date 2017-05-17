package com.zwd.crm.HomePage.clientState.Controller;

import com.zwd.crm.HomePage.clientState.Module.Client_StateGet;
import com.zwd.crm.R;
import com.zwd.crm.interfaze.ItemViewDelegate;
import com.zwd.crm.widget.ViewHolder;

/**
 * Created by asus-pc on 2017/3/22.
 */

public class clientStateItemDelegate implements ItemViewDelegate<Client_StateGet> {
    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.item_client_state;
    }

    @Override
    public boolean isForViewType(Client_StateGet item, int position)
    {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, Client_StateGet party, int position)
    {

        holder.setText(R.id.item_client_state_name,party.getName());
        holder.setText(R.id.item_client_state_state,party.getStatename());
    }
}
