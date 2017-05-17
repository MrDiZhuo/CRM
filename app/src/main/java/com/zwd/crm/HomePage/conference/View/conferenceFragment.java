package com.zwd.crm.HomePage.conference.View;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zwd.crm.HomePage.conference.Module.conferenceGet;
import com.zwd.crm.HomePage.conferenceAdd.View.ConfereceAddActivity;
import com.zwd.crm.HomePage.conferenceXiangqing.View.Conference_XiangqingActivity;
import com.zwd.crm.HomePage.login.Module.loginGet;
import com.zwd.crm.R;
import com.zwd.crm.adapter.MultiItemTypeAdapter;
import com.zwd.crm.adapter.conferenceAdapter;
import com.zwd.crm.base.BaseActivity;
import com.zwd.crm.base.BaseAppManager;
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

public class conferenceFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.conference_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.conference_recycler)
    RecyclerView recyclerView;
    @Bind(R.id.conference_refresh)
    SwipeRefreshLayout refreshLayout;

    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Exhibition_ReturnExhibitionList.aspx";

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    conferenceAdapter conferenceAdapter;
    private int usersgroupid;
    private int userid;
    String state="";
    List<conferenceGet> conferenceGetList;

    protected int getContentViewID() {
        return R.layout.fragment_conference;
    }
    private Context context;
    @Override
    protected void initViews(View rootView) {
        context = getContext();
        initActionBar(actionBar);


        /**
         * userid：7
         * 应该是1
         */
        usersgroupid=((TabViewPager)getActivity()).login_Get.getUserOrganization().getUsersgroupid();
        Log.d("getId:",((TabViewPager)getActivity()).login_Get.getPassword());
        userid=((TabViewPager) getActivity()).login_Get.getId();
        //ToastUtils.showShort(context,userid+" ");
        btn_conference();


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
        conferenceGetList.clear();
        btn_conference();
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

    ///初始化actionbar
    private void initActionBar(layout_action_bar actionBar) {
        actionBar.hideBack();
        actionBar.setTitle("会展");
        actionBar.setImage(R.mipmap.add);
        actionBar.setAddClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("usersgroupid",usersgroupid);
                goActivity(ConfereceAddActivity.class,bundle);
            }
        });
    }

    /**
     * 调接口
     */
    public void btn_conference(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Exhibition_ReturnExhibitionList(userid, baseUrl, url, new CustomCallBack<RemoteDataResult<List<conferenceGet>>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<List<conferenceGet>>> response) {
                conferenceGetList = new ArrayList<conferenceGet>(response.body().getData());
                initrecycler(conferenceGetList);
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(context,message);
            }
        });
    }

    private void initrecycler(final List<conferenceGet> conferenceGetList) {
        conferenceAdapter= new conferenceAdapter(context,conferenceGetList);
        recyclerView.setAdapter(conferenceAdapter);
        conferenceAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Bundle bundle = new Bundle();
                // bundle.putString("title",conferenceElementList.get(position).getTitle().toString());
                bundle.putInt("Listid",conferenceGetList.get(position).getId());
                bundle.putString("name",conferenceGetList.get(position).getExhibitionname());
                bundle.putString("startdate",conferenceGetList.get(position).getStartdate());
                bundle.putString("enddate",conferenceGetList.get(position).getEnddate());
                bundle.putString("address",conferenceGetList.get(position).getAddress());
                bundle.putString("description",conferenceGetList.get(position).getDescription());
                if(conferenceGetList.get(position).getExhibitionstatus()==0){
                    state="筹备中";
                }else if(conferenceGetList.get(position).getExhibitionstatus()==1){
                    state="展览中";
                }else if(conferenceGetList.get(position).getExhibitionstatus()==2){
                    state="整理中";
                }else if(conferenceGetList.get(position).getExhibitionstatus()==3){
                    state="完结";
                }
                bundle.putString("state",state);
                goActivity(Conference_XiangqingActivity.class,bundle);
                //  Toast.makeText(mContext, "Click:" +conferenceElementList.get(position).getTitle().toString() , Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(mContext, "LongClick:" + position , Toast.LENGTH_SHORT).show();
                return false;
            }
        });


    }

}
