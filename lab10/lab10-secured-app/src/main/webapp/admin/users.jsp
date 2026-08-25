<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quan ly tai khoan - Admin</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f4f6f9; }
        .navbar { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); padding: 0 40px; display: flex; align-items: center; justify-content: space-between; box-shadow: 0 2px 10px rgba(0,0,0,0.15); }
        .navbar-brand { color: white; font-size: 22px; font-weight: 700; padding: 18px 0; }
        .navbar-right { display: flex; align-items: center; gap: 20px; color: white; }
        .navbar-right .user-info { font-size: 14px; }
        .navbar-right .role-badge { background: rgba(255,255,255,0.25); padding: 4px 12px; border-radius: 20px; font-size: 12px; font-weight: 600; }
        .navbar-right .btn-logout, .navbar-right .btn-home { color: white; text-decoration: none; font-size: 14px; padding: 6px 16px; border: 1px solid rgba(255,255,255,0.4); border-radius: 6px; }
        .container { max-width: 1200px; margin: 30px auto; padding: 0 20px; }
        .page-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        .page-header h1 { color: #333; font-size: 26px; }
        .btn { padding: 10px 20px; border: none; border-radius: 8px; cursor: pointer; font-size: 14px; font-weight: 600; text-decoration: none; display: inline-block; }
        .btn-primary { background: #667eea; color: white; }
        .btn-primary:hover { background: #5568d3; }
        .btn-danger { background: #dc3545; color: white; }
        .btn-danger:hover { background: #c82333; }
        .btn-secondary { background: #6c757d; color: white; }
        .search-box { background: white; padding: 20px; border-radius: 12px; margin-bottom: 20px; box-shadow: 0 2px 8px rgba(0,0,0,0.06); }
        .search-box input { padding: 10px 14px; border: 2px solid #e0e0e0; border-radius: 8px; width: 300px; font-size: 14px; }
        .search-box input:focus { outline: none; border-color: #667eea; }
        table { width: 100%; background: white; border-radius: 12px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.06); border-collapse: collapse; }
        th { background: #667eea; color: white; padding: 14px 16px; text-align: left; font-weight: 600; }
        td { padding: 12px 16px; border-bottom: 1px solid #f0f0f0; color: #555; }
        tr:last-child td { border-bottom: none; }
        tr:hover td { background: #f8f9ff; }
        .badge { padding: 4px 10px; border-radius: 20px; font-size: 12px; font-weight: 600; }
        .badge-ADMIN { background: #667eea; color: white; }
        .badge-STAFF { background: #43a047; color: white; }
        .badge-USER { background: #ffa726; color: white; }
        .badge-active { background: #e8f5e9; color: #2e7d32; }
        .badge-inactive { background: #ffebee; color: #c62828; }
        .actions a { margin-right: 8px; font-size: 13px; text-decoration: none; padding: 4px 10px; border-radius: 4px; }
        .actions .edit { background: #e3f2fd; color: #1565c0; }
        .actions .delete { background: #ffebee; color: #c62828; }
        .error { background: #ffe6e6; color: #dc3545; padding: 12px 16px; border-radius: 8px; margin-bottom: 16px; border: 1px solid #ffcccc; }
        .success { background: #e6ffe6; color: #2e7d32; padding: 12px 16px; border-radius: 8px; margin-bottom: 16px; border: 1px solid #c8e6c9; }
    </style>
</head>
<body>
    <nav class="navbar">
        <div class="navbar-brand">Lab 10 - Quan ly tai khoan</div>
        <div class="navbar-right">
            <span class="user-info">${sessionScope.userName}</span>
            <span class="role-badge">${sessionScope.userRole}</span>
            <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn-home">Trang chu</a>
            <a href="${pageContext.request.contextPath}/auth?action=logout" class="btn-logout">Dang xuat</a>
        </div>
    </nav>

    <div class="container">
        <div class="page-header">
            <h1>Quan ly tai khoan nguoi dung</h1>
            <a href="${pageContext.request.contextPath}/admin/user-form.jsp" class="btn btn-primary">+ Them tai khoan</a>
        </div>

        <c:if test="${not empty error}"><div class="error">${error}</div></c:if>
        <c:if test="${not empty success}"><div class="success">${success}</div></c:if>

        <div class="search-box">
            <form method="get" action="${pageContext.request.contextPath}/admin/users">
                <input type="text" name="keyword" placeholder="Tim kiem theo email hoac ten..." value="${keyword}">
                <button type="submit" class="btn btn-primary" style="margin-left:8px;">Tim kiem</button>
                <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-secondary">Xoa tim kiem</a>
            </form>
        </div>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Email</th>
                    <th>Ho ten</th>
                    <th>Vai tro</th>
                    <th>Trang thai</th>
                    <th>Han dong</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="u" items="${users}">
                    <tr>
                        <td>${u.id}</td>
                        <td>${u.email}</td>
                        <td>${u.fullName}</td>
                        <td><span class="badge badge-${u.role}">${u.role}</span></td>
                        <td>
                            <span class="badge ${u.active ? 'badge-active' : 'badge-inactive'}">
                                ${u.active ? 'Hoat dong' : 'Bi khoa'}
                            </span>
                        </td>
                        <td class="actions">
                            <a href="${pageContext.request.contextPath}/admin/users?action=edit&id=${u.id}" class="edit">Sua</a>
                            <a href="${pageContext.request.contextPath}/admin/users?action=delete&id=${u.id}"
                               class="delete" onclick="return confirm('Ban co chac chan xoa tai khoan nay?')">Xoa</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty users}">
                    <tr><td colspan="6" style="text-align:center; color:#999; padding:30px;">Khong co tai khoan nao</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>
