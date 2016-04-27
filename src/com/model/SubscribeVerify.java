package com.model;

import org.springframework.stereotype.Component;

/**
 * Created by wjh on 2016/4/10.
 */
@Component
public class SubscribeVerify {
    private String subscribe_verify_id;
    private String subscribe_verify_email;
    private String subscribe_verify_uid;
    private String subscribe_verify_type;
    private String subscribe_verify_code;
    private String subscribe_verify_sendtime;
    private String subscribe_verify_expiretime;
    private String subscribe_verify_state;

    public String getSubscribe_verify_id() {
        return subscribe_verify_id;
    }

    public void setSubscribe_verify_id(String subscribe_verify_id) {
        this.subscribe_verify_id = subscribe_verify_id;
    }

    public String getSubscribe_verify_email() {
        return subscribe_verify_email;
    }

    public void setSubscribe_verify_email(String subscribe_verify_email) {
        this.subscribe_verify_email = subscribe_verify_email;
    }

    public String getSubscribe_verify_uid() {
        return subscribe_verify_uid;
    }

    public void setSubscribe_verify_uid(String subscribe_verify_uid) {
        this.subscribe_verify_uid = subscribe_verify_uid;
    }

    public String getSubscribe_verify_type() {
        return subscribe_verify_type;
    }

    public void setSubscribe_verify_type(String subscribe_verify_type) {
        this.subscribe_verify_type = subscribe_verify_type;
    }

    public String getSubscribe_verify_code() {
        return subscribe_verify_code;
    }

    public void setSubscribe_verify_code(String subscribe_verify_code) {
        this.subscribe_verify_code = subscribe_verify_code;
    }

    public String getSubscribe_verify_sendtime() {
        return subscribe_verify_sendtime;
    }

    public void setSubscribe_verify_sendtime(String subscribe_verify_sendtime) {
        this.subscribe_verify_sendtime = subscribe_verify_sendtime;
    }

    public String getSubscribe_verify_expiretime() {
        return subscribe_verify_expiretime;
    }

    public void setSubscribe_verify_expiretime(String subscribe_verify_expiretime) {
        this.subscribe_verify_expiretime = subscribe_verify_expiretime;
    }

    public String getSubscribe_verify_state() {
        return subscribe_verify_state;
    }

    public void setSubscribe_verify_state(String subscribe_verify_state) {
        this.subscribe_verify_state = subscribe_verify_state;
    }
}
