package com.controller;

import com.commonfunction.Errorcode;
import com.dao.ConnectionManager;
import com.google.gson.Gson;
import com.model.UserText;
import com.model.UserTextUser;
import com.service.impl.UserTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.io.PrintWriter ;
/**
 * Created by wjh on 2016/4/4.
 */
@Controller
@RequestMapping("/usertext")
public class UserTextController {
    @Autowired
    private UserTextService utextservice;

    //    查看文章详情
    @RequestMapping(value = "/gettextinfo")
    public void getTextInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        HashMap hm = new HashMap();
        String textid = (String) request.getParameter("textid");
        try {
            UserTextUser utext=new UserTextUser();
            utext = utextservice.gettextInfo(textid, conn);
            hm.put("state", Errorcode.SUCCESS);
            hm.put("utext",utext);
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
}
