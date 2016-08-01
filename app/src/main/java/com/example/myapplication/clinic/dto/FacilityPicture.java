package com.example.myapplication.clinic.dto;

/**
 * Created by Administrator on 2016-07-07.
 */
public class FacilityPicture {
    private int image1;
    private int image2;

    public FacilityPicture(int image1, int image2) {
        this.image1 = image1;
        this.image2 = image2;
    }

    public int getImage1() {
        return image1;
    }

    public void setImage1(int image1) {
        this.image1 = image1;
    }

    public int getImage2() {
        return image2;
    }

    public void setImage2(int image2) {
        this.image2 = image2;
    }
}
