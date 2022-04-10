<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
Hi Jsp,现在时间是   ${now}
<p>你好${NickName}</p>
<p>
    <a href="listMessage">信息列表</a>
</p>
<p>
    <a href="user/list">用户列表</a>
</p>
<p>
    <a href="user/addUser">添加用户</a>
</p>
<p>
    <a href="chatPage">聊天窗</a>
</p>
<p>
    <a href="user/login">登录</a>
</p>


<div sec:authorize="hasRole('ROLE_admin')">
    <p class="bg-info">${adminonly}</p>
</div>
<div sec:authorize="hasRole('ROLE_chatuser')">
    <p class="bg-info">无更多显示信息</p>
</div>
