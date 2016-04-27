package com.model;

import org.springframework.stereotype.Component;

/**
 * Created by wjh on 2016/3/21.
 */
@Component
public class AdminPermission {
    private String admin_permission_id;
    private String admin_permission_adminid;
    private String admin_permission_perm;

    public java.lang.String getAdmin_permission_id() {
        return admin_permission_id;
    }

    public void setAdmin_permission_id(java.lang.String admin_permission_id) {
        this.admin_permission_id = admin_permission_id;
    }

    public java.lang.String getAdmin_permission_adminid() {
        return admin_permission_adminid;
    }

    public void setAdmin_permission_adminid(java.lang.String admin_permission_adminid) {
        this.admin_permission_adminid = admin_permission_adminid;
    }

    public java.lang.String getAdmin_permission_perm() {
        return admin_permission_perm;
    }

    public void setAdmin_permission_perm(java.lang.String admin_permission_perm) {
        this.admin_permission_perm = admin_permission_perm;
    }
}
