<%--
  Created by IntelliJ IDEA.
  User: wjh
  Date: 2016/3/10
  Time: 23:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<spring:htmlEscape defaultHtmlEscape="true" />--%>
<html>
<head>
    <title>邮箱激活</title>
</head>
<body>
感谢注册！确认邮件已发送至您的邮箱${email}，请进入邮箱查看邮件，并激活大V圈平台账号。<br>
<form action="">
    <input type="submit" value="登录邮箱">
</form><hr>
<a href="/register/toregister">重新填写</a><br>
<a href="/register/register/resendMail">重新发送</a>
</body>
</html>
