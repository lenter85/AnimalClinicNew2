package com.example.myapplication.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.clinic.dto.Clinic;
import com.example.myapplication.clinic.fragment.ClinicListViewAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
    private static final String site = "http://192.168.0.11:9090/Petopia";
    private static String baseUrl = "http://192.168.0.29:8080/Petopia/";

    public static void setReserveList(final ClinicListViewAdapter clinicListViewAdapter, final Context context) {

        Log.i("test1", "setReserveList 메소드 호출");

        //첫번째 매개변수는 doInBackground()로, 두번째 매개변수는 onProgressUpdate()로, 세번째 매개변수는 반환값을 의미한다.
        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {

            ProgressDialog asyncDialog = new ProgressDialog(context);

            @Override
            protected void onPreExecute() {
                Log.i("test", "setReserveList onPreExecute()실행 ");
                asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                asyncDialog.setMessage("로딩중입니다..");

                // show dialog
                asyncDialog.show();
                super.onPreExecute();
            }

            //doInBackground의 리턴값 String은 onPostExecute의 매개변수로 들어간다.
            @Override
            protected String doInBackground(String... params) {

                Log.i("test", "setReserveList doInBackground()실행 ");
                String json = "";

                try {
                    //먼저 URL 객체를 만든다.
                    URL url = new URL(params[0]);
                    Log.i("test", params[0]);

                    //연결 객체를 만든다.
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Content-Type", "application/json");
                    //실제 연결
                    Log.i("test", "연결 요청");
                    conn.connect();
                    Log.i("test", "연결 완료");

                    /* 응답 */

                    //요청을 처리하고 응답이 온다.
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) { //200은 정상 응답을 의미한다. ( 잘못 요청: 404, 서버 에러: 500 )
                        Log.i("test", "응답 OK를 받았음");
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
                        Log.i("test", "응답 OK를 받지못하였음");
                    }

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return json;
            }

            @Override
            protected void onPostExecute(String json) {
                Log.i("test", "setReserveList onPostExecute()실행 ");
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

                asyncDialog.dismiss();
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

    public static void registerMemberImage(final String mid, final String fileName, final Bitmap bitmap) {
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

                    //연결하기
                    conn.connect();

                    //출력 스트림 얻기
                    DataOutputStream out = new DataOutputStream(new BufferedOutputStream(conn.getOutputStream()));

                    //문자열 데이터 전송
                    StringBuffer postDataBuilder = new StringBuffer();
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("mid", mid));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setFile("memberimage", fileName));
                    out.writeUTF(postDataBuilder.toString());


                    //파일 데이터 전송
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 50, bos);
                    /*if(album.getAimage().contains(".jpg")) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
                    } else if(album.getAimage().contains(".png")) {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 50, bos);
                    }*/
                    byte[] bitmapdata = bos.toByteArray();
                    out.write(bitmapdata);
                    bos.close();


                    //종료 구분자 넣기
                    //out.writeUTF(delimiter);
                    out.writeUTF("\r\n--" + boundary + "--\r\n");

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

        asyncTask.execute(baseUrl + "registermemberimage");
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