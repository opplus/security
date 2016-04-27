package com.model;

/**
 * Created by wjh on 2016/3/15.
 */
import org.springframework.stereotype.Component;


@Component
public class RevisePass {
    private String verify_password_id;
    private String verify_password_code;
    private String verify_password_sendtime;
    private String verify_password_expiretime;
    private String verify_password_email;
    private String verify_password_state;

    public java.lang.String getVerify_password_id() {
        return verify_password_id;
    }

    public void setVerify_password_id(java.lang.String verify_password_id) {
        this.verify_password_id = verify_password_id;
    }

    public java.lang.String getVerify_password_code() {
        return verify_password_code;
    }

    public void setVerify_password_code(java.lang.String verify_password_code) {
        this.verify_password_code = verify_password_code;
    }

    public java.lang.String getVerify_password_sendtime() {
        return verify_password_sendtime;
    }

    public void setVerify_password_sendtime(java.lang.String verify_password_sendtime) {
        this.verify_password_sendtime = verify_password_sendtime;
    }

    public java.lang.String getVerify_password_expiretime() {
        return verify_password_expiretime;
    }

    public void setVerify_password_expiretime(java.lang.String verify_password_expiretime) {
        this.verify_password_expiretime = verify_password_expiretime;
    }

    public java.lang.String getVerify_password_email() {
        return verify_password_email;
    }

    public void setVerify_password_email(java.lang.String verify_password_email) {
        this.verify_password_email = verify_password_email;
    }

    public java.lang.String getVerify_password_state() {
        return verify_password_state;
    }

    public void setVerify_password_state(java.lang.String verify_password_state) {
        this.verify_password_state = verify_password_state;
    }
}
