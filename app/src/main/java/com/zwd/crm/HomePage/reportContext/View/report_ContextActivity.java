package com.zwd.crm.HomePage.reportContext.View;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zwd.crm.HomePage.addAffair.Module.Addaffair_person;
import com.zwd.crm.HomePage.reportContentTaskXinxi.View.ReportContent_TaskXinxiActivity;
import com.zwd.crm.HomePage.reportContext.Module.Report_CotentGet;
import com.zwd.crm.HomePage.taskXinxi.View.TaskXinXiActivity;
import com.zwd.crm.R;
import com.zwd.crm.adapter.report_ContextAdapter;
import com.zwd.crm.base.BaseActivity;
import com.zwd.crm.interfaze.CommonAdapter;
import com.zwd.crm.internet.CustomCallBack;
import com.zwd.crm.internet.RemoteDataResult;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.layout.layout_action_bar;
import com.zwd.crm.util.ToastUtils;
import com.zwd.crm.widget.NoScrollGridView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit2.Response;

public class report_ContextActivity extends BaseActivity {
    @Bind(R.id.report_context_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.report_context_completejobcontent)
    TextView txt_completejobcontent;
    @Bind(R.id.report_context_nocompletejobcontent)
    TextView txt_nocompletejobcontent;
    @Bind(R.id.report_context_coordinatejobcontent)
    TextView txt_coordinatejobcontent;
    @Bind(R.id.report_context_reportcontent)
    TextView txt_reportcontent;
    @Bind(R.id.report_context_rv)
    RecyclerView recycler_target;
    @Bind(R.id.report_context_targetnum)
    TextView txt_targetnum;


    @Bind(R.id.report_context_address)
    RelativeLayout rl_address;
    @Bind(R.id.report_context_txt_address)
    TextView txt_address;
    @Bind(R.id.report_context_task)
    RelativeLayout ll_task;
    @Bind(R.id.report_context_wenjian)
    RelativeLayout ll_wenjian;
    @Bind(R.id.report_context_wenjian_down)
    LinearLayout ll_wenjian_down;
    @Bind(R.id.report_context_wenjian_name)
    TextView txt_wenjian_name;

    //建立一个MIME类型与文件后缀名的匹配表
    private final String[][] MIME_MapTable={
            //{后缀名，    MIME类型}
            {".3gp",    "video/3gpp"}, {".apk",    "application/vnd.android.package-archive"}, {".asf",    "video/x-ms-asf"}, {".avi",    "video/x-msvideo"},
            {".bin",    "application/octet-stream"}, {".bmp",      "image/bmp"},
            {".c",        "text/plain"}, {".class",    "application/octet-stream"}, {".conf",    "text/plain"}, {".cpp",    "text/plain"},
            {".doc",    "application/msword"}, {".exe",    "application/octet-stream"},
            {".gif",    "image/gif"}, {".gtar",    "application/x-gtar"},
            {".gz",        "application/x-gzip"}, {".h",        "text/plain"}, {".htm",    "text/html"},
            {".html",    "text/html"}, {".jar",    "application/java-archive"}, {".java",    "text/plain"},
            {".jpeg",    "image/jpeg"}, {".jpg",    "image/jpeg"},
            {".js",        "application/x-javascript"},
            {".log",    "text/plain"}, {".m3u",    "audio/x-mpegurl"}, {".m4a",    "audio/mp4a-latm"},
            {".m4b",    "audio/mp4a-latm"}, {".m4p",    "audio/mp4a-latm"},
            {".m4u",    "video/vnd.mpegurl"}, {".m4v",    "video/x-m4v"}, {".mov",    "video/quicktime"}, {".mp2",    "audio/x-mpeg"},
            {".mp3",    "audio/x-mpeg"}, {".mp4",    "video/mp4"}, {".mpc",    "application/vnd.mpohun.certificate"},
            {".mpe",    "video/mpeg"}, {".mpeg",    "video/mpeg"},
            {".mpg",    "video/mpeg"}, {".mpg4",    "video/mp4"}, {".mpga",    "audio/mpeg"},
            {".msg",    "application/vnd.ms-outlook"}, {".ogg",    "audio/ogg"},
            {".pdf",    "application/pdf"}, {".png",    "image/png"}, {".pps",    "application/vnd.ms-powerpoint"},
            {".ppt",    "application/vnd.ms-powerpoint"}, {".prop",    "text/plain"},
            {".rar",    "application/x-rar-compressed"}, {".rc",        "text/plain"}, {".rmvb",    "audio/x-pn-realaudio"},
            {".rtf",    "application/rtf"}, {".sh",        "text/plain"},
            {".tar",    "application/x-tar"}, {".tgz",    "application/x-compressed"},
            {".txt",    "text/plain"}, {".wav",    "audio/x-wav"}, {".wma",    "audio/x-ms-wma"},
            {".wmv",    "audio/x-ms-wmv"}, {".wps",    "application/vnd.ms-works"},{".xml",    "text/plain"}, {".z",        "application/x-compress"},
            {".zip",    "application/zip"}, {"",        "*/*"}
    };

    private static final String EXTERNAL_DIR = "CRMDownLoad";
    int id;
    Bundle bundle;
    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Report_ReturnReportDetail.aspx";

    DownloadManager downloadManager;
    long mTaskId;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkDownloadStatus();//检查下载状态
        }
    };
    Report_CotentGet report_cotentGet = new Report_CotentGet();


    protected int getContentView() {
        return R.layout.activity_report__context;
    }

    @Override
    protected void initViews() {
        bundle = getIntent().getExtras();
        id = bundle.getInt("id");

        initActionBar(actionBar);
        btn_report_content();




    }



    private void initActionBar(layout_action_bar actionBar) {
        actionBar.setTitle(bundle.getString("title"));
        actionBar.setPriviousClick(new View.OnClickListener() {
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
    public void btn_report_content(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Report_ReturnReportDetail(id, baseUrl, url, new CustomCallBack<RemoteDataResult<Report_CotentGet>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<Report_CotentGet>> response) {
                ToastUtils.showShort(report_ContextActivity.this,response.body().getResultMessage());
                report_cotentGet = response.body().getData();
                initview(report_cotentGet);
                Log.d("get",report_cotentGet.getEnclosureName());
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(report_ContextActivity.this,message);
            }
        });
    }
    private void initview(final Report_CotentGet get) {
        txt_completejobcontent.setText(get.getCompletejobcontent());
        txt_nocompletejobcontent.setText(get.getNocompletejobcontent());
        txt_coordinatejobcontent.setText(get.getCoordinatejobcontent());
        if(get.getReportcontent()==null||get.getReportcontent().isEmpty()){
            txt_reportcontent.setVisibility(View.GONE);
        }else {
            txt_reportcontent.setText(get.getReportcontent());
        }

        if(get.getTaskid()==0){
            ll_task.setVisibility(View.GONE);
        }else {
            ll_task.setVisibility(View.VISIBLE);
            ll_task.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("taskid",get.getTaskid());
                    goActivity(ReportContent_TaskXinxiActivity.class,bundle);
                }
            });
        }

        if (get.getAddress()==null||get.getAddress().isEmpty()){
            rl_address.setVisibility(View.GONE);
        }else {
            rl_address.setVisibility(View.VISIBLE);
            txt_address.setText(get.getAddress());
        }

        if(get.getEnclosureurl()==null||get.getEnclosureurl().isEmpty()){
            ll_wenjian.setVisibility(View.GONE);
        }else {
            ll_wenjian.setVisibility(View.VISIBLE);
            txt_wenjian_name.setText(get.getEnclosureName());
            Log.d("wenjian",get.getEnclosureName());
            Log.d("txt",txt_wenjian_name.getText().toString());
            ll_wenjian_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("确定下载？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String file = "CRMREPORT";
                            if(isFolderExist(file)){
                                //创建下载任务
                                DownloadManager.Request request = new DownloadManager.Request(Uri.parse("http://opoecp2mn.bkt.clouddn.com/"+get.getEnclosureurl()));
                                request.setAllowedOverRoaming(true);//漫游网络是否可以下载

                                //设置文件类型，可以在下载结束后自动打开该文件
                                request.setMimeType(getMIMEType(get.getEnclosureName()));

                                //在通知栏中显示，默认就是显示的
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                request.setVisibleInDownloadsUi(true);

                                //sdcard的目录下的download文件夹，必须设置
                                request.setDestinationInExternalPublicDir("/"+file+"/", get.getEnclosureName());
                                //request.setDestinationInExternalFilesDir(),也可以自己制定下载路径

                                //将下载请求加入下载队列
                                downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
                                //加入下载队列后会给该任务返回一个long型的id，
                                //通过该id可以取消任务，重启任务等等，看上面源码中框起来的方法
                                mTaskId = downloadManager.enqueue(request);

                                //注册广播接收者，监听下载状态
                                mContext.registerReceiver(receiver,
                                        new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                            }
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
                }
            });
        }

        txt_targetnum.setText("共"+get.getTargetList().size()+"人");
        final List<Addaffair_person> targetlist = new ArrayList<>();
        for(int i=0;i<get.getTargetList().size();i++){
            Addaffair_person person = new Addaffair_person(get.getTargetList().get(i).getName(),
                    get.getTargetList().get(i).getName(),get.getTargetList().get(i).getImg());
            targetlist.add(person);
        }
        report_ContextAdapter conferenceAdapter = new report_ContextAdapter(this,targetlist);
        recycler_target.setAdapter(conferenceAdapter);
        LinearLayoutManager layoutManger = new LinearLayoutManager(this);
        layoutManger.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_target.setLayoutManager(layoutManger);
        conferenceAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
               ToastUtils.showShort(mContext,targetlist.get(position).getName());
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(mContext, "LongClick:" + position , Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    /**
     * 判断手机中是有文件没有则重新建立
     * @param dir
     * @return
     */
    private boolean isFolderExist(String dir) {
        File folder = Environment.getExternalStoragePublicDirectory(dir);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }



    //检查下载状态
    private void checkDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(mTaskId);//筛选下载任务，传入任务ID，可变参数
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    Log.d("state",">>>下载暂停");
                case DownloadManager.STATUS_PENDING:
                    Log.d("state",">>>下载延迟");
                case DownloadManager.STATUS_RUNNING:
                    Log.d("state",">>>正在下载");
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    ToastUtils.showShort(this,"下载完成");
                    Log.d("state",">>>下载完成");
                    //下载完成安装APK

                    break;
                case DownloadManager.STATUS_FAILED:
                    ToastUtils.showShort(this,"下载失败");
                    Log.d("state",">>>下载失败");
                    break;
            }
        }
    }
    /**
     * 根据文件后缀名获得对应的MIME类型。
     * @param file
     */
    private String getMIMEType(String file)
    {
        String type="*/*";
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = file.lastIndexOf(".");
        if(dotIndex < 0){
            return type;
        }
    /* 获取文件的后缀名 */
        String end=file.substring(dotIndex,file.length()).toLowerCase();
        if(end=="")return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for(int i=0;i<MIME_MapTable.length;i++){
            if(end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }




}
