package com.zwd.crm.HomePage.xiugaiBuMen.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.zwd.crm.HomePage.createBumen.Module.creatBumenGet;
import com.zwd.crm.HomePage.createBumen.View.CreateBumenActivity;
import com.zwd.crm.HomePage.inform.Module.InformGet;
import com.zwd.crm.HomePage.inform.Module.subjectdepartmentElement;
import com.zwd.crm.HomePage.xiugaiBuMen.Module.XiuGaiBMPost;
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

public class XiuGaiBmActivity extends BaseActivity {
    @Bind(R.id.xiugai_bumen_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.xiugai_bumen_choose)
    TextView choose;
    @Bind(R.id.xiugai_bumen_edit)
    EditText edit;

    public ArrayList<subjectdepartmentElement> subjectDepartments;
    private String baseUrl="http://139.224.164.183:8088/";
    private String url_department="Department_ReturnDepartmentStructureFromUsersGroup.aspx";
    private String url = "Department_EditDepartmentStructure.aspx";
    int usergroupId;
    int id;
    int parentid;
    int position;
    ArrayList<String> mArrayList;
    AlertDialog.Builder builder;
    View outerView;
    String choose_txt;
    Intent intent;

    protected int getContentView() {
        return R.layout.activity_xiu_gai_bm;
    }

    @Override
    protected void initViews() {
        intent =getIntent();
        Bundle bundle =intent.getExtras();
        usergroupId=bundle.getInt("usergroupId");
        id=bundle.getInt("id");
        position=bundle.getInt("position");
        btn_group_department();
        initActionBar(actionBar);

    }
    /**
     * 调整部门接口
     */
    public void btn_xiugai_bumen(){
        if(check()){
            XiuGaiBMPost post = new XiuGaiBMPost();
            post.setId(id);post.setDepartmentintro(edit.getText().toString());
            if(choose.getText().toString().isEmpty()){
                parentid=0;
            }else {
                for(int i=0;i<subjectDepartments.size();i++){
                    if (subjectDepartments.get(i).getDepartmentname().equals(choose_txt)){
                        parentid=subjectDepartments.get(i).getDepartmentid();
                    }
                }
            }
            post.setParentId(parentid);
            RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
            remoteOptionIml.Department_EditDepartment(post, baseUrl, url, new CustomCallBack<RemoteDataResult>() {
                @Override
                public void onSuccess(Response<RemoteDataResult> response) {
                    ToastUtils.showShort(XiuGaiBmActivity.this,response.body().getResultMessage());
                }

                @Override
                public void onFail(String message) {
                    ToastUtils.showShort(XiuGaiBmActivity.this,message);
                }
            });
        }

    }

    /**
     * 判空
     */
    public boolean check(){
        if(edit.getText().toString().isEmpty()){
            ToastUtils.showShort(this,"不能为空!");
            return false;
        }else return true;
    }
    /**
     * 获得从属部门
     */
    public void btn_group_department(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Department_ReturnDepartmentStructureFromUsersGroup(usergroupId, baseUrl, url_department, new CustomCallBack<RemoteDataResult<InformGet>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<InformGet>> response) {
                ToastUtils.showShort(XiuGaiBmActivity.this,response.body().getResultMessage());
                InformGet informGet = response.body().getData();
                subjectDepartments = informGet.getSubjectdepartment();
                mArrayList =initlist(subjectDepartments);
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(XiuGaiBmActivity.this,message);
            }
        });
    }


    @OnClick({R.id.xiugai_bumen_choose})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.xiugai_bumen_choose:
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

    private ArrayList<String> initlist(ArrayList<subjectdepartmentElement> subjectdepartmentElements) {
        ArrayList<String> list =new ArrayList<>();
        for(int i=0;i<subjectdepartmentElements.size();i++){
            list.add(subjectdepartmentElements.get(i).getDepartmentname());
        }
        return list;
    }

    private void initActionBar(layout_action_bar actionBar) {
        String data = intent.getStringExtra("title");
        actionBar.setTitle(data);
        actionBar.setPriviousClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("parentid",parentid);
                bundle.putInt("position",position);
                intent.putExtras(bundle);
                XiuGaiBmActivity.this.setResult(0,intent);
                XiuGaiBmActivity.this.finish();
            }
        });
        actionBar.setImage(R.mipmap.finish);
        actionBar.setAddClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_xiugai_bumen();
            }
        });
    }

    /**
     *键盘返回
     */
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Bundle bundle = new Bundle();
            bundle.putInt("parentid",parentid);
            bundle.putInt("position",position);
            intent.putExtras(bundle);
            XiuGaiBmActivity.this.setResult(0,intent);
            XiuGaiBmActivity.this.finish();
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
