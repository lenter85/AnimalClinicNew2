package com.example.myapplication.network;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by COM on 2016-08-08.
 */
public class HomeNetwork {
    private static String baseUrl = "http://192.168.0.29:8080/Petopia/";

    public static void sendImage(final String imageName, final Bitmap bitmap) {
        AsyncTask<String, Void, Void> asynckTask = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {

                URL url = null;
                try {
                    url = new URL(params[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    OutputStream out = conn.getOutputStream();

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    if(imageName.contains(".jpg")) {
                        Log.i("mylog", imageName);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
                    } else if(imageName.contains(".png")) {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 50, bos);
                    }

                    byte[] bytes = bos.toByteArray();
                    out.write(bytes);

                    bos.close();

                    out.flush();
                    out.close();

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    }

                    conn.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        };

        asynckTask.execute(baseUrl + "registerhomeimg");
    }
}
