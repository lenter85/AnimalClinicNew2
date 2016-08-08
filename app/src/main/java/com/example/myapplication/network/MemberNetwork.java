package com.example.myapplication.network;


import android.os.AsyncTask;
import android.util.Log;

import com.example.myapplication.member.dto.Member;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MemberNetwork {
    public void Login(final String mid, final String mpassword){
        Log.i("mylog","Login() 실행");
        Log.i("mylog","mid : " + mid);
        Log.i("mylog","mpass : " + mpassword);

        AsyncTask<String,Void,String> asyncTask = new AsyncTask<String, Void, String>() {
            String result = "fail";
            String json = "";
            @Override
            protected String doInBackground(String... params) {
                Log.i("mylog","Login() doInBackground 실행");
                try {
                    URL url = new URL(params[0]);
                    //커넥션 객체 생성
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setRequestMethod("POST");
                    conn.connect();

                    //요청 내용 본문에 작성
                    OutputStream os = conn.getOutputStream();
                    String data = "mid=" + mid + "&mpassword=" + mpassword;
                    byte[] bytedata = data.getBytes();
                    os.write(bytedata);
                    os.flush();

                    //응답 수신
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                        InputStream is = conn.getInputStream();
                        Reader reader = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(reader);

                        while(true) {
                            String line = br.readLine();
                            if (line == null) {
                                break;
                            }
                            json += line;
                        }
                        br.close();
                        reader.close();
                        is.close();
                    }
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        result = "success";
                        Log.i("mylog","결과 : " + result);
                    }

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return json;
            }
            @Override
            protected void onPostExecute(String json) {
                if(result == "success"){
                    try{
                        JSONObject jsonObject = new JSONObject(json);
                        Member member = new Member();
                        member.setMid(jsonObject.getString("mid"));
                        member.setMpassword(jsonObject.getString("mpassword"));
                        member.setMname(jsonObject.getString("mname"));
                        member.setMphone(jsonObject.getString("mphone"));
                        member.setMtype(jsonObject.getString("mtype"));
                        member.setMimage(jsonObject.getString("mimage"));

                        Log.i("mylog",member.getMid());
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                } else {

                }
            }
        };
        asyncTask.execute(NetworkSetting.baseUrl + "member/login");
    }
}
