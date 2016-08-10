package com.example.myapplication.network;


import android.os.AsyncTask;
import android.util.Log;

import com.example.myapplication.MainActivity;
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
    public boolean loginResult = false;
    public boolean joinResult = false;
    public boolean chkUserId = false;

    public boolean Login(final String mid, final String mpassword){
        Log.i("mylog","Login() 실행");
        Log.i("mylog","mid : " + mid);
        Log.i("mylog","mpass : " + mpassword);

        AsyncTask<String,Void,Boolean> asyncTask = new AsyncTask<String, Void, Boolean>() {
            String result = "fail";
            String json = "";
            @Override
            protected Boolean doInBackground(String... params) {
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
                Log.i("mylog",json);
                if(!json.equals(null)){
                    try{
                        JSONObject jsonObject = new JSONObject(json);
                        Member member = new Member();
                        member.setMid(jsonObject.getString("mid"));
                        member.setMpassword(jsonObject.getString("mpassword"));
                        member.setMname(jsonObject.getString("mname"));
                        member.setMphone(jsonObject.getString("mphone"));
                        member.setMtype(jsonObject.getString("mtype"));
                        member.setMimage(jsonObject.getString("mimage"));

                        if(member.getMid().equals("")){
                            loginResult = false;
                            MainActivity.loginStatus = loginResult;
                        } else {
                            loginResult = true;
                            MainActivity.loginStatus = loginResult;
                        }

                        Log.i("mylog",member.getMid());
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }

                return loginResult;
            }
        };
        try {
            loginResult = asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, NetworkSetting.baseUrl + "member/login").get();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return loginResult;
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

    public boolean checkUserId(final String mid){
        Log.i("mylog","checkUserId() 실행");
        AsyncTask<String,Void,Boolean> asyncTask = new AsyncTask<String, Void, Boolean>() {
            String body = ""; //null로 초기화하면 안된다.
            @Override
            protected Boolean doInBackground(String... params) {

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

                    if(body.equals("1")){
                        chkUserId = true;
                    } else if(body.equals("0")){
                        chkUserId = false;
                    }

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return chkUserId;
            }
        };
        try {
            chkUserId = asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, NetworkSetting.baseUrl+"member/checkUserId").get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chkUserId;
    }
}
