<html>
<head>
    <title>freemarker</title>
</head>
<body>
    <h2>登录成功</h2><br>
    <@shiro.hasRole name="admin">
        <a href="admin">admin页面</a><br>
    </@shiro.hasRole>
    <br>
    <@shiro.hasPermission name="/list">
        list
    </@shiro.hasPermission>
    <br>
    <a href="logout">退出登录</a>
</body>
</html>
