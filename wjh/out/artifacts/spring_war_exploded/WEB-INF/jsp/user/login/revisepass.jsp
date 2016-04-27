<%--
  Created by IntelliJ IDEA.
  User: wjh
  Date: 2016/3/15
  Time: 11:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<spring:htmlEscape defaultHtmlEscape="true" />--%>
<html>
<head>
    <title>修改密码</title>
    <style>
        form {
            position: absolute;   /* 表单定位 */
            margin-top: 50px;            /* 表单定位 */
            margin:0 auto;         /* 表单定位 */
            color: rgb(120, 120, 120);
        }
        fieldset {
            border: none;
            display: inline;
            margin-top: 50px;
            margin-left:381px;
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
            border-radius: 8px;                /* 圆角边框 */
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
<form action="/user/revisePass" method="post">
    <fieldset>
        <label>修改密码</label>
        <label>密码</label>
        <input type="password" name="password">
        <label>重复密码</label>
        <input type="password" name="repassword"><input type="hidden" name="passcode" value="${passcode}">
        <label><input type="submit" value="确认修改"></label>
    </fieldset>
</form>
</body>
</html>
