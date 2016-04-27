package com.service.impl;

/**
 * Created by wjh on 2016/3/22.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.*;
import java.util.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;

import com.model.News;
import com.model.Tools;
import com.dao.NewsDao;
import com.dao.ToolsDao;
import com.dao.AdminDao;

@Service
public class NewsService {
    @Autowired
    private NewsDao ndao;
    @Autowired
    private ToolsDao toolsdao;
    @Autowired
    private AdminDao admindao;

    //  查看新闻
    public ArrayList<News> getnewslist(String newstype, int page, Connection conn) throws Exception {
        ArrayList<News> newslist = ndao.getnews(conn, newstype, page);
        return newslist;
    }
    //  查看新闻详情
    public  News getnewsDetail(String newsid,Connection conn) throws Exception {
        News news = ndao.getnewsdetail(conn, newsid);
        return news;
    }
    //  查看安全工具详情
    public Tools gettoolsDetail(String toolsid,Connection conn) throws Exception {
        Tools tools= toolsdao.gettoolsdetail(conn, toolsid);
        return tools;
    }
    //查看新闻条数
    public String getnewsCount(String newstype, Connection conn) throws Exception {
        String newscount = ndao.getnewscount(conn, newstype);
        return newscount;
    }

    //    查看安全工具
    public ArrayList<Tools> gettoolslist(int page, Connection conn) throws Exception {
        ArrayList<Tools> toolslist = toolsdao.gettools(page, conn);
        return toolslist;
    }

    // 查看安全工具新闻条数
    public String gettoolscount(Connection conn) throws Exception {
        String toolsscount = toolsdao.gettoolscount(conn);
        return toolsscount;
    }

    //    发布安全工具
    public void fabuTools(String name,
                          String description,
                          String summary,
                          String url,
                          String img,
                          String adminid,
                          Connection conn) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String sendtime = sdf.format(new Date());//发送时间
        admindao.fabutools(name, description, summary, sendtime, url, img, adminid, conn);
    }

    //    发布新闻
    public void fabuNews(String url,
                         String title,
                         String text,
                         String image,
                         String type,
                         String adminid,
                         Connection conn) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String sendtime = sdf.format(new Date());//发送时间
        admindao.fabunews(url, title, text, image, sendtime, type, adminid, conn);
    }
    //   删除新闻或安全工具
    public void delneworTools(String type, String id, Connection conn) throws Exception{
        admindao.delnewortools(type,id,conn);
    }
}
