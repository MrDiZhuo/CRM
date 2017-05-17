package com.zwd.crm.HomePage.reportContext.Module;

import com.google.gson.annotations.SerializedName;
import com.zwd.crm.HomePage.addAffair.Module.Addaffair_person;

import java.util.List;

/**
 * Created by asus-pc on 2017/3/12.
 */

public class Report_CotentGet {
    @SerializedName("completejobcontent")
    private String completejobcontent;
    @SerializedName("nocompletejobcontent")
    private String nocompletejobcontent;
    @SerializedName("coordinatejobcontent")
    private String coordinatejobcontent;
    @SerializedName("reportcontent")
    private String reportcontent;
    @SerializedName("taskid")
    private int taskid;
    @SerializedName("picurl")/////文件名字
    private String enclosureName;
    @SerializedName("enclosureurl")
    private String enclosureurl;
    @SerializedName("address")
    private String address;
    @SerializedName("targetidlist")
    private List<Addaffair_person> targetList;


    public String getEnclosureName() {
        return enclosureName;
    }

    public void setEnclosureName(String enclosureName) {
        this.enclosureName = enclosureName;
    }

    public String getCompletejobcontent() {
        return completejobcontent;
    }

    public void setCompletejobcontent(String completejobcontent) {
        this.completejobcontent = completejobcontent;
    }

    public String getCoordinatejobcontent() {
        return coordinatejobcontent;
    }

    public void setCoordinatejobcontent(String coordinatejobcontent) {
        this.coordinatejobcontent = coordinatejobcontent;
    }

    public String getNocompletejobcontent() {
        return nocompletejobcontent;
    }

    public void setNocompletejobcontent(String nocompletejobcontent) {
        this.nocompletejobcontent = nocompletejobcontent;
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

    public List<Addaffair_person> getTargetList() {
        return targetList;
    }

    public void setTargetList(List<Addaffair_person> targetList) {
        this.targetList = targetList;
    }
}
