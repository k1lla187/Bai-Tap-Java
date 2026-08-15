<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
%>
<!DOCTYPE html>
<html>
<head>
    <title>403 - Forbidden</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f5f5f5;
        }
        .error-container {
            text-align: center;
            padding: 40px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #e74c3c;
            font-size: 72px;
            margin: 0;
        }
        p {
            color: #666;
            font-size: 18px;
        }
        a {
            color: #3498db;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <h1>403</h1>
        <p>Ban khong co quyen truy cap trang nay.</p>
        <p>Vui long <a href="<%= request.getContextPath() %>/login.jsp">dang nhap</a> de tiep tuc.</p>
    </div>
</body>
</html>
