<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách Sinh viên</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body { background: #f8f9fa; padding: 20px 0; }
        .container { margin-top: 30px; }
        .breadcrumb-container { margin-bottom: 20px; }
        .table-card { background: white; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.08); }
        .search-section { background: white; padding: 20px; border-radius: 12px; margin-bottom: 20px; box-shadow: 0 2px 8px rgba(0,0,0,0.05); }
        .table-header-title { padding: 20px; border-bottom: 2px solid #f0f0f0; }
        .table-header-title h3 { margin: 0; color: #333; font-weight: 600; }
        .btn-add { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border: none; }
        .btn-add:hover { background: linear-gradient(135deg, #764ba2 0%, #667eea 100%); }
        .table th { background: #f8f9fa; font-weight: 600; color: #333; border-bottom: 2px solid #e0e0e0; }
        .table tbody tr { border-bottom: 1px solid #e0e0e0; }
        .table tbody tr:hover { background: #f8f9fa; }
        .action-buttons a { margin: 0 3px; font-size: 14px; }
        .action-btn-edit { color: #0066cc; text-decoration: none; }
        .action-btn-delete { color: #dc3545; text-decoration: none; }
        .empty-state { text-align: center; padding: 60px 20px; }
        .empty-state-icon { font-size: 4rem; color: #ccc; margin-bottom: 20px; }
    </style>
</head>
<body style="background: #f8f9fa;">
<div class="container">
    <!-- Breadcrumb -->
    <div class="breadcrumb-container">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb" style="background: transparent; margin: 0;">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/" style="text-decoration: none;"><i class="bi bi-house"></i> Trang chủ</a></li>
                <li class="breadcrumb-item active">Quản lý Sinh viên</li>
            </ol>
        </nav>
    </div>

    <!-- Search Section -->
    <div class="search-section">
        <form method="get" action="${pageContext.request.contextPath}/sinh-vien" class="row g-3 align-items-end">
            <div class="col-md-8">
                <label class="form-label">Tìm kiếm</label>
                <input type="text" class="form-control" name="keyword" placeholder="Nhập tên hoặc lớp học...">
            </div>
            <div class="col-md-4">
                <button type="submit" class="btn btn-primary w-100"><i class="bi bi-search"></i> Tìm kiếm</button>
            </div>
        </form>
    </div>

    <!-- Data Table -->
    <div class="table-card">
        <div class="table-header-title d-flex justify-content-between align-items-center">
            <h3><i class="bi bi-person-badge"></i> Danh sách Sinh viên</h3>
            <a href="${pageContext.request.contextPath}/sinh-vien?action=new" class="btn btn-add btn-sm text-white">
                <i class="bi bi-plus-circle"></i> Thêm mới
            </a>
        </div>

        <div style="padding: 20px;">
            <c:if test="${empty dsSinhVien}">
                <div class="empty-state">
                    <div class="empty-state-icon"><i class="bi bi-inbox"></i></div>
                    <p style="color: #999; font-size: 16px;">Không có dữ liệu sinh viên</p>
                </div>
            </c:if>
            <c:if test="${not empty dsSinhVien}">
                <div style="overflow-x: auto;">
                    <table class="table table-hover mb-0">
                        <thead>
                            <tr>
                                <th style="width: 60px;">ID</th>
                                <th>Mã SV</th>
                                <th>Họ tên</th>
                                <th>Email</th>
                                <th>Lớp</th>
                                <th style="width: 150px; text-align: center;">Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="sv" items="${dsSinhVien}">
                                <tr>
                                    <td><span class="badge bg-light text-dark">${sv.id}</span></td>
                                    <td><strong>${sv.maSinhVien}</strong></td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/sinh-vien?action=detail&id=${sv.id}" style="text-decoration: none; color: #0066cc; font-weight: 500;">
                                            ${sv.hoTen}
                                        </a>
                                    </td>
                                    <td>${sv.email}</td>
                                    <td><span class="badge bg-info text-white">${sv.lop}</span></td>
                                    <td style="text-align: center;">
                                        <div class="action-buttons">
                                            <a href="${pageContext.request.contextPath}/sinh-vien?action=edit&id=${sv.id}" class="action-btn-edit" title="Sửa">
                                                <i class="bi bi-pencil-square"></i> Sửa
                                            </a>
                                            <span style="color: #ddd;">|</span>
                                            <a href="${pageContext.request.contextPath}/sinh-vien?action=delete&id=${sv.id}" class="action-btn-delete" onclick="return confirm('Bạn chắc chắn muốn xóa sinh viên này?')" title="Xóa">
                                                <i class="bi bi-trash"></i> Xóa
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
