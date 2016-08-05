package com.example.myapplication.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.myapplication.diary.AlbumAdapter;
import com.example.myapplication.diary.dto.Album;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

    public static void sendAlbum(final Album album, final Bitmap bitmap) {
        AsyncTask<String, Void, Void> asyncTask = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {

                try {
                    // 데이터 구분 문자
                    String boundary = "----" + System.currentTimeMillis();

                    // 데이터 경계선
                    String delimiter = "\r\n--" + boundary + "\r\n";

                    // 커넥션 생성 및 설정
                    URL url = new URL(params[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                    conn.connect();


                    OutputStream out = conn.getOutputStream();

                    StringBuffer postDataBuilder = new StringBuffer();
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("aimage", album.getAimage()));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("aname", album.getAname()));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("adate", album.getAdate()));
                    Log.i("mylog", album.getAdate());
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("acontent", album.getAcontent()));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("mid", "test"));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setFile("aaimage", album.getAimage()));
                    out.write(postDataBuilder.toString().getBytes());

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    if(album.getAimage().contains(".jpg")) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, bos);
                    } else if(album.getAimage().contains(".png")) {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 30, bos);
                    }
                    byte[] bitmapdata = bos.toByteArray();
                    out.write(bitmapdata);
                    bos.close();

                    //종료 구분자 넣기
                    out.write(("\r\n--" + boundary + "--\r\n").getBytes());

                    //출력스트림 닫기
                    out.flush();
                    out.close();

                    //응답 코드 확인
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    }

                    //연결 끊기
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        };
        asyncTask.execute(baseUrl + "album/register");
    }

    public static String setValue(String key, String value) {
        String str = "Content-Disposition: form-data; name=\"" + key + "\"";
        str += "\r\n\r\n";
        str += value;
        return str;
    }

    public static String setFile(String key, String fileName) {
        String str = "Content-Disposition: form-data; name=\"" + key + "\"; filename=\"" + fileName + "\"";
        str += "\r\n";
        str += "Content-Type: image/png";
        str += "\r\n\r\n";
        return str;
    }

}
