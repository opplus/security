package com.controller;

/**
 * Created by wjh on 2016/3/12.
 */

import com.commonfunction.*;
import com.dao.ConnectionManager;
import com.dao.UserDao;
import com.google.gson.Gson;
import com.model.User;
import com.service.impl.RegisterValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterValidateService service;
    @Autowired
    private UserDao udao;
    @Autowired
    private User user;

    //    去注册页
    @RequestMapping(value = "/toregister")
    public String toRegister() {
        return "user/register/register";
    }

    //    验证码
    @RequestMapping(value = "/register/code")
    public void Code(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RegisterCode.getCode(request, response);
    }

    //    校验邮箱是否已被注册
    @RequestMapping(value = "/checkEmail")
    public void checkEmail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        Gson gson = new Gson();
        HashMap hm = new HashMap();
        String email = (String) request.getParameter("email");
        try {
            boolean flag = udao.checkemail(email, conn); //检验邮箱是否已被注册
            if (!flag) {
                hm.put("result", 1);//1代表邮箱没有被注册
            } else {
                hm.put("result", 0);//0代表邮箱已被注册
            }
            hm.put("state", Errorcode.SUCCESS);
            conn.close();
        } catch (Exception e) {
            hm.put("state", Errorcode.SYSTEM_ERROR);
        }
        String str = gson.toJson(hm);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(str);
        out.flush();
        out.close();
    }

    //    校验密码是否符合要求
    @RequestMapping(value = "/checkPassword")
    public void checkPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Gson gson = new Gson();
        HashMap hm = new HashMap();
        String password = (String) request.getParameter("password");
        try {
            boolean flag = Check.ispassword(password);
            if (flag) {
                hm.put("result", "1");//1代表密码格式正确
            } else {
                hm.put("result", "0");//0s代表密码格式错误
            }
            hm.put("state", Errorcode.SUCCESS);
        } catch (Exception e) {
            hm.put("state", Errorcode.SYSTEM_ERROR);
        }
        String str = gson.toJson(hm);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(str);
        out.flush();
        out.close();
    }

    //   确认验证码是否正确
    @RequestMapping(value = "/checkCode")
    public void checkCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Gson gson = new Gson();
        HashMap hm = new HashMap();
        HttpSession session = request.getSession();
        try {
            String code = (String) request.getParameter("code");
            if ((code.equalsIgnoreCase((String) session.getAttribute("code")))) {
                hm.put("result", "1");//1代表验证码正确
            } else {
                hm.put("result", "0");//0代表验证码错误
            }
            hm.put("state", Errorcode.SUCCESS);
        } catch (Exception e) {
            hm.put("state", Errorcode.SYSTEM_ERROR);
        }
        String str = gson.toJson(hm);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(str);
        out.flush();
        out.close();
    }

    //   验证邮箱格式是否正确
    @RequestMapping(value = "/checkMailcode")
    public void checkMailcode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Gson gson = new Gson();
        HashMap hm = new HashMap();
        String email = (String) request.getParameter("email");
        try {
            boolean flag = Check.ismail(email);
            String responseEmailcode = "";
            if (flag) {
                hm.put("result", "1");//1代表邮箱格式正确
            } else {
                hm.put("result", "0");//0s代表邮箱格式错误
            }
            hm.put("state", Errorcode.SUCCESS);
        } catch (Exception e) {
            hm.put("state", Errorcode.SYSTEM_ERROR);
        }
        String str = gson.toJson(hm);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(str);
        out.flush();
        out.close();
    }

    //   用户注册验证
    @RequestMapping(value = "/checkRegister", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView checkRegister(HttpServletRequest request, HttpServletResponse response) throws ParseException, Exception {
        String action = request.getParameter("action");
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        HttpSession session = request.getSession();
        System.out.println("-----r----" + action);
        ModelAndView mav = new ModelAndView();
        try {
            if ("register".equals(action)) {
                //注册
                String password = (String) request.getParameter("password");
                String email = (String) request.getParameter("email");
                String code = (String) request.getParameter("code");
                boolean flag3 = Check.ispassword(password);//密码格式是否正确
                boolean flag2 = Check.ismail(email);//邮箱格式是否正确
                boolean flag1 = udao.checkemail(email, conn);//邮箱是否已被注册
                 if ((!(code.equalsIgnoreCase((String) session.getAttribute("code")))) || flag1 | password.equals("") || email.equals("") || !flag2 || !flag3) { //忽略验证码大小写
                     mav.setViewName("user/register/registerfail");
                 } else {
                     String pwd = MD5.encode2hex(password);
                     user.setUser_email(email);
                     user.setUser_password(pwd);
                     service.processregister(email, pwd, conn);//发邮箱激活
                     mav.addObject("email", email);
                     mav.setViewName("user/register/activemail");
                 }
            } else if ("activate".equals(action)) {
                //邮箱激活
                String email = request.getParameter("email");//获取email
                String validateCode = request.getParameter("validatecode");//激活码
                boolean flag2 = Check.ismail(email);//邮箱格式是否正确
                if (flag2) {
                    try {
                        boolean flag = service.processActivate(email, validateCode, conn);//是否激活
                        if (flag) {
                            session.setAttribute("email", email);
                            String davcode = MD5.encode2hex(email);
                            mav.addObject("davcode", davcode);
                            mav.setViewName("user/register/activate_success");
                        } else {
                            mav.setViewName("user/register/activate_failure");
                        }
                    } catch (ServiceException e) {
                        request.setAttribute("message", e.getMessage());
                        mav.setViewName("user/register/activate_failure");
                    }
                } else {
                    mav.setViewName("user/register/activate_failure");
                }
            }
        } catch (ServiceException se) {
                request.setAttribute("message", se.getMessage());
                mav.setViewName("user/register/registerfail");
        } finally {
            conn.close();
        }
        return mav;
    }


    //   重发邮件
    @RequestMapping(value = "/register/resendMail")
    public ModelAndView resendMail(HttpServletRequest request, HttpServletResponse response) throws ParseException, Exception {
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        ModelAndView mav = new ModelAndView();
        String email = user.getUser_email();
        String pwd = user.getUser_password();
        boolean flag = Check.ismail(email);//邮箱格式是否正确
        try {
            if (flag) {
                service.processregister(email, pwd, conn);//发邮箱激活
                mav.addObject("email", email);
                mav.setViewName("user/register/activemail");
            } else {
                mav.addObject("email", email);
                mav.setViewName("user/register/registerfail");
            }
        } catch (Exception e) {
            mav.setViewName("user/register/registerfail");
        } finally {
            conn.close();
        }
        return mav;
    }
}