package com.example.myapplication.reservation.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.reservation.dto.ReserveListItem;

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


        ReserveListItem item = new ReserveListItem();
        item.setrName("장준식");
        item.setrBirth("2016년08월03");
        item.setrPhone("010-7735-0407");
        item.setrTime("09:00 ~ 09:30");

        reserveListViewAdapter.addItem(item);

        return view;
    }

}
