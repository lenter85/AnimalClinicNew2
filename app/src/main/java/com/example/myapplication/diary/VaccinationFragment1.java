package com.example.myapplication.diary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.diary.dto.Vaccination;
import com.example.myapplication.network.VaccinationNetwork;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VaccinationFragment1 extends Fragment {
    private ImageView imgAddVac;
    private ListView listViewVaccination;
    private VaccinationAdapter vaccinationAdapter;
    private List<Vaccination> list;


    public VaccinationFragment1() {
        // Required empty public constructor
    }

    public void setList(List<Vaccination> list) {
        this.list = list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vaccination1, container, false);

        Vaccination vaccination = new Vaccination();
        list = new ArrayList<>();

        listViewVaccination = (ListView) view.findViewById(R.id.listViewVaccination);

        vaccinationAdapter = new VaccinationAdapter();
        vaccinationAdapter.setContext(getActivity());
        VaccinationNetwork.getVaccinationList(MyDiaryFragment.my.getDname(), vaccinationAdapter);
        listViewVaccination.setAdapter(vaccinationAdapter);



        imgAddVac = (ImageView) view.findViewById(R.id.imgAddVac);
        imgAddVac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new RegisterVaccinationFragment())
                        .addToBackStack(null)
                        .commit();

            }
        });

        return view;
    }


}
