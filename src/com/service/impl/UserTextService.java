package com.service.impl;

import com.model.UserText;
import com.model.UserTextUser;
import com.dao.UserTextDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * Created by wjh on 2016/3/24.
 */
@Service
public class UserTextService {
    @Autowired
    private UserTextDao utextdao;

    //    搜索会员文章
    public ArrayList<UserTextUser> searchText(String type,
                                              String data,
                                              int page,
                                              Connection conn) throws Exception{
        ArrayList<UserTextUser> textlist=utextdao.searchtext(type,data,page,conn);
        return textlist;
    }
    //    会员发布文章
    public void usersendText(String userid,
                             String name,
                             String summary,
                             String text,
                             String image,
                             Connection conn) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String sendtime = sdf.format(new Date());//发送时间
        utextdao.usersendtext(userid,name,summary,text,image,sendtime,conn);
    }
    //    会员上架或下架文章or取消申请（下架）
    public void changetextStatus(String id,String type,Connection conn) throws Exception{
        if(type.equals("0")||type.equals("1")){
            utextdao.changetextstatus(id,type,conn);
        }else{
           throw new Exception();
        }
    }
    //    查看某会员发布的文章（会员查看自己的文章）
    public ArrayList<UserText> getmytextArr(String userid,String  type, int page, Connection conn) throws Exception{
        ArrayList<UserText> textlist=new ArrayList<UserText>();
        if(userid.equals("")||userid==null){
            throw new Exception();
        }else{
          textlist=utextdao.getmytextarr(userid,type,page,conn);
        }
        return textlist;
    }

    //    查看某会员发布的文章（会员查看自己的文章）--count
    public String getmytextCount(String userid,String  type, Connection conn) throws Exception{
        String count="";
        if(userid.equals("")||userid==null){
            throw new Exception();
        }else{
            count=utextdao.getmytextcount(userid,type,conn);
        }
        return count;
    }



    //    查看文章详情
    public UserTextUser gettextInfo(String id, Connection conn) throws Exception{
        UserTextUser usertext=new UserTextUser();
        if(id.equals("")||id==null){
            throw new Exception();
        }else{
            usertext=utextdao.gettextinfo(id,conn);
        }
        return usertext;
    }
}
