package com.model;

import org.springframework.stereotype.Component;

/**
 * Created by wjh on 2016/3/22.
 */
@Component
public class Tools {
    private String tools_id;
    private String tools_name;
    private String tools_description;
    private String tools_summary;
    private String tools_sendtime;
    private String tools_url;
    private String tools_img;
    private String tools_isdel;
    private String tools_uid;

    public java.lang.String getTools_isdel() {
        return tools_isdel;
    }

    public void setTools_isdel(java.lang.String tools_isdel) {
        this.tools_isdel = tools_isdel;
    }

    public java.lang.String getTools_uid() {
        return tools_uid;
    }

    public void setTools_uid(java.lang.String tools_uid) {
        this.tools_uid = tools_uid;
    }

    public java.lang.String getTools_id() {
        return tools_id;
    }

    public void setTools_id(java.lang.String tools_id) {
        this.tools_id = tools_id;
    }

    public java.lang.String getTools_name() {
        return tools_name;
    }

    public void setTools_name(java.lang.String tools_name) {
        this.tools_name = tools_name;
    }

    public java.lang.String getTools_description() {
        return tools_description;
    }

    public void setTools_description(java.lang.String tools_description) {
        this.tools_description = tools_description;
    }

    public java.lang.String getTools_summary() {
        return tools_summary;
    }

    public void setTools_summary(java.lang.String tools_summary) {
        this.tools_summary = tools_summary;
    }

    public java.lang.String getTools_sendtime() {
        return tools_sendtime;
    }

    public void setTools_sendtime(java.lang.String tools_sendtime) {
        this.tools_sendtime = tools_sendtime;
    }

    public java.lang.String getTools_url() {
        return tools_url;
    }

    public void setTools_url(java.lang.String tools_url) {
        this.tools_url = tools_url;
    }

    public java.lang.String getTools_img() {
        return tools_img;
    }

    public void setTools_img(java.lang.String tools_img) {
        this.tools_img = tools_img;
    }
}
