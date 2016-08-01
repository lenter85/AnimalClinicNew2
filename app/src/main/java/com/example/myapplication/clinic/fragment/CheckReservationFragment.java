package com.example.myapplication.clinic.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import com.example.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckReservationFragment extends Fragment {
    private CalendarView calendarView;
    private Button button1;

    public CheckReservationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_check_reservation, container, false);
        calendarView = (CalendarView) view.findViewById(R.id.calendarView);
        button1 = (Button) view.findViewById(R.id.button1);
        calendarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new CheckReservationDetailFragment()).commit();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new CheckReservationDetailFragment()).commit();
            }
        });


        return view;
    }

}
