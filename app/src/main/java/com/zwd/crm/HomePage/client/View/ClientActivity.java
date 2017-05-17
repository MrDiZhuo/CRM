package com.zwd.crm.HomePage.client.View;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyg.dropdownmenu.DropDownMenu;
import com.hyg.dropdownmenu.DropDownMenuUtils;
import com.zwd.crm.HomePage.client.Controller.ClientItemDelegate;
import com.zwd.crm.HomePage.client.Module.ClientElement;
import com.zwd.crm.HomePage.client.Module.clientGet;
import com.zwd.crm.HomePage.client.Module.clientSearchPost;
import com.zwd.crm.HomePage.clientAdd.View.Client_AddActivity;
import com.zwd.crm.HomePage.clientCiangQing.View.Client_Xiang_QingActivity;
import com.zwd.crm.R;
import com.zwd.crm.adapter.ClientAdapter;
import com.zwd.crm.base.BaseActivity;
import com.zwd.crm.interfaze.CommonAdapter;
import com.zwd.crm.internet.CustomCallBack;
import com.zwd.crm.internet.RemoteDataResult;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.layout.layout_action_bar;
import com.zwd.crm.util.ToastUtils;
import com.zwd.crm.widget.ClearEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Response;

public class ClientActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.client_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.client_recycler)
    RecyclerView recyclerView;
    @Bind(R.id.client_search)
    ImageView search;
    @Bind(R.id.client_edit_search)
    ClearEditText editText;
    @Bind(R.id.client_cancle)
    TextView cancle;
    @Bind(R.id.client_refresh)
    SwipeRefreshLayout refreshLayout;

    private String baseUrl="http://139.224.164.183:8088/";
    private String url_return="Custom_ReturnCustomList.aspx";
    private String url_search="Custom_CustomSearch.aspx";
    private String url_delete="Custom_DeleteCustom.aspx";
    int usergroupid;
    public int createId;
    public List<clientGet> clientGets;
    String Customcontractstatus="";
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public ClientAdapter conferenceAdapter;
    protected int getContentView() {
        return R.layout.activity_client;
    }
    @Override
    protected void initViews() {
        Bundle bundle = getIntent().getExtras();
        usergroupid = bundle.getInt("usergroupid");
        createId=bundle.getInt("createId");
        initActionBar(actionBar);
        btn_client();


        /**
         * 下拉刷新
         */
        refreshLayout.setColorSchemeResources(R.color.dark);
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        refreshLayout.setProgressViewEndTarget(true, 100);
        refreshLayout.setOnRefreshListener(this);

    }
    //下拉刷新
    @Override
    public void onRefresh() {
        if(editText.getText().toString().isEmpty()){
            btn_client();
        }else {
            btn_clientSearch();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            conferenceAdapter.notifyDataSetChanged();
                            refreshLayout.setRefreshing(false);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void initActionBar(layout_action_bar actionBar) {
        actionBar.setPriviousClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionBar.setTitle("客户");
        actionBar.setImage(R.mipmap.add);
        actionBar.setAddClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("usergroupId",usergroupid);
                bundle.putInt("createId",createId);
                goActivity(Client_AddActivity.class,bundle);
            }
        });
    }


    /**
     * 删除客户
     */
    public void btn_deleteCustom(final int position){
        int post = clientGets.get(position).getId();
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Custom_DeleteCustom(post, baseUrl, url_delete, new CustomCallBack<RemoteDataResult>() {
            @Override
            public void onSuccess(Response<RemoteDataResult> response) {
                clientGets.remove(position);
                conferenceAdapter.notifyDataSetChanged();
                ToastUtils.showShort(ClientActivity.this,response.body().getResultMessage());
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(ClientActivity.this,message);
            }
        });
    }

    /**
     * 调接口
     */
    public void btn_client(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Custom_ReturnCustomList(usergroupid, baseUrl, url_return, new CustomCallBack<RemoteDataResult<List<clientGet>>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<List<clientGet>>> response) {
                clientGets = new ArrayList<clientGet>(response.body().getData());
                initrecycler(clientGets);

            }
            @Override
            public void onFail(String message) {
                Log.d("failure",message);
                ToastUtils.showShort(ClientActivity.this,message);
            }
        });
    }

    private void initrecycler(final List<clientGet> clientGets) {
        conferenceAdapter = new ClientAdapter(this,clientGets);
        recyclerView.setAdapter(conferenceAdapter);
        conferenceAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("title",clientGets.get(position));
                bundle.putString("Customcontractstatus",Customcontractstatus);
                goActivity(Client_Xiang_QingActivity.class,bundle);
                //  Toast.makeText(mContext, "Click:" +affairElementList.get(position).getName().toString() , Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(mContext, "LongClick:" + position , Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        conferenceAdapter.buttonSetOnclick(new ClientItemDelegate.ButtonInterface() {
            @Override
            public void onclick(View view, int position) {
                btn_deleteCustom(position);

            }
        });
    }

    /**
     * 搜索接口
     */
    public void btn_clientSearch(){
        clientSearchPost post = new clientSearchPost();
        post.setUsersgroupid(usergroupid);
        post.setCustomname(editText.getText().toString());
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Custom_CustomSearch(post, baseUrl, url_search, new CustomCallBack<RemoteDataResult<List<clientGet>>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<List<clientGet>>> response) {
                clientGets = new ArrayList<clientGet>(response.body().getData());
                initrecycler(clientGets);

            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(ClientActivity.this,message);
            }
        });
    }

    @OnClick(R.id.client_cancle)
    public void click(View v){
        switch (v.getId()){
            case R.id.client_cancle:
                if(editText.getText().toString().isEmpty()){
                    ToastUtils.showShort(ClientActivity.this,"请输入！");
                }else {
                    btn_clientSearch();
                }
                break;
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
