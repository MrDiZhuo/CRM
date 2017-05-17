package com.zwd.crm.HomePage.chengyuanList.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zwd.crm.HomePage.adjust.View.AdjustBumenActivity;
import com.zwd.crm.HomePage.chengyuan.View.ChengYuanInformActivity;
import com.zwd.crm.HomePage.chengyuanList.Module.DeleteUserFromDepartmentPost;
import com.zwd.crm.HomePage.choosePerson.Controller.ChooseTitleItemDecoration;
import com.zwd.crm.HomePage.choosePerson.Module.ChoosePersonGet;
import com.zwd.crm.HomePage.inform.Module.subjectdepartmentElement;
import com.zwd.crm.internet.CustomCallBack;
import com.zwd.crm.internet.RemoteDataResult;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.util.ToastUtils;
import com.zwd.crm.HomePage.chengyuanList.Controller.ItemRecyclerView;
import com.zwd.crm.R;
import com.zwd.crm.adapter.MyAdapter;
import com.zwd.crm.base.BaseActivity;
import com.zwd.crm.interfaze.OnItemClickListener;
import com.zwd.crm.layout.layout_action_bar;
import com.zwd.crm.widget.IndexBar;

import java.util.List;

import butterknife.Bind;
import retrofit2.Response;

public class chengYuanActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.chengyuan_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.rv)
    ItemRecyclerView mRv;
    @Bind(R.id.indexBar)
    IndexBar mIndexBar;
    @Bind(R.id.tvSideBarHint)
    TextView mTvSideBarHint;
    @Bind(R.id.chengyuan_refresh)
    SwipeRefreshLayout refreshLayout;

    private static final String TAG = "zxt";
    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Department_ReturnUsersListByDepartmet.aspx";
    private String url_delete="Department_DeleteUsersForDepartment.aspx";
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private LinearLayoutManager mManager;


    subjectdepartmentElement data;
    int usergroupId;
    MyAdapter mAdapter;

    List<ChoosePersonGet> ChoosePersonGetList;


    private ChooseTitleItemDecoration mDecoration ;

    protected int getContentView(){
        return R.layout.activity_cheng_yuan;
    }

    protected void initViews() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        data = (subjectdepartmentElement)bundle.getSerializable("title");
        usergroupId = bundle.getInt("usergroupId");
        btn_chengyuan();
        initActionBar(actionBar);

        mRv.setLayoutManager(mManager = new LinearLayoutManager(this));

        /**
         * 下拉刷新
         */
        refreshLayout.setColorSchemeResources(R.color.dark);
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        refreshLayout.setProgressViewEndTarget(true, 100);
        refreshLayout.setOnRefreshListener(this);
    }
    public void IndexView(){

    }
    //下拉刷新
    @Override
    public void onRefresh() {
        mRv.removeItemDecoration(mDecoration);
        ChoosePersonGetList.clear();
        btn_chengyuan();
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
    /**
     * 删除用户
     */
    public void btn_deleteUser(final int position) {
        DeleteUserFromDepartmentPost post = new DeleteUserFromDepartmentPost();
        post.setUserid(ChoosePersonGetList.get(position).getId());
            RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
            remoteOptionIml.Department_DeleteUsersForDepartment(post, baseUrl, url_delete, new CustomCallBack<RemoteDataResult>() {
                @Override
                public void onSuccess(Response<RemoteDataResult> response) {
                    mAdapter.removeItem(position);
                    ToastUtils.showShort(chengYuanActivity.this, "12" + response.body().getResultMessage());

                }

                @Override
                public void onFail(String message) {
                    ToastUtils.showShort(chengYuanActivity.this, "111" + message);
                    Log.d("ermessage", message);
                }
            });

    }
    /**
     * 调接口
     */
    public void btn_chengyuan(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Department_ReturnUsersListByDepartmet(data.getDepartmentid(), baseUrl, url, new CustomCallBack<RemoteDataResult<List<ChoosePersonGet>>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<List<ChoosePersonGet>>> response) {
                ToastUtils.showShort(chengYuanActivity.this,response.body().getResultMessage());
                ChoosePersonGetList=response.body().getData();
                initrecycler(ChoosePersonGetList);
                IndexView();
            }
            @Override
            public void onFail(String message) {
                ToastUtils.showShort(chengYuanActivity.this,message);
            }
        });
    }

    private void initrecycler(final List<ChoosePersonGet> choosePersonGetList) {
        mRv.addItemDecoration(mDecoration= new ChooseTitleItemDecoration(this,choosePersonGetList));
        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
                .setmSourceDatas(choosePersonGetList)
                .invalidate();//设置数据源
        mIndexBar.setVisibility(View.VISIBLE);
        mAdapter = new MyAdapter(this, choosePersonGetList);
        mRv.setAdapter(mAdapter);
        mRv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("usergroupId",usergroupId);
                bundle.putString("title",choosePersonGetList.get(position).getName().toString());
                bundle.putInt("id",choosePersonGetList.get(position).getId());
                goActivity(ChengYuanInformActivity.class,bundle);
            }
            @Override
            public void onDeleteClick(int position) {
                btn_deleteUser(position);
            }
            @Override
            public void onAdjust(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("title", choosePersonGetList.get(position).getName().toString());
                bundle.putInt("usergroupId",usergroupId);
                bundle.putInt("usersid",choosePersonGetList.get(position).getId());
                bundle.putInt("departmentoldid",data.getDepartmentid());
                goActivity(AdjustBumenActivity.class,bundle);
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
        actionBar.setTitle(data.getDepartmentname());
        actionBar.hideAdd();
    }

}
