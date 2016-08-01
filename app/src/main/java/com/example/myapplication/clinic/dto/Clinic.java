package com.example.myapplication.clinic.dto;

public class Clinic {

    private String cid;
    private String cname;
    private int cscore;
    private String clocation;
    private String cimage1;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
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

    public String getClocation() {
        return clocation;
    }

    public void setClocation(String caddress) {
        this.clocation = caddress;
    }

    public String getCimage() { return cimage1; }

    public void setCimage(String cimage1) { this.cimage1 = cimage1; }

}
