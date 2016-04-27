<%--
  Created by IntelliJ IDEA.
  User: wjh
  Date: 2016/4/10
  Time: 13:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>订阅新闻详情</title>
    <style>
        #Container {
            width: 1000px;
            margin: 0 auto; /*设置整个容器在浏览器中水平居中*/

        }
        #Header {
            height: 80px;
            background: #2B2B2B
        }
        #main {
            height:auto;
        }
        #Footer {
            height: 50px;
            background: #2B2B2B;
            margin-top: 20px;
        }
        .Clear {
            clear: both;
        }
        #back {
            float: left;
            margin-top: 40px;
        }
        table#news {
            margin: 0 auto;
            width: 800px;
            table-layout: fixed;
            word-break: break-all;
        }
        td#title {
            width: 800px; /*单元格宽度*/
            /*height: 50px; !*单元格高度*!*/
            text-align: left;
        }

        td#text {
            width: 800px; /*单元格宽度*/
            /*height: 50px; !*单元格高度*!*/
            /*text-align: left;*/
            word-break: break-all;
        }

        a.index:link, a.index:visited {
            display: block;
            font-weight: bold;
            color: #B5B5B5;
            text-align: center;
            text-decoration: none;
            text-transform: uppercase;
        }
        a.index:hover, a.index:active {
            color: #FFFFFF;
        }
        </style>
</head>
<body>
<div id="Container">
    <div id="Header">
        <span id="back"><a href="/index.jsp" class="index">返回首页</a></span>
    </div>
    <div id="main">
<table id="news">
    <tr>
        <td><h2>${news.news_title}</h2></td>
    </tr>
    <tr>
        <td id="title" valign="top" align="left">
        <span itemprop="newssendtime"  style="color:#CDBA96;font-size:15px;font-family:"宋体";">${news.news_sendtime}</span>
        </td>
    </tr>
    <tr><td id="text">${news.news_text}</td></tr>
    <tr><td>转载地址:<a href="${news.news_url}" >${news.news_url}</a></td></tr>
</table>
    </div>
    <div class="Clear"><!--如何你上面用到float,下面布局开始前最好清除一下。--></div>
    <div id="Footer"></div>
</body>
</html>
