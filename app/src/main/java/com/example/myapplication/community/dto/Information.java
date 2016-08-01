package com.example.myapplication.community.dto;

public class Information {
    private String iTitle;
    private String iContent;
    private String iDate;
    private String id;
    private int infoImageView;
    private int infoImageViewLarge;

    public String getiTitle() {
        return iTitle;
    }

    public void setiTitle(String iTitle) {
        this.iTitle = iTitle;
    }

    public String getiContent() {
        return iContent;
    }

    public void setiContent(String iContent) {
        this.iContent = iContent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getInfoImageView() {
        return infoImageView;
    }

    public void setInfoImageView(int infoImageView) {
        this.infoImageView = infoImageView;
    }

    public int getInfoImageViewLarge() {
        return infoImageViewLarge;
    }

    public void setInfoImageViewLarge(int infoImageViewLarge) {
        this.infoImageViewLarge = infoImageViewLarge;
    }

    public String getiDate() {
        return iDate;
    }

    public void setiDate(String iDate) {
        this.iDate = iDate;
    }
}
