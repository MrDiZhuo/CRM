package com.zwd.crm.HomePage.conferenceXiuGai.View;

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
import android.widget.EditText;
import android.widget.TextView;

import com.zwd.crm.HomePage.choosePerson.View.ChoosePersonActivity;
import com.zwd.crm.HomePage.conferenceAdd.Module.conferenceAddPost;
import com.zwd.crm.HomePage.conferenceAdd.View.ConfereceAddActivity;
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

public class Conference_XiuGaiActivity extends BaseActivity {
    @Bind(R.id.conference__xiu_gai_layout_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.conference__xiu_gai_start_year)
    TextView start_year;
    @Bind(R.id.conference__xiu_gai_start_month)
    TextView start_month;
    @Bind(R.id.conference__xiu_gai_start_day)
    TextView start_day;
    @Bind(R.id.conference__xiu_gai_end_year)
    TextView end_year;
    @Bind(R.id.conference__xiu_gai_end_month)
    TextView end_month;
    @Bind(R.id.conference__xiu_gai_end_day)
    TextView end_day;
    @Bind(R.id.conference__xiu_gai_layout_state)
    TextView txt_state;
    @Bind(R.id.conference__xiu_gai_edit_name)
    EditText edit_name;
    @Bind(R.id.conference__xiu_gai_edit_address)
    EditText edit_address;
    @Bind(R.id.conference__xiu_gai_edit_description)
    EditText edit_description;

    ArrayList<String> mArrayList_year;
    ArrayList<String> mArrayList_month;
    ArrayList<String> mArrayList_day;

    AlertDialog dialog;
    AlertDialog.Builder builder;
    View outerView;
    String choose_txt;

    private int listId;
    private String name;
    private String startdate;
    private String enddate;
    private String address;
    private String description;
    private String state;
    private int stateNum;

    String baseUrl="http://139.224.164.183:8088/";
    String url="Exhibition_EditExhibition.aspx";

    private conferenceAddPost post;
    private conferenceAddPost back = new conferenceAddPost();

    protected int getContentView() {
        return R.layout.activity_conference__xiu_gai;
    }

    @Override
    protected void initViews() {
        initActionBar(actionBar);
        mArrayList_year = initListYear();
        mArrayList_month = initListMonth();
        mArrayList_day = initListday();

        Bundle bundle=getIntent().getExtras();
        listId = bundle.getInt("Listid");
        name = bundle.getString("name");
        startdate = bundle.getString("startdate");
        enddate = bundle.getString("enddate");
        address=bundle.getString("address");
        description = bundle.getString("description");
        state = bundle.getString("state");

        edit_name.setText(name);
        start_year.setText(startdate.substring(0,4));
        start_month.setText(startdate.substring(5,7));
        start_day.setText(startdate.substring(8,10));
        end_year.setText(enddate.substring(0,4));
        end_month.setText(enddate.substring(5,7));
        end_day.setText(enddate.substring(8,10));
        edit_address.setText(address);
        edit_description.setText(description);
        txt_state.setText(state);

        back.setName(edit_name.getText().toString());
        back.setStartdate(start_year.getText().toString()+"-"+start_month.getText().toString()+"-"+start_day.getText().toString()+" "+"12"+":"+"12"+":"+"12");
        back.setEnddate(end_year.getText().toString()+"-"+end_month.getText().toString()+"-"+end_day.getText().toString()+" "+"12"+":"+"12"+":"+"12");
        back.setAddress(edit_address.getText().toString());
        back.setDescription(edit_description.getText().toString());
        back.setState(stateNum);
        back.setUsersgroupid(listId);

    }



    @OnClick({R.id.conference__xiu_gai_start_year,R.id.conference__xiu_gai_start_month,R.id.conference__xiu_gai_start_day
            ,R.id.conference__xiu_gai_end_year,R.id.conference__xiu_gai_end_month,R.id.conference__xiu_gai_end_day
            ,R.id.conference__xiu_gai_layout_state})
    public void click(View v){
        switch (v.getId()){
            case R.id.conference__xiu_gai_start_year:
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
                        start_year.setText(choose_txt);
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
            case R.id.conference__xiu_gai_start_month:
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
                        start_month.setText(choose_txt);
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
            case R.id.conference__xiu_gai_start_day:
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
                        start_month.setText(choose_txt);
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
            case R.id.conference__xiu_gai_end_year:
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
                        end_year.setText(choose_txt);
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
            case R.id.conference__xiu_gai_end_month:
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
                        end_month.setText(choose_txt);
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
            case R.id.conference__xiu_gai_end_day:
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
                        end_day.setText(choose_txt);
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
            case R.id.conference__xiu_gai_layout_state:
                builder =new AlertDialog.Builder(this);
                outerView= LayoutInflater.from(this).inflate(R.layout.layout_conference_xiu_gai_dialog, null);
                TextView state_1 =(TextView) outerView.findViewById(R.id.conference_xiu_dialog_state_1);
                TextView state_2 =(TextView) outerView.findViewById(R.id.conference_xiu_dialog_state_2);
                TextView state_3 =(TextView) outerView.findViewById(R.id.conference_xiu_dialog_state_3);
                TextView state_4 =(TextView) outerView.findViewById(R.id.conference_xiu_dialog_state_4);
                state_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_state.setText("筹备中");
                        stateNum=0;
                        dialog.dismiss();
                    }
                });
                state_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_state.setText("展览中");
                        stateNum=1;
                        dialog.dismiss();
                    }
                });
                state_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_state.setText("整理中");
                        stateNum=2;
                        dialog.dismiss();
                    }
                });
                state_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_state.setText("完结");
                        stateNum=3;
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

    private void initActionBar(layout_action_bar actionBar) {
        actionBar.setPriviousClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("back",back);
                intent.putExtras(bundle);
                Conference_XiuGaiActivity.this.setResult(0,intent);
                Conference_XiuGaiActivity.this.finish();
            }
        });
        actionBar.setTitle("修改会展");
        actionBar.setImage(R.mipmap.finish);
        actionBar.setAddClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_edit();
            }
        });
    }

    /**
     * 接口
     */
    public void initPost(){
        post = new conferenceAddPost();
        post.setName(edit_name.getText().toString());
        post.setStartdate(start_year.getText().toString()+"-"+start_month.getText().toString()+"-"+start_day.getText().toString()+" "+"12"+":"+"12"+":"+"12");
        post.setEnddate(end_year.getText().toString()+"-"+end_month.getText().toString()+"-"+end_day.getText().toString()+" "+"12"+":"+"12"+":"+"12");
        post.setAddress(edit_address.getText().toString());
        post.setDescription(edit_description.getText().toString());
        post.setState(stateNum);
        post.setUsersgroupid(listId);
    }
    public void btn_edit(){
        initPost();
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Exhibition_EditExhibition(post, baseUrl, url, new CustomCallBack<RemoteDataResult>() {
            @Override
            public void onSuccess(Response<RemoteDataResult> response) {
                back=post;
                ToastUtils.showShort(Conference_XiuGaiActivity.this,"123"+response.body().getResultMessage());

            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(Conference_XiuGaiActivity.this,"456"+message);

            }
        });
    }
    /**
     *键盘返回
     */
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = getIntent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("back",back);
            intent.putExtras(bundle);
            Conference_XiuGaiActivity.this.setResult(0,intent);
            Conference_XiuGaiActivity.this.finish();
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
