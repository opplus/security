package com.model;

import org.springframework.stereotype.Component;

/**
 * Created by wjh on 2016/4/9.
 */
@Component
public class Subscribe {
    private String subscribe_id;
    private String subscribe_type;
    private String subscribe_uid;
    private String subscribe_email;
    private String subscribe_state;
    private String subscribe_num;

    public String getSubscribe_id() {
        return subscribe_id;
    }

    public void setSubscribe_id(String subscribe_id) {
        this.subscribe_id = subscribe_id;
    }

    public String getSubscribe_type() {
        return subscribe_type;
    }

    public void setSubscribe_type(String subscribe_type) {
        this.subscribe_type = subscribe_type;
    }

    public String getSubscribe_uid() {
        return subscribe_uid;
    }

    public void setSubscribe_uid(String subscribe_uid) {
        this.subscribe_uid = subscribe_uid;
    }

    public String getSubscribe_email() {
        return subscribe_email;
    }

    public void setSubscribe_email(String subscribe_email) {
        this.subscribe_email = subscribe_email;
    }

    public String getSubscribe_state() {
        return subscribe_state;
    }

    public void setSubscribe_state(String subscribe_state) {
        this.subscribe_state = subscribe_state;
    }

    public String getSubscribe_num() {
        return subscribe_num;
    }

    public void setSubscribe_num(String subscribe_num) {
        this.subscribe_num = subscribe_num;
    }
}
