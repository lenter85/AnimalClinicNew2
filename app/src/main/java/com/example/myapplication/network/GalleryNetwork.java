package com.example.myapplication.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.myapplication.community.dto.Gallery;
import com.example.myapplication.community.gallery.GalleryFragmentAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GalleryNetwork {

    public static void getGalleryData(int pageNo, final GalleryFragmentAdapter galleryFragmentAdapter){
        Log.i("mylog","getGalleryData() 실행");
        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String json = "";
                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.connect();

                    Log.i("mylog", ""+ conn.getResponseCode());
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream is = conn.getInputStream();
                        Reader reader = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(reader);
                        String data = null;
                        while((data = br.readLine()) != null) {
                            json += data;
                        }
                        br.close();
                        reader.close();
                        is.close();
                    }

                    conn.disconnect();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                return json;
            }

            @Override
            protected void onPostExecute(String json) {
                try {
                    JSONArray ja = new JSONArray(json);
                    Log.i("mylog", "json 파싱 중");
                    for(int i=0; i<ja.length();i++){
                        JSONObject jo = ja.getJSONObject(i);
                        Gallery gallery = new Gallery();
                        gallery.setGno(jo.getInt("gno"));
                        gallery.setMid(jo.getString("mid"));
                        gallery.setGtitle(jo.getString("gtitle"));
                        gallery.setGcontent(jo.getString("gcontent"));
                        gallery.setGdate(jo.getString("gdate"));
                        gallery.setGimage(jo.getString("gimage"));
                        gallery.setGimagelarge(jo.getString("gimagelarge"));

                        galleryFragmentAdapter.addItem(gallery);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        asyncTask.execute(NetworkSetting.baseUrl+"gallery/list?pageNo=" + pageNo);
    }

    public static void getGalleryImage(String imageName, final ImageView imageView){
        Log.i("mylog", imageName);
        AsyncTask<String, Void, Bitmap> asyncTask = new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                Bitmap body = null;
                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();

                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream is = conn.getInputStream();
                        body = BitmapFactory.decodeStream(is);
                        is.close();
                    }
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return body;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                Log.i("mylog", "2");
                imageView.setImageBitmap(bitmap);
            }
        };
        asyncTask.execute(NetworkSetting.baseUrl+"gallery/download?gimage=" + imageName);
    }

    public static void writeGallery(final Gallery gallery, final Bitmap bitmap, final Bitmap smallBitmap){
        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                Log.i("mylog", "갤러리 네트워크 : writeGallery() 실행");
                String result = "fail";
                try {
                    // 데이터 구분 문자
                    String boundary = "----" + System.currentTimeMillis();

                    // 데이터 경계선
                    String delimiter = "\r\n--" + boundary + "\r\n";    //규약

                    // 커넥션 생성 및 설정
                    URL url = new URL(params[0]);
                    // 커넥션 객체 획득
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                    conn.connect();


                    //출력 스트림 얻기
                    //DataOutputStream out = new DataOutputStream(new BufferedOutputStream(conn.getOutputStream()));
                    OutputStream os = conn.getOutputStream();

                    //문자열 데이터 전송
                    StringBuffer postDataBuilder = new StringBuffer();
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("mid",gallery.getMid()));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("gtitle",gallery.getGtitle()));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("gcontent",gallery.getGcontent()));
                    os.write(postDataBuilder.toString().getBytes()); //첫번째 전송

                    if(smallBitmap != null){
                        //이미지 전송
                        postDataBuilder = new StringBuffer();
                        postDataBuilder.append(delimiter);
                        postDataBuilder.append(setFile("attach1", gallery.getGimage()));
                        os.write(postDataBuilder.toString().getBytes());

                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        smallBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
                        byte[] bitmapdata = bos.toByteArray();

                        ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
                        byte[] byteArray = new byte[1024];
                        int readByteNum = -1;
                        while((readByteNum = bs.read(byteArray)) != -1) {
                            os.write(byteArray, 0, readByteNum);
                        }

                        bos.close();
                        bs.close();
                    }

                    if(bitmap != null){
                        //이미지 전송2
                        postDataBuilder = new StringBuffer();
                        postDataBuilder.append(delimiter);
                        postDataBuilder.append(setFile("attach2", gallery.getGimagelarge()));
                        os.write(postDataBuilder.toString().getBytes());

                        ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos2);
                        byte[] bitmapdata2 = bos2.toByteArray();

                        ByteArrayInputStream bs2 = new ByteArrayInputStream(bitmapdata2);
                        byte[] byteArray2 = new byte[1024];
                        int readByteNum2 = -1;
                        while((readByteNum2 = bs2.read(byteArray2)) != -1) {
                            os.write(byteArray2, 0, readByteNum2);
                        }

                        bos2.close();
                        bs2.close();
                    }

                    //종료 구분자 넣기  //세번째 전송
                    os.write(("\r\n--" + boundary + "--\r\n").getBytes());  //규약

                    //출력스트림 닫기
                    os.flush();

                    //응답 코드 확인
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        result = "success";
                        Log.i("mylog",result);
                    } else {
                        result = "fail";
                        Log.i("mylog",result);
                    }

                    //연결 끊기
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {

            }
        };
        asyncTask.execute(NetworkSetting.baseUrl + "gallery/write");
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
