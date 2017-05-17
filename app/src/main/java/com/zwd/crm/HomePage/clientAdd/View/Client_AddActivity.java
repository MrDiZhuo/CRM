package com.zwd.crm.HomePage.clientAdd.View;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.zwd.crm.HomePage.client.View.ClientActivity;
import com.zwd.crm.HomePage.clientAdd.Module.clientAddPost;
import com.zwd.crm.R;
import com.zwd.crm.base.BaseActivity;
import com.zwd.crm.internet.CustomCallBack;
import com.zwd.crm.internet.RemoteDataResult;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.layout.layout_action_bar;
import com.zwd.crm.util.ToastUtils;

import butterknife.Bind;
import retrofit2.Response;

public class Client_AddActivity extends BaseActivity {
    @Bind(R.id.client__add_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.client__add_contractname)
    EditText edit_contractname;
    @Bind(R.id.client__add_customname)
    EditText edit_customname;
    @Bind(R.id.client__add_custompostion)
    EditText edit_custompostion;
    @Bind(R.id.client__add_customphone1)
    EditText edit_customphone1;
    @Bind(R.id.client__add_customemail)
    EditText edit_customemail;
    @Bind(R.id.client__add_customaddress)
    EditText edit_customaddress;

    private int createId;
    private int usergroupId;
    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Custom_AddCustom.aspx";
    protected int getContentView() {
        return R.layout.activity_client__add;
    }
    @Override
    protected void initViews() {
        Bundle bundle = getIntent().getExtras();
        createId=bundle.getInt("createId");
        usergroupId=bundle.getInt("usergroupId");
        initActionBar(actionBar);
    }

    private void initActionBar(layout_action_bar actionBar) {
        actionBar.setPriviousClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionBar.setTitle("添加客户");
        actionBar.setImage(R.mipmap.finish);
        actionBar.setAddClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_clientAdd();
            }
        });
    }
    /**
     * 调接口
     */
    public void btn_clientAdd(){
        if(check()){
            clientAddPost post = new clientAddPost();
            post.setCustomname(edit_customname.getText().toString());
            post.setUsersgroupid(usergroupId);
            post.setContractname(edit_contractname.getText().toString());
            post.setCreatorid(createId);
            post.setCustomaddress(edit_customaddress.getText().toString());
            post.setCustomemail(edit_customemail.getText().toString());
            post.setCustomphone1(edit_customphone1.getText().toString());
            post.setCustomphone2("");
            post.setCustompostion(edit_custompostion.getText().toString());
            post.setCustomstatus(0);
            post.setCustomuniqueid("");
            RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
            remoteOptionIml.Custom_AddCustom(post, baseUrl, url, new CustomCallBack<RemoteDataResult>() {
                @Override
                public void onSuccess(Response<RemoteDataResult> response) {
                    ToastUtils.showShort(Client_AddActivity.this,response.body().getResultMessage());
                }

                @Override
                public void onFail(String message) {
                    ToastUtils.showShort(Client_AddActivity.this,message);
                }
            });
        }

    }
    /**
     * 判空
     **/
    public boolean check(){
        if(edit_customname.getText().toString().isEmpty()||edit_contractname.getText().toString().isEmpty()||
                edit_custompostion.getText().toString().isEmpty()||edit_customphone1.getText().toString().isEmpty()||
                edit_customemail.getText().toString().isEmpty()||edit_customaddress.getText().toString().isEmpty()){
            ToastUtils.showShort(Client_AddActivity.this,"不能为空！");
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
