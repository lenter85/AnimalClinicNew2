package com.example.myapplication.diary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.diary.dto.Album;
import com.example.myapplication.network.AlbumNetwork;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-07-11.
 */
public class AlbumAdapter extends BaseAdapter {
    private ImageView imgViewPhoto;
    private Context context;
    private List<Album> list = new ArrayList<>();

    public void setContext(Context context) {
        this.context = context;
    }

    public void setList(List<Album> list) {
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
        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.item_album, parent, false);
        }

        imgViewPhoto = (ImageView) convertView.findViewById(R.id.imgViewPhoto);


        Album album = list.get(position);
        AlbumNetwork.getAlbumImage(album.getAimage(), imgViewPhoto);
        ///이미지 받아오는 거 바꿔야 한다
        //imgViewPhoto.setImageResource(R.drawable.smile);

        return convertView;
    }

    public void addItem(Album album) {
        list.add(album);
        this.notifyDataSetChanged();
    }
}
