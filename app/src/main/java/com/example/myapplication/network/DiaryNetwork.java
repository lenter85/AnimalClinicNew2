package com.example.myapplication.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.myapplication.diary.DiaryAdapter;
import com.example.myapplication.diary.dto.Diary;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-07-25.
 */
public class DiaryNetwork {

    private static String baseUrl ="http://192.168.0.24:8080/Petopia/";
    public static void getDiaryData(final DiaryAdapter diaryAdapter) {
        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String json="";
                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    if(conn.getResponseCode()==HttpURLConnection.HTTP_OK) {
                        InputStream is = conn.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        String data = null;
                        while((data=br.readLine())!=null) {
                            json += data;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return json;
            }

            @Override
            protected void onPostExecute(String json) {
                List<Diary> list = new ArrayList<>();

                try {
                    JSONArray jsonArray = new JSONArray(json);
                    for(int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Diary diary = new Diary();
                        diary.setDname(jsonObject.getString("dname"));

                        diary.setDgender(jsonObject.getString("dgender"));
                        diary.setDimage(jsonObject.getString("dimage"));
                        diaryAdapter.addItem(diary);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        asyncTask.execute(baseUrl + "diary/list");
    }

    public static void getDiaryImage(String imageName, final ImageView imageView) {
        AsyncTask<String, Void, Bitmap> asyncTask = new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                Bitmap bitmap=null;
                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    if(conn.getResponseCode()==HttpURLConnection.HTTP_OK) {

                        InputStream is = conn.getInputStream();
                        bitmap= BitmapFactory.decodeStream(is);
                        is.close();
                    }
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
        asyncTask.execute(baseUrl + "diary/image?dimage=" + imageName);
    }

    public static void sendDiary(final Diary diary, final String filePath) {
        new AsyncTask<Void, Integer, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String result = "fail";
                try {
                    // 데이터 구분 문자
                    String boundary = "----" + System.currentTimeMillis();

                    // 데이터 경계선
                    String delimiter = "\r\n--" + boundary + "\r\n";

                    // 커넥션 생성 및 설정
                    URL url = new URL(baseUrl + "diary/register");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                    //연결하기
                    conn.connect();

                    //출력 스트림 얻기
                    DataOutputStream out = new DataOutputStream(new BufferedOutputStream(conn.getOutputStream()));
                    Log.i("mylog", filePath);
                    File file = new File(filePath);
                    diary.setDimage(file.getName());
                    //문자열 데이터 전송
                    StringBuffer postDataBuilder = new StringBuffer();
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("dname", diary.getDname()));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("dbirth", diary.getDbirth()));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("dgender", diary.getDgender()));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("mid", "test"));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setFile("dimage", diary.getDimage()));
                    out.writeUTF(postDataBuilder.toString());

                    //파일 데이터 전송
                    FileInputStream fis = new FileInputStream(filePath);
                    byte[] byteArray = new byte[1024];
                    int readByteNum = -1;
                    while((readByteNum = fis.read(byteArray)) != -1) {
                        out.write(byteArray, 0, readByteNum);
                    }
                    fis.close();

                    //종료 구분자 넣기
                    //out.writeUTF(delimiter);
                    out.writeUTF("\r\n--" + boundary + "--\r\n");

                    //출력스트림 닫기
                    out.flush();
                    out.close();

                    //응답 코드 확인
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        result = "success";
                        Log.i("mylog", result);
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
        }.execute();
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
