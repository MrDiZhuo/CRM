package com.zwd.crm.HomePage.forgetPsw.View;

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

import com.zwd.crm.HomePage.login.Module.loginPost;
import com.zwd.crm.HomePage.register.Module.VertifyGet;
import com.zwd.crm.HomePage.register.View.registerActivity;
import com.zwd.crm.R;
import com.zwd.crm.base.BaseActivity;
import com.zwd.crm.interfaze.RemoteApi;
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

public class forgetPswActivity extends BaseActivity {
    @Bind(R.id.forget_psw_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.et_forget_psw_username)
    EditText edit_username;
    @Bind(R.id.et_forget_psw_psw)
    EditText edit_psw;
    @Bind(R.id.et_forget_psw_hint_psw_confirm)
    EditText edit_psw_confirm;
    @Bind(R.id.et_forget_psw_verification_code)
    EditText edit_verification;
    @Bind(R.id.btn_forget_psw_send_verification_code)
    TextView txt_verification;

    private String baseUrl_vertify="http://139.224.164.183:8012/";
    private String url_vertify="returncode.aspx";
    VertifyGet code;
    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Users_ReturnPassword.aspx";
    private loginPost post;

    protected int getContentView(){
        return R.layout.activity_forget_psw;
    }

    protected void initViews() {
        initActionBar(actionBar);
        post=new loginPost();

    }
    public void btn_forget_psw(){
        post.setUsername(edit_username.getText().toString());
        post.setPassword(edit_psw.getText().toString());
        if(check()){
            RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
            remoteOptionIml.forget_pwd(post, baseUrl, url, new CustomCallBack<RemoteDataResult>() {
                @Override
                public void onSuccess(Response<RemoteDataResult> response) {
                    if(response.body().getResultCode()==1){
                        ToastUtils.showShort(forgetPswActivity.this,"密码修改成功!");
                    }

                }
                @Override
                public void onFail(String message) {
                    ToastUtils.showShort(forgetPswActivity.this,message);

                }
            });
        }
    }
    @OnClick({R.id.btn_forget_psw_send_verification_code})
    public void click(View v){
        switch (v.getId()){
            case R.id.btn_forget_psw_send_verification_code:
                btn_code();
                break;
        }
    }

    /**
     * 发送验证码
     */
    public void btn_code(){
        if(edit_username.getText().toString().length()==0){
            ToastUtils.showShort(forgetPswActivity.this,"请输入手机号!");
        }else {
            boolean phone = isPhoneUtil.isPhone(edit_username.getText().toString().trim());
            if(phone){
                CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(txt_verification, 60000, 1000);
                mCountDownTimerUtils.start();
                RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
                remoteOptionIml.returncode(edit_verification.getText().toString(), baseUrl_vertify,url,  new CustomCallBack<RemoteDataResult<VertifyGet>>() {
                    @Override
                    public void onSuccess(Response<RemoteDataResult<VertifyGet>> response) {
                        code = response.body().getData();
                       // Log.d("code",code+" ");
                        ToastUtils.showShort(forgetPswActivity.this,response.body().getResultMessage());
                    }

                    @Override
                    public void onFail(String message) {
                        ToastUtils.showShort(forgetPswActivity.this,message);

                    }
                });
            }else {
                ToastUtils.showShort(forgetPswActivity.this,"请输入正确的手机号!");
            }
        }


    }

    /**
     * 检查输入框
     * @return
     */
    private boolean check(){
        if(edit_psw.getText().toString().isEmpty()||edit_username.getText().toString().isEmpty()||
                edit_psw_confirm.getText().toString().isEmpty()||edit_verification.getText().toString().isEmpty()){
            ToastUtils.showShort(forgetPswActivity.this,"不能为空！");
            return false;
        }else if(edit_psw.getText().toString().equals(edit_psw_confirm.getText().toString())==false){
            ToastUtils.showShort(forgetPswActivity.this,"两次输入密码不匹配！");
            return false;
        }else
            return true;

    }


    private void initActionBar(layout_action_bar actionBar) {
        actionBar.setTitle("忘记密码");
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
                btn_forget_psw();
            }
        });
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
