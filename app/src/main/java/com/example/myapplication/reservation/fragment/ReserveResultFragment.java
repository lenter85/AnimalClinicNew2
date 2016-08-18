package com.example.myapplication.reservation.fragment;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.clinic.fragment.ClinicDetailInformationFragment;
import com.example.myapplication.clinic.fragment.ClinicList_Fragment;
import com.example.myapplication.network.NetworkSetting;
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
        getActivity().setTitle("예약결과");
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
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

    private void showDialog() {

        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(getActivity());
        alert_confirm.setMessage("입력하신 정보로 예약하시겠습니까?").setCancelable(false)

                .setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 확인버튼 클릭

                        //예약 테이블에 예약 데이터를 삽입한다.
                        reserve();

                        Toast toast ;
                        toast = Toast.makeText(getContext(), "예약신청이 처리되었습니다." , Toast.LENGTH_LONG);
                        int offsetX = 0;
                        int offsetY = 0;
                        toast.setGravity(Gravity.CENTER, offsetX, offsetY);
                        toast.show();


                        // 병원 정보 화면으로 돌아간다.
                        /*android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        int back = fragmentManager.getBackStackEntryCount();

                        for(int i=0; i<back; i++){
                            fragmentManager.popBackStack();
                        }

                        Log.i("myBack", String.valueOf(back));

                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragmentContainer, new ClinicList_Fragment())
                                .commit();*/

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
        reserve.setRclinicid(MainActivity.clinicId);
        reserve.setRuserid(MainActivity.loginId);
        reserve.setRpname(ReserveSearchFragment.rpname);
        reserve.setRclinicName(MainActivity.cName);
        reserve.setRaddress(MainActivity.clinicLocation);

        sendReserve(reserve);

    }

    public void sendReserve(final Reserve reserve) {

        Log.i("mytest","sendReserve 호출");

        AsyncTask<String, Void, Void> asyncTask = new AsyncTask<String, Void, Void>() {

            @Override
            protected Void doInBackground(String... params) {

                String strJson = "";

                Log.i("myLog", "어싱크태스크 실행되는 중");

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
                    String data = "rtype="+reserve.getRtype()+"&rdate="+reserve.getRdate()
                            +"&rtime="+reserve.getRtime()+"&rclinicid="+reserve.getRclinicid()+"&ruserid="+reserve.getRuserid()
                            +"&rpname="+reserve.getRpname()+"&rcname="+reserve.getRclinicName()+"&raddress="+reserve.getRaddress();
                    Log.i("mytest", "보낼 파라미터값들:"+data);

                    byte[] bytedata = data.getBytes();
                    os.write(bytedata);
                    os.flush();
                    os.close();

                    //응답코드 확인
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        Log.i("mytest", "응답ok");
                    }

                    conn.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        };

        Log.i("mytest", NetworkSetting.baseUrl4 + "reserve/register");

        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, NetworkSetting.baseUrl4 + "reserve/register");
    }

}
