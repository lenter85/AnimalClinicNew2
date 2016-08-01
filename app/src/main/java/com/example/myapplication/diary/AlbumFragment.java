package com.example.myapplication.diary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.myapplication.R;
import com.example.myapplication.diary.dto.Album;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment {
    private GridView gridViewAlbum;
    private List<Album> list;
    private AlbumAdapter albumAdapter;

    public AlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_album, container, false);
        gridViewAlbum = (GridView) view.findViewById(R.id.gridViewAlbum);
        gridViewAlbum.setNumColumns(3);

        list = new ArrayList<>();

        Album album1 = new Album();
        album1.setPicture(R.drawable.dog04_small);
        Album album2 = new Album();
        album2.setPicture(R.drawable.dog05);
        list.add(album1);
        list.add(album2);



        albumAdapter = new AlbumAdapter();
        albumAdapter.setContext(getActivity());
        albumAdapter.setList(list);
        gridViewAlbum.setAdapter(albumAdapter);

        return view;
    }

}
