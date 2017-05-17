package com.zwd.crm.HomePage.affairXiangQing.Module;

/**
 * Created by asus-pc on 2017/5/3.
 */

public class AffairXiangQingAnswerElement {
    private String answercontent;
    private int answerusersid;
    private int parrentid;
    private int answerstatus;
    private int noticeid;
    private String answerdate;

    public String getAnswerdate() {
        return answerdate;
    }

    public void setAnswerdate(String answerdate) {
        this.answerdate = answerdate;
    }

    public String getAnswercontent() {
        return answercontent;
    }

    public void setAnswercontent(String answercontent) {
        this.answercontent = answercontent;
    }

    public int getAnswerusersid() {
        return answerusersid;
    }

    public void setAnswerusersid(int answerusersid) {
        this.answerusersid = answerusersid;
    }

    public int getAnswerstatus() {
        return answerstatus;
    }

    public void setAnswerstatus(int answerstatus) {
        this.answerstatus = answerstatus;
    }

    public int getParrentid() {
        return parrentid;
    }

    public void setParrentid(int parrentid) {
        this.parrentid = parrentid;
    }

    public int getNoticeid() {
        return noticeid;
    }

    public void setNoticeid(int noticeid) {
        this.noticeid = noticeid;
    }
}
