package com.zwd.crm.HomePage.conferenceXiangqing.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps2d.model.Text;
import com.zwd.crm.HomePage.clientState.View.Client_StateActivity;
import com.zwd.crm.HomePage.conferenceAdd.Module.conferenceAddPost;
import com.zwd.crm.HomePage.conferenceXiangQing_Task.View.ConferenceXiangQ_TaskActivity;
import com.zwd.crm.HomePage.conferenceXiuGai.View.Conference_XiuGaiActivity;
import com.zwd.crm.HomePage.task.View.TaskActivity;
import com.zwd.crm.R;
import com.zwd.crm.base.BaseActivity;
import com.zwd.crm.layout.layout_action_bar;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;

public class Conference_XiangqingActivity extends BaseActivity {
    @Bind(R.id.conference__xiangqing_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.conference__xiangqing_kehu)
    TextView kehu;
    @Bind(R.id.conference__xiangqing_rengwu)
    TextView rengwu;

    @Bind(R.id.conference__xiangqing_name)
    TextView txt_name;
    @Bind(R.id.conference__xiangqing_startdate)
    TextView txt_startdate;
    @Bind(R.id.conference__xiangqing_enddate)
    TextView txt_enddate;
    @Bind(R.id.conference__xiangqing_address)
    TextView txt_address;
    @Bind(R.id.conference__xiangqing_description)
    TextView txt_description;
    @Bind(R.id.conference__xiangqing_state)
    TextView txt_state;



    private int listId;
    private String name;
    private String startdate;
    private String enddate;
    private String address;
    private String description;
    private String state;
    Bundle bundle;
    protected int getContentView() {
        return R.layout.activity_conference__xiangqing;
    }

    @Override
    protected void initViews() {
        bundle=getIntent().getExtras();
        listId=bundle.getInt("Listid");
        name = bundle.getString("name");
        startdate = bundle.getString("startdate");
        enddate = bundle.getString("enddate");
        address=bundle.getString("address");
        description = bundle.getString("description");
        state = bundle.getString("state");
        initActionBar(actionBar);


        /**
         * 赋值
         */
        txt_name.setText(name);
        txt_startdate.setText(startdate.replace("T"," "));
        txt_enddate.setText(enddate.replace("T"," "));
        txt_address.setText(address);
        txt_description.setText(description);
        txt_state.setText(state);




    }
    @OnClick({R.id.conference__xiangqing_rengwu,R.id.conference__xiangqing_kehu})
    public void click(View v){
        switch (v.getId()){
            case R.id.conference__xiangqing_rengwu:
                bundle = new Bundle();
                bundle.putInt("id",listId);
                goActivity(ConferenceXiangQ_TaskActivity.class,bundle);
                break;
            case R.id.conference__xiangqing_kehu:
                bundle = new Bundle();
                bundle.putInt("id",listId);
                goActivity(Client_StateActivity.class,bundle);
                break;
        }
    }

    private void initActionBar(layout_action_bar actionBar) {


        actionBar.setPriviousClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionBar.setTitle(name);
        actionBar.setImage(R.mipmap.change);
        actionBar.setAddClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("Listid",listId);
                bundle.putString("name",txt_name.getText().toString());
                bundle.putString("startdate",txt_startdate.getText().toString());
                bundle.putString("enddate",txt_enddate.getText().toString());
                bundle.putString("address",txt_address.getText().toString());
                bundle.putString("description",txt_address.getText().toString());
                bundle.putString("state",txt_state.getText().toString());
                goActivityForResult(Conference_XiuGaiActivity.class,bundle,0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 0:
                if(resultCode==0){
                    Bundle bundle = data.getExtras();
                    conferenceAddPost back= (conferenceAddPost)bundle.getSerializable("back");
                    initview(back);
                }
        }
    }

    private void initview(conferenceAddPost back) {
        name = back.getName();

        txt_name.setText(back.getName());
        txt_startdate.setText(back.getStartdate());
        txt_enddate.setText(back.getEnddate());
        txt_address.setText(back.getAddress());
        txt_description.setText(back.getDescription());
        String state_txt="";
        switch (back.getState()){
            case 0:
                state_txt="筹备中";
                break;
            case 1:
                state_txt="展览中";
                break;
            case 2:
                state_txt="整理中";
                break;
            case 3:
                state_txt="完结";
                break;
        }
        txt_state.setText(state_txt);

    }
}
