<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chinh sua sinh vien</title>
</head>
<body>
    <h2>Chinh sua sinh vien</h2>
    <form action="${pageContext.request.contextPath}/students" method="post">
        <input type="hidden" name="action" value="update">
        <label>Ma sinh vien:</label><br>
        <input type="text" name="id" value="${student.id}" readonly><br><br>
        <label>Ho ten:</label><br>
        <input type="text" name="name" value="${student.name}" required><br><br>
        <label>Lop:</label><br>
        <input type="text" name="className" value="${student.className}" required><br><br>
        <label>Email:</label><br>
        <input type="email" name="email" value="${student.email}" required><br><br>
        <button type="submit">Luu thay doi</button>
    </form>
    <p><a href="${pageContext.request.contextPath}/students">Quay lai danh sach</a></p>
</body>
</html>
