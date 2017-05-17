package com.zwd.crm.HomePage.taskJiegou.View;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.zwd.crm.HomePage.inform.Module.subjectdepartmentElement;
import com.zwd.crm.HomePage.reportContentTaskXinxi.View.ReportContent_TaskXinxiActivity;
import com.zwd.crm.HomePage.taskJiegou.Module.Task_JIegouGet;
import com.zwd.crm.HomePage.taskSon_XQ.View.TaskSon_XQActivity;
import com.zwd.crm.R;
import com.zwd.crm.adapter.WrapperExpandableListAdapter;
import com.zwd.crm.adapter.adjust_adapter;
import com.zwd.crm.adapter.task_JiegouAdapter;
import com.zwd.crm.base.BaseActivity;
import com.zwd.crm.interfaze.CommonAdapter;
import com.zwd.crm.internet.CustomCallBack;
import com.zwd.crm.internet.RemoteDataResult;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.layout.layout_action_bar;
import com.zwd.crm.util.ToastUtils;
import com.zwd.crm.widget.FloatingGroupExpandableListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit2.Response;

public class Task_JiegouActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.task_jiegou_action_bar)
    layout_action_bar actionBar;

    @Bind(R.id.task_jiegou_rv)
    RecyclerView recyclerView;
    @Bind(R.id.task_jiegou_refresh)
    SwipeRefreshLayout refreshLayout;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    int taskid;
    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Task_ReturnTaskStructureListByTask.aspx";
    List<Task_JIegouGet> task_jIegouGets;


    protected int getContentView(){
        return R.layout.activity_task__jiegou;
    }

    protected void initViews() {
        Bundle bundle = getIntent().getExtras();
        taskid = bundle.getInt("taskid");
        initActionBar(actionBar);
        btn_task_jiegou();
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
        task_jIegouGets.clear();
        btn_task_jiegou();
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


    private void initActionBar(layout_action_bar actionBar) {
        actionBar.setTitle("子任务结构");
        actionBar.setOnClickListener(new View.OnClickListener() {
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
    public void btn_task_jiegou(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Task_ReturnTaskStructureListByTask(taskid, baseUrl, url, new CustomCallBack<RemoteDataResult<List<Task_JIegouGet>>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<List<Task_JIegouGet>>> response) {
                ToastUtils.showShort(Task_JiegouActivity.this,response.body().getResultMessage());
                task_jIegouGets =new ArrayList<Task_JIegouGet>(response.body().getData());
                initrecycler(task_jIegouGets);
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(Task_JiegouActivity.this,message);

            }
        });
    }

    private void initrecycler(final List<Task_JIegouGet> task_jIegouGets) {
        task_JiegouAdapter conferenceAdapter = new task_JiegouAdapter(this,task_jIegouGets);
        recyclerView.setAdapter(conferenceAdapter);
        conferenceAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(mContext, "Click:" +task_jIegouGets.get(position).getTasktitle() , Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putInt("taskid",task_jIegouGets.get(position).getId());
                goActivity(TaskSon_XQActivity.class,bundle);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(mContext, "LongClick:" + position , Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }



}
