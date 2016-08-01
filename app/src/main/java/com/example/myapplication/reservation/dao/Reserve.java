package com.example.myapplication.reservation.dao;

/**
 * Created by Administrator on 2016-07-07.
 */
public class Reserve {

    String txtPname;
    String txtCname;
    String txtCaddress;
    String txtTime;
    String txtRtype;

    public Reserve(String txtPname, String txtCname, String txtCaddress, String txtTime, String txtRtype) {
        this.txtPname = txtPname;
        this.txtCname = txtCname;
        this.txtCaddress = txtCaddress;
        this.txtTime = txtTime;
        this.txtRtype = txtRtype;
    }

    public Reserve(String txtPname) {
        this.txtPname = txtPname;
    }



    public String getTxtRtype() {
        return txtRtype;
    }

    public void setTxtRtype(String txtRtype) {
        this.txtRtype = txtRtype;
    }

    public String getTxtCaddress() {
        return txtCaddress;
    }

    public void setTxtCaddress(String txtCaddress) {
        this.txtCaddress = txtCaddress;
    }

    public String getTxtCname() {
        return txtCname;
    }

    public void setTxtCname(String txtCname) {
        this.txtCname = txtCname;
    }

    public String getTxtPname() {
        return txtPname;
    }

    public void setTxtPname(String txtPname) {
        this.txtPname = txtPname;
    }

    public String getTxtTime() {
        return txtTime;
    }

    public void setTxtTime(String txtTime) {
        this.txtTime = txtTime;
    }
}
