<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table align='center' border='1' cellspacing='0'>
    <tr>
        <td>Name</td>
        <td>${userInfo.username}</td>
    </tr>
    <tr>
        <td>NickName</td>
        <td>${userInfo.nickname}</td>
    </tr>
    <tr>
        <td>Icon</td>
        <td>
            <img alt="img" src="data:image/jpeg;base64,${userInfo.base64Icon}" style="height: 64px;width:64px;"/>
        </td>
    </tr>
</table>
<p>
    <a href="list">返回用户列表</a>
</p>
<jsp:include page="tail.jsp"/>