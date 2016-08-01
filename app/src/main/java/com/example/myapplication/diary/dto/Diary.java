package com.example.myapplication.diary.dto;

/**
 * Created by Administrator on 2016-07-11.
 */
public class Diary {

    String dname;
    String dbirth=null;
    String dgender;
    String dimage;
    String mid;

    /*public Diary(String dname, String dbirth, String dgender, String dimage, String mid) {
        this.dname = dname;
        this.dbirth = dbirth;
        this.dgender = dgender;
        this.dimage = dimage;
        this.mid = mid;
    }*/



    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getDbirth() {
        return dbirth;
    }

    public void setDbirth(String dbirth) {
        this.dbirth = dbirth;
    }

    public String getDgender() {
        return dgender;
    }

    public void setDgender(String dgender) {
        this.dgender = dgender;
    }

    public String getDimage() {
        return dimage;
    }

    public void setDimage(String dimage) {
        this.dimage = dimage;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}

