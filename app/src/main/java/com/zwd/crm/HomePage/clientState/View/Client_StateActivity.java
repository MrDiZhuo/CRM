package com.zwd.crm.HomePage.clientState.View;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.zwd.crm.HomePage.clientState.Module.Client_StateGet;
import com.zwd.crm.HomePage.conference.Module.conferenceGet;
import com.zwd.crm.R;
import com.zwd.crm.adapter.Client_StateAdapter;
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

public class Client_StateActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.client_state_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.client_state_rv)
    RecyclerView recyclerView;
    @Bind(R.id.client_state_refresh)
    SwipeRefreshLayout refreshLayout;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    int id;
    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Exhibition_ReturnCustomDetailByExhibition.aspx";
    List<Client_StateGet> gets=new ArrayList<>();
    private List<Client_StateGet> conferenceElementList = new ArrayList<>();
    Client_StateAdapter conferenceAdapter;
    protected int getContentView() {
        return R.layout.activity_client__state;
    }

    @Override
    protected void initViews() {
        Bundle bundle = getIntent().getExtras();
        id=bundle.getInt("id");
        initActionBar(actionBar);
        btn_ClientState();

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
        conferenceElementList.clear();
        btn_ClientState();
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

    private void initView(){
        conferenceAdapter = new Client_StateAdapter(this,conferenceElementList);
        recyclerView.setAdapter(conferenceAdapter);
        conferenceAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(mContext, "Click:" +conferenceElementList.get(position).getName().toString() , Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(mContext, "LongClick:" + position , Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }


    private void initlist(List<Client_StateGet> gets) {
        String state="";
        for(int i=0;i<gets.size();i++){
            if(gets.get(i).getState()==0){
                state="初始客户";
            }else if(gets.get(i).getState()==1){
                state="已签约客户";
            }else if(gets.get(i).getState()==2){
                state="无意向客户";
            }else if(gets.get(i).getState()==3){
                state="有意向客户";
            }
            Client_StateGet party1 = new Client_StateGet(gets.get(i).getName(),state);
            conferenceElementList.add(party1);
        }

    }

    private void initActionBar(layout_action_bar actionBar) {
        actionBar.setPriviousClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionBar.setTitle("客户状态");
        actionBar.hideAdd();
    }

    /**
     * 调接口
     */
    public void btn_ClientState(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Exhibition_ReturnCustomDetailByExhibition(id, baseUrl, url, new CustomCallBack<RemoteDataResult<List<Client_StateGet>>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<List<Client_StateGet>>> response) {
                gets = new ArrayList<Client_StateGet>(response.body().getData());
                initlist(gets);
                initView();
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(Client_StateActivity.this,message);
            }
        });
    }


}
