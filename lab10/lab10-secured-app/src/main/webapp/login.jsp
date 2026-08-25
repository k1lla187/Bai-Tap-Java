<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Lab 10</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }
        .login-card {
            background: white;
            padding: 40px;
            border-radius: 16px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
            width: 400px;
            max-width: 90%;
        }
        .login-card h2 {
            color: #333;
            text-align: center;
            margin-bottom: 8px;
            font-size: 28px;
        }
        .login-card p.sub {
            text-align: center;
            color: #888;
            margin-bottom: 30px;
            font-size: 14px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            margin-bottom: 6px;
            color: #555;
            font-weight: 500;
            font-size: 14px;
        }
        .form-group input {
            width: 100%;
            padding: 12px 16px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            font-size: 15px;
            transition: border-color 0.3s;
        }
        .form-group input:focus {
            outline: none;
            border-color: #667eea;
        }
        .btn-login {
            width: 100%;
            padding: 14px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: transform 0.2s, box-shadow 0.2s;
        }
        .btn-login:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4);
        }
        .error-msg {
            background: #ffe6e6;
            color: #dc3545;
            padding: 12px 16px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 14px;
            border: 1px solid #ffcccc;
        }
        .info-box {
            background: #f0f0ff;
            border: 1px solid #d0d0ff;
            border-radius: 8px;
            padding: 16px;
            margin-top: 24px;
            font-size: 13px;
            color: #555;
        }
        .info-box strong { color: #333; }
    </style>
</head>
<body>
    <div class="login-card">
        <h2>Lab 10</h2>
        <p class="sub">He thong quan ly phan quyen</p>

        <c:if test="${not empty error}">
            <div class="error-msg">${error}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/auth">
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="email@example.com" required
                       value="${param.email}">
            </div>
            <div class="form-group">
                <label for="password">Mat khau</label>
                <input type="password" id="password" name="password" placeholder="********" required>
            </div>
            <button type="submit" class="btn-login">Dang Nhap</button>
        </form>

        <div class="info-box">
            <strong>Tai khoan test:</strong><br>
            admin@eaut.edu.vn / admin123 (ADMIN)<br>
            staff@eaut.edu.vn / staff123 (STAFF)<br>
            user@eaut.edu.vn / user123 (USER)
        </div>
    </div>
</body>
</html>
