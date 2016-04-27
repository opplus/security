<%--
  Created by IntelliJ IDEA.
  User: wjh
  Date: 2016/3/14
  Time: 12:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<spring:htmlEscape defaultHtmlEscape="true" />--%>
<html>
<head>
    <title>公众号绑定</title>
    <%--<script type="text/javascript">--%>
        <%--function getimage(){--%>
            <%--var val = document.all.uploadFile.value;--%>
            <%--window.location.href ="/user/fileUpload?=uploadFile"+val;--%>
        <%--}--%>
<%--//    </script>--%>
</head>
<body>
<form name="frmUpload" enctype="multipart/form-data" action="/user/fileUpload" method="post">
    <input type="file" name="uploadFile">
    <input type="submit" value="上传">
</form>
</body>
</html>
