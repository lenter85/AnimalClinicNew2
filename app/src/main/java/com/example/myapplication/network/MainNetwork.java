package com.example.myapplication.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.myapplication.clinic.dto.Clinic;
import com.example.myapplication.clinic.fragment.ClinicListViewAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-07-14.
 */
public class MainNetwork {

    private Bitmap bm;
    private static final String site = "http://192.168.0.21:8080/Petopia";

    public static void setReserveList(final ClinicListViewAdapter clinicListViewAdapter) {

        //첫번째 매개변수는 doInBackground()로, 두번째 매개변수는 onProgressUpdate()로, 세번째 매개변수는 반환값을 의미한다.
        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {

            //doInBackground의 리턴값 String은 onPostExecute의 매개변수로 들어간다.
            @Override
            protected String doInBackground(String... params) {

                String json = "";

                try {
                    //먼저 URL 객체를 만든다.
                    URL url = new URL(params[0]);
                    Log.i("myLog", params[0]);

                    //연결 객체를 만든다.
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Content-Type", "application/json");
                    //실제 연결
                    conn.connect();

                    /* 응답 */

                    //요청을 처리하고 응답이 온다.
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) { //200은 정상 응답을 의미한다. ( 잘못 요청: 404, 서버 에러: 500 )
                        //Log.i("myLog", "응답 OK를 받았음");
                        //응답코드가 200번일 경우에 본문 내용을 읽고싶다.

                        InputStream is = conn.getInputStream(); //응답의 결과를 읽는다.
                        Reader reader = new InputStreamReader(is); //문자일 경우 Reader로 읽는 것이 더 낫다.
                        BufferedReader br = new BufferedReader(reader); //한 라인 단위로 읽으려면 BufferedReader로 읽는 것이 낫다. 보조 스트림을 다는 것.

                        while (true) {
                            String line = br.readLine(); //한 줄씩 읽는다.
                            if (line == null) break; //행을 다 읽었을 경우 null이 나온다. while문을 빠져나온다.
                            json += line; //body += line;
                        }

                        br.close();
                        reader.close();
                        is.close();

                    } else {
                        Log.i("myLog", "응답 OK를 받지못하였음");
                    }

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return json;
            }

            @Override
            protected void onPostExecute(String json) {

                List<Clinic> clinicList = new ArrayList<>();

                //JSON을 파싱하는 코드
                try {

                    JSONArray root = new JSONArray(json);
                    for (int i = 0; i < root.length(); i++) {

                        JSONObject jsonObject = root.getJSONObject(i);
                        String cid = jsonObject.getString("cid");
                        String cname = jsonObject.getString("cname");
                        String clocation = jsonObject.getString("clocation");
                        String cimage = jsonObject.getString("cimage");

                        Clinic clinic = new Clinic();
                        clinic.setCid(cid);
                        clinic.setCname(cname);
                        clinic.setClocation(clocation);
                        clinic.setCimage(cimage);

                        clinicListViewAdapter.addItem(clinic);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        Log.i("myLog", site + "/main/getClinicList");

        asyncTask.execute(site + "/main/getClinicList"); //doInBackground()의 매개값으로 들어간다.
    }

    public static void getClinicImage(String imageName, final ImageView imageView){
        Log.i("myLog", imageName);
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
                Log.i("myLog", "2");
                imageView.setImageBitmap(bitmap);
            }
        };

        asyncTask.execute(site+"/main/download?cimage=" + imageName);
    }



}