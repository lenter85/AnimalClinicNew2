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
    public boolean loginResult;
    //public String chkResult;

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
                        loginResult = true;

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
                }
            }
        };
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, NetworkSetting.baseUrl + "member/login");
    }

    public void join(final Member member){
        AsyncTask<String,Void,String> asyncTask = new AsyncTask<String, Void, String>() {
            String body = ""; //null로 초기화하면 안된다.
            @Override
            protected String doInBackground(String... params) {

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
                    String data = "mid="+member.getMid()+"&mpassword="+member.getMpassword()+"&mname="+member.getMname()+"&mphone="+member.getMphone()+"&mtype="+member.getMtype()+"&mimage=null";
                    byte[] bytedata = data.getBytes();
                    os.write(bytedata);
                    os.flush();


                    //응답 수신
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                        InputStream is = conn.getInputStream();
                        Reader reader = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(reader);
                        Log.i("mylog",""+conn.getResponseCode());

                        while(true) {
                            String line = br.readLine();
                            if (line == null) {
                                break;
                            }
                            body += line;
                        }
                        br.close();
                        reader.close();
                        is.close();


                    }

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return body;
            }

            @Override
            protected void onPostExecute(String s) {

            }
        };

        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, NetworkSetting.baseUrl+"member/join");
    }

    public void checkUserId(final String mid, final Member member){
        Log.i("mylog","checkUserId() 실행");
        AsyncTask<String,Void,String> asyncTask = new AsyncTask<String, Void, String>() {
            String body = ""; //null로 초기화하면 안된다.
            @Override
            protected String doInBackground(String... params) {

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
                    String data = "mid="+mid;
                    byte[] bytedata = data.getBytes();
                    os.write(bytedata);
                    os.flush();


                    //응답 수신
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                        InputStream is = conn.getInputStream();
                        Reader reader = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(reader);
                        Log.i("mylog","chkchkchk"+conn.getResponseCode());

                        while(true) {
                            String line = br.readLine();
                            if (line == null) {
                                break;
                            }
                            body += line;
                        }
                        br.close();
                        reader.close();
                        is.close();
                    }

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return body;
            }

            @Override
            protected void onPostExecute(String body) {
                if(body.equals("1")){
                    //Toast.makeText(getContext(),"동일한 ID 존재",Toast.LENGTH_SHORT).show();
                    Log.i("mylog","동일한 ID 존재");
                } else if(body.equals("0")){
                    join(member);
                }
            }
        };
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, NetworkSetting.baseUrl+"member/checkUserId");
    }
}
