<%--
  Created by IntelliJ IDEA.
  User: wjh
  Date: 2016/3/11
  Time: 10:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>个人中心</title>
    <script src="../JS/jQuery JavaScript Library v2.2.1.js" type="text/javascript"></script>
    <script>
        window.onload = function () {
            getmytext(1);
            getaudittext(1);
        }
        function gettext() {
            getaudittext(1);
            getmytext(1);
        }
        function getdetail(id) {
            $.ajax({
                url: "/usertext/gettextinfo",
                type: "post",
                datatype: "json",
                data: {textid: id},
                success: function (data) {
                    var jsondata = JSON.parse(data).utext;
                    var str = '<table id="textinfotab">';
                    str += '<tr><td text-align="center"><h2>' + jsondata.user_text_name + '</h2><br><span id="texttime" style="color:#CDBA96;font-size:15px;font-family:"宋体";' +
                            '>发布时间：' + jsondata.user_text_sendtime + '</span></td></tr>' +
                            '<tr><td><h4>' + jsondata.user_text_summary + '</h4></td></tr>' +
                            '<tr><td>' + jsondata.user_text_text + '</td></tr>';
                    str += '</table>';
                    $("#verifytext").empty();
                    $("#mytext").empty().append(str);
                }
            });
        }
        function getmytext(page) {
            $.ajax({
                url: "/user/getusertext",
                type: "post",
                datatype: "json",
                data: {texttype: "0", page: page},
                success: function (data) {
                    var jsondata = JSON.parse(data).textlist;
                    var str = '<table cellspacing=0 class="textlist">';
                    if (jsondata.length == 0) {
                        str += '<tr><td colspan="4" align="left" class="tishi"><span class="abstract">无发布的文章...</span></td></tr>';
                    } else {
                        str += '<tr><td colspan="4" align="left" class="tishi"><span class="abstract">已发布的文章</span></td></tr>';
                        str += '<tr><th class="title">文章标题</th><th class="summary">文章摘要</th><th class="status">文章状态</th><th></th></tr>';
                        for (var i = 0; i < jsondata.length; i++) {
                            str += '<tr onmouseout="this.style.backgroundColor=\'' + '\'" onmouseover="this.style.backgroundColor=\'' + '#FFcccc' + '\'">' +
                                    '<td class="title" align="left"><input type="hidden" name="textid"  value="' + jsondata[i].user_text_id + '">' +
                                    '<span id="texttile">' + jsondata[i].user_text_name + '</span><br>' +
                                    '<span id="texttime" style="color:#CDBA96;font-size:15px;font-family:"宋体";' +
                                    '>发布时间：' + jsondata[i].user_text_sendtime + '</span></td>' +
                                    '<td class="summary" align="left"><a href="javascript:getdetail(' + jsondata[i].user_text_id + ')" class="detail"><span id="textcontent">' + jsondata[i].user_text_summary + '</span></a></td>';
                            if (jsondata[i].user_text_isdel == 0) {
                                str += '<td class="status"><span id="textstatus">发布中</span></td><td class="action"><a href="javascript:changetext(' + jsondata[i].user_text_id + '\,\'' + '1' + '\')">下架</a></td></tr>';
                            } else {
                                str += '<td class="status"><span id="textstatus">已下架</span></td><td class="action"><a href="javascript:changetext(' + jsondata[i].user_text_id + '\,\'' + '0' + '\')">上架</a></td></tr>';
                            }
                        }
                    }
                    str += '</table><br><hr>';
                    $("#mytext").empty().append(str);
                }
            });
        }
        function getaudittext(page) {
            $.ajax({
                url: "/user/getusertext",
                type: "post",
                datatype: "json",
                data: {texttype: "1", page: page},
                success: function (data) {
                    var jsondata = JSON.parse(data).textlist;
                    var str = '<table class="textlist" cellspacing=0 >';
                    if (jsondata.length == 0) {
                        str += '<tr><td colspan="4" align="left" class="tishi"><span class="abstract">无待审核的文章...</span></td></tr>';
                    } else {
                        str += '<tr><td colspan="4" align="left" class="tishi"><span class="abstract">待审核的文章</span></td></tr>';
                        str += '<tr><th class="title">文章标题</th><th class="summary">文章摘要</th><th class="status">文章状态</th></tr>';
                        for (var i = 0; i < jsondata.length; i++) {
                            str += '<tr onmouseout="this.style.backgroundColor=\'' + '\'" onmouseover="this.style.backgroundColor=\'' + '#FFcccc' + '\'">' +
                                    '<td class="title" align="left"><input type="hidden" name="textid"  value="' + jsondata[i].user_text_id + '">' +
                                    '<span id="texttile">' + jsondata[i].user_text_name + '</span><br>' +
                                    '<span id="texttime" style="color:#CDBA96;font-size:15px;font-family:"宋体";' +
                                    '>发布时间：' + jsondata[i].user_text_sendtime + '</span></td>' +
                                    '<td class="summary" align="left"><a href="javascript:getdetail(' + jsondata[i].user_text_id + ')" class="detail"><span id="textcontent">' + jsondata[i].user_text_summary + '</span></a></td>';
                            if (jsondata[i].user_text_verifystate == 0 && jsondata[i].user_text_isdel == 0) {
                                str += '<td class="status"><span id="textstatus">审核中</span></td><td class="action"><a href="javascript:changetext(' + jsondata[i].user_text_id + '\,\'' + '1' + '\')">取消申请</a></td></tr>';
                            } else if (jsondata[i].user_text_verifystate == 0 && jsondata[i].user_text_isdel == 1) {
                                str += '<td class="status"><span id="textstatus">取消申请</span></td><td class="action"><a href="javascript:changetext(' + jsondata[i].user_text_id + '\,\'' + '0' + '\')">重新申请</a></td></tr>';
                            } else if (jsondata[i].user_text_verifystate == 2 && jsondata[i].user_text_isdel == 0) {
                                str += '<td class="status"><span id="textstatus">审核不通过</span></td><td class="action"><a href="javascript:changetext(' + jsondata[i].user_text_id + '\,\'' + '1' + '\')">删除</a></td></tr>';
                            } else if (jsondata[i].user_text_verifystate == 2 && jsondata[i].user_text_isdel == 1) {
                                str += '<td class="status"><span id="textstatus">审核不通过</span></td><td class="action">已删除</td></tr>';
                            } else {
                                str += '</tr>';
                            }
                        }
                    }
                    str += '</table><br><hr>';
                    $("#verifytext").empty().append(str);
                }
            });
        }
        function changetext(id, type) {
            $.ajax({
                url: "/user/text/change",
                type: "post",
                datatype: "json",
                data: {textid: id, actiontype: type},
                success: function (data) {
                    getaudittext(1);
                    getmytext(1);
                }
            });
        }
    </script>
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
            height: auto;
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
            margin-left: 20px;
        }

        #out {
            float: right;
            margin-top: 40px;
            margin-right: 20px;
        }

        table#tab, table#textinfotab {
            margin: 0 auto;
        }

        table.textlist{
            width:600px;
            table-layout: fixed;
            word-break: break-all;
        }

        td#left {
            height: 800px;
            width: 180px;
            /*float: left; !*设置浮动，实现多列效果，div+Css布局中很重要的*!*/
            background: #FFFFFF;
        }

        td#right {
            height: 800px;
            width: 720px;
            /*float: left; !*设置浮动，实现多列效果，div+Css布局中很重要的*!*/
            text-align: center;
            background: #FFFFFF;
        }

        th.title {
            width: 200px;
            text-align: center;
        }

        th.summary {
            width: 320px;
            text-align: center;
        }
        td.title {
            width: 200px;
            text-align: left;
            display: -webkit-box; /* height: 36px; line-height: 18px; */
            -webkit-line-clamp: 1;
            -webkit-box-orient: vertical;
            overflow: hidden;
        }

        td.summary {
            width: 320px;
            text-align: left;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .status {
            width: 100px;
            text-align: center;
        }

        td.action {
            width: 100px;
            text-align: center;
        }

        a:link, a:visited {
            display: block;
            font-weight: bold;
            color: #000000;
            text-align: center;
            text-decoration: none;
            text-transform: uppercase;
        }

        a:hover, a:active {
            color: #CDBA96;
        }
        a.detail:link, a.detail:visited {
            display: block;
            /*font-weight: bold;*/
            color: #000000;
            text-align: center;
            text-decoration: none;
            text-transform: uppercase;
        }

        a.detail:hover, a.detail:active {
            color: #CDBA96;
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

        .abstract {
            color: #CDBA96;
            font-size: 30px;
            font-family: "宋体";
        }
        #level {  }
        #integral {  }
    </style>
</head>
<body>
<div id="Container">
    <div id="Header">
        <span id="back"><a href="/index.jsp" class="index">返回首页</a></span>
        <span id="out"><a href="/user/outlogin" class="index">退出登录</a></span>
    </div>
    <div id="main">
        <table id="tab">
            <tr>
                <td id="left" valign="top">
                    <div id="headerimage">
                        <img src="${user.user_img}" alt="个人头像" width="160px" height="160px"><br>
                        <span id="nick">${user.user_name}</span><br>
                        <span id="level">等级：${user.user_level}</span><br><span
                            id="integral">积分：${user.user_integral}</span>
                    </div>
                    <hr>
                    <div>
                        <span id="look"><a href="/user/getuserinfo" class="editor2">查看个人资料</a></span><br>
                        <span id="editoruser"><a href="" class="editor2">编辑个人资料</a></span><br>
                        <span id="looktext"><a href="javascript:gettext()" class="editor2">查看文章</a></span><br>
                        <span id="editortext"><a href="/user/gosendtext" class="editor2">发布新文章</a></span>
                    </div>
                </td>
                <td id="right" align="left" valign="top">
                    <div id="realmain">
                    <div id="mytext"></div>
                    <div id="verifytext"></div>
                    </div>
                </td>
            </tr>
        </table>
    </div>
    <div class="Clear"><!--如何你上面用到float,下面布局开始前最好清除一下。--></div>
    <div id="Footer"></div>
</div>
</body>
</html>
