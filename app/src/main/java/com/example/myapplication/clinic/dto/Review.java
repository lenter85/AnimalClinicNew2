package com.example.myapplication.clinic.dto;

/**
 * Created by Administrator on 2016-07-08.
 */
public class Review {

    private int rno;
    private String rcontent;
    private int rscore;
    private String rimage;
    private String ruserid="test";
    private String rclinicid;

    public Review() {}

    public Review(int rno, String rcontent, int rscore, String rimage, String ruserid, String rclinicid) {
        this.rno = rno;
        this.rcontent = rcontent;
        this.rscore = rscore;
        this.rimage = rimage;
        this.ruserid = ruserid;
        this.rclinicid = rclinicid;
    }

    public int getRno() {
        return rno;
    }

    public void setRno(int rno) {
        this.rno = rno;
    }

    public String getRcontent() {
        return rcontent;
    }

    public void setRcontent(String rcontent) {
        this.rcontent = rcontent;
    }

    public int getRscore() {
        return rscore;
    }

    public void setRscore(int rscore) {
        this.rscore = rscore;
    }

    public String getRimage() {
        return rimage;
    }

    public void setRimage(String rimage) {
        this.rimage = rimage;
    }

    public String getRuserid() {
        return ruserid;
    }

    public void setRuserid(String ruserid) {
        this.ruserid = ruserid;
    }

    public String getRclinicid() {
        return rclinicid;
    }

    public void setRclinicid(String rclinicid) {
        this.rclinicid = rclinicid;
    }
}
