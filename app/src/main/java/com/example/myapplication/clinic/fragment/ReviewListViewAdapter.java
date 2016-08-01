package com.example.myapplication.clinic.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.clinic.dto.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-07-08.
 */
public class ReviewListViewAdapter extends BaseAdapter {

    private List<Review> list = new ArrayList<Review>();

    public void setList(List<Review> list) {
        this.list = list;
    }


    //private Context context;

    //생성자 주입
    /*public ReviewListViewAdapter(Context context) {
        this.context = context;
    }*/

    public ReviewListViewAdapter(){}

    @Override
    public int getCount() {  //데이터의 수를 리턴한다.

        return list.size();
    }

    @Override
    public Object getItem(int position) {  //list안에 들어있는 실제 데이터. index가 매개변수로 주어지고 해당 객체를 리턴
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {  //데이터의 아이디가 무엇이냐?(데이터의 식별값이 무엇이냐?), 데이터가 id가 없다면 그냥 index(여기선 position)을 리턴.
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //준식이 어댑터에 있던 코드
        final Context context = parent.getContext();


        //ItemView를 새로 만들어야 될 경우
        if(convertView==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView =layoutInflater.inflate(R.layout.clinic_review_item, parent, false);
        }

        //데이터 셋팅
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewReviewImg);
        ImageView imageViewLarge = (ImageView) convertView.findViewById(R.id.imageViewLarge);



        //Review review = list.get(position);
        //dto인 review에 데이터 집어넣기 작업해야 한다.



        //ItemView 리턴
        return convertView;
    }
}
