package com.example.myapplication.diary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.diary.dto.Diary;
import com.example.myapplication.network.DiaryNetwork;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiaryFragment extends Fragment {
    private ImageView imgAddDiary;
    private ListView listViewDiary;
    private DiaryAdapter diaryAdapter;
    private ImageView dimage;
    public static Diary myDiary;


    public DiaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view =  inflater.inflate(R.layout.fragment_diary, container, false);


        listViewDiary = (ListView) view.findViewById(R.id.listViewDiary);
        dimage = (ImageView) view.findViewById(R.id.imgDimage);
        diaryAdapter = new DiaryAdapter();
        diaryAdapter.setContext(getActivity());
        DiaryNetwork.getDiaryData(diaryAdapter, MainActivity.loginId);
        listViewDiary.setAdapter(diaryAdapter);

        listViewDiary.setOnItemClickListener(onItemClickListener);



        imgAddDiary = (ImageView) view.findViewById(R.id.imgAddDiary);
        imgAddDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new RegisterMyDiaryFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            myDiary = (Diary) diaryAdapter.getItem(position);
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new MyDiaryFragment())
                    .addToBackStack(null)
                    .commit();
        }
    };


}
