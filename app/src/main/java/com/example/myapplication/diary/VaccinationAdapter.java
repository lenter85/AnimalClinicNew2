package com.example.myapplication.diary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.diary.dto.Vaccination;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-07-08.
 */
public class VaccinationAdapter extends BaseAdapter{
    private List<Vaccination> list = new ArrayList<>();

    public void setList(List<Vaccination> list) {
        this.list = list;
    }

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.item_vaccination, parent, false);
        }

        //데이터 세팅
        TextView txtVName = (TextView) convertView.findViewById(R.id.txtVName);
        TextView txtVLastDate = (TextView) convertView.findViewById(R.id.txtVLastDate);
        TextView txtVNextDate = (TextView) convertView.findViewById(R.id.txtVNextDate);

        Vaccination vaccination = list.get(position);
        txtVName.setText(vaccination.getVname());
        txtVLastDate.setText(vaccination.getVdate());
        txtVNextDate.setText(vaccination.getVndate());


        return convertView;
    }

    public void addItem(Vaccination vaccination) {
        list.add(vaccination);
        this.notifyDataSetChanged();
    }
}
