<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table align='center' border='1' cellspacing='0'>
    <tr>
        <td>id</td>
        <td>Name</td>
        <td>NickName</td>
        <td>isSystem</td>
        <td>Icon</td>
        <td>Options</td>
    </tr>
    <c:forEach items="${users}" var="usr" varStatus="st">
        <tr>
            <td>${usr.id}</td>
            <td>${usr.username}</td>
            <td>${usr.nickname}</td>
            <td>${usr.issystem}</td>
            <td>
                <img alt="img" src="data:image/jpeg;base64,${usr.base64Icon}" style="height: 64px;width:64px;"/>
            </td>
            <td>
                <form action="/user/doShowOneUserInfo" method="post" enctype ="multipart/form-data">
                    <input name="username" type="text" value="${usr.username}" style="visibility: hidden;width: 0px">
                    <input  class="btn btn-primary" type="submit" value="详情" style="float: left">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<jsp:include page="tail.jsp"/>