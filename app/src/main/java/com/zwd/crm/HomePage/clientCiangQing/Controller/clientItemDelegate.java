package com.zwd.crm.HomePage.clientCiangQing.Controller;

import com.bumptech.glide.Glide;
import com.zwd.crm.HomePage.clientCiangQing.Module.ClientXQBeizhuElement;
import com.zwd.crm.R;
import com.zwd.crm.base.BaseAppManager;
import com.zwd.crm.interfaze.ItemViewDelegate;
import com.zwd.crm.widget.ViewHolder;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by asus-pc on 2017/5/6.
 */

public class clientItemDelegate implements ItemViewDelegate<ClientXQBeizhuElement> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_affair_xiangqing;
    }

    @Override
    public boolean isForViewType(ClientXQBeizhuElement item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, ClientXQBeizhuElement get, int position) {
        CircleImageView imageView = (CircleImageView) holder.getView(R.id.item_affair_xiangqing_img);
        if(get.getHeading()!=null){
            Glide.with(BaseAppManager.getInstance().getForwardActivity())
                    .load("http://opoecp2mn.bkt.clouddn.com/"+get.getHeading())
                    .into(imageView);
        }
        holder.setText(R.id.item_affair_xiangqing_name,get.getName());
        if(get.getDate()!=null){
            holder.setText(R.id.item_affair_xiangqing_time,get.getDate().replace("T"," "));

        }
        holder.setText(R.id.item_affair_xiangqing_txt,get.getTxt());

    }
}
