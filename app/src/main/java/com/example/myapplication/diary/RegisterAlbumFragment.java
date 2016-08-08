package com.example.myapplication.diary;


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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.diary.dto.Album;
import com.example.myapplication.network.AlbumNetwork;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterAlbumFragment extends Fragment {
    private Button btnRegister;
    private Button btnCancel;
    private EditText aTitle;
    private EditText aContent;
    private String adate;
    private ImageView imageView;
    private Bitmap bitmap;
    private Bitmap smallBitmap;
    private String imageName;

    public RegisterAlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_register_album, container, false);

        btnRegister = (Button) view.findViewById(R.id.btnaRegister);
        btnCancel = (Button) view.findViewById(R.id.btnaCancel);
        aTitle = (EditText) view.findViewById(R.id.aTitle);
        aContent = (EditText) view.findViewById(R.id.aContent);
        imageView = (ImageView) view.findViewById(R.id.imgaView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Album album = new Album();
                album.setAname(aTitle.getText().toString());
                album.setAcontent(aContent.getText().toString());
                album.setAimage(imageName);
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                adate = sdf.format(date);
                album.setAdate(adate);
                AlbumNetwork.sendAlbum(album, smallBitmap);

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainer, new AlbumFragment())
                        .commit();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainer, new AlbumFragment())
                        .commit();
            }
        });
        return view;

    }

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
        if(requestCode == 1) {
            bitmap = (Bitmap) data.getExtras().get("data");
            smallBitmap = getResizedBitmap(bitmap, 300);
            imageView.setImageBitmap(bitmap);

            Uri uri = data.getData();
            String realPath = getAbsolutePathFromUri(getActivity(), uri);
            File file = new File(realPath);
            imageName = file.getName();

        } else if(requestCode == 2) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                smallBitmap = getResizedBitmap(bitmap, 300);
                imageView.setImageBitmap(bitmap);
                Uri uri = data.getData();
                String realPath = getAbsolutePathFromUri(getActivity(), uri);
                File file = new File(realPath);
                imageName = file.getName();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

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

    public static String getAbsolutePathFromUri(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToNext();

        String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
        return path;
    }
}
