package com.service.impl;

import com.commonfunction.*;
import com.dao.ConnectionManager;
import com.dao.SubscribeDao;
import com.model.News;
import com.model.Subscribe;
import com.model.SubscribeVerify;
import com.model.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.sql.Connection;

/**
 * Created by wjh on 2016/4/9.
 */
@Service
public class SubscribeService {

    @Autowired
    private SubscribeDao sdao;

    //    发送订阅确认邮件
    public void subscribenews(String type, String uid, String email, Connection conn) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String sendtime = sdf.format(new Date());//发送时间
        int expiretime1 = Integer.parseInt(sendtime) + 1;
        String expiretime = String.valueOf(expiretime1);//过期时间
        String mailcode = MD5.encode2hex(RegisterCode.getRegisterCode());
        String realtype = "";
        if (type.equals("国内新闻")) {
            realtype = "0";
        } else if (type.equals("国外新闻")) {
            realtype = "1";
        } else if (type.equals("安全工具")) {
            realtype = "2";
        } else {
            throw new Exception();
        }
        try {
            sdao.gensubscribeverify(mailcode, realtype, uid, email, sendtime, expiretime, conn);//生成验证
            ///邮件的内容
            StringBuffer content = new StringBuffer("点击下面链接激活订阅，24小时内有效，否则重新订阅，链接只能使用一次，请尽快激活！</br>");
            content.append("<a href=\"http://localhost:8094/user/subscribe/toconfirm?email=");
            content.append(email);
            content.append("&code=");
            content.append(mailcode);
            content.append("\">http://localhost:8094/user/subscribe/toconfirm?email=");
            content.append(email);
            content.append("&code=");
            content.append(mailcode);
            content.append("</a>");
            String title = "确认订阅邮件";
            String from = "13757136643@163.com";
            //发送邮件
            mail.sendmail(title, content.toString(), email, from);
            System.out.println("发送邮件");
        } catch (Exception e) {
            throw new Exception();
        }
    }

    //    激活
    public boolean processActivate(String email, String validatecode, String type, Connection conn) throws Exception {
        boolean flag = false;
        //数据访问层，通过email获取用户信息
        SubscribeVerify sv = sdao.select(email, validatecode, conn);
        //验证用户是否存在
        if (sv != null) {
            String mail = sv.getSubscribe_verify_email();
            String realtype = sv.getSubscribe_verify_type();
            String id = sv.getSubscribe_verify_id();
            boolean flag2 = sdao.checkmail(mail, realtype, conn);
            //验证用户激活状态
            if (sv.getSubscribe_verify_state().equals("0") || !flag2) {
                ///没激活
                Date currentTime = new Date();//获取当前时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                Date expiretime = sdf.parse(sv.getSubscribe_verify_expiretime());
                //验证链接是否过期
                if (currentTime.before(expiretime)) {
                    //验证激活码是否正确
                    if (validatecode.equals(sv.getSubscribe_verify_code())) {
                        //激活成功， //并更新用户的激活状态，为已激活
                        if (type.equals("0")) {
                            String uid = sv.getSubscribe_verify_uid();
                            String svtype = sv.getSubscribe_verify_type();
                            sdao.gensubscribe(svtype, uid, mail, conn);
                            sdao.updateverify(id, conn);
                            flag = true;
                        } else {
                            sdao.giveup(id, conn);
                        }
                    } else {
                        throw new ServiceException("激活码不正确");
                    }
                } else {
                    throw new ServiceException("激活码已过期！");
                }
            } else {
                throw new ServiceException("您已激活");
            }
        } else {
            throw new ServiceException("请前往首页填写订阅信息");
        }
        return flag;
    }

    //    系统发送订阅文章
    @Scheduled(cron="0 0 8 * * * ")   //每天早上8点推送
//    @Scheduled(cron="0/10 * * * * ? ")   //每5秒执行一次
    public void sendsubscribe() throws Exception {
        HashMap hm1 = new HashMap();
        HashMap hm2 = new HashMap();
        ArrayList<Subscribe> ss0list = new ArrayList<Subscribe>();
        ArrayList<Subscribe> ss1list = new ArrayList<Subscribe>();
        ArrayList<Subscribe> ss2list = new ArrayList<Subscribe>();
        ArrayList<News> news0list = new ArrayList<News>();
        ArrayList<News> news1list = new ArrayList<News>();
        ArrayList<Tools> toolslist = new ArrayList<Tools>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String time = sdf.format(new Date());//发送时间
        int sendtime1 = Integer.parseInt(time) - 1;
        String sendtime = String.valueOf(sendtime1);//过期时间
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            hm1 = sdao.selectsubscribelist(conn);
            ss0list = (ArrayList) hm1.get("ss0list");
            ss1list = (ArrayList) hm1.get("ss1list");
            ss2list = (ArrayList) hm1.get("ss2list");
            hm2 = sdao.selectnews(sendtime, conn);
            news0list = (ArrayList) hm2.get("news0list");
            news1list = (ArrayList) hm2.get("news1list");
            toolslist = (ArrayList) hm2.get("toolslist");
            sendsubscribnewsemail(ss0list,news0list,conn);
            sendsubscribnewsemail(ss1list,news1list,conn);
            sendsubscribtoolsemail(ss2list,toolslist,conn);
        } catch (Exception e) {
            System.out.println("系统故障");
        } finally {
            conn.close();
        }
    }


    //    发送订阅新闻
    public void sendsubscribnewsemail(ArrayList<Subscribe> sslist, ArrayList<News> newslist, Connection conn) throws Exception {
        for (Subscribe ss : sslist) {
            String email = ss.getSubscribe_email();
            StringBuffer content = new StringBuffer("<h2>昨日热点新闻：</h2><br><hr><table id=\"news\">");
            for (News news : newslist) {
                String id = news.getNews_id();
                String img = news.getNews_img();
                String sendtime = news.getNews_sendtime();
                String text = news.getNews_text();
                String title = news.getNews_title();
                String url = news.getNews_url();
                ///邮件的内容
                content.append("<tr><td rowspan=\"2\"><img src=\"http://localhost:8094");
                content.append(img);
                content.append("\" width=\"110\" height=\"90\"/></td>");
                content.append("<td id=\"title\" valign=\"bottom\" align=\"left\">");
                content.append("<a href=\"http://localhost:8094/news/newsdetails?newsid=");
                content.append(id);
                content.append("&type=");
                content.append("0");
                content.append("\" class=\"detail\">");
                content.append(title);
                content.append("</a></td></tr><tr>");
                content.append("<td valign=\"top\" align=\"left\"><span class=\"time\" style=\"color:#CDBA96;font-size:15px;font-family:\"宋体\";\"");
                content.append(">发布时间：");
                content.append(sendtime);
                content.append("</span></td></tr>");
            }
            content.append("</table");
            try {
                String mailtitle = "安全网新闻订阅邮件";
                String from = "13757136643@163.com";
                //发送邮件
                mail.sendmail(mailtitle, content.toString(), email, from);
                System.out.println("发送邮件");
            } catch (Exception e) {
                throw new Exception();
            }
        }
    }
    public void sendsubscribtoolsemail(ArrayList<Subscribe> sslist, ArrayList<Tools> toolslist, Connection conn) throws Exception {
        for (Subscribe ss : sslist) {
            String email = ss.getSubscribe_email();
            StringBuffer content = new StringBuffer("<h2>昨日热点安全工具：</h2><br><hr><table id=\"news\">");
            for (Tools tools : toolslist) {
                String id = tools.getTools_id();
                String img = tools.getTools_img();
                String title= tools.getTools_description();
                String sendtime = tools.getTools_sendtime();
                ///邮件的内容
                content.append("<tr><td rowspan=\"2\"><img src=\"http://localhost:8094");
                content.append(img);
                content.append("\" width=\"110\" height=\"90\"/></td>");
                content.append("<td id=\"title\" valign=\"bottom\" align=\"left\">");
                content.append("<a href=\"http://localhost:8094/news/newsdetails?newsid=");
                content.append(id);
                content.append("&type=");
                content.append("1");
                content.append("\" class=\"detail\">");
                content.append(title);
                content.append("</a></td></tr><tr>");
                content.append("<td valign=\"top\" align=\"left\"><span class=\"time\" style=\"color:#CDBA96;font-size:15px;font-family:\"宋体\";\"");
                content.append(">发布时间：");
                content.append(sendtime);
                content.append("</span></td></tr>");
            }
            content.append("</table");
            try {
                String mailtitle = "安全网安全工具订阅邮件";
                String from = "13757136643@163.com";
                //发送邮件
                mail.sendmail(mailtitle, content.toString(), email, from);
                System.out.println("发送邮件");
            } catch (Exception e) {
                throw new Exception();
            }
        }
    }
}
