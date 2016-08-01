package com.example.myapplication.diary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.diary.dto.Vaccination;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VaccinationFragment extends Fragment {
    private Button btnVList;
    private Button btnVInfo;
    private List<Vaccination> list = new ArrayList<>();
    private Vaccination vaccination;


    public VaccinationFragment() {
        // Required empty public constructor
    }

    public void setList(List<Vaccination> list) {
        this.list = list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vaccination, container, false);

        Button btnVList = (Button) view.findViewById(R.id.btnVList);


        btnVList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().
                        getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragmentSubContainer, new VaccinationFragment1())
                        .addToBackStack(null)
                        .commit();
            }
        });

        Button btnVInfo = (Button) view.findViewById(R.id.btnVInfo);

        btnVInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().
                        getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragmentSubContainer, new VaccinationFragment2())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

}
