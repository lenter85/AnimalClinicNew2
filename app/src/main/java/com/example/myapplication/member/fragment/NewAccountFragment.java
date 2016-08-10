package com.example.myapplication.member.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.HomeActivity;
import com.example.myapplication.R;
import com.example.myapplication.member.dao.CheckUser;
import com.example.myapplication.member.dto.Member;
import com.example.myapplication.network.MemberNetwork;

public class NewAccountFragment extends Fragment {
    MemberNetwork memberNetwork = new MemberNetwork();
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

        name.setText("박민규");
        email.setText("park9831@naver.com");
        phone.setText("01012345678");
        password.setText("12345");

        btnSend.setEnabled(false);
        checkBox();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkUser.checkBlank(name.getText().toString())){
                    String message = "유저명을 입력하세요.";
                    dialog(message);
                    //dialog();
                } else if(!checkUser.checkName(name.getText().toString())) {
                    String message = "유저명을 정확히 입력해주세요.";
                    dialog(message);
                } else if(!checkUser.checkBlank(phone.getText().toString())){
                    String message = "휴대폰 번호를 입력해주세요.";
                    dialog(message);
                } else if(!checkUser.checkPhone(phone.getText().toString())){
                    String message = "휴대폰 번호를 정확히 입력해주세요.";
                    dialog(message);
                } else if(!checkUser.checkBlank(email.getText().toString())){
                    String message = "이메일을 입력해주세요.";
                    dialog(message);
                } else if(!checkUser.checkEmail(email.getText().toString())){
                    String message = "이메일을 정확히 입력해주세요.";
                    dialog(message);
                } else if(!checkUser.checkBlank(password.getText().toString())){
                    String message = "비밀 번호를 입력해주세요.";
                    dialog(message);
                } else if(!checkUser.checkPassLen(password.getText().toString())){
                    String message = "비밀번호를 정확히 입력해주세요.";
                    dialog(message);
                } else {

                    Member member = new Member();
                    member.setMname(name.getText().toString());
                    member.setMphone(phone.getText().toString());
                    member.setMid(email.getText().toString());
                    member.setMpassword(password.getText().toString());
                    member.setMtype("1");

                    if(memberNetwork.checkUserId(email.getText().toString())){
                        Toast.makeText(getContext(),"동일한 ID 존재", Toast.LENGTH_SHORT).show();
                        Log.i("mylog","동일한 ID 존재");
                        } else {
                            memberNetwork.join(member);
                            Toast.makeText(getContext(),"회원가입 성공", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            startActivity(intent);
                        }
                    }
                }
        });
        return view;
    }

    public void checkBox(){
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
    }

    public void dialog(String message){
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


