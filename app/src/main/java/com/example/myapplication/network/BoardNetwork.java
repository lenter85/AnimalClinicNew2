package com.example.myapplication.network;

import android.os.AsyncTask;
import android.util.Log;

import com.example.myapplication.community.dto.Board;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BoardNetwork {
    public void write(final Board board){
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
                    String data = "mid="+board.getmId()+"&bTitle="+board.getbTitle()+"&bcontent="+board.getbContent()+"&mimage=member01.jpg";
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
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, NetworkSetting.baseUrl+"board/write");
    }
}
