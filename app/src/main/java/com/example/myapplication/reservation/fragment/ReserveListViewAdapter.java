package com.example.myapplication.reservation.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.reservation.dto.ReserveListItem;

import java.util.ArrayList;

public class ReserveListViewAdapter extends BaseAdapter {

    private ArrayList<ReserveListItem> reserveList = new ArrayList<ReserveListItem>();

    public ReserveListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return reserveList.size() ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.reserve_list_item, parent, false);
        }

        TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
        TextView txtPname = (TextView) convertView.findViewById(R.id.txtPname);
        TextView txtPhone = (TextView) convertView.findViewById(R.id.txtPhone);
        TextView txtTime = (TextView) convertView.findViewById(R.id.txtTime);

        ReserveListItem item = (ReserveListItem)getItem(position);

        txtName.setText(item.getrName());
        txtPname.setText(item.getrPetName());
        txtPhone.setText(item.getrPhone());
        txtTime.setText(item.getrTime());

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
        return reserveList.get(position) ;
    }

    public void addItem(ReserveListItem item) {

        reserveList.add(item);
        this.notifyDataSetChanged();
    }
}
