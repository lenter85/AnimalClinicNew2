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


        final ReserveSearchFragment reserveSearchFragment = new ReserveSearchFragment();
        reserveSearchFragment.setArguments(new Bundle());  //setArgument메소드로 번들 셋팅은 딲 한번 가능

        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, reserveSearchFragment, "ReserveSearchFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });



        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);


        clinicDetailInformationAdapter = new ClinicDetailInformationAdapter(getChildFragmentManager());
        viewPager.setAdapter(clinicDetailInformationAdapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.info1);
        tabLayout.getTabAt(1).setIcon(R.drawable.photo1);
        tabLayout.getTabAt(2).setIcon(R.drawable.review1);

        return view;
    }





}
