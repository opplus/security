package com.service.impl;

import com.dao.NewsDao;
import com.dao.ToolsDao;
import com.dao.UserDao;
import com.dao.UserTextDao;

import com.model.News;
import com.model.Tools;
import com.model.User;
import com.model.UserTextUser;
import com.model.UserText;

import java.sql.Connection;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wjh on 2016/4/8.
 */
@Service
public class SearchService {
    @Autowired
    private NewsDao ndao;
    @Autowired
    private ToolsDao toolsdao;
    @Autowired
    private UserTextDao utextdao;
    @Autowired
    private UserDao udao;

    //    搜索新闻
    public ArrayList<News> searchNews(Connection conn,
                                      String newstitle,
                                      int page) throws Exception {
        ArrayList<News> newslist = ndao.searchnews(conn,newstitle,page);
        return newslist;
    }
    //    搜索新闻数目
    public String searchNewscount(Connection conn,
                                      String newstitle) throws Exception {
        String newscount = ndao.searchnewscount(conn,newstitle);
        return newscount;
    }

    //    搜索安全工具
    public ArrayList<Tools> searchTools(Connection conn,
                                      String toolstitle,
                                      int page) throws Exception {
        ArrayList<Tools> toolslist = toolsdao.searchtools(conn,toolstitle,page);
        return toolslist;
    }
    //    搜索安全工具--数目
    public String searchToolscount(Connection conn,
                                        String toolstitle) throws Exception {
        String count = toolsdao.searchtoolscount(conn,toolstitle);
        return count;
    }
    //    搜索会员文章
    public ArrayList<UserTextUser> searchUsertext(String data,int page,Connection conn) throws Exception {
        ArrayList<UserTextUser> textlist =utextdao.searchusertext(data,page,conn);
        return textlist;
    }
    //    搜索会员文章--数目
    public String searchUsertextcount(String data,Connection conn) throws Exception {
        String count =utextdao.searchusertextcount(data,conn);
        return count;
    }
}
