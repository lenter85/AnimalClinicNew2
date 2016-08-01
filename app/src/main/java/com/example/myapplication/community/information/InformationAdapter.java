package com.example.myapplication.community.information;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.community.dto.Information;

import java.util.List;

public class InformationAdapter extends BaseAdapter {
    private Context context;
    private ImageView infoImageView;
    private TextView infoItemTitle;
    private TextView infoItemTime;

    public InformationAdapter(Context context) {
        this.context = context;
    }

    private List<Information> list;

    public void setList(List<Information> list) {
        this.list = list;
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

        context = parent.getContext();

        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.fragment_info_item,parent,false);
        }

        infoItemTitle = (TextView)convertView.findViewById(R.id.infoItemTitle);
        infoImageView = (ImageView)convertView.findViewById(R.id.infoImage);
       infoItemTime = (TextView)convertView.findViewById(R.id.infoItemTime);

        Information information = list.get(position);
        infoItemTitle.setText(information.getiTitle());
        infoImageView.setImageResource(information.getInfoImageView());
        infoItemTime.setText(information.getiDate().toString());

        return convertView;
    }
}
