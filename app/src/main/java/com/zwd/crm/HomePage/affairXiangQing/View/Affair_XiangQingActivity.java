package com.zwd.crm.HomePage.affairXiangQing.View;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zwd.crm.HomePage.affairXiangQing.Controller.AddAffair_XiangQing;
import com.zwd.crm.HomePage.affairXiangQing.Module.AffairXiangQingAnswerElement;
import com.zwd.crm.HomePage.affairXiangQing.Module.affairXiangQingGet;
import com.zwd.crm.HomePage.affairXiangQing.Module.affair_XiangQingElement;
import com.zwd.crm.HomePage.affairXiangQing.Module.confirmuserslistElement;
import com.zwd.crm.HomePage.affairXiangQing.Module.noticeanswerlistElement;
import com.zwd.crm.R;
import com.zwd.crm.base.BaseActivity;
import com.zwd.crm.base.BaseAppManager;
import com.zwd.crm.internet.CustomCallBack;
import com.zwd.crm.internet.RemoteDataResult;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.layout.layout_action_bar;
import com.zwd.crm.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

public class Affair_XiangQingActivity extends BaseActivity  {
    @Bind(R.id.affair__xiang_qing_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.affair__xiang_qing_img)
    CircleImageView img;
    @Bind(R.id.affair__xiang_qing_name)
    TextView name;
    @Bind(R.id.affair__xiang_qing_time)
    TextView time;
    @Bind(R.id.affair__xiang_qing_inform)
    TextView inform;
    @Bind(R.id.affair__xiang_qing_txt)
    TextView txt;
    @Bind(R.id.affair__xiang_qing_sure)
    TextView sure;
    @Bind(R.id.affair__xiang_qing_num)
    TextView num;
    @Bind(R.id.affair__xiang_qing_ll)
    LinearLayout parent;
    @Bind(R.id.affair__xiang_qing_edit)
    EditText edit;
    @Bind(R.id.affair__xiang_qing_send)
    TextView send;
    @Bind(R.id.affair__xiang_qing_confirmusers)
    TextView txt_confirmusers;
    @Bind(R.id.affair__xiang_qing_noconfirmusers)
    TextView txt_noconfirmusers;

    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Notice_ReturnNoiceDetail.aspx";
    private String url_answer="NoticeAnswer_AddNoticeAnswer.aspx";
    int id;
    int parentid;
    int userid;
    String heading="";
    String nickname="";
    private List<affair_XiangQingElement> affairList = new ArrayList<>();
    affairXiangQingGet affairXiangQing;
    List<confirmuserslistElement> confirmuserslist = new ArrayList<>();
    List<confirmuserslistElement> noconfirmuserslist = new ArrayList<>();
    List<noticeanswerlistElement> noticeanswerlist=new ArrayList<>();
    protected int getContentView() {
        return R.layout.activity_affair__xiang_qing;
    }
    @Override
    protected void initViews() {
        initActionBar(actionBar);

        Bundle bundle=getIntent().getExtras();
        Glide.with(this)
                .load("http://opoecp2mn.bkt.clouddn.com/"+bundle.getString("img"))
                .into(img);
        name.setText(bundle.getString("name"));
        time.setText(bundle.getString("date"));
        inform.setText(bundle.getString("state"));
        txt.setText(bundle.getString("txt"));
        sure.setText(bundle.getInt("sure")+"人查看");
        num.setText(bundle.getInt("num")+"条回复");
        id = bundle.getInt("id");
        parentid = bundle.getInt("parentid");
        userid=bundle.getInt("userid");
        heading = bundle.getString("heading");
        nickname = bundle.getString("nickname");
        btn_affairXiangQiang();
       /* *//**
         * 动态添加
         *//*
        for(int i=0;i<affairList.size();i++){
            AddAffair_XiangQing.AddAffair_XiangQing_(this,parent,affairList.get(i).getResourceId(),affairList.get(i).getName(),
                    affairList.get(i).getDate(),affairList.get(i).getTxt());
        }*/
        edit.addTextChangedListener(new EditChangedListener());

    }
    @OnClick({R.id.affair__xiang_qing_send})
    public void click(View v){
        switch (v.getId()){
            case R.id.affair__xiang_qing_send:
                btn_affairAnswer();
                break;
        }
    }
    public void initconfirmusers(List<confirmuserslistElement> confirmuserslist){
        String out ="";
        for(int i=0;i<confirmuserslist.size();i++){
            out = out+confirmuserslist.get(i).getName()+" ";
        }
        txt_confirmusers.setText(out);
    }
    public void initnoconfirmuserslist(List<confirmuserslistElement> noconfirmuserslist){
        String out ="";
        for(int i=0;i<noconfirmuserslist.size();i++){
            out = out+noconfirmuserslist.get(i).getName()+" ";
        }
        ToastUtils.showShort(Affair_XiangQingActivity.this,out);
        txt_noconfirmusers.setText(out);
    }
    private void initList(List<noticeanswerlistElement> noticeanswerlist) {
        for(int i=0;i<noticeanswerlist.size();i++){
            AddAffair_XiangQing.AddAffair_XiangQing_(this,parent,noticeanswerlist.get(i).getHeadimg(),noticeanswerlist.get(i).getUsersname(),
                    noticeanswerlist.get(i).getAnswerdate(),noticeanswerlist.get(i).getAnswercontent());

        }
    }

    /**
     * 调接口
     */
    public void btn_affairXiangQiang(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Notice_ReturnNoiceDetail(id, baseUrl, url, new CustomCallBack<RemoteDataResult<affairXiangQingGet>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<affairXiangQingGet>> response) {
                Log.d("sucess:",response.body().getResultMessage()+"  "+response.body().getResultCode());
                ToastUtils.showShort(Affair_XiangQingActivity.this,response.body().getResultMessage());
                affairXiangQing=response.body().getData();
                confirmuserslist =affairXiangQing.getConfirmuserslist();
                noconfirmuserslist = affairXiangQing.getNoconfirmuserslist();
                noticeanswerlist = affairXiangQing.getNoticeanswerlist();
                initconfirmusers(confirmuserslist);
                initnoconfirmuserslist(noconfirmuserslist);
                initList(noticeanswerlist);
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(Affair_XiangQingActivity.this,message);
            }
        });
    }

    /**
     * 回复接口
     */
    public void btn_affairAnswer(){
        AffairXiangQingAnswerElement post = new AffairXiangQingAnswerElement();
        post.setAnswercontent(edit.getText().toString());
        post.setAnswerusersid(userid);
        post.setParrentid(parentid);
        post.setAnswerstatus(0);
        post.setNoticeid(id);
        Time time = new Time();
        time.setToNow();
        final String date =String.valueOf(time.year)+"/"+String.valueOf(time.month)+"/"+String.valueOf(time.monthDay)+" "+String.valueOf(time.hour)+":"+String.valueOf(time.minute)+":"+String.valueOf(time.second);
        post.setAnswerdate(date);
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.NoticeAnswer_AddNoticeAnswer(post, baseUrl, url_answer, new CustomCallBack<RemoteDataResult>() {
            @Override
            public void onSuccess(Response<RemoteDataResult> response) {
                ToastUtils.showShort(Affair_XiangQingActivity.this,response.body().getResultMessage());
                AddAffair_XiangQing.AddAffair_XiangQing_(Affair_XiangQingActivity.this,parent,heading,nickname,
                        date,edit.getText().toString());
                edit.setText("");
            }
            @Override
            public void onFail(String message) {
                ToastUtils.showShort(Affair_XiangQingActivity.this,message);
            }
        });
    }
    class EditChangedListener implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.length()>0){
                send.setTextColor(getResources().getColor(R.color.title));
                send.setClickable(true);
            }else{
                send.setTextColor(getResources().getColor(R.color.hint));
                send.setClickable(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }




    private void initActionBar(layout_action_bar actionBar) {
        actionBar.setPriviousClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionBar.setTitle("事务详情");
        actionBar.hideAdd();
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
