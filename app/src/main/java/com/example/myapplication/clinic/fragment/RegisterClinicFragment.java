package com.example.myapplication.clinic.fragment;


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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.clinic.dto.ClinicRegister;
import com.example.myapplication.network.ClinicNetwork;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterClinicFragment extends Fragment {

    private ImageView imageViewAdd;
    private ImageView imageViewSmall1;
    private ImageView imageViewSmall2;
    private ImageView imageViewSmall3;
    private ImageView imageViewSmall4;


    private EditText editText1;
    private EditText editText2;
    private EditText editText3;

    private TextView textView;
    private Button btnRegister;
    private Button btnCancel;

    private CheckBox checkBox;
    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;
    private Spinner spinner4;

    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioGroup radioGroup;

    private String openTime;
    private String closeTime;
    private String lunchStart;
    private String lunchEnd;

    private Bitmap bitmap1=null;
    private Bitmap bitmap2=null;
    private Bitmap bitmap3=null;
    private Bitmap bitmap4=null;

    private String imageName1=null;
    private String imageName2=null;
    private String imageName3=null;
    private String imageName4=null;
    private Boolean beauty;
    private int workingDay=1;

    public static Double latitude;
    public static Double longitude;


    public RegisterClinicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_register_clinic, container, false);

        imageViewAdd = (ImageView) view.findViewById(R.id.imageViewAdd);
        imageViewSmall1 = (ImageView) view.findViewById(R.id.imageViewSmall1);
        imageViewSmall2 = (ImageView) view.findViewById(R.id.imageViewSmall2);
        imageViewSmall3 = (ImageView) view.findViewById(R.id.imageViewSmall3);
        imageViewSmall4 = (ImageView) view.findViewById(R.id.imageViewSmall4);

        editText1 = (EditText) view.findViewById(R.id.editText1);
        editText2 = (EditText) view.findViewById(R.id.editText2);
        editText3 = (EditText) view.findViewById(R.id.editText3);

        textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(MainActivity.clinicLocation);
        btnRegister = (Button) view.findViewById(R.id.btnRegister);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);

        checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        spinner1 = (Spinner) view.findViewById(R.id.spinner1);
        spinner2 = (Spinner) view.findViewById(R.id.spinner2);
        spinner3 = (Spinner) view.findViewById(R.id.spinner3);
        spinner4 = (Spinner) view.findViewById(R.id.spinner4);

        radioButton1 = (RadioButton) view.findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) view.findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) view.findViewById(R.id.radioButton3);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);

        textView.setOnClickListener(onClickListener);


        //오픈,마감시간 셋팅
        final ArrayList<String> arraylist = new ArrayList<String>();

        arraylist.add("00:00");
        arraylist.add("01:00");
        arraylist.add("02:00");
        arraylist.add("03:00");
        arraylist.add("04:00");
        arraylist.add("05:00");
        arraylist.add("06:00");
        arraylist.add("07:00");
        arraylist.add("08:00");
        arraylist.add("09:00");
        arraylist.add("10:00");
        arraylist.add("11:00");
        arraylist.add("12:00");
        arraylist.add("13:00");
        arraylist.add("14:00");
        arraylist.add("15:00");
        arraylist.add("16:00");
        arraylist.add("17:00");
        arraylist.add("18:00");
        arraylist.add("19:00");
        arraylist.add("20:00");
        arraylist.add("21:00");
        arraylist.add("22:00");
        arraylist.add("23:00");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist);
        //스피너 속성

        spinner1.setPrompt("open"); // 스피너 제목
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //진료 시작시간 셋팅
                openTime = arraylist.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinner2.setPrompt("close"); // 스피너 제목
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //진료 종료시간 셋팅
                closeTime = arraylist.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final ArrayList<String> arraylist2 = new ArrayList<String>();


        arraylist2.add("10:00");
        arraylist2.add("11:00");
        arraylist2.add("12:00");
        arraylist2.add("13:00");
        arraylist2.add("14:00");


        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist2);

        //스피너 속성

        spinner3.setPrompt("start"); // 스피너 제목
        spinner3.setAdapter(adapter2);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //점심시간 시작 셋팅
                lunchStart = arraylist.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinner4.setPrompt("end"); // 스피너 제목
        spinner4.setAdapter(adapter2);
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //점심 시간 종료 셋팅
                lunchEnd = arraylist.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });


        imageViewSmall1.setOnClickListener(imageClickListener);
        imageViewSmall2.setOnClickListener(imageClickListener);
        imageViewSmall3.setOnClickListener(imageClickListener);
        imageViewSmall4.setOnClickListener(imageClickListener);

        checkBox.setOnClickListener(checkBoxClickListener);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(radioButton1.isChecked()) {
                    workingDay = 1;
                } else if (radioButton2.isChecked()) {
                    workingDay = 2;
                } else {
                    workingDay = 3;
                }
            }
        });


        btnRegister.setOnClickListener(btnRegisterOnClickListener);



        return view;
    }

    public View.OnClickListener checkBoxClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(checkBox.isChecked()) {
                beauty = true;
            } else {
                beauty = false;
            }
        }
    };


    public View.OnClickListener btnRegisterOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            ClinicRegister clinicRegister = new ClinicRegister();
            clinicRegister.setCid("testID");
            clinicRegister.setClocation(textView.getText().toString());
            clinicRegister.setCname(editText1.getText().toString());
            clinicRegister.setCintroduction(editText3.getText().toString());
            clinicRegister.setCphone(editText2.getText().toString());
            clinicRegister.setCimage1(imageName1);
            clinicRegister.setCimage2(imageName2);
            clinicRegister.setCimage3(imageName3);
            clinicRegister.setCimage4(imageName4);

            clinicRegister.setCbeauty(beauty);
            clinicRegister.setCweekend(workingDay);
            clinicRegister.setCopentime(spinner1.getSelectedItem().toString());
            clinicRegister.setCclosetime(spinner2.getSelectedItem().toString());
            clinicRegister.setClatitude(latitude);
            clinicRegister.setClongitude(longitude);
            clinicRegister.setClunchstart(spinner3.getSelectedItem().toString());
            clinicRegister.setClunchend(spinner4.getSelectedItem().toString());

            Log.i("mylog", "spinenr3 : " + spinner3.getSelectedItem().toString());
            Log.i("mylog", "spinenr4 : " + spinner4.getSelectedItem().toString());
            Log.i("mylog", "객체를 한번에 String으로 바꾸면? : " + clinicRegister.toString());
            Log.i("mylog", "위도 경도 받나? : " + latitude);

            ClinicNetwork clinicNetwork = new ClinicNetwork();
            clinicNetwork.clinicRegister(clinicRegister, bitmap1, bitmap2, bitmap3, bitmap4);

        }
    };


    public View.OnClickListener imageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v==imageViewSmall1) {
                imageViewSmall1.setImageResource(R.drawable.add_small);
                bitmap1 = null;
                imageName1=null;
            } else if (v==imageViewSmall2) {
                imageViewSmall2.setImageResource(R.drawable.add_small);
                bitmap2 = null;
                imageName2=null;
            } else if (v==imageViewSmall3) {
                imageViewSmall3.setImageResource(R.drawable.add_small);
                bitmap3 = null;
                imageName3=null;
            } else if (v==imageViewSmall4) {
                imageViewSmall4.setImageResource(R.drawable.add_small);
                bitmap4 = null;
                imageName4=null;
            }
        }
    };



    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new AddressCheckFragment())
                    .commit();
        }
    };


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("mylog", "Result 시작");
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 1) {

                if(bitmap1 == null) {
                    try {
                        bitmap1 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                        Uri uri = data.getData();
                        //String bitmap1Path = uri.getPath();
                        String realPath = getAbsolutePathFromUri(getActivity(), uri);
                        //Log.i("mylog", "bitmap1 경로 : " + bitmap1Path);
                        Log.i("mylog", "절대경로 : " + realPath);


                        /*File f = new File(bitmap1Path);
                        String imageName = f.getName();
                        Log.i("mylog", "이미지 이름은 : " + imageName);*/

                        File f = new File(realPath);
                        imageName1 = f.getName();
                        Log.i("mylog", "이미지 이름은 : " + imageName1);

                        imageViewSmall1.setImageBitmap(bitmap1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (bitmap2 == null) {
                    try {
                        bitmap2 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                        Uri uri = data.getData();

                        String realPath = getAbsolutePathFromUri(getActivity(), uri);
                        File f = new File(realPath);
                        imageName2 = f.getName();
                        imageViewSmall2.setImageBitmap(bitmap2);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (bitmap3 == null) {
                    try {
                        bitmap3 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                        Uri uri = data.getData();

                        String realPath = getAbsolutePathFromUri(getActivity(), uri);
                        File f = new File(realPath);
                        imageName3 = f.getName();
                        imageViewSmall3.setImageBitmap(bitmap3);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }  else if (bitmap4 == null) {
                    try {
                        bitmap4 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                        Uri uri = data.getData();

                        String realPath = getAbsolutePathFromUri(getActivity(), uri);
                        File f = new File(realPath);
                        imageName4 = f.getName();

                        imageViewSmall4.setImageBitmap(bitmap4);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }



            }
        }
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

