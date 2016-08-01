package com.example.myapplication.community.board;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.community.dto.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {

    private FloatingActionButton fab;
    private ListView boardListView;
    private BoardFragmentAdapter boardFragmentAdapter;
    private List<Board> list;

    public BoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_board, container, false);

        fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new BoardWriteFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        list = new ArrayList<>();
        for(int i=1; i<=10; i++){
            Board board = new Board();
            board.setBoardTitle("게시판 제목"+i);
            board.setBoardContent("게시판 내용"+i);
            board.getBoardImage();

            list.add(board);
        }

        boardListView = (ListView)view.findViewById(R.id.boardListView);

        //어댑터 생성 및 세팅
        boardFragmentAdapter = new BoardFragmentAdapter(getContext());
        boardFragmentAdapter.setList(list);

        boardListView.setAdapter(boardFragmentAdapter);


        //아이템 클릭 이벤트 처리
        boardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer,new BoardDetailFragment())
                        .commit();
            }
        });

        return view;
    }
}
