package com.controller;

import com.commonfunction.Check;
import com.commonfunction.Errorcode;
import com.commonfunction.ServiceException;
import com.dao.ConnectionManager;
import com.dao.SubscribeDao;
import com.model.Subscribe;
import com.model.SubscribeVerify;
import com.google.gson.Gson;
import com.service.impl.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wjh on 2016/4/9.
 */
@Controller
@RequestMapping("/user/subscribe")
public class SubscribeController {

    @Autowired
    private SubscribeService subscribeservice;
    @Autowired
    private SubscribeDao sdao;

    // 发送激活邮件
    @RequestMapping(value = "/subscribenews")
    public void subscribeNews(HttpServletRequest request, HttpServletResponse response) throws Exception {
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        HashMap hm = new HashMap();
        HttpSession session = request.getSession();
        String userid = (String) session.getAttribute("userid");
        userid = "1";
        try {
            if (userid.equals("") || userid == null) {
                hm.put("state", Errorcode.LOGIN_EXPIRED);
            } else {
                String mail = (String) request.getParameter("mail");
                String remail = (String) request.getParameter("remail");
                boolean flag = Check.ismail(mail);
                if (flag && mail.equals(remail)) {
                    String type = (String) request.getParameter("type");
                    try {
                        subscribeservice.subscribenews(type, userid, mail, conn);
                    } catch (ServiceException se) {
                        hm.put("state", se.getMessage());
                    }
                    hm.put("state", "已发送订阅激活邮件至您的邮箱，请前往邮箱激活！");
                } else {
                    hm.put("state", "两次填写的邮箱必须相同且格式必须正确");
                }
            }
        } catch (Exception e) {
            hm.put("state", "系统故障");
        } finally {
            conn.close();
        }
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String str = gson.toJson(hm);
        out.print(str);
        out.flush();
        out.close();
    }

    //    去激活页
    @RequestMapping(value = "/toconfirm")
    public ModelAndView toconfirm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        ModelAndView mav = new ModelAndView();
        String mail = (String) request.getParameter("email");
        String code = (String) request.getParameter("code");
        mav.addObject("email", mail);
        mav.addObject("mailcode", code);
        mav.setViewName("user/subscribe/confirm");
        return mav;
    }


    //    激活
    @RequestMapping(value = "/confirm")
    public ModelAndView Confirm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        ModelAndView mav = new ModelAndView();
        String mail = (String) request.getParameter("email");
        try {
            boolean flag1 = Check.ismail(mail);
            if (flag1) {
                String type = (String) request.getParameter("type");
                String code = (String) request.getParameter("code");
                    boolean flag = subscribeservice.processActivate(mail, code, type,conn);
                    if (flag) {
                        mav.setViewName("user/subscribe/subscribeconfirmsucc");
                    } else {
                        mav.setViewName("user/subscribe/giveupsubscribe");
                    }
                } else {
                    mav.addObject("message", "邮箱信格式不正确");
                    mav.setViewName("user/subscribe/subscribeconfirmfail");
                }
            }catch(Exception e){
                mav.addObject("message", e.getMessage());
                mav.setViewName("user/subscribe/subscribeconfirmfail");
            }finally{
                conn.close();
            }
            return mav;
        }

        //    取消订阅
        @RequestMapping(value = "/cancle")
        public ModelAndView cancleSubscribe (HttpServletRequest request, HttpServletResponse response)throws Exception {
            java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
            ModelAndView mav = new ModelAndView();
            HttpSession session = request.getSession();
            String userid = (String) session.getAttribute("userid");
            userid = "1";
            String type = (String) request.getParameter("type");
            try {
                sdao.cancle(userid, type, conn);
                mav.setViewName("user/subscribe/canclesuc");
            } catch (Exception e) {
                mav.addObject("message", e.getMessage());
                mav.setViewName("user/subscribe/canclefail");
            } finally {
                conn.close();
            }
            return mav;

        }


    //    取消订阅
    @RequestMapping(value = "/test")
    public void test (HttpServletRequest request, HttpServletResponse response)throws Exception {
        subscribeservice.sendsubscribe();
    }
    }
