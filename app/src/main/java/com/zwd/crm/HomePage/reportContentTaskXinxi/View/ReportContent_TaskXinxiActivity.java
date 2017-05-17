package com.zwd.crm.HomePage.reportContentTaskXinxi.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.loveplusplus.demo.image.ImagePagerActivity;
import com.zwd.crm.HomePage.reportContentTaskXinxi.Module.ReportContent_TaskXinxiGet;
import com.zwd.crm.HomePage.reportContentTaskXinxi.Module.Tasklanchuserslist;
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

public class ReportContent_TaskXinxiActivity extends BaseActivity {


    @Bind(R.id.report__context_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.report__context_tasktitle)
    TextView txt_tasktitle;
    @Bind(R.id.report__context_taskstate)
    TextView txt_taskstate;
    @Bind(R.id.report__context_taskpublish)
    TextView txt_taskpublish;
    @Bind(R.id.report__context_taskset)
    TextView txt_taskset;
    @Bind(R.id.report__context_taskstart)
    TextView txt_taskstart;
    @Bind(R.id.report__context_tasktype)
    TextView txt_tasktype;
    @Bind(R.id.report__context_taskcontent)
    TextView txt_taskcontent;
    @Bind(R.id.report__context_grid_1)
    NoScrollGridView picture_1;
    @Bind(R.id.report__context_taskcreator)
    TextView txt_taskcreater;
    @Bind(R.id.report__context_tasklaunchtype)
    TextView txt_tasklaunchtype;
    @Bind(R.id.report__context_tasklaunchor)
    TextView txt_tasklaunchor;
    @Bind(R.id.report__context_taskleader)
    TextView txt_taskleader;
    @Bind(R.id.report__context_jiegou)
    TextView txt_task_jiegou;


    int taskid;
    int sonnum;
    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Task_ReturnTaskDetail.aspx";

    String[] picture_url=new String[1];

    protected int getContentView() {
        return R.layout.activity_report_content__task_xinxi;
    }

    @Override
    protected void initViews() {
        Bundle bundle = getIntent().getExtras();
        taskid=bundle.getInt("taskid");
        sonnum=bundle.getInt("sonnum");
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
                ToastUtils.showShort(ReportContent_TaskXinxiActivity.this,response.body().getResultMessage());
                ReportContent_TaskXinxiGet get = response.body().getData();
                initView(get);
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(ReportContent_TaskXinxiActivity.this,message);
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
        if(get.getTaskpublishtime()!=null){
            txt_taskpublish.setText(get.getTaskpublishtime().replace("T"," "));

        }
        if(get.getTasksettime()!=null){
            txt_taskset.setText(get.getTasksettime().replace("T"," "));

        }
        if(get.getTaskstarttime()!=null){
            txt_taskstart.setText(get.getTaskstarttime().replace("T"," "));

        }
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
        if (sonnum == 0) {
            txt_task_jiegou.setVisibility(View.GONE);
        }else {
            txt_task_jiegou.setVisibility(View.VISIBLE);
            txt_task_jiegou.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("taskid",taskid);
                    goActivity(Task_JiegouActivity.class,bundle);
                }
            });
        }


    }

}
