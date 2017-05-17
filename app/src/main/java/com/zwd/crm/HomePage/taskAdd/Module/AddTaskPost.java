package com.zwd.crm.HomePage.taskAdd.Module;

import android.content.Intent;

import java.util.List;

/**
 * Created by asus-pc on 2017/5/8.
 */

public class AddTaskPost {
    private int taskusersgroupid;
    private int taskcreatorid;
    private String tasktitle;//会展名字
    private int  exhibitionid;//任务标题-会展名字
    private String taskcontent;//内容
    private String taskpicurl;//图片--1张
    private String taskstarttime;
    private String tasksettime;
    private String taskpublishtime;
    private String tasktype;
    private String tasklanchtype;
    private int tasklancherid;///执行部门id
    private List<Integer> lanchuserslist;//执行成员列表
    private int taskleaderid;

    ///不填
    private String taskvoiceurl;
    private String taskfinishtime;
    private int taskstatus;
    private int taskflowid;
    private int taskfinisheventid;
    private int previoustaskid;
    private int nexttaskid;

    public int getTaskusersgroupid() {
        return taskusersgroupid;
    }

    public void setTaskusersgroupid(int taskusersgroupid) {
        this.taskusersgroupid = taskusersgroupid;
    }

    public int getTaskcreatorid() {
        return taskcreatorid;
    }

    public void setTaskcreatorid(int taskcreatorid) {
        this.taskcreatorid = taskcreatorid;
    }

    public String getTasktitle() {
        return tasktitle;
    }

    public void setTasktitle(String tasktitle) {
        this.tasktitle = tasktitle;
    }

    public int getExhibitionid() {
        return exhibitionid;
    }

    public void setExhibitionid(int exhibitionid) {
        this.exhibitionid = exhibitionid;
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

    public String getTaskstarttime() {
        return taskstarttime;
    }

    public void setTaskstarttime(String taskstarttime) {
        this.taskstarttime = taskstarttime;
    }

    public String getTasksettime() {
        return tasksettime;
    }

    public void setTasksettime(String tasksettime) {
        this.tasksettime = tasksettime;
    }

    public String getTaskpublishtime() {
        return taskpublishtime;
    }

    public void setTaskpublishtime(String taskpublishtime) {
        this.taskpublishtime = taskpublishtime;
    }

    public String getTasktype() {
        return tasktype;
    }

    public void setTasktype(String tasktype) {
        this.tasktype = tasktype;
    }

    public String getTasklanchtype() {
        return tasklanchtype;
    }

    public void setTasklanchtype(String tasklanchtype) {
        this.tasklanchtype = tasklanchtype;
    }

    public int getTasklancherid() {
        return tasklancherid;
    }

    public void setTasklancherid(int tasklancherid) {
        this.tasklancherid = tasklancherid;
    }

    public List<Integer> getLanchuserslist() {
        return lanchuserslist;
    }

    public void setLanchuserslist(List<Integer> lanchuserslist) {
        this.lanchuserslist = lanchuserslist;
    }

    public int getTaskleaderid() {
        return taskleaderid;
    }

    public void setTaskleaderid(int taskleaderid) {
        this.taskleaderid = taskleaderid;
    }

    public String getTaskvoiceurl() {
        return taskvoiceurl;
    }

    public void setTaskvoiceurl(String taskvoiceurl) {
        this.taskvoiceurl = taskvoiceurl;
    }

    public String getTaskfinishtime() {
        return taskfinishtime;
    }

    public void setTaskfinishtime(String taskfinishtime) {
        this.taskfinishtime = taskfinishtime;
    }

    public int getTaskstatus() {
        return taskstatus;
    }

    public void setTaskstatus(int taskstatus) {
        this.taskstatus = taskstatus;
    }

    public int getTaskflowid() {
        return taskflowid;
    }

    public void setTaskflowid(int taskflowid) {
        this.taskflowid = taskflowid;
    }

    public int getTaskfinisheventid() {
        return taskfinisheventid;
    }

    public void setTaskfinisheventid(int taskfinisheventid) {
        this.taskfinisheventid = taskfinisheventid;
    }

    public int getPrevioustaskid() {
        return previoustaskid;
    }

    public void setPrevioustaskid(int previoustaskid) {
        this.previoustaskid = previoustaskid;
    }

    public int getNexttaskid() {
        return nexttaskid;
    }

    public void setNexttaskid(int nexttaskid) {
        this.nexttaskid = nexttaskid;
    }
}
