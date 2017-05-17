package com.zwd.crm.HomePage.affair.View;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zwd.crm.HomePage.addAffair.View.AddAffairActivity;
import com.zwd.crm.HomePage.affair.Module.affairGet;
import com.zwd.crm.HomePage.affairXiangQing.View.Affair_XiangQingActivity;
import com.zwd.crm.R;
import com.zwd.crm.adapter.affairAdapter;
import com.zwd.crm.adapter.conferenceAdapter;
import com.zwd.crm.base.BaseFragment;
import com.zwd.crm.interfaze.CommonAdapter;
import com.zwd.crm.internet.CustomCallBack;
import com.zwd.crm.internet.RemoteDataResult;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.layout.TabViewPager;
import com.zwd.crm.layout.layout_action_bar;
import com.zwd.crm.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit2.Response;


public class
affairFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.affair_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.affair_recycler)
    RecyclerView recyclerView;
    @Bind(R.id.affair_refresh)
    SwipeRefreshLayout refreshLayout;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    affairAdapter conferenceAdapter;


    protected int getContentViewID() {
        return R.layout.fragment_affair;
    }

    Context context;


    public int userid;
    private int usersgroupid;
    private String heading="";
    private String nickname="";
    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Notice_ReturnNoticeListShow.aspx";
    private String url_delete="Notice_DeleteNotice.aspx";
    public List<affairGet> affairGetList;

    @Override
    protected void initViews(View rootView) {

        context = getContext();
        initActionBar(actionBar);

        userid=((TabViewPager) getActivity()).login_Get.getId();
        usersgroupid=((TabViewPager) getActivity()).login_Get.getUserOrganization().getUsersgroupid();
        heading = ((TabViewPager) getActivity()).login_Get.getHeading();
        nickname=((TabViewPager) getActivity()).login_Get.getNickname();
        btn_affair();



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
        affairGetList.clear();
        btn_affair();
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
        actionBar.setTitle("事务");
        actionBar.hideBack();
        actionBar.setImage(R.mipmap.add);
        actionBar.setAddClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("userId",userid);
                bundle.putInt("usersgroupid",usersgroupid);
                goActivity(AddAffairActivity.class,bundle);
            }
        });
    }


    /**
     * 删除事务
     */
    public void btn_deleteAffair(final int position){
        int post = affairGetList.get(position).getId();
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Notice_DeleteNotice(post, baseUrl, url_delete, new CustomCallBack<RemoteDataResult>() {
            @Override
            public void onSuccess(Response<RemoteDataResult> response) {
                affairGetList.remove(position);
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
    public void btn_affair(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Notice_ReturnNoticeListShow(userid, baseUrl, url, new CustomCallBack<RemoteDataResult<List<affairGet>>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<List<affairGet>>> response) {
                ToastUtils.showShort(context,response.body().getResultMessage());
                affairGetList = new ArrayList<affairGet>(response.body().getData());
                initrecycler(affairGetList);


            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(context,message);
                Log.d("failure",message);
            }
        });
    }

    private void initrecycler(final List<affairGet> affairGetList) {
        conferenceAdapter = new affairAdapter(context,affairGetList);
        recyclerView.setAdapter(conferenceAdapter);
        conferenceAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("id",affairGetList.get(position).getId());
                bundle.putString("img",affairGetList.get(position).getHeading());
                bundle.putString("name",affairGetList.get(position).getName());
                if(affairGetList.get(position).getDate()!=null){
                    bundle.putString("date",affairGetList.get(position).getDate().replace("T"," "));
                }

                bundle.putString("state",affairGetList.get(position).getNoticetype());
                bundle.putString("txt",affairGetList.get(position).getContent());
                bundle.putInt("sure",affairGetList.get(position).getConfirmcount());
                bundle.putInt("num",affairGetList.get(position).getAnswercount());
                bundle.putInt("parentid",affairGetList.get(position).getCreatid());
                bundle.putInt("userid",userid);
                bundle.putString("heading",heading);
                bundle.putString("nickname",nickname);

                goActivity(Affair_XiangQingActivity.class,bundle);

                //   Toast.makeText(mContext, "Click:" +affairElementList.get(position).getName().toString() , Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("是否删除？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        btn_deleteAffair(position);
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
