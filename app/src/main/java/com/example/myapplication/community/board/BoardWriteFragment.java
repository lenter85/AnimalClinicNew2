package com.example.myapplication.community.board;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;


public class BoardWriteFragment extends Fragment {
    private Button cancelBtn;
    private Button confirmBtn;
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

        confirmBtn = (Button)view.findViewById(R.id.confirmBtn);
        cancelBtn = (Button)view.findViewById(R.id.cancelBtn);
        boardTitle = (EditText)view.findViewById(R.id.boardTitle);
        boardContent = (EditText)view.findViewById(R.id.boardContent);



        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(boardTitle.getText().toString().matches("")){
                    dialog("제목을 입력하세요.");
                } else if(boardContent.getText().toString().matches("")){
                    dialog("내용을 입력하세요.");
                } else {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainer,new BoardFragment())
                            .commit();
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
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
