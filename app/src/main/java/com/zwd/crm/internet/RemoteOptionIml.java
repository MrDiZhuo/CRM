package com.zwd.crm.internet;

import android.util.Log;

import com.zwd.crm.HomePage.addAffair.Module.AddAffairPost;
import com.zwd.crm.HomePage.adjust.Module.AdjustBumenPost;
import com.zwd.crm.HomePage.affair.Module.affairGet;
import com.zwd.crm.HomePage.affair.Module.affairNoticeSignPost;
import com.zwd.crm.HomePage.affairXiangQing.Module.AffairXiangQingAnswerElement;
import com.zwd.crm.HomePage.affairXiangQing.Module.affairXiangQingGet;
import com.zwd.crm.HomePage.apply.Module.applyPost;
import com.zwd.crm.HomePage.bindPhone.Module.Bind_PhonePost;
import com.zwd.crm.HomePage.changeHeadImg.Module.changeHeadingPost;
import com.zwd.crm.HomePage.chengyuan.Module.ChengYuInfoGet;
import com.zwd.crm.HomePage.chengyuanList.Module.DeleteUserFromDepartmentPost;
import com.zwd.crm.HomePage.choosePerson.Module.ChoosePersonGet;
import com.zwd.crm.HomePage.client.Module.ClientBeizhuPost;
import com.zwd.crm.HomePage.client.Module.ClientStatePost;
import com.zwd.crm.HomePage.client.Module.clientGet;
import com.zwd.crm.HomePage.client.Module.clientSearchPost;
import com.zwd.crm.HomePage.clientAdd.Module.clientAddPost;
import com.zwd.crm.HomePage.clientCiangQing.Module.ClientXQBeizhuGet;
import com.zwd.crm.HomePage.clientState.Module.Client_StateGet;
import com.zwd.crm.HomePage.client_XiuGai.Module.Edit_ClientPost;
import com.zwd.crm.HomePage.conference.Module.conferenceGet;
import com.zwd.crm.HomePage.conferenceAdd.Module.conferenceAddPost;
import com.zwd.crm.HomePage.createBumen.Module.creatBumenGet;
import com.zwd.crm.HomePage.createBumen.Module.creatBumenPost;
import com.zwd.crm.HomePage.editPwd.Module.EditPwdPost;
import com.zwd.crm.HomePage.fullInfo.Module.fullFillPost;
import com.zwd.crm.HomePage.inform.Module.InformGet;
import com.zwd.crm.HomePage.inform.Module.InformListGet;
import com.zwd.crm.HomePage.login.Module.loginGet;
import com.zwd.crm.HomePage.login.Module.loginPost;
import com.zwd.crm.HomePage.register.Module.VertifyGet;
import com.zwd.crm.HomePage.reportContentTaskXinxi.Module.ReportContent_TaskXinxiGet;
import com.zwd.crm.HomePage.reportContext.Module.Report_CotentGet;
import com.zwd.crm.HomePage.reportContextRengwu.Module.Report_context_RengwuGet;
import com.zwd.crm.HomePage.reportContextRengwu.View.ReportContext_RengwuActivity;
import com.zwd.crm.HomePage.reportNew.Module.ReportNewPost;
import com.zwd.crm.HomePage.reportReceive.Module.Report_receiveGet;
import com.zwd.crm.HomePage.reportSend.Module.Report_SendGet;
import com.zwd.crm.HomePage.taskAdd.Module.AddTaskPost;
import com.zwd.crm.HomePage.taskAdd.Module.AddTask_Conference;
import com.zwd.crm.HomePage.taskJiegou.Module.Task_JIegouGet;
import com.zwd.crm.HomePage.xiugaiBuMen.Module.XiuGaiBMPost;
import com.zwd.crm.interfaze.RemoteApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by asus-pc on 2017/4/9.
 */

public class RemoteOptionIml {
    private HttpHelper httpHelper=new HttpHelper();

    public void login(loginPost post, String baseUrl,
                      String url, CustomCallBack<RemoteDataResult<loginGet>> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("username",post.getUsername());
            jsonObject.put("password",post.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<loginGet>> remoteDataResultCall = remoteApi.login(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }

    public void register(loginPost post,String baseUrl,
                         String url,CustomCallBack<RemoteDataResult> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username",post.getUsername());
            jsonObject.put("password",post.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi=httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.register(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }


    public void forget_pwd(loginPost post,String baseUrl,String url,
                           CustomCallBack<RemoteDataResult> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username",post.getUsername());
            jsonObject.put("password",post.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi =httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.forget_pwd(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * conference界面
     */
    public void Exhibition_ReturnExhibitionList(int userId , String baseUrl, String url,
                                                CustomCallBack<RemoteDataResult<List<conferenceGet>>> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("usersid",userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<List<conferenceGet>>> remoteDataResultCall =remoteApi.Exhibition_ReturnExhibitionList(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * conferenceAdd
     */
    public void Exhibition_AddExhibition(conferenceAddPost post,String baseUrl,String url,
                                         CustomCallBack<RemoteDataResult> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("exhibitionname",post.getName());
            jsonObject.put("exhibitionstartdate",post.getStartdate());
            jsonObject.put("exhibitionenddate",post.getEnddate());
            jsonObject.put("exhibitionadress",post.getAddress());
            jsonObject.put("exhibitiondescription",post.getDescription());
            jsonObject.put("usersgroupid",post.getUsersgroupid());
            jsonObject.put("exhibitionstatus",post.getState());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.Exhibition_AddExhibition(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * conferenceEdit
     */
    public void Exhibition_EditExhibition(conferenceAddPost post,String baseUrl,String url,
                                         CustomCallBack<RemoteDataResult> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("exhibitionname",post.getName());
            jsonObject.put("exhibitionstartdate",post.getStartdate());
            jsonObject.put("exhibitionenddate",post.getEnddate());
            jsonObject.put("exhibitionadress",post.getAddress());
            jsonObject.put("exhibitiondescription",post.getDescription());
            jsonObject.put("id",post.getUsersgroupid());
            jsonObject.put("exhibitionstatus",post.getState());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.Exhibition_EditExhibition(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * affair
     */
    public void Notice_ReturnNoticeListShow(int userId , String baseUrl, String url,
                                                CustomCallBack<RemoteDataResult<List<affairGet>>> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<List<affairGet>>> remoteDataResultCall =remoteApi.Notice_ReturnNoticeListShow(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }


     /**
     * 事务选择接收人
     */
    public void Users_ReturnUserList(int post,String baseUrl,String url,CustomCallBack<RemoteDataResult<List<ChoosePersonGet>>> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<List<ChoosePersonGet>>> remoteDataResultCall = remoteApi.Users_ReturnUserList(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * 创建事务
     */
    public void Notice_AddNotice(AddAffairPost addAffairPost,String baseUrl,String url,CustomCallBack<RemoteDataResult> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title",addAffairPost.getTitle());
            jsonObject.put("noticecontent",addAffairPost.getNoticeContent());
            jsonObject.put("noticedate",addAffairPost.getNoticeDate());
            jsonObject.put("creatid",addAffairPost.getCreateId());
            jsonObject.put("noticeenddate",addAffairPost.getNoticeEnddate());
            jsonObject.put("noticestatus",addAffairPost.getNoticestatus());
            jsonObject.put("noticetype",addAffairPost.getNoticeType());
            JSONArray jsonArray = new JSONArray();
            for(int i = 0;i<addAffairPost.getPersonElementList().size();i++){
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("usersid",addAffairPost.getPersonElementList().get(i).getUserId());
                jsonObject1.put("noticeusersdate",addAffairPost.getPersonElementList().get(i).getNoticeusersdate());
                jsonObject1.put("noticeusersstatus",addAffairPost.getPersonElementList().get(i).getNoticeusersstatus());
                jsonArray.put(i,jsonObject1);
                  }
            jsonObject.put("userslist",jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("json",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.Notice_AddNotice(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
        }


    /**
     * 修改头像
     */

    public void Users_EditHeadImg(changeHeadingPost post,String baseUrl, String url,CustomCallBack<RemoteDataResult> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",post.getId());
            jsonObject.put("headimg",post.getUrl());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.Users_EditHeadImg(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * 获取token/获取url
     */
    public void GetToken(String baseUrl,String url,Callback<String> callBack){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        Retrofit build = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(new Converter.Factory() {
                    @Override
                    public Converter<ResponseBody, ?> responseBodyConverter(Type type, java.lang.annotation.Annotation[] annotations, Retrofit retrofit) {
                        return new Converter<ResponseBody, String>() {
                            @Override
                            public String convert(ResponseBody value) throws IOException {
                                return value.string();
                            }
                        };
                    }
                })
                .client(okHttpClient)
                .build();
        RemoteApi remoteApi = build.create(RemoteApi.class);
        Call<String> token = remoteApi.GetToken(url);
        token.enqueue(callBack);
    }




    /**
     * 个人信息完善
     */
    public void Users_EditUserInfo(fullFillPost post,String baseUrl, String url,CustomCallBack<RemoteDataResult> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",post.getId());
            jsonObject.put("nickname",post.getNickname());
            jsonObject.put("wechat",post.getWechat());
            jsonObject.put("alipay",post.getAlipay());
            jsonObject.put("email",post.getEmail());
            jsonObject.put("usertag",post.getUsertag());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.Users_EditUserInfo(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * 修改密码
     */
    public void Users_EditPassword(EditPwdPost post,String baseUrl, String url,CustomCallBack<RemoteDataResult> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",post.getId());
            jsonObject.put("name",post.getNewPsw());
            jsonObject.put("password",post.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.Users_EditPassword(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }


    /**
     * 消息-同意-用户部门-返回部门
     */
    public void Department_ReturnDepartmentStructureFromUsersGroup(int post, String baseUrl, String url, CustomCallBack<RemoteDataResult<InformGet>> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<InformGet>> remoteDataResultCall = remoteApi.Department_ReturnDepartmentStructureFromUsersGroup(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * 消息-同意-用户部门-同意
     */
    public void UsersDepartment_AddUsers(applyPost post,String baseUrl,String url,CustomCallBack<RemoteDataResult> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("usersid",post.getUsersid());
            jsonObject.put("departmentid",post.getDepartmentid());
            jsonObject.put("role",post.getRole());
            jsonObject.put("roletype",post.getRoletype());
            jsonObject.put("updatetime",post.getDate());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.UsersDepartment_AddUsers(url,jsonObject);
        Log.d("json:",jsonObject.toString());
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * 创建部门
     */
    public void Department_AddDepartment(creatBumenPost post,String baseUrl,String url,CustomCallBack<RemoteDataResult<creatBumenGet>> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("departmentname",post.getDepartmentname());
            jsonObject.put("usersgroupid",post.getUsersgroupid());
            jsonObject.put("parentid",post.getParentid());
            jsonObject.put("departmentintro",post.getDepartmentintro());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<creatBumenGet>> remoteDataResultCall = remoteApi.Department_AddDepartment(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }
    /**
     * 部门成员
     */
    public void Department_ReturnUsersListByDepartmet(int post,String baseUrl,String url,CustomCallBack<RemoteDataResult<List<ChoosePersonGet>>> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<List<ChoosePersonGet>>> remoteDataResultCall = remoteApi.Department_ReturnUsersListByDepartmet(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * 客户列表
     */
    public void Custom_ReturnCustomList(int post, String baseUrl, String url, CustomCallBack<RemoteDataResult<List<clientGet>>> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi =httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<List<clientGet>>> remoteDataResultCall = remoteApi.Custom_ReturnCustomList(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * 客户搜索列表
     */
    public void Custom_CustomSearch(clientSearchPost post,String baseUrl,String url,CustomCallBack<RemoteDataResult<List<clientGet>>> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("customname",post.getCustomname());
            jsonObject.put("usersgroupid",post.getUsersgroupid());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<List<clientGet>>> remoteDataResultCall = remoteApi.Custom_CustomSearch(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * 客户添加
     */
    public void Custom_AddCustom(clientAddPost post,String baseUrl,String url,CustomCallBack<RemoteDataResult> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("customname",post.getCustomname());
            jsonObject.put("contractname",post.getContractname());
            jsonObject.put("customphone1",post.getCustomphone1());
            jsonObject.put("customphone2",post.getCustomphone2());
            jsonObject.put("customemail",post.getCustomemail());
            jsonObject.put("customstatus",post.getCustomstatus());
            jsonObject.put("customaddress",post.getCustomaddress());
            jsonObject.put("creatorid",post.getCreatorid());
            jsonObject.put("custompostion",post.getCustompostion());
            jsonObject.put("customuniqueid",post.getCustomuniqueid());
            jsonObject.put("usersgroupid",post.getUsersgroupid());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.Custom_AddCustom(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * 事务标记完成
     */
    public void Notice_ReturnNoticeSign(affairNoticeSignPost post,String baseUrl,String url,CustomCallBack<RemoteDataResult> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",post.getId());
            jsonObject.put("noticeusersstatus",post.getNoticeusersstatus());
            jsonObject.put("usersid",post.getUserid());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.Notice_ReturnNoticeSign(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * 事务详情
     */
    public void Notice_ReturnNoiceDetail(int post, String baseUrl, String url, CustomCallBack<RemoteDataResult<affairXiangQingGet>> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<affairXiangQingGet>> remoteDataResultCall = remoteApi.Notice_ReturnNoiceDetail(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * 添加汇报
     */
    public void Report_AddReport(ReportNewPost post ,String baseUrl,String url,CustomCallBack<RemoteDataResult> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("reportorid",post.getReportorid());
            jsonObject.put("taskid",post.getTaskid());
            jsonObject.put("address",post.getAddress());
            jsonObject.put("reportdate",post.getReportdate());
            jsonObject.put("reporttype",post.getReporttype());
            jsonObject.put("completejobcontent",post.getCompletejobcontent());
            jsonObject.put("coordinatejobcontent",post.getCoordinatejobcontent());
            jsonObject.put("nocompletejobcontent",post.getNocompletejobcontent());
            jsonObject.put("reportcontent",post.getReportcontent());
            jsonObject.put("picurl",post.getPicurl());
            jsonObject.put("enclosureurl",post.getEnclosureurl());
            jsonObject.put("reportstatus",post.getReportstatus());

            JSONArray jsonArray = new JSONArray();
            for(int i = 0;i<post.getTargetidList().size();i++){
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("id",post.getTargetidList().get(i).getId());
                jsonObject1.put("name",post.getTargetidList().get(i).getName());
               jsonArray.put(i,jsonObject1);
            }
            jsonObject.put("targetidList",jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.Report_AddReport(url,jsonObject);
        Log.d("json",jsonObject.toString());
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * 事务详情-回复
     */
    public void NoticeAnswer_AddNoticeAnswer(AffairXiangQingAnswerElement post,String baseUrl,String url,CustomCallBack<RemoteDataResult> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("answercontent",post.getAnswercontent());
            jsonObject.put("answerusersid",post.getAnswerusersid());
            jsonObject.put("parrentid",post.getParrentid());
            jsonObject.put("answerstatus",post.getAnswerstatus());
            jsonObject.put("noticeid",post.getNoticeid());
            jsonObject.put("answerdate",post.getAnswerdate());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall= remoteApi.NoticeAnswer_AddNoticeAnswer(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * 删除客户
     */
    public void Custom_DeleteCustom(int post,String baseUrl,String url,CustomCallBack<RemoteDataResult> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.Custom_DeleteCustom(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * 删除事务
     */
    public void Notice_DeleteNotice(int post,String baseUrl,String url,CustomCallBack<RemoteDataResult> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.Notice_DeleteNotice(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * 将用户从部门中删除
     */
    public void Department_DeleteUsersForDepartment(DeleteUserFromDepartmentPost post,String baseUrl,String url,CustomCallBack<RemoteDataResult> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("usersid",post.getUserid());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.Department_DeleteUsersForDepartment(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);

    }

    /**
     * 调整用户
     */
    public void UsersDepartment_EditUsersDepartmentForDepartment(AdjustBumenPost post,String baseUrl,String url,CustomCallBack<RemoteDataResult> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("usersid",post.getUserid());
            jsonObject.put("departmentid",post.getDepartmentid());
            jsonObject.put("usersrole",post.getRole());
            jsonObject.put("roletype",post.getRoletype());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.UsersDepartment_EditUsersDepartmentForDepartment(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * 会展-客户状态
     */
    public void Exhibition_ReturnCustomDetailByExhibition(int post, String baseUrl, String url, CustomCallBack<RemoteDataResult<List<Client_StateGet>>> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<List<Client_StateGet>>> remoteDataResultCall = remoteApi.Exhibition_ReturnCustomDetailByExhibition(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * 删除部门
     */
    public void Department_DeleteDepartment(int post,String baseUrl,String url,CustomCallBack<RemoteDataResult> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall=remoteApi.Department_DeleteDepartment(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }
    /**
     * 用户-用户信息
     */
    public void Users_ReturnUsersInfoByDepartment(int post, String baseUrl, String url, CustomCallBack<RemoteDataResult<ChengYuInfoGet>> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi= httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<ChengYuInfoGet>> remoteDataResultCall=remoteApi.Users_ReturnUsersInfoByDepartment(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * 调整部门
     */
    public void Department_EditDepartment(XiuGaiBMPost post,String baseUrl,String url,CustomCallBack<RemoteDataResult> callBack){
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("id",post.getId());
            jsonObject.put("parentid",post.getParentId());
            jsonObject.put("departmentintro",post.getDepartmentintro());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi= httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall=remoteApi.Department_EditDepartment(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * 修改客户状态
     */
    public void CustomContract_EditCustomContractStatus(ClientStatePost post,String baseUrl,String url,CustomCallBack<RemoteDataResult> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("customid",post.getCustomid());
            jsonObject.put("customcontractstatus",post.getCustomcontractstatus());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi= httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall=remoteApi.CustomContract_EditCustomContractStatus(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }
    /**
     * 添加备注接口
     */
    public void OperationBackup_AddOperationBackupByCustom(ClientBeizhuPost post,String baseUrl,String url,CustomCallBack<RemoteDataResult> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("customid",post.getCustomid());
            jsonObject.put("operationerid",post.getOperationerid());
            jsonObject.put("backupcontent",post.getBackupcontent());
            jsonObject.put("sharestatus",post.getSharestatus());
            jsonObject.put("updatedate",post.getDatetime());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi= httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall=remoteApi.OperationBackup_AddOperationBackupByCustom(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);

    }

    /**
     * 返回消息列表
     */
    public void Department_ReturnDefaultDepartmentUsers(int post, String baseUrl, String url, CustomCallBack<RemoteDataResult<List<InformListGet>>> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi =httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<List<InformListGet>>> remoteDataResultCall=remoteApi.Department_ReturnDefaultDepartmentUsers(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * 返回客户详情
     */
    public void Custom_ReturnCustomDetail(int post, String baseUrl, String url, CustomCallBack<RemoteDataResult<ClientXQBeizhuGet>> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<ClientXQBeizhuGet>> remoteDataResultCall = remoteApi.Custom_ReturnCustomDetail(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * 修改客户
     */
    public void Custom_EditCUstom(Edit_ClientPost post,String baseUrl,String url,CustomCallBack<RemoteDataResult> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("customname",post.getCustomname());
            jsonObject.put("contractname",post.getContractname());
            jsonObject.put("custompostion",post.getCustompostion());
            jsonObject.put("customphone1",post.getCustomphone1());
            jsonObject.put("customemail",post.getCustomemail());
            jsonObject.put("customaddress",post.getCustomaddress());
            jsonObject.put("id",post.getId());
            jsonObject.put("usersgroupid",post.getGroupid());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall =remoteApi.Custom_EditCUstom(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * report_receive列表
     */
    public void Report_ReturnReportListByReceive(int post, String baseUrl, String url, CustomCallBack<RemoteDataResult<List<Report_receiveGet>>> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<List<Report_receiveGet>>> remoteDataResultCall =remoteApi.Report_ReturnReportListByReceive(url,jsonObject);
        Log.d("id",jsonObject.toString());
        remoteDataResultCall.enqueue(callBack);

    }

    /**
     * report-详情
     */
    public void Report_ReturnReportDetail(int post, String baseUrl, String url, CustomCallBack<RemoteDataResult<Report_CotentGet>> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<Report_CotentGet>> remoteDataResultCall =remoteApi.Report_ReturnReportDetail(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }
    /**
     * report-send列表
     */
    public void Report_ReturnReportListByReport(int post, String baseUrl, String url, CustomCallBack<RemoteDataResult<List<Report_SendGet>>> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<List<Report_SendGet>>> remoteDataResultCall =remoteApi.Report_ReturnReportListByReport(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * 创建汇报--选择任务--任务列表
     */
    public void Task_ReturnTaskListByUsers(int post, String baseUrl, String url, CustomCallBack<RemoteDataResult<List<Report_context_RengwuGet>>> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<List<Report_context_RengwuGet>>> remoteDataResultCall =remoteApi.Task_ReturnTaskListByUsers(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);

    }

    /**
     * report-context-taskxinxi
     */
    public void Task_ReturnTaskDetail(int post, String baseUrl, String url, CustomCallBack<RemoteDataResult<ReportContent_TaskXinxiGet>> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<ReportContent_TaskXinxiGet>> remoteDataResultCall = remoteApi.Task_ReturnTaskDetail(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * report-context-taskxinxi-taskjiegou
     */
    public void Task_ReturnTaskStructureListByTask(int post, String baseUrl, String url, CustomCallBack<RemoteDataResult<List<Task_JIegouGet>>> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<List<Task_JIegouGet>>> remoteDataResultCall = remoteApi.Task_ReturnTaskStructureListByTask(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * conference-content-task
     */
    public void Task_ReturnTaskListByExhibition(int post, String baseUrl, String url, CustomCallBack<RemoteDataResult<List<Report_context_RengwuGet>>> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<List<Report_context_RengwuGet>>> remoteDataResultCall =remoteApi.Task_ReturnTaskListByExhibition(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);

    }

    /**
     * Task-Add-conference
     */
    public void Exhibition_ReturnExhibitionListByTask(int post, String baseUrl, String url, CustomCallBack<RemoteDataResult<List<AddTask_Conference>>> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<List<AddTask_Conference>>> remoteDataResultCall = remoteApi.Exhibition_ReturnExhibitionListByTask(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * 添加task
     */
    public void Task_AddTask(AddTaskPost post,String baseUrl,String url,CustomCallBack<RemoteDataResult> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tasktitle",post.getTasktitle());
            jsonObject.put("taskcontent",post.getTaskcontent());
            jsonObject.put("taskvoiceurl",post.getTaskvoiceurl());
            jsonObject.put("taskpicurl",post.getTaskpicurl());
            jsonObject.put("taskstarttime",post.getTaskstarttime());
            jsonObject.put("taskfinishtime",post.getTaskfinishtime());
            jsonObject.put("taskpublishtime",post.getTaskpublishtime());
            jsonObject.put("tasksettime",post.getTasksettime());
            jsonObject.put("taskcreatorid",post.getTaskcreatorid());
            jsonObject.put("taskstatus",post.getTaskstatus());
            jsonObject.put("taskusersgroupid",post.getTaskusersgroupid());
            jsonObject.put("taskflowid",post.getTaskflowid());
            jsonObject.put("tasklancherid",post.getTasklancherid());
            jsonObject.put("tasklanchtype",post.getTasklanchtype());
            jsonObject.put("taskleaderid",post.getTaskleaderid());
            jsonObject.put("taskfinisheventid",post.getTaskfinisheventid());
            jsonObject.put("tasktype",post.getTasktype());
            jsonObject.put("previoustaskid",post.getPrevioustaskid());
            jsonObject.put("nexttaskid",post.getNexttaskid());
            jsonObject.put("exhibitionid",post.getExhibitionid());
            JSONArray jsonArray = new JSONArray();
            for(int i = 0;i<post.getLanchuserslist().size();i++){
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("usersid",post.getLanchuserslist().get(i));
                jsonArray.put(i,jsonObject1);
            }
            jsonObject.put("lanchuserslist",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.Task_AddTask(url,jsonObject);
        Log.d("taskJson",jsonObject.toString());
        remoteDataResultCall.enqueue(callBack);

    }

    /**
     * 短信验证
     */
    public void returncode(String post, String baseUrl, String url,CustomCallBack<RemoteDataResult<VertifyGet>> callBack){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("myphone",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<VertifyGet>> remoteDataResultCall = remoteApi.returncode(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * 消息---拒绝
     */
    public void Department_DeleteDefaultDepartmentUsers(int post,String baseUrl,String url,CustomCallBack<RemoteDataResult> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("usersid",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.Department_DeleteDefaultDepartmentUsers(url,jsonObject);
        Log.d("json",jsonObject.toString());
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * 汇报删除
     */
    public void Report_DeleteReport(int post ,String baseUrl,String url,CustomCallBack<RemoteDataResult> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.Report_DeleteReport(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }
    /**
     * 绑定号码
     */
    public void Users_EditUsersByUsersName(Bind_PhonePost post ,String baseUrl,String url,CustomCallBack<RemoteDataResult> callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",post.getId());
            jsonObject.put("username",post.getNewphone());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.Users_EditUsersByUsersName(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }


}






