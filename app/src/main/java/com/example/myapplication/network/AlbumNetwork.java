package com.example.myapplication.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.myapplication.diary.AlbumAdapter;
import com.example.myapplication.diary.dto.Album;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016-07-22.
 */
public class AlbumNetwork {
    private static String baseUrl = "http://192.168.0.24:8080/Petopia/";


    public static void getAlbumData(int pageNo, final AlbumAdapter albumAdapter) {
        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String strjson = "";

                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();

                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream is = conn.getInputStream();
                        Reader reader = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(reader);

                        String data=null;
                        while((data=br.readLine())!=null) {
                            strjson += data;
                        }
                        br.close();
                        reader.close();
                        is.close();
                    }

                    conn.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return strjson;
            }

            @Override
            protected void onPostExecute(String strjson) {
                try {
                    JSONArray jsonArray = new JSONArray(strjson);
                    for(int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Album album = new Album();
                        album.setAname(jsonObject.getString("aname"));
                        album.setAdate(jsonObject.getString("adate"));
                        album.setAimage(jsonObject.getString("aimage"));
                        album.setAlocation(jsonObject.getString("alocation"));
                        //album.setAimagelarge(jsonObject.getString("aimagelarge"));
                        albumAdapter.addItem(album);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        asyncTask.execute(baseUrl + "album/getlist?pageNo=" + pageNo);


    }

    public static void getAlbumImage(final String imageName, final ImageView imageView) {
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
                        bitmap = BitmapFactory.decodeStream(is);
                        is.close();
                    }

                    conn.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }


                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        };

        asyncTask.execute(baseUrl + "album/download?aimage=" + imageName);
    }



}
