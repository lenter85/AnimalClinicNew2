package com.example.myapplication.calendar;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.reservation.fragment.ReserveEditFragment;
import com.example.myapplication.reservation.fragment.ReserveSearchFragment;
import com.example.myapplication.reservation.fragment.ReserveSearchResultFragment;
import java.util.Calendar;


public class CalendarFragment extends Fragment {

    public static String previousPage;

    /**
     * 월별 캘린더 뷰 객체
     */
    CalendarMonthView monthView;

    /**
     * 월별 캘린더 어댑터
     */
    CalendarMonthAdapter monthViewAdapter;

    /**
     * 월을 표시하는 텍스트뷰
     */
    TextView monthText;

    /**
     * 현재 연도
     */
    int curYear;

    /**
     * 현재 월
     */
    int curMonth;

    public CalendarFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        // 월별 캘린더 뷰 객체 참조
        monthView = (CalendarMonthView) view.findViewById(R.id.monthView);
        monthViewAdapter = new CalendarMonthAdapter(getContext());
        monthView.setAdapter(monthViewAdapter);

        // 리스너 설정
        monthView.setOnDataSelectionListener(new OnDataSelectionListener() {
            @SuppressLint("LongLogTag")
            public void onDataSelected(AdapterView parent, View v, int position, long id) {

                // 현재 선택한 일자 정보 표시
                MonthItem curItem = (MonthItem) monthViewAdapter.getItem(position);
                int day = curItem.getDay();

                Log.i("myLog", "선택된 날짜는 "+curYear+"년"+(curMonth+1)+"월"+day+"일 입니다");

               /* getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new ReserveListFragment())
                    .addToBackStack(null)
                    .commit();*/

                ReserveSearchFragment.rdate = getDatetoKorean(curYear, curMonth, day);

                if(previousPage.equals("SEARCH")){

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, new ReserveSearchFragment())
                            .commit();

                }else if(previousPage.equals("SEARCH_RESULT")){

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, new ReserveSearchResultFragment())
                            .commit();

                }else if(previousPage.equals("EDIT")){

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, new ReserveEditFragment())
                            .commit();
                }else if(previousPage.equals("VACCINE")){

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, new ReserveSearchFragment())
                            .commit();
                }

            }
        });

        monthText = (TextView) view.findViewById(R.id.monthText);
        setMonthText();

        // 이전 월로 넘어가는 이벤트 처리
        Button monthPrevious = (Button) view.findViewById(R.id.monthPrevious);
        monthPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                monthViewAdapter.setPreviousMonth();
                monthViewAdapter.notifyDataSetChanged();

                setMonthText();
            }
        });

        // 다음 월로 넘어가는 이벤트 처리
        Button monthNext = (Button) view.findViewById(R.id.monthNext);
        monthNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                monthViewAdapter.setNextMonth();
                monthViewAdapter.notifyDataSetChanged();

                setMonthText();
            }
        });

        return view;
    }

    private static String getDatetoKorean(int curYear , int curMonth, int curDay) {

        String rdate = "";

        Calendar cal = Calendar.getInstance();

        //현재 년도, 월, 일
        int year = cal.get ( cal.YEAR );
        int month = cal.get ( cal.MONTH ) + 1 ;
        int date = cal.get ( cal.DATE ) ;

        if((curYear+curMonth+1+curDay) == (year+month+date)){
            rdate = curYear+"년"+(curMonth+1)+"월"+curDay+"일(오늘)";
            return rdate;
        }

        rdate = curYear+"년"+(curMonth+1)+"월"+curDay+"일";
        return rdate;
    }

    /**
     * 월 표시 텍스트 설정
     */
    private void setMonthText() {
        curYear = monthViewAdapter.getCurYear();
        curMonth = monthViewAdapter.getCurMonth();

        monthText.setText(curYear + "년 " + (curMonth + 1) + "월");
    }

}
