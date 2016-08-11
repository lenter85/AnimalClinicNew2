package com.example.myapplication.reservation.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.diary.DiaryFragment;
import com.example.myapplication.reservation.dto.Reserve;
import com.example.myapplication.reservation.util.Network;
import com.example.myapplication.reservation.util.Util;

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

        myReserveListView.setAdapter(myReserveViewAdapter);

        Network.getMyReserveList(DiaryFragment.myDiary.getDname(), MainActivity.loginId, myReserveViewAdapter);

        myReserveListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Reserve reserve = (Reserve)myReserveViewAdapter.getItem(position);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ReserveResultFragment()).commit();
            }
        });

        return view;
    }

}
