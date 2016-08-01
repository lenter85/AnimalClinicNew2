package com.example.myapplication.reservation.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.calendar.CalendarFragment;
import com.example.myapplication.reservation.util.Network;
import com.example.myapplication.reservation.util.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReserveSearchResultFragment extends Fragment {

    private ListView timeListView;

    TextView txtRdate;
    TextView txtDow;
    ImageButton btnCalendar;

    Button btnPrevious;
    Button btnNext;
    Button btnRefresh;

    String dow;

    public static List<String> clinicTimeList;
    public static List<String> beautyTimeList;
    List<String> dbClinicTimeList;

    TimeViewAdapter timeViewAdapter;

    private String fullDate;
    private int year;
    private int month;
    private int day;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reserve_search_result, container, false);

        txtDow = (TextView) view.findViewById(R.id.txtDow);
        txtRdate = (TextView) view.findViewById(R.id.txtRdate);
        btnCalendar = (ImageButton) view.findViewById(R.id.btnCalendar);

        btnPrevious = (Button) view.findViewById(R.id.btnPrevious);
        btnNext = (Button) view.findViewById(R.id.btnNext);
        btnRefresh = (Button) view.findViewById(R.id.btnRefresh);

        txtRdate.setText(ReserveSearchFragment.rdate);
        dow = getDateDay(ReserveSearchFragment.rdate);
        txtDow.setText(dow);
        timeListView = (ListView) view.findViewById(R.id.timeListView);

        timeViewAdapter = new TimeViewAdapter(getContext());
        timeListView.setAdapter(timeViewAdapter);

        setItemSetting();
        setListener();
        getDateInfo(ReserveSearchFragment.rdate);

        return view;
    }

    private void getDateInfo(String rdate) {

        fullDate = rdate;

        if(fullDate.contains("(오늘)")){
            fullDate = rdate.replace("(오늘)","");
        }

        int firstIndex = fullDate.indexOf("년");
        year = Integer.parseInt(fullDate.substring(0, firstIndex));

        int secondIndex = fullDate.indexOf("월");
        month = Integer.parseInt(fullDate.substring(firstIndex + 1, secondIndex));

        int thirdIndex = fullDate.indexOf("일");
        day = Integer.parseInt(fullDate.substring(secondIndex+1, thirdIndex));

    }

    private void setListener() {

        timeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //예약 결과 화면으로 넘어간다.
                String time = (String) timeViewAdapter.getItem(position);
                Log.i("myLog", time);

                //static 변수에 시간정보를 저장한다.
                ReserveSearchFragment.rtime = time;

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new ReserveResultFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CalendarFragment.previousPage = "SEARCH_RESULT";

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new CalendarFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });


        btnPrevious.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //예약 날짜는 2주를 넘지 않게 한다.

                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy년MM월dd일");
                Date date = null;
                try {
                    date = sdf.parse(fullDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                cal.setTime(date);
                cal.add(Calendar.DATE, -1);  //일 3증가

                Log.i("myLog", year+"-1은 ");

                String strDate = sdf.format(cal.getTime());
                Log.i("myLog", strDate);

                //변경된 날짜 표시
                txtRdate.setText(strDate);
                //변경된 요일 표시
                txtDow.setText(getDateDay(strDate));

                fullDate = strDate;
                ReserveSearchFragment.rdate = strDate;


                //네트워크에서 해당 날짜에 있는 예약 가능한 시간을 불러온다.
                setItemSetting();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy년MM월dd일");
                Date date = null;
                try {
                    date = sdf.parse(fullDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                cal.setTime(date);
                cal.add(Calendar.DATE, +1);

                Log.i("myLog", year+"+1은 ");

                String strDate = sdf.format(cal.getTime());
                Log.i("myLog", strDate);

                //변경된 날짜 표시
                txtRdate.setText(strDate);
                //변경된 요일 표시
                txtDow.setText(getDateDay(strDate));

                fullDate = strDate;
                ReserveSearchFragment.rdate = strDate;

                //네트워크에서 해당 날짜에 있는 예약 가능한 시간을 불러온다.
                setItemSetting();
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //새로고침하기
                Log.i("myLog", "새로고침");
                setItemSetting();
            }
        });

    }

    /**
     * 특정 날짜에 대하여 요일을 구함(일 ~ 토)
     *
     * @param date
     * @return
     * @throws Exception
     */
    public String getDateDay(String date) {

        String day = "";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년MM월dd일");
        Date nDate = null;

        try {
            nDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(nDate);

        int dayNum = cal.get(Calendar.DAY_OF_WEEK);

        switch (dayNum) {
            case 1:
                day = "일요일";
                break;
            case 2:
                day = "월요일";
                break;
            case 3:
                day = "화요일";
                break;
            case 4:
                day = "수요일";
                break;
            case 5:
                day = "목요일";
                break;
            case 6:
                day = "금요일";
                break;
            case 7:
                day = "토요일";
                break;

        }

        return day;
    }

    private void setItemSetting() {

        String clinicid = "test";

        Log.i("myLog", "setItemSetting() 메소드 호출"+ ReserveSearchFragment.rdate);
        String date = Util.getSimpleDate(ReserveSearchFragment.rdate);


        Map<String, String> map = new HashMap<>();
        map.put("clinicid", clinicid);
        map.put("date",date);

        Log.i("myLog","서버에 병원 아이디:"+clinicid+"의 "+date+"날짜의 예약시간 정보를 요청합니다.");

        //리스트뷰에 예약 가능한 시간만 보여줍니다.
        Network.setReserveList(timeViewAdapter, Util.getClinicTimeList(), map);

    }



    private void setBeautyTimeSetting() {

        beautyTimeList = new ArrayList<>();

        /*beautyTimeList.add("09:00");
        beautyTimeList.add("10:00");
        beautyTimeList.add("11:00");
        beautyTimeList.add("12:00");
        beautyTimeList.add("13:00");
        beautyTimeList.add("14:00");
        beautyTimeList.add("15:00");
        beautyTimeList.add("16:00");
        beautyTimeList.add("17:00");
        beautyTimeList.add("18:00");
        beautyTimeList.add("19:00");
        beautyTimeList.add("20:00");
        beautyTimeList.add("21:00");
        beautyTimeList.add("22:00");
        beautyTimeList.add("23:00");
        beautyTimeList.add("24:00");*/
    }

    private void setDBClinicTimeSetting() {

        /*
        Log.i("myLog", "setDBClinicTimeSetting이 실행됨");
        dbClinicTimeList = Network.getReviewList();

        for(String time : dbClinicTimeList){
            Log.i("myLog", "db에서 받아온 시간 정보는"+time);
        }*/

        //dbClinicTimeList = new ArrayList<>();

        /*dbClinicTimeList.add("09:00");
        dbClinicTimeList.add("09:30");

        dbClinicTimeList.add("10:30");
        dbClinicTimeList.add("11:00");
        dbClinicTimeList.add("11:30");
        dbClinicTimeList.add("12:00");
        dbClinicTimeList.add("12:30");
        dbClinicTimeList.add("13:00");
        dbClinicTimeList.add("13:30");
        dbClinicTimeList.add("14:00");
        dbClinicTimeList.add("14:30");
        dbClinicTimeList.add("15:00");
        dbClinicTimeList.add("15:30");
        dbClinicTimeList.add("16:00");
        dbClinicTimeList.add("16:30");
        dbClinicTimeList.add("17:00");
        dbClinicTimeList.add("17:30");
        dbClinicTimeList.add("18:00");
        dbClinicTimeList.add("18:30");
        dbClinicTimeList.add("19:00");
        dbClinicTimeList.add("19:30");
        dbClinicTimeList.add("20:00");
        dbClinicTimeList.add("20:30");
        dbClinicTimeList.add("21:00");
        dbClinicTimeList.add("21:30");
        dbClinicTimeList.add("22:00");
        dbClinicTimeList.add("22:30");
        dbClinicTimeList.add("23:00");
        dbClinicTimeList.add("23:30");*/
    }

}
