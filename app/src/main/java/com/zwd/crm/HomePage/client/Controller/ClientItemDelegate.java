package com.zwd.crm.HomePage.client.Controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.zwd.crm.HomePage.client.Module.ClientBeizhuPost;
import com.zwd.crm.HomePage.client.Module.ClientElement;
import com.zwd.crm.HomePage.client.Module.ClientStatePost;
import com.zwd.crm.HomePage.client.Module.clientGet;
import com.zwd.crm.HomePage.client.View.ClientActivity;
import com.zwd.crm.R;
import com.zwd.crm.adapter.ClientAdapter;
import com.zwd.crm.base.BaseAppManager;
import com.zwd.crm.interfaze.ItemViewDelegate;
import com.zwd.crm.interfaze.OnItemClickListener;
import com.zwd.crm.internet.CustomCallBack;
import com.zwd.crm.internet.RemoteDataResult;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.util.ToastUtils;
import com.zwd.crm.widget.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Response;

/**
 * Created by asus-pc on 2017/3/16.
 */

public class ClientItemDelegate implements ItemViewDelegate<clientGet> {
    String baseUrl = "http://139.224.164.183:8088/";
    String url="CustomContract_EditCustomContractStatus.aspx";
    String url_beizhu="OperationBackup_AddOperationBackupByCustom.aspx";
    int customcontractstatus;

    String status="";

    AlertDialog dialog;
    AlertDialog.Builder builder;
    View outerView;
    private ButtonInterface buttonInterface;

    public interface ButtonInterface{
        public void onclick(View view,int position);
    }

    public ClientItemDelegate setButtonInterface(ButtonInterface buttonInterface) {
        this.buttonInterface = buttonInterface;
        return this;
    }

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.item_client_delegate;
    }

    @Override
    public boolean isForViewType(clientGet item, int position)
    {
        return true;
    }

    @Override
    public void convert(final ViewHolder holder, final clientGet party, final int position)
    {
        final ClientActivity clientActivity = (ClientActivity)BaseAppManager.getInstance().getForwardActivity();
        if(party.getCustomcontractstatus()==0){
            status="初始客户";
        }else if(party.getCustomcontractstatus()==1){
            status="已签约客户";
        }else if(party.getCustomcontractstatus()==2){
            status="无意向客户";
        }else if(party.getCustomcontractstatus()==3){
            status ="有意向客户";
        }
        holder.setText(R.id.item_client_name,party.getContractname());
        holder.setText(R.id.item_client_link,party.getCustomname());
        holder.setText(R.id.item_client_poaition,party.getCustompostion());
        holder.setText(R.id.item_client_creator,party.getUsersname());
        holder.setText(R.id.item_client_executor,party.getTasklanchername());
        holder.setText(R.id.item_client_state,status);
        holder.setOnClickListener(R.id.item_client_delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonInterface!=null){
                    buttonInterface.onclick(v,position);
                } else {
                    Log.e("------", "kong");
                }
            }
        });
        holder.setOnClickListener(R.id.item_client_call, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              ////电话
            }
        });
        holder.setOnClickListener(R.id.item_client_new_remark, new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                builder =new AlertDialog.Builder(BaseAppManager.getInstance().getForwardActivity());
                outerView= LayoutInflater.from(BaseAppManager.getInstance().getForwardActivity()).inflate(R.layout.layout_client_beizhu_dialog, null);
                final EditText edit = (EditText)outerView.findViewById(R.id.dialog_client_beizhu_edit);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (edit.getText().toString().isEmpty()){
                            ToastUtils.showShort(v.getContext(),"请输入内容！");
                        }else {
                            btn_editBeizhu(v.getContext(),party.getId(),clientActivity.createId,edit.getText().toString());

                            InputMethodManager inputMethodManager =(InputMethodManager)BaseAppManager.getInstance().getForwardActivity().getApplicationContext().
                                    getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputMethodManager.hideSoftInputFromWindow(edit.getWindowToken(), 0); //隐藏

                            dialog.dismiss();
                        }


                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        InputMethodManager inputMethodManager =(InputMethodManager)BaseAppManager.getInstance().getForwardActivity().getApplicationContext().
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(edit.getWindowToken(), 0); //隐藏

                        dialog.dismiss();
                    }
                });
                builder.setView(outerView);
                dialog =builder.create();
                dialog.show();

            }
        });
        holder.setOnClickListener(R.id.item_client_new_state, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder =new AlertDialog.Builder(BaseAppManager.getInstance().getForwardActivity());
                outerView= LayoutInflater.from(BaseAppManager.getInstance().getForwardActivity()).inflate(R.layout.layout_client_dialog, null);
                TextView state_1 =(TextView) outerView.findViewById(R.id.client_dialog_state_1);
                TextView state_2 =(TextView) outerView.findViewById(R.id.client_dialog_state_2);
                TextView state_3 =(TextView) outerView.findViewById(R.id.client_dialog_state_3);
                TextView state_4 =(TextView) outerView.findViewById(R.id.client_dialog_state_4);
                state_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        status="初始客户";
                        holder.setText(R.id.item_client_state,status);
                        customcontractstatus=0;
                        btn_editState(v.getContext(),party.getId(),customcontractstatus);
                        dialog.dismiss();
                    }
                });
                state_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        status="已签约客户";
                        holder.setText(R.id.item_client_state,status);
                        customcontractstatus=1;
                        btn_editState(v.getContext(),party.getId(),customcontractstatus);
                        dialog.dismiss();
                    }
                });
                state_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        status="无意向客户";
                        holder.setText(R.id.item_client_state,status);
                        customcontractstatus=2;
                        btn_editState(v.getContext(),party.getId(),customcontractstatus);
                        dialog.dismiss();
                    }
                });
                state_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        status="有意向客户";
                        holder.setText(R.id.item_client_state,status);
                        customcontractstatus=3;
                        btn_editState(v.getContext(),party.getId(),customcontractstatus);
                        dialog.dismiss();
                    }
                });
                builder.setView(outerView);
                dialog =builder.create();
                dialog.show();
                ////状态
            }
        });

        holder.setOnClickListener(R.id.item_client_call, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call(party.getCustomphone1());
            }
        });
    }

    /**
     * 拨打电话
     */
    public void call(String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseAppManager.getInstance().getForwardActivity().startActivity(intent);
    }

    /**
     * 修改客户状态
     */
    public void btn_editState(final Context context, int customeid, int customcontractstatus){
        ClientStatePost post = new ClientStatePost();
        post.setCustomid(customeid);post.setCustomcontractstatus(customcontractstatus);
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.CustomContract_EditCustomContractStatus(post, baseUrl, url, new CustomCallBack<RemoteDataResult>() {
            @Override
            public void onSuccess(Response<RemoteDataResult> response) {
                ToastUtils.showShort(context,response.body().getResultMessage());
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(context,message);
            }
        });
    }
    /**
     * 修改备注
     */
    public void btn_editBeizhu(final Context context, int customeid, int createid, String content){
        ClientBeizhuPost post = new ClientBeizhuPost();
        post.setCustomid(customeid);post.setOperationerid(createid);
        post.setBackupcontent(content);post.setSharestatus(1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());   post.setDatetime(date);
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.OperationBackup_AddOperationBackupByCustom(post, baseUrl, url_beizhu, new CustomCallBack<RemoteDataResult>() {
            @Override
            public void onSuccess(Response<RemoteDataResult> response) {
                ToastUtils.showShort(context,response.body().getResultMessage());
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(context,message);
            }
        });
    }



}
