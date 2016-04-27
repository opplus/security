package com.model;

import org.springframework.stereotype.Component;

/**
 * Created by wjh on 2016/4/12.
 */
@Component
public class IndexImg {

    private String index_img;
    private String index_type;
    private String index_id;

    public String getIndex_img() {
        return index_img;
    }

    public void setIndex_img(String index_img) {
        this.index_img = index_img;
    }

    public String getIndex_type() {
        return index_type;
    }

    public void setIndex_type(String index_type) {
        this.index_type = index_type;
    }

    public String getIndex_id() {
        return index_id;
    }

    public void setIndex_id(String index_id) {
        this.index_id = index_id;
    }
}
