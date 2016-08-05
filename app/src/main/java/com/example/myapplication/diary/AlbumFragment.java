package com.example.myapplication.diary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.myapplication.R;
import com.example.myapplication.diary.dto.Album;
import com.example.myapplication.network.AlbumNetwork;

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

        //list = new ArrayList<>();

        albumAdapter = new AlbumAdapter();
        albumAdapter.setContext(getActivity());
        AlbumNetwork.getAlbumData(1, albumAdapter);
        gridViewAlbum.setAdapter(albumAdapter);

        return view;
    }

}
