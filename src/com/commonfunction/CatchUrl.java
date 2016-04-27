package com.commonfunction;

/**
 * Created by wjh on 2016/3/14.
 */
import java.util.Date;
import org.jsoup.*;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;import java.io.IOException;

public class CatchUrl {
    /**
     * 根据jsoup方法获取htmlContent
     * 加入简单的时间记录
     * @throws IOException
     */
    public static String getContentByJsoup(String url){
        String content="";
        try {
            System.out.println("time=====start");
            Date startdate=new Date();
            Document doc=Jsoup.connect(url)
                    .data("jquery", "java")
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(50000)
                    .get();
            Date enddate=new Date();
            Long time=enddate.getTime()-startdate.getTime();
            System.out.println("使用Jsoup耗时=="+time);
            System.out.println("time=====end");
            content=doc.toString();//获取url网站的源码html内容
            System.out.println(doc.title());//获取iteye网站的标题
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 使用jsoup来对文档分析
     * 获取目标内容所在的目标层
     * 这个目标层可以是div，table，tr等等
     */
    public static String getDivContentByJsoup1(String content){
        String divContent="";
        Document doc=Jsoup.parse(content);
        Element divs=doc.getElementById("activity-name");
        divContent=divs.toString();
//        if(divContent==null){
//            Element divs2=doc.getElementById("js_content");
//            divContent=divs2.toString();
//        }
       System.out.println("div==="+divContent);
        return divContent;
    }
    public static String getDivContentByJsoup2(String content){
        String divContent="";
        Document doc=Jsoup.parse(content);
        Element divs=doc.getElementById("js_content");
        divContent=divs.toString();
        System.out.println("div==="+divContent);
        return divContent;
    }
    /**
     *对取得的divcontent进行分析，判断里面是否存在大V码
     */
    public static  boolean getdav(String url,String davcode){
        boolean flag=false;
        String content=getContentByJsoup(url);//获取url网站的源码html内容
        String divContent1=getDivContentByJsoup1(content);
        String divContent2=getDivContentByJsoup2(content);
        String sub=davcode;
        int a1=divContent1.indexOf(sub);
        if(a1>=0){
            System.out.println("morning在字符串中的位置a1:"+a1);
            String ss1=divContent1.substring(a1,a1+sub.length());
            String ss2=divContent1.substring(a1+sub.length(),divContent1.length());
            System.out.println("你需要的结果是:"+ss1);
            System.out.println("删掉的字符是:"+ss2);
            flag=true;
        }else{
            int a2=divContent2.indexOf(sub);
            if(a2>=0){
                System.out.println("morning在字符串中的位置a2:"+a2);
                String ss1=divContent2.substring(a2,a2+sub.length());
                String ss2=divContent2.substring(a2+sub.length(),divContent2.length());
                System.out.println("你需要的结果是:"+ss1);
                System.out.println("删掉的字符是:"+ss2);
                flag=true;
            }else {
                System.out.println("不存在："+sub);
            }
        }
        return flag;
    }
}
