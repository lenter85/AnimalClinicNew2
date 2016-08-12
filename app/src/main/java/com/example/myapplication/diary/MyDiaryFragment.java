package com.example.myapplication.diary;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.diary.dto.Diary;
import com.example.myapplication.diary.dto.Vaccination;
import com.example.myapplication.network.DiaryNetwork;
import com.google.android.gms.vision.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDiaryFragment extends Fragment {
    private ImageView album;
    private ImageView reservationList;
    private ImageView vaccination;
    private ImageView weight;
    private List<Vaccination> list = new ArrayList<>();
    private TextView myDname;
    private TextView myDbirth;
    private TextView myDgender;
    private ImageView myDimage;
    public static Diary my;
    public MyDiaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mydiary, container, false);

        my = DiaryFragment.myDiary;
        myDname = (TextView) view.findViewById(R.id.myDname);
        myDbirth = (TextView) view.findViewById(R.id.myDbirth);
        myDgender = (TextView) view.findViewById(R.id.myDgender);
        myDimage = (ImageView) view.findViewById(R.id.myDimage);

        myDname.setText(my.getDname());
        myDbirth.setText(my.getDbirth());
        if(my.getDgender().equals("m")) {
            myDgender.setText("수컷");
        } else if (my.getDgender().equals("f")) {
            myDgender.setText("암컷");
        }

        DiaryNetwork.getDiaryImage(my.getDimage(), myDimage);



        album = (ImageView) view.findViewById(R.id.album);
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new AlbumFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        Bitmap albumbitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.albumimg);
        albumbitmap = getCircleBitmap(albumbitmap, 220);
        album.setImageBitmap(albumbitmap);


        reservationList = (ImageView) view.findViewById(R.id.reservationList);
        reservationList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new ReservationListFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        Bitmap rbitmap = BitmapFactory.decodeResource(getResources(), R.drawable.reservation);
        rbitmap = getCircleBitmap(rbitmap, 300);
        reservationList.setImageBitmap(rbitmap);

        vaccination = (ImageView) view.findViewById(R.id.vaccination);
        vaccination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new VaccinationFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        Bitmap vbitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.vacci);
        vbitmap = getCircleBitmap(vbitmap, 250);
        vaccination.setImageBitmap(vbitmap);


        weight = (ImageView) view.findViewById(R.id.weight);
        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new WeightFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        Bitmap wbitmap = BitmapFactory.decodeResource(getResources(), R.drawable.weightimg);
        wbitmap = getCircleBitmap(wbitmap, 220);
        weight.setImageBitmap(wbitmap);

        return view;
    }


    private Bitmap getCircleBitmap(Bitmap scaleBitmapImage, int radiusDp) {
        int width = 0;
        int height = 0;
        int radius = (int)(radiusDp * getResources().getDisplayMetrics().density);
        if(scaleBitmapImage.getWidth()<scaleBitmapImage.getHeight()) {
            width = radius * 2;
            height =radius * 2 * scaleBitmapImage.getHeight()/scaleBitmapImage.getWidth();
        } else {
            width =radius * 2 * scaleBitmapImage.getWidth()/scaleBitmapImage.getHeight();
            height = radius * 2;
        }
        Bitmap targetBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(width/2, height/2, radius, Path.Direction.CCW);
        canvas.clipPath(path);

        canvas.drawBitmap(
                scaleBitmapImage,
                new Rect(0, 0, scaleBitmapImage.getWidth(), scaleBitmapImage.getHeight()),
                new Rect(0, 0, width, height),
                null
        );

        return targetBitmap;
    }

}
