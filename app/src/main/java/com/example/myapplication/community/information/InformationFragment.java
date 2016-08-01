package com.example.myapplication.community.information;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.community.dto.Information;
import com.example.myapplication.network.InfoNetwork;

import java.util.List;

public class InformationFragment extends Fragment {
    private ListView listView;
    private InformationAdapter informationAdapter;
    private List<Information> list;
    private InfoNetwork infoNetwork;

    public InformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_information, container, false);

        /*infoNetwork = new InfoNetwork();
        //list = infoNetwork.getInfoMain();*/

        /*listView = (ListView)view.findViewById(R.id.listView);

        informationAdapter = new InformationAdapter(getContext());
        informationAdapter.setList(list);
        listView.setAdapter(informationAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new InfoDetailFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });*/

        return view;
    }

}
