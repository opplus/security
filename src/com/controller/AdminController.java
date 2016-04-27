package com.controller;

import com.commonfunction.Errorcode;
import com.commonfunction.MD5;
import com.dao.ConnectionManager;
import com.dao.UserTextDao;
import com.google.gson.Gson;
import com.model.Admin;
import com.model.User;
import com.model.UserText;
import com.model.UserTextUser;
import com.service.impl.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by wjh on 2016/3/21.
 */
@Controller
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private UserTextDao utextdao;
    @Autowired
    private AdminService aservice;

    @RequestMapping(value = "/admintologin")
    public String tologin() {
        return "admin/adminlogin";
    }

    @RequestMapping(value = "")
    public String gologin() {
        return "admin/adminlogin";
    }

    //    管理员登录验证
    @RequestMapping(value = "/adminlogin")
    public ModelAndView adminLogin(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        session.setAttribute("adminid", "1");
        if ((String) session.getAttribute("adminid") == null) {
            java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
            String adminname = (String) request.getParameter("adminname");
            String adminpass = (String) request.getParameter("adminpassword");
            String admincode = (String) request.getParameter("admincode");
            if (adminpass == null || adminname == null || admincode == null) {
                mav.setViewName("admin/adminlogin");
            } else {
                String adminpwd = MD5.encode2hex(adminpass);
                if (adminpass.equals("") || adminname.equals("")) {
                    mav.addObject("state", "用户名和密码不能为空");
                    mav.setViewName("admin/adminlogin");
                } else {
                    if ((admincode.equalsIgnoreCase((String) session.getAttribute("code")))) {
                        try {
                            String adminid = aservice.adminlogin(adminname, adminpass, conn);
                            if (adminid.equals("")) {
                                mav.addObject("state", "用户名或密码错误");
                                mav.setViewName("admin/adminlogin");
                            } else {
                                session.setAttribute("adminid", adminid);
                                mav.setViewName("admin/admin");
                            }
                        } catch (Exception e) {
                            mav.addObject("state", Errorcode.SYSTEM_ERROR);
                            mav.setViewName("admin/adminlogin");
                        } finally {
                            conn.close();
                        }
                    } else {
                        mav.addObject("state", "验证码错误");
                        mav.setViewName("admin/adminlogin");
                    }
                }
            }
        } else {
            mav.setViewName("admin/admin");
        }
        return mav;
    }

    //    管理员退出登录
    @RequestMapping(value = "/adminout")
    public String outAdmin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        session.invalidate();
        return "admin/adminlogin";
    }

    // 添加管理员
    @RequestMapping(value = "/addadmin")
    public void addAdmin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        Gson gson = new Gson();
        HashMap hm = new HashMap();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String adminname = (String) request.getParameter("adminname");
        String pass = (String) request.getParameter("adminpassword");
        String repass = (String) request.getParameter("readminpassword");
        String perm = (String) request.getParameter("adminperm");
        if (!pass.equals(repass) || pass == null || pass.equals("") || adminname == null || adminname.equals("") || perm == null || perm.equals("")) {
            hm.put("error", Errorcode.SYSTEM_ERROR);
        } else {
            try {
                aservice.addadmins(adminname, pass, perm, conn);
                hm.put("state", Errorcode.SUCCESS);
            } catch (Exception e) {
                hm.put("error", Errorcode.SYSTEM_ERROR);
            } finally {
                conn.close();
            }
        }
        String str = gson.toJson(hm);
        out.print(str);
        out.flush();
        out.close();
    }

    //    改变管理员使用状态
    @RequestMapping(value = "/alteradminstate")
    public void alterAdminState(HttpServletRequest request, HttpServletResponse response) throws Exception {
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        Gson gson = new Gson();
        HashMap hm = new HashMap();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String adminid = (String) request.getParameter("adminid");
        String state = (String) request.getParameter("adminstate");
        String adminstate = "";
        if (state.equals("超级管理员")) {
            adminstate = "0";
        } else if (state.equals("正常")) {
            adminstate = "1";
        } else if (state.equals("停用")) {
            adminstate = "2";
        } else {
            hm.put("error", Errorcode.SYSTEM_ERROR);
        }
        try {
            aservice.alteradminState(adminid, adminstate, conn);
            hm.put("state", Errorcode.SUCCESS);
        } catch (Exception e) {
            hm.put("error", Errorcode.SYSTEM_ERROR);
        } finally {
            conn.close();
        }
        String str = gson.toJson(hm);
        out.print(str);
        out.flush();
        out.close();
    }

    //   更改管理员权限(删除或添加)
    @RequestMapping(value = "/alteradminperm")
    public void alterAdminPerm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        Gson gson = new Gson();
        HashMap hm = new HashMap();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String adminid = (String) request.getParameter("adminid");
        String perm = (String) request.getParameter("adminperm");
        String type = (String) request.getParameter("type");
        try {
            aservice.alteradminPerm(adminid, perm, type, conn);
            hm.put("state", Errorcode.SUCCESS);
        } catch (Exception e) {
            hm.put("error", Errorcode.SYSTEM_ERROR);
        } finally {
            conn.close();
        }
        String str = gson.toJson(hm);
        out.print(str);
        out.flush();
        out.close();
    }

    //    删除管理员
    @RequestMapping(value = "/deladmin")
    public void delAdmin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        Gson gson = new Gson();
        HashMap hm = new HashMap();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String adminid = (String) request.getParameter("adminid");
        try {
            aservice.deleteadmin(adminid, conn);
            hm.put("state", Errorcode.SUCCESS);
        } catch (Exception e) {
            hm.put("error", Errorcode.SYSTEM_ERROR);
        } finally {
            conn.close();
        }
        String str = gson.toJson(hm);
        out.print(str);
        out.flush();
        out.close();
    }


    //    获取管理员信息
    @RequestMapping(value = "/getadminlist")
    public void getAdminList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        HttpSession session = request.getSession();
        Gson gson = new Gson();
        HashMap hm = new HashMap();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String adminid = (String) session.getAttribute("adminid");
        String isall = (String) request.getParameter("isall");
        try {
            ArrayList<Admin> adminlist = aservice.getadminList(adminid, isall, conn);
            hm.put("adminlist", adminlist);
            hm.put("state", Errorcode.SUCCESS);
        } catch (Exception e) {
            hm.put("error", Errorcode.SYSTEM_ERROR);
        } finally {
            conn.close();
        }
        String str = gson.toJson(hm);
        out.print(str);
        out.flush();
        out.close();
    }

    //    获取会员信息
    @RequestMapping(value = "/userlist")
    public void getUserList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        Gson gson = new Gson();
        HashMap hm = new HashMap();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String level = (String) request.getParameter("level");
        int page = Integer.parseInt(request.getParameter("page"));
        try {
            ArrayList<User> userlist = aservice.getuserList(level, page, conn);
            String count = aservice.getuserCount(level, conn);
            hm.put("state", Errorcode.SUCCESS);
            hm.put("count", count);
            hm.put("page", page);
            hm.put("userlist", userlist);
        } catch (Exception e) {
            hm.put("error", Errorcode.SYSTEM_ERROR);
        } finally {
            conn.close();
        }
        String str = gson.toJson(hm);
        out.print(str);
        out.flush();
        out.close();
    }

    // 获取会员文章
    @RequestMapping(value = "/usertext/list")
    public void getUserTextList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        HashMap hm = new HashMap();
        String state = (String) request.getParameter("state");
        int page = Integer.parseInt(request.getParameter("page"));
        try {
            ArrayList<UserTextUser> textlist = utextdao.getusertext(page, state, conn);
            String count = utextdao.getusertextcount(state, conn);
            hm.put("state", Errorcode.SUCCESS);
            hm.put("count", count);
            hm.put("page", page);
            hm.put("textlist", textlist);
        } catch (Exception e) {
            hm.put("error", Errorcode.SYSTEM_ERROR);
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

    //    审核文章
    @RequestMapping(value = "/usertext/list/audit")
    public void auditUserText(HttpServletRequest request, HttpServletResponse response) throws Exception {
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        HashMap hm = new HashMap();
        String state = (String) request.getParameter("state");
        String textid = (String)request.getParameter("textid");
        if(state.equals("待审核")){
            state ="0";
        }else if(state.equals("通过")){
            state ="1";
        }
        else if(state.equals("不通过")){
            state ="2";
        } else{
            throw new Exception();
        }
        try {
            utextdao.audittext(textid, state, conn);
            hm.put("state", Errorcode.SUCCESS);
        } catch (Exception e) {
            hm.put("error", Errorcode.SYSTEM_ERROR);
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


}
