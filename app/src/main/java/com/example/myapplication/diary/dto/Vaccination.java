package com.example.myapplication.diary.dto;

/**
 * Created by Administrator on 2016-07-08.
 */
public class Vaccination {
    private String vname;
    private String vdate;
    private String vndate;
    private String mid;
    private String dname;

    public Vaccination(String vname, String vdate, String vndate, String mid, String dname) {
        this.vname = vname;
        this.vdate = vdate;
        this.vndate = vndate;
        this.mid = mid;
        this.dname = dname;
    }

    public Vaccination () {}

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getVdate() {
        return vdate;
    }

    public void setVdate(String vdate) {
        this.vdate = vdate;
    }

    public String getVndate() {
        return vndate;
    }

    public void setVndate(String vndate) {
        this.vndate = vndate;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }
}

