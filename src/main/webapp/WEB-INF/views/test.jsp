<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>测试页面</title>
</head>
<body style="background-color:#ddd;">
<button id="submit">点击测试</button><br />
<label id="rtn"></label>
<script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
<script>
    $(function(){
        console.log("opened");
    });

    $("#submit").click(function(){
        console.log("clicked");
        var url = "/test/call";
        var data = '{"id":"12","name":"myname"}';
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
            },
            error: function (xhr, status) {
                console.log("Status: " + status);
                console.log(xhr);
                window.alert("请求数据失败");
            }
        });
    });
</script>
</body>
</html>
