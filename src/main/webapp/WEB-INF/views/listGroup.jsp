<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>

<table align='center' border='1' cellspacing='0'>
    <tr>
        <td>id</td>
        <td>Name</td>
        <td>Item</td>
    </tr>
    <c:forEach items="${groups}" var="group" varStatus="st">
        <tr>
            <td>${group.id}</td>
            <td>${group.name}</td>
            <td>
                <button onclick="joinGroup('${group.name}')">加入</button>
            </td>
        </tr>
    </c:forEach>
</table>

<script type="text/javascript">
    function joinGroup(groupname){
        var url = "/group/doJoinGroup";
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
                console.log("jdata.joinGroupFlag");
                if(jdata.joinGroupFlag == "success") {
                    window.alert("加入群组成功");
                }else if(jdata.joinGroupFlag == "ingroup"){
                    window.alert("你已经在群组中");
                }else{
                    window.alert("出现错误");
                }
            },
            error: function (xhr, status) {
                console.log("Status: " + status);
                console.log(xhr);
                window.alert("请求数据失败");
            }
        });

    }
</script>
<jsp:include page="tail.jsp"/>