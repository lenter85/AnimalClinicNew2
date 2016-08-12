package com.example.myapplication.reservation.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-07-22.
 */
public class Util {

    public static String getSimpleDate(String date){

        date = date.replace("(오늘)","");
        date = date.replace("년","-");
        date = date.replace("월","-");
        date = date.replace("일","");

        return date;
    }


    public static List<String> getClinicTimeList() {

        List<String> clinicTimeList = new ArrayList<>();

        clinicTimeList.add("09:00");
        clinicTimeList.add("09:30");
        clinicTimeList.add("10:30");
        clinicTimeList.add("11:00");
        clinicTimeList.add("11:30");
        clinicTimeList.add("12:00");
        clinicTimeList.add("12:30");
        clinicTimeList.add("13:00");
        clinicTimeList.add("13:30");
        clinicTimeList.add("14:00");
        clinicTimeList.add("14:30");
        clinicTimeList.add("15:00");
        clinicTimeList.add("15:30");
        clinicTimeList.add("16:00");
        clinicTimeList.add("16:30");
        clinicTimeList.add("17:00");
        clinicTimeList.add("17:30");
        clinicTimeList.add("18:00");
        clinicTimeList.add("18:30");
        clinicTimeList.add("19:00");
        //clinicTimeList.add("19:30");
        //clinicTimeList.add("20:00");
//        clinicTimeList.add("20:30");
//        clinicTimeList.add("21:00");
//        clinicTimeList.add("21:30");
//        clinicTimeList.add("22:00");
//        clinicTimeList.add("22:30");
//        clinicTimeList.add("23:00");
//        clinicTimeList.add("23:30");
//        clinicTimeList.add("24:00");

        return clinicTimeList;
    }


    public static void showToast(Context context, String message){
        Toast toast ;
        toast = Toast.makeText(context,  message , Toast.LENGTH_LONG);
        int offsetX = 0;
        int offsetY = 0;
        toast.setGravity(Gravity.CENTER, offsetX, offsetY);
        toast.show();
    }
}
