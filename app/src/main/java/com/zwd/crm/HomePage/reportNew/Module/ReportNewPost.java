package com.zwd.crm.HomePage.reportNew.Module;

import java.util.List;

/**
 * Created by asus-pc on 2017/4/29.
 */

public class ReportNewPost {
    private int reportorid;
    private String reportdate;
    private String reporttype;
    private int reportstatus;
    private String completejobcontent;
    private String nocompletejobcontent;
    private String coordinatejobcontent;
    private String reportcontent;

    private int taskid;
    private String picurl;/////存放文件名字
    private String enclosureurl;
    private String address;

    private List<targetidListElement> targetidList;

    public int getReportorid() {
        return reportorid;
    }

    public void setReportorid(int reportorid) {
        this.reportorid = reportorid;
    }

    public String getReportdate() {
        return reportdate;
    }

    public void setReportdate(String reportdate) {
        this.reportdate = reportdate;
    }

    public String getReporttype() {
        return reporttype;
    }

    public void setReporttype(String reporttype) {
        this.reporttype = reporttype;
    }

    public int getReportstatus() {
        return reportstatus;
    }

    public void setReportstatus(int reportstatus) {
        this.reportstatus = reportstatus;
    }

    public String getCompletejobcontent() {
        return completejobcontent;
    }

    public void setCompletejobcontent(String completejobcontent) {
        this.completejobcontent = completejobcontent;
    }

    public String getNocompletejobcontent() {
        return nocompletejobcontent;
    }

    public void setNocompletejobcontent(String nocompletejobcontent) {
        this.nocompletejobcontent = nocompletejobcontent;
    }

    public String getCoordinatejobcontent() {
        return coordinatejobcontent;
    }

    public void setCoordinatejobcontent(String coordinatejobcontent) {
        this.coordinatejobcontent = coordinatejobcontent;
    }

    public String getReportcontent() {
        return reportcontent;
    }

    public void setReportcontent(String reportcontent) {
        this.reportcontent = reportcontent;
    }

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getEnclosureurl() {
        return enclosureurl;
    }

    public void setEnclosureurl(String enclosureurl) {
        this.enclosureurl = enclosureurl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<targetidListElement> getTargetidList() {
        return targetidList;
    }

    public void setTargetidList(List<targetidListElement> targetidList) {
        this.targetidList = targetidList;
    }
}
