<%--
  Created by IntelliJ IDEA.
  User: wjh
  Date: 2016/3/21
  Time: 13:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理员后台</title>
    <script src="../JS/jQuery JavaScript Library v2.2.1.js" type="text/javascript"></script>
    <script src="../JS/ajaxfileupload.js"></script>
    <script type="text/javascript" charset="utf-8" src="../JS/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="../JS/ueditor/ueditor.all.min.js"></script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="./JS/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script>
        function getadmininfo(type) {
            $.ajax({
                url: "/admins/getadminlist",
                type: "post",
                datatype: "json",
                data: {isall: type},
                success: function (data) {
                    var jsondata = JSON.parse(data).adminlist;
                    var str = '<table id="admintab">';
                    for (var i = 0; i < jsondata.length; i++) {
                        str += '<tr><td>昵称</td><td>' + jsondata[i].admin_name + '</td></tr><tr><td>发布新闻数</td><td>' + jsondata[i].admin_newsnum + '</td></tr><tr><td>权限</td><td>';
                        for (var j = 0; j < jsondata[i].adminpermlist.length; j++) {
                            str += jsondata[i].adminpermlist[j].admin_permission_perm + '<br>';
                        }
                        str += '</td></tr>';
                    }
                    if (jsondata[0].admin_state == 0) {
                        str += '<tr><td><a href="javascript:getadminall(\'' + '1' + '\')" class="admin">查看所有管理员信息</a></td>' +
                                '<td><a href="javascript:addadmin()" class="admin">添加管理员</a></td></tr>';
                    }
                    str += '</table>';
                    $("#editorcontent").empty();
                    $("#INFO").empty().append(str);
                }
            });
        }
        function getadminall(type) {
            $.ajax({
                url: "/admins/getadminlist",
                type: "post",
                datatype: "json",
                data: {isall: type},
                success: function (data) {
                    var jsondata = JSON.parse(data).adminlist;
                    var str = '<table id="admintab"><tr><th>管理员昵称</th><th>管理员发布新闻数</th><th>管理员权限</th><th>管理员状态</th></tr>';
                    for (var i = 0; i < jsondata.length; i++) {
                        str += '<tr>' +
                                '<td>' + jsondata[i].admin_name + '</td>' +
                                '<td>' + jsondata[i].admin_newsnum + '</td>' +
                                '<td>';
                        for (var j = 0; j < jsondata[i].adminpermlist.length; j++) {
                            str += jsondata[i].adminpermlist[j].admin_permission_perm + '<br>';

                        }
                        str += '</td>';
                        var state = '';
                        if (jsondata[i].admin_state == 0) {
                            state += '超级管理员';
                        } else if (jsondata[i].admin_state == 1) {
                            state += '正常';
                        } else {
                            state += '停用';
                        }
                        str += '<td>' + state + '</td>';
                        str += '<td><a href="javascript:alteradminperm(' + jsondata[i].admin_id + ')" class="admin">更改管理员权限</a></td>' +
                                '<td><a href="javascript:alteradminstate(' + jsondata[i].admin_id + ')" class="admin">更改管理员状态</a></td>' +
                                '<td><a href="/admins/deladmin?adminid=' + jsondata[i].admin_id + '" class="admin">删除管理员</a></td>' +
                                '</tr>';
                    }
                    str += '</table><br><hr>';
                    $("#editorcontent").empty();
                    $("#INFO").empty().append(str);
                }
            });
        }
        function addadmin() {
            str = '<form action="/admins/addadmin"><table id="addadmin">' +
                    '<tr>' +
                    '<th>管理员昵称</th>' +
                    '<th>管理员密码</th>' +
                    '<th>重复密码</th>' +
                    '<th>管理员权限名</th>' +
                    '</tr>' +
                    '<tr>' +
                    '<td><input type="text" name="adminname"></td>' +
                    '<td><input type="password" name="adminpassword"></td>' +
                    '<td><input type="password" name="readminpassword"></td>' +
                    '<td><input type="text" name="adminperm"></td>' +
                    '<td><input type="submit" value="确认"><input type="reset" value="重置"></td>' +
                    '</tr>' +
                    '</table></form>';
            $("#editorcontent").empty();
            $("#INFO").empty().append(str);
        }
        function alteradminperm(adminid) {
            str = '<form action="/admins/alteradminperm">' +
                    '<table id="alter">' +
                    '<tr>' +
                    '<th>权限名</th>' +
                    '<th>操作类型 </th>' +
                    '</tr>' +
                    '<tr>' +
                    '<td><input type="hidden" name="adminid" value="' + adminid + '"><input type="text" name="adminperm"></td>' +
                    '<td><select name="type"><option vlaue="add">add</option><option vlaue="del">del</option></select></td>' +
                    '<td><input type="submit" value="确认"></td>' +
                    '</tr>' +
                    '</table>' +
                    '</form><hr>';
            $("#editorcontent").empty();
            $("#INFO").empty().append(str);
        }
        function alteradminstate(adminid) {
            str = '<form action="/admins/alteradminstate">' +
                    '<table id="alter">' +
                    '<tr>' +
                    '<th>状态</th>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>' +
                    '<input type="hidden" name="adminid" value="' + adminid + '">' +
                    '<select name="adminstate">' +
                    '<option vlaue="2">停用</option>' +
                    '<option vlaue="1">正常</option>' +
                    '<option vlaue="0">超级管理员</option>' +
                    '</select>' +
                    '</td>' +
                    '<td><input type="submit" value="确认"></td>' +
                    '</tr>' +
                    '</table>' +
                    '</form><hr>';
            $("#editorcontent").empty();
            $("#INFO").empty().append(str);
        }
        function delmenu() {
            var str = '<ul>' +
                    '<li class="del"><a href="javascript:delnews(\'' + '0' + '\')" class="delnews">删除国内新闻</a></li>' +
                    '<li class="del"><a href="javascript:delnews(\'' + '1' + '\')" class="delnews">删除国外新闻</a></li>' +
                    '<li class="del"><a href="javascript:deltools()" class="delnews">删除安全工具</a></li>' +
                    '</ul>';
            $("#editorcontent").empty();
            $("#INFO").empty().append(str);
        }
        function del(id, type) {
            $.ajax({
                url: "/news/del",
                type: "post",
                datatype: "json",
                data: {type: type, id: id},
                success: function (data) {
                    var jsondata = JSON.parse(data);
                    if (jsondata.state == 000000) {
                        alert("删除成功");
                    } else {
                        alert("删除失败");
                    }
                }
            });
        }
        function delnews(type) {
            $.ajax({
                url: "/news/catnews",
                type: "post",
                datatype: "json",
                data: {newstype: type},
                success: function (data) {
                    var str = '<table>';
                    var jsondata = JSON.parse(data).news;
                    for (var i = 0; i < jsondata.length; i++) {
                        str += '<tr><td><img src="' + jsondata[i].news_img +
                                '" width="65" height="65"/></td>' +
                                '<td id="title" valign="bottom"align="left">' + jsondata[i].news_title + '' +
                                '</td>' +
                                '<td align="right"><a href="javascript:del(' + jsondata[i].news_id + '\,\'' + 'news' + '\')" class="delnews">删除</a></td></tr>';
                    }
                    str += '</table>';
                    $("#editorcontent").empty();
                    $("#INFO").empty().append(str);
                }
            });
        }
        function deltools() {
            $.ajax({
                url: "/news/cattools",
                type: "post",
                datatype: "json",
                success: function (data) {
                    var jsondata = JSON.parse(data).tools;
                    var str = '<table>';
                    for (var i = 0; i < jsondata.length; i++) {
                        str += '<tr><td><img src="' + jsondata[i].tools_img +
                                '" width="65" height="65"/></td>' +
                                '<td id="text" align="left">' + jsondata[i].tools_description + '' +
                                '</td>' +
                                '<td align="right"><a href="javascript:del(' + jsondata[i].tools_id + '\,\'' + 'tools' + '\')" class="delnews">删除</a></td></tr>';
                    }
                    str += '</table>';
                    $("#INFO").empty().append(str);
                }
            });
        }
        function systemset() {
        }
        function getuserinfo(type, page) {
            $.ajax({
                url: "/admins/userlist",
                type: "post",
                datatype: "json",
                data: {level: type, page: page},
                success: function (data) {
                    var jsondata = JSON.parse(data).userlist;
                    if (jsondata.length == 0) {
                        str = '无相关的会员信息...';
                    } else {
                        var str = '<table id="usertab"><tr><th>会员头像</th><th>会员名</th><th>会员等级</th><th>会员积分</th><th>会员发布的文章数</th></tr>';
                        for (var i = 0; i < jsondata.length; i++) {
                            str += '<tr>' +
                                    '<td><img src="' + jsondata[i].user_img + '" width="65" height="65"/></td>' +
                                    '<td>' + jsondata[i].user_name + '</td>' +
                                    '<td>' + jsondata[i].user_level + '</td>' +
                                    '<td>' + jsondata[i].user_integral + '</td>' +
                                    '<td>' + jsondata[i].user_textnum + '</td>' +
                                    '</tr>';
                        }
                        str += '</table>'
                    }
                    $("#editorcontent").empty();
                    $("#INFO").empty().append(str);
                }
            });
        }

        function gettext(type) {
            $.ajax({
                url: "/admins/usertext/list",
                type: "post",
                datatype: "json",
                data: {state: type, page: "1"},
                success: function (data) {
                    var jsondata = JSON.parse(data).textlist;
                    var str = "";
                    if (jsondata.length == 0) {
                        str += '<span class="abstract">无相关信息...</span>';
                    } else {
                        str += '<table id="texted" ><tr>' +
                                '<th class="sendtime">时间</th>' +
                                '<th class="titlename">标题</th>' +
                                '<th class="summary">摘要</th>' +
                                '<th class="status">状态</th></tr>';
                        for (var i = 0; i < jsondata.length; i++) {
                            str += '<tr><td class="sendtime"><span id="texttime" style="font-family:"宋体";">' + jsondata[i].user_text_sendtime + '</span></td>' +
                                    '<td class="titlename"><a href="javascript:textinfo(' + jsondata[i].user_text_id + ')" class="textinfo">' +
                                    '<span id="texttile">' + jsondata[i].user_text_name + '</span></a></td>' +
                                    '<td class="summary"><span id="textcontent">' + jsondata[i].user_text_summary + '</span></td>';
                            if (jsondata[i].user_text_verifystate == 0) {
                                str += '<td class="status" algin="center"><span id="textstatus">待审核</span></td><td class="action"><input type="hidden" name="textid" id="textid" ' +
                                        'value="' + jsondata[i].user_text_id + '">' +
                                        '<select name="status" id="status">' +
                                        '<option vlaue="2">不通过</option>' +
                                        '<option vlaue="1">通过</option>' +
                                        '<option vlaue="0">待审核</option>' +
                                        '</select></td><td align="right"><a href="javascript:audittext(' + type + ')" class="admin">确认</a></td></tr>';
                            } else if (jsondata[i].user_text_verifystate == 1) {
                                str += '<td class="status" algin="center"><span id="textstatus">已发布</span></td><td class="action"><input type="hidden" name="textid" id="textid" ' +
                                        'value="' + jsondata[i].user_text_id + '">' +
                                        '<select name="status" id="status">' +
                                        '<option vlaue="2">不通过</option>' +
                                        '<option vlaue="1">通过</option>' +
                                        '<option vlaue="0">待审核</option>' +
                                        '</select></td><td align="right"><a href="javascript:audittext(' + type + ')" class="admin">确认</a></td></tr>';
                            } else if (jsondata[i].user_text_verifystate == 2) {
                                str += '<td class="status" algin="center"><span id="textstatus">已拒绝</span></td><td class="action"><input type="hidden" name="textid" id="textid" ' +
                                        'value="' + jsondata[i].user_text_id + '">' +
                                        '<select name="status" id="status">' +
                                        '<option vlaue="2">不通过</option>' +
                                        '<option vlaue="1">通过</option>' +
                                        '<option vlaue="0">待审核</option>' +
                                        '</select></td><td align="right"><a href="javascript:audittext(' + type + ')" class="admin">确认</a></td></tr>';
                            } else {
                                str += '</tr>';
                            }
                        }
                        str += '</table><hr>';
                    }
                    $("#editorcontent").empty();
                    $("#INFO").empty().append(str);
                }
            });
        }
        function audittext(type) {
            var state = $("#status  option:selected").text();
            var textid = $("#textid").val();
            $.ajax({
                url: "/admins/usertext/list/audit",
                type: "post",
                datatype: "json",
                data: {state: state, textid: textid},
                success: function (data) {
                    gettext(type);
                }
            });
        }

        function textinfo(textid) {
            $.ajax({
                url: "/usertext/gettextinfo",
                type: "post",
                datatype: "json",
                data: {textid: textid},
                success: function (data) {
                    var jsondata = JSON.parse(data).utext;
                    var str = '<table id="textinfotab">';
                    str += '<tr><td text-align="center"><h2>' + jsondata.user_text_name + '</h2><br><span id="texttime" style="color:#CDBA96;font-size:15px;font-family:"宋体";' +
                            '>发布时间：' + jsondata.user_text_sendtime + '</span></td></tr>' +
                            '<tr><td>' + jsondata.user_text_summary + '</td></tr>' +
                            '<tr><td>' + jsondata.user_text_text + '</td></tr>';
                    str += '</table>';
                    $("#INFO").empty().append(str);
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
            background: #CDC673;
        }

        #main {
            height: auto;
        }

        #Footer {
            height: 50px;
            background: #CDC673;
            margin-top: 20px;
        }

        .Clear {
            clear: both;
        }

        #back {
            float: left;
            margin-top: 40px;
        }

        td#left {
            height: auto;
            width: 180px;
            /*float: left; !*设置浮动，实现多列效果，div+Css布局中很重要的*!*/
            background: #2B2B2B;
        }

        td#right {
            height: auto;
            width: 820px;
            /*float: left; !*设置浮动，实现多列效果，div+Css布局中很重要的*!*/
            background: #FFFFFF;
        }

        .title {
            color: #F0F8FF;
        }

        #texttime {
            float: left;

        }

        a.delnews, a.admin:link, a.delnews, a.admin:visited {
            display: block;
            font-weight: bold;
            color: #2B2B2B;
            text-align: center;
            text-decoration: none;
            text-transform: uppercase;
        }

        a.textinfo:link, a.textinfo:visited {
            display: block;
            font-weight: bold;
            text-align: center;
            text-decoration: none;
            text-transform: uppercase;
        }

        a.textinfo:hover, a.textinfo:active {
            color: #DDA0DD;
        }

        a.delnews, a.admin:hover, a.delnews, a.admin:active {
            color: #2F4F4F;
        }

        a.index:link, a.index:visited {
            display: block;
            font-weight: bold;
            color: #2B2B2B;
            text-align: center;
            text-decoration: none;
            text-transform: uppercase;
        }

        a.index:hover, a.index:active {
            color: #2F4F4F;
        }

        a:link, a:visited {
            display: block;
            font-weight: bold;
            color: #B5B5B5;
            text-align: center;
            text-decoration: none;
            text-transform: uppercase;
        }

        a:hover, a:active {
            color: #FFFFFF;
        }

        ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
        }

        li {
            margin: 10px;
            float: left;
        }

        ul.del {
            margin: 0;
            padding: 0;
        }

        li.del {
            margin: 10px;
            float: left;
        }
        td.news {
            text-align: left;
        }

        td.sendtime {
            text-align: center;
            width: 80px;
        }

        td.titlename {
            text-align: left;
            width: 200px;
        }

        td.summary {
            text-align: left;
            width: 300px;
        }

        td.status {
            text-align: left;
            width: 100px;
        }

        #img, #upload {
            float: left;
        }

        table#tab {
            margin: 0 auto;
        }
    </style>
</head>
<body>
<div id="Container">
    <div id="Header">
        <span id="back"><a href="/index.jsp" class="index">返回首页</a></span>
    </div>
    <div id="main">
        <table id="tab">
            <td id="left" valign="top">
                <li class="li admin">
                    <span class="title">管理员信息</span></a>
                    <ul>
                        <li class="li"><a href="javascript:getadmininfo('0')" class="option">管理员信息</a></li>
                        <li class="li"><a href="javascript:systemset()" class="option">系统设置</a></li>
                        <li class="li"><a href="/admins/adminout" class="option">退出登录</a></li>
                    </ul>
                </li>
                <li class="li admin">
                    <span class="title">新闻</span>
                    <ul>
                        <li class="li"><a href="/news/tosendnews" class="option">国内新闻发布</a></li>
                        <li class="li"><a href="/news/tosendnews" class="option">国外新闻发布</a></li>
                        <li class="li"><a href="/news/tosendtools" class="option">安全工具发布</a></li>
                        <li class="li"><a href="javascript:delmenu()" class="option">删除新闻</a></li>
                    </ul>
                </li>
                <li class="li admin">
                    <span class="title">会员审核</span>
                    <ul>
                        <li class="li"><a href="javascript:gettext('0')" class="option">待审核文章</a></li>
                        <li class="li"><a href="javascript:gettext('1')" class="option">已审核通过文章</a></li>
                        <li class="li"><a href="javascript:gettext('2')" class="option">审核未过文章</a></li>
                    </ul>
                </li>
                <li class="li admin">
                    <span class="title">会员信息</span>
                    <ul>
                        <li class="li"><a href="javascript:getuserinfo('1','1')" class="option">青铜会员</a></li>
                        <li class="li"><a href="javascript:getuserinfo('2','1')" class="option">白银会员</a></li>
                        <li class="li"><a href="javascript:getuserinfo('3','1')" class="option">黄金会员</a></li>
                        <li class="li"><a href="javascript:getuserinfo('4','1')" class="option">白金会员</a></li>
                        <li class="li"><a href="javascript:getuserinfo('5','1')" class="option">钻石会员</a></li>
                    </ul>
                </li>
            </td>
            <td id="right" align="left" valign="top">
                <div id="INFO"></div>
            </td>
        </table>
    </div>
    <div class="Clear"></div>
    <div id="Footer"></div>
</div>
</body>
</html>
