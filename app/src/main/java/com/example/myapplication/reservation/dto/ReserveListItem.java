package com.example.myapplication.reservation.dto;

/**
 * Created by Administrator on 2016-08-03.
 */
public class ReserveListItem {

    private String rId;
    private String rName;
    private String rPetName;
    private String rPhone;
    private String rTime;

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public String getrPhone() {
        return rPhone;
    }

    public void setrPhone(String rPhone) {
        this.rPhone = rPhone;
    }

    public String getrTime() {
        return rTime;
    }

    public void setrTime(String rTime) {
        this.rTime = rTime;
    }

    public String getrPetName() {
        return rPetName;
    }

    public void setrPetName(String rPetName) {
        this.rPetName = rPetName;
    }
}
