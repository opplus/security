<%--
  Created by IntelliJ IDEA.
  User: wjh
  Date: 2016/4/4
  Time: 15:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人资料</title>
    <style type="text/css">
        #Container {
            width: 1000px;
            margin: 0 auto; /*设置整个容器在浏览器中水平居中*/

        }

        #Header {
            height: 80px;
            background: #2B2B2B
        }

        #main {
            height:800px;
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
        td.tag{
            text-align:right;
            width:150px;
            height:30px;
        }
        td.data{
            text-align: left;
            width:200px;
            height:30px;
        }
        td.noti{
            text-align: left;
            width:40px;
            height:30px;
        }
    </style>
</head>
<body>
<div id="Container">
    <div id="Header">
        <span id="back"><a href="/user/login" class="index">返回个人中心</a></span>
    </div>
    <div id="main">
        <hr>
        <table class="tab">
            <tr>
                <td><img src="${user.user_img}" alt="个人头像" width="160px" height="160px"></td>
                <td valign="bottom" align="left">
                    昵称：${user.user_name}<br>
                    等级：${user.user_level}<br>
                    积分：${user.user_integral}
                </td>
            </tr>
        </table>
        <hr>
        <table class="tab">
            <tr>
                <td class="tag">注册时间：</td>
                <td class="data">${user.user_registertime}</td>
            </tr>
            <tr>
                <td class="tag">注册邮箱：</td>
                <td class="data">${user.user_email}</td>
                <td>是否通知：</td>
                <td class="noti">${user.user_emailnoti}</td>
            </tr>
            <tr>
                <td class="tag">绑定手机号：</td>
                <td class="data">${user.user_phone}</td>
                <td>是否通知：</td>
                <td class="noti">${user.user_phonenoti}</td>
            </tr>
            <tr>
                <td class="tag">发布的文章数：</td>
                <td class="data">${user.user_textnum}篇</td>
            </tr>
        </table>
        <hr>
    </div>
    <div class="Clear"><!--如何你上面用到float,下面布局开始前最好清除一下。--></div>
    <div id="Footer"></div>
</div>
</body>
</html>
