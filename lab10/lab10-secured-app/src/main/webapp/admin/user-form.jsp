<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${empty user ? 'Them tai khoan' : 'Sua tai khoan'} - Admin</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f4f6f9; min-height: 100vh; display: flex; align-items: center; justify-content: center; }
        .form-card { background: white; padding: 40px; border-radius: 16px; box-shadow: 0 8px 30px rgba(0,0,0,0.1); width: 500px; max-width: 95%; }
        h2 { color: #333; margin-bottom: 6px; font-size: 24px; }
        .subtitle { color: #888; margin-bottom: 28px; font-size: 14px; }
        .form-group { margin-bottom: 18px; }
        .form-group label { display: block; margin-bottom: 6px; color: #555; font-weight: 500; font-size: 14px; }
        .form-group input, .form-group select { width: 100%; padding: 11px 14px; border: 2px solid #e0e0e0; border-radius: 8px; font-size: 14px; font-family: inherit; }
        .form-group input:focus, .form-group select:focus { outline: none; border-color: #667eea; }
        .checkbox-group { display: flex; align-items: center; gap: 8px; }
        .checkbox-group input { width: auto; }
        .btn-row { display: flex; gap: 12px; margin-top: 28px; }
        .btn { padding: 12px 24px; border: none; border-radius: 8px; cursor: pointer; font-size: 15px; font-weight: 600; text-decoration: none; display: inline-block; }
        .btn-primary { background: #667eea; color: white; }
        .btn-primary:hover { background: #5568d3; }
        .btn-secondary { background: #6c757d; color: white; }
        .btn-secondary:hover { background: #5a6268; }
        .error { background: #ffe6e6; color: #dc3545; padding: 12px 16px; border-radius: 8px; margin-bottom: 20px; font-size: 14px; border: 1px solid #ffcccc; }
        .top-bar { position: fixed; top: 0; left: 0; right: 0; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); padding: 12px 40px; display: flex; align-items: center; justify-content: space-between; box-shadow: 0 2px 10px rgba(0,0,0,0.15); }
        .top-bar .brand { color: white; font-weight: 700; font-size: 18px; }
        .top-bar a { color: rgba(255,255,255,0.8); text-decoration: none; font-size: 14px; }
        .top-bar a:hover { color: white; }
        .wrapper { padding-top: 60px; width: 100%; display: flex; justify-content: center; }
    </style>
</head>
<body>
    <div class="top-bar">
        <span class="brand">Lab 10 - Quan ly tai khoan</span>
        <a href="${pageContext.request.contextPath}/admin/users">&larr; Quay ve danh sach</a>
    </div>
    <div class="wrapper">
        <div class="form-card">
            <h2>${empty user ? 'Them tai khoan moi' : 'Sua tai khoan'}</h2>
            <p class="subtitle">${empty user ? 'Tao tai khoan nguoi dung moi' : 'Cap nhat thong tin tai khoan'}</p>

            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>

            <form method="post" action="${pageContext.request.contextPath}/admin/users">
                <c:if test="${not empty user}">
                    <input type="hidden" name="id" value="${user.id}">
                </c:if>

                <div class="form-group">
                    <label for="email">Email *</label>
                    <input type="email" id="email" name="email" value="${user.email}" required placeholder="email@example.com">
                </div>

                <div class="form-group">
                    <label for="fullName">Ho ten *</label>
                    <input type="text" id="fullName" name="fullName" value="${user.fullName}" required placeholder="Nguyen Van A">
                </div>

                <c:if test="${empty user}">
                    <div class="form-group">
                        <label for="password">Mat khau *</label>
                        <input type="password" id="password" name="password" required placeholder="It nhat 6 ky tu">
                    </div>
                </c:if>

                <div class="form-group">
                    <label for="role">Vai tro *</label>
                    <select id="role" name="role" required>
                        <option value="">-- Chon vai tro --</option>
                        <option value="ADMIN" ${user.role == 'ADMIN' ? 'selected' : ''}>ADMIN</option>
                        <option value="STAFF" ${user.role == 'STAFF' ? 'selected' : ''}>STAFF</option>
                        <option value="USER" ${user.role == 'USER' ? 'selected' : ''}>USER</option>
                    </select>
                </div>

                <div class="form-group checkbox-group">
                    <input type="checkbox" id="active" name="active" ${user.active != false ? 'checked' : ''}>
                    <label for="active" style="margin-bottom:0; font-weight:normal;">Tai khoan hoat dong</label>
                </div>

                <div class="btn-row">
                    <button type="submit" class="btn btn-primary">${empty user ? 'Tao tai khoan' : 'Luu thay doi'}</button>
                    <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-secondary">Huy</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
