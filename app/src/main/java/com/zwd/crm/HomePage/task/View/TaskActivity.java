package com.zwd.crm.HomePage.task.View;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.zwd.crm.HomePage.reportContentTaskXinxi.View.ReportContent_TaskXinxiActivity;
import com.zwd.crm.HomePage.reportContextRengwu.Module.Report_context_RengwuGet;
import com.zwd.crm.HomePage.reportContextRengwu.View.ReportContext_RengwuActivity;
import com.zwd.crm.HomePage.taskAdd.View.TaskAddActivity;
import com.zwd.crm.HomePage.taskXinxi.View.TaskXinXiActivity;
import com.zwd.crm.R;
import com.zwd.crm.adapter.report_context_RengwuAdapter;
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

public class TaskActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.task_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.task_rv)
    RecyclerView recyclerView;
    @Bind(R.id.task_refresh)
    SwipeRefreshLayout refreshLayout;

    int usergroupid;
    int createId;
    String token;
    Bundle bundle;
    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Task_ReturnTaskListByUsers.aspx";
    List<Report_context_RengwuGet> taskGet;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    protected int getContentView() {
        return R.layout.activity_task;
    }

    @Override
    protected void initViews() {
        bundle = getIntent().getExtras();
        createId = bundle.getInt("createId");
        usergroupid=bundle.getInt("usergroupid");
        token=bundle.getString("token");
        initActionBar(actionBar);
        btn_task();
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
        taskGet.clear();
        btn_task();
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



    private void initActionBar(layout_action_bar actionBar) {
        actionBar.setPriviousClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionBar.setTitle("任务清单");
        actionBar.setImage(R.mipmap.add);
        actionBar.setAddClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("createId",createId);
                bundle.putInt("usergroupid",usergroupid);
                bundle.putString("token",token);
                goActivity(TaskAddActivity.class,bundle);
            }
        });
    }


    /**
     * 调接口
     */
    public void btn_task(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Task_ReturnTaskListByUsers(createId, baseUrl, url, new CustomCallBack<RemoteDataResult<List<Report_context_RengwuGet>>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<List<Report_context_RengwuGet>>> response) {
                ToastUtils.showShort(TaskActivity.this,response.body().getResultMessage());
                taskGet = new ArrayList<Report_context_RengwuGet>(response.body().getData());
                initlist(taskGet);
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(TaskActivity.this,message);
            }
        });
    }

    private void initlist(final List<Report_context_RengwuGet> taskGet) {
        report_context_RengwuAdapter conferenceAdapter = new report_context_RengwuAdapter(this,taskGet);
        recyclerView.setAdapter(conferenceAdapter);
        conferenceAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                bundle = new Bundle();
                bundle.putInt("taskid",taskGet.get(position).getId());
                bundle.putInt("usergroupid",usergroupid);

                bundle.putInt("createId",createId);
                bundle.putString("token",token);
                bundle.putInt("sonnum",taskGet.get(position).getNum());

                goActivity(TaskXinXiActivity.class,bundle);
                Toast.makeText(TaskActivity.this, "Click:" +taskGet.get(position).getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(TaskActivity.this, "LongClick:" + position , Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }


}
