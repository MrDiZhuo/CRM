package com.zwd.crm.HomePage.taskIntroduce.Controller;

import com.zwd.crm.HomePage.taskIntroduce.Module.taskIntroduceElement;
import com.zwd.crm.R;
import com.zwd.crm.interfaze.ItemViewDelegate;
import com.zwd.crm.widget.ViewHolder;

/**
 * Created by asus-pc on 2017/3/10.
 */

public class taskIntroduceItemDelegate implements ItemViewDelegate<taskIntroduceElement> {
        @Override
    public int getItemViewLayoutId()
    {
        return R.layout.item_task_introduce_delegate;
    }

    @Override
    public boolean isForViewType(taskIntroduceElement item, int position)
    {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, taskIntroduceElement party, int position)
    {
        holder.setText(R.id.item_introduce_name,party.getName());
        holder.setText(R.id.item_introduce_time,party.getTime());
        holder.setText(R.id.item_introduce_txt,party.getText());
        holder.setText(R.id.item_introduce_juese,party.getJuese());
        holder.setText(R.id.item_introduce_state,party.getState());
    }
}
