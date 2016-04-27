package com.controller;

import com.commonfunction.Errorcode;
import com.commonfunction.UploadFile;
import com.dao.ConnectionManager;
import com.dao.NewsDao;
import com.google.gson.Gson;
import com.model.News;
import com.model.Tools;
import com.model.IndexImg;
import com.model.UserTextUser;
import com.service.impl.NewsService;
import com.service.impl.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.io.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;

/**
 * Created by wjh on 2016/3/16.
 */
@Controller
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsservice;
    @Autowired
    private SearchService searchservice;
    @Autowired
    private News news;
    @Autowired
    private NewsDao newsdao;

    //    获取新闻
    @RequestMapping(value = "/catnews")
    public void getNewsList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        response.setContentType("text/html;charset=UTF-8");
        String newstype = (String) request.getParameter("newstype");
        int page = Integer.parseInt(request.getParameter("page"));
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            ArrayList<News> newslist = newsservice.getnewslist(newstype, page, conn);
            String newscount = newsservice.getnewsCount(newstype, conn);
            map.put("state", Errorcode.SUCCESS);
            map.put("newscount", newscount);
            map.put("news", newslist);
            map.put("page", page);
        } catch (Exception e) {
            map.put("state", Errorcode.SYSTEM_ERROR);
        } finally {
            conn.close();
        }
        PrintWriter out = response.getWriter();
        String str = gson.toJson(map);
        out.print(str);
        out.flush();
        out.close();
    }

    //    获取最新安全工具信息
    @RequestMapping(value = "/cattools")
    public void getToolsList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        response.setContentType("text/html;charset=UTF-8");
//        int page = Integer.parseInt(request.getParameter("page"));
        int page = 1;
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            ArrayList<Tools> toolslist = newsservice.gettoolslist(page, conn);
            String toolscount = newsservice.gettoolscount(conn);
            map.put("state", Errorcode.SUCCESS);
            map.put("toolscount", toolscount);
            map.put("tools", toolslist);
            map.put("page", page);
        } catch (Exception e) {
            map.put("state", Errorcode.SYSTEM_ERROR);
        } finally {
            conn.close();
        }
        PrintWriter out = response.getWriter();
        String str = gson.toJson(map);
        out.print(str);
        out.flush();
        out.close();
    }

    //   去发布新闻
    @RequestMapping(value = "/tosendnews")
    public String tosendnews() {
        return "admin/publishnews";
    }

    //   去发布安全工具
    @RequestMapping(value = "/tosendtools")
    public String tosendtools() {
        return "admin/publishtools";
    }

    //   发布新闻
    @RequestMapping(value = "/sendnews")
    public void sendNews(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Gson gson = new Gson();
        HttpSession session = request.getSession();
        Map<String, Object> map = new HashMap<String, Object>();
        response.setContentType("text/html;charset=UTF-8");
        String url = (String) request.getParameter("newsurl");
        String title = (String) request.getParameter("title");
        String text = (String) request.getParameter("content");
        String type = (String) request.getParameter("newstype");
        String adminid = (String) session.getAttribute("adminid");
        adminid = "1";
//        text.replaceAll("\n","<br>");
        if (type.equals("国内新闻")) {
            type = "0";
        } else if (type.equals("国外新闻")) {
            type = "1";
        } else {
            map.put("state", Errorcode.SYSTEM_ERROR);
        }
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            String img = news.getNews_img();
            newsservice.fabuNews(url, title, text, img, type, adminid, conn);
            map.put("state", Errorcode.SUCCESS);
        } catch (Exception e) {
            map.put("state", Errorcode.SYSTEM_ERROR);
        } finally {
            conn.close();
        }
        PrintWriter out = response.getWriter();
        String str = gson.toJson(map);
        out.print(str);
        out.flush();
        out.close();
    }

    //    发布安全工具
    @RequestMapping(value = "/sendtools")
    public void sendTools(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Gson gson = new Gson();
        HttpSession session = request.getSession();
        Map<String, Object> map = new HashMap<String, Object>();
        response.setContentType("text/html;charset=UTF-8");
        String name = (String) request.getParameter("name");
        String description = (String) request.getParameter("description");
        String summary = (String) request.getParameter("content");
        String url = (String) request.getParameter("url");
        String adminid = (String) session.getAttribute("adminid");
        adminid = "1";
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            String img = news.getNews_img();
            newsservice.fabuTools(name, description, summary, url, img, adminid, conn);
            map.put("state", Errorcode.SUCCESS);
        } catch (Exception e) {
            map.put("state", Errorcode.SYSTEM_ERROR);
        } finally {
            conn.close();
        }
        PrintWriter out = response.getWriter();
        String str = gson.toJson(map);
        out.print(str);
        out.flush();
        out.close();
    }

    //    上传图片
    @RequestMapping(value = "/uploadimg")
    public void fileUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        response.setContentType("text/html;charset=UTF-8");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd-HH");
        /** 构建文件保存的目录* */
        String logoPathDir = "/news/newsimage/"
                + dateformat.format(new Date());
        /** 页面控件的文件流* */
        MultipartFile multipartFile = multipartRequest.getFile("uploadFile");
        try {
            String uploadFileName = UploadFile.uploadfile(logoPathDir, multipartFile, request);
            news.setNews_img(uploadFileName);
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

    //   删除新闻或安全工具
    @RequestMapping(value = "/del")
    public void delNeworTools(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Gson gson = new Gson();
        HttpSession session = request.getSession();
        Map<String, Object> map = new HashMap<String, Object>();
        String type = (String) request.getParameter("type");
        String id = (String) request.getParameter("id");
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            newsservice.delneworTools(type, id, conn);
            map.put("state", Errorcode.SUCCESS);
        } catch (Exception e) {
            map.put("state", Errorcode.SYSTEM_ERROR);
        } finally {
            conn.close();
        }
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String str = gson.toJson(map);
        out.print(str);
        out.flush();
        out.close();
    }

    //   获取新闻详情
    @RequestMapping(value = "/newsdetail")
    public void getNewsDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        response.setContentType("text/html;charset=UTF-8");
        String newsid = (String) request.getParameter("newsid");
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            News news = new News();
            news = newsservice.getnewsDetail(newsid, conn);
            map.put("state", Errorcode.SUCCESS);
            map.put("news", news);
            map.put("newstext", news.getNews_text());
        } catch (Exception e) {
            map.put("state", Errorcode.SYSTEM_ERROR);
        } finally {
            conn.close();
        }
        PrintWriter out = response.getWriter();
        String str = gson.toJson(map);
        out.print(str);
        out.flush();
        out.close();
    }

    //   获取安全工具详情
    @RequestMapping(value = "/toolsdetail")
    public void getToolsDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        response.setContentType("text/html;charset=UTF-8");
        String newsid = (String) request.getParameter("toolsid");
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            Tools tools = new Tools();
            tools = newsservice.gettoolsDetail(newsid, conn);
            map.put("state", Errorcode.SUCCESS);
            map.put("tools", tools);
        } catch (Exception e) {
            map.put("state", Errorcode.SYSTEM_ERROR);
        } finally {
            conn.close();
        }
        PrintWriter out = response.getWriter();
        String str = gson.toJson(map);
        out.print(str);
        out.flush();
        out.close();
    }

    //   搜索
    @RequestMapping(value = "/search")
    public void search(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String type = (String) request.getParameter("type");
        String data = (String) request.getParameter("data");
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        int page = Integer.parseInt(request.getParameter("page"));
        try {
            if (type.equals("新闻")) {
                ArrayList<News> newslist = searchservice.searchNews(conn, data, page);
                String newscount = searchservice.searchNewscount(conn, data);
                map.put("newslist", newslist);
                map.put("newscount", newscount);
                map.put("state", Errorcode.SUCCESS);
            } else if (type.equals("工具")) {
                ArrayList<Tools> toolslist = searchservice.searchTools(conn, data, page);
                String toolscount = searchservice.searchToolscount(conn, data);
                map.put("toolslist", toolslist);
                map.put("toolscount", toolscount);
                map.put("state", Errorcode.SUCCESS);
            } else if (type.equals("文章")) {
                ArrayList<UserTextUser> usertextlist = searchservice.searchUsertext(data, page, conn);
                String textcount = searchservice.searchUsertextcount(data, conn);
                map.put("usertextlist", usertextlist);
                map.put("usertextcount", textcount);
                map.put("state", Errorcode.SUCCESS);
            } else {
                map.put("state", Errorcode.SYSTEM_ERROR);
            }
        } catch (Exception e) {
            map.put("state", Errorcode.SYSTEM_ERROR);
        } finally {
            conn.close();
        }
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String str = gson.toJson(map);
        out.print(str);
        out.flush();
        out.close();
    }


    //   获取订阅新闻详情
    @RequestMapping(value = "/newsdetails")
    public ModelAndView getNewsDetails(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();
        String newsid = (String) request.getParameter("newsid");
        String type = (String) request.getParameter("type");
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            if (type.equals("0")) {
                News news = new News();
                news = newsservice.getnewsDetail(newsid, conn);
                mav.addObject("news", news);
                mav.setViewName("news/newsdetail");
            } else if (type.equals("1")) {
                Tools tools = new Tools();
                tools = newsservice.gettoolsDetail(newsid, conn);
                mav.addObject("tools", tools);
                mav.setViewName("news/toolsdetail");
            } else {
                mav.setViewName("news/fail");
            }
        } catch (Exception e) {
            mav.setViewName("news/fail");
        } finally {
            conn.close();
        }
        return mav;
    }

    //   首页图片
    @RequestMapping(value = "/indeximg")
    public void indexImg(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        java.sql.Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            ArrayList<IndexImg> indeximglist = newsdao.indeximglist(conn);
            map.put("indeximglist", indeximglist);
            map.put("state", Errorcode.SUCCESS);
        } catch (Exception e) {
            map.put("state", Errorcode.SYSTEM_ERROR);
        } finally {
            conn.close();
        }
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String str = gson.toJson(map);
        out.print(str);
        out.flush();
        out.close();
    }
}
