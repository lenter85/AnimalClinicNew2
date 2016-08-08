package com.example.myapplication.clinic.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.network.ClinicNetwork;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClinicFacilitiesFragment extends Fragment {

    public static Context context;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;

    public ClinicFacilitiesFragment() {
        // Required empty public constructor
    }

    public void setContext (Context context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clinic_facilities, container, false);

        imageView1 = (ImageView) view.findViewById(R.id.imageView1);
        imageView2 = (ImageView) view.findViewById(R.id.imageView2);
        imageView3 = (ImageView) view.findViewById(R.id.imageView3);
        imageView4 = (ImageView) view.findViewById(R.id.imageView4);

        ClinicNetwork clinicNetwork = new ClinicNetwork();
        clinicNetwork.getClinicFacility(MainActivity.clinicId, imageView1, imageView2, imageView3, imageView4);


        return view;

    }




}
