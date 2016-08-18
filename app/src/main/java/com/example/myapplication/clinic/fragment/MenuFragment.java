package com.example.myapplication.clinic.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.calendar.CalendarFragment;
import com.example.myapplication.reservation.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {
    private TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        textView1 = (TextView) view.findViewById(R.id.textView1);
        textView2 = (TextView) view.findViewById(R.id.textView2);
        textView3 = (TextView) view.findViewById(R.id.textView3);
        textView4 = (TextView) view.findViewById(R.id.textView4);
        textView5 = (TextView) view.findViewById(R.id.textView5);
        textView6 = (TextView) view.findViewById(R.id.textView6);


        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.loginStatus == true){
                    if(MainActivity.LoginType.equals("CLINIC")){
                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragmentContainer, MainActivity.registerClinicFragment)
                                .addToBackStack(null)
                                .commit();
                    }else{

                        Util.showToast(getContext(),  "병원 관리자 기능입니다.");
                    }
                }else{

                    Util.showToast(getContext(), "로그인 후 이용하실 수 있습니다.");
                }



                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, MainActivity.registerClinicFragment).commit();
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "서비스 준비중 입니다.", Toast.LENGTH_LONG).show();
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ModifyClinicInformationFragment()).commit();
            }
        });


        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(MainActivity.loginStatus == true){
                    if(MainActivity.LoginType.equals("CLINIC")){
                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragmentContainer, new CalendarFragment())
                                .addToBackStack(null)
                                .commit();
                    }else{

                        Util.showToast(getContext(),  "병원 관리자 기능입니다.");
                    }
                }else{

                    Util.showToast(getContext(), "로그인 후 이용하실 수 있습니다.");
                }


            }
        });


        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "서비스 준비중 입니다.", Toast.LENGTH_LONG).show();
            }
        });

        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "서비스 준비중 입니다.", Toast.LENGTH_LONG).show();
            }
        });


        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "서비스 준비중 입니다.", Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }

}
