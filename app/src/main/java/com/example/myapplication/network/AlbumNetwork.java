package com.example.myapplication.network;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016-07-22.
 */
public class AlbumNetwork {
    private static String baseUrl = "http://192.168.0.14:8080/Petopia";


    public static void getAlbumData() {

    }

    public static void getAlbumImage() {
        AsyncTask<String, Void, Bitmap> asyncTask = new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                Bitmap bitmap = null;

                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();

                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream is = conn.getInputStream();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


                return bitmap;
            }
        };
    }


}
