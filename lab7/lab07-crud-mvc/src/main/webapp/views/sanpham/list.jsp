<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách Sản phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body { background: #f8f9fa; padding: 20px 0; }
        .container { margin-top: 30px; }
        .table-card { background: white; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.08); }
        .search-section { background: white; padding: 20px; border-radius: 12px; margin-bottom: 20px; box-shadow: 0 2px 8px rgba(0,0,0,0.05); }
        .table-header-title { padding: 20px; border-bottom: 2px solid #f0f0f0; }
        .table-header-title h3 { margin: 0; color: #333; font-weight: 600; }
        .btn-add { background: linear-gradient(135deg, #dc3545 0%, #c82333 100%); border: none; }
        .btn-add:hover { background: linear-gradient(135deg, #c82333 0%, #dc3545 100%); }
        .table th { background: #f8f9fa; font-weight: 600; color: #333; border-bottom: 2px solid #e0e0e0; }
        .table tbody tr { border-bottom: 1px solid #e0e0e0; }
        .table tbody tr:hover { background: #f8f9fa; }
        .action-buttons a { margin: 0 3px; font-size: 14px; }
        .action-btn-edit { color: #0066cc; text-decoration: none; }
        .action-btn-delete { color: #dc3545; text-decoration: none; }
    </style>
</head>
<body style="background: #f8f9fa;">
<div class="container">
    <!-- Breadcrumb -->
    <div style="margin-bottom: 20px;">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb" style="background: transparent; margin: 0;">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/" style="text-decoration: none;"><i class="bi bi-house"></i> Trang chủ</a></li>
                <li class="breadcrumb-item active">Quản lý Sản phẩm</li>
            </ol>
        </nav>
    </div>

    <!-- Search Section -->
    <div class="search-section">
        <form method="get" action="${pageContext.request.contextPath}/san-pham" class="row g-3 align-items-end">
            <div class="col-md-8">
                <label class="form-label">Tìm kiếm</label>
                <input type="text" class="form-control" name="keyword" placeholder="Nhập tên sản phẩm...">
            </div>
            <div class="col-md-4">
                <button type="submit" class="btn btn-primary w-100"><i class="bi bi-search"></i> Tìm kiếm</button>
            </div>
        </form>
    </div>

    <!-- Data Table -->
    <div class="table-card">
        <div class="table-header-title d-flex justify-content-between align-items-center">
            <h3><i class="bi bi-bag-check"></i> Danh sách Sản phẩm</h3>
            <a href="${pageContext.request.contextPath}/san-pham?action=new" class="btn btn-add btn-sm text-white">
                <i class="bi bi-plus-circle"></i> Thêm mới
            </a>
        </div>

        <div style="padding: 20px;">
            <c:if test="${empty dsSanPham}">
                <div style="text-align: center; padding: 60px 20px;">
                    <div style="font-size: 4rem; color: #ccc; margin-bottom: 20px;"><i class="bi bi-inbox"></i></div>
                    <p style="color: #999; font-size: 16px;">Không có dữ liệu sản phẩm</p>
                </div>
            </c:if>
            <c:if test="${not empty dsSanPham}">
                <div style="overflow-x: auto;">
                    <table class="table table-hover mb-0">
                        <thead>
                            <tr>
                                <th style="width: 60px;">ID</th>
                                <th>Mã</th>
                                <th>Tên</th>
                                <th>Mô tả</th>
                                <th>Giá</th>
                                <th>Số lượng</th>
                                <th style="width: 150px; text-align: center;">Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="sp" items="${dsSanPham}">
                                <tr>
                                    <td><span class="badge bg-light text-dark">${sp.id}</span></td>
                                    <td><strong>${sp.ma}</strong></td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/san-pham?action=detail&id=${sp.id}" style="text-decoration: none; color: #0066cc; font-weight: 500;">
                                            ${sp.ten}
                                        </a>
                                    </td>
                                    <td>${sp.moTa}</td>
                                    <td><strong><fmt:formatNumber value="${sp.gia}" type="currency" currencySymbol="đ"/></strong></td>
                                    <td><span class="badge bg-secondary">${sp.soLuong}</span></td>
                                    <td style="text-align: center;">
                                        <div class="action-buttons">
                                            <a href="${pageContext.request.contextPath}/san-pham?action=edit&id=${sp.id}" class="action-btn-edit">
                                                <i class="bi bi-pencil-square"></i> Sửa
                                            </a>
                                            <span style="color: #ddd;">|</span>
                                            <a href="${pageContext.request.contextPath}/san-pham?action=delete&id=${sp.id}" class="action-btn-delete" onclick="return confirm('Bạn chắc chắn muốn xóa sản phẩm này?')">
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
