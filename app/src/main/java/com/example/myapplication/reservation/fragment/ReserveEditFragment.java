package com.example.myapplication.reservation.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.calendar.CalendarFragment;
import com.example.myapplication.reservation.util.Network;
import com.example.myapplication.reservation.util.Util;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReserveEditFragment extends Fragment {

    Button btnOk;
    TextView txtRdate;
    TextView txtRtime;

    public ReserveEditFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reserve_edit, container, false);

        btnOk = (Button)view.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "수정이 완료되었습니다", Toast.LENGTH_LONG).show();

                getActivity().
                        getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragmentContainer, new ReserveResultFragment())
                        .commit();
            }
        });


        txtRdate = (TextView) view.findViewById(R.id.txtRdate);
        txtRtime = (TextView) view.findViewById(R.id.txtRtime);
        //년월일의 날짜를 표시함
        txtRdate.setText(ReserveSearchFragment.rdate);
        txtRtime.setText(ReserveSearchFragment.rtime);

        setListener(view);


        return view;
    }

    private void setListener(View view) {

        //날짜 클릭
        txtRdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CalendarFragment.previousPage = "EDIT";

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainer, new CalendarFragment())
                        .commit();
            }
        });

        //시간 클릭
        txtRtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog(){

        //예약 가능한 시간을 보여준다.

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        alertBuilder.setTitle("예약 가능 시간");

        // List Adapter 생성
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>( getActivity(),android.R.layout.select_dialog_singlechoice);

        //예약 가능한 시간 받아오기
        Log.i("myLog",  "network의 getReserveList메소드 호출하기 전 "+ ReserveSearchFragment.rclinicid+", "+ ReserveSearchFragment.rdate);

        List<String> list =  Network.getReserveList(Util.getClinicTimeList(), ReserveSearchFragment.rclinicid, ReserveSearchFragment.rdate);

        for(String time : list){
            adapter.add(time);
        }



        // 버튼 생성
        alertBuilder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                    }
                });

        // Adapter 셋팅
        alertBuilder.setAdapter(adapter,
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {

                // AlertDialog 안에 있는 AlertDialog
                    String time = adapter.getItem(id);
                    ReserveSearchFragment.rtime = time;
                    Log.i("myLog", "선택된 시간: "+ ReserveSearchFragment.rtime);
                    txtRtime.setText(time);

                }
            });

        alertBuilder.show();
    }

    private void showCancleDialog() {

        android.app.AlertDialog.Builder alert_confirm = new android.app.AlertDialog.Builder(getActivity());
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
        android.app.AlertDialog alert = alert_confirm.create();
        alert.show();
    }

}
