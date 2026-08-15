<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="vn.edu.eaut.lab6.store.StudentStore" %>
<%@ page import="vn.edu.eaut.lab6.model.Student" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.stream.Collectors" %>
<%
    java.util.List<Student> allStudents = StudentStore.findAll();
    int totalStudents = allStudents.size();
    java.util.Map<String, Long> byClass = allStudents.stream()
        .collect(Collectors.groupingBy(Student::getClassName, Collectors.counting()));
%>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 20px; }
        .stat-card { background: #f0f0f0; padding: 15px; margin: 10px 0; border-radius: 5px; }
        .welcome { color: #2c3e50; }
        .class-list { margin-top: 20px; }
    </style>
</head>
<body>
    <h1>Dashboard</h1>
    <p>Xin chao, <strong>${sessionScope.username}</strong></p>
    <p>Vai tro: <strong>${sessionScope.role}</strong></p>
    <p>Thoi gian dang nhap: <%= new java.util.Date() %></p>

    <div class="stat-card">
        <h3>Tong so sinh vien: <%= totalStudents %></h3>
    </div>

    <div class="class-list">
        <h3>So sinh vien theo lop:</h3>
        <% for (Map.Entry<String, Long> entry : byClass.entrySet()) { %>
            <p><%= entry.getKey() %>: <%= entry.getValue() %> sinh vien</p>
        <% } %>
    </div>

    <br>
    <a href="${pageContext.request.contextPath}/students">Quan ly sinh vien</a> |
    <a href="${pageContext.request.contextPath}/logout">Dang xuat</a>
</body>
</html>
