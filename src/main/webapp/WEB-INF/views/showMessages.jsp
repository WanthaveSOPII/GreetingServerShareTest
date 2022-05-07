<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table align='center' border='1' cellspacing='0'>
    <tr>
        <td>id</td>
        <td>info</td>
        <td>sender</td>
        <td>recver</td>
        <td>time</td>
        <td>zoneID</td>
    </tr>
    <c:forEach items="${messages}" var="s" varStatus="st">
        <tr>
            <td>${s.id}</td>
            <td>${s.info}</td>
            <td>${s.sender}</td>
            <td>${s.recver}</td>
            <td>${s.time}</td>
            <td>${s.zoneID}</td>
        </tr>
    </c:forEach>
</table>
<jsp:include page="tail.jsp"/>