<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>HTML5仿电脑端微信聊天窗口界面代码</title>

    <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,600" rel="stylesheet">
    <link rel="stylesheet" href="css/reset.min.css">
    <link rel="stylesheet" href="css/style.css">

</head>
<body>

<div class="wrapper">
    <div class="container">
        <div class="left">
            <ul class="people">
                <c:forEach items="${users}" var="usr" varStatus="st">
                    <li class="person">
                        <span class="name">${usr.username}</span>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="right">
            <div class="chat" >
                <div class="conversation-start">
                    <span>Today, 5:38 PM</span>
                </div>
                <c:forEach items="${messages}" var="msg" varStatus="st">
                    <div STYLE="height: 70px;">
<%--                        <p class ="leftName" name="${msg.sender}">${msg.sender}</p>--%>
                        <div class="chaticon you" name="${msg.sender}">
                            <img src="img/dog.png" class="chaticon">
                        </div>
                        <div class="bubble you" name="${msg.sender}">
                            ${msg.info}
                        </div>
                    </div>
                </c:forEach>
            </div>
            <script type="text/javascript">
                var objs = document.getElementsByName("${me}");
                var arr = [];
                for (var i=0;i<objs.length;i++){
                    if(objs[i].className == "leftName")
                        objs[i].className = "rightName";
                    if (objs[i].className == "bubble you")
                        objs[i].className = "bubble me";
                    if (objs[i].className == "chaticon you")
                        objs[i].className = "chaticon me";
                }
            </script>

            <div class="write">
                <a href="javascript:;" class="write-link attach"></a>
                <input type="text" />
                <a href="javascript:;" class="write-link smiley"></a>
                <a href="javascript:;" class="write-link send"></a>
            </div>
        </div>
    </div>
</div>

<div style="text-align:center;margin:1px 0; font:normal 14px/24px 'MicroSoft YaHei';">
</div>
</body>