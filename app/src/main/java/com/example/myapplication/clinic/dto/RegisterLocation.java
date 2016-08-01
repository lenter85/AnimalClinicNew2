package com.example.myapplication.clinic.dto;

/**
 * Created by Administrator on 2016-07-21.
 */
public class RegisterLocation {
    private String latitude;  //위도
    private String longitude; //경도
    private String address;

    public RegisterLocation() {}

    public RegisterLocation(String latitude, String longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
