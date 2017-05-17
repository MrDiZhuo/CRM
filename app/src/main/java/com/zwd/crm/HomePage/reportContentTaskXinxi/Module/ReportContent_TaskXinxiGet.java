package com.zwd.crm.HomePage.reportContentTaskXinxi.Module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by asus-pc on 2017/5/8.
 */

public class ReportContent_TaskXinxiGet {
    @SerializedName("exhibitionname")
    private String exhibitionname;//公司名字
    @SerializedName("tasktitle")
    private String tasktitle;//任务标题
    @SerializedName("taskstatus")
    private int taskstatus;//任务状态  0代表初始状态，1代表任务已经完成，2代表任务已经失败
    @SerializedName("taskpublishtime")
    private String taskpublishtime;//任务发布时间
    @SerializedName("tasksettime")
    private String tasksettime;//截止时间
    @SerializedName("taskstarttime")
    private String taskstarttime;//执行时间
    @SerializedName("tasktype")
    private String tasktype;////任务类型
    @SerializedName("taskcontent")
    private String taskcontent;///任务内容
    @SerializedName("taskpicurl")
    private String taskpicurl;///任务图片
    @SerializedName("taskcreatorname")
    private String taskcreatorname;///任务创建者名字
    @SerializedName("tasklanchtype")
    private String tasklanchtype;//任务执行者类型  部门:department，个人:person，用户群体:some
    @SerializedName("tasklanchername")
    private String tasklanchername;//仍无执行者名字
    @SerializedName("tasklanchuserslist")
    private List<Tasklanchuserslist> tasklanchuserslist;//当任务执行者为群体时返回的列表
    @SerializedName("taskleadername")
    private String taskleadername;///任务负责ren
    @SerializedName("parentid")
    private int parentid;//0:主任务  其余：子任务

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public String getExhibitionname() {
        return exhibitionname;
    }

    public void setExhibitionname(String exhibitionname) {
        this.exhibitionname = exhibitionname;
    }

    public String getTasktitle() {
        return tasktitle;
    }

    public void setTasktitle(String tasktitle) {
        this.tasktitle = tasktitle;
    }

    public int getTaskstatus() {
        return taskstatus;
    }

    public void setTaskstatus(int taskstatus) {
        this.taskstatus = taskstatus;
    }

    public String getTaskpublishtime() {
        return taskpublishtime;
    }

    public void setTaskpublishtime(String taskpublishtime) {
        this.taskpublishtime = taskpublishtime;
    }

    public String getTasksettime() {
        return tasksettime;
    }

    public void setTasksettime(String tasksettime) {
        this.tasksettime = tasksettime;
    }

    public String getTaskstarttime() {
        return taskstarttime;
    }

    public void setTaskstarttime(String taskstarttime) {
        this.taskstarttime = taskstarttime;
    }

    public String getTasktype() {
        return tasktype;
    }

    public void setTasktype(String tasktype) {
        this.tasktype = tasktype;
    }

    public String getTaskcontent() {
        return taskcontent;
    }

    public void setTaskcontent(String taskcontent) {
        this.taskcontent = taskcontent;
    }

    public String getTaskpicurl() {
        return taskpicurl;
    }

    public void setTaskpicurl(String taskpicurl) {
        this.taskpicurl = taskpicurl;
    }

    public String getTaskcreatorname() {
        return taskcreatorname;
    }

    public void setTaskcreatorname(String taskcreatorname) {
        this.taskcreatorname = taskcreatorname;
    }

    public String getTasklanchtype() {
        return tasklanchtype;
    }

    public void setTasklanchtype(String tasklanchtype) {
        this.tasklanchtype = tasklanchtype;
    }

    public String getTasklanchername() {
        return tasklanchername;
    }

    public void setTasklanchername(String tasklanchername) {
        this.tasklanchername = tasklanchername;
    }

    public List<Tasklanchuserslist> getTasklanchuserslist() {
        return tasklanchuserslist;
    }

    public void setTasklanchuserslist(List<Tasklanchuserslist> tasklanchuserslist) {
        this.tasklanchuserslist = tasklanchuserslist;
    }

    public String getTaskleadername() {
        return taskleadername;
    }

    public void setTaskleadername(String taskleadername) {
        this.taskleadername = taskleadername;
    }
}
