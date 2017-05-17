package com.zwd.crm.HomePage.apply.View;

import android.content.Context;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.zwd.crm.HomePage.apply.Module.applyPost;
import com.zwd.crm.HomePage.inform.Module.InformGet;
import com.zwd.crm.HomePage.inform.Module.subjectdepartmentElement;
import com.zwd.crm.R;
import com.zwd.crm.adapter.WrapperExpandableListAdapter;
import com.zwd.crm.adapter.adjust_adapter;
import com.zwd.crm.base.BaseActivity;
import com.zwd.crm.internet.CustomCallBack;
import com.zwd.crm.internet.RemoteDataResult;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.layout.layout_action_bar;
import com.zwd.crm.util.ToastUtils;
import com.zwd.crm.widget.FloatingGroupExpandableListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

public class ApplyActivity extends BaseActivity {
    @Bind(R.id.apply_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.apply_img)
    CircleImageView img;
    @Bind(R.id.apply_name)
    TextView name;
    @Bind(R.id.apply_tel)
    TextView tel;
    @Bind(R.id.apply_list)
    FloatingGroupExpandableListView listView;
    @Bind(R.id.apply_role)
    EditText edit_role;
    @Bind(R.id.apply_roletype)
    EditText edit_roletype;

    private int id;
    private int usergroupId;
    private String baseUrl="http://139.224.164.183:8088/";
    private String url_department="Department_ReturnDepartmentStructureFromUsersGroup.aspx";
    private String url_apply="UsersDepartment_AddUsers.aspx";

    private int departmentId;

    private com.zwd.crm.adapter.adjust_adapter adjust_adapter;
    WrapperExpandableListAdapter wrapperExpandableListAdapter;

    private ArrayList<subjectdepartmentElement> father = new ArrayList<>();
    private ArrayList<ArrayList<subjectdepartmentElement>> child = new ArrayList<>();

    protected int getContentView() {
        return R.layout.activity_apply;
    }

    @Override
    protected void initViews() {
        Bundle bundle =getIntent().getExtras();
        img.setImageResource(bundle.getInt("img"));
        name.setText(bundle.getString("name"));
        tel.setText(bundle.getString("tell"));
        usergroupId = bundle.getInt("usergroupId");
        id = bundle.getInt("id");
        initActionBar(actionBar);
        btn_department();

    }

    private void initView() {
        adjust_adapter = new adjust_adapter(ApplyActivity.this,father,child);
        wrapperExpandableListAdapter = new WrapperExpandableListAdapter(adjust_adapter);
        listView.setGroupIndicator(null);
        listView.setAdapter(wrapperExpandableListAdapter);
        for(int i =0;i<wrapperExpandableListAdapter.getGroupCount();i++){
            listView.expandGroup(i);
            listView.collapseGroup(i);
        }
        listView.setOnScrollFloatingGroupListener(new FloatingGroupExpandableListView.OnScrollFloatingGroupListener() {

            @Override
            public void onScrollFloatingGroupListener(View floatingGroupView, int scrollY) {
                float interpolation = -scrollY / (float) floatingGroupView.getHeight();
            }
        });
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ToastUtils.showShort(v.getContext(),"选择的是："+child.get(groupPosition).get(childPosition).getDepartmentname());
                departmentId = child.get(groupPosition).get(childPosition).getDepartmentid();
                return true;
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
        actionBar.setTitle("用户部门");
        actionBar.setImage(R.mipmap.finish);
        actionBar.setAddClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_apply();
            }
        });
    }


    public void initList(List<subjectdepartmentElement> subjectDepartments){
       for(int i=0;i<subjectDepartments.size();i++){
           subjectdepartmentElement father1=new subjectdepartmentElement(subjectDepartments.get(i).getDepartmentid(),subjectDepartments.get(i).getDepartmentname(),
                   subjectDepartments.get(i).getSubdepartment());
           father.add(father1);
           Log.d("id",father1.getDepartmentid()+" ");
           List<subjectdepartmentElement> child1 = father1.getSubdepartment();
           if(child1==null){
               ArrayList<subjectdepartmentElement> child2 = new ArrayList<>();
               subjectdepartmentElement child3 = new subjectdepartmentElement(0,"无附属部门",null);
               child2.add(child3);
               child.add(child2);
           }else {
               ArrayList<subjectdepartmentElement> child2 = new ArrayList<>();
               for(int j=0;j<child1.size();j++){
                   subjectdepartmentElement child3 = new subjectdepartmentElement(child1.get(j).getDepartmentid(),
                           child1.get(j).getDepartmentname(),child1.get(j).getSubdepartment());
                   child2.add(child3);
               }
               child.add(child2);
           }
       }


    }
    /**
     * 调接口-返回部门
     */
    public void btn_department(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Department_ReturnDepartmentStructureFromUsersGroup(usergroupId, baseUrl, url_department, new CustomCallBack<RemoteDataResult<InformGet>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<InformGet>> response) {
                ToastUtils.showShort(ApplyActivity.this,response.body().getResultMessage());
                InformGet informGet = response.body().getData();
                List<subjectdepartmentElement> subjectDepartments = informGet.getSubjectdepartment();
                initList(subjectDepartments);
                initView();
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(ApplyActivity.this,message);
            }
        });
    }

    /**
     * 调接口-提交
     */
    public void btn_apply(){
        if (check()){
            applyPost applypost = new applyPost();
            applypost.setUsersid(id);/////名字必须在用户表中
            applypost.setDepartmentid(departmentId);
            applypost.setRole(edit_role.getText().toString());
            applypost.setRoletype(edit_roletype.getText().toString());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(new Date()); applypost.setDate(date);
          //  Log.d("time",date);
            RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
            remoteOptionIml.UsersDepartment_AddUsers(applypost, baseUrl, url_apply, new CustomCallBack<RemoteDataResult>() {
                @Override
                public void onSuccess(Response<RemoteDataResult> response) {
                    ToastUtils.showShort(ApplyActivity.this,response.body().getResultMessage());
                }

                @Override
                public void onFail(String message) {
                    ToastUtils.showShort(ApplyActivity.this,message);
                    Log.d("falure:",message);
                }
            });
        }
    }

    public boolean check(){
        if(departmentId==0){
            ToastUtils.showShort(ApplyActivity.this,"请选择部门！");
            return false;
        }else {
            if(edit_role.getText().toString().isEmpty()||edit_roletype.getText().toString().isEmpty()){
                ToastUtils.showShort(ApplyActivity.this,"不能为空！");
                return false;
            }else return true;
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

}
