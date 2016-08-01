package com.example.myapplication.reservation.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.clinic.fragment.ClinicDetailInformationFragment;
import com.example.myapplication.reservation.dto.Reserve;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReserveResultFragment extends Fragment {

    Button btnReserve;
    Button btnEdit;

    TextView txtRcname;
    TextView txtRpname;
    TextView txtRtype;
    TextView txtRdate;
    TextView txtRtime;

    public ReserveResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reserve_result, container, false);


        txtRcname = (TextView) view.findViewById(R.id.txtRcname);
        txtRpname = (TextView) view.findViewById(R.id.txtRpname);
        txtRtype = (TextView) view.findViewById(R.id.txtRtype);
        txtRdate = (TextView) view.findViewById(R.id.txtRdate);
        txtRtime = (TextView) view.findViewById(R.id.txtRtime);
        btnReserve = (Button) view.findViewById(R.id.btnReserve);

        txtRcname.setText(ReserveSearchFragment.rclinicname);
        txtRpname.setText(ReserveSearchFragment.rpname);
        txtRtype.setText(ReserveSearchFragment.rtype);
        txtRdate.setText(ReserveSearchFragment.rdate);
        txtRtime.setText(ReserveSearchFragment.rtime);

        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //예약하기 스프링 서버에게 Reserve 데이터를 Http통신을 이용하여 보내준다.

                showDialog();


            }
        });

        btnEdit = (Button) view.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new ReserveEditFragment())
                        .commit();
            }
        });
        return view;
    }

    private void showDialog() {

        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(getActivity());
        alert_confirm.setMessage("입력한 정보로 예약하시겠습니까?").setCancelable(false)

                .setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 확인버튼 클릭

                        //예약 테이블에 예약 데이터를 삽입한다.
                        reserve();

                        Toast.makeText(getContext(), "예약이 신청되었습니다.",Toast.LENGTH_LONG).show();

                        // 병원 정보 화면으로 돌아간다.
                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragmentContainer, new ClinicDetailInformationFragment())
                                .commit();

                        return;
                    }
                }).setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 취소버튼 클릭
                        return;
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }

    private void showCancleDialog() {

        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(getActivity());
        alert_confirm.setMessage("예약을 취소하시겠습니까?").setCancelable(false)

                .setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 확인버튼 클릭

                                return;
                            }
                        }).setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 취소버튼 클릭
                        return;
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }

    private void reserve() {

        Log.i("myLog", "사용자가 입력한 정보로 서버에 예약을 요청합니다.");

        String rdate = ReserveSearchFragment.rdate.replace("(오늘)","");

        rdate = rdate.replace('년','-');
        rdate = rdate.replace('월','-');
        rdate = rdate.replace("일","");


        Reserve reserve = new Reserve();
        reserve.setRtype(ReserveSearchFragment.rtype);
        reserve.setRdate(rdate);
        reserve.setRtime(ReserveSearchFragment.rtime);
        reserve.setRclinicid(ReserveSearchFragment.rclinicid);
        reserve.setRuserid(ReserveSearchFragment.ruserid);
        reserve.setRpname(ReserveSearchFragment.rpname);

        sendReserve(reserve);

    }

    public void sendReserve(final Reserve reserve) {


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                String strJson = "";

                Log.i("myLog", "어싱크태스크 실행되는 중");

                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("rtype", reserve.getRtype());
                    jsonObject.put("rdate", reserve.getRdate());
                    jsonObject.put("rtime", reserve.getRtime());
                    jsonObject.put("rclinicid", reserve.getRclinicid());
                    jsonObject.put("ruserid", reserve.getRuserid());
                    jsonObject.put("rpname", reserve.getRpname());

                    strJson = jsonObject.toString();

                    URL url = new URL("http://192.168.0.21:8080/Petopia/reserve/register?json_reserve="+strJson);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.connect();

                    //출력스트림
                    OutputStream os = conn.getOutputStream();
                    os.write(strJson.getBytes());

                    os.flush();
                    os.close();

                    //응답코드 확인
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        Log.i("myLog", "응답ok");
                    }

                    conn.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();
    }

}
