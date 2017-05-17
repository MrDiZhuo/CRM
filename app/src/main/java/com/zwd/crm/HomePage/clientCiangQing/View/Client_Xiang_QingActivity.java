package com.zwd.crm.HomePage.clientCiangQing.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zwd.crm.HomePage.client.Module.clientGet;
import com.zwd.crm.HomePage.clientCiangQing.Module.ClientXQBeizhuElement;
import com.zwd.crm.HomePage.clientCiangQing.Module.ClientXQBeizhuGet;
import com.zwd.crm.HomePage.client_XiuGai.Module.Edit_ClientBack;
import com.zwd.crm.HomePage.client_XiuGai.View.Client_Xiu_GaiActivity;
import com.zwd.crm.R;
import com.zwd.crm.adapter.clientXqAdapter;
import com.zwd.crm.base.BaseActivity;
import com.zwd.crm.interfaze.CommonAdapter;
import com.zwd.crm.internet.CustomCallBack;
import com.zwd.crm.internet.RemoteDataResult;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.layout.layout_action_bar;
import com.zwd.crm.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit2.Response;

public class Client_Xiang_QingActivity extends BaseActivity {
    @Bind(R.id.client__xiang__qing_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.client__xiang__qing_beizhu)
    RecyclerView recyclerView_beizhu;
    @Bind(R.id.client__xiang__qing_customname)
    TextView txt_customname;
    @Bind(R.id.client__xiang__qing_custompostion)
    TextView txt_custompostion;
    @Bind(R.id.client__xiang__qing_customphone1)
            TextView txt_customphone1;
    @Bind(R.id.client__xiang__qing_customemail)
            TextView txt_customemail;
    @Bind(R.id.client__xiang__qing_customaddress)
            TextView txt_customaddress;
    @Bind(R.id.client__xiang__qing_usersname)
            TextView txt_usersname;
    @Bind(R.id.client__xiang__qing_customcontractstatus)
            TextView txt_customcontractstatus;
    @Bind(R.id.client__xiang__qing_tasklanchername)
            TextView txt_tasklanchername;

    private static final  int EDIT_CLIENT=0;
    clientGet clientGets;
    String Customcontractstatus;
    clientXqAdapter adapter;

    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Custom_ReturnCustomDetail.aspx";

    String title="";
    List<ClientXQBeizhuElement> clientXQBeizhuElementList = new ArrayList<>();
    protected int getContentView() {
        return R.layout.activity_client__xiang__qing;
    }
    @Override
    protected void initViews() {
        Bundle bundle = getIntent().getExtras();
        clientGets= (clientGet)bundle.getSerializable("title");
        Customcontractstatus=bundle.getString("Customcontractstatus");


        initActionBar(actionBar);
        btn_ClientXQ();
        Edit_ClientBack back = new Edit_ClientBack();
        back.setCustomaddress(clientGets.getCustomaddress());
        back.setCustomemail(clientGets.getCustomemail());
        back.setCustomphone1(clientGets.getCustomphone1());
        back.setCustompostion(clientGets.getCustompostion());
        back.setContractname(clientGets.getContractname());
        back.setCustomname(clientGets.getCustomname());
        initview(back);


    }

    /**
     * 调接口
     */
    public void btn_ClientXQ(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Custom_ReturnCustomDetail(clientGets.getId(), baseUrl, url, new CustomCallBack<RemoteDataResult<ClientXQBeizhuGet>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<ClientXQBeizhuGet>> response) {
                clientXQBeizhuElementList = response.body().getData().getBackupList();
                adapter = new clientXqAdapter(Client_Xiang_QingActivity.this, clientXQBeizhuElementList);
                recyclerView_beizhu.setAdapter(adapter);
                ToastUtils.showShort(Client_Xiang_QingActivity.this,response.body().getResultMessage());
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(Client_Xiang_QingActivity.this,message);
            }
        });
    }


    public void initview(Edit_ClientBack back){
        actionBar.setTitle(back.getContractname());
        txt_customname.setText(back.getCustomname());
        txt_custompostion.setText(back.getCustompostion());
        txt_customphone1.setText(back.getCustomphone1());
        txt_customemail.setText(back.getCustomemail());
        txt_customaddress.setText(back.getCustomaddress());
        txt_usersname.setText(clientGets.getUsersname());
        txt_customcontractstatus.setText(Customcontractstatus);
        txt_tasklanchername.setText(clientGets.getTasklanchername());
    }

    private void initActionBar(layout_action_bar actionBar) {
        actionBar.setPriviousClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionBar.setImage(R.mipmap.change);
        actionBar.setAddClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("clientGets",clientGets);
                goActivityForResult(Client_Xiu_GaiActivity.class,bundle,EDIT_CLIENT);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        switch (requestCode) {
            case EDIT_CLIENT:
                if (resultCode==0){
                    Bundle bundle = data.getExtras();
                    Edit_ClientBack back = (Edit_ClientBack)bundle.getSerializable("editClientback");
                    initview(back);
                }
                break;


        }

    }


}
