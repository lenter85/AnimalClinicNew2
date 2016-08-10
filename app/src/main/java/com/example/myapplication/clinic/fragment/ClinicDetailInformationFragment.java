package com.example.myapplication.clinic.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.reservation.fragment.ReserveSearchFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClinicDetailInformationFragment extends Fragment {

    Button btnReserve;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ClinicDetailInformationAdapter clinicDetailInformationAdapter;

    public ClinicDetailInformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_clinic_detail_information, container, false);


        btnReserve = (Button)view.findViewById(R.id.btnReserve);


        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ReserveSearchFragment reserveSearchFragment = new ReserveSearchFragment();

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, reserveSearchFragment)
                        .commit();
            }
        });



        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);


        clinicDetailInformationAdapter = new ClinicDetailInformationAdapter(getChildFragmentManager());
        viewPager.setAdapter(clinicDetailInformationAdapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_menu_camera);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_menu_gallery);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_menu_manage);

        return view;
    }

//dd



}
