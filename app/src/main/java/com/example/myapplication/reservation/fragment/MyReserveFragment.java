package com.example.myapplication.reservation.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.reservation.dto.Reserve;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyReserveFragment extends Fragment {

    ListView myReserveListView;
    MyReserveViewAdapter myReserveViewAdapter;

    public MyReserveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("나의 예약목록");
        View view = inflater.inflate(R.layout.fragment_my_reserve, container, false);

        myReserveListView = (ListView)view.findViewById(R.id.myReserveListView);
        myReserveViewAdapter = new MyReserveViewAdapter();

        itemSetting();

        myReserveListView.setAdapter(myReserveViewAdapter);

        myReserveListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Reserve reserve = (Reserve)myReserveViewAdapter.getItem(position);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ReserveResultFragment()).commit();
            }
        });

        return view;
    }

    private void itemSetting() {
        //myReserveViewAdapter.addItem(new Reserve("우디","우리동네 동물병원1","서울시 노원구 하계1동","2016년7월16일","10:00~10:30","진료예약"));
        //myReserveViewAdapter.addItem(new Reserve("미유","우리동네 동물병원2","서울시 노원구 하계2동","2016년7월16일","12:00~12:30","진료예약"));
        //myReserveViewAdapter.addItem(new Reserve("두유","우리동네 동물병원3","서울시 노원구 하계3동","2016년7월16일","1:00~10:30","미용예약"));
        //myReserveViewAdapter.addItem(new Reserve("두유","우리동네 동물병원3","서울시 노원구 하계3동","2016년7월16일","1:00~10:30","미용예약"));
        //myReserveViewAdapter.addItem(new Reserve("두유","우리동네 동물병원3","서울시 노원구 하계3동","2016년7월16일","1:00~10:30","미용예약"));
        //myReserveViewAdapter.addItem(new Reserve("두유","우리동네 동물병원3","서울시 노원구 하계3동","2016년7월16일","1:00~10:30","미용예약"));
        //myReserveViewAdapter.addItem(new Reserve("두유","우리동네 동물병원3","서울시 노원구 하계3동","2016년7월16일","1:00~10:30","미용예약"));
        //myReserveViewAdapter.addItem(new Reserve("두유","우리동네 동물병원3","서울시 노원구 하계3동","2016년7월16일","1:00~10:30","미용예약"));
        //myReserveViewAdapter.addItem(new Reserve("두유","우리동네 동물병원3","서울시 노원구 하계3동","2016년7월16일","1:00~10:30","미용예약"));
        //myReserveViewAdapter.addItem(new Reserve("두유","우리동네 동물병원3","서울시 노원구 하계3동","2016년7월16일","1:00~10:30","미용예약"));
    }

}
