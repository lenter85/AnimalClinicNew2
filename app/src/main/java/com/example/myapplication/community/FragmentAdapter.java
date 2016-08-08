package com.example.myapplication.community;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.myapplication.community.board.BoardFragment;
import com.example.myapplication.community.gallery.GalleryFragment;

/**
 * Created by Administrator on 2016-07-05.
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new GalleryFragment();
        } else if(position == 1){
            return new BoardFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0) {
            return "갤러리";
        }else if(position == 1) {
            return "게시판";
        }
        return null;
    }
}
