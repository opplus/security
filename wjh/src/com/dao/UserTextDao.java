package com.dao;

/**
 * Created by wjh on 2016/3/30.
 */

import com.commonfunction.Configuration;
import com.model.UserText;
import com.model.UserTextUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Repository
public class UserTextDao {

    //    搜索会员文章
    public ArrayList<UserTextUser> searchtext(String type,
                                              String data,
                                              int page,
                                              Connection conn) throws Exception {
        PreparedStatement ps = null;
        if (type.equals("nickname")) {
            if (data.equals("") || data == null) {
                ps = conn.prepareStatement("select * " +
                        "from usertext " +
                        "where user_text_verifystate=1 " +
                        "and user_text_isdel=0 " +
                        "order by user_text_sendtime_desc," +
                        "user_text_id desc limit ?,?");
                ps.setInt(1, Configuration.pageshow * (page - 1));
                ps.setInt(2, Configuration.pageshow * page);
            } else {
                ps = conn.prepareStatement("select * " +
                        "from usertext " +
                        "where user_name like ? " +
                        "and user_text_verifystate=1 " +
                        "and user_text_isdel=0 " +
                        "order by user_text_sendtime_desc," +
                        "user_text_id desc limit ?,?");
                ps.setString(1, "%" + data + "%");
                ps.setInt(2, Configuration.pageshow * (page - 1));
                ps.setInt(3, Configuration.pageshow * page);
            }
        } else if (type.equals("title")) {
            if (data.equals("") || data == null) {
                ps = conn.prepareStatement("select * " +
                        "from usertext " +
                        "where user_text_verifystate=1 " +
                        "and user_text_isdel=0 " +
                        "order by user_text_sendtime_desc," +
                        "user_text_id desc limit ?,?");
                ps.setInt(1, Configuration.pageshow * (page - 1));
                ps.setInt(2, Configuration.pageshow * page);
            } else {
                ps = conn.prepareStatement("select * " +
                        "from usertext " +
                        "where user_text_name like ? " +
                        "and user_text_verifystate=1 " +
                        "and user_text_isdel=0 " +
                        "order by user_text_sendtime_desc," +
                        "user_id asc limit ?,?");
                ps.setString(1, "%" + data + "%");
                ps.setInt(2, Configuration.pageshow * (page - 1));
                ps.setInt(3, Configuration.pageshow * page);
            }
        } else {
            throw new Exception();
        }
        ResultSet rs = ps.executeQuery();
        ArrayList<UserTextUser> textlist = new ArrayList<UserTextUser>();
        while (rs.next()) {
            UserTextUser usertext = new UserTextUser();
            usertext.setUser_text_id(rs.getString("user_text_id"));
            usertext.setUser_text_uid(rs.getString("user_text_uid"));
            usertext.setUser_text_name(rs.getString("user_text_name"));
            usertext.setUser_text_summary(rs.getString("user_text_summary"));
            usertext.setUser_text_text(rs.getString("user_text_text"));
            usertext.setUser_text_image(rs.getString("user_text_image"));
            usertext.setUser_text_sendtime(rs.getString("user_text_sendtime"));
            usertext.setUser_text_verifystate(rs.getString("user_text_verifystate"));
            usertext.setUser_text_isdel(rs.getString("user_text_isdel"));
            usertext.setUser_id(rs.getString("user_id"));
            usertext.setUser_name(rs.getString("user_name"));
            usertext.setUser_level(rs.getString("user_level"));
            usertext.setUser_integral(rs.getString("user_integral"));
            usertext.setUser_email(rs.getString("user_email"));
            usertext.setUser_img(rs.getString("user_img"));
            usertext.setUser_textnum(rs.getString("user_textnum"));
            textlist.add(usertext);
        }
        return textlist;
    }

    //    会员发布文章
    public void usersendtext(String userid,
                             String name,
                             String summary,
                             String text,
                             String image,
                             String sendtime,
                             Connection conn) throws Exception {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO " +
                "`user_text`(" +
                "`user_text_uid`, " +
                "`user_text_name`," +
                " `user_text_summary`, " +
                "`user_text_text`, " +
                "`user_text_image`, " +
                "`user_text_sendtime`) " +
                "VALUES (?,?,?,?,?,?)");
        ps.setString(1, userid);
        ps.setString(2, name);
        ps.setString(3, summary);
        ps.setString(4, text);
        ps.setString(5, image);
        ps.setString(6, sendtime);
        int count = ps.executeUpdate();
        if (count == 0) {
            throw new Exception();
        }
    }

    //    会员上架或下架文章or取消申请（下架）
    public void changetextstatus(String id, String type, Connection conn) throws Exception {
        PreparedStatement ps = conn.prepareStatement("update user_text set user_text_isdel=? where user_text_id=?");
        ps.setString(1, type);
        ps.setString(2, id);
        int count = ps.executeUpdate();
        if (count == 0) {
            throw new Exception();
        }
    }

    //    查看某会员发布的文章（会员查看自己的文章）
    public ArrayList<UserText> getmytextarr(String userid, String type, int page, Connection conn) throws Exception {
        PreparedStatement ps = null;
        if (type.equals("0")) {
            ps = conn.prepareStatement("select * " +
                    "from user_text " +
                    "where user_text_uid=? " +
                    "and user_text_verifystate=1 " +
                    "order by user_text_sendtime desc," +
                    "user_text_id desc limit ?,?");
            ps.setString(1, userid);
            ps.setInt(2, Configuration.pageshow * (page - 1));
            ps.setInt(3, Configuration.pageshow * page);
        } else if (type.equals("1")) {
            ps = conn.prepareStatement("select * " +
                    "from user_text " +
                    "where user_text_verifystate IN (0,2) " +
                    "and user_text_uid=?  " +
                    "order by user_text_verifystate asc," +
                    "user_text_isdel asc ," +
                    "user_text_id desc limit ?,?");
            ps.setString(1, userid);
            ps.setInt(2, Configuration.pageshow * (page - 1));
            ps.setInt(3, Configuration.pageshow * page);
        } else {
            throw new Exception();
        }
        ResultSet rs = ps.executeQuery();
        ArrayList<UserText> textlist = new ArrayList<UserText>();
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
            textlist.add(utext);
        }
        return textlist;
    }

    //    会员文章数
    public String getmytextcount(String userid,String type, Connection conn) throws Exception {
        PreparedStatement ps = null;
        if (type.equals("0")) {
            ps = conn.prepareStatement("select count(1) as c " +
                    "from user_text " +
                    "where user_text_verifystate=1 " +
                    "and user_text_uid=?");
            ps.setString(1, userid);
        } else if (type.equals("1")) {
            ps = conn.prepareStatement("select count(1) as c " +
                    "from user_text " +
                    "where user_text_verifystate IN (0,2) " +
                    "and user_text_uid=? ");
            ps.setString(1, userid);
        } else {
            throw new Exception();
        }
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("c");
    }



    //    查看文章详情
    public UserTextUser gettextinfo(String id, Connection conn) throws Exception {
        PreparedStatement ps = conn.prepareStatement("select * from usertext where user_text_id=?");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        UserTextUser usertext = new UserTextUser();
        while (rs.next()) {
            usertext.setUser_text_id(rs.getString("user_text_id"));
            usertext.setUser_text_uid(rs.getString("user_text_uid"));
            usertext.setUser_text_name(rs.getString("user_text_name"));
            usertext.setUser_text_summary(rs.getString("user_text_summary"));
            usertext.setUser_text_text(rs.getString("user_text_text"));
            usertext.setUser_text_image(rs.getString("user_text_image"));
            usertext.setUser_text_sendtime(rs.getString("user_text_sendtime"));
            usertext.setUser_text_verifystate(rs.getString("user_text_verifystate"));
            usertext.setUser_text_isdel(rs.getString("user_text_isdel"));
            usertext.setUser_id(rs.getString("user_id"));
            usertext.setUser_name(rs.getString("user_name"));
            usertext.setUser_level(rs.getString("user_level"));
            usertext.setUser_integral(rs.getString("user_integral"));
            usertext.setUser_email(rs.getString("user_email"));
            usertext.setUser_img(rs.getString("user_img"));
            usertext.setUser_textnum(rs.getString("user_textnum"));
        }
        return usertext;
    }


    //    管理员获取待审核文章
    public ArrayList<UserTextUser> getusertext(int page, String state, Connection conn) throws Exception {
        PreparedStatement ps = conn.prepareStatement("select * " +
                "from usertext " +
                "where user_text_verifystate=? and user_text_isdel=0 " +
                "order by user_text_sendtime asc,user_text_uid asc limit ?,?");
        ps.setString(1, state);
        ps.setInt(2, Configuration.pageshow * (page - 1));
        ps.setInt(3, Configuration.pageshow * page);
        ResultSet rs = ps.executeQuery();
        ArrayList<UserTextUser> textlist = new ArrayList<UserTextUser>();
        while (rs.next()) {
            UserTextUser usertext = new UserTextUser();
            usertext.setUser_text_id(rs.getString("user_text_id"));
            usertext.setUser_text_name(rs.getString("user_text_name"));
            usertext.setUser_text_summary(rs.getString("user_text_summary"));
            usertext.setUser_text_image(rs.getString("user_text_image"));
            usertext.setUser_text_sendtime(rs.getString("user_text_sendtime"));
            usertext.setUser_text_verifystate(rs.getString("user_text_verifystate"));
            usertext.setUser_id(rs.getString("user_id"));
            usertext.setUser_name(rs.getString("user_name"));
            usertext.setUser_level(rs.getString("user_level"));
            usertext.setUser_integral(rs.getString("user_integral"));
            usertext.setUser_email(rs.getString("user_email"));
            usertext.setUser_img(rs.getString("user_img"));
            usertext.setUser_textnum(rs.getString("user_textnum"));
            textlist.add(usertext);
        }
        return textlist;
    }

    //    文章数
    public String getusertextcount(String state, Connection conn) throws Exception {
        PreparedStatement ps = conn.prepareStatement("select count(1) as c " +
                "from usertext " +
                "where user_text_verifystate=? and user_text_isdel=0");
        ps.setString(1, state);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("c");
    }

    //    审核文章
    public void audittext(String textid, String state, Connection conn) throws Exception {
        PreparedStatement ps = conn.prepareStatement("update user_text set user_text_verifystate=? where user_text_id=?");
        ps.setString(1, state);
        ps.setString(2, textid);
        int count = ps.executeUpdate();
        if (count == 0) {
            throw new Exception();
        }
    }

    //    搜索会员文章
    public ArrayList<UserTextUser> searchusertext(String data, int page, Connection conn) throws Exception {
        PreparedStatement ps =null;
        if (data.equals("") || data == null) {
            ps= conn.prepareStatement("SELECT * FROM " +
                    "`usertext` WHERE  " +
                    "user_text_isdel=0 and user_text_verifystate=1 " +
                    "order by user_id asc " +
                    "limit ?,?");
            ps.setInt(1, Configuration.pageshow * (page - 1));
            ps.setInt(2, Configuration.pageshow * page);
        } else {
            ps= conn.prepareStatement("SELECT * FROM " +
                    "`usertext` WHERE  " +
                    "(`user_text_name` like ? or `user_name` like ? ) and " +
                    "user_text_isdel=0 and user_text_verifystate=1 " +
                    "order by user_id asc " +
                    "limit ?,?");
            ps.setString(1, "%" + data + "%");
            ps.setString(2, "%" + data + "%");
            ps.setInt(3, Configuration.pageshow * (page - 1));
            ps.setInt(4, Configuration.pageshow * page);
        }
        ResultSet rs = ps.executeQuery();
        ArrayList<UserTextUser> textlist = new ArrayList<UserTextUser>();
        while (rs.next()) {
            UserTextUser usertext = new UserTextUser();
            usertext.setUser_text_id(rs.getString("user_text_id"));
            usertext.setUser_text_name(rs.getString("user_text_name"));
            usertext.setUser_text_summary(rs.getString("user_text_summary"));
            usertext.setUser_text_image(rs.getString("user_text_image"));
            usertext.setUser_text_sendtime(rs.getString("user_text_sendtime"));
            usertext.setUser_text_verifystate(rs.getString("user_text_verifystate"));
            usertext.setUser_id(rs.getString("user_id"));
            usertext.setUser_name(rs.getString("user_name"));
            usertext.setUser_level(rs.getString("user_level"));
            usertext.setUser_integral(rs.getString("user_integral"));
            usertext.setUser_email(rs.getString("user_email"));
            usertext.setUser_img(rs.getString("user_img"));
            usertext.setUser_textnum(rs.getString("user_textnum"));
            textlist.add(usertext);
        }
        return textlist;
    }
    //    搜索会员文章--数目
    public String searchusertextcount(String data, Connection conn) throws Exception {
        PreparedStatement ps =null;
        if (data.equals("") || data == null) {
            ps= conn.prepareStatement("SELECT count(1) as c FROM " +
                    "`usertext` WHERE  " +
                    "user_text_isdel=0 and user_text_verifystate=1");
        } else {
            ps= conn.prepareStatement("SELECT count(1) as c FROM " +
                    "`usertext` WHERE  " +
                    "(`user_text_name` like ? or `user_name` like ? ) and " +
                    "user_text_isdel=0");
            ps.setString(1, "%" + data + "%");
            ps.setString(2, "%" + data + "%");
        }
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("c");
    }
}
