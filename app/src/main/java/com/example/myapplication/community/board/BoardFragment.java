package com.example.myapplication.community.board;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.community.dto.Board;
import com.example.myapplication.network.BoardNetwork;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {

    private FloatingActionButton fab;
    private ListView boardListView;
    private BoardFragmentAdapter boardFragmentAdapter;
    private List<Board> list;
    public static Board selectedBoard = new Board();

    private boolean lastItem;
    private int pageNo = 1;

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
                if(MainActivity.loginStatus){
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, new BoardWriteFragment())
                            .addToBackStack(null)
                            .commit();
                } else {
                    dialog("로그인 후 사용 가능합니다.");
                }
            }
        });

        /*list = new ArrayList<>();
        for(int i=1; i<=10; i++){
            Board board = new Board();
            board.setbTitle("게시판 제목"+i);
            board.setbContent("게시판 내용"+i);

            list.add(board);
        }*/

        boardListView = (ListView)view.findViewById(R.id.boardListView);

        //어댑터 생성 및 세팅
        boardFragmentAdapter = new BoardFragmentAdapter(getContext());
        //boardFragmentAdapter.setList(list);
        boardListView.setAdapter(boardFragmentAdapter);
        BoardNetwork.getBoard(pageNo, boardFragmentAdapter);
        Log.i("mylog","getBoard() 실행");

        //아이템 클릭 이벤트 처리
        boardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedBoard = (Board)boardFragmentAdapter.getItem(position);
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer,new BoardDetailFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        boardListView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                    BoardNetwork.getBoard(pageNo, boardFragmentAdapter);
                    Log.i("mylog","getBoard() 실행2");
                }
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
