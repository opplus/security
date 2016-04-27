<%--
  Created by IntelliJ IDEA.
  User: wjh
  Date: 2016/3/14
  Time: 9:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<spring:htmlEscape defaultHtmlEscape="true" />--%>
<html>
<head>
    <title>忘记密码</title>
</head>
<body>
<form action="/user/forgetpassmail" method="post">
    <input type="text" name="forgetpassemail"><br>
    <input type="submit" value="发送重置连接">
</form>
</body>
</html>
