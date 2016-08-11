package com.example.myapplication.community.board;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.community.dto.Board;

import java.util.List;

public class BoardFragmentAdapter extends BaseAdapter{

    private Context context;
    private ImageView boardImage;
    private TextView boardTitle;

    public BoardFragmentAdapter(Context context){
        this.context = context;
    }

    //리스트 객체 추가
    private List<Board> list;
    public void setList(List<Board> list) {
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        context = parent.getContext();

        //아이템 뷰 생성
        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.fragment_board_item,parent,false);
        }

        //데이터 세팅
        boardImage = (ImageView) convertView.findViewById(R.id.boardImage);
        boardTitle = (TextView) convertView.findViewById(R.id.boardTitle);

        Board board = list.get(position);
        boardImage.setImageResource(board.getbImage());
        boardTitle.setText(board.getbTitle());

        //아이템 뷰 리턴
        return convertView;
    }
}