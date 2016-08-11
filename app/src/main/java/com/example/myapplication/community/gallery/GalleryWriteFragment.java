package com.example.myapplication.community.gallery;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.community.CommunityFragment;
import com.example.myapplication.community.dto.Gallery;
import com.example.myapplication.network.GalleryNetwork;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryWriteFragment extends Fragment {
    private ImageView imageView;
    private Button cancelBtn;
    private Button writeBtn;
    private Bitmap bitmap;
    private EditText galleryTitle;
    private EditText galleryContent;
    private Bitmap smallBitmap;

    private List<Gallery> list;

    private Gallery gallery = new Gallery();

    public GalleryWriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery_write, container, false);

        galleryTitle = (EditText)view.findViewById(R.id.galleryTitle);

        galleryContent = (EditText)view.findViewById(R.id.galleryContent);

        imageView = (ImageView)view.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });

        //글쓰기 확인 버튼
        writeBtn = (Button)view.findViewById(R.id.confirmBtn);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gallery.setGtitle(galleryTitle.getText().toString());
                gallery.setGcontent(galleryContent.getText().toString());
                gallery.setMid("test");

                GalleryNetwork.writeGallery(gallery, bitmap, smallBitmap);

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new CommunityFragment())
                        .commit();
            }
        });

        //취소 버튼
        cancelBtn = (Button)view.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }


    //이미지 획득 방식 선택
    private void dialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog
                .setTitle("선택")
                .setNegativeButton("카메라", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 1);
                    }
                });
        dialog
                .setPositiveButton("갤러리", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, 2);
                    }
                });
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                bitmap = (Bitmap) data.getExtras().get("data");
                smallBitmap = getResizedBitmap(bitmap,300);
                imageView.setImageBitmap(bitmap);

                //파일 이름 얻기
                Uri uri = data.getData();
                String realPath = getAbsolutePathFromUri(getActivity(),uri);
                File file = new File(realPath);
                String imageName = file.getName();
                gallery.setGimage("small_"+imageName);
                gallery.setGimagelarge(imageName);

                Log.i("mylog","갤러리에서 추가한 파일 이름 : " + imageName);

            } else if (requestCode == 2) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    smallBitmap = getResizedBitmap(bitmap,300);
                    imageView.setImageBitmap(bitmap);

                    //파일 이름 얻기
                    Uri uri = data.getData();
                    String realPath = getAbsolutePathFromUri(getActivity(),uri);
                    File file = new File(realPath);
                    String imageName = file.getName();
                    gallery.setGimage("small_"+imageName);
                    gallery.setGimagelarge(imageName);

                    Log.i("mylog","갤러리에서 추가한 파일 이름 : " + imageName);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //Bitmap 리사이징
    public static Bitmap getResizedBitmap(Bitmap bitmap, int maxSize) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    //uri에 대한 절대경로 구하기
    public static String getAbsolutePathFromUri(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();

        String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
        Log.d("path", path);

        return path;
    }

}
