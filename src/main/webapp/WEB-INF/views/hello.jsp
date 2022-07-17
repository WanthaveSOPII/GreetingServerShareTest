<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--Hi Jsp,现在时间是   ${now}--%>
<%--<p>你好${NickName}</p>--%>
<%--<p>--%>
<%--    <a href="listMessage">信息列表</a>--%>
<%--</p>--%>
<%--<p>--%>
<%--    <a href="user/list">用户列表</a>--%>
<%--</p>--%>
<%--<p>--%>
<%--    <a href="user/addUser">添加用户</a>--%>
<%--</p>--%>
<%--<p>--%>
<%--    <a href="group/list">群组列表</a>--%>
<%--</p>--%>
<%--<p>--%>
<%--    <a href="/chatPage">聊天窗</a>--%>
<%--</p>--%>
<%--<p>--%>
<%--    <a href="user/login">登录</a>--%>
<%--</p>--%>


<%--<div sec:authorize="hasRole('ROLE_admin')">--%>
<%--    <p class="bg-info">${adminonly}</p>--%>
<%--</div>--%>
<%--<div sec:authorize="hasRole('ROLE_chatuser')">--%>
<%--    <p class="bg-info">无更多显示信息</p>--%>
<%--</div>--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>HTML5仿电脑端微信聊天窗口界面代码</title>

    <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,600" rel="stylesheet">
    <link rel="stylesheet" href="css/reset.min.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/addGroupWindow.css">
    <script type="text/javascript" src="js/reconnecting-websocket.js"></script>
    <script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
    <%--    <script type="text/javascript" src="js/addGroupWindow.js"></script>--%>
</head>
<body>

<div class="wrapper">
    <div class="container" >
        <div class="left" style="height: 100%;width: 100%;clear: both;">

            <ul class="usrgrp" id="people">
                <li class="person" onclick="location.href='/listMessage'">
                    <span class="name">信息列表</span>
                </li>
                <li class="person" onclick="location.href='user/list'">
                    <span class="name">用户列表</span>
                </li>
                <li class="person" onclick="location.href='user/addUser'">
                    <span class="name">添加用户</span>
                </li>
                <li class="person" onclick="location.href='/chatPage'">
                    <span class="name">聊天窗</span>
                </li>
                <li class="person" onclick="location.href='group/list'">
                    <span class="name">群组列表</span>
                </li>
            </ul>
        </div>
    </div>
</div>

<div style="text-align:center;margin:1px 0; font:normal 14px/24px 'MicroSoft YaHei';">
</div>
</body>
<jsp:include page="tail.jsp"/>
