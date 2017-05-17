package com.zwd.crm.layout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zwd.crm.R;

public class layout_action_bar extends RelativeLayout {
    View mcontentView;
    ImageView mprivious;
    ImageView madd;
    TextView mtitle;

    public layout_action_bar(Context context) {
        super(context);
        initView(context);
    }



    public layout_action_bar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public layout_action_bar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    private void initView(Context context) {
        mcontentView = LayoutInflater.from(context).inflate(R.layout.layout_action_bar,this)
                .findViewById(R.id.layout_action_bar);
        mprivious = (ImageView)mcontentView.findViewById(R.id.action_bar_privious);
        mtitle = (TextView)mcontentView.findViewById(R.id.action_bar_title);
        madd =(ImageView)mcontentView.findViewById(R.id.action_bar_add);
    }
    /**
     * 设置回调监听
     */
    public void setPriviousClick(OnClickListener listener){
        mprivious.setOnClickListener(listener);
    }
    public void setAddClick(OnClickListener listener){
        madd.setOnClickListener(listener);
    }
    /**
     * 设置标题
     */
    public void setTitle(String title){
        mtitle.setText(title);
    }
    public String getTitle(){
        return mtitle.getText().toString();
    }
    /**
     * 设置图片
     */
    public void setImage(int resourceId){
        Glide.with(getContext()).load(resourceId).into(madd);
    }
    public Drawable getImage(){
        return madd.getBackground();
    }
    /**
     * 隐藏
     */
    public void hideBack(){
        mprivious.setVisibility(View.INVISIBLE);
    }
    public void hideAdd(){
        madd.setVisibility(View .INVISIBLE);
    }

}
