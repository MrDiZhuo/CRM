package com.zwd.crm.HomePage.conference.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/4/14.
 */

public class conferenceGet {
    @SerializedName("exhibitionname")
    private String exhibitionname;
    @SerializedName("id")
    private int id;
    @SerializedName("exhibitionstatus")
    private int exhibitionstatus;

    @SerializedName("exhibitionstartdate")
    private String startdate;
    @SerializedName("exhibitionenddate")
    private String enddate;
    @SerializedName("exhibitionadress")
    private String address;
    @SerializedName("exhibitiondescription")
    private String description;

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExhibitionname() {
        return exhibitionname;
    }

    public void setExhibitionname(String exhibitionname) {
        this.exhibitionname = exhibitionname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExhibitionstatus() {
        return exhibitionstatus;
    }

    public void setExhibitionstatus(int exhibitionstatus) {
        this.exhibitionstatus = exhibitionstatus;
    }
}
