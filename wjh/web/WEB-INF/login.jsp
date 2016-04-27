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
    <title>登录页面</title>
</head>
<body>
<form id="login"  method="post" action="/user/login">
    <input type="hidden" path="someFormField" htmlEscape="true">
    <table>
        <tr>
            <td>邮箱地址:</td>
            <td><input type="text" name="email" id="email"/></td>
        </tr>
        <tr>
            <td>密&nbsp;&nbsp;码:</td>
            <td><input  type="password" name="password"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="登录"><a href="/user/toforgetpass">忘记密码</a></td>
        </tr>
        <tr>
            <td><a href="/user/toregister">注册</a></td>
        </tr>
    </table>
</form>
</body>
</html>
