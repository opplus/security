package com.model;

import org.springframework.stereotype.Component;


/**
 * Created by wjh on 2016/3/16.
 */
@Component
public class News {
    private String news_id;
    private String news_url;
    private String news_title;
    private String news_text;
    private String news_img;
    private String news_sendtime;
    private String news_type;
    private String news_uid;
    private String news_isdel;

    public java.lang.String getNews_uid() {
        return news_uid;
    }

    public void setNews_uid(java.lang.String news_uid) {
        this.news_uid = news_uid;
    }

    public java.lang.String getNews_isdel() {
        return news_isdel;
    }

    public void setNews_isdel(java.lang.String news_isdel) {
        this.news_isdel = news_isdel;
    }

    public java.lang.String getNews_id() {
        return news_id;
    }

    public void setNews_id(java.lang.String news_id) {
        this.news_id = news_id;
    }

    public java.lang.String getNews_url() {
        return news_url;
    }

    public void setNews_url(java.lang.String news_url) {
        this.news_url = news_url;
    }

    public java.lang.String getNews_title() {
        return news_title;
    }

    public void setNews_title(java.lang.String news_title) {
        this.news_title = news_title;
    }

    public java.lang.String getNews_text() {
        return news_text;
    }

    public void setNews_text(java.lang.String news_text) {
        this.news_text = news_text;
    }

    public java.lang.String getNews_img() {
        return news_img;
    }

    public void setNews_img(java.lang.String news_img) {
        this.news_img = news_img;
    }

    public java.lang.String getNews_sendtime() {
        return news_sendtime;
    }

    public void setNews_sendtime(java.lang.String news_sendtime) {
        this.news_sendtime = news_sendtime;
    }

    public java.lang.String getNews_type() {
        return news_type;
    }

    public void setNews_type(java.lang.String news_type) {
        this.news_type = news_type;
    }
}
