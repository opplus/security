<%--
  Created by IntelliJ IDEA.
  User: wjh
  Date: 2016/3/11
  Time: 10:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>安全网</title>
    <style>
        #Container {
            width: 1000px;
            margin: 0 auto; /*设置整个容器在浏览器中水平居中*/
        }

        #Header {
            height: 80px;
            background: #2B2B2B
        }

        #top {
            padding-left: 50px;
            padding-top: 20px;
            padding-bottom: 50px;
            float: left; /*设置浮动，实现多列效果，div+Css布局中很重要的*/
        }

        * {
            margin: 0;
            padding: 0;
            list-style-type: none;
        }

        a.searcha {
            color: #2d374b;
            text-decoration: none
        }

        a.searcha:hover {
            color: #cd0200;
            text-decoration: underline
        }

        div#search {
            margin-left: 438px;
            width: 487px;
            height: 34px;
            margin-bottom: 40px;
        }

        /* searchTxt */
        .searchBtn button, .searchTxt .searchMenu .searchSelected {
            background-image: url(images/searchbg.png);
            background-repeat: no-repeat;
        }

        .searchTxt {
            float: left;
            width: 399px;
            height: 30px;
            border: 2px solid #d3d3d3;
            border-right: 0;
            position: relative;
            z-index: 20;
            background: #fff;
        }

        .searchTxt .radius {
            width: 1px;
            height: 1px;
            overflow: hidden;
            background: #f4f4f4;
            position: absolute;
            top: -2px;
            left: -2px;
        }

        .searchTxtHover {
            float: left;
            width: 399px;
            height: 30px;
            border: 2px solid #3297d8;
            border-right: 0;
            position: relative;
            z-index: 20;
            background: #fff;
        }

        .searchTxtHover .radius {
            width: 1px;
            height: 1px;
            overflow: hidden;
            background: #cce5f5;
            position: absolute;
            top: -2px;
            left: -2px;
        }

        .searchTxt .searchMenu {
            float: left;
        }

        .searchTxt .searchMenu .searchSelected {
            color: #a8a8a8;
            cursor: pointer;
            font-size: 14px;
            font-weight: bold;
            height: 30px;
            line-height: 30px;
            padding: 0 10px;
            width: 48px;
            background-position: 0px -54px;
        }

        .searchTxt .searchMenu .searchOpen {
            background-position: 0px -104px;
        }

        .searchTxt .searchMenu .searchTab {
            display: none;
            position: absolute;
            top: 30px;
            left: -2px;
            width: 58px;
            border: 2px solid #3297d8;
            border-top: 0;
            background: #fff;
            height: 95px;
            z-index: 20;
        }

        .searchTxt .searchMenu .searchTab li.searchtype {
            width: 58px;
            height: 28px;
            line-height: 28px;
            color: #a8a8a8;
            font-size: 14px;
            font-weight: bold;
            text-indent: 10px;
            cursor: pointer;
        }

        .searchTxt .searchMenu .searchTab li.selected {
            background: #edf3fc;
            color: #6994c1;
        }

        .searchTxt input {
            float: left;
            border: 0;
            background: #fff;
            color: #333;
            font: 14px/22px '宋体', verdana, tahoma, arial, 'SimSun', sans-serif;
            width: 300px;
            height: 28px;
            margin: 0;
            outline: medium none;
            padding: 4px;
        }

        .searchTxt .sosoLogo {
            float: right;
            margin: 8px 6px 0 0;
            display: inline;
        }

        .searchBtn {
            float: left;
        }

        .searchBtn button {
            background-position: 0px 0px;
            border: 0;
            color: #fff;
            cursor: pointer;
            float: left;
            font-size: 16px;
            height: 34px;
            text-indent: -9999px;
            width: 86px;
        }

        ul.indul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
        }

        li.ind {
            margin: 10px;
            float: left;
        }

        ul.login {
            list-style-type: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
        }

        li.login {
            margin: 5px;
            float: right;
        }

        #lo {
            padding-right: 50px;
            padding-top: 25px;
            padding-bottom: 50px;
            float: right; /*设置浮动，实现多列效果，div+Css布局中很重要的*/
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

        a.login:link, a.login:visited {
            display: block;
            font-weight: bold;
            color: #B5B5B5;
            text-align: center;
            text-decoration: none;
            text-transform: uppercase;
        }

        a.login:hover, a.lohin:active {
            color: #FFFFFF;
        }

        a.subscribe:link, a.subscribe:visited {
            display: block;
            font-weight: bold;
            font-size: 20px;
            color: #303030;
            text-align: center;
            text-decoration: none;
            text-transform: uppercase;
        }

        a.subscribe:hover, a.subscribe:active {
            color: #00FF7F;
        }

        a.detail:link, a.detail:visited {
            display: block;
            width: auto;
            font-weight: bold;
            color: #000000;
            text-decoration: none;
            text-transform: uppercase;
        }

        a.detail:hover, a.detail:active {
            color: #EEC591;
        }

        #Content {
            height: auto;
            margin-top: 20px; /*此处讲解margin的用法，设置content与上面header元素之间的距离*/
        }

        table#tab {
            margin: 0 auto;
            height: 700px;
            table-layout: fixed;
            word-break: break-all;
        }

        table.newsdetail {
            margin: 0 auto;
            width: 700px;
            table-layout: fixed;
            word-break: break-all;
        }

        td#left {
            width: 700px;
            word-wrap: break-word;
        }

        td#right {
            width: 300px;
            height: 800px;
        }

        #Content-main {
            height: auto;
            width: 700px;
            /*margin-top: 10px; !*设置元素跟其他元素的距离为20像素*!*/
            /*margin:0 auto;*/
            /*float: left; !*设置浮动，实现多列效果，div+Css布局中很重要的*!*/
            /*background: #33cc66;*/
        }

        #Content-right {
            height: auto;
            width: 300px;
            margin-top: 10px; /*设置元素跟其他元素的距离为20像素*/
            margin: 0 auto;
            background: #FFFAF0;
        }

        #Footer {
            height: 50px;
            background: #2B2B2B;
            margin-top: 20px;
        }

        .Clear {
            clear: both;
        }

        td#title {
            width: 720px; /*单元格宽度*/
            /*height: 50px; !*单元格高度*!*/
            text-align: left;
        }

        td#text {
            width: 720px; /*单元格宽度*/
            /*height: 50px; !*单元格高度*!*/
            /*text-align: left;*/
            word-break: break-all;
        }

        input.din {
            margin: 0;
            padding: 10px 0;
            border: 1px solid rgb(180, 180, 180);
            border-radius: 8px; /* 圆角边框 */
            width: 150px;
            font-size: 10px;
            text-align: center;
        }

        input.din:hover {
            border: 1px solid rgb(255, 0, 0);
        }

        label.qwe {
            display: block;
            margin: 0;
            width: 60px;
            font-size: 10px;
            letter-spacing: 2px;
            text-align: right;
        }

        label.sure {
            display: block;
            margin: 10px 5px 10px 200px;
            width: 60px;
            font-size: 20px;
            letter-spacing: 2px;
        }

        #div_pager {
            clear: both;
            height: 30px;
            line-height: 30px;
            color: #999999;
        }

        #div_pager a {
            padding: 4px 8px;
            margin: 10px 3px;
            font-size: 12px;
            border: 1px solid #DFDFDF;
            background-color: #FFF;
            color: #9d9d9d;
            text-decoration: none;
        }

        #div_pager span {
            padding: 4px 8px;
            margin: 10px 3px;
            font-size: 14px;
        }

        #div_pager span.disabled {
            padding: 4px 8px;
            margin: 10px 3px;
            font-size: 12px;
            border: 1px solid #DFDFDF;
            background-color: #FFF;
            color: #DFDFDF;
        }

        #div_pager span.curr {
            padding: 4px 8px;
            margin: 10px 3px;
            font-size: 12px;
            border: 1px solid #FF6600;
            background-color: #FF6600;
            color: #FFF;
        }

        #div_pager a:hover {
            background-color: #FFEEE5;
            border: 1px solid #FF6600;
        }

        #div_pager span.normalsize {
            font-size: 12px;
        }
    </style>
    <%--<link rel="stylesheet" href="css/style.css" type="text/css" media="screen" title="no title" charset="utf-8">--%>
    <script type="text/javascript" src="JS/jQuery JavaScript Library v2.2.1.js"></script>
    <script type="text/javascript" src="JS/flux.js"></script>
    <script type="text/javascript" charset="utf-8">
       function indeximg() {
            if (!flux.browser.supportsTransitions)
                alert("Flux Slider requires a browser that supports CSS3 transitions");

            window.f = new flux.slider('#slider', {
                pagination: true,
                controls: true,
                transitions: ["bars", "blinds", "blocks", "blocks2", "concentric", "dissolve", "slide", "warp", "zip"],
                autoplay: true
            });

            $('.transitions').click(function (event) {
                event.preventDefault();
                window.f.next($(event.target).data('transition'), $(event.target).data('params'));
            });
        }
        function fenye(totalpage, totalrecords, page, newstype) {
//生成分页控件 根据分页的形式在这里设置
            kkpager.init({

                pno0: page,
//总页码
                total0: totalpage,
//总数据条数
                totalRecords0: totalrecords,

                newstype123: newstype

            });
            kkpager.generPageHtml();
        }
        function getLink(n, m) {
            if (m == 0) {
                getinnews(n);
            } else if (m == 1) {
                getoutnews(n);
            } else if (m == 2) {
                gettools(n);
            } else if (m == 3) {
                getusertext(n);
            } else {
                toosearch(n);
            }
        }
        var kkpager = {
            //divID
            pagerid: 'div_pager',
            //当前页码
            pno: 1,
            //总页码
            total: 1,
            //总数据条数
            totalRecords: 0,
            //类型
            newstype: 0,
            //是否显示总页数
            isShowTotalPage: true,
            //是否显示总记录数
            isShowTotalRecords: true,
            //是否显示页码跳转输入框
            isGoPage: true,
            /****链接算法****/
            //跳转框得到输入焦点时
            focus_gopage: function () {
                var btnGo = $('#btn_go');
                $('#btn_go_input').attr('hideFocus', true);
                btnGo.show();
                btnGo.css('left', '0px');
                $('#go_page_wrap').css('border-color', '#6694E3');
                btnGo.animate({left: '+=44'}, 50, function () {
                    //$('#go_page_wrap').css('width','88px');
                });
            },
            //跳转框失去输入焦点时
            blur_gopage: function () {
                setTimeout(function () {
                    var btnGo = $('#btn_go');
                    //$('#go_page_wrap').css('width','44px');
                    btnGo.animate({
                        left: '-=44'
                    }, 100, function () {
                        $('#btn_go').css('left', '0px');
                        $('#btn_go').hide();
                        $('#go_page_wrap').css('border-color', '#DFDFDF');
                    });
                }, 400);
            },
            //跳转框页面跳转
            gopage: function () {
                var str_page = $("#btn_go_input").val();
                if (isNaN(str_page)) {
                    $("#btn_go_input").val(this.next);
                    return;
                }
                var n = parseInt(str_page);
                if (n < 1 || n > this.total) {
                    $("#btn_go_input").val(this.next);
                    return;
                }
                //这里可以按需改window.open
                getLink(n, this.newstype);
            },
            //分页按钮控件初始化
            init: function (config) {
                //赋值
                this.pno = isNaN(config.pno0) ? 1 : parseInt(config.pno0);
                this.total = isNaN(config.total0) ? 1 : parseInt(config.total0);
                this.totalRecords = isNaN(config.totalRecords0) ? 0 : parseInt(config.totalRecords0);
                this.newstype = config.newstype123;
                //验证
                if (this.pno < 1) this.pno = 1;
                this.total = (this.total <= 1) ? 1 : this.total;
                if (this.pno > this.total) this.pno = this.total;
                this.prv = (this.pno <= 2) ? 1 : (this.pno - 1);
                this.next = (this.pno >= this.total - 1) ? this.total : (this.pno + 1);
                this.hasPrv = (this.pno > 1);
                this.hasNext = (this.pno < this.total);

                this.inited = true;
            },
            //生成分页控件Html
            generPageHtml: function () {
                if (this.total == 1) {
                    $("#" + this.pagerid).empty();
                } else {
                    if (!this.inited) {
                        return;
                    }

                    var str_prv = '', str_next = '';
                    if (this.hasPrv) {
                        str_prv = '<a href="javascript:getLink(' + this.prv + '\,' + this.newstype + ')" title="上一页">上一页</a>';
                    } else {
                        str_prv = '<span class="disabled">上一页</span>';
                    }

                    if (this.hasNext) {
                        str_next = '<a href="javascript:getLink(' + this.next + '\,' + this.newstype + ')" title="下一页">下一页</a>';
                    } else {
                        str_next = '<span class="disabled">下一页</span>';
                    }


                    var str = '';
                    var dot = '<span>...</span>';
                    var total_info = '';
                    if (this.isShowTotalPage || this.isShowTotalRecords) {
                        total_info = '<span class="normalsize">共';
                        if (this.isShowTotalPage) {
                            total_info += this.total + '页';
                            if (this.isShowTotalRecords) {
                                total_info += '&nbsp;/&nbsp;';
                            }
                        }
                        if (this.isShowTotalRecords) {
                            total_info += this.totalRecords + '条数据';
                        }

                        total_info += '</span>';
                    }

                    var gopage_info = '';
                    if (this.isGoPage) {
                        gopage_info = '&nbsp;转到<span id="go_page_wrap" ' +
                                'style="display:inline-block;width:44px;height:18px;border:1px solid #DFDFDF;margin:0px 1px;padding:0px;position:relative;left:0px;top:5px;">' +
                                '<input type="button" id="btn_go" onclick="kkpager.gopage();" ' +
                                'style="width:44px;height:20px;line-height:20px;padding:0px;font-family:arial,宋体,sans-serif;' +
                                'text-align:center;border:0px;background-color:#0063DC;color:#FFF;position:absolute;left:0px;top:-1px;display:none;" value="确定" />' +
                                '<input type="text" id="btn_go_input" onfocus="kkpager.focus_gopage()" ' +
                                'onkeypress="if(event.keyCode<48 || event.keyCode>57)return false;" onblur="kkpager.blur_gopage()" ' +
                                'style="width:42px;height:16px;text-align:center;border:0px;position:absolute;left:0px;top:0px;outline:none;" value="' + this.next + '" /></span>页';
                    }

                    //分页处理
                    if (this.total <= 3) {
                        for (var i = 1; i <= this.total; i++) {
                            if (this.pno == i) {
                                str += '<span class="curr">' + i + '</span>';
                            } else {
                                str += '<a href="javascript:getLink(' + i + '\,' + this.newstype + ')" title="第' + i + '页">' + i + '</a>';
                            }
                        }
                    } else {
                        if (this.pno <= 5) {
                            for (var i = 1; i <= 3; i++) {
                                if (this.pno == i) {
                                    str += '<span class="curr">' + i + '</span>';
                                } else {
                                    str += '<a href="javascript:getLink(' + i + '\,' + this.newstype + ')" title="第' + i + '页">' + i + '</a>';
                                }
                            }
                            str += dot;
                        } else {
                            str += '<a href="javascript:getLink(1' + '\,' + this.newstype + ')" title="第1页">1</a>';
                            str += '<a href="javascript:getLink(2' + '\,' + this.newstype + ')" title="第2页">2</a>';
                            str += dot;

                            var begin = this.pno - 2;
                            var end = this.pno + 2;
                            if (end > this.total) {
                                end = this.total;
                                begin = end - 4;
                                if (this.pno - begin < 2) {
                                    begin = begin - 1;
                                }
                            } else if (end + 1 == this.total) {
                                end = this.total;
                            }
                            for (var i = begin; i <= end; i++) {
                                if (this.pno == i) {
                                    str += '<span class="curr">' + i + '</span>';
                                } else {
                                    str += '<a href="javascript:getLink(' + i + '\,' + this.newstype + ')" title="第' + i + '页">' + i + '</a>';
                                }
                            }
                            if (end != this.total) {
                                str += dot;
                            }
                        }
                    }
                    str = "&nbsp;" + str_prv + str + str_next + total_info + gopage_info;
                    $("#" + this.pagerid).html(str);
                }
            }
        };

        window.onload = function () {
            $.ajax({
                url: "/news/indeximg",
                method: "get",
                datatype: "json",
                success: function (data) {
                   var jsondata = JSON.parse(data).indeximglist;
                    var str = '<div id="slider" style="width:500px;margin-top:40px">';
                    for (var i = 0; i < jsondata.length; i++) {
                        if (jsondata[i].index_type == 0) {
                            str += ' <a href="javascript:getnewsdetail(' + jsondata[i].index_id + ')"><img src="' + jsondata[i].index_img + '" alt="热点新闻图片" style="width:500px;height: 300px" /><a> ';
                        } else {
                            str += ' <a href="javascript:gettoolsdetail(' + jsondata[i].index_id + ')"><img src="' + jsondata[i].index_img + '" alt="热点新闻图片" style="width:500px;height: 300px" /><a> ';
                        }
                    }
                    str += '</div><br><hr>';
                    $("#lunbo").empty().append(str);
                    indeximg();
                }
            });
        }

        $(function () {
            $("#searchSelected").click(function () {
                $("#searchTab").show();
                $(this).addClass("searchOpen");
            });
            $("#searchTab li").hover(function () {
                $(this).addClass("selected");
            }, function () {
                $(this).removeClass("selected");
            });
            $("#searchTab li").click(function () {
                $("#searchSelected").html($(this).html());
                $("#searchTab").hide();
                $("#searchSelected").removeClass("searchOpen");
            });
        });
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
        function getinnews(page) {
            var xhr = createxhr();
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200) {
                        var data = xhr.responseText;
                        var jsondata = JSON.parse(data).news;
                        var jsondata2 = JSON.parse(data);
                        var totalrecords = jsondata2.newscount;
                        var pageSize = 8; // 每页显示几条记录
                        var totalpage = Math.ceil(totalrecords / pageSize); // 总页数
                        var str = '<table id="news">';
                        for (var i = 0; i < jsondata.length; i++) {
                            str += '<tr><td rowspan="2"><img src="' + jsondata[i].news_img + '' +
                                    '"width="110" height="90"/></td>' +
                                    '<td id="title" valign="bottom" align="left">' +
                                    '<a href="javascript:getnewsdetail(' + jsondata[i].news_id + ')" class="detail">' + jsondata[i].news_title + '' +
                                    '</a></td></tr><tr>' +
                                    '<td valign="top" align="left"><span class="time" style="color:#CDBA96;font-size:15px;font-family:"宋体";"' +
                                    '>发布时间：' + jsondata[i].news_sendtime + '</span></td></tr>';
                        }
                        str += '</table';
                        $("#lunbo").empty();
                        document.getElementById("Content-main").innerHTML = str;
                        var newstype = 0;
                        fenye(totalpage, totalrecords, page, newstype);
                    }
                }
            }
            xhr.open("GET", "/news/catnews?newstype=" + "0" + "&page=" + page, true);
            xhr.setRequestHeader("Content-type", "application/x-www-model-urlencoded");
            xhr.send(null);
        }
        function getoutnews(page) {
            var xhr = createxhr();
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200) {
                        var data = xhr.responseText;
                        var jsondata = JSON.parse(data).news;
                        var jsondata2 = JSON.parse(data);
                        var totalrecords = jsondata2.newscount;
                        var pageSize = 8; // 每页显示几条记录
                        var totalpage = Math.ceil(totalrecords / pageSize); // 总页数
                        var str = '<table id="news">';
                        for (var i = 0; i < jsondata.length; i++) {
                            str += '<tr><td rowspan="2"><img src="' + jsondata[i].news_img + '' +
                                    '"width="110" height="90"/></td>' +
                                    '<td id="title" valign="bottom" align="left">' +
                                    '<a href="javascript:getnewsdetail(' + jsondata[i].news_id + ')" class="detail">' + jsondata[i].news_title + '' +
                                    '</a></td></tr><tr>' +
                                    '<td valign="top" align="left"><span class="time" style="color:#CDBA96;font-size:15px;font-family:"宋体";"' +
                                    '>发布时间：' + jsondata[i].news_sendtime + '</span></td></tr>';
                        }
                        str += '</table';
                        $("#lunbo").empty();
                        document.getElementById("Content-main").innerHTML = str;
                        var newstype = 1;
                        fenye(totalpage, totalrecords, page, newstype);
                    }
                }
            }
            xhr.open("GET", "/news/catnews?newstype=" + "1" + "&page=" + page, true);
            xhr.setRequestHeader("Content-type", "application/x-www-model-urlencoded");
            xhr.send(null);
        }
        function getnewsdetail(id) {
            var xhr = createxhr();
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200) {
                        var data = xhr.responseText;
                        var jsondata = JSON.parse(data).news;
                        var str = '<table class="newsdetail">';
                        str += '<tr><td><h2>' + jsondata.news_title + '</h2></td></tr>' +
                                '<tr><td id="title" valign="top" align="left">' +
                                '<span itemprop="newssendtime"  style="color:#CDBA96;font-size:15px;font-family:"宋体";">发布时间：' + jsondata.news_sendtime + '</span>' +
                                '</td></tr>' +
                                '<tr><td id="text">' + jsondata.news_text + '</td></tr>' +
                                '<tr><td>转载地址:<a href="' + jsondata.news_url + '" >' + jsondata.news_url + '</a></td></tr>';
                        str += '</table>';
                        $("#lunbo").empty();
                        document.getElementById("Content-main").innerHTML = str;
                    }
                }
            }
            xhr.open("GET", "/news/newsdetail?newsid=" + id, true);
            xhr.setRequestHeader("Content-type", "application/x-www-model-urlencoded");
            xhr.send(null);
        }
        function gettools(page) {
            var xhr = createxhr();
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200) {
                        var data = xhr.responseText;
                        var jsondata = JSON.parse(data).tools;
                        var jsondata2 = JSON.parse(data);
                        var totalrecords = jsondata2.toolscount;
                        var pageSize = 8; // 每页显示几条记录
                        var totalpage = Math.ceil(totalrecords / pageSize); // 总页数
                        var str = '<table id="news">';
                        for (var i = 0; i < jsondata.length; i++) {
                            str += '<tr><td rowspan="2"><img src="' + jsondata[i].tools_img +
                                    '" width="110" height="90"/></td>' +
                                    '<td id="title" valign="bottom" align="left"><a href="javascript:gettoolsdetail(' + jsondata[i].tools_id + ')" class="detail">' + jsondata[i].tools_description + '</a></td></tr>' +
                                    '<tr><td id="toolssendtime" valign="top"align="left"><span itemprop="newssendtime"  style="color:#CDBA96;font-size:15px;font-family:"宋体";">发布时间：' + jsondata[i].tools_sendtime + '</span></td></tr>';
                        }
                        str += '</table>';
                        $("#lunbo").empty();
                        document.getElementById("Content-main").innerHTML = str;
                        var newstype = 2;
                        fenye(totalpage, totalrecords, page, newstype);
                    }
                }
            }
            xhr.open("GET", "/news/cattools", true);
            xhr.setRequestHeader("Content-type", "application/x-www-model-urlencoded");
            xhr.send(null);
        }
        function gettoolsdetail(id) {
            var xhr = createxhr();
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200) {
                        var data = xhr.responseText;
                        var jsondata = JSON.parse(data).tools;
                        var str = '<table class="newsdetail">';
                        str += '<tr><td><h2>' + jsondata.tools_description + '</h2></td></tr>' +
                                '<tr><td id="title" valign="top" align="left">' +
                                '<span itemprop="newssendtime"  style="color:#CDBA96;font-size:15px;font-family:"宋体";">发布时间：' + jsondata.tools_sendtime + '</span>' +
                                '</td></tr>' +
                                '<tr><td id="text">' + jsondata.tools_summary + '</td></tr>' +
                                '<tr><td>转载地址:<a href="' + jsondata.tools_url + '" >' + jsondata.tools_url + '</a></td></tr>';
                        str += '</table>';
                        $("#lunbo").empty();
                        document.getElementById("Content-main").innerHTML = str;
                    }
                }
            }
            xhr.open("GET", "/news/toolsdetail?toolsid=" + id, true);
            xhr.setRequestHeader("Content-type", "application/x-www-model-urlencoded");
            xhr.send(null);
        }
        function tosearch() {
            var searchtype = document.getElementById("searchSelected").innerText;
            var searchdata = document.getElementById("searchdata").value;
            var page = 1;
            $.ajax({
                url: "/news/search",
                method: "get",
                datatype: "json",
                data: {type: searchtype, data: searchdata, page: page},
                success: function (data) {
                    if (searchtype == "新闻") {
                        var jsondata = JSON.parse(data).newslist;
                        var jsondata2 = JSON.parse(data);
                        var totalrecords = jsondata2.newscount;
                        var pageSize = 10; // 每页显示几条记录
                        var totalpage = Math.ceil(totalrecords / pageSize); // 总页数
                        if (jsondata.length == 0) {
                            var str = '<span  class="time" style="text-align:center;color:#CDBA96;font-size:28px;font-family:"宋体";">无相关信息</span>';
                            $("#Content-main").empty().append(str);
                        } else {
                            var str = '<table id="news">';
                            for (var i = 0; i < jsondata.length; i++) {
                                str += '<tr><td rowspan="2"><img src="' + jsondata[i].news_img + '' +
                                        '"width="110" height="90"/></td>' +
                                        '<td id="title" valign="bottom" align="left">' +
                                        '<a href="javascript:getnewsdetail(' + jsondata[i].news_id + ')" class="detail">' + jsondata[i].news_title + '' +
                                        '</a></td></tr><tr>' +
                                        '<td valign="top" align="left"><span class="time" style="color:#CDBA96;font-size:15px;font-family:"宋体";"' +
                                        '>发布时间：' + jsondata[i].news_sendtime + '</span></td></tr>';
                            }
                            str += '</table';
                            $("#lunbo").empty();
                            $("#Content-main").empty().append(str);
                            var newstype = 4;
                            fenye(totalpage, totalrecords, page, newstype);
                        }
                    } else if (searchtype == "工具") {
                        var jsondata = JSON.parse(data).toolslist;
                        var jsondata2 = JSON.parse(data);
                        var totalrecords = jsondata2.toolscount;
                        var pageSize = 10; // 每页显示几条记录
                        var totalpage = Math.ceil(totalrecords / pageSize); // 总页数
                        if (jsondata.length == 0) {
                            var str = '<span  class="time" style="text-align:center;color:#CDBA96;font-size:28px;font-family:"宋体";">无相关信息</span>';
                            $("#Content-main").empty().append(str);
                        } else {
                            var str = '<table id="news">';
                            for (var i = 0; i < jsondata.length; i++) {
                                str += '<tr><td rowspan="2"><img src="' + jsondata[i].tools_img +
                                        '" width="110" height="90"/></td>' +
                                        '<td id="title" valign="bottom" align="left"><a href="javascript:gettoolsdetail(' + jsondata[i].tools_id + ')" class="detail">' + jsondata[i].tools_description + '</a></td></tr>' +
                                        '<tr><td id="toolssendtime" valign="top"align="left"><span itemprop="newssendtime"  style="color:#CDBA96;font-size:15px;font-family:"宋体";">发布时间：' + jsondata[i].tools_sendtime + '</span></td></tr>';
                            }
                            str += '</table>';
                            $("#lunbo").empty();
                            $("#Content-main").empty().append(str);
                            var newstype = 4;
                            fenye(totalpage, totalrecords, page, newstype);
                        }
                    } else if (searchtype == "文章") {
                        var jsondata = JSON.parse(data).usertextlist;
                        var jsondata2 = JSON.parse(data);
                        var totalrecords = jsondata2.usertextcount;
                        var pageSize = 10; // 每页显示几条记录
                        var totalpage = Math.ceil(totalrecords / pageSize); // 总页数
                        if (jsondata.length == 0) {
                            var str = '<span  class="time" style="text-align:center;color:#CDBA96;font-size:28px;font-family:"宋体";">无相关信息</span>';
                            $("#Content-main").empty().append(str);
                        } else {
                            var str = '<table id="news">';
                            for (var i = 0; i < jsondata.length; i++) {
                                str += '<tr><td id="title" align="left"><a href="javascript:getdetail(' + jsondata[i].user_text_id + ')" class="detail">' + jsondata[i].user_text_name + '</a><br>' +
                                        '<span itemprop="newssendtime"  style="color:#CDBA96;font-size:15px;font-family:"宋体";">发布时间：' + jsondata[i].user_text_sendtime + '</span>' +
                                        '</td></tr>';
                            }
                            str += '</table>';
                            $("#lunbo").empty();
                            $("#Content-main").empty().append(str);
                            var newstype = 4;
                            fenye(totalpage, totalrecords, page, newstype);
                        }
                    } else {
                        alert("非法输入");
                    }
                }
            });
        }
        function toosearch(page) {
            var searchtype = document.getElementById("searchSelected").innerText;
            var searchdata = document.getElementById("searchdata").value;
            var page = page;
            $.ajax({
                url: "/news/search",
                method: "get",
                datatype: "json",
                data: {type: searchtype, data: searchdata, page: page},
                success: function (data) {
                    if (searchtype == "新闻") {
                        var jsondata = JSON.parse(data).newslist;
                        var jsondata2 = JSON.parse(data);
                        var totalrecords = jsondata2.newscount;
                        var pageSize = 10; // 每页显示几条记录
                        var totalpage = Math.ceil(totalrecords / pageSize); // 总页数
                        if (jsondata.length == 0) {
                            var str = '<span  class="time" style="text-align:center;color:#CDBA96;font-size:28px;font-family:"宋体";">无相关信息</span>';
                            $("#Content-main").empty().append(str);
                        } else {
                            var str = '<table id="news">';
                            for (var i = 0; i < jsondata.length; i++) {
                                str += '<tr><td rowspan="2"><img src="' + jsondata[i].news_img + '' +
                                        '"width="110" height="90"/></td>' +
                                        '<td id="title" valign="bottom" align="left">' +
                                        '<a href="javascript:getnewsdetail(' + jsondata[i].news_id + ')" class="detail">' + jsondata[i].news_title + '' +
                                        '</a></td></tr><tr>' +
                                        '<td valign="top" align="left"><span class="time" style="color:#CDBA96;font-size:15px;font-family:"宋体";"' +
                                        '>发布时间：' + jsondata[i].news_sendtime + '</span></td></tr>';
                            }
                            str += '</table';
                            $("#lunbo").empty();
                            $("#Content-main").empty().append(str);
                            var newstype = 4;
                            fenye(totalpage, totalrecords, page, newstype);
                        }
                    } else if (searchtype == "工具") {
                        var jsondata = JSON.parse(data).toolslist;
                        var jsondata2 = JSON.parse(data);
                        var totalrecords = jsondata2.toolscount;
                        var pageSize = 10; // 每页显示几条记录
                        var totalpage = Math.ceil(totalrecords / pageSize); // 总页数
                        if (jsondata.length == 0) {
                            var str = '<span  class="time" style="text-align:center;color:#CDBA96;font-size:28px;font-family:"宋体";">无相关信息</span>';
                            $("#Content-main").empty().append(str);
                        } else {
                            var str = '<table id="news">';
                            for (var i = 0; i < jsondata.length; i++) {
                                str += '<tr><td rowspan="2"><img src="' + jsondata[i].tools_img +
                                        '" width="110" height="90"/></td>' +
                                        '<td id="title" valign="bottom" align="left"><a href="javascript:gettoolsdetail(' + jsondata[i].tools_id + ')" class="detail">' + jsondata[i].tools_description + '</a></td></tr>' +
                                        '<tr><td id="toolssendtime" valign="top"align="left"><span itemprop="newssendtime"  style="color:#CDBA96;font-size:15px;font-family:"宋体";">发布时间：' + jsondata[i].tools_sendtime + '</span></td></tr>';
                            }
                            str += '</table>';
                            $("#lunbo").empty();
                            $("#Content-main").empty().append(str);
                            var newstype = 4;
                            fenye(totalpage, totalrecords, page, newstype);
                        }
                    } else if (searchtype == "文章") {
                        var jsondata = JSON.parse(data).usertextlist;
                        var jsondata2 = JSON.parse(data);
                        var totalrecords = jsondata2.usertextcount;
                        var pageSize = 10; // 每页显示几条记录
                        var totalpage = Math.ceil(totalrecords / pageSize); // 总页数
                        if (jsondata.length == 0) {
                            var str = '<span  class="time" style="text-align:center;color:#CDBA96;font-size:28px;font-family:"宋体";">无相关信息</span>';
                            $("#Content-main").empty().append(str);
                        } else {
                            var str = '<table id="news">';
                            for (var i = 0; i < jsondata.length; i++) {
                                str += '<tr><td id="title" align="left"><a href="javascript:getdetail(' + jsondata[i].user_text_id + ')" class="detail">' + jsondata[i].user_text_name + '</a><br>' +
                                        '<span itemprop="newssendtime"  style="color:#CDBA96;font-size:15px;font-family:"宋体";">发布时间：' + jsondata[i].user_text_sendtime + '</span>' +
                                        '</td></tr>';
                            }
                            str += '</table>';
                            $("#lunbo").empty();
                            $("#Content-main").empty().append(str);
                            var newstype = 4;
                            fenye(totalpage, totalrecords, page, newstype);
                        }
                    } else {
                        alert("非法输入");
                    }
                }
            });
        }
        function getdetail(id) {
            $.ajax({
                url: "/usertext/gettextinfo",
                type: "post",
                datatype: "json",
                data: {textid: id},
                success: function (data) {
                    var jsondata = JSON.parse(data).utext;
                    var str = '<table id="newsdetail">';
                    str += '<tr><td text-align="center"><h2>' + jsondata.user_text_name + '</h2><br><span id="texttime" style="color:#CDBA96;font-size:15px;font-family:"宋体";' +
                            '>作者：' + jsondata.user_name + '&nbsp&nbsp&nbsp' + '发布时间：' + jsondata.user_text_sendtime + '</span></td></tr>' +
                            '<tr><td><h4>' + jsondata.user_text_summary + '</h4></td></tr>' +
                            '<tr><td>' + jsondata.user_text_text + '</td></tr>';
                    str += '</table>';
                    $("#lunbo").empty();
                    $("#Content-main").empty().append(str);
                }
            });
        }
        function getusertext(page) {
            $.ajax({
                url: "/admins/usertext/list",
                method: "get",
                datatype: "json",
                data: {state: "1", page: "1"},
                success: function (data) {
                    var jsondata = JSON.parse(data).textlist;
                    var jsondata2 = JSON.parse(data);
                    var totalrecords = jsondata2.count;
                    var pageSize = 8; // 每页显示几条记录
                    var totalpage = Math.ceil(totalrecords / pageSize); // 总页数
                    var str = '<table id="news">';
                    for (var i = 0; i < jsondata.length; i++) {
                        str += '<tr><td id="title" align="left"><a href="javascript:getdetail(' + jsondata[i].user_text_id + ')" class="detail">' + jsondata[i].user_text_name + '</a><br>' +
                                '<span itemprop="newssendtime"  style="color:#CDBA96;font-size:15px;font-family:"宋体";">作者：' + jsondata[i].user_name + '&nbsp&nbsp&nbsp' + '发布时间：' + jsondata[i].user_text_sendtime + '</span>' +
                                '</td></tr>';
                    }
                    str += '</table>';
                    $("#lunbo").empty();
                    $("#Content-main").empty().append(str);
                    var newstype = 3;
                    fenye(totalpage, totalrecords, page, newstype);
                }
            });
        }
        function subscribe() {
            var mail = $("#mail").val();
            var remail = $("#remail").val();
            var type = $("#dingyuetype  option:selected").text();
            $.ajax({
                url: "/user/subscribe/subscribenews",
                method: "post",
                datatype: "json",
                data: {mail: mail, remail: remail, type: type},
                success: function (data) {
                    alert(data);
                }
            });
        }
    </script>
</head>
<body>
<div id="Container">
    <div id="Header">
        <div id="top">
            <ul class="indul">
                <li class="ind"><a href="index.jsp" class="index">首页</a></li>
                <li class="ind"><a href="javascript:getinnews(1)" class="index">国内安全新闻</a></li>
                <li class="ind"><a href="javascript:getoutnews(1)" class="index">国外安全新闻</a></li>
                <li class="ind"><a href="javascript:gettools(1)" class="index">最新安全工具</a></li>
                <li class="ind"><a href="javascript:getusertext(1)" class="index">会员文章</a></li>
                <li class="ind"><a href="/user/login" class="index">个人中心</a></li>
            </ul>
        </div>
        <div id="lo">
            <ul class="login">
                <li class="login"><a href="/register/toregister" align="right" class="login">注册</a></li>
                <li class="login"><a href="/user/tologin" align="right" class="login">登录</a></li>
                <li class="login"><a href="/admins/admintologin" align="right" class="login">管理员登录</a></li>
            </ul>
        </div>
        <div id="logo">
        </div>
    </div>
    <div id="Content">
        <div id="search">
            <div id="searchTxt" class="searchTxt" onMouseOver="this.className='searchTxt searchTxtHover';"
                 onMouseOut="this.className='searchTxt';">
                <div class="radius" style="top:-2px;"></div>
                <div class="radius" style="top:31px;"></div>
                <div class="searchMenu">
                    <div class="searchSelected" id="searchSelected">新闻</div>
                    <div style="display:none;" class="searchTab" id="searchTab">
                        <div class="radius" style="top:259px;"></div>
                        <div class="radius" style="top:259px;left:59px;"></div>
                        <ul>
                            <li class="searchtype">新闻</li>
                            <li class="searchtype">工具</li>
                            <li class="searchtype">文章</li>
                        </ul>
                    </div>
                </div>
                <input name="searchdata" id="searchdata" type="text">
            </div>
            <div class="searchBtn">
                <button id="searchBtn" type="button" onclick="javascript:tosearch()">搜索</button>
            </div>
        </div>
        <br>
        <hr style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width=1000px color=#987cb9 SIZE=1>
        <br>
        <table id="tab" cellpadding="0" cellspacing="0" style="word-break: break-all;">
            <tr>
                <td id="left" align=center valign=top>
                    <div id="Content-main">
                        <div id="lunbo"></div>
                    </div>
                    <div id="div_pager"></div>
                </td>
                <td id="right" align=center valign=top style="border-left:1px solid #6CA6CD;">
                    <div id="Content-right">
                        <fieldset style="border-radius:8px; width:252;height:180 " align="center">
                            <legend align="left">订阅</legend>
                            <table id="dingyuetab" style="margin:0 auto;margin-top:10px;">
                                <tr>
                                    <td width="60px" align="right"><label class="qwe">类型：</label></td>
                                    <td>
                                        <select id="dingyuetype" name="dingyuetype">
                                            <option vlaue="0">国内新闻</option>
                                            <option vlaue="1">国外新闻</option>
                                            <option vlaue="2">安全工具</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="60px" align="right"><label class="qwe">订阅邮箱</label></td>
                                    <td><input class="din" type="text" id="mail" class="ding" style="width:150;"></td>
                                </tr>
                                <tr>
                                    <td width="60px" align="right"><label class="qwe">确认邮箱</label></td>
                                    <td><input class="din" type="text" id="remail" class="ding" style="width:150;"></td>
                                </tr>
                                <tr colspan="2" align="center"></tr>
                            </table>
                            <label class="sure"><a href="javascript:subscribe()" class="subscribe">确认</a></label>
                        </fieldset>
                    </div>
                </td>
            </tr>
        </table>
    </div>
    <div class="Clear"></div>
    <div id="Footer"></div>
</div>
</body>
</html>