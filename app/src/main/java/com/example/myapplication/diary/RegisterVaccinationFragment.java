package com.example.myapplication.diary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.diary.dto.Vaccination;
import com.example.myapplication.network.VaccinationNetwork;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterVaccinationFragment extends Fragment {
    private Button btnRegisterVac;
    private Button btnCancelVac;
    EditText txtVname;
    EditText txtVdate;
    EditText txtVndate;
    private Vaccination vaccination = new Vaccination();

    public RegisterVaccinationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_register_vaccination, container, false);

        btnRegisterVac = (Button) view.findViewById(R.id.btnRegisterVac);
        txtVname = (EditText) view.findViewById(R.id.txtVname);
        txtVdate = (EditText) view.findViewById(R.id.txtVdate);
        txtVndate = (EditText) view.findViewById(R.id.txtVndate);



        btnRegisterVac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vaccination.setVname(txtVname.getText().toString());
                vaccination.setVdate(txtVdate.getText().toString());
                vaccination.setVndate(txtVndate.getText().toString());
                ////////////////임의값
                vaccination.setDname("우디");
                vaccination.setMid("test");
                ////////////////
                VaccinationNetwork.sendVaccination(vaccination);
                getActivity().
                        getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragmentContainer, new VaccinationFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnCancelVac = (Button) view.findViewById(R.id.btnCancelVac);

        btnCancelVac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().
                        getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragmentContainer, new VaccinationFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;

    }

}
