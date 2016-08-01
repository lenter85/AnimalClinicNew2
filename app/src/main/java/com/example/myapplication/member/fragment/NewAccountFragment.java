package com.example.myapplication.member.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.member.dao.CheckUser;

public class NewAccountFragment extends Fragment {

    CheckUser checkUser = new CheckUser();

    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private Button btnSend;

    private TextView name;
    private TextView phone;
    private TextView email;
    private TextView password;

    public NewAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_new_account, container, false);

        btnSend = (Button)view.findViewById(R.id.btnSend);

        checkBox1 = (CheckBox)view.findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox)view.findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox)view.findViewById(R.id.checkBox3);

        name = (EditText)view.findViewById(R.id.name);
        email = (EditText)view.findViewById(R.id.login_email);
        phone = (EditText)view.findViewById(R.id.phone);
        password = (EditText)view.findViewById(R.id.login_password);


        btnSend.setEnabled(false);

        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox1.isChecked() == true){
                    checkBox1.setChecked(true);
                    checkBox2.setChecked(true);
                    checkBox3.setChecked(true);
                    btnSend.setClickable(true);
                    btnSend.setBackgroundColor(Color.GREEN);
                    btnSend.setEnabled(true);
                }else{
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    btnSend.setBackgroundColor(Color.LTGRAY);
                }
            }
        });

        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((checkBox2.isChecked() == true && checkBox3.isChecked() == true)){
                    btnSend.setBackgroundColor(Color.GREEN);
                    btnSend.setEnabled(true);
            }else{ btnSend.setBackgroundColor(Color.LTGRAY);
                }
            }
        });

        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((checkBox2.isChecked() == true && checkBox3.isChecked() == true)){
                    btnSend.setBackgroundColor(Color.GREEN);
                    btnSend.setEnabled(true);
                }else{ btnSend.setBackgroundColor(Color.LTGRAY);
                }
            }
        });

/*
        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((checkBox2.isChecked() == false && checkBox3.isChecked() == true)){
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(true);
                    btnSend.setClickable(false);
                    btnSend.setBackgroundColor(Color.LTGRAY);
                    btnSend.setEnabled(false);

                }else{ }
            }
        });

        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((checkBox3.isChecked() == false && checkBox2.isChecked() == true)){
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(true);
                    checkBox3.setChecked(false);
                    btnSend.setClickable(false);
                    btnSend.setBackgroundColor(Color.LTGRAY);
                    btnSend.setEnabled(false);

                }else{ }
            }
        });
*/




////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /*btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean joinOk = false;
                    if(!checkUser.checkBlank(name.getText().toString())){
                        String message = "유저명을 입력하세요.";
                        dialog(message);
                        //dialog();
                } else if(!checkUser.checkName(name.getText().toString())) {
                        String message = "특수문자를 사용할 수 없습니다. 또는 유저명이 너무 깁니다.";
                        dialog(message);
                }else if(!checkUser.checkBlank(phone.getText().toString())){
                        String message = "휴대폰 번호를 입력하세요.\n\n'-'와 스페이스바를 제외한 휴대폰 번호 8~11자를 입력하세요.";
                        dialog(message);
                } else if(!checkUser.checkPhone(phone.getText().toString())){
                        String message = "휴대폰 번호를 입력하세요.\n\n'-'와 스페이스바를 제외한 휴대폰 번호 8~11자를 입력하세요.";
                        dialog(message);
                } else if(!checkUser.checkBlank(email.getText().toString())){
                        String message = "이메일을 입력하세요.";
                        dialog(message);
                } else if(!checkUser.checkEmail(email.getText().toString())){
                        String message = "이메일을 정확히 입력하세요.";
                        dialog(message);
                } else if(!checkUser.checkBlank(password.getText().toString())){
                        String message = "비밀 번호를 입력하세요.\n\n영문, 숫자 또는 영문과 숫자의 조합으로 4~20자를 입력하세요.";
                        dialog(message);
                } else if(!checkUser.checkPassLen(password.getText().toString())){
                        String message = "비밀번호를 정확히 입력하세요. \n\n영문, 숫자 또는 영문과 숫자의 조합으로 4~20자입니다.";
                        dialog(message);
                }  else {
                        user = new User();
                        user.setName(name.getText().toString());
                        user.setPhone(phone.getText().toString());
                        user.setEmail(email.getText().toString());
                        user.setPassword(password.getText().toString());
                        joinOk = true;
                    }

                if(joinOk == true){
                        btnSend.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getActivity()
                                        .getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragmentContainer, new LogInFragment())
                                        .commit();
                            }
                        });
                    }
            }

        }
        );*/

        return view;
    }

    public void dialog(String message)
    {
        //다이얼로그

        new AlertDialog.Builder(getContext())
                .setTitle("실패")
                .setMessage(message)
                .setIcon(R.drawable.check)
                .setPositiveButton("확인",onClickListener)
                .show();
    }

    DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    };
}


