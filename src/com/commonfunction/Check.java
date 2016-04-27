package com.commonfunction;

/**
 * Created by wjh on 2016/3/10.
 */
import java.util.regex.*;
//检查邮箱格式是否正确
public class Check {
    public static  boolean ismail(String email){
        String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email);
        boolean flag = matcher.matches();
        return  flag;
    }
//    检查密码格式是否符合要求
    public static boolean ispassword(String password){
        String check="^(?![^a-zA-Z]+$)(?!\\D+$).{8,16}$";//字母加数字组成，8至16位
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(password);
        boolean flag = matcher.matches();
        return  flag;
    }

}

