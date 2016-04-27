package com.commonfunction;

import java.util.Date;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Authenticator;
/**
 * Created by tzry on 16/3/2.
 */
public class mail {
    //发送邮件
    public static String sendmail(String title,String content,String to,String from) {
      /*  try {
            Properties props = new Properties();
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.host", "smtp.163.com");
            props.setProperty("mail.smtp.auth", "true");
            Session session = Session.getInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("13757136643", "wjh930125");
                        }
                    }
            );
            session.setDebug(true);
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(RecipientType.TO, InternetAddress.parse(to));
            msg.setSubject(title);
            msg.setSentDate(new Date(100000));
            msg.setContent(content, "text/html;charset=utf-8");
            Transport.send(msg);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }*/
        try{
            Properties prop = new Properties();
//            prop.setProperty("mail.host", "localhost");
            prop.setProperty("mail.host", "smtp.163.com");
            prop.setProperty("mail.transport.protocol", "smtp");
            prop.setProperty("mail.smtp.auth", "false");
//            prop.setProperty("mail.smtp.auth", "false");
            //使用JavaMail发送邮件的5个步骤
            //1、创建session
            Session session = Session.getInstance(prop);
            //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
            session.setDebug(true);
            //2、通过session得到transport对象
            Transport ts = session.getTransport();
            //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
            ts.connect("smtp.163.com","13757136643", "wjh930125");
            //4、创建邮件
            Message message = createSimpleMail(session,title,content,to,from);
            //5、发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();
            return "success";
        }
        catch(Exception e){
            return e.getMessage();
        }

    }
    public static MimeMessage createSimpleMail(Session session,String title,String content,String to,String from)
            throws Exception {
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人
        message.setFrom(new InternetAddress(from));
        //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        message.setRecipients(RecipientType.TO, InternetAddress.parse(to));
        //邮件的标题
        message.setSubject(title);
        //邮件的文本内容
        message.setContent(content, "text/html;charset=UTF-8");
        //返回创建好的邮件对象
        return message;
    }
}
