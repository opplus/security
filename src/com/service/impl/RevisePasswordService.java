package com.service.impl;

import com.commonfunction.Check;
import com.commonfunction.MD5;
import com.commonfunction.ServiceException;
import com.commonfunction.mail;
import com.dao.UserDao;
import com.model.RevisePass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wjh on 2016/3/15.
 */
@Service
public class RevisePasswordService {

    @Autowired
    private UserDao udao;

    /**
     * 发送密码重置邮件
     */
    public void processrevisemail(String email, String passcode, Connection conn) throws ParseException, Exception {
        boolean flag = udao.checkemail(email, conn);//用户是已经注册
        if (flag) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String sendtime = sdf.format(new Date());//发送时间
            int expiretime1 = Integer.parseInt(sendtime) + 1;
            String expiretime = String.valueOf(expiretime1);//过期时间
            udao.insertverifypass(email, passcode, expiretime, sendtime, conn);//生成邮件激活状态
            //邮件的内容
            StringBuffer content = new StringBuffer("点击下面链接修改账号密码，24小时内有效，否则重新发送连接，链接只能使用一次，请尽快激活！</br>");
            content.append("<a href=\"http://localhost:8094/user/torevisepass?passcode=");
            content.append(passcode);
            content.append("\">http://localhost:8094/user/torevisepass?passcode=");
            content.append(passcode);
            content.append("</a>");
            String title = "账户密码重置邮件";
            String from = "13757136643@163.com";
            //发送邮件
            mail.sendmail(title, content.toString(), email, from);
            System.out.println("发送邮件");
        } else {
            throw new ServiceException("您还没有注册，请注册之后再执行该操作！");
        }

    }

    public boolean processrevise(String passcode, String password, String repassword, Connection conn) throws Exception {
        boolean flag = false;
        //数据访问层，通过email获取用户信息
        RevisePass rp = udao.getrevisemail(passcode, conn);
        String state= rp.getVerify_password_state();
        String id= rp.getVerify_password_id();
        if (state.equals("0")) {
//        验证密码否一致
            if (password.equals(repassword)) {
//            验证密码格式是否符合要求
                if (Check.ispassword(password)) {
                    Date currentTime = new Date();//获取当前时间
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    Date expiretime = sdf.parse(rp.getVerify_password_expiretime());
                    //验证链接是否过期
                    if (currentTime.before(expiretime)) {
                        String email = rp.getVerify_password_email();
                        String pwd = MD5.encode2hex(password);
                        udao.updatepass(email, pwd, id, conn);
                        flag = true;
                    } else {
                        throw new ServiceException("该链接已过期");
                    }
                } else {
                    throw new ServiceException("密码格式不符合要求");
                }
            } else {
                throw new ServiceException("两次密码不一致");
            }
        } else {
            throw new ServiceException("该链接已失效");
        }
        return flag;
    }
}
