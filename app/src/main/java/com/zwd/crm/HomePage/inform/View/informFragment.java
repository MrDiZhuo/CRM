package com.zwd.crm.HomePage.inform.View;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.zwd.crm.HomePage.inform.Controller.informItemDelegate;
import com.zwd.crm.HomePage.inform.Module.InformListGet;
import com.zwd.crm.R;
import com.zwd.crm.adapter.informAdapter;
import com.zwd.crm.base.BaseFragment;
import com.zwd.crm.interfaze.CommonAdapter;
import com.zwd.crm.internet.CustomCallBack;
import com.zwd.crm.internet.RemoteDataResult;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.layout.TabViewPager;
import com.zwd.crm.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit2.Response;


public class informFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.inform_refresh)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.inform_recycler)
    RecyclerView recyclerView;

    private int usergroupId;
    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Department_ReturnDefaultDepartmentUsers.aspx";
    private String url_delete="Department_DeleteDefaultDepartmentUsers.aspx";
     Context context;
    informAdapter conferenceAdapter;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    List<InformListGet> informListGetList;
    protected int getContentViewID() {
        return R.layout.fragment_inform;
    }

    @Override
    protected void initViews(View rootView) {
        context = getContext();
        usergroupId =((TabViewPager)getActivity()).login_Get.getUserOrganization().getUsersgroupid();

        btn_inform();
     //   initlist();

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
        informListGetList.clear();
        btn_inform();
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
     * 获取列表接口
     */
    public void btn_inform(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Department_ReturnDefaultDepartmentUsers(usergroupId, baseUrl, url, new CustomCallBack<RemoteDataResult<List<InformListGet>>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<List<InformListGet>>> response) {
                informListGetList=new ArrayList<InformListGet>(response.body().getData());
                initrecycler(informListGetList);
                ToastUtils.showShort(context,response.body().getResultMessage());
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(mContext,message);
            }
        });
    }

    private void initrecycler(List<InformListGet> informListGetList) {
        conferenceAdapter= new informAdapter(context,informListGetList);
        recyclerView.setAdapter(conferenceAdapter);
        conferenceAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(mContext, "LongClick:" + position , Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        conferenceAdapter.buttonSetOnclick(new informItemDelegate.ButtonInterface() {
            @Override
            public void onclick(View view, int position) {
                btn_delete(position);

            }
        });
    }

    /**
     * 删除
     */
    public void btn_delete(final int position){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Department_DeleteDefaultDepartmentUsers(informListGetList.get(position).getId(), baseUrl, url_delete, new CustomCallBack<RemoteDataResult>() {
            @Override
            public void onSuccess(Response<RemoteDataResult> response) {
                informListGetList.remove(position);
                conferenceAdapter.notifyDataSetChanged();
                ToastUtils.showShort(context,response.body().getResultMessage());
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(context,message);

            }
        });
    }


}
