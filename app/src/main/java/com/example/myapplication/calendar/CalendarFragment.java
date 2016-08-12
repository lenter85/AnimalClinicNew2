package com.example.myapplication.calendar;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.reservation.fragment.ReserveEditFragment;
import com.example.myapplication.reservation.fragment.ReserveListFragment;
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

    int realMonth;
    int realYear;
    int realDay;

    public CalendarFragment() {

        Calendar oCalendar = Calendar.getInstance( );  // 현재 날짜/시간 등의 각종 정보 얻기
        realYear = oCalendar.get(Calendar.YEAR);
        realMonth = oCalendar.get(Calendar.MONTH) + 1;
        realDay = oCalendar.get(Calendar.DATE)+1;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("달력");

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

                if(( (curMonth+1) == realMonth )){

                    if((day < 1) || (day > 31) || ( day < realDay)){
                        //Toast.makeText(getContext(), "선택불가능한 날짜입니다", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                Log.i("myLog", "선택된 날짜는 "+curYear+"년"+(curMonth+1)+"월"+day+"일 입니다");
                ReserveSearchFragment.rdate = getDatetoKorean(curYear, curMonth, day);

                if(MainActivity.LoginType.equals("NOMAL")){

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

                }else if(MainActivity.LoginType.equals("CLINIC")){

                    getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new ReserveListFragment())
                        .addToBackStack(null)
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

                if(MainActivity.LoginType.equals("NOMAL")){

                    if((realYear == curYear) && (curMonth < realMonth)){
                        Toast.makeText(getContext(), "이전달 선택 불가", Toast.LENGTH_LONG).show();
                        return;
                    }
                }


                if(MainActivity.LoginType.equals("CLINIC")){
                    showProgressDialog();
                }


                monthViewAdapter.setPreviousMonth();
                monthViewAdapter.notifyDataSetChanged();

                setMonthText();
            }
        });

        // 다음 월로 넘어가는 이벤트 처리
        Button monthNext = (Button) view.findViewById(R.id.monthNext);
        monthNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(MainActivity.LoginType.equals("CLINIC")){
                    showProgressDialog();
                }


                //일단 DB를 탐색하고 색을 지정해주고
                //월을 변경한다.
                monthViewAdapter.setNextMonth();
                monthViewAdapter.notifyDataSetChanged();

                setMonthText();
            }
        });

        return view;
    }

    private void showProgressDialog() {

        AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {

            ProgressDialog asyncDialog = new ProgressDialog(getContext());

            @Override
            protected void onPreExecute() {
                Log.i("test", "setReserveList onPreExecute()실행 ");
                asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                asyncDialog.setMessage("예약 정보를 불러오고 있습니다..");

                // show dialog
                asyncDialog.show();
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                asyncDialog.dismiss();

            }
        };

        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR , null);
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
