package com.example.myapplication.clinic.dto;

/**
 * Created by Administrator on 2016-07-14.
 */
public class ClinicInformation {
    private String cid;
    private String cname;
    private String clocation;
    private String cdlocation;
    private String copentime;
    private String cclosetime;
    private String cintroduction;

    //기본 생성자
    public ClinicInformation() {
    }

    public ClinicInformation(String cid, String cname, String clocation, String cdlocation, String copentime, String cclosetime, String cintroduction) {
        this.cid = cid;
        this.cname = cname;
        this.clocation = clocation;
        this.cdlocation = cdlocation;
        this.copentime = copentime;
        this.cclosetime = cclosetime;
        this.cintroduction = cintroduction;
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

    public String getCdlocation() {
        return cdlocation;
    }

    public void setCdlocation(String cdlocation) {
        this.cdlocation = cdlocation;
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
