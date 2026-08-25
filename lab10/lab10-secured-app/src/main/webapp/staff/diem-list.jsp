<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quan ly Diem</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f4f6f9; }
        .navbar { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); padding: 0 40px; display: flex; align-items: center; justify-content: space-between; }
        .navbar-brand { color: white; font-size: 22px; font-weight: 700; padding: 18px 0; }
        .navbar-right { display: flex; align-items: center; gap: 16px; color: white; }
        .navbar-right a { color: white; text-decoration: none; font-size: 14px; padding: 6px 14px; border: 1px solid rgba(255,255,255,0.4); border-radius: 6px; }
        .container { max-width: 1100px; margin: 30px auto; padding: 0 20px; }
        .page-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        h1 { color: #333; font-size: 26px; }
        .btn { padding: 10px 20px; border: none; border-radius: 8px; cursor: pointer; font-size: 14px; font-weight: 600; text-decoration: none; display: inline-block; }
        .btn-primary { background: #43a047; color: white; }
        .btn-secondary { background: #6c757d; color: white; }
        table { width: 100%; background: white; border-radius: 12px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.06); border-collapse: collapse; }
        th { background: #43a047; color: white; padding: 14px 16px; text-align: left; }
        td { padding: 12px 16px; border-bottom: 1px solid #f0f0f0; color: #555; }
        tr:last-child td { border-bottom: none; }
        tr:hover td { background: #f8fff8; }
        .actions a { margin-right: 8px; font-size: 13px; text-decoration: none; padding: 4px 10px; border-radius: 4px; }
        .actions .edit { background: #e3f2fd; color: #1565c0; }
        .actions .delete { background: #ffebee; color: #c62828; }
        .error { background: #ffe6e6; color: #dc3545; padding: 12px 16px; border-radius: 8px; margin-bottom: 16px; }
        .badge { padding: 3px 10px; border-radius: 20px; font-size: 12px; font-weight: 600; }
        .badge-gioi { background: #e8f5e9; color: #2e7d32; }
        .badge-kha { background: #e3f2fd; color: #1565c0; }
        .badge-tb { background: #fff8e1; color: #f57f17; }
        .badge-yeu { background: #ffebee; color: #c62828; }
    </style>
</head>
<body>
    <nav class="navbar">
        <div class="navbar-brand">Quan ly Diem</div>
        <div class="navbar-right">
            <span>${sessionScope.userName} (${sessionScope.userRole})</span>
            <a href="${pageContext.request.contextPath}/dashboard.jsp">Trang chu</a>
            <a href="${pageContext.request.contextPath}/staff/index.jsp">Module</a>
            <a href="${pageContext.request.contextPath}/auth?action=logout">Dang xuat</a>
        </div>
    </nav>
    <div class="container">
        <div class="page-header">
            <h1>Danh sach Diem</h1>
            <a href="${pageContext.request.contextPath}/staff/diem-form.jsp" class="btn btn-primary">+ Nhap Diem</a>
        </div>
        <c:if test="${not empty error}"><div class="error">${error}</div></c:if>
        <table>
            <thead><tr><th>ID</th><th>Sinh Vien</th><th>Mon Hoc</th><th>Diem GK</th><th>Diem CK</th><th>Tong Ket</th><th>Xep Loai</th><th>Hanh dong</th></tr></thead>
            <tbody>
                <c:forEach var="d" items="${dsDiem}">
                    <tr>
                        <td>${d.id}</td>
                        <td>${d.sinhVien.hoTen} (${d.sinhVien.maSV})</td>
                        <td>${d.monHoc.tenMon}</td>
                        <td>${d.diemGK}</td>
                        <td>${d.diemCK}</td>
                        <td>${d.diemTongKet}</td>
                        <td>
                            <c:if test="${not empty d.xepLoai}">
                                <span class="badge badge-${d.xepLoai == 'Gioi' ? 'gioi' : d.xepLoai == 'Kha' ? 'kha' : d.xepLoai == 'Trung binh' ? 'tb' : 'yeu'}">
                                    ${d.xepLoai}
                                </span>
                            </c:if>
                        </td>
                        <td class="actions">
                            <a href="${pageContext.request.contextPath}/staff/diem?action=edit&id=${d.id}" class="edit">Sua</a>
                            <a href="${pageContext.request.contextPath}/staff/diem?action=delete&id=${d.id}" class="delete" onclick="return confirm('Xoa diem nay?')">Xoa</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty dsDiem}"><tr><td colspan="8" style="text-align:center;color:#999;padding:30px;">Chua co du lieu diem</td></tr></c:if>
            </tbody>
        </table>
    </div>
</body>
</html>
