package com.example.myapplication.clinic.dto;

/**
 * Created by Administrator on 2016-07-08.
 */
public class Review {

    private String pname = "사진이름";
    private int clinicPicture=2;
    private String content= "안녕하세요";
    private int score=3;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Review(String pname, int clinicPicture, String content, int score) {
        this.pname = pname;
        this.clinicPicture = clinicPicture;
        this.content = content;
        this.score = score;
    }

    public Review () {}

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getClinicPicture() {
        return clinicPicture;
    }

    public void setClinicPicture(int clinicPicture) {
        this.clinicPicture = clinicPicture;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
