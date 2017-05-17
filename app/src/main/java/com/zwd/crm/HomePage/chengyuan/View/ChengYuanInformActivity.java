package com.zwd.crm.HomePage.chengyuan.View;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zwd.crm.HomePage.chengyuan.Module.ChengYuInfoGet;
import com.zwd.crm.HomePage.taskIntroduce.View.TaskIntroduceActivity;
import com.zwd.crm.R;
import com.zwd.crm.base.BaseActivity;
import com.zwd.crm.base.BaseAppManager;
import com.zwd.crm.internet.CustomCallBack;
import com.zwd.crm.internet.RemoteDataResult;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.layout.layout_action_bar;
import com.zwd.crm.util.ToastUtils;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

public class ChengYuanInformActivity extends BaseActivity {
    @Bind(R.id.cheng_yuan_inform_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.cheng_yuan_inform_task)
    RelativeLayout task;
    @Bind(R.id.cheng_yuan_inform_heading)
    CircleImageView heading;
    @Bind(R.id.cheng_yuan_inform_name)
    TextView txt_name;
    @Bind(R.id.cheng_yuan_inform_roletype)
    TextView txt_roletype;
    @Bind(R.id.cheng_yuan_inform_department)
    TextView txt_department;
    @Bind(R.id.cheng_yuan_inform_name_2)
    TextView txt_name_2;
    @Bind(R.id.cheng_yuan_inform_phone)
    TextView txt_phone;
    @Bind(R.id.cheng_yuan_inform_email)
    TextView txt_email;
    @Bind(R.id.cheng_yuan_inform_roletype_2)
    TextView txt_roletype_2;
    @Bind(R.id.cheng_yuan_inform_department_2)
    TextView txt_department_2;
    @Bind(R.id.cheng_yuan_inform_role)
    TextView txt_role;

    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Users_ReturnUsersInfoByDepartment.aspx";

    int id;
    int usergroupId;
    ChengYuInfoGet chengYuInfoGet;

    protected int getContentView(){
        return R.layout.activity_cheng_yuan_inform;
    }

    protected void initViews() {
        initActionBar(actionBar);
        Bundle bundle = getIntent().getExtras();
        id=bundle.getInt("id");
        usergroupId=bundle.getInt("usergroupId");
        btn_ChengYuanInform();


    }
    public void initView(){
        if (chengYuInfoGet.getHeadimg()!=null){
            Glide.with(this).load("http://source.reminisce.cn/"+chengYuInfoGet.getHeadimg()).into(heading);

        }
        txt_name.setText(chengYuInfoGet.getName());
        if(chengYuInfoGet.getDepartmentSimple().getSonlist().getDepartmentname().isEmpty()){
            txt_department.setText(chengYuInfoGet.getDepartmentSimple().getDepartmentname());
            txt_department_2.setText(chengYuInfoGet.getDepartmentSimple().getDepartmentname());
            txt_role.setText(chengYuInfoGet.getDepartmentSimple().getUsersrole());
            txt_roletype.setText(chengYuInfoGet.getDepartmentSimple().getRoletype());
            txt_roletype_2.setText(chengYuInfoGet.getDepartmentSimple().getRoletype());
        }else{
            txt_department.setText(chengYuInfoGet.getDepartmentSimple().getDepartmentname()+"/"+chengYuInfoGet.getDepartmentSimple().getSonlist().getDepartmentname());
            txt_department_2.setText(chengYuInfoGet.getDepartmentSimple().getDepartmentname()+"/"+chengYuInfoGet.getDepartmentSimple().getSonlist().getDepartmentname());
            txt_role.setText(chengYuInfoGet.getDepartmentSimple().getSonlist().getUsersrole());
            txt_roletype.setText(chengYuInfoGet.getDepartmentSimple().getSonlist().getRoletype());
            txt_roletype_2.setText(chengYuInfoGet.getDepartmentSimple().getSonlist().getRoletype());
        }
        txt_name_2.setText(chengYuInfoGet.getName());
        txt_phone.setText(chengYuInfoGet.getPhone());
        txt_email.setText(chengYuInfoGet.getEmail());
    }
    @OnClick({R.id.cheng_yuan_inform_task,R.id.cheng_yuan_inform_tell})
    public void click(View v){
        switch (v.getId()){
            case R.id.cheng_yuan_inform_task:
                Bundle bundle = new Bundle();
                bundle.putString("title",chengYuInfoGet.getName());
                bundle.putInt("id",id);
                bundle.putInt("usergroupId",usergroupId);
                goActivity(TaskIntroduceActivity.class,bundle);
                break;
            case R.id.cheng_yuan_inform_tell:
                call(chengYuInfoGet.getPhone());
                break;

        }
    }
    /**
     * 拨打电话
     */
    public void call(String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    /**
     * 调接口
     * @param
     */
    public void btn_ChengYuanInform(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Users_ReturnUsersInfoByDepartment(id, baseUrl, url, new CustomCallBack<RemoteDataResult<ChengYuInfoGet>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<ChengYuInfoGet>> response) {
                chengYuInfoGet = response.body().getData();
                initView();
                ToastUtils.showShort(ChengYuanInformActivity.this,response.body().getResultMessage());
            }

            @Override
            public void onFail(String message) {
                Log.d("error:",message);
                ToastUtils.showShort(ChengYuanInformActivity.this,message);
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
        actionBar.hideAdd();
        actionBar.setTitle("成员详情");
    }
}
