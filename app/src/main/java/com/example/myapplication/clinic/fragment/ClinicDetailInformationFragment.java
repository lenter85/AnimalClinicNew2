package com.example.myapplication.clinic.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.reservation.fragment.ReserveSearchFragment;
import com.example.myapplication.reservation.util.Util;

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

                if(MainActivity.loginStatus == false){

                    Util.showToast(getContext(), "로그인 후에 이용하실 수 있습니다.");
                    return;
                }else{

                    if(MainActivity.LoginType.equals("CLINIC")){
                        Util.showToast(getContext(), "병원 관리자 이용불가");
                        return;
                    }
                }

                ReserveSearchFragment reserveSearchFragment = new ReserveSearchFragment();

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, reserveSearchFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });



        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);


        clinicDetailInformationAdapter = new ClinicDetailInformationAdapter(getChildFragmentManager());
        viewPager.setAdapter(clinicDetailInformationAdapter);
        clinicDetailInformationAdapter.notifyDataSetChanged();



        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.info1);
        tabLayout.getTabAt(1).setIcon(R.drawable.photo1);
        tabLayout.getTabAt(2).setIcon(R.drawable.review1);

        return view;
    }


}
