package com.example.myapplication.network;

import android.os.AsyncTask;

import com.example.myapplication.diary.dto.Weight;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016-07-22.
 */
public class WeightNetwork {
    private static String url = NetworkSetting.baseUrl2;
    public void registerWeight(double weight, String wdate) {
        AsyncTask<String, Void, Void> asyncTask = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    }
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url + "weight/register?weight=" + weight + "?wdate=" + wdate);
    }
}
