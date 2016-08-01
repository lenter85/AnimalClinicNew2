package com.example.myapplication.diary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.diary.dto.Diary;
import com.example.myapplication.network.DiaryNetwork;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-07-09.
 */
public class DiaryAdapter extends BaseAdapter {
    private List<Diary> list = new ArrayList<>();
    private Context context;

    public void setList(List<Diary> list) {
        this.list = list;
    }

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
            convertView = layoutInflater.inflate(R.layout.item_diary, parent, false);
        }

        TextView txtDname = (TextView) convertView.findViewById(R.id.txtDname);
        TextView txtDgender = (TextView) convertView.findViewById(R.id.txtDgender);
        ImageView imgDimage = (ImageView) convertView.findViewById(R.id.imgDimage);

        Diary diary = list.get(position);
        DiaryNetwork.getDiaryImage(diary.getDimage(), imgDimage);
        txtDname.setText(diary.getDname());
        txtDgender.setText(diary.getDgender());


        return convertView;
    }

    public void addItem(Diary diary) {
        list.add(diary);
        this.notifyDataSetChanged();
    }
}
