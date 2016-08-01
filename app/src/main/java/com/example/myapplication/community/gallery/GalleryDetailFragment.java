package com.example.myapplication.community.gallery;


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

import com.example.myapplication.R;
import com.example.myapplication.community.dto.Gallery;
import com.example.myapplication.network.GalleryNetwork;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryDetailFragment extends Fragment {
    private ImageView imageViewLarge;
    private ImageView imageView;
    private TextView galleryTitle;
    private TextView galleryContent;
    private TextView id;
    private TextView galleryDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery_detail, container, false);

        setHasOptionsMenu(true);

        galleryDate = (TextView)view.findViewById(R.id.galleryDate);
        imageView = (ImageView)view.findViewById(R.id.imageView);
        imageViewLarge = (ImageView)view.findViewById(R.id.imageViewLarge);
        galleryTitle = (TextView)view.findViewById(R.id.galleryTitle);
        galleryContent = (TextView)view.findViewById(R.id.galleryContent);
        id = (TextView)view.findViewById(R.id.id);

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
        if(item.equals("수정")){
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, new GalleryWriteFragment())
                            .commit();
                    return false;
                }
            });
        } else if(item.equals("삭제")){
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return false;
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    public void setDetail(Gallery gallery){
        id.setText(gallery.getMid());
        galleryTitle.setText(gallery.getGtitle());
        galleryContent.setText(gallery.getGcontent());
        GalleryNetwork.getGalleryImage(gallery.getGimagelarge(),imageViewLarge);
        galleryDate.setText(gallery.getGdate());

    }
}
