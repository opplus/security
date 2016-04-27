package com.service.impl;

import com.model.Admin;
import com.model.User;
import com.commonfunction.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dao.AdminDao;
import java.sql.Connection;
import java.util.*;
/**
 * Created by wjh on 2016/3/22.
 */
@Service
public class AdminService {

    @Autowired
    private AdminDao admindao;

    //    管理员登录验证
    public String adminlogin(String adminname,
                             String adminpass,
                             Connection conn) throws Exception {
        String pass=MD5.encode2hex(adminpass);
        String adminid = admindao.checkadmin(adminname, pass, conn);
        return adminid;
    }

    public void addadmins(String name,
                          String password,
                          String perm,
                          Connection conn) throws Exception{
        String pass=MD5.encode2hex(password);
        admindao.addadmin(name,pass,perm,conn);
    }
    public void alteradminState(String adminid,
                                String state,
                                Connection conn) throws Exception {
        admindao.alteradminstate(adminid,state,conn);
    }
    public void alteradminPerm(String adminid,
                               String perm,
                               String type,
                               Connection conn) throws Exception {
        admindao.alteradminperm(adminid,perm,type,conn);
    }
     public void deleteadmin(String adminid,
                             Connection conn) throws Exception{
         admindao.deladmin(adminid,conn);
     }
    public ArrayList<Admin> getadminList(String adminid,String isall,Connection conn) throws Exception {
        ArrayList<Admin>  adminlist=admindao.getadminlist(adminid,isall,conn);
        return adminlist;
    }
//    获取会员信息
    public ArrayList<User> getuserList(String level,
                                       int page,
                                       Connection conn) throws Exception {
        ArrayList<User> userlist = admindao.getuserlist(level,page, conn);
        return userlist;
    }
    public String getuserCount(String level,
                                       Connection conn) throws Exception {
        String count = admindao.getusercount(level,conn);
        return count;
    }
}
