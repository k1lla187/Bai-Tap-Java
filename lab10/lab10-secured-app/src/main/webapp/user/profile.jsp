<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ho so ca nhan</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f4f6f9; min-height: 100vh; }
        .navbar { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); padding: 0 40px; display: flex; align-items: center; justify-content: space-between; box-shadow: 0 2px 10px rgba(0,0,0,0.15); }
        .navbar-brand { color: white; font-size: 22px; font-weight: 700; padding: 18px 0; }
        .navbar-right { display: flex; align-items: center; gap: 20px; color: white; }
        .navbar-right .user-info { font-size: 14px; }
        .navbar-right .role-badge { background: rgba(255,255,255,0.25); padding: 4px 12px; border-radius: 20px; font-size: 12px; font-weight: 600; }
        .navbar-right a { color: white; text-decoration: none; font-size: 14px; padding: 6px 16px; border: 1px solid rgba(255,255,255,0.4); border-radius: 6px; }
        .container { max-width: 700px; margin: 40px auto; padding: 0 20px; }
        h1 { color: #333; margin-bottom: 24px; font-size: 26px; }
        .card { background: white; border-radius: 16px; padding: 36px; box-shadow: 0 2px 12px rgba(0,0,0,0.08); margin-bottom: 24px; }
        .card h2 { color: #333; font-size: 18px; margin-bottom: 20px; padding-bottom: 12px; border-bottom: 2px solid #f0f0f0; }
        .form-group { margin-bottom: 18px; }
        .form-group label { display: block; margin-bottom: 6px; color: #555; font-weight: 500; font-size: 14px; }
        .form-group input, .form-group select { width: 100%; padding: 11px 14px; border: 2px solid #e0e0e0; border-radius: 8px; font-size: 14px; font-family: inherit; }
        .form-group input:focus, .form-group select:focus { outline: none; border-color: #667eea; }
        .btn { padding: 12px 24px; border: none; border-radius: 8px; cursor: pointer; font-size: 15px; font-weight: 600; display: inline-block; }
        .btn-primary { background: #667eea; color: white; }
        .btn-primary:hover { background: #5568d3; }
        .alert { padding: 12px 16px; border-radius: 8px; margin-bottom: 20px; font-size: 14px; }
        .alert-success { background: #e8f5e9; color: #2e7d32; border: 1px solid #c8e6c9; }
        .alert-error { background: #ffe6e6; color: #dc3545; border: 1px solid #ffcccc; }
        .info-row { display: flex; padding: 10px 0; border-bottom: 1px solid #f0f0f0; }
        .info-row:last-child { border-bottom: none; }
        .info-label { color: #888; width: 150px; font-size: 14px; }
        .info-value { color: #333; font-weight: 500; font-size: 14px; }
    </style>
</head>
<body>
    <nav class="navbar">
        <div class="navbar-brand">Lab 10 - Ho so ca nhan</div>
        <div class="navbar-right">
            <span class="user-info">${sessionScope.userName}</span>
            <span class="role-badge">${sessionScope.userRole}</span>
            <a href="${pageContext.request.contextPath}/dashboard.jsp">Trang chu</a>
            <a href="${pageContext.request.contextPath}/auth?action=logout">Dang xuat</a>
        </div>
    </nav>

    <div class="container">
        <h1>Ho so ca nhan</h1>

        <!-- Profile Info -->
        <div class="card">
            <h2>Thong tin tai khoan</h2>
            <div class="info-row">
                <span class="info-label">Email</span>
                <span class="info-value">${sessionScope.currentUser.email}</span>
            </div>
            <div class="info-row">
                <span class="info-label">Ho ten</span>
                <span class="info-value">${sessionScope.currentUser.fullName}</span>
            </div>
            <div class="info-row">
                <span class="info-label">Vai tro</span>
                <span class="info-value">${sessionScope.currentUser.role}</span>
            </div>
            <div class="info-row">
                <span class="info-label">So dien thoai</span>
                <span class="info-value">${sessionScope.currentUser.phone != null ? sessionScope.currentUser.phone : 'Chua cap nhat'}</span>
            </div>
        </div>

        <!-- Update Profile Form -->
        <div class="card">
            <h2>Cap nhat ho so</h2>
            <c:if test="${not empty error}"><div class="alert alert-error">${error}</div></c:if>
            <c:if test="${not empty success}"><div class="alert alert-success">${success}</div></c:if>

            <form method="post" action="${pageContext.request.contextPath}/user/profile">
                <input type="hidden" name="action" value="updateProfile">
                <div class="form-group">
                    <label for="fullName">Ho ten *</label>
                    <input type="text" id="fullName" name="fullName" value="${sessionScope.currentUser.fullName}" required>
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" value="${sessionScope.currentUser.email}" readonly style="background:#f5f5f5; color:#888;">
                </div>
                <div class="form-group">
                    <label for="phone">So dien thoai</label>
                    <input type="text" id="phone" name="phone" value="${sessionScope.currentUser.phone}" placeholder="0xxxxxxxxx">
                </div>
                <button type="submit" class="btn btn-primary">Luu thay doi</button>
            </form>
        </div>

        <!-- Change Password -->
        <div class="card">
            <h2>Doi mat khau</h2>
            <c:if test="${not empty passwordError}"><div class="alert alert-error">${passwordError}</div></c:if>
            <c:if test="${not empty passwordSuccess}"><div class="alert alert-success">${passwordSuccess}</div></c:if>

            <form method="post" action="${pageContext.request.contextPath}/user/profile">
                <input type="hidden" name="action" value="changePassword">
                <div class="form-group">
                    <label for="oldPassword">Mat khau cu *</label>
                    <input type="password" id="oldPassword" name="oldPassword" required>
                </div>
                <div class="form-group">
                    <label for="newPassword">Mat khau moi *</label>
                    <input type="password" id="newPassword" name="newPassword" required placeholder="It nhat 6 ky tu">
                </div>
                <div class="form-group">
                    <label for="confirmPassword">Xac nhan mat khau moi *</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required>
                </div>
                <button type="submit" class="btn btn-primary">Doi mat khau</button>
            </form>
        </div>
    </div>
</body>
</html>
