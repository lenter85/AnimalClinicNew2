package com.example.myapplication.reservation.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.reservation.util.Network;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReserveListFragment extends Fragment {

    private ListView reserveListView;
    private ReserveListViewAdapter reserveListViewAdapter;

    public ReserveListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reserve_list, container, false);

        reserveListView = (ListView) view.findViewById(R.id.reserveListView);
        reserveListViewAdapter = new ReserveListViewAdapter();

        reserveListView.setAdapter(reserveListViewAdapter);

        //예약자 명단 리스트를 DB에서 받아와 리스트뷰에 보여준다.

        //1.네트워크 요청이 있어야함.
        Network.getSubscriberList(reserveListViewAdapter, "test", "2016-7-22");

        return view;
    }

}
