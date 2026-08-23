<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Danh sach Sinh Vien</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background: #f5f5f5;
        }
        .container {
            max-width: 1200px;
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
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .nav {
            margin: 15px 0;
        }
        .nav a {
            margin-right: 15px;
            color: #667eea;
            text-decoration: none;
        }
        .search-form {
            margin-bottom: 20px;
            padding: 15px;
            background: #f8f9fa;
            border-radius: 5px;
        }
        .search-form input[type="text"] {
            padding: 8px;
            width: 300px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .search-form button {
            padding: 8px 15px;
            background: #667eea;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background: #667eea;
            color: white;
        }
        tr:hover {
            background: #f5f5f5;
        }
        .btn {
            padding: 5px 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            color: white;
        }
        .btn-primary {
            background: #667eea;
        }
        .btn-danger {
            background: #dc3545;
        }
        .btn-sm {
            padding: 3px 8px;
            font-size: 12px;
        }
        .pagination {
            margin-top: 20px;
            text-align: center;
        }
        .pagination a {
            padding: 8px 12px;
            margin: 0 5px;
            background: #f0f0f0;
            text-decoration: none;
            color: #333;
            border-radius: 4px;
        }
        .pagination a.active {
            background: #667eea;
            color: white;
        }
        .error {
            color: red;
            padding: 10px;
            background: #ffe6e6;
            border-radius: 5px;
            margin-bottom: 15px;
        }
        .success {
            color: green;
            padding: 10px;
            background: #e6ffe6;
            border-radius: 5px;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Quan ly Sinh Vien</h1>
            <div class="nav">
                <a href="${pageContext.request.contextPath}/">Trang chu</a>
                <a href="${pageContext.request.contextPath}/logout">Dang xuat</a>
            </div>
        </div>

        <% if (request.getAttribute("error") != null) { %>
            <p class="error"><%= request.getAttribute("error") %></p>
        <% } %>
        <% if (request.getAttribute("success") != null) { %>
            <p class="success"><%= request.getAttribute("success") %></p>
        <% } %>

        <div class="search-form">
            <form action="${pageContext.request.contextPath}/sinh-vien" method="get">
                <input type="text" name="keyword" placeholder="Tim kiem theo ten hoac lop..." value="${keyword}">
                <button type="submit">Tim kiem</button>
                <a href="${pageContext.request.contextPath}/sinh-vien">Xoa tim kiem</a>
            </form>
        </div>

        <c:if test="${sessionScope.role == 'ADMIN'}">
            <p><a href="${pageContext.request.contextPath}/views/sinhvien/form.jsp" class="btn btn-primary">Them Sinh Vien</a></p>
        </c:if>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Ma SV</th>
                    <th>Ho Ten</th>
                    <th>Email</th>
                    <th>Lop</th>
                    <th>Hanh dong</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="sv" items="${dsSinhVien}">
                    <tr>
                        <td>${sv.id}</td>
                        <td>${sv.maSinhVien}</td>
                        <td>${sv.hoTen}</td>
                        <td>${sv.email}</td>
                        <td>${sv.lop}</td>
                        <td>
                            <c:if test="${sessionScope.role == 'ADMIN'}">
                                <a href="${pageContext.request.contextPath}/sinh-vien?action=edit&id=${sv.id}" class="btn btn-primary btn-sm">Sua</a>
                                <a href="${pageContext.request.contextPath}/sinh-vien?action=delete&id=${sv.id}" 
                                   class="btn btn-danger btn-sm" 
                                   onclick="return confirm('Ban co chac chan xoa?')">Xoa</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty dsSinhVien}">
                    <tr>
                        <td colspan="6" style="text-align: center;">Khong co sinh vien nao</td>
                    </tr>
                </c:if>
            </tbody>
        </table>

        <c:if test="${totalPages != null}">
            <div class="pagination">
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <a href="${pageContext.request.contextPath}/sinh-vien?page=${i}" 
                       class="${currentPage == i ? 'active' : ''}">${i}</a>
                </c:forEach>
            </div>
        </c:if>
    </div>
</body>
</html>
