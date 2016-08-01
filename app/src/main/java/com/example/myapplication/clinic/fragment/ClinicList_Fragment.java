package com.example.myapplication.clinic.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.network.MainNetwork;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClinicList_Fragment extends Fragment {

    private ListView clinicListView;
    private ClinicListViewAdapter clinicListViewAdapter;

    public ClinicList_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_cliniclist, container, false);
        clinicListView = (ListView)view.findViewById(R.id.clinicListView);

        clinicListViewAdapter = new ClinicListViewAdapter();

        /*for(int i=1; i<=10; i++){
            Clinic clinic = new Clinic();
            clinic.setCid(String.valueOf(i));
            clinic.setCname("동물병원"+i);
            clinic.setClocation("경찰병원역 "+i+"번 출구");
            clinic.setCscore(i);

            clinicListViewAdapter.addItem(clinic);
        }*/

        clinicListView.setAdapter(clinicListViewAdapter);

        //DB에서 병원 리스트를 불러온다.
        MainNetwork.setReserveList(clinicListViewAdapter);


        clinicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.i("mylog", "리스트뷰가 클릭됨");
                //Clinic clinic = (Clinic)clinicListViewAdapter.getItem(position);

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new ClinicDetailInformationFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });


        return view;
    }

}
