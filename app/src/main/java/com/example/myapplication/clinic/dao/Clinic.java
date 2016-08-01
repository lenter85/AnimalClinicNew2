package com.example.myapplication.clinic.dao;

/**
 * Created by Administrator on 2016-07-06.
 */
public class Clinic {

    private int cnum;
    private String cname;
    private int cscore;
    private String caddress;

    public int getCnum() {
        return cnum;
    }

    public void setCnum(int cnum) {
        this.cnum = cnum;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getCscore() {
        return cscore;
    }

    public void setCscore(int cscore) {
        this.cscore = cscore;
    }

    public String getCaddress() {
        return caddress;
    }

    public void setCaddress(String caddress) {
        this.caddress = caddress;
    }
}
