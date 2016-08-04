package com.example.myapplication.diary;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.myapplication.R;
import com.example.myapplication.diary.dto.Diary;
import com.example.myapplication.network.DiaryNetwork;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterMyDiaryFragment extends Fragment {
    private Button btnAddDiary;
    private Button btnCanceld;
    private Button btnPickPicture;
    private ImageView imgRdimage;
    private EditText txtRdname;
    private EditText txtRdbirth;
    private EditText txtRdage;
    private RadioButton maleButton;
    private RadioButton femaleButton;
    private String filePath;
    private RadioGroup rGroup;
    private Diary diary = new Diary();

    public RegisterMyDiaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_mydiary, container, false);

        imgRdimage = (ImageView) view.findViewById(R.id.imgRdimage);
        txtRdname = (EditText) view.findViewById(R.id.txtRdname);
        txtRdbirth = (EditText) view.findViewById(R.id.txtRdbirth);
        txtRdage = (EditText) view.findViewById(R.id.txtRdage);
        rGroup = (RadioGroup) view.findViewById(R.id.rgroup);
        maleButton = (RadioButton) view.findViewById(R.id.maleButton);
        femaleButton = (RadioButton) view.findViewById(R.id.femaleButton);

        btnAddDiary = (Button) view.findViewById(R.id.btnAddDiary);




        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(maleButton.isChecked()) {
                    diary.setDgender("m");
                }
                if(femaleButton.isChecked()) {
                    diary.setDgender("f");
                }
            }
        });


        btnAddDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                diary.setDname(txtRdname.getText().toString());
                diary.setDbirth(txtRdbirth.getText().toString());

                DiaryNetwork.sendDiary(diary, filePath);

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new DiaryFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnCanceld = (Button) view.findViewById(R.id.btnCanceld);
        btnCanceld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new DiaryFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnPickPicture = (Button) view.findViewById(R.id.btnPickPicture);
        btnPickPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);

            }
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 1) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    Uri uri = data.getData();
                    filePath = getAbsolutePathFromUri(getActivity(), uri);


                    imgRdimage.setImageBitmap(bitmap);
                    imgRdimage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getAbsolutePathFromUri(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();

        String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
        Log.d("path", path);

        return path;
    }
}
