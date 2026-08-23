<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="vn.edu.eaut.lab9.model.SinhVien" %>
<%@ page import="jakarta.servlet.http.HttpServletResponse" %>
<%
    SinhVien sv = (SinhVien) request.getAttribute("sinhVien");
    boolean isEdit = sv != null && sv.getId() != null;
    String pageTitle = isEdit ? "Chinh sua Sinh Vien" : "Them Sinh Vien";
    if (!"ADMIN".equals(session.getAttribute("role"))) {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Ban khong co quyen thuc hien thao tac nay");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title><%= pageTitle %></title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background: #f5f5f5;
        }
        .container {
            max-width: 600px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            border-bottom: 2px solid #667eea;
            padding-bottom: 10px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            color: #555;
            font-weight: bold;
        }
        input[type="text"],
        input[type="email"],
        input[readonly] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 14px;
        }
        input[readonly] {
            background: #f0f0f0;
        }
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
        }
        .btn-primary {
            background: #667eea;
            color: white;
        }
        .btn-secondary {
            background: #6c757d;
            color: white;
        }
        .error {
            color: red;
            padding: 10px;
            background: #ffe6e6;
            border-radius: 5px;
            margin-bottom: 15px;
        }
        .nav {
            margin-top: 20px;
        }
        .nav a {
            color: #667eea;
            text-decoration: none;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1><%= pageTitle %></h1>

        <% if (request.getAttribute("error") != null) { %>
            <p class="error"><%= request.getAttribute("error") %></p>
        <% } %>

        <form action="${pageContext.request.contextPath}/sinh-vien" method="post">
            <% if (isEdit) { %>
                <input type="hidden" name="id" value="<%= sv.getId() %>">
            <% } %>

            <div class="form-group">
                <label>Ma Sinh Vien (<span style="color: red">*</span>):</label>
                <input type="text" name="maSinhVien" value="<%= sv != null ? sv.getMaSinhVien() : "" %>" 
                       <%= isEdit ? "readonly" : "required" %>>
                <% if (isEdit) { %>
                    <small style="color: #666;">Ma sinh vien khong the thay doi</small>
                <% } %>
            </div>

            <div class="form-group">
                <label>Ho Ten (<span style="color: red">*</span>):</label>
                <input type="text" name="hoTen" value="<%= sv != null ? sv.getHoTen() : "" %>" required>
            </div>

            <div class="form-group">
                <label>Email:</label>
                <input type="email" name="email" value="<%= sv != null && sv.getEmail() != null ? sv.getEmail() : "" %>">
            </div>

            <div class="form-group">
                <label>Lop:</label>
                <input type="text" name="lop" value="<%= sv != null && sv.getLop() != null ? sv.getLop() : "" %>">
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-primary">
                    <%= isEdit ? "Luu thay doi" : "Them moi" %>
                </button>
                <a href="${pageContext.request.contextPath}/sinh-vien" class="btn btn-secondary">Huy</a>
            </div>
        </form>

        <div class="nav">
            <a href="${pageContext.request.contextPath}/sinh-vien">Quay lai danh sach</a>
        </div>
    </div>
</body>
</html>
