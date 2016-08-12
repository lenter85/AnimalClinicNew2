package com.example.myapplication.network;

import android.os.AsyncTask;

import com.example.myapplication.MainActivity;
import com.example.myapplication.diary.MyDiaryFragment;
import com.example.myapplication.diary.dto.Weight;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2016-07-22.
 */
public class WeightNetwork {
    private static String url = NetworkSetting.baseUrl2;
    public static void registerWeight(final double weight, final String wdate) {
        AsyncTask<String, Void, Void> asyncTask = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setRequestMethod("POST");
                    conn.connect();

                    OutputStream os = conn.getOutputStream();
                    String data = "weight=" + weight + "&wdate=" + wdate +
                    "&mid=" + MainActivity.loginId + "&dname=" + MyDiaryFragment.my.getDname();

                    os.write(data.getBytes());
                    os.flush();
                    os.close();

                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    }
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

            asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url + "weight/register");

    }





}
