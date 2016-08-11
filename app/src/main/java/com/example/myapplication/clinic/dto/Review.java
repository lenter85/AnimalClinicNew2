package com.example.myapplication.clinic.dto;

import com.example.myapplication.MainActivity;

/**
 * Created by Administrator on 2016-07-08.
 */
public class Review {

    private int rno;
    private String rcontent;
    private float rscore;
    private String rimage;
    private String ruserid= MainActivity.loginId;
    private String rclinicid=MainActivity.clinicId;

    public Review() {}

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

    public float getRscore() {
        return rscore;
    }

    public void setRscore(float rscore) {
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
