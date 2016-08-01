package com.example.myapplication.reservation.dto;

/**
 * Created by Administrator on 2016-07-07.
 */
public class Reserve {

    int rno;
    String rtype;
    String rdate;
    String rtime;
    String rclinicid;
    String ruserid;
    String rpname;
    String raddress;
    String rclinicName;

    public int getRno() {
        return rno;
    }

    public void setRno(int rno) {
        this.rno = rno;
    }

    public String getRtype() {
        return rtype;
    }

    public void setRtype(String rtype) {
        this.rtype = rtype;
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public String getRtime() {
        return rtime;
    }

    public void setRtime(String rtime) {
        this.rtime = rtime;
    }

    public String getRclinicid() {
        return rclinicid;
    }

    public void setRclinicid(String rclinicid) {
        this.rclinicid = rclinicid;
    }

    public String getRuserid() {
        return ruserid;
    }

    public void setRuserid(String ruserid) {
        this.ruserid = ruserid;
    }

    public String getRpname() {
        return rpname;
    }

    public void setRpname(String rpname) {
        this.rpname = rpname;
    }

    public String getRaddress() {
        return raddress;
    }

    public void setRaddress(String raddress) {
        this.raddress = raddress;
    }

    public String getRclinicName() {
        return rclinicName;
    }

    public void setRclinicName(String rclinicName) {
        this.rclinicName = rclinicName;
    }
}
