package com.example.myapplication.diary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterWeightFragment extends Fragment {
    private Button btnAddWeight;
    private Button btnCancelw;

    public RegisterWeightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_register_weight, container, false);

        btnAddWeight = (Button) view.findViewById(R.id.btnAddWeight);
        btnAddWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new WeightFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnCancelw = (Button) view.findViewById(R.id.btnCancelw);
        btnCancelw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new WeightFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }
}
