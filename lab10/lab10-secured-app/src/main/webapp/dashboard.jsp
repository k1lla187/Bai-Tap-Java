<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - Lab 10</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f4f6f9; min-height: 100vh; }
        .navbar {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            padding: 0 40px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            box-shadow: 0 2px 10px rgba(0,0,0,0.15);
        }
        .navbar-brand {
            color: white;
            font-size: 22px;
            font-weight: 700;
            padding: 18px 0;
        }
        .navbar-right {
            display: flex;
            align-items: center;
            gap: 20px;
            color: white;
        }
        .navbar-right .user-info {
            font-size: 14px;
        }
        .navbar-right .role-badge {
            background: rgba(255,255,255,0.25);
            padding: 4px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
        }
        .navbar-right .btn-logout {
            color: white;
            text-decoration: none;
            font-size: 14px;
            padding: 6px 16px;
            border: 1px solid rgba(255,255,255,0.4);
            border-radius: 6px;
            transition: background 0.2s;
        }
        .navbar-right .btn-logout:hover {
            background: rgba(255,255,255,0.2);
        }
        .container { max-width: 1200px; margin: 40px auto; padding: 0 20px; }
        h1 { color: #333; margin-bottom: 8px; }
        .subtitle { color: #777; margin-bottom: 30px; font-size: 15px; }
        .grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        .card {
            background: white;
            border-radius: 12px;
            padding: 28px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            transition: transform 0.2s, box-shadow 0.2s;
        }
        .card:hover {
            transform: translateY(-4px);
            box-shadow: 0 8px 24px rgba(0,0,0,0.12);
        }
        .card h3 { color: #333; margin-bottom: 10px; font-size: 18px; }
        .card p { color: #777; font-size: 14px; margin-bottom: 16px; line-height: 1.6; }
        .card .card-icon {
            font-size: 40px;
            margin-bottom: 12px;
        }
        .card .card-link {
            display: inline-block;
            padding: 8px 18px;
            border-radius: 6px;
            text-decoration: none;
            font-size: 14px;
            font-weight: 500;
            transition: background 0.2s;
        }
        .card .card-link.admin { background: #667eea; color: white; }
        .card .card-link.admin:hover { background: #5568d3; }
        .card .card-link.staff { background: #43a047; color: white; }
        .card .card-link.staff:hover { background: #388e3c; }
        .card .card-link.user { background: #ffa726; color: white; }
        .card .card-link.user:hover { background: #fb8c00; }
    </style>
</head>
<body>
    <nav class="navbar">
        <div class="navbar-brand">Lab 10 - Secured App</div>
        <div class="navbar-right">
            <div class="user-info">
                Xin chao, <strong>${sessionScope.userName}</strong>
            </div>
            <span class="role-badge">${sessionScope.userRole}</span>
            <a href="${pageContext.request.contextPath}/auth?action=logout" class="btn-logout">Dang xuat</a>
        </div>
    </nav>

    <div class="container">
        <h1>Xin chao, ${sessionScope.userName}</h1>
        <p class="subtitle">Tai khoan: ${sessionScope.userEmail} | Vai tro: ${sessionScope.userRole}</p>

        <div class="grid">
            <div class="card">
                <div class="card-icon">&#128100;</div>
                <h3>Quan ly tai khoan</h3>
                <p>Xem, them, sua, xoa tai khoan nguoi dung. Chi ADMIN moi co quyen truy cap.</p>
                <c:if test="${sessionScope.userRole == 'ADMIN'}">
                    <a href="${pageContext.request.contextPath}/admin/users" class="card-link admin">Truy cap</a>
                </c:if>
                <c:if test="${sessionScope.userRole != 'ADMIN'}">
                    <span style="color:#ccc; font-size:13px;">Chi ADMIN</span>
                </c:if>
            </div>

            <div class="card">
                <div class="card-icon">&#128221;</div>
                <h3>Ho so ca nhan</h3>
                <p>Xem va cap nhat thong tin ca nhan, doi mat khau tai khoan.</p>
                <a href="${pageContext.request.contextPath}/user/profile" class="card-link user">Truy cap</a>
            </div>

            <div class="card">
                <div class="card-icon">&#128187;</div>
                <h3>Module nghiep vu</h3>
                <p>Quan ly du lieu nghiep vu. STAFF co the them/sua, ADMIN toan quyen.</p>
                <a href="${pageContext.request.contextPath}/staff/index" class="card-link staff">Truy cap</a>
            </div>
        </div>
    </div>
</body>
</html>
