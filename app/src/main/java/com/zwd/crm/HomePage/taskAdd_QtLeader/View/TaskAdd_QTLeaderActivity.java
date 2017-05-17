package com.zwd.crm.HomePage.taskAdd_QtLeader.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.zwd.crm.HomePage.choosePerson.Controller.ChooseTitleItemDecoration;
import com.zwd.crm.HomePage.choosePerson.Module.ChoosePersonElement;
import com.zwd.crm.HomePage.choosePerson.Module.ChoosePersonGet;
import com.zwd.crm.HomePage.taskAdd_BumenLeader.View.TaskAdd_BmLeaderActivity;
import com.zwd.crm.R;
import com.zwd.crm.adapter.ChoosePersonAdapter;
import com.zwd.crm.adapter.MultiItemTypeAdapter;
import com.zwd.crm.base.BaseActivity;
import com.zwd.crm.layout.layout_action_bar;
import com.zwd.crm.widget.IndexBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class TaskAdd_QTLeaderActivity extends BaseActivity{
    @Bind(R.id.task_add__qtleader_bar)
    layout_action_bar actionBar;
    @Bind(R.id.task_add__qtleader_rv)
    RecyclerView mRv;
    @Bind(R.id.task_add__qtleader_indexBar)
    IndexBar mIndexBar;
    @Bind(R.id.task_add__qtleader_tvSideBarHint)
    TextView mTvSideBarHint;

    private List<ChoosePersonGet> choosePersonGets=new ArrayList<>();
    Bundle bundle;
    ChoosePersonAdapter mAdapter;

    private LinearLayoutManager mManager;


    private ChooseTitleItemDecoration mDecoration ;
    String person="";
    int Id;


    protected int getContentView() {
        return R.layout.activity_task_add__qtleader;
    }
    @Override
    protected void initViews() {
        bundle= getIntent().getExtras();
        choosePersonGets =(List<ChoosePersonGet>) bundle.getSerializable("personList");
        initActionBar(actionBar);

        mRv.addItemDecoration(mDecoration= new ChooseTitleItemDecoration(this,choosePersonGets));
        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
                .setmSourceDatas(choosePersonGets)
                .invalidate();//设置数据源
        mIndexBar.setVisibility(View.VISIBLE);

        mRv.setLayoutManager(mManager = new LinearLayoutManager(this));
        mAdapter = new ChoosePersonAdapter(this, choosePersonGets);
        mRv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("目标为"+choosePersonGets.get(position).getName());
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        person =choosePersonGets.get(position).getName();
                        for(int i=0;i<choosePersonGets.size();i++){
                            if(choosePersonGets.get(i).getName().equals(person)){
                                Id=choosePersonGets.get(i).getId();
                            }
                        }
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


    }

    private void initActionBar(layout_action_bar actionBar) {
        actionBar.setPriviousClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =getIntent();
                intent1.putExtra("person",person);
                intent1.putExtra("personId",Id);
                TaskAdd_QTLeaderActivity.this.setResult(0,intent1);

                TaskAdd_QTLeaderActivity.this.finish();
            }
        });
        actionBar.setTitle("选择目标");
        actionBar.hideAdd();
    }


    /**
     *键盘返回
     */
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent =getIntent();
            intent.putExtra("person",person);
            intent.putExtra("personId",Id);
            TaskAdd_QTLeaderActivity.this.setResult(0,intent);

            TaskAdd_QTLeaderActivity.this.finish();
        }
        return true;
    }



}
