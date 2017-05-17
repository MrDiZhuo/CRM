package com.zwd.crm.HomePage.reportSend.View;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.zwd.crm.HomePage.report.View.ReportActivity;
import com.zwd.crm.HomePage.reportSend.Module.Report_SendGet;
import com.zwd.crm.R;
import com.zwd.crm.adapter.report_SendAdapter;
import com.zwd.crm.base.BaseFragment;
import com.zwd.crm.interfaze.CommonAdapter;
import com.zwd.crm.internet.CustomCallBack;
import com.zwd.crm.internet.RemoteDataResult;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit2.Response;

public class Report_SendFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.report_send_rv)
    RecyclerView recyclerView;
    @Bind(R.id.report_send_refresh)
    SwipeRefreshLayout refreshLayout;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    int id;
    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Report_ReturnReportListByReport.aspx";
    private String url_delete="Report_DeleteReport.aspx";

    String heading;
    Context mContext;
    report_SendAdapter conferenceAdapter;
    private List<Report_SendGet> report_sendGets;

    protected int getContentViewID() {
        return R.layout.fragment_report__send;
    }


    @Override
    protected void initViews(View rootView) {
        mContext = getContext();
        id=((ReportActivity)getActivity()).createId;
        heading=((ReportActivity)getActivity()).heading;

        btn_report_send();
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
        report_sendGets.clear();
        btn_report_send();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // conferenceAdapter.notifyDataSetChanged();
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
     * 删除
     */
    public void btn_delete(final int position){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Report_DeleteReport(report_sendGets.get(position).getId(), baseUrl, url_delete, new CustomCallBack<RemoteDataResult>() {
            @Override
            public void onSuccess(Response<RemoteDataResult> response) {
                report_sendGets.remove(position);
                conferenceAdapter.notifyDataSetChanged();
                ToastUtils.showShort(mContext,response.body().getResultMessage());
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(mContext,message);

            }
        });
    }


    /**
     * diaojiekou
     */
    public void btn_report_send(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Report_ReturnReportListByReport(id, baseUrl, url, new CustomCallBack<RemoteDataResult<List<Report_SendGet>>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<List<Report_SendGet>>> response) {
                ToastUtils.showShort(mContext,response.body().getResultMessage());
                report_sendGets = new ArrayList<Report_SendGet>(response.body().getData());
                for(int i=0;i<report_sendGets.size();i++){
                    report_sendGets.get(i).setResourceId(heading);
                }
                initrecycler(report_sendGets);
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(mContext,message);
            }
        });
    }

    private void initrecycler(final List<Report_SendGet> report_sendGets) {
        conferenceAdapter = new report_SendAdapter(mContext,report_sendGets);
        recyclerView.setAdapter(conferenceAdapter);
        conferenceAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                ToastUtils.showShort(mContext,report_sendGets.get(position).getReportdate());
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("是否删除？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        btn_delete(position);
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
                return false;
            }
        });
    }
}
