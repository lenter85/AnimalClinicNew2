package com.example.myapplication.reservation.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

    public class TimeViewAdapter extends BaseAdapter {

        // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
        private List<String> list = new ArrayList<String>();
        public void setList(List<String> list) {this.list = list;}

        // ListViewAdapter의 생성자
        private Context context;
        public TimeViewAdapter(Context context) {
            this.context = context;
        }

        // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
        @Override
        public int getCount() {
            return list.size() ;
        }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.time_view, parent, false);
        }

        TextView txtTime = (TextView)convertView.findViewById(R.id.txtTime);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        String time = list.get(position);
        txtTime.setText(time);

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return list.get(position) ;
    }

    public void addItem(String time) {

        list.add(time);
    }
}
