package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public class HomeActivity extends AppCompatActivity {
    private ImageView homePetImg;
    private Bitmap bitmap;
    private Bitmap circlebitmap;
    private String imageName;
    /*ImageView mImageView;
    int mDegree = -30;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homePetImg = (ImageView) findViewById(R.id.homePetImg);

        homePetImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });


       /* mImageView = (ImageView) findViewById(R.id.imageView);
        mImageView.setImageBitmap(rotateImage(
                BitmapFactory.decodeResource(getResources(),
                        R.drawable.dog), mDegree));
        mImageView.setMinimumWidth(150);
        mImageView.setMaxWidth(150);
        mImageView.setMinimumHeight(150);
        mImageView.setMaxHeight(150);*/
    }

    /*public Bitmap rotateImage(Bitmap src, float degree) {

        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }*/

    private void dialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
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
            circlebitmap = getCircleBitmap(bitmap, 500);
            homePetImg.setImageBitmap(circlebitmap);

            Uri uri = data.getData();
            String realPath = getAbsolutePathFromUri(this, uri);
            File file = new File(realPath);
            imageName = file.getName();

        } else if(requestCode == 2) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                circlebitmap = getCircleBitmap(bitmap, 500);
                homePetImg.setImageBitmap(circlebitmap);
                Uri uri = data.getData();
                String realPath = getAbsolutePathFromUri(this, uri);
                File file = new File(realPath);
                imageName = file.getName();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void onClickCommunityBtn(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("page", "community");
        startActivity(intent);
    }

    public void onClickClinicBtn(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("page", "clinic");
        startActivity(intent);
    }

    public void onClickNoteBtn(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("page", "note");
        startActivity(intent);
    }

    public static String getAbsolutePathFromUri(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToNext();

        String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
        return path;
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
