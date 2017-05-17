package com.zwd.crm.HomePage.reportContextRengwu.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.zwd.crm.HomePage.choosePerson.View.ChoosePersonActivity;
import com.zwd.crm.HomePage.reportContextRengwu.Module.Report_context_RengwuGet;
import com.zwd.crm.R;
import com.zwd.crm.adapter.affairAdapter;
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

public class ReportContext_RengwuActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.report_context_rengwu_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.report_context_rengwu_rv)
    RecyclerView recyclerView;
    @Bind(R.id.report_context_rengwu_refresh)
    SwipeRefreshLayout refreshLayout;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Task_ReturnTaskListByUsers.aspx";
    int id ;
    Intent intent;
    List<Report_context_RengwuGet> reportContextRengwuGets;
    int back=0;


    protected int getContentView() {
        return R.layout.activity_report_context__rengwu;
    }

    @Override
    protected void initViews() {
        intent = getIntent();
        Bundle bundle =intent.getExtras();
        id=bundle.getInt("id");
        initActionBar(actionBar);
        btn_report_RengWu();

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
        btn_report_RengWu();
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
                intent.putExtra("taskId",back);
                ReportContext_RengwuActivity.this.setResult(2,intent);
                ReportContext_RengwuActivity.this.finish();
            }
        });
        actionBar.setTitle("选择相关任务");
        actionBar.hideAdd();
    }


    /**
     *键盘返回
     */
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            intent.putExtra("taskId",back);
            ReportContext_RengwuActivity.this.setResult(2,intent);
            ReportContext_RengwuActivity.this.finish();
        }
        return true;
    }
    /**
     * 调接口
     */
    public void btn_report_RengWu(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Task_ReturnTaskListByUsers(id, baseUrl, url, new CustomCallBack<RemoteDataResult<List<Report_context_RengwuGet>>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<List<Report_context_RengwuGet>>> response) {
                ToastUtils.showShort(ReportContext_RengwuActivity.this,response.body().getResultMessage());
                reportContextRengwuGets = new ArrayList<Report_context_RengwuGet>(response.body().getData());
                initlist(reportContextRengwuGets);
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(ReportContext_RengwuActivity.this,message);
            }
        });
    }

    private void initlist(final List<Report_context_RengwuGet> reportContextRengwuGets) {
        report_context_RengwuAdapter conferenceAdapter = new report_context_RengwuAdapter(this,reportContextRengwuGets);
        recyclerView.setAdapter(conferenceAdapter);
        conferenceAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("选择任务："+reportContextRengwuGets.get(position).getTitle().toString());
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        back=reportContextRengwuGets.get(position).getId();
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
                //      Toast.makeText(mContext, "Click:" +affairElementList.get(position).getName().toString() , Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(mContext, "LongClick:" + position , Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

}
