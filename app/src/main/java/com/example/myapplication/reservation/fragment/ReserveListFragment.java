package com.example.myapplication.reservation.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.myapplication.MainActivity;
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
        getActivity().setTitle("예약자 정보");
        View view = inflater.inflate(R.layout.fragment_reserve_list, container, false);

        reserveListView = (ListView) view.findViewById(R.id.reserveListView);
        reserveListViewAdapter = new ReserveListViewAdapter();

        reserveListView.setAdapter(reserveListViewAdapter);

        //예약자 명단 리스트를 DB에서 받아와 리스트뷰에 보여준다.

        //1.네트워크 요청이 있어야함.

        String rdate = ReserveSearchFragment.rdate.replace("(오늘)","");

        rdate = rdate.replace('년','-');
        rdate = rdate.replace('월','-');
        rdate = rdate.replace("일","");

        Log.i("test1", "병원 아이디: "+MainActivity.loginId+ " 예약 정보" +rdate);
        Network.getSubscriberList(reserveListViewAdapter, MainActivity.loginId, rdate);

        return view;
    }

}
