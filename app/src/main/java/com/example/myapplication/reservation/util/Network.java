package com.example.myapplication.reservation.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.example.myapplication.MainActivity;
import com.example.myapplication.calendar.MonthItemView;
import com.example.myapplication.network.NetworkSetting;
import com.example.myapplication.reservation.dto.Reserve;
import com.example.myapplication.reservation.dto.ReserveListItem;
import com.example.myapplication.reservation.fragment.MyReserveViewAdapter;
import com.example.myapplication.reservation.fragment.ReserveListViewAdapter;
import com.example.myapplication.reservation.fragment.TimeViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

//네트워크 관련 코드는 한 클래스에서 담당하는 것이 나중에 유지보수 할 떄를 위해서도 좋다.

public class Network {

    private static final String site = NetworkSetting.baseUrl4;

    public static void setReserveList(final Context context, final TimeViewAdapter timeViewAdapter, List<String> local_timeList, Map<String, String> map) {

        final List<String> nonReservedTimeList = local_timeList;
        final List<String> dbReservedTimeList = new ArrayList<>();

        //첫번째 매개변수는 doInBackground()로, 두번째 매개변수는 onProgressUpdate()로, 세번째 매개변수는 반환값을 의미한다.
        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {

            ProgressDialog asyncDialog = new ProgressDialog(context);

            @Override
            protected void onPreExecute() {

                asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                asyncDialog.setMessage("예약 가능한 시간을 불러오고 있습니다..");

                // show dialog
                asyncDialog.show();
                super.onPreExecute();
            }


            @Override
            protected String doInBackground(String... params) {
                Log.i("myLog", "asyncTask 시작 중");

                String json = "";

                Log.i("myLog", "Network 클래스의 doInBackground()를 실행합니다");

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


                    //* 응답 *//*

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

                //Log.i("myLog", json);

                return json;
                //doInBackground가 종료하면 onPostExecute(String json)을 호출한다.
            }


            //doInBackground메소드가 다 수행되면 onPostExecute 메소드가 자동으로 호출된다. 매개값은 doInBackground 메소드의 리턴값이다.
            @Override
            protected void onPostExecute(String json) {

                long curTime = System.currentTimeMillis();
                SimpleDateFormat dayTime = new SimpleDateFormat("hhmm");
                String tempTime = dayTime.format(new Date(curTime));

                Log.i("test", "현재 시간은 : " + tempTime);

                int currentTime = Integer.parseInt(tempTime);

                Log.i("myLog", "asyncTask 시작 종료");

                /* Spring 서버에서 안드로이드로 보내는 데이터 형태
                [
                   "12:30",
                   "13:00",
                   "13:30"
                ]
                */


                //JSON을 파싱하는 코드
                try {
                    Log.i("myLog", "받아 온 시간 데이터");
                    JSONArray root = new JSONArray(json);
                    for (int i = 0; i < root.length(); i++) {

                        String time = root.getString(i);
                        Log.i("myLog", time);
                        dbReservedTimeList.add(time);
                    }


                    for (int i = 0; i < nonReservedTimeList.size(); i++) {

                        int localTime = Integer.parseInt(nonReservedTimeList.get(i).replace(":", ""));

                        //현재 시간보다 이전 시간은 지운다.
                        if (currentTime > localTime) {
                            Log.i("myLog", nonReservedTimeList.get(i) + " 제거");
                            nonReservedTimeList.remove(i);
                            i--;
                            continue;
                        }

                        String registerTime = nonReservedTimeList.get(i);
                        //Log.i("myList", "registerTime"+registerTime);

                        for (int j = 0; j < dbReservedTimeList.size(); j++) {
                            String dbClinicTime = dbReservedTimeList.get(j);
                            //Log.i("myList", "dbClinicTime"+dbClinicTime);

                            if (registerTime.equals(dbClinicTime)) {

                                //Log.i("mylog", registerTime+"과 "+dbClinicTime+"는 같으므로 제거");

                                //Log.i("mylog",i+"번째에 있는"+clinicTimeList.get(i)+"를 제거합니다.");
                                nonReservedTimeList.remove(i);
                                i--;

                                break;
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                timeViewAdapter.setList(nonReservedTimeList);
                timeViewAdapter.notifyDataSetChanged();

                asyncDialog.dismiss();
            }
        };

        Log.i("myLog", "asyncTask 시작 전");
        String clinicid = map.get("clinicid");
        String date = map.get("date");

        Log.i("myLog", clinicid + "," + date);

        //asyncTask를 실행한다. doInBackground 메소드를 실행한다.
        Log.i("myLog", site + "/reserve/getTimeList?clinicid=" + clinicid + "&date=" + date);

        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, site + "reserve/getTimeList?clinicid=" + clinicid + "&date=" + date); //doInBackground()의 매개값으로 들어간다.
    }


    public static List<String> getReserveTimeList(List<String> local_timeList, String clinicid, String date) {

        final List<String> nonReservedTimeList = local_timeList;
        final List<String> dbReservedTimeList = new ArrayList<>();

        //첫번째 매개변수는 doInBackground()로, 두번째 매개변수는 onProgressUpdate()로, 세번째 매개변수는 반환값을 의미한다.

        /* AsyncTask<Integer, Void, Bitmap>
          Integer : execute, doInBackground 의 파라미터 타입
          Void : onProgressUpdate 의 파라미터 타입
          Bitmap : doInBackground 의 리턴값, onPostExecute 의 파라미터로 설정됩니다.*/

        AsyncTask<String, Void, List<String>> asyncTask = new AsyncTask<String, Void, List<String>>() {

            //doInBackground의 리턴값 String은 onPostExecute의 매개변수로 들어간다.
            @Override
            protected List<String> doInBackground(String... params) {

                String json = "";

                Log.i("myLog", "getReserveList 메소드가 doInBackground()를 실행합니다");

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

                List<String> list = calculateReserveTime(json, dbReservedTimeList, nonReservedTimeList);

                return list;
            }
        };
        //asyncTask의 끝


        Log.i("myLog", site + "/reserve/getTimeList?clinicid=" + clinicid + "&date=" + Util.getSimpleDate(date));

        try {
            List<String> finalList = asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, site + "/reserve/getTimeList?clinicid=" + clinicid + "&date=" + Util.getSimpleDate(date)).get(); //doInBackground()의 매개값으로 들어간다.
            return finalList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void getMyReserveList(final String rpname, final String ruserid, final MyReserveViewAdapter myReserveViewAdapter) {

        final AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                Log.i("mytest", "getMyReserveList 호출- doInBackground 시작");

                String json = "";
                try {
                    URL url = new URL(params[0]);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.setDoInput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Content-Type", "application/json");

                    conn.connect();

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

                        InputStream is = conn.getInputStream();
                        Reader reader = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(reader);

                        while (true) {
                            String line = br.readLine();
                            if (line == null) break;
                            json += line;
                        }

                        br.close();
                        is.close();
                        reader.close();

                    } else {
                        Log.i("myLog", "응답 OK를 받지 못했습니다");
                    }


                    conn.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return json;
            }

            @Override
            protected void onPostExecute(String json) {
                Log.i("mytest", "getMyReserveList 호출- onPostExecute 시작");
                try {

                    JSONArray root = new JSONArray(json);
                    for (int i = 0; i < root.length(); i++) {

                        JSONObject jsonObject = root.getJSONObject(i);
                        int rno = jsonObject.getInt("rno");
                        String rtype = jsonObject.getString("rtype");
                        String rdate = jsonObject.getString("rdate");
                        String rtime = jsonObject.getString("rtime");
                        String rclinicid = jsonObject.getString("rclinicid");
                        String rcname = jsonObject.getString("rcname");
                        String raddress = jsonObject.getString("raddress");
                        String rpname = jsonObject.getString("rpname");

                        Log.i("myLog", "받아온 나의 예약 리스트 목록");
                        Log.i("myLog", "rno:" + rno);
                        Log.i("myLog", "rtype:" + rtype);
                        Log.i("myLog", "rdate:" + rdate);
                        Log.i("myLog", "rtime:" + rtime);
                        Log.i("myLog", "rclinicid:" + rclinicid);
                        Log.i("myLog", "rcname:" + rcname);
                        Log.i("myLog", "raddress" + raddress);
                        Log.i("myLog", "rpname:" + rpname);


                        Reserve reserve = new Reserve();
                        reserve.setRno(rno);
                        reserve.setRtype(rtype);
                        reserve.setRdate(rdate);
                        reserve.setRtime(rtime);
                        reserve.setRclinicid(rclinicid);
                        reserve.setRclinicName(rcname);
                        reserve.setRaddress(raddress);
                        reserve.setRpname(rpname);

                        myReserveViewAdapter.addItem(reserve);

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Log.i("mytest", "getMyReserveList 호출- asyncTask 시작");

        Log.i("mytest", site + "/reserve/getMyReserveList?rpname=" + rpname + "&ruserid=" + ruserid);
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, site + "/reserve/getMyReserveList?rpname=" + rpname + "&ruserid=" + ruserid);
    }


    private static List<String> calculateReserveTime(String json, List<String> dbReservedTimeList, List<String> nonReservedTimeList) {

        try {

            JSONArray root = new JSONArray(json);
            for (int i = 0; i < root.length(); i++) {

                String time = root.getString(i);
                Log.i("myLog", time);
                dbReservedTimeList.add(time);
            }

            for (int i = 0; i < nonReservedTimeList.size(); i++) {

                String registerTime = nonReservedTimeList.get(i);
                //Log.i("myList", "registerTime"+registerTime);

                for (int j = 0; j < dbReservedTimeList.size(); j++) {
                    String dbClinicTime = dbReservedTimeList.get(j);
                    //Log.i("myList", "dbClinicTime"+dbClinicTime);

                    if (registerTime.equals(dbClinicTime)) {

                        //Log.i("mylog", registerTime+"과 "+dbClinicTime+"는 같으므로 제거");

                        //Log.i("mylog",i+"번째에 있는"+clinicTimeList.get(i)+"를 제거합니다.");
                        nonReservedTimeList.remove(i);
                        i--;

                        break;
                    }
                }
            }

            return nonReservedTimeList;

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    public static void isReserved(final MonthItemView itemView, String clinicid, String date) {
        ProgressDialog dialog;

        Log.i("test", "isReserved메소드를 호출했습니다.");

        //시작할 때 로딩바 올리고 끝나면 종료하기

        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {

            //doInBackground의 리턴값 String은 onPostExecute의 매개변수로 들어간다.
            @Override
            protected String doInBackground(String... params) {


                Log.i("test", "doInBackground() 시작");

                String json = "";

                Log.i("test", "getReserveNumber 메소드가 doInBackground()를 실행합니다");

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

                        Log.i("test", "응답 OK를 받음: 받은 json: " + json);

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

                Log.i("test", "받은 json 데이터는 :" + json);

                int num = Integer.parseInt(json);

                Log.i("test", "예약 조회결과 :" + num);
                if (num <= 0) {
                    Log.i("test", "예약 0건 배경 하얀색");
                    itemView.setBackgroundColor(Color.parseColor("#F6FFCC"));
                    itemView.setTextSize(13);
                } else {
                    Log.i("test", "예약 1건 이상 배경 노란색");
                    itemView.setBackgroundColor(Color.parseColor("#FF5A5A"));

                    itemView.append("\n\n\n예약 내역\n "+num+"건");
                    itemView.setTextSize(10);
                }
            }
        };

        Log.i("test", site + "/reserve/getReserveNo?clinicid=" + clinicid + "&date=" + Util.getSimpleDate(date));
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, site + "/reserve/getReserveNo?clinicid=" + clinicid + "&date=" + Util.getSimpleDate(date)); //doInBackground()의 매개값으로 들어간다.
    }


    public static void getSubscriberList(final ReserveListViewAdapter viewAdapter, String clinicId, String date) {

        //첫번째 매개변수는 doInBackground()로, 두번째 매개변수는 onProgressUpdate()로, 세번째 매개변수는 반환값을 의미한다.
        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {

            //doInBackground의 리턴값 String은 onPostExecute의 매개변수로 들어간다.
            @Override
            protected String doInBackground(String... params) {

                Log.i("myLog", "getSubscriberList메소드의 asyncTask doInBackground 수행");

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
                        Log.i("myLog", "응답 OK를 받았음");
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

                Log.i("myLog", "받은 json:" + json);

                //JSON을 파싱하는 코드
                try {

                    JSONArray root = new JSONArray(json);
                    for (int i = 0; i < root.length(); i++) {

                        JSONObject jsonObject = root.getJSONObject(i);
                        String rname = jsonObject.getString("rname");
                        String rpname = jsonObject.getString("rpname");
                        String rphone = jsonObject.getString("rphone");
                        String rtime = jsonObject.getString("rtime");

                        ReserveListItem item = new ReserveListItem();
                        item.setrName(rname);
                        item.setrPetName(rpname);
                        item.setrPhone(rphone);
                        item.setrTime(rtime);

                        viewAdapter.addItem(item);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        Log.i("myLog", site + "/reserve/getSubscriberList?clinicId=" + clinicId + "&date=" + date);

        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, site + "/reserve/getSubscriberList?clinicId=" + clinicId + "&date=" + date);

        Log.i("myLog", "asyncTask 수행 완료");
    }
}