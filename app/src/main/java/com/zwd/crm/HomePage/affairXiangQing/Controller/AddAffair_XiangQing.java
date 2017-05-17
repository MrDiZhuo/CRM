package com.zwd.crm.HomePage.affairXiangQing.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zwd.crm.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by asus-pc on 2017/3/18.
 */

public class AddAffair_XiangQing {
    public static void AddAffair_XiangQing_(final Context context, final LinearLayout ll,
                                            final String ResourceId,final String name,final String date,final String text){
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.item_affair_xiangqing,null);
        CircleImageView img = (CircleImageView)view.findViewById(R.id.item_affair_xiangqing_img);
        Glide.with(context).load("http://opoecp2mn.bkt.clouddn.com/"+ResourceId).into(img);
        TextView txt_name =(TextView)view.findViewById(R.id.item_affair_xiangqing_name);
        txt_name.setText(name);
        TextView txt_date =(TextView)view.findViewById(R.id.item_affair_xiangqing_time);
        txt_date.setText(date.replace("T"," "));
        TextView txt_txt =(TextView)view.findViewById(R.id.item_affair_xiangqing_txt);
        txt_txt.setText(text);
        view.setLayoutParams(lp);
        ll.addView(view);

    }
}
