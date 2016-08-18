package com.example.myapplication.community.board;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.network.BoardNetwork;
import com.example.myapplication.network.MainNetwork;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardDetailFragment extends Fragment {

    public BoardDetailFragment() {
        // Required empty public constructor
    }
    private TextView boardTitle;
    private TextView boardContent;
    private TextView boardDate;
    private TextView mid;
    private ImageView boardImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board_detail, container, false);

        boardTitle = (TextView)view.findViewById(R.id.boardTitle);
        boardContent = (TextView)view.findViewById(R.id.boardContent);
        boardDate = (TextView)view.findViewById(R.id.boardDate);
        mid = (TextView)view.findViewById(R.id.mid);
        boardImage = (ImageView) view.findViewById(R.id.boardImage);

        boardTitle.setText(BoardFragment.selectedBoard.getbTitle());
        boardContent.setText(BoardFragment.selectedBoard.getbContent());
        boardDate.setText(BoardFragment.selectedBoard.getbDate());
        mid.setText(BoardFragment.selectedBoard.getmId());

        BoardNetwork boardNetwork = new BoardNetwork();
        boardNetwork.getMemberImage(BoardFragment.selectedBoard.getmId(), boardImage);

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add("수정");
        menu.add("삭제");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.toString().equals("수정")){
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, new BoardWriteFragment())
                            .commit();
                    return true;
                }
            });
        } else if(item.toString().equals("삭제")){
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return false;
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}
