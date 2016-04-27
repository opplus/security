<%--
  Created by IntelliJ IDEA.
  User: wjh
  Date: 2016/3/10
  Time: 23:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>确认订阅</title>
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
            height: 1000px;
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

        #tab {
            margin: 0 auto;
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
        form {
            position: absolute; /* 表单定位 */
            margin-top: 50px; /* 表单定位 */
            margin: 0 auto; /* 表单定位 */
            color: rgb(120, 120, 120);
        }

        fieldset {
            border: none;
            display: inline;
            margin-top: 50px;
            margin-left: 381px;
        }

        label {
            display: block;
            margin: 0;
            padding: 10px 1px;
            width: 150px;
            font-size: 16px;
            letter-spacing: 2px;
            text-align: center;
        }

        input {
            margin: 0;
            padding: 10px 0;
            border: 1px solid rgb(180, 180, 180);
            border-radius: 8px; /* 圆角边框 */
            width: 150px;
            font-size: 16px;
            text-align: center;
        }

        input:hover {
            border: 1px solid rgb(255, 0, 0);
        }
    </style>
</head>
<body>
<div id="Container">
    <div id="Header">
        <span id="back"><a href="/index.jsp" class="index">返回首页</a></span>
    </div>
    <div id="main">
        <input type="hidden" path="someFormField" htmlEscape="true">
        <form action="/user/login" method="post">
            <fieldset>
                <label>会员登录</label>
                <label>昵称</label>
                <input type="text" name="email" id="email"></span>
                <label>密码</label>
                <input type="password" name="password"/>
                <label><input type="submit" value="登录"></label>
            </fieldset>
        </form>
    </div>
    <div class="Clear"><!--如何你上面用到float,下面布局开始前最好清除一下。--></div>
    <div id="Footer"></div>
</div>
</body>
</html>