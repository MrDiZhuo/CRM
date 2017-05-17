package com.zwd.crm.HomePage.me.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.zwd.crm.HomePage.bindPhone.View.Bind_PhoneActivity;
import com.zwd.crm.HomePage.changeHeadImg.View.ChangeHeadActivity;
import com.zwd.crm.HomePage.editPwd.View.editPswActivity;
import com.zwd.crm.HomePage.fullInfo.View.FullInfoActivity;
import com.zwd.crm.HomePage.login.View.loginActivity;
import com.zwd.crm.R;
import com.zwd.crm.base.BaseFragment;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.layout.TabViewPager;
import com.zwd.crm.layout.layout_action_bar;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;


public class meFragment extends BaseFragment {
    @Bind(R.id.me_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.circleimgview_my_setting_head_img)
    CircleImageView mHeadImg;
    @Bind(R.id.iv_my_setting_change_img)
    ImageView ivChangeImg;
    @Bind(R.id.ll_my_setting_full_info)
    LinearLayout llFullInfo;
    @Bind(R.id.ll_my_setting_change_psw)
    LinearLayout llChangePsw;
    @Bind(R.id.ll_my_setting_bundle_phone)
    LinearLayout llBundlePhone;
    @Bind(R.id.ll_my_setting_out)
    LinearLayout out;
    protected int getContentViewID() {
        return R.layout.fragment_me;
    }
    Context context;

    private static final int REQUEST_head = 0;
    private String filename="";

    private String url_heading="";
    private String url="http://opoecp2mn.bkt.clouddn.com/";

    private int userid;
    private String password="";
    private String oldphone="";
    private String token="";
    Bundle bundle = new Bundle();

    @Override
    protected void initViews(View rootView) {
        context = getContext();
        initActionBar(actionBar);
        userid=((TabViewPager) getActivity()).login_Get.getId();
        password=((TabViewPager)getActivity()).login_Get.getPassword();
        url_heading = ((TabViewPager)getActivity()).login_Get.getHeading();
        oldphone =  ((TabViewPager)getActivity()).login_Get.getPhone();
        token=((TabViewPager)getActivity()).token;
        url=url+url_heading;
        Glide.with(mContext).load(url).into(mHeadImg);

        bundle.putInt("userid",userid);
        bundle.putString("password",password);
        bundle.putString("token",token);
    }

    private void initActionBar(layout_action_bar actionBar) {
        actionBar.hideBack();
        actionBar.hideAdd();
        actionBar.setTitle("我的");
    }
    @OnClick({R.id.iv_my_setting_change_img,R.id.ll_my_setting_change_psw,R.id.ll_my_setting_full_info
    ,R.id.ll_my_setting_out,R.id.ll_my_setting_bundle_phone})
    public void click(View v){
        switch (v.getId()){
            case R.id.iv_my_setting_change_img:
                goActivityForResult(ChangeHeadActivity.class,bundle,0);
                break;
            case R.id.ll_my_setting_change_psw:
                goActivity(editPswActivity.class,bundle);
                break;
            case R.id.ll_my_setting_full_info:
                goActivity(FullInfoActivity.class,bundle);
                break;
            case R.id.ll_my_setting_out:
                goActivity(loginActivity.class);
                break;
            case R.id.ll_my_setting_bundle_phone:
                Bundle bundle = new Bundle();
                bundle.putString("oldphone",oldphone);
                bundle.putInt("userid",userid);
                goActivity(Bind_PhoneActivity.class,bundle);
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (resultCode) {
            case REQUEST_head:
                if(requestCode ==0){
                    Bundle bundle = intent.getExtras();
                    filename=bundle.getString("filename");
                    Glide.with(this).load(filename).into(mHeadImg);
                }
                break;
        }
    }

}
