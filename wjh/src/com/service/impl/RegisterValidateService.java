package com.service.impl;

/**
 * Created by wjh on 2016/3/12.
 */

import com.commonfunction.MD5;
import com.commonfunction.RegisterCode;
import com.commonfunction.ServiceException;
import com.commonfunction.mail;
import com.dao.UserDao;
import com.model.VerifyEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class RegisterValidateService {

    @Autowired
    private UserDao udao;

    /**
     * 发送注册邮件
     */

    public void processregister(String email,String password,Connection conn)throws ParseException,Exception{
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String sendtime=sdf.format(new Date());//发送时间
        int expiretime1=Integer.parseInt(sendtime)+1;
        String expiretime=String.valueOf(expiretime1);//过期时间
        String registercode= RegisterCode.getRegisterCode();//注册验证码
       try{
           udao.insertemail(email,password,sendtime,sendtime, MD5.encode2hex(registercode),expiretime,conn);//生成邮件激活状态
           ///邮件的内容
           StringBuffer content=new StringBuffer("点击下面链接激活账号，24小时内有效，否则重新注册账号，链接只能使用一次，请尽快激活！</br>");
           content.append("<a href=\"http://localhost:8094/register/checkRegister?action=activate&email=");
           content.append(email);
           content.append("&validatecode=");
           content.append(MD5.encode2hex(registercode));
           content.append("\">http://localhost:8094/register/checkRegister?action=activate&email=");
           content.append(email);
           content.append("&validatecode=");
           content.append(MD5.encode2hex(registercode));
           content.append("</a>");
           String title = "账户注册激活邮件";
           String from = "13757136643@163.com";
           //发送邮件
           mail.sendmail(title, content.toString(), email, from);
           System.out.println("发送邮件");
       }catch(Exception e){
            throw new ServiceException("邮件发送失败，请重试！");
        }
    }

    /**
     * 处理激活
     * @throws ParseException
     */
    ///传递激活码和email过来
    public boolean processActivate(String email , String validatecode,Connection conn)throws Exception, ParseException{
        boolean flag=false;
        //数据访问层，通过email获取用户信息
        VerifyEmail vemail=udao.getemailstate(conn,email);
        //验证用户是否存在
        if(vemail!=null) {
            boolean flag2=udao.checkemail(email, conn);
            //验证用户激活状态
            if(vemail.getVerify_email_state().equals("0")||!flag2) {
                ///没激活
                Date currentTime = new Date();//获取当前时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                Date expiretime= sdf.parse(vemail.getVerify_email_expiretime());
                //验证链接是否过期
                if(currentTime.before(expiretime)) {
                    //验证激活码是否正确
                    if(validatecode.equals(vemail.getVerify_email_code())) {
                        //激活成功， //并更新用户的激活状态，为已激活
                        String password=vemail.getVerify_email_password();
                        String time = vemail.getVerify_email_registertime();
                        String verifyemailid = vemail.getVerify_email_id();
                        String davcode = MD5.encode2hex(email);
                        udao.insertUser(password,email,time, conn);
                        udao.updateemail(verifyemailid ,conn);
                        flag=true;
                    } else {
                        throw new ServiceException("激活码不正确");
                    }
                } else { throw new ServiceException("激活码已过期！");
                }
            } else {
                throw new ServiceException("邮箱已激活，请登录！");
            }
        } else {
            throw new ServiceException("该邮箱未注册（邮箱地址不存在）！");
        }
        return flag;
    }
}