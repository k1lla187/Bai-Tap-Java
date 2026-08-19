<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập hệ thống</title>
    <!-- Tích hợp Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0;
        }
        .login-card {
            background: white;
            padding: 2.5rem;
            border-radius: 12px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.2);
            width: 100%;
            max-width: 400px;
        }
    </style>
</head>
<body>
    <div class="login-card">
        <h3 class="text-center mb-4" style="color: #4a4a4a; font-weight: bold;">Đăng Nhập</h3>
        
        <!-- Hiển thị lỗi nếu đăng nhập sai -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger text-center p-2">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="mb-3">
                <label class="form-label text-muted">Tên đăng nhập</label>
                <input type="text" name="username" class="form-control" placeholder="Nhập admin" required>
            </div>
            <div class="mb-4">
                <label class="form-label text-muted">Mật khẩu</label>
                <input type="password" name="password" class="form-control" placeholder="Nhập admin123" required>
            </div>
            <button type="submit" class="btn btn-primary w-100" style="background: #667eea; border: none;">
                Đăng nhập
            </button>
        </form>

        <div class="mt-4 text-center text-muted" style="font-size: 0.85rem;">
            Demo Account: <b>admin</b> / <b>admin123</b>
        </div>
    </div>
</body>
</html>