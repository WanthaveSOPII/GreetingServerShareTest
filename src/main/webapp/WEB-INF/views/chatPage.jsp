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
    <script type="text/javascript" src="js/reconnecting-websocket.js"></script>
</head>
<body>

<div class="wrapper">
    <div class="container" >
        <div class="left" >
            <ul class="people" id="people" > <%--oncontextmenu="openMenu()">--%>
<%--                <c:forEach items="${users}" var="usr" varStatus="st">--%>
<%--                    <li class="person">--%>
<%--                        <span class="name">${usr.username}</span>--%>
<%--                    </li>--%>
<%--                </c:forEach>--%>
                <div class="right-click-menu">
                    <ul class="right-click-menu-list">
                        <li class="right-click-menu-item">
                            <span>点赞</span>
                        </li>
                        <li class="right-click-menu-item">
                            <span>收藏</span>
                        </li>
                        <li class="right-click-menu-item">
                            <span>分享</span>
                        </li>
                        <li class="right-click-menu-item-divider"></li>
                        <li class="right-click-menu-item right-click-menu-item-danger">
                            <span>投币</span>
                        </li>
                    </ul>
                </div>
            </ul>
        </div>
        <div class="right">
            <div class="chat" id="chat">
                <div class="conversation-start">
                    <span>Today, 5:38 PM</span>
                </div>
                <c:forEach items="${messages}" var="msg" varStatus="st">
                    <div STYLE="height: 70px;" >
<%--                        <p class ="leftName" name="${msg.sender}">${msg.sender}</p>--%>
                        <div class="chaticon you" name="${msg.sender}">
                            <img src="img/dog.png" class="chaticon" name="${msg.sender}">
                        </div>
                        <div class="bubble you" name="${msg.sender}">
                            ${msg.info}
                        </div>
                    </div>
                </c:forEach>
<%--                <div id="newdiv"></div>--%>
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
                <input type="text" id="msg"/>
                <button onclick="send()" style="height: 41px" class="write-link send"></button>
<%--                <button onclick="send()" class="write-link send"></button>--%>
            </div>

            <script type="text/javascript">
                var ws;

                function setIconInStorage(){
                    <c:forEach items="${users}" var="usr">
                        sessionStorage.setItem("${usr.username}", "${usr.base64Icon}");
                    </c:forEach>
                }
                function setUserIcon(){
                    var img = document.getElementsByTagName("img");

                    for (var i = 0, len = img.length; i < len; i++) {
                        if(sessionStorage.getItem(img[i].name)!=null){
                            img[i].setAttribute("src", "data:image/jpeg;base64,"+sessionStorage.getItem(img[i].name));
                        }
                    }
                }
                function connect() {
                    console.log("${users}[0].username");
                    setIconInStorage();
                    setUserIcon();
                    var username = "${me}";

                    var host = document.location.host;
                    // var pathname = document.location.pathname;

                    //reconnect可以用了。以前引用javascript 的句法写错了
                    ws = new ReconnectingWebSocket("ws://" +host  + "/wsPage/" + username);
                    //ws = new WebSocket("ws://" +host  + "/wsPage/" + username);
                    var time = nowTime();
                    var json = JSON.stringify({
                        "sender":username,
                        "info":username+"进入了聊天室",
                        "recver":"ALLUSER",
                        "stringTime":time,
                        "zoneID":Intl.DateTimeFormat().resolvedOptions().timeZone,
                        "type":"hello"
                    });
                    setTimeout( function(){
                        ws.send(json);
                    }, 0.5 * 1000 );


                    ws.onmessage = function(event) {
                        console.log("收到 "+event.data);
                        var message = JSON.parse(event.data);
                        var chatdiv = document.getElementById("chat");
                         if(message.type =="chat") {

                            var newMessage = document.createElement('div');
                            newMessage.setAttribute("id", "newMessage");
                            newMessage.setAttribute("style", "height: 70px;");
                            chatdiv.append(newMessage);
                            var chaticon = document.createElement('div');
                            var img = document.createElement('img');
                            img.src = "data:image/jpeg;base64,"+sessionStorage.getItem(message.sender);
                            img.setAttribute("class", "chaticon");
                            chaticon.setAttribute("name", message.sender);
                            if (message.sender == username) {
                                chaticon.setAttribute("class", "chaticon me");
                            } else {
                                chaticon.setAttribute("class", "chaticon you");
                            }
                            chaticon.append(img);
                            newMessage.append(chaticon);
                            var messageInfo = document.createElement('div');
                            messageInfo.setAttribute("name", message.sender);
                            if (message.sender == username) {
                                messageInfo.setAttribute("class", "bubble me");
                            } else {
                                messageInfo.setAttribute("class", "bubble you");
                            }
                            messageInfo.innerHTML = message.info;
                            newMessage.append(messageInfo);

                        }else if(message.type == "hello"){
                             if(hasUserInList(message.sender)==true) {
                                 return;
                             }
                            addUserToList(message.sender);

                            systemNotification(message.sender+"进入了聊天室")
                            // var newSysInfo = document.createElement('div');
                            // newSysInfo.setAttribute("class","conversation-start");
                            // var newPeopleInfo = document.createElement('span');
                            // newPeopleInfo.innerHTML = message.sender+"进入了聊天室";
                            // newSysInfo.append(newPeopleInfo);
                            // chatdiv.append(newSysInfo);

                        }else if(message.type == "listUser"){
                             var userList = message.info.split("/");
                             console.log(userList);
                             for (var i = 0 ;i <userList.length-1;i++){
                                 if(userList[i]==username) {
                                     continue;
                                 }
                                 addUserToList(userList[i]);

                             }
                         }else if(message.type == "bye"){
                             var who = message.info;
                             removeUserFromList(who);
                             systemNotification(message.info+"离开了聊天室")
                         }

                        var scroll = document.getElementById('chat');
                        scroll.scrollTop = scroll.scrollHeight;

                        // log.innerHTML += message.from + " : " + message.content + "\n";
                    }
                }

                function addUserToList(username){
                    if(hasUserInList(username)==true)
                        return;
                    var newPeople = document.createElement('li');
                    newPeople.setAttribute("class", "person");
                    newPeople.setAttribute("id", "userList"+username);
                    var people = document.getElementById("people");
                    people.append(newPeople);
                    var img = document.createElement('img');
                    img.src = "data:image/jpeg;base64,"+sessionStorage.getItem(username);
                    img.setAttribute("class", "chaticon");
                    newPeople.append(img);
                    var newPeopleName = document.createElement('span');
                    newPeopleName.setAttribute("class", "name");
                    newPeopleName.innerHTML = username;
                    newPeople.append(newPeopleName);
                }
                function removeUserFromList(username){
                    var closePeople = document.getElementById("userList"+username);
                    if(closePeople!=null){
                        closePeople.parentNode.removeChild(closePeople);
                    }

                }
                function hasUserInList(username){
                    var closePeople = document.getElementById("userList"+username);
                    if(closePeople!=null){
                        return true;
                    }
                    return false;
                }
                function systemNotification(info){
                    var chatdiv = document.getElementById("chat");
                    var newSysInfo = document.createElement('div');
                    newSysInfo.setAttribute("class","conversation-start");
                    var newPeopleInfo = document.createElement('span');
                    newPeopleInfo.innerHTML = info;
                    newSysInfo.append(newPeopleInfo);
                    chatdiv.append(newSysInfo);
                }
                function nowTime(){
                    var date = new Date(new Date().getTime() + new Date().getTimezoneOffset()*60*1000);
                    //年 getFullYear()：四位数字返回年份
                    var year = date.getFullYear();  //getFullYear()代替getYear()
                    //月 getMonth()：0 ~ 11
                    var month = date.getMonth() + 1;
                    //日 getDate()：(1 ~ 31)
                    var day = date.getDate();
                    //时 getHours()：(0 ~ 23)
                    var hour = date.getHours();
                    //分 getMinutes()： (0 ~ 59)
                    var minute = date.getMinutes();
                    //秒 getSeconds()：(0 ~ 59)
                    var second = date.getSeconds();

                    var time =year + '-' + addZero(month) + '-' + addZero(day) + ' ' + addZero(hour) + ':' + addZero(minute) + ':' + addZero(second);

                    return time;

                }
                function send() {

                    var time =nowTime();

                    var content = document.getElementById("msg").value;
                    var json = JSON.stringify({
                        "info":content,
                        "recver":"ALLUSER",
                        "stringTime":time,
                        "zoneID":Intl.DateTimeFormat().resolvedOptions().timeZone,
                        "type":"chat"
                    });
                    console.log("发送信息 "+ content);
                    ws.send(json);
                    document.getElementById('msg').value=''
                }
                function addZero(s) {
                    return s < 10 ? ('0' + s) : s;
                }

                window.onload = () => {
                    connect();
                    const peopleDiv = document.getElementById('people');
                    const menu = document.querySelector('.right-click-menu')
                    const menuHeight = menu.offsetHeight - parseInt(getComputedStyle(menu)['paddingTop']) - parseInt(getComputedStyle(menu)['paddingBottom'])

                    //hide the menu
                    menu.style.height = '0'

                    //右键开菜单加在这里。以前的用法过时了，传不了e
                    peopleDiv.addEventListener("contextmenu", (e) => {
                        console.log('openMenu '+menuHeight);
                        e.preventDefault();

                        //以前的赋值写错了
                        menu.style.left = e.clientX+'px';
                        menu.style.top = (e.clientY + 5)+'px';
                        menu.style.height = menuHeight+'px';
                        //把css class 加给menu
                        menu.classList.add('right-click-is-active');

                        return false;
                    });

                    window.onclick = () => {
                        console.log('closeMenu');

                        menu.style.height = '0';
                        //抄程序都抄错了！！！
                        <%--menu.classList.remove('is-active');--%>
                        //把css class 从menu显示中去掉
                        menu.classList.remove('right-click-is-active');
                    }

                }
            </script>


        </div>
    </div>
</div>

<div style="text-align:center;margin:1px 0; font:normal 14px/24px 'MicroSoft YaHei';">
</div>
</body>
<jsp:include page="tail.jsp"/>