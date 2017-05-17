package com.zwd.crm.HomePage.client_XiuGai.View;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.zwd.crm.HomePage.choosePerson.View.ChoosePersonActivity;
import com.zwd.crm.HomePage.client.Module.clientGet;
import com.zwd.crm.HomePage.clientAdd.View.Client_AddActivity;
import com.zwd.crm.HomePage.client_XiuGai.Module.Edit_ClientBack;
import com.zwd.crm.HomePage.client_XiuGai.Module.Edit_ClientPost;
import com.zwd.crm.R;
import com.zwd.crm.base.BaseActivity;
import com.zwd.crm.internet.CustomCallBack;
import com.zwd.crm.internet.RemoteDataResult;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.layout.layout_action_bar;
import com.zwd.crm.util.ToastUtils;

import butterknife.Bind;
import retrofit2.Response;

public class Client_Xiu_GaiActivity extends BaseActivity {
    @Bind(R.id.client__xiu__gai_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.client__xiu__gai_customename)
    EditText edit_customename;
    @Bind(R.id.client__xiu__gai_contractname)
    EditText edit_contractname;
    @Bind(R.id.client__xiu__gai_customeposition)
    EditText edit_customeposition;
    @Bind(R.id.client__xiu__gai_customephone)
    EditText edit_customephone;
    @Bind(R.id.client__xiu__gai_customeemail)
    EditText edit_customeemail;
    @Bind(R.id.client__xiu__gai_customeaddress)
    EditText edit_customeaddress;

    Intent intent ;
    private clientGet clientGets;
    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Custom_EditCUstom.aspx";
    Edit_ClientBack editClientback =new Edit_ClientBack();



    protected int getContentView() {
        return R.layout.activity_client__xiu__gai;
    }
    @Override
    protected void initViews() {
        intent = getIntent();
        Bundle bundle =intent.getExtras();
        clientGets = (clientGet)bundle.getSerializable("clientGets");
        initActionBar(actionBar);
        initview();
    }

    private void initview() {
        edit_customename.setText(clientGets.getCustomname());
        edit_contractname.setText(clientGets.getContractname());
        edit_customeposition.setText(clientGets.getCustompostion());
        edit_customephone.setText(clientGets.getCustomphone1());
        edit_customeemail.setText(clientGets.getCustomemail());
        edit_customeaddress.setText(clientGets.getCustomaddress());

        editClientback.setCustomname(clientGets.getCustomname());
        editClientback.setContractname(clientGets.getContractname());
        editClientback.setCustompostion(clientGets.getCustompostion());
        editClientback.setCustomphone1(clientGets.getCustomphone1());
        editClientback.setCustomemail(clientGets.getCustomemail());
        editClientback.setCustomaddress(clientGets.getCustomaddress());

    }

    /**
     * 调接口
     */
    public void btn_editClient() {
        final Edit_ClientPost post = new Edit_ClientPost();
        post.setId(clientGets.getId());
        post.setGroupid(clientGets.getUsersgroupid());
        post.setContractname(edit_contractname.getText().toString());
        post.setCustomaddress(edit_customeaddress.getText().toString());
        post.setCustomemail(edit_customeemail.getText().toString());
        post.setCustomphone1(edit_customephone.getText().toString());
        post.setCustomname(edit_customename.getText().toString());
        post.setCustompostion(edit_customeposition.getText().toString());
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Custom_EditCUstom(post, baseUrl, url, new CustomCallBack<RemoteDataResult>() {
            @Override
            public void onSuccess(Response<RemoteDataResult> response) {
                editClientback.setCustomname(post.getCustomname());
                editClientback.setContractname(post.getContractname());
                editClientback.setCustompostion(post.getCustompostion());
                editClientback.setCustomphone1(post.getCustomphone1());
                editClientback.setCustomemail(post.getCustomemail());
                editClientback.setCustomaddress(post.getCustomaddress());

                ToastUtils.showShort(Client_Xiu_GaiActivity.this, response.body().getResultMessage());
            }
            @Override
            public void onFail(String message) {
                ToastUtils.showShort(Client_Xiu_GaiActivity.this, message);
            }
        });
    }

    private void initActionBar(layout_action_bar actionBar) {
        actionBar.setTitle("客户修改");
        actionBar.setPriviousClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("editClientback",editClientback);
                intent.putExtras(bundle);
                Client_Xiu_GaiActivity.this.setResult(0,intent);
                Client_Xiu_GaiActivity.this.finish();
            }
        });
        actionBar.setImage(R.mipmap.finish);
        actionBar.setAddClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_editClient();
            }
        });
    }


    /**
     *键盘返回
     */
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("editClientback",editClientback);
            intent.putExtras(bundle);
            Client_Xiu_GaiActivity.this.setResult(0,intent);
            Client_Xiu_GaiActivity.this.finish();
        }
        return true;
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
