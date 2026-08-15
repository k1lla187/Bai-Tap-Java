<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dang nhap</title>
</head>
<body>
    <h2>Dang nhap he thong</h2>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <label>Ten dang nhap:</label><br>
        <input type="text" name="username"><br><br>
        <label>Mat khau:</label><br>
        <input type="password" name="password"><br><br>
        <button type="submit">Dang nhap</button>
    </form>
    <p style="color:red">${error}</p>
    <p><small>Tai khoan mau: admin/123456 hoac user/123456</small></p>
</body>
</html>
