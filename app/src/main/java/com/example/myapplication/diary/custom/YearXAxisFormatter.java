package com.example.myapplication.diary.custom;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.AxisValueFormatter;

/**
 * Created by Philipp Jahoda on 14/09/15.
 */
public class YearXAxisFormatter implements AxisValueFormatter {

    protected String[] mMonths = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"
    };

    public YearXAxisFormatter() {
        // maybe do something here or provide parameters in constructor

    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        //float percent = value / axis.mAxisRange;
        float percent = value / 12;
        return mMonths[(int) (mMonths.length * percent)];
    }

    @Override
    public int getDecimalDigits() {
        return 0;
    }
}
