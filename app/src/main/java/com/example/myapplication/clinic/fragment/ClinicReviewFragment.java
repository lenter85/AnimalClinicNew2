package com.example.myapplication.clinic.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.clinic.dto.Review;
import com.example.myapplication.network.ClinicNetwork;
import com.example.myapplication.network.GalleryNetwork;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClinicReviewFragment extends Fragment {
    private ListView listView;
    private ReviewListViewAdapter reviewListViewAdapter;
    private List<Review> list = new ArrayList<>();

    private Button button;
    private boolean lastItem;
    private int pageNo = 1;


    public ClinicReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clinic_review, container, false);
        button = (Button) view.findViewById(R.id.button);

        //list.add(new Review());
        //list.add(new Review());

        //ListView에 어댑터 설정
        listView = (ListView) view.findViewById(R.id.listView);
        reviewListViewAdapter = new ReviewListViewAdapter();
        reviewListViewAdapter.setList(list);
        listView.setAdapter(reviewListViewAdapter);
        ClinicNetwork.getReviewData(pageNo, reviewListViewAdapter);
        Log.i("mylog","getReviewData() 실행");

        //ListView 이벤트 처리
       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Review review = list.get(position);
                Review review = (Review) reviewListViewAdapter.getItem(position);
                //imageViewLarge.setImageResource(review.getImageLarge());
            }
        });*/

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                    ClinicNetwork.getReviewData(pageNo, reviewListViewAdapter);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new WriteReviewFragment())
                        .commit();
            }
        });

        return view;
    }

    /*private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Review review = list.get(position);
            Review review = (Review) reviewListViewAdapter.getItem(position);
            //imageViewLarge.setImageResource(review.getImageLarge());
        }
    };*/

}
