package com.example.myapplication.reservation.fragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.reservation.dto.Reserve;

import java.util.ArrayList;

public class MyReserveViewAdapter extends BaseAdapter {

    private Reserve reserve;

    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<Reserve> listViewItemList = new ArrayList<Reserve>();

    // ListViewAdapter의 생성자
    public MyReserveViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.myreserve_itemview, parent, false);
        }

        TextView txtPname = (TextView)convertView.findViewById(R.id.txtPname);
        TextView txtCname = (TextView)convertView.findViewById(R.id.txtCname);
        TextView txtCaddress = (TextView)convertView.findViewById(R.id.txtCaddress);
        TextView txtTime = (TextView)convertView.findViewById(R.id.txtTime);
        TextView txtRtype = (TextView)convertView.findViewById(R.id.txtRtype);
        Button btnEdit = (Button)convertView.findViewById(R.id.btnEdit);
        Button btnCancle = (Button)convertView.findViewById(R.id.btnCancle);

        reserve = (Reserve)getItem(position);

        Log.i("test", reserve.getRpname());
        Log.i("test", reserve.getRclinicName());
        Log.i("test", reserve.getRaddress());
        Log.i("test", reserve.getRtime());
        Log.i("test", reserve.getRtype());



        txtPname.setText(reserve.getRpname());
        txtCname.setText(reserve.getRclinicName());
        txtCaddress.setText(reserve.getRaddress());
        txtTime.setText(reserve.getRtime());
        txtRtype.setText(reserve.getRtype());



        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("mylog","수정버튼 클릭됨");

                //수정 화면으로 이동
            }
        });

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("mylog","취소버튼 클릭됨");

                //확인 팝업을 띄우고 취소하기
            }
        });

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
        return listViewItemList.get(position) ;
    }

    public void addItem(Reserve reserve) {

        listViewItemList.add(reserve);
        this.notifyDataSetChanged();
    }
}
