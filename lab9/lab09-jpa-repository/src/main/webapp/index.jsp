<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lab 9 - Quan ly Sinh Vien</title>
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
        .nav {
            margin: 20px 0;
            padding: 15px;
            background: #f8f9fa;
            border-radius: 5px;
        }
        .nav a {
            margin-right: 15px;
            color: #667eea;
            text-decoration: none;
        }
        .nav a:hover {
            text-decoration: underline;
        }
        .user-info {
            float: right;
            color: #666;
        }
        .clear {
            clear: both;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Lab 9 - JPA Repository</h1>
        
        <div class="nav">
            <span class="user-info">
                Xin chao, <strong>${sessionScope.username}</strong> (${sessionScope.role})
            </span>
            <div class="clear"></div>
            <br>
            <a href="${pageContext.request.contextPath}/sinh-vien">Quan ly Sinh Vien</a>
            <a href="${pageContext.request.contextPath}/logout">Dang xuat</a>
        </div>
        
        <h2>Tong quan</h2>
        <p>Ung dung quan ly sinh vien su dung JPA/Hibernate.</p>
        <ul>
            <li>Entity: SinhVien, LopHoc, MonHoc, Diem, User, Role</li>
            <li>Repository: Day la lop trung gian giua Service va EntityManager</li>
            <li>Transaction: Quan ly giao dich bang EntityTransaction</li>
            <li>JPQL: Truy van huong doi tuong</li>
        </ul>
        
        <h3>Huong dan</h3>
        <ol>
            <li>Dang nhap voi tai khoan admin/123456 hoac user/123456</li>
            <li>Truy cap Quan ly Sinh Vien de xem, them, sua, xoa sinh vien</li>
            <li>Du lieu duoc luu vao MySQL database</li>
        </ol>
    </div>
</body>
</html>
