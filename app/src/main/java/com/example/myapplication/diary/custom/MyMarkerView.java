
package com.example.myapplication.diary.custom;

import android.content.Context;
import android.widget.TextView;

import com.example.myapplication.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.Utils;


/**
 * Custom implementation of the MarkerView.
 * 
 * @author Philipp Jahoda
 */
public class MyMarkerView extends MarkerView {

    private TextView tvContent;

    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;
            tvContent.setSingleLine(false);
            tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 0, true) + "kg\n" + e.getData());

        } else {
            tvContent.setSingleLine(false);
            tvContent.setText("" + Utils.formatNumber(e.getY(), 0, true) + "kg\n" + e.getData());
        }
    }

    @Override
    public int getXOffset(float xpos) {
        // this will center the marker-view horizontally
        return -(getWidth() / 2);
        /*if(xpos <-(getWidth() / 2)) {
            return (int)  -(getWidth() / 2);
        } else {
            return (int) -(getWidth() / 2) - 30;
        }*/
    }

    @Override
    public int getYOffset(float ypos) {
        // this will cause the marker-view to be above the selected value
        return -getHeight();
        /*if(ypos > getHeight()-5) {
            return -getHeight()+getHeight()/2;
        } else {
            return -getHeight();
        }*/

    }
}
