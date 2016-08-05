package com.example.myapplication.diary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.diary.dto.Album;
import com.example.myapplication.network.AlbumNetwork;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumDetailFragment extends Fragment {
    private ImageView image;
    private TextView albumTitle;
    private TextView albumDate;

    public AlbumDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_album_detail, container, false);

        image = (ImageView) view.findViewById(R.id.aDetailImage);
        albumTitle = (TextView) view.findViewById(R.id.albumTitle);
        albumDate = (TextView) view.findViewById(R.id.albumDate);

        Album album = AlbumFragment.selectedItem;
        AlbumNetwork.getAlbumImage(album.getAimage(), image);
        albumTitle.setText(album.getAname());
        albumDate.setText(album.getAdate());


        // Inflate the layout for this fragment

        return view;
    }

}
