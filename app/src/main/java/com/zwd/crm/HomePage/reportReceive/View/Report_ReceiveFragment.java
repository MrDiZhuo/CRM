package com.zwd.crm.HomePage.reportReceive.View;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.zwd.crm.HomePage.report.View.ReportActivity;
import com.zwd.crm.HomePage.reportContext.View.report_ContextActivity;
import com.zwd.crm.HomePage.reportReceive.Module.Report_receiveGet;
import com.zwd.crm.R;
import com.zwd.crm.adapter.report_receiveAdapter;
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


public class Report_ReceiveFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.report_receive_rv)
    RecyclerView recyclerView;
    @Bind(R.id.report_receive_refresh)
    SwipeRefreshLayout refreshLayout;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    Context mContext;

    report_receiveAdapter conferenceAdapter;
    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Report_ReturnReportListByReceive.aspx";
    private String url_delete="Report_DeleteReport.aspx";
    int id;
    private List<Report_receiveGet> affairElementList;
    protected int getContentViewID() {
        return R.layout.fragment_report__receive;
    }


    @Override
    protected void initViews(View rootView) {
        mContext = getContext();
        id=((ReportActivity)getActivity()).createId;

        btn_receive();
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
        affairElementList.clear();
        btn_receive();
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
        remoteOptionIml.Report_DeleteReport(affairElementList.get(position).getId(), baseUrl, url_delete, new CustomCallBack<RemoteDataResult>() {
            @Override
            public void onSuccess(Response<RemoteDataResult> response) {
                affairElementList.remove(position);
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
     * 调接口
     */
    public void btn_receive(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Report_ReturnReportListByReceive(id, baseUrl, url, new CustomCallBack<RemoteDataResult<List<Report_receiveGet>>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<List<Report_receiveGet>>> response) {
                ToastUtils.showShort(mContext,response.body().getResultMessage());
                affairElementList = new ArrayList<Report_receiveGet>(response.body().getData());
                initrecycler(affairElementList);
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(mContext,message);
            }
        });
    }

    public void initrecycler(final List<Report_receiveGet> list){
        conferenceAdapter = new report_receiveAdapter(mContext,list);
        recyclerView.setAdapter(conferenceAdapter);
        conferenceAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Bundle bundle =new Bundle();
                bundle.putInt("id",list.get(position).getId());
                bundle.putString("title",list.get(position).getState()+"报");
                goActivity(report_ContextActivity.class,bundle);
                //              Toast.makeText(mContext, "Click:" +affairElementList.get(position).getName().toString() , Toast.LENGTH_SHORT).show();
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
