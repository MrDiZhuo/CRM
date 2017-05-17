package com.zwd.crm.HomePage.inform.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.zwd.crm.HomePage.apply.View.ApplyActivity;
import com.zwd.crm.HomePage.client.Controller.ClientItemDelegate;
import com.zwd.crm.HomePage.inform.Module.InformListGet;
import com.zwd.crm.R;
import com.zwd.crm.base.BaseAppManager;
import com.zwd.crm.interfaze.ItemViewDelegate;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.layout.TabViewPager;
import com.zwd.crm.util.ToastUtils;
import com.zwd.crm.widget.ViewHolder;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by asus-pc on 2017/3/8.
 */

public class informItemDelegate implements ItemViewDelegate<InformListGet> {
    private informItemDelegate.ButtonInterface buttonInterface;

    public interface ButtonInterface{
        public void onclick(View view,int position);
    }
    public informItemDelegate setButtonInterface(ButtonInterface buttonInterface) {
        this.buttonInterface = buttonInterface;
        return this;
    }
    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.item_inform_shenqing;
    }

    @Override
    public boolean isForViewType(InformListGet item, int position)
    {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, final InformListGet party, final int position)
    {
        CircleImageView imageView = (CircleImageView) holder.getView(R.id.shenqing_img);
        if(party.getHeading()!=null){
            Glide.with(BaseAppManager.getInstance().getForwardActivity())
                    .load("http://opoecp2mn.bkt.clouddn.com/"+party.getHeading())
                    .into(imageView);
        }

        holder.setText(R.id.shenqing_name,party.getName());
        holder.setText(R.id.shenqing_date,party.getDatetime().replace("T"," "));
        holder.setText(R.id.shenqing_tel,party.getPhone());
        holder.setOnClickListener(R.id.shenqing_yes, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent( BaseAppManager.getInstance().getForwardActivity(),ApplyActivity.class);
                intent.putExtra("img",party.getHeading());
                intent.putExtra("name",party.getName());
                intent.putExtra("tell",party.getPhone());
                TabViewPager tabViewPager = (TabViewPager)BaseAppManager.getInstance().getForwardActivity();
                intent.putExtra("id",party.getId());
                intent.putExtra("usergroupId",tabViewPager.login_Get.getUserOrganization().getUsersgroupid());
                BaseAppManager.getInstance().getForwardActivity().startActivity(intent);
            }
        });

        holder.setOnClickListener(R.id.shenqing_no, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonInterface!=null){
                    buttonInterface.onclick(v,position);
                } else {
                    Log.e("------", "kong");
                }
            }
        });

    }


}
