package com.zwd.crm.HomePage.login.View;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zwd.crm.HomePage.forgetPsw.View.forgetPswActivity;
import com.zwd.crm.HomePage.login.Module.loginGet;
import com.zwd.crm.HomePage.login.Module.loginPost;
import com.zwd.crm.HomePage.register.View.registerActivity;
import com.zwd.crm.R;
import com.zwd.crm.base.BaseActivity;
import com.zwd.crm.internet.CustomCallBack;
import com.zwd.crm.internet.RemoteDataResult;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.layout.TabViewPager;
import com.zwd.crm.layout.layout_action_bar;
import com.zwd.crm.util.ToastUtils;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class loginActivity extends BaseActivity {
    @Bind(R.id.login_action_bar)
    layout_action_bar actionbar;
    @Bind(R.id.btn_login_login)
    Button btnLogin;
    @Bind(R.id.btn_login_register)
    Button btnRegister;
    @Bind(R.id.et_login_psw)
    EditText etPsw;
    @Bind(R.id.et_login_username)
    EditText etUserName;
    @Bind(R.id.tv_login_forget_psw)
    TextView tvForgetPsw;

    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Users_CheckLogin.aspx";

    private String baseUrl_get="http://139.224.164.183:1000/";
    private String url_get="SJFQiNiuUploadToken.aspx";

    String token="";

    private loginPost post;

    protected int getContentView(){
        return R.layout.activity_login2;
    }

    protected void initViews() {
        initActionBar(actionbar);
        post = new loginPost();

    }

    private void initActionBar(layout_action_bar actionbar) {
        actionbar.hideBack();
        actionbar.setTitle("登录");
        actionbar.hideAdd();
    }

    @OnClick({R.id.btn_login_login,R.id.tv_login_forget_psw,R.id.btn_login_register})
    public void click(View v){
        switch (v.getId()){
            case R.id.btn_login_login:
                btn_qiniu();
                btn_login();
                break;
            case R.id.tv_login_forget_psw:
                goActivity(forgetPswActivity.class);
                break;
            case R.id.btn_login_register:
                goActivity(registerActivity.class);
                break;
        }
    }

    /**
     * 七牛获取token
     */
    public void btn_qiniu(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.GetToken(baseUrl_get, url_get, new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                byte[] get=response.body().getBytes();
                token = new String(get);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("error",call.toString());
            }
        });
    }


    /**
     * 调接口
     */

    public void btn_login(){
        if(check()){
            post.setUsername(etUserName.getText().toString());
            post.setPassword(etPsw.getText().toString());
            final RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
            remoteOptionIml.login(post, baseUrl, url, new CustomCallBack<RemoteDataResult<loginGet>>() {
                @Override
                public void onSuccess(Response<RemoteDataResult<loginGet>> response) {
                    if(response.body().getResultCode()==1){
                        loginGet login_get = response.body().getData();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("login_get",login_get);
                        Log.d("heading",login_get.getHeading());
                        bundle.putString("token",token);
                        Log.d("tokebun",token);
                        //ToastUtils.showShort(loginActivity.this,String.valueOf(login_get.getUserOrganization().getUsersgroupid()));
                        goActivity(TabViewPager.class,bundle);
                    }
                }
                @Override
                public void onFail(String message) {
                    ToastUtils.showShort(loginActivity.this,"123546"+message);
                    Log.d("error","123546"+message);
                }
            });
        }

    }

    /**
     * 判空
     * @return
     */
    public boolean check(){
        if(etPsw.getText().toString().isEmpty()||etUserName.getText().toString().isEmpty()){
            ToastUtils.showShort(this,"不能为空！");
            return false;
        }else return true;
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
