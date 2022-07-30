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
    <link rel="stylesheet" href="css/addGroupWindow.css">
    <script type="text/javascript" src="js/reconnecting-websocket.js"></script>
    <script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
<%--    <script type="text/javascript" src="js/addGroupWindow.js"></script>--%>
</head>
<body>

<div class="wrapper">
    <div class="container" >
        <div class="usermenu"><div style="float:left;font-size: 22px;">用户</div></div>
        <div class="left" style="height: 375px;clear: both;">

            <ul class="usrgrp" id="people">

                <div class="right-click-menu" id="peopleMenu" style="z-index:999">
                    <ul class="right-click-menu-list">
                        <li class="right-click-menu-item">
                            <span>踢出群组</span>
                        </li>
                        <li class="right-click-menu-item">
                            <span>改变昵称</span>
                        </li>
                        <li class="right-click-menu-item-divider"></li>
                        <li class="right-click-menu-item right-click-menu-item-danger">
                            <span>投币</span>
                        </li>
                    </ul>
                </div>
            </ul>
        </div>
        <div class="groupmenu" style="clear: both;"><div style="float:left;font-size: 22px;">群组</div>
            <button style="float:right" id="addGroupBtn">添加群组</button>
            <div class="addGroupform">
<%--                <form action="/group/doAddGroup" method="post"  enctype ="multipart/form-data">--%>
                    <div class="addGroupInput"><div class="login_logo">添加群组</div><div class="close">X</div></div>
                    <hr>
                    <div class="addGroupInput"><input style="width:280px;height:30px;border-radius: 5px;border:1px solid  #e5dfdf;" type="text" name="groupname" placeholder="&nbsp;组名"></div>
                    <div class="addGroupInput"><button class="submit_1" type="submit" onclick="addGroup(document.getElementsByName('groupname')[0].value)">添&nbsp;加</button></div>
<%--                </form>--%>
            </div>
        </div>
        <div class="left" style="height: 375px;clear: both;">
            <ul class="usrgrp" id="group">
                <div class="right-click-menu" id="grpMenu" style="z-index:999">
                    <ul class="right-click-menu-list">
                        <li class="right-click-menu-item">
                            <span>加入</span>
                        </li>
                        <li class="right-click-menu-item" id="inviteli">
                            <span>邀请</span>
                        </li>
                        <li class="right-click-menu-item" onclick="outFromGroup()">
                            <span>退出</span>
                        </li>
                        <li class="right-click-menu-item" onclick="deleteGroup()">
                            <span>删除</span>
                        </li>
                        <li class="right-click-menu-item-divider"></li>
                        <li class="right-click-menu-item right-click-menu-item-danger">
                            <span>投币</span>
                        </li>
                    </ul>
                </div>

                <div class="addGroupform" id="addPeopleToGroupform">
                    <%--                <form action="/group/doAddGroup" method="post"  enctype ="multipart/form-data">--%>
                    <div class="addGroupInput"><div class="login_logo">邀请用户</div><div class="close" id="inviteClose">X</div></div>
                    <hr>
                    <div class="addGroupInput"><input style="width:280px;height:30px;border-radius: 5px;border:1px solid  #e5dfdf;" type="text" name="beInvitedUser" placeholder="&nbsp;用户名"></div>
                    <div class="addGroupInput"><button class="submit_1" type="submit" onclick="inviteToGroup(document.getElementsByName('beInvitedUser')[0].value)">添&nbsp;加</button></div>
                    <%--                </form>--%>
                </div>

                            <c:forEach items="${groups}" var="grp" varStatus="st">
                                <li class="person" id="grouplist${grp.name}" onclick="getGroupMember('${grp.name}')">
                                    <span class="name">${grp.name}</span>
                                </li>
                            </c:forEach>

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
                        <div STYLE="height: 100px;" >
                                <div class="bubble you" name="${msg.sender}">
                                    <img alt="img" src="data:image/jpeg;base64,${msg.base64pic}" style="height: 64px;width:64px;"/>
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
                <form method="post" enctype="multipart/form-data" id="file_upload">
                        <input type="file" id="file" name="upload_image" style="width: 29px" class="write-link attach" accept="image/gif, image/jpeg, image/png, image/jpg">
                </form>
                <input type="text" id="msg"/>
                <button onclick="send()" style="height: 41px" class="write-link send"></button>
<%--                <button onclick="send()" class="write-link send"></button>--%>
            </div>

            <script type="text/javascript">
                var ws;
                var personSelElemId;
                var groupSelElemId;
                var choosedGroup;
                var groupNow;
                var memberListNow;
                var fileInput = document.getElementById('file');

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

                    //ws = new ReconnectingWebSocket("ws://" +host  + "/wsPage/" + username);
                    ws = new WebSocket("ws://" +host  + "/wsPage/" + username);
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
                             messageInfo.setAttribute("style", "bubble you");
                            messageInfo.innerHTML = message.info;
                            newMessage.append(messageInfo);

                        }else if(message.type =="picture") {

                            var newMessage = document.createElement('div');
                            newMessage.setAttribute("id", "newMessage");
                            newMessage.setAttribute("style", "height: 100px;");
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
                             //<img alt="img" src="data:image/jpeg;base64,${msg.base64pic}" style="height: 64px;width:64px;"/>
                             var messagePicture = document.createElement('img');
                             messagePicture.setAttribute("alt","img");
                             messagePicture.setAttribute("src",message.info);
                             messagePicture.setAttribute("style","height: 64px;width:64px;");

                            messageInfo.append(messagePicture);
                            newMessage.append(messageInfo);

                        }else if(message.type == "hello"&&groupNow == null){
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
                             var userList = JSON.parse(message.info);
                             console.log(userList);
                             setTimeout( function(){
                                 for (var i = 0 ;i <userList.length;i++){
                                     if(userList[i].username==username) {
                                         continue;
                                     }
                                     addUserToList(userList[i].username);

                                 }
                             }, 0.6 * 1000 );

                         }else if(message.type == "bye"){
                             var who = message.info;
                             removeUserFromList(who);
                             systemNotification(message.info+"离开了聊天室")
                         } else if(message.type == "leftGroup"){
                             var deleteGroupUI = message.info;
                             removeGroupFromList(deleteGroupUI);
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

                function removeGroupFromList(groupname){
                    var closeGroup = document.getElementById("grouplist"+groupname);
                    if(closeGroup!=null){
                        closeGroup.parentNode.removeChild(closeGroup);
                    }

                }
                function hasUserInList(username){
                    var closePeople = document.getElementById("userList"+username);
                    if(closePeople!=null){
                        return true;
                    }
                    return false;
                }
                // function getGroupMember(groupname){
                //     var time = nowTime();
                //     var json = JSON.stringify({
                //         "sender":username,
                //         "info":groupname,
                //         "recver":"SYSTEM",
                //         "stringTime":time,
                //         "zoneID":Intl.DateTimeFormat().resolvedOptions().timeZone,
                //         "type":"getGroupMember"
                //     });
                //     ws.send(json);
                // }
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
                function sendPicture(data) {

                    var time =nowTime();

                    var json = JSON.stringify({
                        "info":data,
                        "recver":"ALLUSER",
                        "stringTime":time,
                        "zoneID":Intl.DateTimeFormat().resolvedOptions().timeZone,
                        "type":"picture"
                    });
                    ws.send(json);
                }
                function addZero(s) {
                    return s < 10 ? ('0' + s) : s;
                }
                function getGroupMember(groupname){
                    console.log("clicked");
                    var url = "/group/getGroupMember";
                   // var data = '{"name":"'+groupname+'","id":"11"}';
                    var data = '{"name":"'+groupname+'"}';
                    $.ajax({
                        headers:{'Content-Type':'application/json;charset=utf8'},
                        type: "POST",
                        url: url,
                        data: data,
                        //headers:{'Content-Type':'application/json;charset=utf8'},
                        dataType: "json",
                        success: function (jdata, status) {
                            console.log("Status: " + status);
                            console.log(jdata);
                            $("#rtn").html("返回结果：" + JSON.stringify(jdata));
                            var e = document.getElementById("people");
                            var child = e.lastElementChild;
                            while (child) {
                                e.removeChild(child);
                                child = e.lastElementChild;
                            }
                            if (groupNow!=null) {
                                var grpli = document.getElementById("grouplist" + groupNow);
                                grpli.classList.remove('ingroup')
                            }
                            groupNow=groupname;
                            grpli = document.getElementById("grouplist"+groupNow);
                            grpli.classList.add('ingroup');
                            memberListNow = jdata;
                            var userList = jdata;
                            for (var i = 0 ;i <userList.length;i++){
                                addUserToList(userList[i].username);

                            }

                        },
                        error: function (xhr, status) {
                            console.log("Status: " + status);
                            console.log(xhr);
                            window.alert("请求数据失败");
                        }
                    });
                }

                function addGroup(groupname){
                    console.log("addGroup " + groupname);
                    var url = "/group/doAddGroup";
                    var data = '{"name":"'+groupname+'"}';
                    $.ajax({
                        headers:{'Content-Type':'application/json;charset=utf8'},
                        type: "POST",
                        url: url,
                        data: data,
                        dataType: "json",
                        success: function (jdata, status) {
                            console.log("Status: " + status);
                            console.log(jdata);
                            $("#rtn").html("返回结果：" + JSON.stringify(jdata));
                            console.log("add Group action success");
                            var form=document.getElementsByClassName("addGroupform");
                            form[0].className="addGroupform";
                        },
                        error: function (xhr, status) {
                            console.log("Status: " + status);
                            console.log(xhr);
                            window.alert("请求数据失败");
                        }
                    });
                }

                function deleteGroup(){
                    var groupname = groupSelElemId.replace("grouplist","")
                    console.log("deleteGroup " + groupname);
                    var url = "/group/doDeleteGroup";
                    var data = '{"name":"'+groupname+'"}';
                    $.ajax({
                        headers:{'Content-Type':'application/json;charset=utf8'},
                        type: "POST",
                        url: url,
                        data: data,
                        dataType: "json",
                        success: function (jdata, status) {
                            console.log("Status: " + status);
                            console.log(jdata);
                            $("#rtn").html("返回结果：" + JSON.stringify(jdata));
                            console.log("delete Group action success");
                        },
                        error: function (xhr, status) {
                            console.log("Status: " + status);
                            console.log(xhr);
                            window.alert("请求数据失败");
                        }
                    });
                }

                function outFromGroup(){
                    var groupname = groupSelElemId.replace("grouplist","")
                    console.log("outFromGroup " + groupname);
                    var url = "/group/doOutFromGroup";
                    var data = '{"name":"'+groupname+'"}';
                    $.ajax({
                        headers:{'Content-Type':'application/json;charset=utf8'},
                        type: "POST",
                        url: url,
                        data: data,
                        dataType: "json",
                        success: function (jdata, status) {
                            console.log("Status: " + status);
                            console.log(jdata);
                            $("#rtn").html("返回结果：" + JSON.stringify(jdata));
                            console.log("outFromGroup action success");

                            var time = nowTime();
                            var json = JSON.stringify({
                                "sender":"${me}",
                                "info":groupname,
                                "recver":"SYSTEM",
                                "stringTime":time,
                                "zoneID":Intl.DateTimeFormat().resolvedOptions().timeZone,
                                "type":"leftGroup"
                            });
                            ws.send(json);
                        },
                        error: function (xhr, status) {
                            console.log("Status: " + status);
                            console.log(xhr);
                            window.alert("请求数据失败");
                        }
                    });

                }

                function inviteToGroup(username){
                    var url = "/group/doInviteToGroup";
                    var data = '{"groupname":"'+choosedGroup+'","username":"'+username+'"}';


                    $.ajax({
                        headers:{'Content-Type':'application/json;charset=utf8'},
                        type: "POST",
                        url: url,
                        data: data,
                        dataType: "json",
                        success: function (jdata, status) {
                            console.log("Status: " + status);
                            console.log(jdata);
                            $("#rtn").html("返回结果：" + JSON.stringify(jdata));
                            console.log("outFromGroup action success");
                        },
                        error: function (xhr, status) {
                            console.log("Status: " + status);
                            console.log(xhr);
                            window.alert("请求数据失败");
                        }
                    });

                }



                window.onload = () => {

                    const peopleDiv = document.getElementById('people');
                    const groupDiv = document.getElementById('group');
                    const menu = document.querySelector('.right-click-menu')
                    const grpmenu = document.getElementById('grpMenu')
                    const menuHeight = menu.offsetHeight - parseInt(getComputedStyle(menu)['paddingTop']) - parseInt(getComputedStyle(menu)['paddingBottom'])
                    var usrselectedMenu;
                    var grpselectedMenu;

                    menu.style.height = '0'

                    connect();

                    var btn=document.getElementById("addGroupBtn");
                    var close=document.getElementsByClassName("close");
                    var form=document.getElementsByClassName("addGroupform");
                    btn.addEventListener('click',function(){
                        form[0].className="addGroupform open";
                    })
                    close[0].addEventListener('click',function(){
                        form[0].className="addGroupform";
                    })

                    var inviteOpen=document.getElementById("inviteli");
                    var inviteClose=document.getElementById("inviteClose");
                    var inviteForm=document.getElementById("addPeopleToGroupform");
                    inviteOpen.addEventListener('click',function(){
                        inviteForm.className="addGroupform open";
                        choosedGroup = groupSelElemId.replace("grouplist","")
                        console.log(choosedGroup);
                    })
                    inviteClose.addEventListener('click',function(){
                        inviteForm.className="addGroupform";
                    })
                        //右键开菜单加在这里。以前的用法过时了，传不了e
                    peopleDiv.addEventListener("contextmenu",openUserRightClickMenu);

                    peopleDiv.addEventListener("mouseover", function( e ) {
                        // highlight the mouseenter target
                        if((e.target.parentElement.id=="people")&&(e.target.id!="")){
                            if(personSelElemId!=e.target.id) {
                                if(usrselectedMenu!=null) {
                                    usrselectedMenu.classList.remove('active');
                                }
                                personSelElemId = e.target.id;
                                usrselectedMenu = document.getElementById(e.target.id);

                                closeRightClickMenu();
                            }
                            // console.log(personSelElemId);
                            // console.log(e.target.parentElement.id);
                            // console.log("---------");
                        }

                    });

                    groupDiv.addEventListener("mouseover", function( e ) {
                        // highlight the mouseenter target

                        if((e.target.parentElement.id=="group")&&(e.target.id!="")&&(e.target.id!="grpMenu")){
                            if(groupSelElemId!=e.target.id) {
                                if(grpselectedMenu!=null) {
                                    grpselectedMenu.classList.remove('active');
                                }
                                groupSelElemId = e.target.id;
                                grpselectedMenu = document.getElementById(e.target.id);
                                closeRightClickMenu();
                                console.log(e.target.id);
                            }

                            // console.log(groupSelElemId);
                            // console.log(e.target.parentElement.id);
                            // console.log("---------");
                        }

                    });
                    //groupDiv.addEventListener("contextmenu",openUserRightClickMenu);
                    groupDiv.addEventListener("contextmenu",openGroupRightClickMenu);
                    function closeRightClickMenu(){
                        if(usrselectedMenu!=null) {
                            usrselectedMenu.classList.remove('active');
                        }
                        if(grpselectedMenu!=null) {
                            grpselectedMenu.classList.remove('active');
                        }
                        console.log('closeMenu');
                        menu.style.height = '0';
                        menu.classList.remove('right-click-is-active');
                        grpmenu.style.height = '0';
                        grpmenu.classList.remove('right-click-is-active');
                    }

                    function openUserRightClickMenu(e){
                        usrselectedMenu.classList.add('active');
                        console.log('openMenu '+menuHeight);
                        e.preventDefault();

                        //menu.style.left = (e.clientX-500)+'px';
                        menu.style.left = (250)+'px';
                        menu.style.top = (e.clientY-80)+'px';
                        //menu.style.top = (0)+'px';
                        menu.style.height = menuHeight+'px';
                        //把css class 加给menu
                        menu.classList.add('right-click-is-active');

                        return false;
                    }
                    function openGroupRightClickMenu(e){
                        grpselectedMenu.classList.add('active');
                        console.log('openMenu '+menuHeight);
                        e.preventDefault();

                        //menu.style.left = (e.clientX-500)+'px';
                        grpmenu.style.left = (250)+'px';
                        grpmenu.style.top = (e.clientY-80)+'px';
                        //menu.style.top = (0)+'px';
                        grpmenu.style.height = menuHeight+'px';
                        //把css class 加给menu
                        grpmenu.classList.add('right-click-is-active');

                        return false;
                    }
                    window.onclick = closeRightClickMenu;

                    fileInput.addEventListener('change', function() {
                        if (!fileInput.value) {
                            info.innerHTML = '没有选择文件';
                            return;
                        }
                        var file = fileInput.files[0];
                        // 获取File信息:
                        <%--info.innerHTML = `文件名称:  + ${file.name}<br>文件大小: ${file.size} <br>上传时间: ${file.lastModifiedDate}`;--%>
                        <%--if (!['image/jpeg', 'image/png', 'image/gif'].includes(file.type)) {--%>
                        <%--    alert('不是有效的图片文件!');--%>
                        <%--    return;--%>
                        <%--}--%>
                        // 读取文件:
                        let reader = new FileReader();
                        reader.onload = function(e) {
                            var data = e.target.result;
                            console.log("图片数据");
                            console.log(data);
                            sendPicture(data);
                        };
                        // 以DataURL的形式读取文件:
                        reader.readAsDataURL(file);
                        // console.log("以下是file")
                        // console.log(file);

                    });


                    var groupMemberCheck = window.setInterval(function() {

                        if(groupNow!=null){
                            var url = "/group/getGroupMember";
                            var data = '{"name":"'+groupNow+'"}';
                            $.ajax({
                                headers:{'Content-Type':'application/json;charset=utf8'},
                                type: "POST",
                                url: url,
                                data: data,
                                dataType: "json",
                                success: function (jdata, status) {
                                    console.log("Status: " + status);
                                    console.log(jdata);
                                    $("#rtn").html("返回结果：" + JSON.stringify(jdata));
                                    if(memberListNow!=jdata) {
                                        var e = document.getElementById("people");
                                        var child = e.lastElementChild;
                                        while (child) {
                                            e.removeChild(child);
                                            child = e.lastElementChild;
                                        }
                                        memberListNow = jdata;
                                        var userList = jdata;
                                        console.log(userList);
                                        for (var i = 0; i < userList.length; i++) {
                                            addUserToList(userList[i].username);
                                        }
                                    }
                                },
                                error: function (xhr, status) {
                                    console.log("Status: " + status);
                                    console.log(xhr);
                                    window.alert("请求数据失败");
                                }
                            });
                        }

                    },10000)

                }
            </script>


        </div>
    </div>
</div>

<div style="text-align:center;margin:1px 0; font:normal 14px/24px 'MicroSoft YaHei';">
</div>
</body>
<jsp:include page="tail.jsp"/>