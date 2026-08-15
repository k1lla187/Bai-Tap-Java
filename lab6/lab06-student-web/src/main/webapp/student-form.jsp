<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Them sinh vien</title>
</head>
<body>
    <h2>Them sinh vien</h2>
    <form action="${pageContext.request.contextPath}/students" method="post">
        <label>Ma sinh vien:</label><br>
        <input type="text" name="id" required><br><br>
        <label>Ho ten:</label><br>
        <input type="text" name="name" required><br><br>
        <label>Lop:</label><br>
        <input type="text" name="className" required><br><br>
        <label>Email:</label><br>
        <input type="email" name="email" required><br><br>
        <button type="submit">Luu sinh vien</button>
    </form>
    <p><a href="${pageContext.request.contextPath}/students">Quay lai danh sach</a></p>
</body>
</html>
