package com.example.myapplication.network;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.example.myapplication.MainActivity;
import com.example.myapplication.clinic.dto.Review;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by COM on 2016-08-08.
 */
public class HomeNetwork {
    private static String baseUrl = NetworkSetting.baseUrl3;

    public static void sendImage(final String mid, final Bitmap bitmap) {
        Log.i("mylog", "homenetwork sendImage 실행 " );
        new AsyncTask<Void, Integer, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String result = "fail";
                try {
                    // 데이터 구분 문자
                    String boundary = "----" + System.currentTimeMillis();

                    // 데이터 경계선
                    String delimiter = "\r\n--" + boundary + "\r\n";    //규약

                    // 커넥션 생성 및 설정
                    URL url = new URL(NetworkSetting.baseUrl2 + "member/registerMemberImage");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                    conn.connect();

                    //post방식으로 보내기//
                    /*PrintWriter pw = new PrintWriter(conn.getOutputStream());
                    pw.println("json="+strJson);
                    pw.close();*/

                    //연결하기
                    //int resultCode = conn.getResponseCode();

                    //출력 스트림 얻기
                    OutputStream out = conn.getOutputStream();

                    //문자열 데이터 전송
                    StringBuffer postDataBuilder = new StringBuffer();
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("mid", mid));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setFile("memberImage", mid + ".png"));
                    out.write(postDataBuilder.toString().getBytes()); //첫번째 전송

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, bos);
                    byte[] bitmapdata = bos.toByteArray();

                    out.write(bitmapdata);
                    bos.close();


                    //종료 구분자 넣기  //세번째 전송
                    out.write(("\r\n--" + boundary + "--\r\n").getBytes());  //규약

                    //출력스트림 닫기
                    out.flush();
                    out.close();

                    bos.close();


                    //응답 코드 확인
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        result = "success";
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
                if (result.equals("success")) {
                    Log.i("mylog", "성공");

                } else {
                    Log.i("mylog", "실패");
                }
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
