<%--
  Created by IntelliJ IDEA.
  User: wjh
  Date: 2016/3/10
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<spring:htmlEscape defaultHtmlEscape="true" />--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript">
        function changeValidateCode(obj) {
            var timenow = new Date().getTime();
            obj.src = "/register/register/code?d=" + timenow;
        }
        function createxhr() {
            var xmlHttp;
            try {
                xmlHttp = new XMLHttpRequest();
            }
            catch (e) {
                try {
                    xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
                }
                catch (e) {
                    try {
                        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
                    }
                    catch (e) {
                    }
                }
            }
            return xmlHttp;
        }
        function checkEmailcode() {
            var email = document.getElementById("email").value;
            var xhr = createxhr();
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200) {
                        var data = xhr.responseText;
                        if (data == "1") {
                            document.getElementById("error1").innerHTML = "邮箱格式错误";
                        } else {
                            document.getElementById("error1").innerHTML = "邮箱格式正确，可以使用";
                        }
                    }
                }
            }
            xhr.open("GET", "/register/checkMailcode?email=" + email, true);
            xhr.setRequestHeader("Content-type", "application/x-www-model-urlencoded");
            xhr.send(null);
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
            height: 800px;
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
        　　
        <form id="register" method="post" action="/register/checkRegister?action=register">
            <fieldset>
                <label>用户注册</label>
                <label>邮箱地址</label>
                <input type="text" name="email" id="email" onBlur="checkEmailcode()"/><span id="error1"></span>
                <label>密码</label>
                <input type="password" name="password"/>
                <label>重复密码</label>
                <input type="password" name="repassword"/>
                <label>验证码</label>
                <input type="text" name="code"><img id="codeImg" alt="验证码" src="${ctx}/register/register/code"
                                                    onclick="changeValidateCode(this)"
                                                    style="height:30px;margin-bottom:-10px;margin-left:10px;"/>
                <label><input type="submit" value="下一步"></label>
            </fieldset>
    </div>
    <div class="Clear"><!--如何你上面用到float,下面布局开始前最好清除一下。--></div>
    <div id="Footer"></div>
</div>
</form>
</body>
</html>
