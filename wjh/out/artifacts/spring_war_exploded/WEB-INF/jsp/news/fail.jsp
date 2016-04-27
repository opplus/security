<%--
  Created by IntelliJ IDEA.
  User: wjh
  Date: 2016/4/10
  Time: 13:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        .progress-button {
            position: relative;
            display: inline-block;
            text-align: center;
        }
        .progress-button button {
            display: block;
            margin: 0 auto;
            margin-top: 25%;
            padding: 0;
            width: 1000px;
            height: 50%;
            border: 2px solid #1ECD97;
            border-radius: 40px;
            background: transparent;
            color: #000000;
            letter-spacing: 1px;
            font-size: 100px;
            font-family: 'Montserrat', sans-serif;
            -webkit-transition: background-color 0.3s, color 0.3s, width 0.3s, border-width 0.3s, border-color 0.3s;
            transition: background-color 0.3s, color 0.3s, width 0.3s, border-width 0.3s, border-color 0.3s;
        }
        .progress-button button:hover {
            background-color: #1ECD97;
            color: #fff;
        }
        .progress-button button:focus {
            outline: none;
        }
        .progress-button svg {
            position: absolute;
            top: 0;
            left: 50%;
            -webkit-transform: translateX(-50%);
            transform: translateX(-50%);
            pointer-events: none;
        }
        .progress-button svg path {
            opacity: 0;
            fill: none;
        }
        .progress-button svg.progress-circle path {
            stroke: #1ECD97;
            stroke-width: 5;
        }
        .progress-button svg.checkmark path,
        .progress-button svg.cross path {
            stroke: #fff;
            stroke-linecap: round;
            stroke-width: 4;
            -webkit-transition: opacity 0.1s;
            transition: opacity 0.1s;
        }
        .loading.progress-button button {
            width: 70px; /* make a circle */
            border-width: 5px;
            border-color: #ddd;
            background-color: transparent;
            color: #fff;
        }
        .loading.progress-button span {
            -webkit-transition: opacity 0.15s;
            transition: opacity 0.15s;
        }
        .loading.progress-button span,
        .success.progress-button span,
        .error.progress-button span {
            opacity: 0; /* keep it hidden in all states */
        }
        .progress-button button span {
            -webkit-transition: opacity 0.3s 0.1s;
            transition: opacity 0.3s 0.1s;
        }
        .success.progress-button button,
        .error.progress-button button {
            -webkit-transition: background-color 0.3s, width 0.3s, border-width 0.3s;
            transition: background-color 0.3s, width 0.3s, border-width 0.3s;
        }
        .success.progress-button button {
            border-color: #1ECD97;
            background-color: #1ECD97;
        }
        .error.progress-button button {
            border-color: #FB797E;
            background-color: #FB797E;
        }
        .loading.progress-button svg.progress-circle path,
        .success.progress-button svg.checkmark path,
        .error.progress-button svg.cross
    </style>
</head>
<body>
<div id="Container">
    <div id="Header">
        <span id="back"><a href="/index.jsp" class="index">返回首页</a></span>
    </div>
    <div id="main">
        <div id="progress-button" class="progress-button">
                <button onclick=""><span>您访问的页面不存在</span></button>
            <svg class="progress-circle" width="70" height="70">
                <path d="m35,2.5c17.955803,0 32.5,14.544199 32.5,32.5c0,17.955803 -14.544197,32.5 -32.5,32.5c-17.955803,0 -32.5,-14.544197 -32.5,-32.5c0,-17.955801 14.544197,-32.5 32.5,-32.5z"/>
            </svg>


            <svg class="checkmark" width="70" height="70">
                <path d="m31.5,46.5l15.3,-23.2"/>
                <path d="m31.5,46.5l-8.5,-7.1"/>
            </svg>


            <svg class="cross" width="70" height="70">
                <path d="m35,35l-9.3,-9.3"/>
                <path d="m35,35l9.3,9.3"/>
                <path d="m35,35l-9.3,9.3"/>
                <path d="m35,35l9.3,-9.3"/>
            </svg>
        </div>
    </div>
    <div class="Clear"><!--如何你上面用到float,下面布局开始前最好清除一下。--></div>
    <div id="Footer"></div>
</body>
</html>
