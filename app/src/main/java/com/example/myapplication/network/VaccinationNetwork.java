package com.example.myapplication.network;

import android.os.AsyncTask;

import com.example.myapplication.diary.VaccinationAdapter;
import com.example.myapplication.diary.dto.Vaccination;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-07-22.
 */
public class VaccinationNetwork {
    public static String baseUrl = "http://192.168.0.29:8080/Petopia/";
    //public static String baseUrl = "http://106.253.56.123:8080/Petopia/";
    //public static String baseUrl = "http://192.168.0.24:8080/SpringMVCProject2/";
    private static String strjson;
    public static void getVaccinationList(String dname, final VaccinationAdapter vaccinationAdapter) {
        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String strjson="";
                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();

                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream is = conn.getInputStream();
                        Reader reader = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(reader);

                        String data = null;
                        while((data = br.readLine()) != null) {
                            strjson += data;
                        }

                        is.close();
                        br.close();
                    }

                    conn.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return strjson;
            }

            @Override
            protected void onPostExecute(String s) {
                List<Vaccination> list = new ArrayList<>();

                try {
                    JSONArray jsonArray = new JSONArray(s);
                    for(int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Vaccination vaccination = new Vaccination();
                        vaccination.setVname(jsonObject.getString("vname"));
                        vaccination.setVdate(jsonObject.getString("vdate"));
                        vaccination.setVndate(jsonObject.getString("vndate"));
                        vaccinationAdapter.addItem(vaccination);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        asyncTask.execute(baseUrl + "vaccination/list?dname=" + dname);

    }

    public static void sendVaccination(final Vaccination vaccination) {
        AsyncTask<String, Void, Void> asyncTask = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    }
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        asyncTask.execute(baseUrl + "vaccination?vname=" + vaccination.getVname() + "&vdate=" + vaccination.getVdate() +
                "&vndate=" + vaccination.getVndate() + "&mid=" + vaccination.getMid() + "&dname=" + vaccination.getDname());
    }
}
