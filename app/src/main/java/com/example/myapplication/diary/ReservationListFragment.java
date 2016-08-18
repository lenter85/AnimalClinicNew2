package com.example.myapplication.diary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.reservation.dto.Reserve;
import com.example.myapplication.reservation.fragment.ReserveResultFragment;
import com.example.myapplication.reservation.util.Network;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationListFragment extends Fragment {

    ListView myReserveListView;
    MyReserveViewAdapter myReserveViewAdapter;


    public ReservationListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //toolbar(툴바) 제목 바꾸기
        //getActivity().setTitle("나의 예약목록");
        View view = inflater.inflate(R.layout.fragment_reservation_list, container, false);

        myReserveListView = (ListView)view.findViewById(R.id.myReserveListView);
        myReserveViewAdapter = new MyReserveViewAdapter();

        myReserveListView.setAdapter(myReserveViewAdapter);

        Network.getMyReserveList(MyDiaryFragment.my.getDname(), MainActivity.loginId, myReserveViewAdapter);


        //예약목록 클릭이벤트 처리 주석
        /*myReserveListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Reserve reserve = (Reserve)myReserveViewAdapter.getItem(position);

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new ReserveResultFragment())
                        .commit();
            }
        });*/

        return view;
    }

}
