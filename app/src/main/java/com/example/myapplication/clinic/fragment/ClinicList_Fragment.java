package com.example.myapplication.clinic.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.clinic.dto.Clinic;
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

        Toast.makeText(getContext(), "onCreateView()호출 ", Toast.LENGTH_LONG).show();
        View view =  inflater.inflate(R.layout.fragment_cliniclist, container, false);
        clinicListView = (ListView)view.findViewById(R.id.clinicListView);

        clinicListViewAdapter = new ClinicListViewAdapter();

        clinicListView.setAdapter(clinicListViewAdapter);

        //DB에서 병원 리스트를 불러온다.
        MainNetwork.setReserveList(clinicListViewAdapter, getContext());


        clinicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.i("mylog", "리스트뷰가 클릭됨");

                Clinic clinic = (Clinic)clinicListViewAdapter.getItem(position);
                MainActivity.cName = clinic.getCname();

                Toast.makeText(getContext(), clinic.getCname(), Toast.LENGTH_LONG).show();

                ClinicDetailInformationFragment fragment = new ClinicDetailInformationFragment();

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


        return view;
    }
}
