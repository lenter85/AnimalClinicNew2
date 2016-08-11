package com.example.myapplication.community.gallery;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.community.dto.Gallery;
import com.example.myapplication.network.GalleryNetwork;

public class GalleryFragment extends Fragment {
    private GridView gridView;
    private GalleryFragmentAdapter galleryFragmentAdapter;
    private FloatingActionButton fab;
    public static Gallery selectedGallery = new Gallery();

    private boolean lastItem;
    private int pageNo = 1;

    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        fab = (FloatingActionButton)view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.loginStatus){
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, new GalleryWriteFragment())
                            .addToBackStack(null)
                            .commit();
                } else {
                    dialog("로그인 후 사용 가능합니다.");
                }
            }
        });

        gridView = (GridView)view.findViewById(R.id.gridView);

        galleryFragmentAdapter = new GalleryFragmentAdapter(getContext());
        gridView.setAdapter(galleryFragmentAdapter);
        GalleryNetwork.getGalleryData(pageNo, galleryFragmentAdapter);
        Log.i("mylog","getGalleryData() 실행2");


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedGallery = (Gallery)galleryFragmentAdapter.getItem(position);
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new GalleryDetailFragment())
                        .addToBackStack(null)
                        .commit();
                GalleryNetwork.getGalleryData(pageNo, galleryFragmentAdapter);
            }
        });

        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(totalItemCount>0 && (firstVisibleItem+visibleItemCount>=totalItemCount-1)) {
                    lastItem = true;
                } else {
                    lastItem = false;
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(lastItem && scrollState==AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    pageNo++;
                    GalleryNetwork.getGalleryData(pageNo, galleryFragmentAdapter);
                }
            }
        });

        return view;
    }

    public void dialog(String message){
        //다이얼로그
        new AlertDialog.Builder(getContext())
                .setTitle("로그인 필요")
                .setMessage(message)
                .setIcon(R.drawable.check)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

}
