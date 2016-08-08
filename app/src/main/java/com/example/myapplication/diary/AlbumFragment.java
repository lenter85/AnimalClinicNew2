package com.example.myapplication.diary;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    private FloatingActionButton fab2;
    public static Album selectedItem = new Album();

    public AlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
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

        gridViewAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = (Album) albumAdapter.getItem(position);
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainer, new AlbumDetailFragment())
                        .commit();
            }
        });

        fab2 = (FloatingActionButton) view.findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainer, new RegisterAlbumFragment())
                        .commit();
            }
        });
        return view;
    }

}
