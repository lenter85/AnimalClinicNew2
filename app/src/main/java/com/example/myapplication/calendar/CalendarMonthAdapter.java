package com.example.myapplication.calendar;

import android.content.Context;
import android.graphics.Color;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.reservation.util.Network;

import java.util.Calendar;

/**
 * 어댑터 객체 정의
 *
 * @author Mike
 */
public class CalendarMonthAdapter extends BaseAdapter {

    public static final String TAG = "CalendarMonthAdapter";

    Context mContext;

    public static int oddColor = Color.rgb(225, 225, 225);
    public static int headColor = Color.rgb(12, 32, 158);

    private int selectedPosition = -1;

    private MonthItem[] items;

    private int countColumn = 7;

    int mStartDay;
    int startDay;
    int curYear;
    int curMonth;

    int firstDay;
    int lastDay;

    int realMonth;
    int realDay;

    Calendar mCalendar;
    boolean recreateItems = false;

    public CalendarMonthAdapter(Context context) {
        super();

        mContext = context;

        init();
    }

    public CalendarMonthAdapter(Context context, AttributeSet attrs) {
        super();

        mContext = context;

        init();
    }

    private void init() {
        items = new MonthItem[7 * 6];

        mCalendar = Calendar.getInstance();
        recalculate();
        resetDayNumbers();

        Calendar oCalendar = Calendar.getInstance();  // 현재 날짜/시간 등의 각종 정보 얻기
        realMonth = oCalendar.get(Calendar.MONTH) + 1;
        realDay = oCalendar.get(Calendar.DATE);
    }

    public void recalculate() {

        // set to the first day of the month
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);

        // get week day
        int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
        firstDay = getFirstDay(dayOfWeek);
        Log.d(TAG, "firstDay : " + firstDay);


        mStartDay = mCalendar.getFirstDayOfWeek();
        curYear = mCalendar.get(Calendar.YEAR);
        curMonth = mCalendar.get(Calendar.MONTH);
        lastDay = getMonthLastDay(curYear, curMonth);

        Log.d(TAG, "curYear : " + curYear + ", curMonth : " + curMonth + ", lastDay : " + lastDay);

        int diff = mStartDay - Calendar.SUNDAY - 1;
        startDay = getFirstDayOfWeek();
        Log.d(TAG, "mStartDay : " + mStartDay + ", startDay : " + startDay);

    }

    public void setPreviousMonth() {
        mCalendar.add(Calendar.MONTH, -1);
        recalculate();

        resetDayNumbers();
        selectedPosition = -1;
    }

    public void setNextMonth() {
        mCalendar.add(Calendar.MONTH, 1);
        recalculate();

        resetDayNumbers();
        selectedPosition = -1;
    }

    public void resetDayNumbers() {
        for (int i = 0; i < 42; i++) {
            // calculate day number
            int dayNumber = (i + 1) - firstDay;
            if (dayNumber < 1 || dayNumber > lastDay) {
                dayNumber = 0;
            }

            // save as a data item
            items[i] = new MonthItem(dayNumber);
        }
    }

    private int getFirstDay(int dayOfWeek) {
        int result = 0;
        if (dayOfWeek == Calendar.SUNDAY) {
            result = 0;
        } else if (dayOfWeek == Calendar.MONDAY) {
            result = 1;
        } else if (dayOfWeek == Calendar.TUESDAY) {
            result = 2;
        } else if (dayOfWeek == Calendar.WEDNESDAY) {
            result = 3;
        } else if (dayOfWeek == Calendar.THURSDAY) {
            result = 4;
        } else if (dayOfWeek == Calendar.FRIDAY) {
            result = 5;
        } else if (dayOfWeek == Calendar.SATURDAY) {
            result = 6;
        }

        return result;
    }


    public int getCurYear() {
        return curYear;
    }

    public int getCurMonth() {
        return curMonth;
    }


    public int getNumColumns() {
        return 7;
    }

    public int getCount() {
        return 7 * 6;
    }

    public Object getItem(int position) {
        return items[position];
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView(" + position + ") called.");

        MonthItemView itemView;
        if (convertView == null) {
            itemView = new MonthItemView(mContext);
        } else {
            itemView = (MonthItemView) convertView;
        }

        // create a params
        GridView.LayoutParams params = new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, 330);

        // calculate row and column
        int rowIndex = position / countColumn;
        int columnIndex = position % countColumn;


        int month = getCurMonth() + 1;
        int day = items[position].getDay();


        Log.d(TAG, "Index : " + rowIndex + ", " + columnIndex);

        // set item data and properties
        itemView.setItem(items[position]);
        //Log.i("test", "items[position]: " + items[position].getDay());
        itemView.setLayoutParams(params);
        itemView.setPadding(4, 4, 4, 4);

        // set properties
        itemView.setGravity(Gravity.LEFT);

        if (columnIndex == 0) {
            itemView.setTextColor(Color.RED);
        } else {
            itemView.setTextColor(Color.BLACK);
        }

        if (MainActivity.LoginType.equals("NOMAL")) {

            Log.i("test2","선택한 day:"+day);
            Log.i("test2","realDay:"+realDay);

            if ((month == realMonth) && (1 <= day) && (day <= 31) && (day < realDay)) {

                //현재 날짜보다 이전 날짜라면 회색
                //itemView.append("\n예약불가");
                //itemView.setTextSize(10);

                itemView.setBackgroundColor(Color.parseColor("#BDBDBD"));
                itemView.setClickable(false);

            }else {
                itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }

        } else {


            StringBuilder reserveDate = new StringBuilder();
            reserveDate.append(getCurYear()).append("-").append(month).append("-").append(day);

            Log.i("test1", "예약 조회일: " + reserveDate.toString());

            //해당일에 예약이 되있으면 파란색

            //예약이 되지 않았으면 하얀색으로 표시한다.

            //예약이 됐는지 안됐는지의 여부는 해당일에 예약된 예약테이블에 행이 1개 이상이면 예약된 것 아니면 예약되지 않은 것이다.

            //int로 리턴받는다. 1이상이면 예약된 것 0이면 예약안된 것.


            Network.isReserved( itemView, MainActivity.loginId, reserveDate.toString());
        }


        return itemView;
    }


    /**
     * Get first day of week as android.text.format.Time constant.
     *
     * @return the first day of week in android.text.format.Time
     */
    public static int getFirstDayOfWeek() {
        int startDay = Calendar.getInstance().getFirstDayOfWeek();
        if (startDay == Calendar.SATURDAY) {
            return Time.SATURDAY;
        } else if (startDay == Calendar.MONDAY) {
            return Time.MONDAY;
        } else {
            return Time.SUNDAY;
        }
    }


    /**
     * get day count for each month
     *
     * @param year
     * @param month
     * @return
     */
    private int getMonthLastDay(int year, int month) {
        switch (month) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return (31);

            case 3:
            case 5:
            case 8:
            case 10:
                return (30);

            default:
                if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                    return (29);   // 2월 윤년계산
                } else {
                    return (28);
                }
        }
    }


    /**
     * set selected row
     */
    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    /**
     * get selected row
     *
     * @return
     */
    public int getSelectedPosition() {
        return selectedPosition;
    }


}
