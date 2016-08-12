package com.example.myapplication.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.diary.DiaryAdapter;
import com.example.myapplication.diary.dto.Diary;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-07-25.
 */
public class DiaryNetwork {

    private static String baseUrl = NetworkSetting.baseUrl2;
    public static void getDiaryData(final DiaryAdapter diaryAdapter, String loginid) {
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
                        if(!(jsonObject.getString("dbirth").equals(""))) {
                            String str = jsonObject.getString("dbirth").substring(0, 10);
                            Log.i("substring", str);
                            diary.setDbirth(str);
                        } else {
                            diary.setDbirth(jsonObject.getString("dbirth"));
                        }

                        diary.setDgender(jsonObject.getString("dgender"));
                        diary.setDimage(jsonObject.getString("dimage"));
                        diaryAdapter.addItem(diary);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, baseUrl + "diary/list?loginid=" + MainActivity.loginId);
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
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, baseUrl + "diary/image?dimage=" + imageName);
    }

    public static void sendDiary(final Diary diary, final Bitmap bitmap) {
        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String result = "fail";
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
                    //DataOutputStream out = new DataOutputStream(new BufferedOutputStream(conn.getOutputStream()));
                    OutputStream out = conn.getOutputStream();
                    //File file = new File(filePath);
                    //diary.setDimage(file.getName());
                    //문자열 데이터 전송
                    StringBuffer postDataBuilder = new StringBuffer();
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("dname", diary.getDname()));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("dbirth", diary.getDbirth()));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("dgender", diary.getDgender()));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("dimage", diary.getDimage()));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("mid", MainActivity.loginId));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setFile("ddimage", diary.getDimage()));
                    //out.writeUTF(postDataBuilder.toString());
                    out.write(postDataBuilder.toString().getBytes());

                    //파일 데이터 전송
                    /*FileInputStream fis = new FileInputStream(filePath);
                    byte[] byteArray = new byte[1024];
                    int readByteNum = -1;
                    while((readByteNum = fis.read(byteArray)) != -1) {
                        out.write(byteArray, 0, readByteNum);
                    }
                    fis.close();*/

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    if(diary.getDimage().contains(".jpg")) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, bos);
                    } else if(diary.getDimage().contains(".png")) {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 30, bos);
                    }
                    byte[] bitmapdata = bos.toByteArray();
                    out.write(bitmapdata);
                    bos.close();

                    //종료 구분자 넣기
                    //out.writeUTF(delimiter);
                    //out.writeUTF("\r\n--" + boundary + "--\r\n");
                    out.write(("\r\n--" + boundary + "--\r\n").getBytes());

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
        };

        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, baseUrl + "diary/register");
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
