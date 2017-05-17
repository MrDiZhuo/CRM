package com.zwd.crm.HomePage.choosePerson.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import com.zwd.crm.HomePage.choosePerson.Controller.ChooseTitleItemDecoration;
import com.zwd.crm.HomePage.choosePerson.Module.ChoosePersonElement;
import com.zwd.crm.HomePage.choosePerson.Module.ChoosePersonGet;
import com.zwd.crm.R;
import com.zwd.crm.adapter.ChoosePersonAdapter;
import com.zwd.crm.adapter.MultiItemTypeAdapter;
import com.zwd.crm.base.BaseActivity;
import com.zwd.crm.internet.CustomCallBack;
import com.zwd.crm.internet.RemoteDataResult;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.layout.layout_action_bar;
import com.zwd.crm.util.ToastUtils;
import com.zwd.crm.widget.DividerItemDecoration;
import com.zwd.crm.widget.IndexBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit2.Response;

public class ChoosePersonActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.choose_personaction_bar)
    layout_action_bar actionBar;
    @Bind(R.id.choose_person_rv)
    RecyclerView mRv;
    @Bind(R.id.choose_person_indexBar)
    IndexBar mIndexBar;
    @Bind(R.id.choose_person_tvSideBarHint)
    TextView mTvSideBarHint;
    @Bind(R.id.choose_person_refresh)
    SwipeRefreshLayout refreshLayout;



    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    ChoosePersonAdapter mAdapter;

    private static final String TAG = "zxt";
    private LinearLayoutManager mManager;


    private ChooseTitleItemDecoration mDecoration ;
    String person="";
    int Id;
    int usersgroupid;

    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Users_ReturnUserList.aspx";
    List<ChoosePersonGet> ChoosePersonGetList;


    protected int getContentView() {
        return R.layout.activity_choose_person;
    }
    @Override
    protected void initViews() {
        Bundle bundle = getIntent().getExtras();
        usersgroupid=bundle.getInt("usersgroupid");
        initActionBar(actionBar);
        btn_choosePerson();


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
        mRv.removeItemDecoration(mDecoration);
        ChoosePersonGetList.clear();
        btn_choosePerson();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.setRefreshing(false);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void IndexView(){
        mRv.addItemDecoration(mDecoration= new ChooseTitleItemDecoration(this,ChoosePersonGetList));
        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
                .setmSourceDatas(ChoosePersonGetList)
                .invalidate();//设置数据源
        mIndexBar.setVisibility(View.VISIBLE);

    }

    private void initActionBar(layout_action_bar actionBar) {
        actionBar.setPriviousClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =getIntent();
                intent1.putExtra("person",person);
                intent1.putExtra("personId",Id);
                ChoosePersonActivity.this.setResult(0,intent1);

                ChoosePersonActivity.this.finish();
            }
        });
        actionBar.setTitle("选择目标");
        actionBar.hideAdd();
    }



    /**
     * 调接口
     */
    public void btn_choosePerson(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Users_ReturnUserList(usersgroupid,baseUrl, url, new CustomCallBack<RemoteDataResult<List<ChoosePersonGet>>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<List<ChoosePersonGet>>> response) {
                ToastUtils.showShort(ChoosePersonActivity.this,response.body().getResultMessage());
                ChoosePersonGetList=response.body().getData();
                initrecycler(ChoosePersonGetList);
                IndexView();
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(ChoosePersonActivity.this,message);
                Log.d("failure:",message);
            }
        });
    }

    private void initrecycler(final List<ChoosePersonGet> choosePersonGetList) {
        mRv.setLayoutManager(mManager = new LinearLayoutManager(this));
        mAdapter = new ChoosePersonAdapter(this, choosePersonGetList);
        mRv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("目标为"+choosePersonGetList.get(position).getName());
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        person =choosePersonGetList.get(position).getName();
                        for(int i=0;i<choosePersonGetList.size();i++){
                            if(choosePersonGetList.get(i).getName().equals(person)){
                                Id=choosePersonGetList.get(i).getId();
                            }
                        }
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    /**
     *键盘返回
     */
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent =getIntent();
            intent.putExtra("person",person);
            intent.putExtra("personId",Id);
            ChoosePersonActivity.this.setResult(0,intent);

           /* Intent intent2 =getIntent();
            intent2.putExtra("person",person);
            intent2.putExtra("personId",Id);
            ChoosePersonActivity.this.setResult(2,intent2);*/

            ChoosePersonActivity.this.finish();
        }
        return true;
    }

}
