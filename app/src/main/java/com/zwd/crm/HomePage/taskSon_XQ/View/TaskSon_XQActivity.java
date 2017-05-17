package com.zwd.crm.HomePage.taskSon_XQ.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.loveplusplus.demo.image.ImagePagerActivity;
import com.zwd.crm.HomePage.reportContentTaskXinxi.Module.ReportContent_TaskXinxiGet;
import com.zwd.crm.HomePage.reportContentTaskXinxi.Module.Tasklanchuserslist;
import com.zwd.crm.HomePage.reportContentTaskXinxi.View.ReportContent_TaskXinxiActivity;
import com.zwd.crm.HomePage.taskJiegou.View.Task_JiegouActivity;
import com.zwd.crm.R;
import com.zwd.crm.adapter.NoScrollGridAdapter;
import com.zwd.crm.base.BaseActivity;
import com.zwd.crm.internet.CustomCallBack;
import com.zwd.crm.internet.RemoteDataResult;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.layout.layout_action_bar;
import com.zwd.crm.util.ToastUtils;
import com.zwd.crm.widget.NoScrollGridView;

import java.util.List;

import butterknife.Bind;
import retrofit2.Response;

public class TaskSon_XQActivity extends BaseActivity {

    @Bind(R.id.task_son__xq_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.task_son__xq_tasktitle)
    TextView txt_tasktitle;
    @Bind(R.id.task_son__xq_taskstate)
    TextView txt_taskstate;
    @Bind(R.id.task_son__xq_taskpublish)
    TextView txt_taskpublish;
    @Bind(R.id.task_son__xq_taskset)
    TextView txt_taskset;
    @Bind(R.id.task_son__xq_taskstart)
    TextView txt_taskstart;
    @Bind(R.id.task_son__xq_tasktype)
    TextView txt_tasktype;
    @Bind(R.id.task_son__xq_taskcontent)
    TextView txt_taskcontent;
    @Bind(R.id.task_son__xq_grid_1)
    NoScrollGridView picture_1;
    @Bind(R.id.task_son__xq_taskcreator)
    TextView txt_taskcreater;
    @Bind(R.id.task_son__xq_tasklaunchtype)
    TextView txt_tasklaunchtype;
    @Bind(R.id.task_son__xq_tasklaunchor)
    TextView txt_tasklaunchor;
    @Bind(R.id.task_son__xq_taskleader)
    TextView txt_taskleader;

    int taskid;
    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Task_ReturnTaskDetail.aspx";

    String[] picture_url=new String[1];
    protected int getContentView() {
        return R.layout.activity_task_son__xq;
    }

    @Override
    protected void initViews() {
        Bundle bundle = getIntent().getExtras();
        taskid=bundle.getInt("taskid");
        initActionBar(actionBar);
        btn_xinxi();
    }
    private void initActionBar(layout_action_bar actionBar) {
        actionBar.setPriviousClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionBar.hideAdd();
    }

    private void imageBrower(int position ,String[] urls){
        Intent intent=new Intent(this,ImagePagerActivity.class);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS,urls);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX,position);
        startActivity(intent);
    }

    /**
     * 调接口
     */
    public void btn_xinxi(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Task_ReturnTaskDetail(taskid, baseUrl, url, new CustomCallBack<RemoteDataResult<ReportContent_TaskXinxiGet>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<ReportContent_TaskXinxiGet>> response) {
                ToastUtils.showShort(TaskSon_XQActivity.this,response.body().getResultMessage());
                ReportContent_TaskXinxiGet get = response.body().getData();
                initView(get);
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(TaskSon_XQActivity.this,message);
            }
        });
    }

    private void initView(ReportContent_TaskXinxiGet get) {
        actionBar.setTitle(get.getExhibitionname());
        txt_tasktitle.setText(get.getTasktitle());
        String taskstate="";
        if(get.getTaskstatus()==0){
            taskstate="初始状态";
        }else if(get.getTaskstatus()==1){
            taskstate="已完成";
        }else if(get.getTaskstatus()==2){
            taskstate="失败";
        }
        txt_taskstate.setText(taskstate);
        txt_taskpublish.setText(get.getTaskpublishtime().replace("T"," "));
        txt_taskset.setText(get.getTasksettime().replace("T"," "));
        txt_taskstart.setText(get.getTaskstarttime().replace("T"," "));
        txt_tasktype.setText(get.getTasktype());
        txt_taskcontent.setText(get.getTaskcontent());

        if(get.getTaskpicurl()!=null){
            picture_url[0]="http://opoecp2mn.bkt.clouddn.com/"+get.getTaskpicurl();
            if(picture_url!=null&&picture_url.length>0){
                picture_1.setVisibility(View.VISIBLE);
                picture_1.setAdapter(new NoScrollGridAdapter(picture_url,this));
                picture_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        imageBrower(position,picture_url);
                    }
                });
            }else {
                picture_1.setVisibility(View.GONE);
            }
        }
        txt_taskcreater.setText(get.getTaskcreatorname());
        String tasklaunchtype =get.getTasklanchtype();
        txt_tasklaunchtype.setText(tasklaunchtype);
        if(tasklaunchtype!=null){
            if(tasklaunchtype.equals("some")){
                String tasklanchername ="";
                List<Tasklanchuserslist> tasklanchu = get.getTasklanchuserslist();
                for(int i=0;i<tasklanchu.size();i++){
                    tasklanchername=tasklanchername+tasklanchu.get(i).getName()+" ";
                }
                txt_tasklaunchor.setText(tasklanchername);
            }else {
                txt_tasklaunchor.setText(get.getTasklanchername());
            }
        }

        txt_taskleader.setText(get.getTaskleadername());

    }


}
