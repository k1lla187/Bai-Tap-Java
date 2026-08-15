<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
    <h2>Dashboard</h2>
    <p>Xin chao, <strong>${sessionScope.username}</strong></p>
    <p>Vai tro: <strong>${sessionScope.role}</strong></p>
    <p>Thoi gian dang nhap: <%= new java.util.Date() %></p>
    <p><a href="${pageContext.request.contextPath}/students">Quan ly sinh vien</a></p>
    <p><a href="${pageContext.request.contextPath}/logout">Dang xuat</a></p>
</body>
</html>
