package com.example.myapplication.community.board;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;


public class BoardWriteFragment extends Fragment {
    private Button cancelBtn;
    private EditText boardTitle;
    private EditText boardContent;

    public BoardWriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board_write, container, false);

        cancelBtn = (Button)view.findViewById(R.id.cancelBtn);
        boardTitle = (EditText)view.findViewById(R.id.boardTitle);
        boardContent = (EditText)view.findViewById(R.id.boardContent);



        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

}
