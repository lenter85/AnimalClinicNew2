package com.example.myapplication.diary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.network.WeightNetwork;
import com.google.android.gms.vision.text.Text;

import java.sql.SQLDataException;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterWeightFragment extends Fragment {
    private Button btnAddWeight;
    private Button btnCancelw;
    private TextView txtWeight;
    private TextView txtWeightDate;

    public RegisterWeightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_register_weight, container, false);

        btnAddWeight = (Button) view.findViewById(R.id.btnAddWeight);
        txtWeight = (TextView) view.findViewById(R.id.txtWeight);
        txtWeightDate = (TextView) view.findViewById(R.id.txtWeightDate);

        if(checkwdate(txtWeightDate.getText().toString()))

        btnAddWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    WeightNetwork.registerWeight(Double.parseDouble(txtWeight.getText().toString()), txtWeightDate.getText().toString());
                    Log.i("mylog", txtWeight.getText().toString());
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

    public boolean checkwdate(String wdate) {
        String checkedwdate = "\\d{4}.\\d{2}.\\d{2}";
        if (Pattern.matches(checkedwdate, wdate)) {
            return true;
        } else {
            return false;
        }
    }
}