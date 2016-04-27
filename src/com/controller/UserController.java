package com.controller;


import com.commonfunction.*;
import com.dao.ConnectionManager;
import com.dao.UserDao;
import com.google.gson.Gson;
import com.model.User;
import com.model.UserText;
import com.service.impl.RevisePasswordService;
import com.service.impl.UserTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * Created by wjh on 2016/3/10.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private RevisePasswordService passservice;
    @Autowired
    private UserTextService utextservice;
    @Autowired
    private UserDao udao;
    @Autowired
    private UserText utext;

    //    去登录页
    @RequestMapping(value = "/tologin")
    public String tologin() {
        return "user/login/login";
    }

    //    用户退出登录
    @RequestMapping(value = "/outlogin")
    public String outLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        session.invalidate();
        return "../../index";
    }

    //    用户登录验证
    @RequestMapping(value = "/login")
    public ModelAndView login(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView();
        User user=new User();
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        HttpSession session = request.getSession();
        session.setAttribute("userid", "1");
        String userid = (String) session.getAttribute("userid");
        try {
            if (userid == null) {
                String password = (String) request.getParameter("password");
                String email = (String) request.getParameter("email");
                if (password == null || password.equals("") || email == null || email.equals("")) {
                    mav.setViewName("user/login/loginerror");
                } else {
                    String pwd = MD5.encode2hex(password);
                    user = udao.checkuser(email, pwd, conn);
                    userid = String.valueOf(user.getUser_id());
                    if (userid.equals("null")) {
                        mav.setViewName("user/login/loginerror");
                    } else {
                        session.setAttribute("userid", userid);
                        user = udao.getUserinfo(userid, conn);
                        String level = user.getUser_level();
                        String integral = user.getUser_integral();
                        int inte = Integer.parseInt(integral);
                        if (inte==0||0 < inte && inte < 100) {
                            level = "青铜";
                            integral += "/" + "100";
                        } else if (inte==100||100 < inte && inte < 1000) {
                            level = "白银";
                            integral += "/" + "1000";
                        } else if (inte==1000||1000 < inte && inte < 5000) {
                            level = "黄金";
                            integral += "/" + "5000";
                        } else if (inte==5000||5000 < inte && inte < 10000) {
                            level = "白金";
                            integral += "/" + "10000";
                        } else if (inte==10000||10000 < inte) {
                            level = "钻石";
                            integral += "/" + "10000";
                        } else {
                            throw new Exception();
                        }
                        user.setUser_level(level);
                        user.setUser_integral(integral);
                        mav.addObject("user", user);
                        mav.setViewName("user/login/personal");
                    }
                }
            } else {
                user = udao.getUserinfo(userid, conn);
                String level = user.getUser_level();
                String integral = user.getUser_integral();
                int inte = Integer.parseInt(integral);
                if (inte==0||0 < inte && inte < 100) {
                    level = "青铜";
                    integral += "/" + "100";
                } else if (inte==100||100 < inte && inte < 1000) {
                    level = "白银";
                    integral += "/" + "1000";
                } else if (inte==1000||1000 < inte && inte < 5000) {
                    level = "黄金";
                    integral += "/" + "5000";
                } else if (inte==5000||5000 < inte && inte < 10000) {
                    level = "白金";
                    integral += "/" + "10000";
                } else if (inte==10000||10000 < inte) {
                    level = "钻石";
                    integral += "/" + "10000";
                } else {
                    throw new Exception();
                }
                user.setUser_level(level);
                user.setUser_integral(integral);
                mav.addObject("user", user);
                mav.setViewName("user/login/personal");
            }
        } catch (Exception e) {
            mav.setViewName("user/login/loginerror");
        } finally {
            conn.close();
        }
        return mav;
    }

    //    获取用户个人信息
    @RequestMapping(value = "/getuserinfo")
    public ModelAndView getUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        ModelAndView mav = new ModelAndView();
        User user=new User();
        HttpSession session = request.getSession();
        session.setAttribute("userid", "1");
        String userid = (String) session.getAttribute("userid");
        try {
            if (userid.equals("") || userid == null) {
                mav.setViewName("user/login/loginerror");
            } else {
                user = udao.getUserinfo(userid, conn);
                String level = user.getUser_level();
                String integral = user.getUser_integral();
                String phonenoti = user.getUser_phonenoti();
                String emailnoti = user.getUser_emailnoti();
                if (emailnoti.equals("0")) {
                    emailnoti = "否";
                } else if (emailnoti.equals("1")) {
                    emailnoti = "是";
                } else {
                    throw new Exception();
                }
                if (phonenoti.equals("0")) {
                    phonenoti = "否";
                } else if (phonenoti.equals("1")) {
                    phonenoti = "是";
                } else {
                    throw new Exception();
                }
                int inte = Integer.parseInt(integral);
                if (inte==0||0 < inte && inte < 100) {
                    level = "青铜";
                    integral += "/" + "100";
                } else if (inte==100||100 < inte && inte < 1000) {
                    level = "白银";
                    integral += "/" + "1000";
                } else if (inte==1000||1000 < inte && inte < 5000) {
                    level = "黄金";
                    integral += "/" + "5000";
                } else if (inte==5000||5000 < inte && inte < 10000) {
                    level = "白金";
                    integral += "/" + "10000";
                } else if (inte==10000||10000 < inte) {
                    level = "钻石";
                    integral += "/" + "10000";
                } else {
                    throw new Exception();
                }
                user.setUser_level(level);
                user.setUser_integral(integral);
                user.setUser_phonenoti(phonenoti);
                user.setUser_emailnoti(emailnoti);
                mav.addObject("user", user);
                mav.setViewName("user/userinfo");
            }
        } catch (Exception e) {
            mav.setViewName("user/login/loginerror");
        } finally {
            conn.close();
        }
        return mav;
    }

    //    去重置密码页
    @RequestMapping(value = "/toforgetpass")
    public String toforgetpass() {
        return "user/login/forgetpass";
    }

    //    发送重置密码邮件
    @RequestMapping(value = "/forgetpassmail")
    public ModelAndView forgetpassmail(HttpServletRequest request) throws Exception {
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        ModelAndView mav = new ModelAndView();
        String email = (String) request.getParameter("forgetpassemail");
        try {
            boolean flag = Check.ismail(email);
            if (flag) {
                String passwordcode = RegisterCode.getRegisterCode();//验证码
                String passcode = MD5.encode2hex(passwordcode);
                passservice.processrevisemail(email, passcode, conn);//发邮箱激活
                mav.setViewName("user/login/forgetpass");
            } else {
                mav.setViewName("user/login/revisepassfail");
            }
        } catch (ServiceException e) {
            request.setAttribute("message", e.getMessage());
            mav.setViewName("user/login/revisepassfail");
        } finally {
            conn.close();
        }
        return mav;
    }

    //    去密码修改页
    @RequestMapping(value = "/torevisepass", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView torevisepass(HttpServletRequest request) {
        String passcode = (String) request.getParameter("passcode");
        return new ModelAndView("user/login/revisepass", "passcode", passcode);
    }

    //    处理密码重置业务
    @RequestMapping(value = "/revisePass")
    public ModelAndView revisePass(HttpServletRequest request) throws Exception {
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        ModelAndView mav = new ModelAndView();
        String passcode = (String) request.getParameter("passcode");
        String password = (String) request.getParameter("password");
        String repassword = (String) request.getParameter("repassword");
        try {
            boolean flag = passservice.processrevise(passcode, password, repassword, conn);//是否修改密码
            if (flag) {
                mav.setViewName("user/login/revisepasssuc");
            } else {
                mav.setViewName("user/login/revisepassfail");
            }
        } catch (ServiceException e) {
            request.setAttribute("message", e.getMessage());
            mav.setViewName("user/login/revisepassfail");
        } catch (SQLException e) {
            request.setAttribute("message", Errorcode.SYSTEM_ERROR);
            mav.setViewName("user/login/revisepassfail");
        } finally {
            conn.close();
        }
        return mav;
    }

    //  上传头像
    @RequestMapping(value = "/editor/uploadHead")
    public void uploadHead(HttpServletRequest request, HttpServletResponse response) throws Exception {
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        ModelAndView mav = new ModelAndView();
        HashMap map = new HashMap();
        HttpSession session = request.getSession();
        String userid = (String) session.getAttribute("userid");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH");
        /** 构建文件保存的目录* */
        String logoPathDir = "/user/headimage/"
                + dateformat.format(new Date());
        /** 页面控件的文件流* */
        MultipartFile multipartFile = multipartRequest.getFile("uploadFile");
        try {
            String uploadFileName = UploadFile.uploadfile(logoPathDir, multipartFile, request);
            udao.userhead(userid, uploadFileName, conn);
            map.put("image", uploadFileName);
            map.put("state", Errorcode.SUCCESS);
        } catch (Exception e) {
            map.put("state", Errorcode.SYSTEM_ERROR);
        } finally {
            conn.close();
        }
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        String str = gson.toJson(map);
        out.print(str);
        out.flush();
        out.close();
    }

    //    获取用户发布的文章
    @RequestMapping(value = "/getusertext")
    public void getUserText(HttpServletRequest request, HttpServletResponse response) throws Exception {
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        HashMap hm = new HashMap();
        HttpSession session = request.getSession();
        String userid = (String) session.getAttribute("userid");
        try {
            if (userid.equals("") || userid == null) {
                hm.put("state", Errorcode.SYSTEM_ERROR);
            } else {
                String type = (String) request.getParameter("texttype");
                int page = Integer.parseInt(request.getParameter("page"));
                ArrayList<UserText> textlist = utextservice.getmytextArr(userid, type, page, conn);
                String count = utextservice.getmytextCount(userid, type,conn);
                hm.put("state", Errorcode.SUCCESS);
                hm.put("textlist", textlist);
                hm.put("textcount", count);
            }
        } catch (Exception e) {
            hm.put("state", Errorcode.SYSTEM_ERROR);
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

    //    会员上架或下架文章or取消申请（下架）
    @RequestMapping(value = "/text/change")
    public void changeTextStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        HashMap hm = new HashMap();
        HttpSession session = request.getSession();
        String textid = (String) request.getParameter("textid");
        String type = (String) request.getParameter("actiontype");
        try {
            utextservice.changetextStatus(textid, type, conn);
            hm.put("state", Errorcode.SUCCESS);
        } catch (Exception e) {
            hm.put("state", Errorcode.SYSTEM_ERROR);
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

    //    会员发布文章
    @RequestMapping(value = "/gosendtext")
    public String usergoSendText() {
        return "user/publishtext";
    }

    @RequestMapping(value = "/sendtext")
    public void userSendText(HttpServletRequest request, HttpServletResponse response) throws Exception {
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        HashMap hm = new HashMap();
        HttpSession session = request.getSession();
        String userid = (String) session.getAttribute("userid");
        String name = (String) request.getParameter("name");
        String summary = (String) request.getParameter("summary");
        String text = (String) request.getParameter("text");
        try {
            String image = utext.getUser_text_image();
            if (image.equals("") || image == null) {
                image = "N/A";
            }
            utextservice.usersendText(userid, name, summary, text, image, conn);
            hm.put("state", Errorcode.SUCCESS);
        } catch (Exception e) {
            hm.put("state", Errorcode.SYSTEM_ERROR);
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

    //    上传文章图片
    @RequestMapping(value = "/sendtext/uploadimg")
    public void fileUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        response.setContentType("text/html;charset=UTF-8");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd-HH");
        /** 构建文件保存的目录* */
        String logoPathDir = "/user/textimage/"
                + dateformat.format(new Date());
        /** 页面控件的文件流* */
        MultipartFile multipartFile = multipartRequest.getFile("uploadFile");
        try {
            String uploadFileName = UploadFile.uploadfile(logoPathDir, multipartFile, request);
            utext.setUser_text_image(uploadFileName);
            map.put("image", uploadFileName);
            map.put("state", Errorcode.SUCCESS);
        } catch (Exception e) {
            map.put("state", Errorcode.SYSTEM_ERROR);
        }
        PrintWriter out = response.getWriter();
        String str = gson.toJson(map);
        out.print(str);
        out.flush();
        out.close();
    }
}
