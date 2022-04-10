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
        <td>updateIcon</td>
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
                <form action="/user/doInsertIcon" method="post" enctype ="multipart/form-data">
                    <label for="file_${usr.id}" class="btn btn-primary" style="float: left;height: 30px;width: 180px;margin-right: 20px">选择用户头像</label>
                    <input  id="file_${usr.id}" name="file" type="file" style="float: left;display:none"/>
                    <input name="id" type="text" value="${usr.id}" style="visibility: hidden;width: 0px">
                    <input  class="btn btn-primary" type="submit" value="提交" style="float: left">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>