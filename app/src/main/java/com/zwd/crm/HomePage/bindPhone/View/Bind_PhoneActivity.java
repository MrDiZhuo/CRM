package com.zwd.crm.HomePage.bindPhone.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zwd.crm.HomePage.bindPhone.Module.Bind_PhonePost;
import com.zwd.crm.HomePage.forgetPsw.View.forgetPswActivity;
import com.zwd.crm.HomePage.register.Module.VertifyGet;
import com.zwd.crm.R;
import com.zwd.crm.base.BaseActivity;
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

public class Bind_PhoneActivity extends BaseActivity {
    @Bind(R.id.bind_phone_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.bind_phone_phone)
    EditText edit_phone;/////新号码
    @Bind(R.id.bind_phone_code)
    EditText edit_code;
    @Bind(R.id.bind_phone_btn_code)
    TextView btn_code;

    private int userid;
    private String oldphone="";
    VertifyGet code;
    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Users_EditUsersByUsersName.aspx";

    private String baseUrl_vertify="http://139.224.164.183:8012/";
    private String url_vertify="returncode.aspx";

    protected int getContentView(){
        return R.layout.activity_bind__phone;
    }

    protected void initViews() {
        initActionBar(actionBar);
        Bundle bundle = getIntent().getExtras();
        oldphone=bundle.getString("oldphone");
        userid=bundle.getInt("userid");

    }

    @OnClick(R.id.bind_phone_btn_code)
    public void click(View v){
        switch (v.getId()){
            case R.id.bind_phone_btn_code:
                btn_code();
                break;
        }
    }

    private void btn_code() {
        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(btn_code, 60000, 1000);
        mCountDownTimerUtils.start();
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.returncode(oldphone, baseUrl_vertify,url_vertify, new CustomCallBack<RemoteDataResult<VertifyGet>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<VertifyGet>> response) {
                code = response.body().getData();
                // Log.d("code",code+" ");
                ToastUtils.showShort(Bind_PhoneActivity.this,response.body().getResultMessage());
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(Bind_PhoneActivity.this,message);
            }
        });
    }

    private void initActionBar(layout_action_bar actionBar) {
        actionBar.setPriviousClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionBar.setImage(R.mipmap.finish);
        actionBar.setTitle("更换手机");
        actionBar.setAddClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_bing();
            }
        });
    }
    /**
     * 判断
     */
    public boolean check(){
        if(edit_phone.getText().toString().isEmpty()||edit_code.getText().toString().isEmpty()){
            ToastUtils.showShort(this,"不能为空!");
            return false;
        }else {
            if(isPhoneUtil.isPhone(edit_phone.getText().toString().trim())==false){
                ToastUtils.showShort(this,"请输入正确的手机号码!");
                return false;
            }else {
                if (edit_code.getText().toString().equals(code)==false){
                    ToastUtils.showShort(this,"验证码输入不正确!");
                    return false;
                }else return true;
            }

        }
    }

    /**
     * 调接口
     */
    public void btn_bing(){
        if (check()){
            Bind_PhonePost post = new Bind_PhonePost();
            post.setId(userid);post.setNewphone(edit_phone.getText().toString());
            RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
            remoteOptionIml.Users_EditUsersByUsersName(post, baseUrl, url, new CustomCallBack<RemoteDataResult>() {
                @Override
                public void onSuccess(Response<RemoteDataResult> response) {
                    ToastUtils.showShort(Bind_PhoneActivity.this,response.body().getResultMessage());
                }

                @Override
                public void onFail(String message) {
                    ToastUtils.showShort(Bind_PhoneActivity.this,message);
                }
            });
        }

    }

}
