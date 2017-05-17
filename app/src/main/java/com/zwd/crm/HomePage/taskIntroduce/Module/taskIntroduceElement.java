package com.zwd.crm.HomePage.taskIntroduce.Module;

/**
 * Created by asus-pc on 2017/3/10.
 */

public class taskIntroduceElement {
    private String name;
    private String time;
    private String text;
    private String juese;
    private String state;

    public taskIntroduceElement(String name, String time, String text, String juese, String state) {
        this.name = name;
        this.time = time;
        this.text = text;
        this.juese = juese;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getJuese() {
        return juese;
    }

    public void setJuese(String juese) {
        this.juese = juese;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
