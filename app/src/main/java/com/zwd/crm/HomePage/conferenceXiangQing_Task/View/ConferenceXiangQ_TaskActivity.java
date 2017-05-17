package com.zwd.crm.HomePage.conferenceXiangQing_Task.View;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.zwd.crm.HomePage.reportContentTaskXinxi.View.ReportContent_TaskXinxiActivity;
import com.zwd.crm.HomePage.reportContextRengwu.Module.Report_context_RengwuGet;
import com.zwd.crm.HomePage.reportContextRengwu.View.ReportContext_RengwuActivity;
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

public class ConferenceXiangQ_TaskActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.conference_xiang_q__task_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.conference_xiang_q__task_refresh)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.conference_xiang_q__task_rv)
    RecyclerView recyclerView;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    int id;
    Bundle bundle;
    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Task_ReturnTaskListByExhibition.aspx";
    List<Report_context_RengwuGet> reportContextRengwuGets;
    protected int getContentView() {
        return R.layout.activity_conference_xiang_q__task;
    }

    @Override
    protected void initViews() {
        bundle = getIntent().getExtras();
        id=bundle.getInt("id");
        initActionBar(actionBar);
        btn_conferenceTask();
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
        reportContextRengwuGets.clear();
        btn_conferenceTask();
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
        actionBar.hideAdd();
    }

    /**
     * 调接口
     */
    public void btn_conferenceTask(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Task_ReturnTaskListByExhibition(id, baseUrl, url, new CustomCallBack<RemoteDataResult<List<Report_context_RengwuGet>>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<List<Report_context_RengwuGet>>> response) {
                ToastUtils.showShort(ConferenceXiangQ_TaskActivity.this,response.body().getResultMessage());
                reportContextRengwuGets = new ArrayList<Report_context_RengwuGet>(response.body().getData());
                initrecycler(reportContextRengwuGets);
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(ConferenceXiangQ_TaskActivity.this,message);

            }
        });
    }

    private void initrecycler(final List<Report_context_RengwuGet> reportContextRengwuGets) {
        report_context_RengwuAdapter conferenceAdapter = new report_context_RengwuAdapter(this,reportContextRengwuGets);
        recyclerView.setAdapter(conferenceAdapter);
        conferenceAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                bundle = new Bundle();
                bundle.putInt("taskid",reportContextRengwuGets.get(position).getId());
                bundle.putInt("sonnum",reportContextRengwuGets.get(position).getNum());
                goActivity(ReportContent_TaskXinxiActivity.class,bundle);
                Toast.makeText(mContext, "Click:" +reportContextRengwuGets.get(position).getName() , Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(mContext, "LongClick:" + position , Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

}
