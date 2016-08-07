package com.example.myapplication.clinic.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.network.ClinicNetwork;
import com.example.myapplication.reservation.fragment.ReserveSearchFragment;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClinicDetailInformationFragment extends Fragment {

    Button btnReserve;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ClinicDetailInformationAdapter clinicDetailInformationAdapter;
    public static String clinicId;

    public ClinicDetailInformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_clinic_detail_information, container, false);

        ClinicNetwork clinicNetwork = new ClinicNetwork();
        clinicNetwork.getClinicInformation(view);

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

        tabLayout.getTabAt(0).setIcon(android.R.drawable.ic_menu_my_calendar);
        tabLayout.getTabAt(1).setIcon(android.R.drawable.ic_menu_mylocation);
        tabLayout.getTabAt(2).setIcon(android.R.drawable.ic_menu_myplaces);

        return view;
    }



    public void getClinicInformation () {
        new AsyncTask<Void, Integer, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String result = "fail";
                try {
                    // 데이터 구분 문자
                    String boundary = "----" + System.currentTimeMillis();

                    // 데이터 경계선
                    String delimiter = "\r\n--" + boundary + "\r\n";    //규약

                    // 커넥션 생성 및 설정
                    URL url = new URL("http://192.168.0.38:8080/AnimalClinicProject/clinicinformation");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();


                    //응답 코드 확인
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        result = "success";
                    }

                    //연결 끊기
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                if (result.equals("success")) {
                    Log.i("mylog", "성공");

                } else {
                    Log.i("mylog","실패");
                }
            }
        }.execute();

    }

}
