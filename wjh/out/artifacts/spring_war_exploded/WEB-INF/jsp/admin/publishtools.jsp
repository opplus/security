<%--
  Created by IntelliJ IDEA.
  User: wjh
  Date: 2016/4/4
  Time: 10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>发布安全工具</title>
    <script src="../JS/jQuery JavaScript Library v2.2.1.js" type="text/javascript"></script>
    <script src="../JS/ajaxfileupload.js"></script>
    <script type="text/javascript" charset="utf-8" src="../JS/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="../JS/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="./JS/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script>
        window.onload = function () {
            sendtools();
        }
        function sendtools() {
            var str11 = '<label>安全工具名称</label>';
            var str12 = '<input type="text" id="name" name="name">';
            var str21 = '<label>安全工具描述</label>';
            var str22 = '<textarea id="description" name="description" style="width:100%;overflow:auto;word-break:break-all;"></textarea>';
            var str31 = '<label>安全工具介绍</label>';
            var str41 = '<label>原文链接</label>';
            var str42 = '<input type="text" id="newsurl" name="url">';
            var str61 = '<input type="button" value="提交" onclick="javascript:sends(\'' + 'tools' + '\')">';
            var str51 = '<label>图片</label>';
            var str52 = '<input type="file" name="uploadFile" id="uploadFile"/>' +
                    '<button class="button" id="buttonUpload" onclick="return ajaxFileUpload();">上传</button>  ' +
                    '<br><hr>';
            var str62 = '<span id="img"></span>';
            $("#label1").empty().append(str11);
            $("#input1").empty().append(str12);
            $("#label2").empty().append(str21);
            $("#input2").empty().append(str22);
            $("#label3").empty().append(str31);
            $("#label4").empty().append(str41);
            $("#input4").empty().append(str42);
            $("#label5").empty().append(str51);
            $("#input5").empty().append(str52);
            $("#label6").empty().append(str61);
            $("#input6").empty().append(str62);
        }
        function ajaxFileUpload() {
            $.ajaxFileUpload
            ({
                        url: "/news/uploadimg",
                        secureuri: false,
                        fileElementId: 'uploadFile',
                        dataType: 'json',
                        success: function (data, status) {
                            $("#img").empty().append('<img src="' + data.image + '" width ="500" height="300">');
                        }
                    }
            );
            return false;
        }
        function sends(newsclass) {
            var describle = $("#description").val();
            var toolsname = $("#name").val();
            var url = $("#newsurl").val();
            var newscontent = UE.getEditor('content').getContent();
            $.ajax({
                url: "/news/sendtools",
                type: "post",
                datatype: "json",
                data: {name: toolsname, description: describle, content: newscontent, url: url},
                success: function (data) {
                    var jsondata = JSON.parse(data);
                    if(jsondata .state==00000){
                        alert("发布成功");
                    }else{
                        alert("发布失败");
                    }
                }
            });
        }
    </script>
    <style type="text/css">
        #Container {
            width: 1000px;
            margin: 0 auto; /*设置整个容器在浏览器中水平居中*/

        }

        #Header {
            height: 80px;
            background: #2B2B2B
        }
        #main{
            height: auto;
        }
        #Footer {
            height: 50px;
            background:#2B2B2B;
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
        table#tab{
            margin:0 auto;
        }
        td.news {
            text-align: left;
        }

        #img, #upload {
            float: left;
        }
    </style>
</head>
<body>
<div id="Container">
    <div id="Header">
        <span id="back"><a href="/admins/adminlogin" class="index">返回管理员后台</a></span>
    </div>
    <div id="main">
        <table id="tab">
            <tr>
                <td><span id="label1"></span></td>
                <td class="news"><span id="input1"></span></td>
            </tr>
            <tr>
                <td><span id="label2"></span></td>
                <td class="news"><span id="input2"></span></td>
            </tr>
            <tr>
                <td><span id="label3"></span></td>
                <td id="newscontent">
                    <script type="text/plain" id="content" name="content" style="width:600px"></script>
                </td>
            </tr>
            <tr>
                <td><span id="label4"></span></td>
                <td class="news"><span id="input4"></span></td>
            </tr>
            <tr>
                <td><span id="label5"></span></td>
                <td class="news"><span id="input5"></span></td>
            </tr>
            <tr>
                <td><span id="label6"></span></td>
                <td class="news"><span id="input6"></span></td>
            </tr>
        </table>
    </div>
    <div class="Clear"><!--如何你上面用到float,下面布局开始前最好清除一下。--></div>
    <div id="Footer"></div>
</div>
<script type="text/javascript">
    var ue=UE.getEditor('content', {
        initialFrameWidth: 600,//设置编辑器宽度
        initialFrameHeight: 500,//设置编辑器高度
        scaleEnabled: true
    });
</script>
</body>
</html>