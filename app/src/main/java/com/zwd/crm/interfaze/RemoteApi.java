package com.zwd.crm.interfaze;

import com.zwd.crm.HomePage.affair.Module.affairGet;
import com.zwd.crm.HomePage.affairXiangQing.Module.affairXiangQingGet;
import com.zwd.crm.HomePage.chengyuan.Module.ChengYuInfoGet;
import com.zwd.crm.HomePage.choosePerson.Module.ChoosePersonGet;
import com.zwd.crm.HomePage.client.Module.clientGet;
import com.zwd.crm.HomePage.clientCiangQing.Module.ClientXQBeizhuGet;
import com.zwd.crm.HomePage.clientState.Module.Client_StateGet;
import com.zwd.crm.HomePage.conference.Module.conferenceGet;
import com.zwd.crm.HomePage.createBumen.Module.creatBumenGet;
import com.zwd.crm.HomePage.inform.Module.InformGet;
import com.zwd.crm.HomePage.inform.Module.InformListGet;
import com.zwd.crm.HomePage.login.Module.loginGet;
import com.zwd.crm.HomePage.register.Module.VertifyGet;
import com.zwd.crm.HomePage.reportContentTaskXinxi.Module.ReportContent_TaskXinxiGet;
import com.zwd.crm.HomePage.reportContext.Module.Report_CotentGet;
import com.zwd.crm.HomePage.reportContextRengwu.Module.Report_context_RengwuGet;
import com.zwd.crm.HomePage.reportReceive.Module.Report_receiveGet;
import com.zwd.crm.HomePage.reportSend.Module.Report_SendGet;
import com.zwd.crm.HomePage.taskAdd.Module.AddTask_Conference;
import com.zwd.crm.HomePage.taskJiegou.Module.Task_JIegouGet;
import com.zwd.crm.internet.RemoteDataResult;

import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by asus-pc on 2017/4/9.
 */

public interface RemoteApi {
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<loginGet>>login(@Url String url, @Field("") JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>register(@Url String url,@Field("") JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>forget_pwd(@Url String url,@Field("") JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<List<conferenceGet>>>Exhibition_ReturnExhibitionList(@Url String url, @Field("") JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>Exhibition_AddExhibition(@Url String url, @Field("") JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>Exhibition_EditExhibition(@Url String url, @Field("") JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<List<affairGet>>>Notice_ReturnNoticeListShow(@Url String url, @Field("") JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<List<ChoosePersonGet>>>Users_ReturnUserList(@Url String url, @Field("") JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>Notice_AddNotice(@Url String url, @Field("") JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>Users_EditHeadImg(@Url String url,@Field("") JSONObject jsonObject);


    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>Users_EditUserInfo(@Url String url,@Field("") JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>Users_EditPassword(@Url String url,@Field("") JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<InformGet>>Department_ReturnDepartmentStructureFromUsersGroup(@Url String url,@Field("") JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>UsersDepartment_AddUsers(@Url String url,@Field("") JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<creatBumenGet>>Department_AddDepartment(@Url String url, @Field("") JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<List<ChoosePersonGet>>>Department_ReturnUsersListByDepartmet(@Url String url,@Field("") JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<List<clientGet>>>Custom_ReturnCustomList(@Url String url,@Field("") JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<List<clientGet>>>Custom_CustomSearch(@Url String url,@Field("") JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>Custom_AddCustom(@Url String url,@Field("") JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>Notice_ReturnNoticeSign(@Url String url,@Field("") JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<affairXiangQingGet>>Notice_ReturnNoiceDetail(@Url String url, @Field("") JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>Report_AddReport(@Url String url, @Field("") JSONObject jsonObject);


    @GET
    Call<String>GetToken(@Url String url);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>NoticeAnswer_AddNoticeAnswer(@Url String url, @Field("") JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>Custom_DeleteCustom(@Url String url,@Field("")JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>Notice_DeleteNotice(@Url String url,@Field("")JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>Department_DeleteUsersForDepartment(@Url String url,@Field("")JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>UsersDepartment_EditUsersDepartmentForDepartment(@Url String url,@Field("")JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<List<Client_StateGet>>>Exhibition_ReturnCustomDetailByExhibition(@Url String url,@Field("")JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>Department_DeleteDepartment(@Url String url,@Field("")JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<ChengYuInfoGet>>Users_ReturnUsersInfoByDepartment(@Url String url, @Field("")JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>Department_EditDepartment(@Url String url, @Field("")JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>CustomContract_EditCustomContractStatus(@Url String url, @Field("")JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>OperationBackup_AddOperationBackupByCustom(@Url String url, @Field("")JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<List<InformListGet>>>Department_ReturnDefaultDepartmentUsers(@Url String url, @Field("")JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<ClientXQBeizhuGet>>Custom_ReturnCustomDetail(@Url String url, @Field("")JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>Custom_EditCUstom(@Url String url, @Field("")JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<List<Report_receiveGet>>>Report_ReturnReportListByReceive(@Url String url, @Field("")JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<Report_CotentGet>>Report_ReturnReportDetail(@Url String url, @Field("")JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<List<Report_SendGet>>>Report_ReturnReportListByReport(@Url String url, @Field("")JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<List<Report_context_RengwuGet>>>Task_ReturnTaskListByUsers(@Url String url, @Field("")JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<ReportContent_TaskXinxiGet>>Task_ReturnTaskDetail(@Url String url, @Field("")JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<List<Task_JIegouGet>>>Task_ReturnTaskStructureListByTask(@Url String url, @Field("")JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<List<Report_context_RengwuGet>>>Task_ReturnTaskListByExhibition(@Url String url, @Field("")JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<List<AddTask_Conference>>>Exhibition_ReturnExhibitionListByTask(@Url String url, @Field("")JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>Task_AddTask(@Url String url, @Field("")JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<VertifyGet>>returncode(@Url String url, @Field("")JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>Department_DeleteDefaultDepartmentUsers(@Url String url, @Field("")JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>Report_DeleteReport(@Url String url, @Field("")JSONObject jsonObject);


    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>Users_EditUsersByUsersName(@Url String url, @Field("")JSONObject jsonObject);




}
