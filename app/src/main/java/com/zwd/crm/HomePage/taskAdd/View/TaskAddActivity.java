package com.zwd.crm.HomePage.taskAdd.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lidong.photopicker.PhotoPickerActivity;
import com.lidong.photopicker.PhotoPreviewActivity;
import com.lidong.photopicker.SelectModel;
import com.lidong.photopicker.intent.PhotoPickerIntent;
import com.lidong.photopicker.intent.PhotoPreviewIntent;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.zwd.crm.HomePage.addAffair.Module.Addaffair_person;
import com.zwd.crm.HomePage.choosePerson.Module.ChoosePersonElement;
import com.zwd.crm.HomePage.choosePerson.Module.ChoosePersonGet;
import com.zwd.crm.HomePage.choosePerson.View.ChoosePersonActivity;
import com.zwd.crm.HomePage.createBumen.View.CreateBumenActivity;
import com.zwd.crm.HomePage.inform.Module.InformGet;
import com.zwd.crm.HomePage.inform.Module.subjectdepartmentElement;
import com.zwd.crm.HomePage.reportContext.Module.Report_CotentGet;
import com.zwd.crm.HomePage.taskAdd.Module.AddTaskPost;
import com.zwd.crm.HomePage.taskAdd.Module.AddTask_Conference;
import com.zwd.crm.HomePage.taskAdd_BumenLeader.View.TaskAdd_BmLeaderActivity;
import com.zwd.crm.HomePage.taskAdd_QtLeader.View.TaskAdd_QTLeaderActivity;
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
import com.zwd.crm.widget.WheelView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Response;

public class TaskAddActivity extends BaseActivity {
    @Bind(R.id.take_add_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.take_add_gridview)
    NoScrollGridView gridview;

    @Bind(R.id.take_add_start_year)
    TextView txt_start_year;
    @Bind(R.id.take_add_start_month)
    TextView txt_start_month;
    @Bind(R.id.take_add_start_day)
    TextView txt_start_day;
    @Bind(R.id.take_add_end_year)
    TextView txt_end_year;
    @Bind(R.id.take_add_end_month)
    TextView txt_end_month;
    @Bind(R.id.take_add_end_day)
    TextView txt_end_day;

    @Bind(R.id.take_add_task_title)
    TextView txt_task_title;
    @Bind(R.id.take_add_task_varity)
    TextView txt_task_varity;

    @Bind(R.id.take_add_zhixing_varity)
    TextView txt_zhixing_varity;
    @Bind(R.id.take_add_task_leader)
    TextView txt_task_leader;

    @Bind(R.id.take_add_task_content)
    EditText txt_task_content;
    @Bind(R.id.take_add_task_zhixingzhe)
    RecyclerView recyclerView;
    @Bind(R.id.take_add_task_name)
            EditText txt_task_name;
    int tasklauncherid;
    int taskleader;
    int exhibitionid;
    List<Integer> zhixingzheid = new ArrayList<>();
    List<subjectdepartmentElement> subjectdepartmentElements;

    ArrayList<String> mArrayList_year;
    ArrayList<String> mArrayList_month;
    ArrayList<String> mArrayList_day;

    AlertDialog dialog;
    AlertDialog.Builder builder;
    View outerView;
    String choose_txt;
    int stateNum;
    int usergroupid;
    int createId;
    Bundle bundle;
    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Task_AddTaskByTask.aspx";
    private String url_conference="Exhibition_ReturnExhibitionListByTask.aspx";
    private String url_department="Department_ReturnDepartmentStructureFromUsersGroup.aspx";
    ArrayList<String> conference = new ArrayList<>();
    ArrayList<String> department = new ArrayList<>();
    List<AddTask_Conference> task_conferences;
    String conference_txt="";

    report_ContextAdapter conferenceAdapter;
    ArrayList<Addaffair_person> affairElementList=new ArrayList<>();

    Addaffair_person have =new Addaffair_person("","添加",R.mipmap.item_grid);

    private static final int CHOOSE_QTLADER = 4;
    private static final int CHOOSE_BMLEADER = 3;
    private static final int REQUEST_CAMERA_CODE = 1;
    private static final int REQUEST_PREVIEW_CODE = 2;
    private static final int CHOOSE_PERSON = 0;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private GridAdapter gridAdapter;
    private String depp;
    private String TAG =TaskAddActivity.class.getSimpleName();

    String token="";
    protected int getContentView() {
        return R.layout.activity_task_add;
    }

    @Override
    protected void initViews() {
        bundle = getIntent().getExtras();
        usergroupid=bundle.getInt("usergroupid");
        createId = bundle.getInt("createId");
        token=bundle.getString("token");
        initActionBar(actionBar);
        btn_conferenceList();
        btn_group_department();

        mArrayList_year = initListYear();
        mArrayList_month = initListMonth();
        mArrayList_day = initListday();


        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        cols = cols < 3 ? 3 : cols;
        gridview.setNumColumns(cols);
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));

        // preview
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String imgs = (String) parent.getItemAtPosition(position);
                if ("000000".equals(imgs) ){
                    PhotoPickerIntent intent = new PhotoPickerIntent(TaskAddActivity.this);
                    intent.setSelectModel(SelectModel.MULTI);
                    intent.setShowCarema(true); // 是否显示拍照
                    intent.setMaxTotal(9); // 最多选择照片数量，默认为6
                    intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                    startActivityForResult(intent, REQUEST_CAMERA_CODE);
                }else{
                    PhotoPreviewIntent intent = new PhotoPreviewIntent(TaskAddActivity.this);
                    intent.setCurrentItem(position);
                    intent.setPhotoPaths(imagePaths);
                    startActivityForResult(intent, REQUEST_PREVIEW_CODE);
                }
            }
        });
        imagePaths.add("000000");
        gridAdapter = new GridAdapter(imagePaths);
        gridview.setAdapter(gridAdapter);

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
                if(txt_zhixing_varity.getText().toString().isEmpty()){
                    ToastUtils.showShort(TaskAddActivity.this,"请选择执行者类型");
                }else {
                    if(txt_zhixing_varity.getText().toString().equals("成员")){
                        if (imgs.equals(have.getName().toString())) {
                            Bundle bundle1 = new Bundle();
                            bundle1.putInt("usersgroupid",usergroupid);
                            goActivityForResult(ChoosePersonActivity.class,bundle1,CHOOSE_PERSON);
                            //    Toast.makeText(mContext, "Click:" + affairElementList.get(position).getName().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }else if(txt_zhixing_varity.getText().toString().equals("部门")){
                        builder =new AlertDialog.Builder(TaskAddActivity.this);
                        outerView= LayoutInflater.from(TaskAddActivity.this).inflate(R.layout.layout_createbumen_dialog, null);
                        WheelView wv = (WheelView) outerView.findViewById(R.id.wv);
                        wv.setOffset(2);
                        //设置每一个Item中的数据 mArrayList中装着的是一堆String字符串
                        wv.setItems(department);
                        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                            @Override
                            public void onSelected(int selectedIndex, String item) {
                                //selectedIndex当前高亮的位置
                                //item当前高亮的位置的内容
                                choose_txt = item;
                            }
                        });
                        builder.setView(outerView);
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                affairElementList.remove(have);
                                Addaffair_person choose_bumen =new Addaffair_person(choose_txt,choose_txt,R.mipmap.circle);
                                affairElementList.add(choose_bumen);
                                conferenceAdapter = new report_ContextAdapter(TaskAddActivity.this,affairElementList);
                                recyclerView.setAdapter(conferenceAdapter);
                                for(int i=0;i<subjectdepartmentElements.size();i++){
                                    if(subjectdepartmentElements.get(i).getDepartmentname().equals(choose_txt)){
                                        tasklauncherid = subjectdepartmentElements.get(i).getDepartmentid();
                                    }
                                }
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @OnClick({R.id.take_add_task_leader,R.id.take_add_task_title,R.id.take_add_start_year,R.id.take_add_start_month,R.id.take_add_start_day,R.id.take_add_end_year,
              R.id.take_add_end_month,R.id.take_add_end_day,R.id.take_add_task_varity,R.id.take_add_zhixing_varity})
    public void click(View v){
        switch (v.getId()){
            case R.id.take_add_task_leader:
                    if(txt_zhixing_varity.getText().toString().equals("部门")){
                        if(affairElementList.size()==1){
                            bundle = new Bundle();
                            bundle.putInt("department",tasklauncherid);
                            goActivityForResult(TaskAdd_BmLeaderActivity.class,bundle,CHOOSE_BMLEADER);
                        }else {
                            ToastUtils.showShort(this,"请选择执行者");
                        }

                    }else if(txt_zhixing_varity.getText().toString().equals("成员")){
                        if(affairElementList.size()==1){
                            ToastUtils.showShort(this,"请选择执行者");
                        }else {
                            if(affairElementList.size()==2){
                                zhixingzheid.add(affairElementList.get(0).getId());
                                txt_task_leader.setText(affairElementList.get(0).getName());
                            }else {
                                List<ChoosePersonGet> post = new ArrayList<>();
                                for(int i=0;i<affairElementList.size()-1;i++){
                                    ChoosePersonGet person = new ChoosePersonGet(affairElementList.get(i).getId(),affairElementList.get(i).getName());
                                    post.add(person);
                                    zhixingzheid.add(affairElementList.get(i).getId());
                                }
                                bundle = new Bundle();
                                bundle.putSerializable("personList", (Serializable) post);
                                goActivityForResult(TaskAdd_QTLeaderActivity.class,bundle,CHOOSE_QTLADER);
                            }
                        }

                    }


                break;
            case R.id.take_add_task_title:
                builder =new AlertDialog.Builder(this);
                outerView= LayoutInflater.from(this).inflate(R.layout.layout_createbumen_dialog, null);
                TextView title_0 = (TextView) outerView.findViewById(R.id.title);
                title_0.setText("选择会展");
                WheelView wv_conference = (WheelView) outerView.findViewById(R.id.wv);
                wv_conference.setOffset(2);
                //设置每一个Item中的数据 mArrayList中装着的是一堆String字符串
                wv_conference.setItems(conference);
                wv_conference.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        //selectedIndex当前高亮的位置
                        //item当前高亮的位置的内容
                        conference_txt = item;
                    }
                });
                builder.setView(outerView);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txt_task_title.setText(conference_txt);
                        for(int i=0;i<task_conferences.size();i++){
                            if(task_conferences.get(i).getExhibitionname().equals(conference_txt)){
                                exhibitionid=task_conferences.get(i).getId();
                            }
                        }
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;

            case R.id.take_add_start_year:
                builder =new AlertDialog.Builder(this);
                outerView= LayoutInflater.from(this).inflate(R.layout.layout_createbumen_dialog, null);
                TextView title_1 = (TextView) outerView.findViewById(R.id.title);
                title_1.setText("选择年");
                WheelView wv_year = (WheelView) outerView.findViewById(R.id.wv);
                wv_year.setOffset(2);
                //设置每一个Item中的数据 mArrayList中装着的是一堆String字符串
                wv_year.setItems(mArrayList_year);
                wv_year.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        //selectedIndex当前高亮的位置
                        //item当前高亮的位置的内容
                        choose_txt = item;
                    }
                });
                builder.setView(outerView);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txt_start_year.setText(choose_txt);
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
            case R.id.take_add_start_month:
                builder =new AlertDialog.Builder(this);
                outerView= LayoutInflater.from(this).inflate(R.layout.layout_createbumen_dialog, null);
                TextView title_2 = (TextView) outerView.findViewById(R.id.title);
                title_2.setText("选择月");
                WheelView wv_month = (WheelView) outerView.findViewById(R.id.wv);
                wv_month.setOffset(2);
                //设置每一个Item中的数据 mArrayList中装着的是一堆String字符串
                wv_month.setItems(mArrayList_month);
                wv_month.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        //selectedIndex当前高亮的位置
                        //item当前高亮的位置的内容
                        choose_txt = item;
                    }
                });
                builder.setView(outerView);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txt_start_month.setText(choose_txt);
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
            case R.id.take_add_start_day:
                builder =new AlertDialog.Builder(this);
                outerView= LayoutInflater.from(this).inflate(R.layout.layout_createbumen_dialog, null);
                TextView title_3 = (TextView) outerView.findViewById(R.id.title);
                title_3.setText("选择日");
                WheelView wv_day = (WheelView) outerView.findViewById(R.id.wv);
                wv_day.setOffset(2);
                //设置每一个Item中的数据 mArrayList中装着的是一堆String字符串
                wv_day.setItems(mArrayList_day);
                wv_day.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        //selectedIndex当前高亮的位置
                        //item当前高亮的位置的内容
                        choose_txt = item;
                    }
                });
                builder.setView(outerView);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txt_start_day.setText(choose_txt);
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
            case R.id.take_add_end_year:
                builder =new AlertDialog.Builder(this);
                outerView= LayoutInflater.from(this).inflate(R.layout.layout_createbumen_dialog, null);
                TextView title_4 = (TextView) outerView.findViewById(R.id.title);
                title_4.setText("选择年");
                WheelView wv_year_2 = (WheelView) outerView.findViewById(R.id.wv);
                wv_year_2.setOffset(2);
                //设置每一个Item中的数据 mArrayList中装着的是一堆String字符串
                wv_year_2.setItems(mArrayList_year);
                wv_year_2.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        //selectedIndex当前高亮的位置
                        //item当前高亮的位置的内容
                        choose_txt = item;
                    }
                });
                builder.setView(outerView);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txt_end_year.setText(choose_txt);
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
            case R.id.take_add_end_month:
                builder =new AlertDialog.Builder(this);
                outerView= LayoutInflater.from(this).inflate(R.layout.layout_createbumen_dialog, null);
                TextView title_5 = (TextView) outerView.findViewById(R.id.title);
                title_5.setText("选择月");
                WheelView wv_month_2 = (WheelView) outerView.findViewById(R.id.wv);
                wv_month_2.setOffset(2);
                //设置每一个Item中的数据 mArrayList中装着的是一堆String字符串
                wv_month_2.setItems(mArrayList_month);
                wv_month_2.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        //selectedIndex当前高亮的位置
                        //item当前高亮的位置的内容
                        choose_txt = item;
                    }
                });
                builder.setView(outerView);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txt_end_month.setText(choose_txt);
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
            case R.id.take_add_end_day:
                builder =new AlertDialog.Builder(this);
                outerView= LayoutInflater.from(this).inflate(R.layout.layout_createbumen_dialog, null);
                TextView title_6 = (TextView) outerView.findViewById(R.id.title);
                title_6.setText("选择日");
                WheelView wv_day_2 = (WheelView) outerView.findViewById(R.id.wv);
                wv_day_2.setOffset(2);
                //设置每一个Item中的数据 mArrayList中装着的是一堆String字符串
                wv_day_2.setItems(mArrayList_day);
                wv_day_2.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        //selectedIndex当前高亮的位置
                        //item当前高亮的位置的内容
                        choose_txt = item;
                    }
                });
                builder.setView(outerView);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txt_end_day.setText(choose_txt);
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
            case R.id.take_add_task_varity:
                builder =new AlertDialog.Builder(this);
                outerView= LayoutInflater.from(this).inflate(R.layout.layout_task_add, null);
                TextView state_1 =(TextView) outerView.findViewById(R.id.task_add_dialog_state_1);
                TextView state_2 =(TextView) outerView.findViewById(R.id.task_add_dialog_state_2);
                TextView state_3 =(TextView) outerView.findViewById(R.id.task_add_dialog_state_3);
                TextView state_4 =(TextView) outerView.findViewById(R.id.task_add_dialog_state_4);
                state_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_task_varity.setText("筹备中");
                        stateNum=0;
                        dialog.dismiss();
                    }
                });
                state_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_task_varity.setText("展览中");
                        stateNum=1;
                        dialog.dismiss();
                    }
                });
                state_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_task_varity.setText("整理中");
                        stateNum=2;
                        dialog.dismiss();
                    }
                });
                state_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_task_varity.setText("完结");
                        stateNum=3;
                        dialog.dismiss();
                    }
                });
                builder.setView(outerView);
                dialog =builder.create();
                dialog.show();
                break;
            case R.id.take_add_zhixing_varity:
                builder =new AlertDialog.Builder(this);
                outerView= LayoutInflater.from(this).inflate(R.layout.layout_task_add_zhixing_varity, null);
                LinearLayout bumen =(LinearLayout)outerView.findViewById(R.id.taskAdd_zhixingzhe_bumen);
                LinearLayout geren =(LinearLayout)outerView.findViewById(R.id.taskAdd_zhixingzhe_geren);
                LinearLayout quxiao =(LinearLayout)outerView.findViewById(R.id.taskAdd_zhixingzhe_quxiao);
                bumen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_zhixing_varity.setText("部门");
                        dialog.dismiss();
                    }
                });
                geren.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_zhixing_varity.setText("成员");
                        dialog.dismiss();
                    }
                });
                quxiao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                builder.setView(outerView);
                dialog =builder.create();
                dialog.show();
                break;

        }
    }
    private ArrayList<String> initListday() {
        ArrayList<String> list =new ArrayList<>();
        list.add("01");list.add("02");list.add("03");list.add("04");list.add("05");list.add("06");list.add("07");
        list.add("08");list.add("09");list.add("10");list.add("11");list.add("12");list.add("13");list.add("14");
        list.add("15");list.add("16");list.add("17");list.add("18");list.add("19");list.add("20");list.add("21");
        list.add("22");list.add("23");list.add("24");list.add("25");list.add("26");list.add("27");list.add("28");
        list.add("29");list.add("30");list.add("31");
        return list;
    }
    private ArrayList<String> initListMonth() {
        ArrayList<String> list =new ArrayList<>();
        list.add("01");list.add("02");list.add("03");list.add("04");list.add("05");list.add("06");list.add("07");
        list.add("08");list.add("09");list.add("10");list.add("11");list.add("12");
        return list;
    }

    private ArrayList<String> initListYear() {
        ArrayList<String> list =new ArrayList<>();
        list.add("2016");
        list.add("2017");
        list.add("2018");
        list.add("2019");
        list.add("2020");
        return list;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       switch (requestCode){
           case CHOOSE_QTLADER:
               if(resultCode==0){
                   bundle = data.getExtras();
                   txt_task_leader.setText(bundle.getString("person"));
                   taskleader = bundle.getInt("personId");
               }
               break;
           case CHOOSE_BMLEADER:
               if(resultCode==0){
                   bundle = data.getExtras();
                   txt_task_leader.setText(bundle.getString("person"));
                   taskleader = bundle.getInt("personId");
               }
               break;
           case REQUEST_CAMERA_CODE:
               if(resultCode == RESULT_OK) {
                   ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                   Log.d(TAG, "list: " + "list = [" + list.size());
                   loadAdpater(list);
               }
               break;
           // 预览
           case REQUEST_PREVIEW_CODE:
               if(resultCode == RESULT_OK) {
                   ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                   Log.d(TAG, "ListExtra: " + "ListExtra = [" + ListExtra.size());
                   loadAdpater(ListExtra);
               }
               break;
           case CHOOSE_PERSON:
               if(resultCode ==0) {
                   Boolean temp = false;
                   bundle = data.getExtras();
                   String person = bundle.getString("person");
                   int personId = bundle.getInt("personId");

                   Addaffair_person contextelement = new Addaffair_person(person,person,R.mipmap.circle);
                   contextelement.setId(personId);
                   for(int i=0;i<affairElementList.size();i++){
                       if(affairElementList.get(i).getName().equals(person) ){
                           temp =true;
                       }
                   }
                   if(temp==false&&person.length()>0){
                       ToastUtils.showShort(TaskAddActivity.this,person);
                       affairElementList.remove(have);
                       affairElementList.add(contextelement);
                       affairElementList.add(have);
                       conferenceAdapter = new report_ContextAdapter(this,affairElementList);
                       conferenceAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
                           @Override
                           public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                               Bundle bundle1 = new Bundle();
                               bundle1.putInt("usersgroupid",usergroupid);
                               goActivityForResult(ChoosePersonActivity.class,bundle1,CHOOSE_PERSON);
                               //    Toast.makeText(mContext, "Click:" + affairElementList.get(position).getName().toString(), Toast.LENGTH_SHORT).show();
                           }

                           @Override
                           public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                               Toast.makeText(mContext, "LongClick:" + position , Toast.LENGTH_SHORT).show();
                               return false;
                           }
                       });
                       recyclerView.setAdapter(conferenceAdapter);

                   }
                   break;
               }else break;


       }

    }

    private void loadAdpater(ArrayList<String> paths){
        if (imagePaths!=null&& imagePaths.size()>0){
            imagePaths.clear();
        }
        if (paths.contains("000000")){
            paths.remove("000000");
        }
        paths.add("000000");
        imagePaths.addAll(paths);
        gridAdapter  = new GridAdapter(imagePaths);
        gridview.setAdapter(gridAdapter);
        try{
            JSONArray obj = new JSONArray(imagePaths);
            Log.e("--", obj.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class GridAdapter extends BaseAdapter {
        private ArrayList<String> listUrls;
        private LayoutInflater inflater;
        public GridAdapter(ArrayList<String> listUrls) {
            this.listUrls = listUrls;
            if(listUrls.size() == 10){
                listUrls.remove(listUrls.size()-1);
            }
            inflater = LayoutInflater.from(TaskAddActivity.this);
        }

        public int getCount(){
            return  listUrls.size();
        }
        @Override
        public String getItem(int position) {
            return listUrls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_image, parent,false);
                holder.image = (ImageView) convertView.findViewById(R.id.imageView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }

            final String path=listUrls.get(position);
            if (path.equals("000000")){
                holder.image.setImageResource(R.mipmap.fx_icon_tag_add);
            }else {
                Glide.with(TaskAddActivity.this)
                        .load(path)
                        .placeholder(R.mipmap.default_error)
                        .error(R.mipmap.default_error)
                        .centerCrop()
                        .crossFade()
                        .into(holder.image);
            }
            return convertView;
        }
        class ViewHolder {
            ImageView image;
        }
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

    /**
     * 会展列表
     */
    public void btn_conferenceList(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Exhibition_ReturnExhibitionListByTask(usergroupid, baseUrl, url_conference, new CustomCallBack<RemoteDataResult<List<AddTask_Conference>>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<List<AddTask_Conference>>> response) {
                ToastUtils.showShort(TaskAddActivity.this,response.body().getResultMessage());
                task_conferences= new ArrayList<AddTask_Conference>(response.body().getData());
                conference=initlistConference(task_conferences);
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(TaskAddActivity.this,message);

            }
        });
    }

    private ArrayList<String> initlistConference(List<AddTask_Conference> task_conferences) {
        ArrayList<String> list =new ArrayList<>();
        for(int i=0;i<task_conferences.size();i++){
            list.add(task_conferences.get(i).getExhibitionname());
        }
        return list;
    }
    /**
     * 获得从属部门
     */
    public void btn_group_department(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Department_ReturnDepartmentStructureFromUsersGroup(usergroupid, baseUrl, url_department, new CustomCallBack<RemoteDataResult<InformGet>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<InformGet>> response) {
                ToastUtils.showShort(TaskAddActivity.this,response.body().getResultMessage());
                InformGet informGet = response.body().getData();
                subjectdepartmentElements = informGet.getSubjectdepartment();
                department =initlist(subjectdepartmentElements);
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(TaskAddActivity.this,message);
            }
        });
    }

    private ArrayList<String> initlist(List<subjectdepartmentElement> subjectdepartmentElements) {
        ArrayList<String> list =new ArrayList<>();
        for(int i=0;i<subjectdepartmentElements.size();i++){
            list.add(subjectdepartmentElements.get(i).getDepartmentname());
        }
        return list;
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
                btn_addTask(key);
            }
        }, null);
    }
    /**
     * 判空
     */
    public boolean check(){
        if(txt_task_name.getText().toString().length()==0||txt_task_title.getText().toString().length()==0||
                txt_task_varity.getText().toString().length()==0||txt_zhixing_varity.getText().toString().length()==0||
                zhixingzheid.size()==0||txt_task_leader.getText().toString().length()==0){
            ToastUtils.showShort(TaskAddActivity.this,"请填写完整!");
            return false;
        }else return true;
    }

    /**
     * 调接口
     */
    public void btn_addTask(String key){
        if(check()){
            AddTaskPost post = new AddTaskPost();
            post.setTaskcreatorid(createId);
            post.setTaskusersgroupid(usergroupid);
            post.setTasktitle(txt_task_name.getText().toString());
            post.setExhibitionid(exhibitionid);
            post.setTaskcontent(txt_task_content.getText().toString());
            post.setTaskpicurl(key);
            post.setTaskstarttime(txt_start_year.getText().toString()+"/"+txt_start_month.getText().toString()+"/"+txt_start_day.getText().toString()+" "+"12"+":"+"12"+":"+"12");
            post.setTasksettime(txt_end_year.getText().toString()+"/"+txt_end_month.getText().toString()+"/"+txt_end_day.getText().toString()+" "+"12"+":"+"12"+":"+"12");


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(new Date());
            post.setTaskpublishtime(date);
            post.setTasktype(txt_task_varity.getText().toString());
            post.setTasklanchtype(txt_zhixing_varity.getText().toString());
            post.setTasklancherid(tasklauncherid);
            post.setLanchuserslist(zhixingzheid);
            post.setTaskleaderid(taskleader);

            ///不用的
            post.setTaskvoiceurl("");
            post.setTaskfinishtime("");
            post.setTaskstatus(0);
            post.setTaskflowid(0);
            post.setTaskfinisheventid(0);
            post.setPrevioustaskid(0);
            post.setNexttaskid(0);
            RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
            remoteOptionIml.Task_AddTask(post, baseUrl, url, new CustomCallBack<RemoteDataResult>() {
                @Override
                public void onSuccess(Response<RemoteDataResult> response) {
                    ToastUtils.showShort(TaskAddActivity.this,response.body().getResultMessage());
                }

                @Override
                public void onFail(String message) {
                    ToastUtils.showShort(TaskAddActivity.this,message);
                    Log.d("okhttpException", message);
                }
            });
        }


    }

    private void initActionBar(layout_action_bar actionBar) {
        actionBar.setPriviousClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionBar.setTitle("创建任务");
        actionBar.setImage(R.mipmap.finish);
        actionBar.setAddClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imagePaths.size()==1){
                    btn_addTask(null);
                }else {
                    uploadImageToQiniu(imagePaths.get(0),token); /////只上传第一张图片
                }
            }
        });
    }
}
