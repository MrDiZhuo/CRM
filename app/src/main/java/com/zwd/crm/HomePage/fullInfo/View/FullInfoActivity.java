package com.zwd.crm.HomePage.fullInfo.View;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.zwd.crm.HomePage.fullInfo.Module.fullFillPost;
import com.zwd.crm.R;
import com.zwd.crm.base.BaseActivity;
import com.zwd.crm.internet.CustomCallBack;
import com.zwd.crm.internet.RemoteDataResult;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.layout.layout_action_bar;
import com.zwd.crm.util.ToastUtils;

import butterknife.Bind;
import retrofit2.Response;

public class FullInfoActivity extends BaseActivity {
    @Bind(R.id.full_info_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.et_full_info_nickname)
    EditText edit_nickname;
    @Bind(R.id.et_full_info_wechat)
    EditText edit_wechit;
    @Bind(R.id.et_full_info_alipay)
    EditText edit_alipay;
    @Bind(R.id.et_full_info_email)
    EditText edit_email;
    @Bind(R.id.et_full_info_user_tag)
    EditText edit_tag;

    String baseUrl="http://139.224.164.183:8088/";
    String url="Users_EditUserInfo.aspx";

    protected int getContentView(){
        return R.layout.activity_full_info;
    }

    protected void initViews() {
        initActionBar(actionBar);
    }

    private void initActionBar(layout_action_bar actionBar) {
        actionBar.setTitle("个人信息完善");
        actionBar.setPriviousClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionBar.setImage(R.mipmap.finish);
        actionBar.setAddClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_fullInfo();
            }
        });
    }

    /**
     * 调接口
     */
    public void btn_fullInfo(){
        fullFillPost post = new fullFillPost();
        Bundle bundle = getIntent().getExtras();
        post.setId(bundle.getInt("userid"));
        post.setNickname(edit_nickname.getText().toString());
        post.setWechat(edit_wechit.getText().toString());
        post.setAlipay(edit_alipay.getText().toString());
        post.setEmail(edit_email.getText().toString());
        post.setUsertag(edit_tag.getText().toString());
        if(check()){
            RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
            remoteOptionIml.Users_EditUserInfo(post, baseUrl, url, new CustomCallBack<RemoteDataResult>() {
                @Override
                public void onSuccess(Response<RemoteDataResult> response) {
                    ToastUtils.showShort(FullInfoActivity.this,response.body().getResultMessage());
                }

                @Override
                public void onFail(String message) {
                    ToastUtils.showShort(FullInfoActivity.this,message);
                }
            });
        }

    }
    /**
     * 判空
     */
    public boolean check(){
        if(edit_nickname.getText().toString().length()>0||edit_wechit.getText().toString().length()>0||
                edit_alipay.getText().toString().length()>0||edit_email.getText().toString().length()>0||
                edit_tag.getText().toString().length()>0){
            return true;
        }else {
            ToastUtils.showShort(FullInfoActivity.this,"至少填写一处!");
            return false;
        }
    }
    /**
     * 点击键盘以外的区域隐藏键盘
     * @param ev
     * @return
     */

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        if(ev.getAction() ==MotionEvent.ACTION_DOWN){
            View v =getCurrentFocus();
            if(isShoudHideKeyBoard(v,ev)){
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm!=null){
                    imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        if(getWindow().superDispatchTouchEvent(ev)){
            return true;
        }
        return onTouchEvent(ev);
    }
    private boolean isShoudHideKeyBoard(View v, MotionEvent event) {
        if(v!=null&&(v instanceof EditText)){
            int[] l = {0,0};
            v.getLocationInWindow(l);
            int left = l[0], top =l[1],bottom = top+v.getHeight(),right=left+v.getWidth();
            if(event.getX()>left && event.getX()<right &&event.getY()<bottom && event.getY()>top){
                return false;
            }else {
                return true;
            }
        }
        return false;

    }


}
