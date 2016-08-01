package com.example.myapplication.diary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.diary.dto.Vaccination;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDiaryFragment extends Fragment {
    private ImageView album;
    private ImageView reservationList;
    private ImageView vaccination;
    private ImageView weight;
    private List<Vaccination> list = new ArrayList<>();

    public MyDiaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mydiary, container, false);


        album = (ImageView) view.findViewById(R.id.album);
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new AlbumFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        reservationList = (ImageView) view.findViewById(R.id.reservationList);
        reservationList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new ReservationListFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });


        vaccination = (ImageView) view.findViewById(R.id.vaccination);
        vaccination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new VaccinationFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        weight = (ImageView) view.findViewById(R.id.weight);
        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new WeightFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

}
