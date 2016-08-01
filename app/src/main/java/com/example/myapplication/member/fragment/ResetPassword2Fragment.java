package com.example.myapplication.member.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.member.dao.CheckUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPassword2Fragment extends Fragment {
    private Button btnSend;
    private EditText code;
    private EditText newpw;

    CheckUser checkUser = new CheckUser();

    public ResetPassword2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset_password2, container, false);

        btnSend = (Button) view.findViewById(R.id.btnSend);
        code = (EditText)view.findViewById(R.id.email);
        newpw = (EditText)view.findViewById(R.id.newpw);


        btnSend.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(code.getText().toString().equals("apple") && (checkUser.checkPassLen(newpw.getText().toString()))) {
                String message1 = "새 비밀번호가 생성되었습니다.";
                dialog1(message1);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new LogInFragment()).commit();
            } else {
                String message = "비밀번호 재설정 코드, 새 비밀번호를 입력하세요.\n\n비밀번호 재설정 코드 또는 새 비밀번호가 정확하지 않습니다.\n\n비밀번호는 영문, 숫자 또는 영문과 숫자의 조합으로 4~20자로 설정 가능합니다.";
                dialog(message);
            }

                /*Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);*/
        }
    });
        return view;
    }


    public void dialog1(String message1)
    {
        //다이얼로그

        new AlertDialog.Builder(getContext())
                .setTitle("성공")
                .setMessage(message1)
                .setIcon(R.drawable.check)
                .setPositiveButton("확인",onClickListener)
                .show();
    }


    public void dialog(String message)
    {
        //다이얼로그

        new AlertDialog.Builder(getContext())
                .setTitle("펫클리닉")
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
