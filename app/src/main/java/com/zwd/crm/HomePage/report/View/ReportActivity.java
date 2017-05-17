package com.zwd.crm.HomePage.report.View;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwd.crm.HomePage.inform.View.informFragment;
import com.zwd.crm.HomePage.reportNew.View.ReportNewActivity;
import com.zwd.crm.HomePage.reportReceive.View.Report_ReceiveFragment;
import com.zwd.crm.HomePage.reportSend.View.Report_SendFragment;
import com.zwd.crm.R;
import com.zwd.crm.adapter.MyViewPagerAdapter;
import com.zwd.crm.base.BaseActivity;
import com.zwd.crm.base.BaseFragment;
import com.zwd.crm.layout.layout_action_bar;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

import static android.R.id.message;

public class ReportActivity extends BaseActivity {
    @Bind(R.id.report_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.report_tab)
    TabLayout tabLayout;
    @Bind(R.id.report_viewpager)
    ViewPager viewPager;

    AlertDialog dialog;
    AlertDialog.Builder builder;
    View outerView;
    private ArrayList<BaseFragment> fgms;
    private ArrayList<String> mTitle;
    public int createId;
    int usergroupid;
    public String heading;
    private String token;
    protected int getContentView() {
        return R.layout.activity_report;
    }

    @Override
    protected void initViews() {
        Bundle bundle = getIntent().getExtras();
        createId=bundle.getInt("createId");
        usergroupid=bundle.getInt("usergroupid");
        heading =bundle.getString("heading");
        token = bundle.getString("token");

        initActionBar(actionBar);
        fgms = new ArrayList<>();
        mTitle =new ArrayList<>();
        mTitle.add("我收到的");
        mTitle.add("我发出的");
        BaseFragment mTab_01 = new Report_ReceiveFragment();
        BaseFragment mTab_02 = new Report_SendFragment();
        fgms.add(mTab_01);
        fgms.add(mTab_02);
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager(),mTitle,fgms);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);
    }

    private void initActionBar(layout_action_bar actionBar) {
        actionBar.setPriviousClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionBar.setTitle("汇报");
        actionBar.setImage(R.mipmap.add);
        actionBar.setAddClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder =new AlertDialog.Builder(v.getContext());
                outerView= LayoutInflater.from(v.getContext()).inflate(R.layout.layout_report_array, null);
                LinearLayout day =(LinearLayout)outerView.findViewById(R.id.report_dialog_day);
                LinearLayout week =(LinearLayout)outerView.findViewById(R.id.report_dialog_week);
                LinearLayout month =(LinearLayout)outerView.findViewById(R.id.report_dialog_month);
                day.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("title","日");
                        bundle.putInt("createId",createId);
                        bundle.putInt("usergroupid",usergroupid);
                        bundle.putString("token",token);
                        goActivity(ReportNewActivity.class,bundle);
                        dialog.dismiss();
                    }
                });
                week.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("title","周");
                        bundle.putInt("createId",createId);
                        bundle.putInt("usergroupid",usergroupid);
                        bundle.putString("token",token);
                        goActivity(ReportNewActivity.class,bundle);
                        dialog.dismiss();
                    }
                });
                month.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("title","月");
                        bundle.putInt("createId",createId);
                        bundle.putInt("usergroupid",usergroupid);
                        bundle.putString("token",token);
                        goActivity(ReportNewActivity.class,bundle);
                        dialog.dismiss();
                    }
                });
                builder.setView(outerView);
                dialog =builder.create();
                dialog.show();
            }
        });
    }


}
