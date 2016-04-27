package com.dao;

import com.commonfunction.Configuration;
import com.model.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Created by wjh on 2016/3/22.
 */
@Repository
public class ToolsDao {

    //    获取安全工具新闻
    public ArrayList<Tools> gettools(int page, Connection conn) throws Exception {
        ArrayList<Tools> toolslist = new ArrayList<Tools>();
        String sql = "SELECT * FROM " +
                "`tools` " +
                "WHERE tools_isdel=0 " +
                "order by tools_id desc " +
                "limit ?,?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, Configuration.pageshow * (page - 1));
        ps.setInt(2, Configuration.pageshow * page);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Tools tools = new Tools();
            tools.setTools_id(rs.getString("tools_id"));
            tools.setTools_name(rs.getString("tools_name"));
            tools.setTools_description(rs.getString("tools_description"));
            tools.setTools_summary(rs.getString("tools_summary"));
            tools.setTools_sendtime(rs.getString("tools_sendtime"));
            tools.setTools_url(rs.getString("tools_url"));
            tools.setTools_img(rs.getString("tools_img"));
            toolslist.add(tools);
        }
        return toolslist;
    }

    //    获取安全工具新闻条数
    public String gettoolscount(Connection conn) throws Exception {
        String sql = "SELECT count(1) as c " +
                "FROM `tools` " +
                "WHERE tools_isdel=0 ";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("c");
    }

    //   查看tools详情
    public Tools gettoolsdetail(Connection conn,
                                String toolsid) throws Exception {
        String sql = "SELECT * FROM " +
                "`tools` WHERE  " +
                "`tools_id`=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, toolsid);
        ResultSet rs = ps.executeQuery();
        Tools tools = new Tools();
        while (rs.next()) {
            tools.setTools_id(rs.getString("tools_id"));
            tools.setTools_name(rs.getString("tools_name"));
            tools.setTools_description(rs.getString("tools_description"));
            tools.setTools_summary(rs.getString("tools_summary"));
            tools.setTools_sendtime(rs.getString("tools_sendtime"));
            tools.setTools_url(rs.getString("tools_url"));
            tools.setTools_img(rs.getString("tools_img"));
        }
        return tools;
    }

    //    搜索安全工具
    public ArrayList<Tools> searchtools(Connection conn,
                                        String description,
                                        int page) throws Exception {
        PreparedStatement ps=null;
        if (description.equals("") || description == null) {
            ps = conn.prepareStatement("SELECT * FROM " +
                    "`tools` WHERE  " +
                    "`tools_isdel`=0 " +
                    "order by tools_id desc " +
                    "limit ?,?");
            ps.setInt(1, Configuration.pageshow * (page - 1));
            ps.setInt(2, Configuration.pageshow * page);
        } else {
            ps = conn.prepareStatement("SELECT * FROM " +
                    "`tools` WHERE  " +
                    "`tools_description` like ? and " +
                    "`tools_isdel`=0 " +
                    "order by tools_id desc " +
                    "limit ?,?");
            ps.setString(1, "%" + description + "%");
            ps.setInt(2, Configuration.pageshow * (page - 1));
            ps.setInt(3, Configuration.pageshow * page);
        }
        ResultSet rs = ps.executeQuery();
        ArrayList<Tools> toolslist = new ArrayList<Tools>();
        while (rs.next()) {
            Tools tools = new Tools();
            tools.setTools_id(rs.getString("tools_id"));
            tools.setTools_name(rs.getString("tools_name"));
            tools.setTools_description(rs.getString("tools_description"));
            tools.setTools_summary(rs.getString("tools_summary"));
            tools.setTools_sendtime(rs.getString("tools_sendtime"));
            tools.setTools_url(rs.getString("tools_url"));
            tools.setTools_img(rs.getString("tools_img"));
            toolslist.add(tools);
        }
        return toolslist;
    }

    //    搜索安全工具--数目
    public String searchtoolscount(Connection conn,
                                   String description) throws Exception {
        PreparedStatement ps=null;
        if (description.equals("") || description == null) {
            ps = conn.prepareStatement("SELECT count(1) as c FROM `tools` WHERE  `tools_isdel`=0 ");
        } else {
            ps = conn.prepareStatement("SELECT count(1) as c FROM " +
                    "`tools` WHERE  " +
                    "`tools_description` like ? and " +
                    "`tools_isdel`=0 ");
            ps.setString(1, "%" + description+ "%");
        }
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("c");
    }
}
