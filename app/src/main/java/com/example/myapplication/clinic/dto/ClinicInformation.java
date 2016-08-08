package com.example.myapplication.clinic.dto;

/**
 * Created by Administrator on 2016-07-14.
 */
public class ClinicInformation {
    private String cid;
    private String cname;
    private String clocation;
    private String cphone;
    private String copentime;
    private String cclosetime;
    private String cintroduction;
    private String clongitude;
    private String clatitude;

    //기본 생성자
    public ClinicInformation() {
    }

    public ClinicInformation(String cid, String cname, String clocation, String cphone, String copentime, String cclosetime, String cintroduction, String clongitude, String clatitude) {
        this.cid = cid;
        this.cname = cname;
        this.clocation = clocation;
        this.cphone = cphone;
        this.copentime = copentime;
        this.cclosetime = cclosetime;
        this.cintroduction = cintroduction;
        this.clongitude = clongitude;
        this.clatitude = clatitude;
    }

    public String getClongitude() {
        return clongitude;
    }

    public void setClongitude(String clongitude) {
        this.clongitude = clongitude;
    }

    public String getClatitude() {
        return clatitude;
    }

    public void setClatitude(String clatitude) {
        this.clatitude = clatitude;
    }

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

    public String getClocation() {
        return clocation;
    }

    public void setClocation(String clocation) {
        this.clocation = clocation;
    }

    public String getCphone() {
        return cphone;
    }

    public void setCphone(String cphone) {
        this.cphone = cphone;
    }

    public String getCopentime() {
        return copentime;
    }

    public void setCopentime(String copentime) {
        this.copentime = copentime;
    }

    public String getCclosetime() {
        return cclosetime;
    }

    public void setCclosetime(String cclosetime) {
        this.cclosetime = cclosetime;
    }

    public String getCintroduction() {
        return cintroduction;
    }

    public void setCintroduction(String cintroduction) {
        this.cintroduction = cintroduction;
    }
}
