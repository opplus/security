package com.dao;

import com.commonfunction.Configuration;
import com.model.Admin;
import com.model.AdminPermission;
import com.model.User;
import com.model.Tools;
import com.model.UserText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Created by wjh on 2016/3/21.
 */
@Repository
public class AdminDao {

    //管理员登录验证
    public String checkadmin(String name,
                             String password,
                             Connection conn) throws Exception {
        String id = "";
        String state="";
        PreparedStatement ps = conn.prepareStatement("select * " +
                "from admin where " +
                "admin_name=? " +
                "and admin_password=?");
        ps.setString(1, name);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            id = rs.getString("admin_id");
            state= rs.getString("admin_state");
        }
        if(state.equals("2")){
            id="";
        }
        return id;
    }

    //    添加管理员
    public void addadmin(String name,
                         String password,
                         String perm,
                         Connection conn) throws Exception {
        int count = 0;
        conn.setAutoCommit(false);//取消自动提交事务
        PreparedStatement ps1 = conn.prepareStatement("insert into " +
                        "admin (" +
                        "admin_name," +
                        "admin_password) " +
                        "values (?,?)",
                Statement.RETURN_GENERATED_KEYS);
        ps1.setString(1, name);
        ps1.setString(2, password);
        ps1.executeUpdate(); //执行插入
        ResultSet rs = ps1.getGeneratedKeys();
        while (rs.next()) {
            String adminid = rs.getString(1);
            PreparedStatement ps3 = conn.prepareStatement("insert into " +
                    "admin_permission (" +
                    "admin_permission_adminid," +
                    "admin_permission_perm) " +
                    "values (?,?)");
            ps3.setString(1, adminid);
            ps3.setString(2, perm);
            count = ps3.executeUpdate();
        }
        conn.commit();//提交事务
        conn.setAutoCommit(true);//恢复原来的自动提交事务
        if (count == 0) {
            throw new Exception();
        }
    }

    //    更改管理员状态
    public void alteradminstate(String adminid,
                                String state,
                                Connection conn) throws Exception {
        PreparedStatement ps = conn.prepareStatement("update admin " +
                "set admin_state=? " +
                "where admin_id=?");
        ps.setString(1, state);
        ps.setString(2, adminid);
        int count = ps.executeUpdate();
        if (count == 0) {
            throw new Exception();
        }
    }

    //   更改管理员权限(删除或添加)
    public void alteradminperm(String adminid,
                               String perm,
                               String type,
                               Connection conn) throws Exception {
        PreparedStatement ps = null;
        if (type.equals("add")) {
            ps = conn.prepareStatement("INSERT INTO " +
                    "`admin_permission`(" +
                    " `admin_permission_adminid`, " +
                    "`admin_permission_perm`) " +
                    "VALUES (?,?)");
            ps.setString(1, adminid);
            ps.setString(2, perm);
        } else if (type.equals("del")) {
            ps = conn.prepareStatement("DELETE FROM " +
                    "admin_permission " +
                    "where admin_permission_adminid=? and admin_permission_perm=?");
            ps.setString(1, adminid);
            ps.setString(2, perm);
        } else {
            throw new Exception();
        }
        int count = ps.executeUpdate();
        if (count == 0) {
            throw new Exception();
        }
    }


    //    删除管理员
    public void deladmin(String adminid,
                         Connection conn) throws Exception {
        conn.setAutoCommit(false);//取消自动提交事务
        PreparedStatement ps1 = conn.prepareStatement("delete from " +
                "admin where " +
                "admin_id=?");
        PreparedStatement ps2 = conn.prepareStatement("delete from " +
                "admin_permission where " +
                "admin_permission_adminid=?");
        ps1.setString(1, adminid);
        ps2.setString(1, adminid);
        int count = ps1.executeUpdate() + ps2.executeUpdate();
        conn.commit();//提交事务
        conn.setAutoCommit(true);//恢复原来的自动提交事务
        if (count == 1) {
            throw new Exception();
        }
    }


    //    获取管理员信息
    public ArrayList<Admin>getadminlist(String adminid, String isall,Connection conn) throws Exception {
        conn.setAutoCommit(false);
        PreparedStatement ps = null;
        if (isall.equals("1")) {
            ps = conn.prepareStatement("select * " +
                    "from admin where 1");
        } else {
            ps = conn.prepareStatement("select *  " +
                    "from admin where admin_id=?");
            ps.setString(1, adminid);
        }
        ResultSet rs = ps.executeQuery();
        ArrayList<Admin> adminlist = new ArrayList<Admin>();
        while (rs.next()){
            Admin admin = new Admin();
            admin.setAdmin_id(rs.getString("admin_id"));
            admin.setAdmin_name(rs.getString("admin_name"));
            admin.setAdmin_state(rs.getString("admin_state"));
            admin.setAdmin_newsnum(rs.getString("admin_newsnum"));
            String id=rs.getString("admin_id");
            PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM `admin_permission` WHERE `admin_permission_adminid`=?");
            ps1.setString(1,id);
            ResultSet rs1 = ps1.executeQuery();
            ArrayList<AdminPermission> adminpermlist=new ArrayList<AdminPermission>();
            while (rs1.next()) {
                AdminPermission adminperm = new AdminPermission();
                adminperm.setAdmin_permission_id(rs1.getString("admin_permission_id"));
                adminperm.setAdmin_permission_adminid(rs1.getString("admin_permission_adminid"));
                adminperm.setAdmin_permission_perm(rs1.getString("admin_permission_perm"));
                adminpermlist.add(adminperm);
            }
            admin.setAdminpermlist(adminpermlist);
            adminlist.add(admin);
        }
        conn.commit();//提交事务
        conn.setAutoCommit(true);//恢复原来的自动提交事务
        return adminlist;
    }

    //    获取会员信息
    public ArrayList<User> getuserlist(String level,
                                       int page,
                                       Connection conn) throws Exception {
        ArrayList<User> userlist = new ArrayList<User>();
        PreparedStatement ps = null;
        ps = conn.prepareStatement("select * " +
                "from user where user_level=? limit ?,?");
        ps.setString(1, level);
        ps.setInt(2, Configuration.pageshow * (page - 1));
        ps.setInt(3, Configuration.pageshow * page);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            User user = new User();
            user.setUser_id(rs.getString("user_id"));
            user.setUser_name(rs.getString("user_name"));
            user.setUser_level(rs.getString("user_level"));
            user.setUser_integral(rs.getString("user_integral"));
            user.setUser_registertime(rs.getString("user_registertime"));
            user.setUser_email(rs.getString("user_email"));
            user.setUser_phone(rs.getString("user_phone"));
            user.setUser_emailnoti(rs.getString("user_emailnoti"));
            user.setUser_phonenoti(rs.getString("user_phonenoti"));
            user.setUser_img(rs.getString("user_img"));
            user.setUser_textnum(rs.getString("user_textnum"));
            userlist.add(user);
        }
        return userlist;
    }
    public String getusercount(String level,
                                       Connection conn) throws Exception {
        PreparedStatement ps = null;
        ps = conn.prepareStatement("select count(1) as c from user where user_level=?");
        ps.setString(1,level);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("c");
    }

    //    发布新闻
    public void fabunews(String url,
                         String title,
                         String text,
                         String image,
                         String sendtime,
                         String type,
                         String adminid,
                         Connection conn) throws Exception {
        conn.setAutoCommit(false);//取消自动提交事务
        PreparedStatement ps = conn.prepareStatement("INSERT INTO `news`( `news_url`, `news_title`, `news_text`, `news_image`, `news_sendtime`, `news_type`, `news_uid`) VALUES (?,?,?,?,?,?,?)");
        ps.setString(1, url);
        ps.setString(2, title);
        ps.setString(3, text);
        ps.setString(4, image);
        ps.setString(5, sendtime);
        ps.setString(6, type);
        ps.setString(7, adminid);
        int count = ps.executeUpdate();
        PreparedStatement ps1 = conn.prepareStatement("update admin " +
                "set admin_newsnum=admin_newsnum+1 " +
                "where admin_id=?");
        ps1.setString(1,adminid);
        count += ps1.executeUpdate();
        conn.commit();//提交事务
        conn.setAutoCommit(true);//恢复原来的自动提交事务
        if (count != 2) {
            throw new Exception();
        }
    }


    //    发布安全工具
    public void fabutools(String name,
                          String description,
                          String summary,
                          String sendtime,
                          String url,
                          String img,
                          String adminid,
                          Connection conn) throws Exception {
        conn.setAutoCommit(false);//取消自动提交事务
        PreparedStatement ps = conn.prepareStatement("INSERT INTO `tools`(" +
                "`tools_name`, " +
                "`tools_description`, " +
                "`tools_summary`, " +
                "`tools_sendtime`, " +
                "`tools_url`, " +
                "`tools_img`, " +
                " tools_uid) " +
                "VALUES (?,?,?,?,?,?,?)");
        ps.setString(1, name);
        ps.setString(2, description);
        ps.setString(3, summary);
        ps.setString(4, sendtime);
        ps.setString(5, url);
        ps.setString(6, img);
        ps.setString(7, adminid);
        int count = ps.executeUpdate();
        PreparedStatement ps1 = conn.prepareStatement("update admin " +
                "set admin_newsnum=admin_newsnum+1 " +
                "where admin_id=?");
        ps1.setString(1,adminid);
        count += ps1.executeUpdate();
        conn.commit();//提交事务
        conn.setAutoCommit(true);//恢复原来的自动提交事务
        if (count != 2) {
            throw new Exception();
        }
    }

    //   删除新闻或安全工具
    public void delnewortools(String type, String id, Connection conn) throws Exception {
        PreparedStatement ps = null;
        if (type.equals("news")) {
            ps = conn.prepareStatement("update news set news_isdel=1 where news_id=?");
        } else if (type.equals("tools")) {
            ps = conn.prepareStatement("update tools set tools_isdel=1 where tools_id=?");
        } else {
            throw new Exception();
        }
        ps.setString(1,id);
        int count = ps.executeUpdate();
        if (count == 0) {
            throw new Exception();
        }
    }


    //    获取用户待审核文章列表
    public ArrayList<UserText> getusertext(int page,String state, Connection conn) throws Exception {
        PreparedStatement ps = conn.prepareStatement("select * " +
                " from user_text " +
                " where user_text_verifystate=? " +
                " user_text_isdel=0 " +
                " order by user_text_uid asc ," +
                "user_text_sendtime asc " +
                " limit ?,?");
        ps.setString(1,state);
        ps.setInt(2, Configuration.pageshow * (page - 1));
        ps.setInt(3, Configuration.pageshow * page);
        ResultSet rs = ps.executeQuery();
        ArrayList<UserText> utextlist = new ArrayList<UserText>();
        while (rs.next()) {
            UserText utext = new UserText();
            utext.setUser_text_id(rs.getString("user_text_id"));
            utext.setUser_text_uid(rs.getString("user_text_uid"));
            utext.setUser_text_name(rs.getString("user_text_name"));
            utext.setUser_text_summary(rs.getString("user_text_summary"));
            utext.setUser_text_text(rs.getString("user_text_text"));
            utext.setUser_text_image(rs.getString("user_text_image"));
            utext.setUser_text_sendtime(rs.getString("user_text_sendtime"));
            utext.setUser_text_verifystate(rs.getString("user_text_verifystate"));
            utext.setUser_text_isdel(rs.getString("user_text_isdel"));
            utextlist.add(utext);
        }
        return utextlist;
    }

    //    审核用户文章
    public void auditusertext(String state, String textid, Connection conn) throws Exception {
        PreparedStatement ps = conn.prepareStatement("update user_text set user_text_verifystate=? where user_text_id=?");
        ps.setString(1, state);
        ps.setString(2, textid);
        int count = ps.executeUpdate();
        if (count == 0) {
            throw new Exception();
        }
    }
}
