package com.example.myapplication.member.dao;


import java.util.regex.Pattern;

public class CheckUser {

    private boolean result;

    public boolean checkName(String name) {
        String checkedName = "[\uac00-\ud7af]{2,10}";
        result = Pattern.matches(checkedName, name);
        if (result) {
            return true;
        } else {
            return false;
        }
    }

    /*//유저명 길이가 4자 미만이거나 20자 이상일 경우 false 리턴
    public boolean checkName(String name) {
        String checkName = "\\w{0,20}" + "[\uac00-\ud7af]{0,10}" + "\\w{0,20}";

        result = Pattern.matches(checkName, name);
        if (result) {
            return true;
        } else {
            return false;
        }
    }*/


    /*한글, 영문, 숫자 최대 4자 또는 조합으로 최대 4자까지 입력 가능*/
    /*"[\uac00-\ud7af]{2,10}" + "\\w{4,20}"*/


  /*  public boolean checkNameLen(String str) {
        if(str == null || str.length<4 || str.length>20){
            alert("id에 이상이 있습니다. 조건을 살펴보세요.");
            return false;
        }else if(match1 == null || str1.length<6||str1.length>12){
            alert("pwd에 이상이 있습니다. 조건을 살펴보세요.");
            return false;
        }

    }*/

    // Email이 정규표현식에 맞게 입력 되었는지 검사
    public boolean checkEmail(String email) {
        String checkedEmail = "\\w+@\\w+\\.\\w+(\\.\\w+)?";
        result = Pattern.matches(checkedEmail, email);
        if (result) {
            return true;
        } else {
            return false;
        }
    }

    //Phone이 정규표현식에 맞게 완성 되었는지 검사
    public boolean checkPhone(String phone) {
        String checkedPhone = "\\d{8,11}";
        result = Pattern.matches(checkedPhone, phone);
        if (result) {
            return true;
        } else {
            return false;
        }
    }

    // 빈칸이 있는지 검사
    public boolean checkBlank(String str){
        if(str.equals("") || str.length() ==0)
        {
            return false;
        } else {
            return true;
        }
    }

/*    //비밀번호와 비밀번호 확인이 같은지 검사
    public boolean eCheckPass(String pass, String chkPass) {
        if(pass.equals(chkPass)) {
            return true;
        } else {
            return false;
        }
    }*/

    //비밀번호가 4글자 이상 입력되었는지 확인하는 검사
    public boolean checkPassLen(String password) {
        String checkPassLength = "\\w{4,20}";
        result = Pattern.matches(checkPassLength, password);
        if(result) {
            return true;
        } else {
            return false;//비밀번호 길이가 4자 미만이거나 20자 이상일 경우 false 리턴
        }
    }


}
