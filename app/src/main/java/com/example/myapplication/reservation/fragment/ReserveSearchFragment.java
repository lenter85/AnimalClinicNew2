package com.example.myapplication.reservation.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.calendar.CalendarFragment;
import com.example.myapplication.reservation.util.Network;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReserveSearchFragment extends Fragment {

    public static String calendarDay;

    public static String rtype;
    public static String rdate;
    public static String rtime;
    public static String rpname;
    public static String ruserid;
    public static String rclinicname;
    public static String rclinicid;

    Button btnSearch;
    Button btnClinic;
    Button btnBeauty;
    TextView txtRdate;
    ImageButton imgCalendar;
    TextView txtCname;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        getActivity().setTitle("예약하기");
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_reserve_search, container, false);
        txtCname = (TextView) view.findViewById(R.id.txtCname);

        //화면에 기본값인 오늘 날짜를 등록해준다.
        setReserveInfo(view);


        //조회하기 버튼 클릭 시
        btnSearch = (Button) view.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new ReserveSearchResultFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        //진료 예약 버튼 클릭 시
        btnClinic = (Button) view.findViewById(R.id.btnClinic);
        btnClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rtype = "진료";
                btnClinic.setBackgroundResource(R.drawable.green_round_angle);
                btnClinic.setTextColor(Color.parseColor("#FFFFFF"));
                btnBeauty.setBackgroundResource(R.drawable.round_angle);
                btnBeauty.setTextColor(Color.parseColor("#5D5D5D"));
            }
        });

        //미용 예약 버튼 클릭 시
        btnBeauty = (Button)view.findViewById(R.id.btnBeauty);
        btnBeauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rtype="미용";
                btnClinic.setBackgroundResource(R.drawable.round_angle);
                btnClinic.setTextColor(Color.parseColor("#5D5D5D"));
                btnBeauty.setBackgroundResource(R.drawable.green_round_angle);
                btnBeauty.setTextColor(Color.parseColor("#FFFFFF"));

            }
        });

        //달력 버튼 클릭 시
        imgCalendar = (ImageButton) view.findViewById(R.id.imgCalendar);
        imgCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //달력을 누르기 전에 지금 페이지에 대한 정보를 넘긴다.
                CalendarFragment.previousPage = "SEARCH";

                MainActivity.LoginId = "NOMAL";

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new CalendarFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(getContext(), "onResume 호출됨", Toast.LENGTH_LONG).show();
    }

    public void setReserveInfo(View view) {


        //애완 동물명 세팅
        final List<String> arraylist = Network.getMyPetList();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist);
        //스피너 속성
        Spinner sp = (Spinner) view.findViewById(R.id.spinner1);
        sp.setPrompt("애완동물을 선택해주세요"); // 스피너 제목
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("myLog", "스피너뷰 클릭됨");
                //예약할 애완동물 이름 변경한다.
                rpname = arraylist.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //오늘 날짜를 등록합니다.
        txtRdate = (TextView) view.findViewById(R.id.txtRdate);

        if(rdate!= null && !(rdate.equals(""))){

            if(rdate.equals(getToday())){
                rdate += "(오늘)";
            }

            txtRdate.setText(rdate);

        }else{

            rdate = getToday()+"(오늘)";
            txtRdate.setText(rdate);
        }

        //진료 유형 세팅
        rtype = "진료";

        //유저 아이디 세팅
        ruserid = MainActivity.loginId;

        //병원 아이디 세팅
        rclinicid = MainActivity.clinicId;

        rclinicname = MainActivity.cName;

        //애완동물명 세팅
        rpname = arraylist.get(0); //애완동물은 처음엔 스피너의 첫번째 리스트항목에 있는 애완동물명으로 세팅한다.

        calendarDay = null;


        txtCname.setText(rclinicname);

    }

    public String getToday(){

        Calendar cal = Calendar.getInstance();

        //현재 년도, 월, 일
        int year = cal.get ( cal.YEAR );
        int month = cal.get ( cal.MONTH ) + 1 ;
        int date = cal.get ( cal.DATE ) ;
        String str_today = year+"년"+month+"월"+date+"일";

        return str_today;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("mylog", "onDestroyView");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i("mylog", "onSaveInstanceState");
    }
}