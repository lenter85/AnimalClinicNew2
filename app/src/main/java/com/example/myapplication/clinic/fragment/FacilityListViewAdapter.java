package com.example.myapplication.clinic.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.clinic.dto.FacilityPicture;

import java.util.List;

/**
 * Created by Administrator on 2016-07-07.
 */
public class FacilityListViewAdapter extends BaseAdapter {
    private List<FacilityPicture> list;

    public void setList(List<FacilityPicture> list) {
        this.list = list;
    }


    private Context context;

    //생성자 주입
    public FacilityListViewAdapter(Context context) {
        this.context = context;
    }

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

        //ItemView를 새로 만들어야 될 경우
        if(convertView==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView =layoutInflater.inflate(R.layout.facility_item_view, parent, false);
        }

        //데이터 셋팅
        ImageView imageView1 = (ImageView) convertView.findViewById(R.id.imageView1);
        ImageView imageView2 = (ImageView) convertView.findViewById(R.id.imageViewLarge);

        FacilityPicture facilityPicture = list.get(position);
        imageView1.setImageResource(facilityPicture.getImage1());
        imageView2.setImageResource(facilityPicture.getImage2());


        //ItemView 리턴
        return convertView;
    }
}
