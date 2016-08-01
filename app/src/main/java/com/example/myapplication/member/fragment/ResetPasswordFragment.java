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

/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPasswordFragment extends Fragment {
    //private AlertDialog.Builder alert_confirm;
    private Button btnSend;
    private EditText email;

    //private AlertDialog alert;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);

        btnSend = (Button) view.findViewById(R.id.btnSend);
        email = (EditText)view.findViewById(R.id.email);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().equals("aaa@naver.com")) {
                    String message1 = "비밀번호 재설정 코드가 이메일로 발송되었습니다.\n\n메일이 없으면 스팸함을 확인해주세요.";
                    dialog1(message1);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ResetPassword2Fragment()).commit();
                } else {
                    String message = "이메일이 정확하지 않습니다.";
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
                .setTitle("펫클리닉")
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
