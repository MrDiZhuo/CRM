package com.zwd.crm.HomePage.editPwd.View;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.zwd.crm.HomePage.editPwd.Module.EditPwdPost;
import com.zwd.crm.R;
import com.zwd.crm.base.BaseActivity;
import com.zwd.crm.base.BaseFragment;
import com.zwd.crm.internet.CustomCallBack;
import com.zwd.crm.internet.RemoteDataResult;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.layout.layout_action_bar;
import com.zwd.crm.util.ToastUtils;

import butterknife.Bind;
import retrofit2.Response;

public class editPswActivity extends BaseActivity {
    @Bind(R.id.edit_psw_action_bar)
    layout_action_bar actionbar;
    @Bind(R.id.et_editpsw_psw)
    EditText edit_psw;
    @Bind(R.id.et_editpsw_new_psw)
    EditText edit_new_psw;
    @Bind(R.id.et_editpsw_hint_psw_confirm)
    EditText edit_confirm;

    String baseUrl="http://139.224.164.183:8088/";
    String url="Users_EditPassword.aspx";
    protected int getContentView(){
        return R.layout.activity_edit_psw;
    }

    protected void initViews() {
        initActionBar(actionbar);
    }

    private void initActionBar(layout_action_bar actionbar) {
        actionbar.setPriviousClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionbar.setTitle("密码更改");
        actionbar.setImage(R.mipmap.finish);
        actionbar.setAddClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_EditPsw();
            }
        });
    }

    /**
     * 调接口
     */
    public void btn_EditPsw(){
        EditPwdPost post = new EditPwdPost();
        Bundle bundle = getIntent().getExtras();
        post.setId(bundle.getInt("userid"));
        post.setPassword(edit_psw.getText().toString());
        post.setNewPsw(edit_new_psw.getText().toString());
        if(check()){
            RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
            remoteOptionIml.Users_EditPassword(post, baseUrl, url, new CustomCallBack<RemoteDataResult>() {
                @Override
                public void onSuccess(Response<RemoteDataResult> response) {
                    Log.d("seccess",response.body().getResultCode()+"");
                    ToastUtils.showShort(editPswActivity.this,response.body().getResultMessage());
                }

                @Override
                public void onFail(String message) {
                    ToastUtils.showShort(editPswActivity.this,message);
                }
            });
        }

    }

    /**
     * 判空
     */
    public boolean check(){
        if(edit_psw.getText().toString().length()>0&&edit_new_psw.getText().toString().length()>0&&
                edit_confirm.getText().toString().length()>0){
            Bundle bundle = getIntent().getExtras();
            if(edit_psw.getText().toString().equals(bundle.getString("password"))){
                if(edit_new_psw.getText().toString().equals(edit_confirm.getText().toString())){
                    return true;
                }else {
                    ToastUtils.showShort(editPswActivity.this,"两次输入密码不匹配！");
                    return false;
                }
            }else {
                ToastUtils.showShort(editPswActivity.this,"原密码输入不正确!");
                return false;
            }

        }else {
            ToastUtils.showShort(editPswActivity.this,"不能为空！");
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
