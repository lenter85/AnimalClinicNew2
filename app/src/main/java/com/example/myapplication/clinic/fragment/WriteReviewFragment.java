package com.example.myapplication.clinic.fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.clinic.dto.Review;
import com.example.myapplication.network.NetworkSetting;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class WriteReviewFragment extends Fragment {
    private Button buttonCancel;
    private Button btnRegister;
    ImageView imageViewReviewImg;
    private Bitmap bitmap;
    private RatingBar ratingBar;
    private EditText editTextContent;

    public WriteReviewFragment() {
        // Required empty public constructor
    }

    Review review = new Review();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_write_review, container, false);
        editTextContent = (EditText) view.findViewById(R.id.editTextContent);
        imageViewReviewImg = (ImageView) view.findViewById(R.id.imageViewReviewImg);

        imageViewReviewImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        /*ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.setRating(rating);
            }
        });*/

        buttonCancel = (Button) view.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ClinicDetailInformationFragment())
                        .commit();
            }
        });


        btnRegister = (Button) view.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Review review = new Review();
                review.setRuserid(MainActivity.loginId);
                review.setRcontent(editTextContent.getText().toString());
                review.setRscore(ratingBar.getNumStars());
                sendReview(review, bitmap);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ClinicDetailInformationFragment())
                        .commit();
            }
        });

        //String filepath = "C:\KosaCourse\AndroidStudioProjects\AnimalClinic\app\src\main\res\drawable-nodpi\woman.png";

        /*btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("mylog", "클릭");
                sendFoodReview(review, bitmap);
            }
        });*/


        return view;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("mylog", "Result 시작");
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 1) {
                try {
                    bitmap 	= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());

                    imageViewReviewImg.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void sendReview(final Review review, final Bitmap bitmap) {
        new AsyncTask<Void, Integer, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String result = "fail";
                try {
                    // 데이터 구분 문자
                    String boundary = "----" + System.currentTimeMillis();

                    // 데이터 경계선
                    String delimiter = "\r\n--" + boundary + "\r\n";    //규약

                    // 커넥션 생성 및 설정
                    URL url = new URL(NetworkSetting.baseUrl2 + "registerreivew");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                    conn.connect();

                    /*PrintWriter pw = new PrintWriter(conn.getOutputStream());
                    pw.println("json="+strJson);
                    pw.close();*/

                    //연결하기
                    //int resultCode = conn.getResponseCode();

                    //출력 스트림 얻기
                    OutputStream out = conn.getOutputStream();

                    //문자열 데이터 전송
                    StringBuffer postDataBuilder = new StringBuffer();
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("rscore", String.valueOf(review.getRscore())));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("ruserid", String.valueOf(review.getRuserid())));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("rcontent", review.getRcontent()));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setValue("rclinicid", review.getRclinicid()));
                    postDataBuilder.append(delimiter);
                    postDataBuilder.append(setFile("clinicImage", "filename.png"));
                    out.write(postDataBuilder.toString().getBytes()); //첫번째 전송





                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, bos);
                    byte[] bitmapdata = bos.toByteArray();

                    out.write(bitmapdata);
                    bos.close();


                    //종료 구분자 넣기  //세번째 전송
                    out.write(("\r\n--" + boundary + "--\r\n").getBytes());  //규약

                    //출력스트림 닫기
                    out.flush();
                    out.close();

                    bos.close();


                    //응답 코드 확인
                   if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        result = "success";
                    }

                    //연결 끊기
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
             if (result.equals("success")) {
                   Log.i("mylog", "성공");

                } else {
                 Log.i("mylog","실패");
             }
            }
        }.execute();
    }

    public static String setValue(String key, String value) {
        String str = "Content-Disposition: form-data; name=\"" + key + "\"";
        str += "\r\n\r\n";
        str += value;
        return str;
    }

    public static String setFile(String key, String fileName) {
        String str = "Content-Disposition: form-data; name=\"" + key + "\"; filename=\"" + fileName + "\"";
        str += "\r\n";
        str += "Content-Type: image/png";
        str += "\r\n\r\n";
        return str;
    }

}
