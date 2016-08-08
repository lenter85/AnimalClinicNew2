package com.example.myapplication.member.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.myapplication.HomeActivity;
import com.example.myapplication.R;
import com.example.myapplication.member.dao.CheckUser;
import com.example.myapplication.network.MemberNetwork;

public class LogInFragment extends Fragment {

    CheckUser checkUser = new CheckUser();

    private ImageButton btnLogin;
    private ImageButton btnSignup;
    private Button btnReset;

    private EditText email;
    private EditText password;

    private MemberNetwork memberNetwork = new MemberNetwork();

    public LogInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_log_in, container, false);


        btnLogin = (ImageButton) view.findViewById(R.id.btnLogin);

        email = (EditText)view.findViewById(R.id.login_email);
        password = (EditText)view.findViewById(R.id.login_password);

        //btnLogin.setEnabled(false);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkUser.checkBlank(email.getText().toString())){
                    dialog("이메일을 입력해 주세요.");
                } else if(!checkUser.checkEmail(email.getText().toString())){
                    dialog("이메일을 다시 입력해 주세요.");
                } else if(!checkUser.checkBlank(password.getText().toString())){
                    dialog("비밀번호를 입력해 주세요.");
                } else if(!checkUser.checkPassLen(password.getText().toString())){
                    dialog("비밀번호를 다시 입력해 주세요.");
                } else {
                    memberNetwork.Login(email.getText().toString(), password.getText().toString());

                    /*if(!email.getText().toString().equals(memberNetwork.member.getMid())){
                        dialog("이메일을 다시 입력해주세요.");
                    } else if(!password.getText().toString().equals(memberNetwork.member.getMpassword())){
                        dialog("비밀번호를 다시 입력해주세요.");
                    } else {
                        Toast toast = new Toast(getContext());
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setText(memberNetwork.member.getMname() + "님 환영합니다.");
                        toast.show();

                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        startActivity(intent);
                    }*/
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnSignup = (ImageButton) view.findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new NewAccountFragment()).commit();
            }
        });


        btnReset = (Button) view.findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ResetPasswordFragment()).commit();
            }
        });





        return view;
    }

    public void dialog(String message)
    {
        //다이얼로그

        new AlertDialog.Builder(getContext())
                .setTitle("로그인 실패")
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
