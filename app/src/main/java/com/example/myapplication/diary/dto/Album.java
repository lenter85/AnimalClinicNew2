package com.example.myapplication.diary.dto;

/**
 * Created by Administrator on 2016-07-11.
 */
public class Album {
    private int picture;
    private int ano;
    private String aimage;
    private String aimagelarge;
    private String mid;
    private String adate;
    private String aname;
    //String acontent;
    String alocation;

    public String getAimagelarge() {
        return aimagelarge;
    }

    public void setAimagelarge(String aimagelarge) {
        this.aimagelarge = aimagelarge;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getAimage() {
        return aimage;
    }

    public void setAimage(String aimage) {
        this.aimage = aimage;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getAdate() {
        return adate;
    }

    public void setAdate(String adate) {
        this.adate = adate;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getAlocation() {
        return alocation;
    }

    public void setAlocation(String alocation) {
        this.alocation = alocation;
    }
}
