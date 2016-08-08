package com.example.myapplication.network;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.clinic.dto.ClinicFacility;
import com.example.myapplication.clinic.dto.ClinicInformation;
import com.example.myapplication.clinic.dto.ClinicRegister;
import com.example.myapplication.clinic.dto.RegisterLocation;
import com.example.myapplication.clinic.fragment.ClinicDetailInformationFragment;
import com.example.myapplication.clinic.fragment.RegisterClinicFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-07-14.
 */
public class ClinicNetwork {

    private String url = "http://192.168.0.38:8080/Petopia/";
    private String body="";  //여기 처음에 String body; 로 안해야 한다. 주의해라!!
    private Bitmap bm;
    private GoogleMap googleMap;
    private Context context;

    private Bitmap bm1;
    private Bitmap bm2;
    private Bitmap bm3;
    private Bitmap bm4;

    public void setGoogleMap (GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public void setContext (Context context) {
        this.context = context;
    }

    public void getClinicInformation(final View view) {
        new AsyncTask<String, Integer, ClinicInformation>() {
            ClinicInformation clinicInformation = new ClinicInformation();

            @Override
            protected ClinicInformation doInBackground(String... params) {
                String result = "fail";
                try {
                    // 데이터 구분 문자
                    String boundary = "----" + System.currentTimeMillis();

                    // 데이터 경계선
                    String delimiter = "\r\n--" + boundary + "\r\n";    //규약

                    // 커넥션 생성 및 설정
                    URL url = new URL(params[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();


                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        body="";
                        InputStream is = conn.getInputStream();
                        Reader reader = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(reader);

                        while(true) {
                            String line = br.readLine();
                            if(line==null) break;
                            body += line;
                        }

                        br.close();
                        reader.close();
                        is.close();


                        JSONObject jsonObject = new JSONObject(body);


                        clinicInformation.setCid(jsonObject.getString("cid"));
                        clinicInformation.setCname(jsonObject.getString("cname"));
                        clinicInformation.setClocation(jsonObject.getString("clocation"));
                        clinicInformation.setCphone(jsonObject.getString("cphone"));
                        clinicInformation.setCopentime(jsonObject.getString("csopentime"));
                        clinicInformation.setCclosetime(jsonObject.getString("cclosetime"));
                        clinicInformation.setCintroduction(jsonObject.getString("cintroduction"));


                    }


                    //연결 끊기
                    conn.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return clinicInformation;
            }

            @Override
            protected void onPostExecute(ClinicInformation clinicInformation) {
                TextView textView = (TextView)view.findViewById(R.id.editTextClinicName);
                textView.setText(clinicInformation.getCname());
            }
        }.execute(url + "clinicinformation");
    }


    public void getClinicInformationTab(final View view, GoogleMap googleMap) {
        new AsyncTask<String, Integer, ClinicInformation>() {
            ClinicInformation clinicInformation = new ClinicInformation();

            @Override
            protected ClinicInformation doInBackground(String... params) {
                String result = "fail";
                try {
                    // 데이터 구분 문자
                    String boundary = "----" + System.currentTimeMillis();

                    // 데이터 경계선
                    String delimiter = "\r\n--" + boundary + "\r\n";    //규약

                    // 커넥션 생성 및 설정
                    URL url = new URL(params[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();


                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        body="";
                        InputStream is = conn.getInputStream();
                        Reader reader = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(reader);

                        while(true) {
                            String line = br.readLine();
                            if(line==null) break;
                            body += line;
                        }

                        br.close();
                        reader.close();
                        is.close();


                        JSONObject jsonObject = new JSONObject(body);


                        clinicInformation.setCid(jsonObject.getString("cid"));
                        clinicInformation.setCname(jsonObject.getString("cname"));
                        clinicInformation.setClocation(jsonObject.getString("clocation"));
                        clinicInformation.setCphone(jsonObject.getString("cphone"));
                        clinicInformation.setCopentime(jsonObject.getString("copentime"));
                        clinicInformation.setCclosetime(jsonObject.getString("cclosetime"));
                        clinicInformation.setCintroduction(jsonObject.getString("cintroduction"));
                        clinicInformation.setClongitude(jsonObject.getString("clongitude"));
                        clinicInformation.setClatitude(jsonObject.getString("clatitude"));


                    }


                    //연결 끊기
                    conn.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return clinicInformation;
            }

            @Override
            protected void onPostExecute(ClinicInformation clinicInformation) {
                TextView textViewName = (TextView) view.findViewById(R.id.textViewName);
                TextView textViewLocation = (TextView) view.findViewById(R.id.textViewLocation);
                TextView textViewDLocation = (TextView) view.findViewById(R.id.textViewPhone);
                TextView textViewOpenTime = (TextView) view.findViewById(R.id.textViewOpenTime);
                TextView textViewCloseTime = (TextView) view.findViewById(R.id.textViewCloseTime);
                TextView textViewIntroduction = (TextView) view.findViewById(R.id.textViewIntroduction);

                textViewName.setText(clinicInformation.getCname());
                textViewLocation.setText(clinicInformation.getClocation());
                textViewDLocation.setText(clinicInformation.getCphone());
                textViewOpenTime.setText(clinicInformation.getCopentime());
                textViewCloseTime.setText(clinicInformation.getCclosetime());
                textViewIntroduction.setText(clinicInformation.getCintroduction());


            }
        }.execute(url + "clinicinformation?cid=" + MainActivity.loginId);
    }


    public void getClinicFacility(String clinicId, ImageView imageView1,ImageView imageView2, ImageView imageView3, ImageView imageView4 ) {


        Log.i("mylog", "getClicFacility 실행");
        new AsyncTask<String, Void, Void>() {
            ClinicFacility clinicFacility = new ClinicFacility();

            @Override
            protected Void doInBackground(String... params) {
                Log.i("mylog", "getClicFacility do in background 실행");
                Log.i("mylog", "요청주소는 " + params[0]);
                String result = "fail";
                try {

                    // 커넥션 생성 및 설정
                    URL url = new URL(params[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();

                    Log.i("my",""+conn.getResponseCode());

                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        body="";
                        InputStream is = conn.getInputStream();
                        Reader reader = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(reader);

                        while(true) {
                            String line = br.readLine();
                            if(line==null) break;
                            body += line;
                        }

                        br.close();
                        reader.close();
                        is.close();

                    }

                    //연결 끊기
                    conn.disconnect();


                    JSONObject jsonObject = new JSONObject(body);
                    if(jsonObject.getString("cimage1") != null) {


                        URL url2 = new URL("http://106.253.56.123:8080/Petopia/clinicFacilityImage?cimage1=" + jsonObject.getString("cimage1"));
                        Log.i("mylog", url2.toString());
                        URLConnection conn2 = url2.openConnection();
                        conn2.connect();
                        BufferedInputStream bis = new BufferedInputStream(conn2.getInputStream());
                        bm1 = BitmapFactory.decodeStream(bis);
                        bis.close();

                        conn.disconnect();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute("http://192.168.0.38:8080/Petopia/" + "clinicfacility?cid=" + ClinicDetailInformationFragment.clinicId);
    }




    public void getLocation(String address) {
        Log.i("mylog", "getLocation메소드 실행");



        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String json = "";
                try {


                    URL url = new URL(params[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.connect();
                    Log.i("mylog", "1");
                    Log.i("mylog", "responseCode" + conn.getResponseCode());
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream is = conn.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        String data = null;
                        Log.i("mylog", "2");
                        while((data = br.readLine()) != null) {
                            Log.i("mylog", "3");
                            json += data;
                        }

                        br.close(); is.close();
                    }
                    Log.i("mylog", "4");
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return json;
            }

            @Override
            protected void onPostExecute(String json) {
                Log.i("mylog", "5");
                Log.i("mylog", "result: " + json);
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    JSONObject jsonObject2 = jsonObject.getJSONObject("channel");
                    Log.i("mylog", "json 1차 파싱 : " + jsonObject2.toString());
                    JSONArray jsonArray = jsonObject2.getJSONArray("item");

                    List<RegisterLocation> list = new ArrayList<>();
                    for (int i = 0 ; i<jsonArray.length() ; i++) {

                        Log.i("mylog", "json 2차 파싱 : " + jsonArray.toString());
                        JSONObject jsonObject3 = jsonArray.getJSONObject(i);

                        RegisterLocation registerLocation = new RegisterLocation();
                        registerLocation.setLatitude(jsonObject3.getString("point_y"));
                        registerLocation.setLongitude(jsonObject3.getString("point_x"));
                        registerLocation.setAddress(jsonObject3.getString("localName_1")+jsonObject3.getString("localName_2") +
                                jsonObject3.getString("localName_3") + jsonObject3.getString("buildingAddress"));

                        list.add(registerLocation);
                    }

                    MainActivity.list = list;

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RegisterLocation registerLocation = MainActivity.list.get(0);
                RegisterClinicFragment.latitude = Double.parseDouble(registerLocation.getLatitude());
                RegisterClinicFragment.longitude = Double.parseDouble(registerLocation.getLongitude());

                showCurrentLocation(Double.parseDouble(registerLocation.getLatitude()),Double.parseDouble(registerLocation.getLongitude()));

            }
        };




        try {
            address = URLEncoder.encode(address, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        asyncTask.execute("http://apis.daum.net/local/geo/addr2coord?apikey=ed39f1a2d5d0ef6900f484030523afe2&q=" + address  + "&output=json");


    }

    private void showCurrentLocation(Double latitude, Double longitude) {
        LatLng curPoint = new LatLng(latitude, longitude);
        //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }



    }




    public static void clinicRegister(final ClinicRegister clinicRegister, final Bitmap bitmap1, final Bitmap bitmap2, final Bitmap bitmap3, final Bitmap bitmap4 ) {
        new AsyncTask<Void, Integer, String>() {
            @Override
            protected String doInBackground(Void... params) {
                Log.i("mylog", "clinicRegister메소드 실행");

                String result = "fail";
                try {
                    // 데이터 구분 문자
                    String boundary = "----" + System.currentTimeMillis();

                    // 데이터 경계선
                    String delimiter = "\r\n--" + boundary + "\r\n";    //규약

                    // 커넥션 생성 및 설정
                    URL url = new URL("http://192.168.0.38:8080/Petopia/clinicregister");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                    conn.connect();

                    /*PrintWriter pw = new PrintWriter(conn.getOutputStream());
                    pw.println("json="+strJson);
                    pw.close();*/

                    //연결하기
                    //int resultCode = conn.getResponseCode();

                   /* String strObject = String.valueOf(clinicRegister);
                    Log.i("mylog", strObject);*/



                    //출력 스트림 얻기
                    OutputStream out = conn.getOutputStream();

                    //문자열 데이터 전송
                    StringBuffer postDataBuilder = new StringBuffer();
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("cid", clinicRegister.getCid()));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("cname", clinicRegister.getCname()));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("clocation", clinicRegister.getClocation()));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("cintroduction", clinicRegister.getCintroduction()));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("cphone", clinicRegister.getCphone()));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("cbeauty", String.valueOf(clinicRegister.isCbeauty())));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("cweekend", String.valueOf(clinicRegister.getCweekend())));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("copentime", clinicRegister.getCopentime()));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("cclosetime", clinicRegister.getCclosetime()));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("clatitude", String.valueOf(clinicRegister.getClatitude())));
                    postDataBuilder.append(delimiter);
                    Log.i("mylog", "전송직전 위도 : " + String.valueOf(clinicRegister.getClatitude()));
                    postDataBuilder.append(setValue("clongitude", String.valueOf(clinicRegister.getClongitude())));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("clunchstart", clinicRegister.getClunchstart()));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("clunchend", clinicRegister.getClunchend()));
                    out.write(postDataBuilder.toString().getBytes()); //첫번째 전송


                   if (bitmap1 != null) {
                        postDataBuilder = new StringBuffer();
                        postDataBuilder.append(delimiter);
                        postDataBuilder.append(setFile("clinicImage1", clinicRegister.getCimage1()));
                        out.write(postDataBuilder.toString().getBytes()); //첫번째 전송

                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bitmap1.compress(Bitmap.CompressFormat.PNG, 0, bos);
                        byte[] bitmapdata = bos.toByteArray();

                        ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
                        byte[] byteArray = new byte[1024];
                        int readByteNum = -1;
                        while ((readByteNum = bs.read(byteArray)) != -1) {
                            out.write(byteArray, 0, readByteNum);
                        }

                        bos.close();
                        bs.close();

                    }

                    if (bitmap2 != null) {
                        postDataBuilder = new StringBuffer();
                        postDataBuilder.append(delimiter);
                        postDataBuilder.append(setFile("clinicImage2", clinicRegister.getCimage2()));
                        out.write(postDataBuilder.toString().getBytes()); //첫번째 전송

                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bitmap2.compress(Bitmap.CompressFormat.PNG, 0, bos);
                        byte[] bitmapdata = bos.toByteArray();

                        ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
                        byte[] byteArray = new byte[1024];
                        int readByteNum = -1;
                        while ((readByteNum = bs.read(byteArray)) != -1) {
                            out.write(byteArray, 0, readByteNum);
                        }

                        bos.close();
                        bs.close();

                    }

                    if (bitmap3 != null) {
                        postDataBuilder = new StringBuffer();
                        postDataBuilder.append(delimiter);
                        postDataBuilder.append(setFile("clinicImage3", clinicRegister.getCimage3()));
                        out.write(postDataBuilder.toString().getBytes()); //첫번째 전송

                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bitmap3.compress(Bitmap.CompressFormat.PNG, 0, bos);
                        byte[] bitmapdata = bos.toByteArray();

                        ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
                        byte[] byteArray = new byte[1024];
                        int readByteNum = -1;
                        while ((readByteNum = bs.read(byteArray)) != -1) {
                            out.write(byteArray, 0, readByteNum);
                        }

                        bos.close();
                        bs.close();

                    }

                    if (bitmap4 != null) {
                        postDataBuilder = new StringBuffer();
                        postDataBuilder.append(delimiter);
                        postDataBuilder.append(setFile("clinicImage4", clinicRegister.getCimage4()));
                        out.write(postDataBuilder.toString().getBytes()); //첫번째 전송

                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bitmap4.compress(Bitmap.CompressFormat.PNG, 0, bos);
                        byte[] bitmapdata = bos.toByteArray();

                        ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
                        byte[] byteArray = new byte[1024];
                        int readByteNum = -1;
                        while ((readByteNum = bs.read(byteArray)) != -1) {
                            out.write(byteArray, 0, readByteNum);
                        }

                        bos.close();
                        bs.close();

                    }



                    //종료 구분자 넣기  //세번째 전송
                    out.write(("\r\n--" + boundary + "--\r\n").getBytes());  //규약

                    //출력스트림 닫기
                    out.flush();
                    out.close();



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
                    Log.i("mylog","실패");
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