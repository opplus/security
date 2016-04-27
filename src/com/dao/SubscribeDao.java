package com.dao;

/**
 * Created by wjh on 2016/4/9.
 */

import com.commonfunction.Configuration;
import com.model.Subscribe;
import com.model.SubscribeVerify;
import com.model.News;
import com.model.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@Repository
public class SubscribeDao {
    //生成订阅
    public void gensubscribe(String type, String uid, String email, Connection conn) throws Exception {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO `subscribe`(`subscribe_type`, `subscribe_uid`, `subscribe_email`) VALUES (?,?,?)");
        ps.setString(1, type);
        ps.setString(2, uid);
        ps.setString(3, email);
        int count = ps.executeUpdate();
        if (count == 0) {
            throw new Exception();
        }
    }

    //生成订阅验证信息
    public void gensubscribeverify(String code, String type, String uid, String email, String sendtime, String expiretime, Connection conn) throws Exception {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO `subscribe_verify`(`subscribe_verify_type`, subscribe_verify_code,`subscribe_verify_uid`, `subscribe_verify_email`,subscribe_verify_sendtime,subscribe_verify_expiretime) VALUES (?,?,?,?,?,?)");
        ps.setString(1, type);
        ps.setString(2, code);
        ps.setString(3, uid);
        ps.setString(4, email);
        ps.setString(5, sendtime);
        ps.setString(6, expiretime);
        int count = ps.executeUpdate();
        if (count == 0) {
            throw new Exception();
        }
    }

    //获取订阅验证信息
    public SubscribeVerify select(String mail, String code, Connection conn) throws Exception {
        PreparedStatement ps = conn.prepareStatement("select * from subscribe_verify where subscribe_verify_email=? and subscribe_verify_code=?");
        ps.setString(1, mail);
        ps.setString(2, code);
        ResultSet rs = ps.executeQuery();
        SubscribeVerify sv = new SubscribeVerify();
        while (rs.next()) {
            sv.setSubscribe_verify_id(rs.getString("subscribe_verify_id"));
            sv.setSubscribe_verify_email(rs.getString("subscribe_verify_email"));
            sv.setSubscribe_verify_uid(rs.getString("subscribe_verify_uid"));
            sv.setSubscribe_verify_type(rs.getString("subscribe_verify_type"));
            sv.setSubscribe_verify_code(rs.getString("subscribe_verify_code"));
            sv.setSubscribe_verify_sendtime(rs.getString("subscribe_verify_sendtime"));
            sv.setSubscribe_verify_expiretime(rs.getString("subscribe_verify_expiretime"));
            sv.setSubscribe_verify_state(rs.getString("subscribe_verify_state"));
        }
        return sv;
    }

    //检查是否已订阅
    public boolean checkmail(String mail, String type, Connection conn) throws Exception {
        boolean flag = false;
        PreparedStatement ps = conn.prepareStatement("select count(1) as c from subscribe where subscribe_email=? and subscribe_type=?");
        ps.setString(1, mail);
        ps.setString(2, type);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String count = rs.getString("c");
        if (!count.equals("0")) {
            flag = true;
        }
        return flag;
    }

    //确认订阅
    public void updateverify(String id, Connection conn) throws Exception {
        PreparedStatement ps = conn.prepareStatement("update subscribe_verify set subscribe_verify_state=2 where subscribe_verify_id=?");
        ps.setString(1, id);
        int count = ps.executeUpdate();
        if (count == 0) {
            throw new Exception();
        }
    }

    //   放弃订阅
    public void giveup(String id, Connection conn) throws Exception {
        PreparedStatement ps = conn.prepareStatement("update subscribe_verify set subscribe_verify_state=1 where subscribe_verify_id=?");
        ps.setString(1, id);
        int count = ps.executeUpdate();
        if (count == 0) {
            throw new Exception();
        }
    }


    //    取消订阅
    public void cancle(String userid, String type, Connection conn) throws Exception {
        PreparedStatement ps = conn.prepareStatement("update subscribe set subscribe_state=1 where subscribe_uid=? and subscribe_type=?");
        ps.setString(1, userid);
        ps.setString(2, type);
        int count = ps.executeUpdate();
        if (count == 0) {
            throw new Exception();
        }
    }


    //    系统定时查看获取订阅用户列表
    public HashMap selectsubscribelist(Connection conn) throws Exception {
        PreparedStatement ps = conn.prepareStatement("select subscribe_type ,subscribe_email from subscribe where subscribe_state=0");
        ResultSet rs = ps.executeQuery();
        ArrayList<Subscribe> ss0list = new ArrayList<Subscribe>();
        ArrayList<Subscribe> ss1list = new ArrayList<Subscribe>();
        ArrayList<Subscribe> ss2list = new ArrayList<Subscribe>();
        HashMap hm = new HashMap();
        while (rs.next()) {
            Subscribe ss = new Subscribe();
            String type = rs.getString("subscribe_type");
            String email = rs.getString("subscribe_email");
            ss.setSubscribe_email(email);
            ss.setSubscribe_type(type);
            if (type.equals("0")) {
                ss0list.add(ss);
            } else if (type.equals("1")) {
                ss1list.add(ss);
            } else {
                ss2list.add(ss);
            }
        }
        hm.put("ss0list", ss0list);
        hm.put("ss1list", ss1list);
        hm.put("ss2list", ss2list);
        return hm;
    }

    //    系统根据订阅用户列表获取订阅文章
    public HashMap selectnews(String time, Connection conn) throws Exception {
        HashMap hm = new HashMap();
        PreparedStatement ps = conn.prepareStatement("select * from news where news_sendtime=? and news_isdel=0");
        ps.setString(1,time);
        ResultSet rs = ps.executeQuery();
        ArrayList<News> news0list = new ArrayList<News>();
        ArrayList<News> news1list = new ArrayList<News>();
        while (rs.next()) {
            News news = new News();
            String newstype = rs.getString("news_type");
            news.setNews_id(rs.getString("news_id"));
            news.setNews_url(rs.getString("news_url"));
            news.setNews_title(rs.getString("news_title"));
            news.setNews_text(rs.getString("news_text"));
            news.setNews_img(rs.getString("news_image"));
            news.setNews_sendtime(rs.getString("news_sendtime"));
            if (newstype.equals("0")) {
                news0list.add(news);
            } else {
                news1list.add(news);
            }
        }
        hm.put("news0list", news0list);
        hm.put("news1list", news1list);
        PreparedStatement ps1 = conn.prepareStatement("select * from tools where tools_sendtime=? and tools_isdel=0");
        ps1.setString(1,time);
        ResultSet rs1 = ps1.executeQuery();
        ArrayList<Tools> toolslist = new ArrayList<Tools>();
        while (rs1.next()) {
            Tools tools = new Tools();
            tools.setTools_id(rs1.getString("tools_id"));
            tools.setTools_name(rs1.getString("tools_name"));
            tools.setTools_description(rs1.getString("tools_description"));
            tools.setTools_summary(rs1.getString("tools_summary"));
            tools.setTools_sendtime(rs1.getString("tools_sendtime"));
            tools.setTools_url(rs1.getString("tools_url"));
            tools.setTools_img(rs1.getString("tools_img"));
            toolslist.add(tools);
        }
        hm.put("toolslist", toolslist);
        return hm;
    }

}
