package com.zwd.crm.HomePage.reportSend.Module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by asus-pc on 2017/3/12.
 */

public class Report_SendGet {
    @SerializedName("reportnamelist")
    private List<Reportnamelist> reportnamelists;
    @SerializedName("id")
    private int id;
    @SerializedName("reporttype")
    private String reporttype;
    @SerializedName("reportdate")
    private String reportdate;
    private String resourceId;

    public List<Reportnamelist> getReportnamelists() {
        return reportnamelists;
    }

    public void setReportnamelists(List<Reportnamelist> reportnamelists) {
        this.reportnamelists = reportnamelists;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReporttype() {
        return reporttype;
    }

    public void setReporttype(String reporttype) {
        this.reporttype = reporttype;
    }

    public String getReportdate() {
        return reportdate;
    }

    public void setReportdate(String reportdate) {
        this.reportdate = reportdate;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}
