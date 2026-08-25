<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quan ly Sinh Vien</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f4f6f9; }
        .navbar { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); padding: 0 40px; display: flex; align-items: center; justify-content: space-between; }
        .navbar-brand { color: white; font-size: 22px; font-weight: 700; padding: 18px 0; }
        .navbar-right { display: flex; align-items: center; gap: 16px; color: white; }
        .navbar-right a { color: white; text-decoration: none; font-size: 14px; padding: 6px 14px; border: 1px solid rgba(255,255,255,0.4); border-radius: 6px; }
        .container { max-width: 1200px; margin: 30px auto; padding: 0 20px; }
        .page-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        h1 { color: #333; font-size: 26px; }
        .btn { padding: 10px 20px; border: none; border-radius: 8px; cursor: pointer; font-size: 14px; font-weight: 600; text-decoration: none; display: inline-block; }
        .btn-primary { background: #43a047; color: white; }
        .btn-primary:hover { background: #388e3c; }
        .btn-danger { background: #dc3545; color: white; }
        .btn-secondary { background: #6c757d; color: white; }
        .search-box { background: white; padding: 20px; border-radius: 12px; margin-bottom: 20px; box-shadow: 0 2px 8px rgba(0,0,0,0.06); }
        .search-box input { padding: 10px 14px; border: 2px solid #e0e0e0; border-radius: 8px; width: 300px; font-size: 14px; }
        .search-box input:focus { outline: none; border-color: #667eea; }
        table { width: 100%; background: white; border-radius: 12px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.06); border-collapse: collapse; }
        th { background: #43a047; color: white; padding: 14px 16px; text-align: left; }
        td { padding: 12px 16px; border-bottom: 1px solid #f0f0f0; color: #555; }
        tr:last-child td { border-bottom: none; }
        tr:hover td { background: #f8fff8; }
        .actions a { margin-right: 8px; font-size: 13px; text-decoration: none; padding: 4px 10px; border-radius: 4px; }
        .actions .edit { background: #e3f2fd; color: #1565c0; }
        .actions .delete { background: #ffebee; color: #c62828; }
        .error { background: #ffe6e6; color: #dc3545; padding: 12px 16px; border-radius: 8px; margin-bottom: 16px; }
        .success { background: #e6ffe6; color: #2e7d32; padding: 12px 16px; border-radius: 8px; margin-bottom: 16px; }
    </style>
</head>
<body>
    <nav class="navbar">
        <div class="navbar-brand">Quan ly Sinh Vien</div>
        <div class="navbar-right">
            <span>${sessionScope.userName} (${sessionScope.userRole})</span>
            <a href="${pageContext.request.contextPath}/dashboard.jsp">Trang chu</a>
            <a href="${pageContext.request.contextPath}/staff/index.jsp">Module</a>
            <a href="${pageContext.request.contextPath}/auth?action=logout">Dang xuat</a>
        </div>
    </nav>
    <div class="container">
        <div class="page-header">
            <h1>Danh sach Sinh Vien</h1>
            <a href="${pageContext.request.contextPath}/staff/sinhvien-form.jsp" class="btn btn-primary">+ Them Sinh Vien</a>
        </div>
        <c:if test="${not empty error}"><div class="error">${error}</div></c:if>
        <c:if test="${not empty success}"><div class="success">${success}</div></c:if>
        <div class="search-box">
            <form method="get" action="${pageContext.request.contextPath}/staff/sinhvien">
                <input type="text" name="keyword" placeholder="Tim kiem ma SV, ten, lop..." value="${keyword}">
                <button type="submit" class="btn btn-primary" style="margin-left:8px;">Tim kiem</button>
                <a href="${pageContext.request.contextPath}/staff/sinhvien" class="btn btn-secondary">Xoa</a>
            </form>
        </div>
        <table>
            <thead><tr><th>ID</th><th>Ma SV</th><th>Ho ten</th><th>Email</th><th>Lop</th><th>Ngay sinh</th><th>Hanh dong</th></tr></thead>
            <tbody>
                <c:forEach var="s" items="${dsSinhVien}">
                    <tr>
                        <td>${s.id}</td><td>${s.maSV}</td><td>${s.hoTen}</td><td>${s.email}</td><td>${s.lop}</td><td>${s.ngaySinh}</td>
                        <td class="actions">
                            <a href="${pageContext.request.contextPath}/staff/sinhvien?action=edit&id=${s.id}" class="edit">Sua</a>
                            <a href="${pageContext.request.contextPath}/staff/sinhvien?action=delete&id=${s.id}" class="delete" onclick="return confirm('Xoa sinh vien nay?')">Xoa</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty dsSinhVien}"><tr><td colspan="7" style="text-align:center;color:#999;padding:30px;">Khong co sinh vien nao</td></tr></c:if>
            </tbody>
        </table>
    </div>
</body>
</html>
