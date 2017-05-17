package com.zwd.crm.HomePage.group.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwd.crm.HomePage.chengyuanList.View.chengYuanActivity;
import com.zwd.crm.HomePage.createBumen.Module.creatBumenGet;
import com.zwd.crm.HomePage.createBumen.View.CreateBumenActivity;
import com.zwd.crm.HomePage.inform.Module.InformGet;
import com.zwd.crm.HomePage.inform.Module.subjectdepartmentElement;
import com.zwd.crm.HomePage.xiugaiBuMen.View.XiuGaiBmActivity;
import com.zwd.crm.R;
import com.zwd.crm.adapter.MultiItemTypeAdapter;
import com.zwd.crm.adapter.groupAdapter;
import com.zwd.crm.base.BaseAppManager;
import com.zwd.crm.base.BaseFragment;
import com.zwd.crm.interfaze.CommonAdapter;
import com.zwd.crm.internet.CustomCallBack;
import com.zwd.crm.internet.RemoteDataResult;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.layout.TabViewPager;
import com.zwd.crm.util.ToastUtils;
import com.zwd.crm.widget.CustomLinearLayoutManager;
import com.zwd.crm.widget.PopupList;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Response;


public class groupFragment extends BaseFragment {
    @Bind(R.id.ll_team_group_parent)
    LinearLayout parent;
    @Bind(R.id.group_ll_new)
    LinearLayout create;
    @Bind(R.id.group_name)
    TextView txt_group_name;
    @Bind(R.id.group_recycler)
    RecyclerView recyclerView;


    int parentid;
    ArrayList<subjectdepartmentElement> bumen;
    public ArrayList<subjectdepartmentElement> subjectDepartments;

    static Context context;

    groupAdapter adapter ;
    private String baseUrl="http://139.224.164.183:8088/";
    private String url_department="Department_ReturnDepartmentStructureFromUsersGroup.aspx";
    private int usergroupId ;
    private String usergroupname="";
    protected int getContentViewID() {
        return R.layout.fragment_group;
    }

    @Override
    protected void initViews(View rootView) {
        context = getContext();
        usergroupId =((TabViewPager)getActivity()).login_Get.getUserOrganization().getUsersgroupid();
        usergroupname =((TabViewPager)getActivity()).login_Get.getUserOrganization().getUsersgroupname();
        txt_group_name.setText(usergroupname);
        btn_group_department();

        CustomLinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(context);
        linearLayoutManager.setScrollEnabled(false);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    private void initlist(final List<subjectdepartmentElement> subjectDepartments) {

        adapter = new groupAdapter(mContext,subjectDepartments);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("title",subjectDepartments.get(position));
                bundle.putInt("usergroupId",usergroupId);
                goActivity(chengYuanActivity.class,bundle);
            }
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, final int pos) {
                List<String> popupMenuItemList = new ArrayList<>();
                popupMenuItemList.add("删除");
                popupMenuItemList.add("调整");
                PopupList popuplist = new PopupList();
                popuplist.init(context.getApplicationContext(),view,popupMenuItemList,new PopupList.OnPopupListClickListener(){
                    @Override
                    public void onPopupListClick(final View contextView, int contextPosition, int position) {
                        if(position ==1){
                            int id = subjectDepartments.get(pos).getDepartmentid();
                            Bundle bundle = new Bundle();
                            bundle.putInt("id",id);
                            bundle.putInt("usergroupId",usergroupId);
                            bundle.putInt("position",pos);
                            goActivityForResult(XiuGaiBmActivity.class,bundle,1);
                        //    delete(ll,contextView);
                        }else{
                            btn_deleteDepartment(subjectDepartments.get(pos).getDepartmentid(),pos);
                        }
                    }
                });
                popuplist.setTextSize(popuplist.sp2px(12));
                popuplist.setTextPadding(popuplist.dp2px(16), popuplist.dp2px(8), popuplist.dp2px(16), popuplist.dp2px(8));
                popuplist.setIndicatorView(popuplist.getDefaultIndicatorView(popuplist.dp2px(16), popuplist.dp2px(8), 0xFF444444));

                return true;
            }
        });

    }


    @OnClick({R.id.group_ll_new})
    public void click(View v){
        switch (v.getId()){
            case R.id.group_ll_new:
                Intent intent = new Intent(context,CreateBumenActivity.class);
                intent.putExtra("title","创建部门");
                intent.putExtra("usergroupid",usergroupId);
                startActivityForResult(intent,0);
                break;
        }
    }

    /**
     * 调接口
     */
    public void btn_group_department(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Department_ReturnDepartmentStructureFromUsersGroup(usergroupId, baseUrl, url_department, new CustomCallBack<RemoteDataResult<InformGet>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<InformGet>> response) {
                ToastUtils.showShort(context,response.body().getResultMessage());
                InformGet informGet = response.body().getData();
                subjectDepartments = informGet.getSubjectdepartment();
                initlist(subjectDepartments);
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(context,message);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent intent){
        switch (requestCode){
            case 0:
                if (resultCode==0){
                    Bundle data = intent.getExtras();
                    creatBumenGet bumen_name =(creatBumenGet)data.getSerializable("name");
                    if(bumen_name.getParentid()==0){
                        subjectdepartmentElement sub = new subjectdepartmentElement(bumen_name.getId(),bumen_name.getDepartmentname(),null);
                        if(" ".equals(sub.getDepartmentname())==false){
                            subjectDepartments.add(sub);
                            adapter.notifyDataSetChanged();
                            //addview_(usergroupId,context,parent,sub);
                        }
                    }
                }
                break;
            case 1:
                if(resultCode==0){
                    Bundle bundle = intent.getExtras();
                    parentid = bundle.getInt("parentid");
                    int position = bundle.getInt("position");
                    if(parentid!=0){
                        subjectDepartments.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                }
        }

        }





    /**
     * 调接口
     */
    public void btn_deleteDepartment(int post, final int position){
        String baseUrl="http://139.224.164.183:8088/";
        String url="Department_DeleteDepartment.aspx";
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Department_DeleteDepartment(post, baseUrl, url, new CustomCallBack<RemoteDataResult>() {
            @Override
            public void onSuccess(Response<RemoteDataResult> response) {
                subjectDepartments.remove(position);
                adapter.notifyDataSetChanged();
                ToastUtils.showShort(context,response.body().getResultMessage());
            }

            @Override
            public void onFail(String message) {
                // flag=false;
                ToastUtils.showShort(context,message);
            }
        });

    }




}
