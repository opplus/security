package com.dao;

/**
 * Created by wjh on 2016/3/16.
 */

import com.commonfunction.Configuration;
import com.model.News;
import com.model.IndexImg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class NewsDao {

    //   获取新闻
    public ArrayList<News> getnews(Connection conn,
                                   String newstype,
                                   int page) throws Exception {
        ArrayList<News> newslist = new ArrayList<News>();
        String sql = "SELECT * FROM " +
                "`news` WHERE  " +
                "`news_type`=? and " +
                "`news_isdel`=0 " +
                "order by news_id desc " +
                "limit ?,?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, newstype);
        ps.setInt(2, Configuration.pageshow * (page - 1));
        ps.setInt(3, Configuration.pageshow * page);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            News news = new News();
            news.setNews_id(rs.getString("news_id"));
            news.setNews_url(rs.getString("news_url"));
            news.setNews_title(rs.getString("news_title"));
            news.setNews_text(rs.getString("news_text"));
            news.setNews_img(rs.getString("news_image"));
            news.setNews_sendtime(rs.getString("news_sendtime"));
            newslist.add(news);
        }
        return newslist;
    }

    //    获取新闻条数
    public String getnewscount(Connection conn,
                               String newstype) throws Exception {
        String sql = "SELECT count(1) as c " +
                "FROM `news` " +
                "WHERE  `news_type`=? " +
                "and news_isdel=0";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, newstype);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("c");
    }

    //    搜索新闻
    public ArrayList<News> searchnews(Connection conn,
                                   String newstitle,
                                   int page) throws Exception {
        PreparedStatement ps=null;
        if (newstitle.equals("") || newstitle == null) {
            ps = conn.prepareStatement("SELECT * FROM `news` WHERE  `news_isdel`=0 order by news_id desc limit ?,?");
            ps.setInt(1, Configuration.pageshow * (page - 1));
            ps.setInt(2, Configuration.pageshow * page);
        } else {
            ps = conn.prepareStatement("SELECT * FROM " +
                    "`news` WHERE  " +
                    "`news_title` like ? and " +
                    "`news_isdel`=0 " +
                    "order by news_id desc " +
                    "limit ?,?");
            ps.setString(1, "%" + newstitle+ "%");
            ps.setInt(2, Configuration.pageshow * (page - 1));
            ps.setInt(3, Configuration.pageshow * page);
        }
        ArrayList<News> newslist = new ArrayList<News>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            News news = new News();
            news.setNews_id(rs.getString("news_id"));
            news.setNews_url(rs.getString("news_url"));
            news.setNews_title(rs.getString("news_title"));
            news.setNews_text(rs.getString("news_text"));
            news.setNews_img(rs.getString("news_image"));
            news.setNews_sendtime(rs.getString("news_sendtime"));
            newslist.add(news);
        }
        return newslist;
    }

    //    搜索新闻--数目
    public String searchnewscount(Connection conn,
                                      String newstitle) throws Exception {
        PreparedStatement ps=null;
        if (newstitle.equals("") || newstitle == null) {
            ps = conn.prepareStatement("SELECT count(1) as c FROM `news` WHERE  `news_isdel`=0 ");
        } else {
            ps = conn.prepareStatement("SELECT count(1) as c FROM " +
                    "`news` WHERE  " +
                    "`news_title` like ? and " +
                    "`news_isdel`=0 ");
            ps.setString(1, "%" + newstitle+ "%");
        }
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("c");
    }


    //   查看新闻详情
    public News getnewsdetail(Connection conn,
                                   String newsid) throws Exception {
        String sql = "SELECT * FROM " +
                "`news` WHERE  " +
                "`news_id`=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, newsid);
        ResultSet rs = ps.executeQuery();
        News news=new News();
        while (rs.next()) {
            news.setNews_id(rs.getString("news_id"));
            news.setNews_url(rs.getString("news_url"));
            news.setNews_title(rs.getString("news_title"));
            news.setNews_text(rs.getString("news_text"));
            news.setNews_img(rs.getString("news_image"));
            news.setNews_sendtime(rs.getString("news_sendtime"));
        }
        return news;
    }


    public static ArrayList<IndexImg> indeximglist(Connection conn) throws Exception{
        PreparedStatement ps1 = conn.prepareStatement("select news_image,news_id from news where news_isdel=0 order by news_sendtime desc,news_id desc limit 0,4 ");
        ResultSet rs1=ps1.executeQuery();
        ArrayList<IndexImg> indeximglist=new ArrayList<IndexImg>();
        while(rs1.next()){
            IndexImg indeximg=new IndexImg();
            indeximg.setIndex_id(rs1.getString("news_id"));
            indeximg.setIndex_img(rs1.getString("news_image"));
            indeximg.setIndex_type("0");
            indeximglist.add(indeximg);
        }
        PreparedStatement ps2 = conn.prepareStatement("select tools_img,tools_id from tools where tools_isdel=0 order by tools_sendtime desc,tools_id desc limit 0,2 ");
        ResultSet rs2=ps2.executeQuery();
        while(rs2.next()){
            IndexImg indeximg=new IndexImg();
            indeximg.setIndex_id(rs2.getString("tools_id"));
            indeximg.setIndex_img(rs2.getString("tools_img"));
            indeximg.setIndex_type("1");
            indeximglist.add(indeximg);
        }
        return indeximglist;
    }
}
