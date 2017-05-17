package com.zwd.crm.HomePage.client.Module;

/**
 * Created by asus-pc on 2017/3/16.
 */

public class ClientElement {
    private String name;
    private String link;
    private String position;
    private String creator;
    private String executor;
    private String state;

    public ClientElement(String name, String link, String position, String creator, String executor, String state) {
        this.name = name;
        this.link = link;
        this.position = position;
        this.creator = creator;
        this.executor = executor;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
