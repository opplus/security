package com.dao;


import com.model.RevisePass;
import com.model.User;
import com.model.VerifyEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class UserDao {
    //  登录验证
    public User checkuser(String email,
                          String password,
                          Connection conn) throws Exception {
        User user = new User();
        PreparedStatement ps = conn.prepareStatement("select * from user where user_email=? and user_password=?");
        ps.setString(1, email);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if(rs.next()==false){
            ps = conn.prepareStatement("select * from user where user_name=? and user_password=?");
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
        }
        while (rs.next()) {
            user.setUser_id(rs.getString("user_id"));
            user.setUser_email(rs.getString("user_email"));
            user.setUser_name(rs.getString("user_name"));
        }
        return user;
    }

    //		重置密码验证表
    public   void insertverifypass(String email, String passcode, String expiretime, String sendtime, Connection conn) throws Exception {
        conn.setAutoCommit(false);//取消自动提交事务.好处是中间做的事情是
        String sql = "insert verify_password (`verify_password_code`, `verify_password_sendtime`, `verify_password_expiretime`, `verify_password_email`) VALUES (?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, passcode);
        ps.setString(2, sendtime);
        ps.setString(3, expiretime);
        ps.setString(4, email);
        int count=ps.executeUpdate();
        conn.commit();//提交事务
        conn.setAutoCommit(true);//恢复原来的自动提交事务
        if(count==0){
            throw new Exception();
        }
    }

    //		获得需重置的邮箱根据passcode
    public  RevisePass getrevisemail(String passcode, Connection conn) throws  Exception {
        RevisePass rp=new RevisePass();
        String sql = "SELECT * FROM verify_password WHERE verify_password_code=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, passcode);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            rp.setVerify_password_email(rs.getString("verify_password_email"));
            rp.setVerify_password_id(rs.getString("verify_password_id"));
            rp.setVerify_password_sendtime(rs.getString("verify_password_sendtime"));
            rp.setVerify_password_expiretime(rs.getString("verify_password_expiretime"));
            rp.setVerify_password_state(rs.getString("verify_password_state"));
        }
        return rp;
    }

    //  修改密码
    public  void updatepass(String email, String password, String id,Connection conn) throws Exception {
        conn.setAutoCommit(false);//取消自动提交事务.好处是中间做的事情是
        String sql = "UPDATE user SET user_password=? WHERE user_email=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        PreparedStatement ps2 = conn.prepareStatement("UPDATE `verify_password` SET verify_password_state=1 WHERE verify_password_id=?");
        ps.setString(1, password);
        ps.setString(2, email);
        ps2.setString(1,id);
        int count=ps.executeUpdate();
        count+=ps2.executeUpdate();
        conn.commit();//提交事务
        conn.setAutoCommit(true);//恢复原来的自动提交事务
        if(count!=2){
            throw new Exception();
        }
    }

    //获取用户信息
    public  User getUserinfo(String userid, Connection conn) throws Exception {
        User user=new User();
        String sql = "SELECT * FROM user WHERE user_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, userid);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
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
        }
        return user;
    }


//    上传或修改用户头像
    public  void userhead(String userid,String img,Connection conn) throws Exception{
        conn.setAutoCommit(false);//取消自动提交事务.好处是中间做的事情是
        String sql = "UPDATE user SET user_img=? WHERE user_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, img);
        ps.setString(2, userid);
        int count=ps.executeUpdate();
        conn.commit();//提交事务
        conn.setAutoCommit(true);//恢复原来的自动提交事务
        if(count==0){
            throw new Exception();
        }
    }





  /*********************注********册*****************************************************/

    //	  验证邮箱是否存在
    public  boolean checkemail(String email, Connection conn) throws Exception{
        boolean flag = false;
        PreparedStatement ps = conn.prepareStatement("select * from user where user_email=?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            flag = true;
        }
        return flag;
    }
   public boolean checkname(String name,Connection conn)throws Exception{
       boolean flag = false;
       PreparedStatement ps = conn.prepareStatement("select * from user where user_name=?");
       ps.setString(1, name);
       ResultSet rs = ps.executeQuery();
       if (rs.next()) {
           flag = true;
       }
       return flag;
   }
    //    注册新用户
    public  void insertUser(String password, String email, String registertime, Connection conn) throws Exception {
        String sql = "INSERT INTO user (user_password,user_registertime,user_email)VALUES (?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, password);
        ps.setString(2, registertime);
        ps.setString(3, email);
        int count=ps.executeUpdate();
        if(count==0){
            throw new Exception();
        }
    }

    //	  生成邮箱验证信息
    public   void insertemail(String email, String password, String registertime, String emailsendtime, String code, String expiretime, Connection conn) throws Exception {
        String sql = "INSERT INTO `verify_email`(`verify_email_email`, `verify_email_password`, `verify_email_registertime`, `verify_email_sendtime`, `verify_email_expiretime`, `verify_email_code`) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, password);
        ps.setString(3, registertime);
        ps.setString(4, emailsendtime);
        ps.setString(5, expiretime);
        ps.setString(6, code);
        int count=ps.executeUpdate();
        if(count==0){
            throw new Exception();
        }
    }

    //	  改变邮箱验证状态
    public  void updateemail(String id, Connection conn) throws Exception {
        String sql = "UPDATE verify_email SET verify_email_state=1 WHERE verify_email_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, id);
        int count=ps.executeUpdate();
        if(count==0){
            throw new Exception();
        }
    }

    //    邮箱验证查询
    public   VerifyEmail getemailstate(Connection conn, String email) throws  Exception {
        VerifyEmail vemail=new VerifyEmail();
        String sql = "SELECT * FROM verify_email WHERE verify_email_email=? order by verify_email_id desc limit 0,1";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            vemail.setVerify_email_id(rs.getString("verify_email_id"));
            vemail.setVerify_email_email(rs.getString("verify_email_email"));
            vemail.setVerify_email_sendtime(rs.getString("verify_email_sendtime"));
            vemail.setVerify_email_state(rs.getString("verify_email_state"));
            vemail.setVerify_email_code(rs.getString("verify_email_code"));
            vemail.setVerify_email_expiretime(rs.getString("verify_email_expiretime"));
            vemail.setVerify_email_registertime(rs.getString("verify_email_registertime"));
            vemail.setVerify_email_password(rs.getString("verify_email_password"));
        }
        return vemail;
    }
}






