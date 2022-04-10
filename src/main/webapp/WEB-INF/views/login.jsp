<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
    #parent{
        width:500px;
        height:200px;
        margin-top:20%;
        margin-left:50%;
        transform:translate(-50%,-50%) ;
        background:#009688;
    }
    .password,.subBtn{
        margin-top: 2%;
        margin-left: 3%;
    }
    .loginHeader{
        padding-top: 1%;
    }
</style>

<div id="parent">
    <section class="loginBox">
        <header class="loginHeader" style="text-align:center; ">
            <h1>登录</h1>
        </header>
        <section class="loginCont">
            <form class="loginForm" action="login" method="post"  enctype ="multipart/form-data">
                <div style="visibility: ${error ? "visible" : "hidden"}">Failed to login </div>
                <div class="inputbox"  style="text-align:center; ">
                    <label for="username">用户名：</label>
                    <input id="username" type="text" name="userName" placeholder="请输入用户名" required="required" />
                    <input id="password" type="text" name="password" placeholder="请输入密码" required="required" />
                </div>
                <div class="subBtn"  style="text-align:center; ">
                    <input type="submit" value="提交" />
                </div>
            </form>
        </section>
    </section>
</div>