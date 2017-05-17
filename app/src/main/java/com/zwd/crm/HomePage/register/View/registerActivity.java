package com.zwd.crm.HomePage.register.View;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.util.Util;
import com.zwd.crm.HomePage.login.Module.loginPost;
import com.zwd.crm.HomePage.register.Module.VertifyGet;
import com.zwd.crm.R;
import com.zwd.crm.base.BaseActivity;
import com.zwd.crm.base.BaseFragment;
import com.zwd.crm.internet.CustomCallBack;
import com.zwd.crm.internet.RemoteDataResult;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.layout.layout_action_bar;
import com.zwd.crm.util.CountDownTimerUtils;
import com.zwd.crm.util.ToastUtils;
import com.zwd.crm.util.isPhoneUtil;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Response;

public class registerActivity extends BaseActivity {
    @Bind(R.id.register_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.et_register_username)
    EditText edit_username;
    @Bind(R.id.et_resister_psw)
    EditText edit_psw;
    @Bind(R.id.et_register_hint_psw_confirm)
    EditText edit_psw_confirm;
    @Bind(R.id.et_register_verification_code)
    EditText edit_verification;
    @Bind(R.id.btn_register_send_verification_code)
    TextView txt_verification;

    VertifyGet code ;
    private String baseUrl_vertify="http://139.224.164.183:8012/";
    private String url_vertify="returncode.aspx";
    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Users_Register.aspx";
    private loginPost post;
    protected int getContentView(){
        return R.layout.activity_register;
    }

    protected void initViews() {
        initActionBar(actionBar);
        post=new loginPost();
    }

    private void initActionBar(layout_action_bar actionBar) {
        actionBar.setTitle("账户注册");
        actionBar.setImage(R.mipmap.finish);
        actionBar.setPriviousClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionBar.setAddClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_register();
            }
        });
    }

    /**
     * 调接口
     */
    private void btn_register(){
        post.setUsername(edit_username.getText().toString());
        post.setPassword(edit_psw.getText().toString());
        if(check()){
            RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
                remoteOptionIml.register(post, baseUrl, url, new CustomCallBack<RemoteDataResult>() {
                    @Override
                    public void onSuccess(Response<RemoteDataResult> response) {
                        if(response.body().getResultCode()==1){
                            ToastUtils.showShort(registerActivity.this,"注册成功!");
                        }
                    }
                    @Override
                    public void onFail(String message) {
                        ToastUtils.showShort(registerActivity.this,message);

                    }
                });
            }



    }
    @OnClick({R.id.btn_register_send_verification_code})
    public void click(View v){
        switch (v.getId()){
            case R.id.btn_register_send_verification_code:
                btn_code();
                break;
        }
    }

    /**
     * 判断输入框
     * @return
     */
    private boolean check() {
        if (edit_psw.getText().toString().isEmpty() || edit_username.getText().toString().isEmpty() ||
                edit_psw_confirm.getText().toString().isEmpty() || edit_verification.getText().toString().isEmpty()) {
            ToastUtils.showShort(registerActivity.this, "不能为空！");
            return false;
        } else {
            if (edit_psw.getText().toString().equals(edit_psw_confirm.getText().toString()) == false) {
                ToastUtils.showShort(registerActivity.this, "两次输入密码不匹配！");
                return false;
            } else {
                if (edit_verification.getText().toString()!=code.getData()) {
                    ToastUtils.showShort(registerActivity.this, "验证码输入错误！");
                    return false;
                }else return true;

            }

        }
    }


    /**
     * 发送验证码
     */
    public void btn_code(){
        if(edit_username.getText().toString().length()==0){
            ToastUtils.showShort(registerActivity.this,"请输入手机号!");
        }else {
            boolean phone = isPhoneUtil.isPhone(edit_username.getText().toString().trim());
            if(phone){
                CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(txt_verification, 60000, 1000);
                mCountDownTimerUtils.start();
                RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
                remoteOptionIml.returncode(edit_username.getText().toString(), baseUrl_vertify,url_vertify, new CustomCallBack<RemoteDataResult<VertifyGet>>() {
                    @Override
                    public void onSuccess(Response<RemoteDataResult<VertifyGet>> response) {
                        code = response.body().getData();
                        Log.d("code",code+" ");
                        ToastUtils.showShort(registerActivity.this,response.body().getResultMessage());
                    }

                    @Override
                    public void onFail(String message) {
                        Log.d("error",message);
                        ToastUtils.showShort(registerActivity.this,message);

                    }
                });
            }else {
                ToastUtils.showShort(registerActivity.this,"请输入正确的手机号!");
            }
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
