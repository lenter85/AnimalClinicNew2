package com.example.myapplication.clinic.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2016-07-07.
 */
public class ClinicDetailInformationAdapter extends FragmentPagerAdapter {
    public ClinicDetailInformationAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new ClinicInformationTabFragment();
        } else if(position == 1){
            return new ClinicFacilitiesFragment();
        } else if(position == 2){
            return new ClinicReviewFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0) {
            return "정  보";
        }else if(position == 1) {
            return "시  설";
        }else if(position == 2) {
            return "후  기";
        }
        return null;
    }
}
