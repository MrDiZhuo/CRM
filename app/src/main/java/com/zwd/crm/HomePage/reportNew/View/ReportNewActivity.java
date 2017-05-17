package com.zwd.crm.HomePage.reportNew.View;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ipaulpro.afilechooser.utils.FileUtils;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.zwd.crm.HomePage.addAffair.Module.Addaffair_person;
import com.zwd.crm.HomePage.choosePerson.View.ChoosePersonActivity;
import com.zwd.crm.HomePage.reportContext.Module.Report_CotentGet;
import com.zwd.crm.HomePage.reportContextLocation.Report_Context_LocationActivity;
import com.zwd.crm.HomePage.reportContextRengwu.View.ReportContext_RengwuActivity;

import com.zwd.crm.HomePage.reportNew.Module.ReportNewPost;
import com.zwd.crm.HomePage.reportNew.Module.targetidListElement;
import com.zwd.crm.R;
import com.zwd.crm.adapter.report_ContextAdapter;
import com.zwd.crm.base.BaseActivity;
import com.zwd.crm.interfaze.CommonAdapter;
import com.zwd.crm.internet.CustomCallBack;
import com.zwd.crm.internet.RemoteDataResult;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.layout.layout_action_bar;
import com.zwd.crm.util.ToastUtils;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.http.Field;

public class ReportNewActivity extends BaseActivity {
    @Bind(R.id.report_new_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.report_new_rv)
    RecyclerView recyclerView;
    @Bind(R.id.report_new_num)
    TextView txt_num;
    @Bind(R.id.report_new_completejobcontent)
    EditText edit_completejobcontent;
    @Bind(R.id.report_new_nocompletejobcontent)
    EditText edit_nocompletejobcontent;
    @Bind(R.id.report_new_coordinatejobcontent)
    EditText edit_coordinatejobcontent;
    @Bind(R.id.report_new_reportcontent)
    EditText edit_reportcontent;

    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Report_AddReport.aspx";
    private int usergroupid;
    private int createId;
    private String token;
    private String reporttype="";
    private String address="";
    private int taskid;

    String enclosureName="";
    private String picurl="";
    private String enclosureurl="";
    private int reportstatus=0;
    private List<targetidListElement> targetidListElementList = new ArrayList<>();

    private ArrayList<String> imagePaths = new ArrayList<>();
   // private static final int REQUEST_CAMERA_CODE = 0;
   private static final int REQUEST_TASK = 2;
    private static final int REQUEST_doc = 1;
    private static final int REQUEST_person = 0;
    private static final int REQUEST_location = 3;

    report_ContextAdapter conferenceAdapter;
    ArrayList<Addaffair_person> affairElementList=new ArrayList<>();

    Addaffair_person have =new Addaffair_person("","添加人员",R.mipmap.item_grid);

  //  private List<contextElement> affairElementList = new ArrayList<>();

    protected int getContentView() {
        return R.layout.activity_report_new;
    }

    @Override
    protected void initViews() {
        Bundle bundle =getIntent().getExtras();
        reporttype=bundle.getString("title");
        createId=bundle.getInt("createId");
        usergroupid=bundle.getInt("usergroupid");
        token = bundle.getString("token");

        initActionBar(actionBar);
        affairElementList.add(have);
        conferenceAdapter = new report_ContextAdapter(this,affairElementList);
        recyclerView.setAdapter(conferenceAdapter);
        LinearLayoutManager layoutManger = new LinearLayoutManager(this);
        layoutManger.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManger);
        conferenceAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                String imgs = affairElementList.get(position).getName().toString();
                if (imgs.equals(have.getName().toString())) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("usersgroupid",usergroupid);
                    goActivityForResult(ChoosePersonActivity.class,bundle1,REQUEST_person);
                    //    Toast.makeText(mContext, "Click:" + affairElementList.get(position).getName().toString(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(mContext, "LongClick:" + position , Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    /**
     * 调接口
     * @param
     */
    public void btn_reportNew(String key){
        if(check()){

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(new Date());
              ReportNewPost post = new ReportNewPost();
            post.setReportorid(createId);
            post.setTaskid(taskid);
            post.setAddress(address);
            post.setReportdate(date);
            post.setReporttype(reporttype);
            post.setCompletejobcontent(edit_completejobcontent.getText().toString());
            post.setCoordinatejobcontent(edit_coordinatejobcontent.getText().toString());
            post.setNocompletejobcontent(edit_nocompletejobcontent.getText().toString());
            post.setReportcontent(edit_reportcontent.getText().toString());
            post.setEnclosureurl(key);
            post.setTargetidList(targetidListElementList);
            post.setPicurl(enclosureName);
            post.setReportstatus(reportstatus);

            RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
            remoteOptionIml.Report_AddReport(post, baseUrl, url, new CustomCallBack<RemoteDataResult>() {
                @Override
                public void onSuccess(Response<RemoteDataResult> response) {
                    ToastUtils.showShort(ReportNewActivity.this,response.body().getResultMessage());
                }
                @Override
                public void onFail(String message) {
                    ToastUtils.showShort(ReportNewActivity.this,message);
                }
            });
        }



    }

    /**
     * 必须有回报的对象
     */
    public boolean check(){
        if(targetidListElementList.isEmpty()){
            ToastUtils.showShort(this,"请选择回报目标!");
            return false;
        }else return true;
    }

    /**
     * 七牛上传
     * @param filePath
     * @param token
     * @return
     */
    public void uploadImageToQiniu(String filePath, String token) {
        UploadManager uploadManager = new UploadManager();
        // 设置图片名字
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String key = "icon_" + sdf.format(new Date());
        uploadManager.put(filePath, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject res) {
                Log.d("error:", info.toString());

                // info.error中包含了错误信息，可打印调试
                // 上传成功后将key值上传到自己的服务器
                btn_reportNew(key);
                Log.d("key",key);
            }
        }, null);
    }


    @OnClick({R.id.report_new_task,R.id.report_new_doc,R.id.report_new_location})
    public void click(View v){
        switch (v.getId()){
            case R.id.report_new_task:
                Bundle bundle =new Bundle();
                bundle.putInt("id",createId);
                goActivityForResult(ReportContext_RengwuActivity.class,bundle,REQUEST_TASK);
                break;
            case R.id.report_new_doc:
                Intent getContentIntent = FileUtils.createGetContentIntent();
                Intent intent2=Intent.createChooser(getContentIntent,"选择文件");
                startActivityForResult(intent2,REQUEST_doc);
                break;
            case R.id.report_new_location:
                goActivityForResult(Report_Context_LocationActivity.class,REQUEST_location);
                break;

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            switch (requestCode) {
                case REQUEST_TASK:
                    if(resultCode==2){
                        Bundle bundle = data.getExtras();
                        taskid = bundle.getInt("taskId");
                    }
                    break;

                case REQUEST_location:
                    if(resultCode==3){
                        Bundle bundle = data.getExtras();
                        address = bundle.getString("address");
                    }
                    break;

                case REQUEST_doc:
                    if(resultCode == RESULT_OK) {
                        Uri uri = data.getData();
                        String path = FileUtils.getPath(this,uri);
                        //enclosureurl=FileUtils.getPath(this,uri);
                        if(path!=null&&FileUtils.isLocal(path)){
                            File file = new File(path);
                            enclosureurl=file.getAbsolutePath();
                            Log.d("pat",enclosureurl);
                            int a =enclosureurl.lastIndexOf("/");
                            enclosureName=enclosureurl.substring(a+1,enclosureurl.length());
                        }
                        ToastUtils.showShort(this,uri.getPath().toString());
                    }

                    break;
                case REQUEST_person:
                    if(resultCode ==0) {
                        Boolean temp = false;
                        final Bundle bundle = data.getExtras();
                        String person = bundle.getString("person");
                        int personId = bundle.getInt("personId");

                        Addaffair_person contextelement = new Addaffair_person(person,person,R.mipmap.circle);
                        for(int i=0;i<affairElementList.size();i++){
                            if(affairElementList.get(i).getName().equals(person) ){
                                temp =true;
                            }
                        }
                        if(temp==false&&person.length()>0){
                            ToastUtils.showShort(ReportNewActivity.this,person);
                            affairElementList.remove(have);
                            affairElementList.add(contextelement);
                            affairElementList.add(have);
                            conferenceAdapter = new report_ContextAdapter(this,affairElementList);
                            conferenceAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                                    Bundle bundle1 = new Bundle();
                                    bundle1.putInt("usersgroupid",usergroupid);
                                    goActivityForResult(ChoosePersonActivity.class,bundle1,0);
                                    //    Toast.makeText(mContext, "Click:" + affairElementList.get(position).getName().toString(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                                    Toast.makeText(mContext, "LongClick:" + position , Toast.LENGTH_SHORT).show();
                                    return false;
                                }
                            });
                            recyclerView.setAdapter(conferenceAdapter);
                            txt_num.setText("共"+(affairElementList.size()-1)+"人");
                            targetidListElement element = new targetidListElement();
                            element.setId(personId);
                            element.setName(person);
                            targetidListElementList.add(element);
                        }
                        break;
                    }else break;


            }

    }



    private void initActionBar(layout_action_bar actionBar) {

        actionBar.setPriviousClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionBar.setTitle("创建"+reporttype+"报");
        actionBar.setImage(R.mipmap.finish);
        actionBar.setAddClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImageToQiniu(enclosureurl,token);
            }
        });
    }



    /**
     * 点击键盘以外的区域隐藏键盘
     * @param ev
     * @return
     */

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        if(ev.getAction() ==MotionEvent.ACTION_DOWN){
            View v =getCurrentFocus();
            if(isShoudHideKeyBoard(v,ev)){
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm!=null){
                    imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        if(getWindow().superDispatchTouchEvent(ev)){
            return true;
        }
        return onTouchEvent(ev);
    }
    private boolean isShoudHideKeyBoard(View v, MotionEvent event) {
        if(v!=null&&(v instanceof EditText)){
            int[] l = {0,0};
            v.getLocationInWindow(l);
            int left = l[0], top =l[1],bottom = top+v.getHeight(),right=left+v.getWidth();
            if(event.getX()>left && event.getX()<right &&event.getY()<bottom && event.getY()>top){
                return false;
            }else {
                return true;
            }
        }
        return false;

    }




}
