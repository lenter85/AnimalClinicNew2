package com.example.myapplication.clinic.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.clinic.dto.Clinic;
import com.example.myapplication.network.MainNetwork;

import java.util.ArrayList;
import java.util.List;

public class ClinicListViewAdapter extends BaseAdapter {

    Context context;

    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private List<Clinic> clinicList = new ArrayList<Clinic>();
    public void setList(List<Clinic> list) {this.clinicList = list;}

    // ListViewAdapter의 생성자
    public ClinicListViewAdapter() {
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return clinicList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.clinic_itemview, parent, false);
        }

        TextView txtName = (TextView)convertView.findViewById(R.id.txtAName);
        TextView txtAddress = (TextView)convertView.findViewById(R.id.txtAddress);

        ImageView imgClinic = (ImageView) convertView.findViewById(R.id.imgClinic);


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        Clinic clinic = (Clinic)getItem(position);

        String cname = clinic.getCname();
        String clocation = clinic.getClocation();
        String cimage1 = clinic.getCimage();
        //int cscore = clinic.getCscore();

        txtName.setText(cname); //병원이름
        txtAddress.setText(clocation); //병원위치

        MainNetwork.getClinicImage(cimage1, imgClinic); //병원사진 등록

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
        return clinicList.get(position) ;
    }

    public void addItem(Clinic clinic) {

        clinicList.add(clinic);
        this.notifyDataSetChanged();
    }
}
