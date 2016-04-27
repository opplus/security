package com.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.model.AdminPermission;
import java.util.*;
/**
 * Created by wjh on 2016/3/21.
 */
@Component
public class Admin {

    private String admin_id;
    private String admin_name;
    private String admin_password;
    private String admin_state;
    private String admin_newsnum;

    private ArrayList<AdminPermission> adminpermlist;

    public ArrayList<AdminPermission> getAdminpermlist() {
        return adminpermlist;
    }

    public void setAdminpermlist(ArrayList<AdminPermission> adminpermlist) {
        this.adminpermlist = adminpermlist;
    }

    public java.lang.String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(java.lang.String admin_id) {
        this.admin_id = admin_id;
    }

    public java.lang.String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(java.lang.String admin_name) {
        this.admin_name = admin_name;
    }

    public java.lang.String getAdmin_password() {
        return admin_password;
    }

    public void setAdmin_password(java.lang.String admin_password) {
        this.admin_password = admin_password;
    }

    public java.lang.String getAdmin_state() {
        return admin_state;
    }

    public void setAdmin_state(java.lang.String admin_state) {
        this.admin_state = admin_state;
    }

    public java.lang.String getAdmin_newsnum() {
        return admin_newsnum;
    }

    public void setAdmin_newsnum(java.lang.String admin_newsnum) {
        this.admin_newsnum = admin_newsnum;
    }
}
