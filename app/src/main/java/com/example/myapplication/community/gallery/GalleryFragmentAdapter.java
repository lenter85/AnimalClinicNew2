package com.example.myapplication.community.gallery;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.community.dto.Gallery;
import com.example.myapplication.network.GalleryNetwork;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragmentAdapter extends BaseAdapter {
    private ImageView imageView;
    private Context context;
    public GalleryFragmentAdapter(Context context) {
        this.context = context;
    }

    private List<Gallery> list = new ArrayList<>();

    public List<Gallery> getList() {
        return list;
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
        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.fragment_gallery_item,parent,false);
        }

        imageView = (ImageView)convertView.findViewById(R.id.thumbnail);

        Gallery gallery = (Gallery)getItem(position);
        GalleryNetwork.getGalleryImage(gallery.getGimage(), imageView);
        Log.i("mylog", "getGalleryImage() 실행");

        return convertView;
    }

    public void addItem(Gallery gallery) {
        list.add(gallery);
        this.notifyDataSetChanged();
    }

}
