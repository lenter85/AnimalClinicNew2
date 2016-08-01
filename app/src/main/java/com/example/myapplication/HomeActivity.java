package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class HomeActivity extends AppCompatActivity {
    /*ImageView mImageView;
    int mDegree = -30;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);





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

}
