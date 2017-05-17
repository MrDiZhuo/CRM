package com.zwd.crm.HomePage.addAffair.View;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zwd.crm.HomePage.addAffair.Module.AddAffairPost;
import com.zwd.crm.HomePage.addAffair.Module.Addaffair_person;
import com.zwd.crm.HomePage.addAffair.Module.PersonElement;
import com.zwd.crm.HomePage.choosePerson.View.ChoosePersonActivity;
import com.zwd.crm.HomePage.reportContext.Module.Report_CotentGet;
import com.zwd.crm.R;
import com.zwd.crm.adapter.report_ContextAdapter;
import com.zwd.crm.base.BaseActivity;
import com.zwd.crm.interfaze.CommonAdapter;
import com.zwd.crm.internet.CustomCallBack;
import com.zwd.crm.internet.RemoteDataResult;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.layout.layout_action_bar;
import com.zwd.crm.util.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Response;

import static android.support.v7.appcompat.R.styleable.AlertDialog;

public class AddAffairActivity extends BaseActivity {
    @Bind(R.id.add_affair_action_bar)
    layout_action_bar actionBar;

    @Bind(R.id.add_affair_vary)
    RelativeLayout vary;

    @Bind(R.id.add_affair_edit)
    EditText edit_text;
    @Bind(R.id.add_affair_vary_txt)
    TextView txt_vary;
    @Bind(R.id.add_affair_vary_rv)
    RecyclerView recyclerView;
    @Bind(R.id.add_affair_vary_num)
    TextView txt_num;
    @Bind(R.id.add_affair_ll)
    LinearLayout linearLayout;

    AlertDialog dialog;
    AlertDialog.Builder builder;
    View outerView;

    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Notice_AddNotice.aspx";
    private int creatId;
    private int usersgroupid;

    report_ContextAdapter conferenceAdapter;
    ArrayList<Addaffair_person> affairElementList=new ArrayList<>();

    Addaffair_person have =new Addaffair_person("","添加人员",R.mipmap.item_grid);
    List<Integer> choosepersonId = new ArrayList<>();


    protected int getContentView() {
        return R.layout.activity_add_affair;
    }
    @Override
    protected void initViews() {
        Bundle bundle = getIntent().getExtras();
        creatId=bundle.getInt("userId");
        usersgroupid=bundle.getInt("usersgroupid");
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
                if(txt_vary.getText().toString().isEmpty()){
                    ToastUtils.showShort(AddAffairActivity.this,"请选择事务类型");
                }else {
                    if(txt_vary.getText().toString().equals("提醒")){
                        if (imgs.equals(have.getName().toString())) {
                            Bundle bundle1 = new Bundle();
                            bundle1.putInt("usersgroupid",usersgroupid);
                            goActivityForResult(ChoosePersonActivity.class,bundle1,0);
                            //    Toast.makeText(mContext, "Click:" + affairElementList.get(position).getName().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(mContext, "LongClick:" + position , Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }
    private void initActionBar(layout_action_bar actionBar) {
        actionBar.setPriviousClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionBar.setTitle("创建事务");
        actionBar.setImage(R.mipmap.finish);
        actionBar.setAddClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_addAffair();
            }
        });
    }
    @OnClick(R.id.add_affair_vary)
    public void click(View v){
        switch (v.getId()){
            case R.id.add_affair_vary:
                builder =new AlertDialog.Builder(this);
                outerView= LayoutInflater.from(this).inflate(R.layout.layout_addaffair_dialog, null);
                LinearLayout tongzhi =(LinearLayout)outerView.findViewById(R.id.addaffair_dialog_tongzhi);
                LinearLayout tixing =(LinearLayout)outerView.findViewById(R.id.addaffair_dialog_tixing);
                LinearLayout quxiao =(LinearLayout)outerView.findViewById(R.id.addaffair_dialog_quxiao);
                tongzhi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_vary.setText("通知");
                        linearLayout.setVisibility(View.GONE);
                        txt_num.setText("所有成员");
                        dialog.dismiss();
                    }
                });
                tixing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_vary.setText("提醒");
                        linearLayout.setVisibility(View.VISIBLE);
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

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        switch (requestCode) {
            case 0:
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
                        ToastUtils.showShort(AddAffairActivity.this,person);
                        affairElementList.remove(have);
                        affairElementList.add(contextelement);
                        affairElementList.add(have);
                        conferenceAdapter = new report_ContextAdapter(this,affairElementList);
                        conferenceAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                                Bundle bundle1 = new Bundle();
                                bundle1.putInt("usersgroupid",usersgroupid);
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
                        choosepersonId.add(personId);
                    }
                    break;
                }else break;
        }
    }

    /**
     * diaojiekou
     */
    public void btn_addAffair(){
        String title ="";
        String noticeContent = edit_text.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date()); int createId=creatId;
        String noticeEnddate="";
        int noticestatus=0;
        String noticeType = txt_vary.getText().toString();
        List<PersonElement> personElementList = new ArrayList<>();
        for(int i=0;i<choosepersonId.size();i++){
            PersonElement personElement = new PersonElement(choosepersonId.get(i),"",0);
            personElementList.add(personElement);
        }

        AddAffairPost addAffairPost =new AddAffairPost();
        addAffairPost.setTitle(title);
        addAffairPost.setNoticeContent(noticeContent);
        addAffairPost.setNoticeDate(date);
        addAffairPost.setCreateId(createId);
        addAffairPost.setNoticeEnddate(noticeEnddate);
        addAffairPost.setNoticestatus(noticestatus);
        addAffairPost.setNoticeType(noticeType);
        addAffairPost.setPersonElementList(personElementList);
        if(check()){
            RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
            remoteOptionIml.Notice_AddNotice(addAffairPost, baseUrl, url, new CustomCallBack<RemoteDataResult>() {
                @Override
                public void onSuccess(Response<RemoteDataResult> response) {
                  ToastUtils.showShort(AddAffairActivity.this,response.body().getResultMessage());
                    Log.d("success",response.body().getResultMessage());
                }

                @Override
                public void onFail(String message) {
                    ToastUtils.showShort(AddAffairActivity.this,message);
                    Log.d("failure",message);
                }
            });
        }
    }
    /**
     * 检查
     */
    public boolean check(){
        if(txt_vary.getText().toString().isEmpty()||choosepersonId.isEmpty()||
                edit_text.getText().toString().isEmpty()){
            ToastUtils.showShort(AddAffairActivity.this,"不能为空！");
            return false;
        }else return true;
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
