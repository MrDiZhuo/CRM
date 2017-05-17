package com.zwd.crm.HomePage.taskJiegou.Controller;

import com.zwd.crm.HomePage.taskJiegou.Module.Task_JIegouGet;
import com.zwd.crm.R;
import com.zwd.crm.interfaze.ItemViewDelegate;
import com.zwd.crm.widget.ViewHolder;

/**
 * Created by asus-pc on 2017/3/15.
 */

public class taskjiegouItemDelegate implements ItemViewDelegate<Task_JIegouGet> {
    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.item_task_jiegou;
    }

    @Override
    public boolean isForViewType(Task_JIegouGet item, int position)
    {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, Task_JIegouGet party, int position)
    {
        holder.setText(R.id.item_task_jiegou,party.getTasktitle());
    }
}
