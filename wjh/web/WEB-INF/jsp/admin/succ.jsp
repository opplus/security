<%--
  Created by IntelliJ IDEA.
  User: wjh
  Date: 2016/4/4
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>操作成功</title>
    <meta http-equiv='refresh' content="1.2;url=/admins/adminlogin">
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
    </div>
    <div id="main">
    </div>
    <div class="Clear"><!--如何你上面用到float,下面布局开始前最好清除一下。--></div>
    <div id="Footer"></div>
</div>
</body>
</html>
