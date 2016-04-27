package com.model;

import org.springframework.stereotype.Component;


/**
 * Created by wjh on 2016/3/19.
 */
@Component
public class VerifyEmail {
    private String verify_email_id;
    private String verify_email_email;
    private String verify_email_password;
    private String verify_email_registertime;
    private String verify_email_sendtime;
    private String verify_email_code;
    private String verify_email_expiretime;
    private String verify_email_state;

    public java.lang.String getVerify_email_id() {
        return verify_email_id;
    }

    public void setVerify_email_id(java.lang.String verify_email_id) {
        this.verify_email_id = verify_email_id;
    }

    public java.lang.String getVerify_email_email() {
        return verify_email_email;
    }

    public void setVerify_email_email(java.lang.String verify_email_email) {
        this.verify_email_email = verify_email_email;
    }

    public java.lang.String getVerify_email_password() {
        return verify_email_password;
    }

    public void setVerify_email_password(java.lang.String verify_email_password) {
        this.verify_email_password = verify_email_password;
    }

    public java.lang.String getVerify_email_registertime() {
        return verify_email_registertime;
    }

    public void setVerify_email_registertime(java.lang.String verify_email_registertime) {
        this.verify_email_registertime = verify_email_registertime;
    }

    public java.lang.String getVerify_email_sendtime() {
        return verify_email_sendtime;
    }

    public void setVerify_email_sendtime(java.lang.String verify_email_sendtime) {
        this.verify_email_sendtime = verify_email_sendtime;
    }

    public java.lang.String getVerify_email_code() {
        return verify_email_code;
    }

    public void setVerify_email_code(java.lang.String verify_email_code) {
        this.verify_email_code = verify_email_code;
    }

    public java.lang.String getVerify_email_expiretime() {
        return verify_email_expiretime;
    }

    public void setVerify_email_expiretime(java.lang.String verify_email_expiretime) {
        this.verify_email_expiretime = verify_email_expiretime;
    }

    public java.lang.String getVerify_email_state() {
        return verify_email_state;
    }

    public void setVerify_email_state(java.lang.String verify_email_state) {
        this.verify_email_state = verify_email_state;
    }
}
