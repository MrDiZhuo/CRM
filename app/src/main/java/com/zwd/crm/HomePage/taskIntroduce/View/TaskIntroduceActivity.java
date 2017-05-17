package com.zwd.crm.HomePage.taskIntroduce.View;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.zwd.crm.HomePage.reportContentTaskXinxi.View.ReportContent_TaskXinxiActivity;
import com.zwd.crm.HomePage.reportContextRengwu.Module.Report_context_RengwuGet;
import com.zwd.crm.HomePage.task.View.TaskActivity;
import com.zwd.crm.HomePage.taskIntroduce.Module.taskIntroduceElement;
import com.zwd.crm.HomePage.taskXinxi.View.TaskXinXiActivity;
import com.zwd.crm.R;
import com.zwd.crm.adapter.report_context_RengwuAdapter;
import com.zwd.crm.adapter.taskIntroduceAdapter;
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

public class TaskIntroduceActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.task_introduce_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.task_introduce_recycler)
    RecyclerView recyclerView;
    @Bind(R.id.task_introduce_refresh)
    SwipeRefreshLayout refreshLayout;

    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Task_ReturnTaskListByUsers.aspx";
    List<Report_context_RengwuGet> taskGet;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    String title="";
    int id;
    int usergroupId;
    Bundle bundle;
   // private List<taskIntroduceElement> affairElementList = new ArrayList<>();
    protected int getContentView() {
        return R.layout.activity_task_introduce;
    }

    @Override
    protected void initViews() {
        bundle = getIntent().getExtras();
        id=bundle.getInt("id");
        usergroupId=bundle.getInt("usergroupId");
        title=bundle.getString("title");
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
        actionBar.setTitle(title+"的任务");
        actionBar.setPriviousClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionBar.hideAdd();
    }


    /**
     * 调接口
     */
    public void btn_task(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Task_ReturnTaskListByUsers(id, baseUrl, url, new CustomCallBack<RemoteDataResult<List<Report_context_RengwuGet>>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<List<Report_context_RengwuGet>>> response) {
                ToastUtils.showShort(TaskIntroduceActivity.this,response.body().getResultMessage());
                taskGet = new ArrayList<Report_context_RengwuGet>(response.body().getData());
                initlist(taskGet);
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(TaskIntroduceActivity.this,message);
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
                bundle.putInt("sonnum",taskGet.get(position).getNum());
                goActivity(ReportContent_TaskXinxiActivity.class,bundle);
               // Toast.makeText(TaskIntroduceActivity.this, "Click:" +taskGet.get(position).getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(TaskIntroduceActivity.this, "LongClick:" + position , Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

}
