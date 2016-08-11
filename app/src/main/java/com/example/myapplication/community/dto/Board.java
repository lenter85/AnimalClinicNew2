package com.example.myapplication.community.dto;

/**
 * Created by Administrator on 2016-07-07.
 */
public class Board {

    private String bTitle;
    private String bContent;
    private String mId;
    private String bDate;
    private int bImage;

    public String getbTitle() {
        return bTitle;
    }

    public void setbTitle(String bTitle) {
        this.bTitle = bTitle;
    }

    public String getbContent() {
        return bContent;
    }

    public void setbContent(String bContent) {
        this.bContent = bContent;
    }

    public String getbDate() {
        return bDate;
    }

    public void setbDate(String bDate) {
        this.bDate = bDate;
    }

    public int getbImage() {
        return bImage;
    }

    public void setbImage(int bImage) {
        this.bImage = bImage;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }
}
