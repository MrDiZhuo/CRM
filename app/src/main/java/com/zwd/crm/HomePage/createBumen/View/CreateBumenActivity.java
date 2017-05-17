package com.zwd.crm.HomePage.createBumen.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zwd.crm.HomePage.createBumen.Module.creatBumenGet;
import com.zwd.crm.HomePage.createBumen.Module.creatBumenPost;
import com.zwd.crm.HomePage.group.View.groupFragment;
import com.zwd.crm.HomePage.inform.Module.InformGet;
import com.zwd.crm.HomePage.inform.Module.subjectdepartmentElement;
import com.zwd.crm.HomePage.xiugaiBuMen.View.XiuGaiBmActivity;
import com.zwd.crm.R;
import com.zwd.crm.base.BaseActivity;
import com.zwd.crm.internet.CustomCallBack;
import com.zwd.crm.internet.RemoteDataResult;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.layout.layout_action_bar;
import com.zwd.crm.util.ToastUtils;
import com.zwd.crm.widget.WheelView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Response;

public class CreateBumenActivity extends BaseActivity {
    @Bind(R.id.create_bumen_action_bar)
    layout_action_bar actionbar;
    @Bind(R.id.create_bumen_choose)
    TextView choose;
    @Bind(R.id.create_bumen_txt_name)
    EditText txt_name;
    @Bind(R.id.create_bumen_content)
    EditText txt_content;

    ArrayList<String> mArrayList;
    AlertDialog.Builder builder;
    View outerView;
    String choose_txt;

    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Department_AddDepartment.aspx";
    private String url_department="Department_ReturnDepartmentStructureFromUsersGroup.aspx";

    private int usergroupid;
    private int parentid;
    Intent intent;
    boolean flag=false;

    creatBumenGet get ;


    ArrayList<subjectdepartmentElement> subjectdepartmentElements ;
    protected int getContentView() {
        return R.layout.activity_create_bumen;
    }

    @Override
    protected void initViews() {
        initActionBar(actionbar);
        intent = getIntent();
        usergroupid = intent.getExtras().getInt("usergroupid");
        btn_group_department();

    }

    /**
     * 获得从属部门
     */
    public void btn_group_department(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Department_ReturnDepartmentStructureFromUsersGroup(usergroupid, baseUrl, url_department, new CustomCallBack<RemoteDataResult<InformGet>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<InformGet>> response) {
                ToastUtils.showShort(CreateBumenActivity.this,response.body().getResultMessage());
                InformGet informGet = response.body().getData();
                subjectdepartmentElements = informGet.getSubjectdepartment();
                mArrayList =initlist(subjectdepartmentElements);
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(CreateBumenActivity.this,message);
            }
        });
    }


    private void initActionBar(layout_action_bar actionbar) {
        actionbar.setPriviousClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag==true){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("name",get);
                    intent.putExtras(bundle);
                    CreateBumenActivity.this.setResult(0, intent);
                    CreateBumenActivity.this.finish();
                }else {
                    Bundle bundle = new Bundle();
                    creatBumenGet get = new creatBumenGet();
                    get.setId(0);get.setUsersgroupid(0);get.setParentid(0);
                    get.setDepartmentintro("");get.setDepartmentname(" ");
                    bundle.putSerializable("name",get);
                    intent.putExtras(bundle);
                    CreateBumenActivity.this.setResult(0, intent);
                    CreateBumenActivity.this.finish();
                }

            }
        });
        actionbar.setTitle("创建部门");
        actionbar.setImage(R.mipmap.finish);
        actionbar.setAddClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_createBumen();

            }
        });
    }
    private ArrayList<String> initlist(ArrayList<subjectdepartmentElement> subjectdepartmentElements) {
        ArrayList<String> list =new ArrayList<>();
        for(int i=0;i<subjectdepartmentElements.size();i++){
            list.add(subjectdepartmentElements.get(i).getDepartmentname());
        }
        return list;
    }

    /**
     * 调接口
     */
    public void btn_createBumen(){
        if(check()){
            creatBumenPost post = new creatBumenPost();
            post.setDepartmentname(txt_name.getText().toString());
            post.setDepartmentintro(txt_content.getText().toString());
            if(choose.getText().toString().isEmpty()){
                parentid=0;
            }else {
                for(int i=0;i<subjectdepartmentElements.size();i++){
                    if (subjectdepartmentElements.get(i).getDepartmentname().equals(choose_txt)){
                        parentid=subjectdepartmentElements.get(i).getDepartmentid();
                    }
                }
            }
            post.setParentid(parentid);
            post.setUsersgroupid(usergroupid);
            RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
            remoteOptionIml.Department_AddDepartment(post, baseUrl, url, new CustomCallBack<RemoteDataResult<creatBumenGet>>() {
                @Override
                public void onSuccess(Response<RemoteDataResult<creatBumenGet>> response) {
                    flag = true;
                    get = response.body().getData();
                    ToastUtils.showShort(CreateBumenActivity.this,response.body().getResultMessage());
                    //ToastUtils.showShort(CreateBumenActivity.this,response.body().getResultMessage());
                   /* Bundle bundle = new Bundle();
                    bundle.putSerializable("name",response.body().getData());
                    intent.putExtras(bundle);
                    CreateBumenActivity.this.setResult(0, intent);
                    CreateBumenActivity.this.finish();*/
                }

                @Override
                public void onFail(String message) {
                    ToastUtils.showShort(CreateBumenActivity.this,message);
                }
            });
        }
    }

    /**
     * 判空
     */
    public boolean check(){
        if(txt_name.getText().toString().isEmpty()||txt_content.getText().toString().isEmpty()){
            ToastUtils.showShort(CreateBumenActivity.this,"不能为空!");
            return false;
        }else return true;
    }


    @OnClick({R.id.create_bumen_choose})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.create_bumen_choose:
                builder =new AlertDialog.Builder(this);
                outerView= LayoutInflater.from(this).inflate(R.layout.layout_createbumen_dialog, null);
                WheelView wv = (WheelView) outerView.findViewById(R.id.wv);
                wv.setOffset(2);
                //设置每一个Item中的数据 mArrayList中装着的是一堆String字符串
                wv.setItems(mArrayList);
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
                        choose.setText(choose_txt);
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;

        }
    }
    /**
     *键盘返回
     */
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (flag==true){
                Bundle bundle = new Bundle();
                bundle.putSerializable("name",get);
                intent.putExtras(bundle);
                CreateBumenActivity.this.setResult(0, intent);
                CreateBumenActivity.this.finish();
            }else {
                Bundle bundle = new Bundle();
                creatBumenGet get = new creatBumenGet();
                get.setId(0);get.setUsersgroupid(0);get.setParentid(0);
                get.setDepartmentintro("");get.setDepartmentname(" ");
                bundle.putSerializable("name",get);
                intent.putExtras(bundle);
                CreateBumenActivity.this.setResult(0, intent);
                CreateBumenActivity.this.finish();
            }
        }
        return true;
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
