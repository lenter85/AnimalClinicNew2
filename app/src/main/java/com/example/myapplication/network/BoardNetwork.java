package com.example.myapplication.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.community.board.BoardFragmentAdapter;
import com.example.myapplication.community.dto.Board;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                    String data = "mid="+board.getmId()+"&btitle="+board.getbTitle()+"&bcontent="+board.getbContent();
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
        };
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, NetworkSetting.baseUrl+"board/write");
    }

    public static void getBoard(int pageNo, final BoardFragmentAdapter boardFragmentAdapter){
        Log.i("mylog","getBoard() 실행");
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
                        Board board = new Board();
                        board.setmId(jo.getString("mid"));
                        board.setbTitle(jo.getString("btitle"));
                        board.setbContent(jo.getString("bcontent"));
                        board.setbDate(jo.getString("bdate"));
                        board.setbImage(jo.getString("mimage"));
                        Log.i("mylog", "getBoard메소드 실행중 board객체에 저장된 image는 : " + jo.getString("mimage"));

                        boardFragmentAdapter.addItem(board);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, NetworkSetting.baseUrl+"board/list?pageNo=" + pageNo);
    }

    public void getMemberImage(String mid, final ImageView boardImage) {

        AsyncTask<String, Void, Bitmap> asyncTask = new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                Bitmap bitmap=null;
                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.connect();

                    Log.i("mylog", ""+ conn.getResponseCode());
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream is = conn.getInputStream();
                        bitmap = BitmapFactory.decodeStream(is);
                        is.close();
                    }

                    conn.disconnect();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if(bitmap != null) {
                    boardImage.setImageBitmap(bitmap);
                } else {
                    boardImage.setImageResource(R.drawable.human);
                }


            }
        };
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, NetworkSetting.baseUrl+"board/memberId?mid=" + mid);

    }



}
