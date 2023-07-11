<%--
  Created by IntelliJ IDEA.
  User: hai-kk
  Date: 2022/4/28
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h2>登录成功</h2><br>
    <shiro:hasRole name="admin">
        <a href="admin">admin页面</a><br>
    </shiro:hasRole>
    <shiro:hasPermission name="/list">
        list
    </shiro:hasPermission>
    <br>
    <a href="logout">退出登录</a>
</body>
</html>
