package com.model;

import org.springframework.stereotype.Component;

/**
 * Created by wjh on 2016/3/19.
 */
@Component
public class User {
    private String user_id;
    private String user_name;
    private String user_password;
    private String user_level;
    private String user_integral;
    private String user_registertime;
    private String user_email;
    private String user_phone;
    private String user_emailnoti;
    private String user_phonenoti;
    private String user_img;
    private String user_textnum;

    public java.lang.String getUser_img() {
        return user_img;
    }

    public void setUser_img(java.lang.String user_img) {
        this.user_img = user_img;
    }

    public java.lang.String getUser_id() {
        return user_id;
    }

    public void setUser_id(java.lang.String user_id) {
        this.user_id = user_id;
    }

    public java.lang.String getUser_name() {
        return user_name;
    }

    public void setUser_name(java.lang.String user_name) {
        this.user_name = user_name;
    }

    public java.lang.String getUser_password() {
        return user_password;
    }

    public void setUser_password(java.lang.String user_password) {
        this.user_password = user_password;
    }

    public java.lang.String getUser_level() {
        return user_level;
    }

    public void setUser_level(java.lang.String user_level) {
        this.user_level = user_level;
    }

    public java.lang.String getUser_integral() {
        return user_integral;
    }

    public void setUser_integral(java.lang.String user_integral) {
        this.user_integral = user_integral;
    }

    public java.lang.String getUser_registertime() {
        return user_registertime;
    }

    public void setUser_registertime(java.lang.String user_registertime) {
        this.user_registertime = user_registertime;
    }

    public java.lang.String getUser_email() {
        return user_email;
    }

    public void setUser_email(java.lang.String user_email) {
        this.user_email = user_email;
    }

    public java.lang.String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(java.lang.String user_phone) {
        this.user_phone = user_phone;
    }

    public java.lang.String getUser_emailnoti() {
        return user_emailnoti;
    }

    public void setUser_emailnoti(java.lang.String user_emailnoti) {
        this.user_emailnoti = user_emailnoti;
    }

    public java.lang.String getUser_phonenoti() {
        return user_phonenoti;
    }

    public void setUser_phonenoti(java.lang.String user_phonenoti) {
        this.user_phonenoti = user_phonenoti;
    }

    public java.lang.String getUser_textnum() {
        return user_textnum;
    }

    public void setUser_textnum(java.lang.String user_textnum) {
        this.user_textnum = user_textnum;
    }
}
