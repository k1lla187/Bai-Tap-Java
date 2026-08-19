<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách Điểm sinh viên</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body { background: #f8f9fa; padding: 20px 0; }
        .container { margin-top: 30px; }
        .table-card { background: white; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.08); }
        .table-header-title { padding: 20px; border-bottom: 2px solid #f0f0f0; }
        .table-header-title h3 { margin: 0; color: #333; font-weight: 600; }
        .btn-add { background: linear-gradient(135deg, #17a2b8 0%, #20c997 100%); border: none; }
        .btn-add:hover { background: linear-gradient(135deg, #20c997 0%, #17a2b8 100%); }
        .table th { background: #f8f9fa; font-weight: 600; color: #333; border-bottom: 2px solid #e0e0e0; }
        .table tbody tr { border-bottom: 1px solid #e0e0e0; }
        .table tbody tr:hover { background: #f8f9fa; }
        .grade-A { background-color: #d4edda !important; color: #155724; }
        .grade-B { background-color: #cfe2ff !important; color: #084298; }
        .grade-C { background-color: #fff3cd !important; color: #664d03; }
        .grade-D { background-color: #f8d7da !important; color: #842029; }
        .grade-F { background-color: #e2e3e5 !important; color: #383d41; }
        .badge-grade { font-weight: 600; padding: 6px 12px; font-size: 13px; }
        .action-buttons a { margin: 0 3px; font-size: 14px; }
        .pagination-container { display: flex; gap: 5px; margin-top: 20px; flex-wrap: wrap; }
        .pagination-container a, .pagination-container span { padding: 6px 12px; border-radius: 4px; text-decoration: none; }
        .pagination-container a { background: #e9ecef; color: #0066cc; border: 1px solid #dee2e6; }
        .pagination-container a:hover { background: #dee2e6; }
        .pagination-container span.active { background: linear-gradient(135deg, #17a2b8 0%, #20c997 100%); color: white; border: 1px solid #17a2b8; }
    </style>
</head>
<body style="background: #f8f9fa;">
<div class="container">
    <!-- Breadcrumb -->
    <div style="margin-bottom: 20px;">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb" style="background: transparent; margin: 0;">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/" style="text-decoration: none;"><i class="bi bi-house"></i> Trang chủ</a></li>
                <li class="breadcrumb-item active">Quản lý Điểm sinh viên</li>
            </ol>
        </nav>
    </div>

    <!-- Data Table -->
    <div class="table-card">
        <div class="table-header-title d-flex justify-content-between align-items-center">
            <h3><i class="bi bi-graph-up"></i> Danh sách Điểm sinh viên <small style="font-size: 14px; font-weight: normal; color: #666;">(5 dòng/trang)</small></h3>
            <a href="${pageContext.request.contextPath}/diem-sinh-vien?action=new" class="btn btn-add btn-sm text-white">
                <i class="bi bi-plus-circle"></i> Thêm mới
            </a>
        </div>

        <div style="padding: 20px;">
            <c:if test="${empty dsDiem}">
                <div style="text-align: center; padding: 60px 20px;">
                    <div style="font-size: 4rem; color: #ccc; margin-bottom: 20px;"><i class="bi bi-inbox"></i></div>
                    <p style="color: #999; font-size: 16px;">Không có dữ liệu điểm</p>
                </div>
            </c:if>
            <c:if test="${not empty dsDiem}">
                <div style="overflow-x: auto;">
                    <table class="table table-hover mb-0">
                        <thead>
                            <tr>
                                <th style="width: 60px;">ID</th>
                                <th>SV ID</th>
                                <th>Điểm chuyên</th>
                                <th>Điểm giữa kỳ</th>
                                <th>Điểm cuối kỳ</th>
                                <th>Tổng kết</th>
                                <th>Xếp loại</th>
                                <th style="width: 120px; text-align: center;">Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="diem" items="${dsDiem}">
                                <tr class="grade-${diem.xepLoai}">
                                    <td><span class="badge bg-light text-dark">${diem.id}</span></td>
                                    <td><strong>${diem.sinhVienId}</strong></td>
                                    <td>${diem.diemChuyen}</td>
                                    <td>${diem.diemGiuaKy}</td>
                                    <td>${diem.diemCuoiKy}</td>
                                    <td><strong>${String.format("%.2f", diem.diemTongKet)}</strong></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${diem.xepLoai == 'A'}">
                                                <span class="badge badge-grade" style="background-color: #d4edda; color: #155724;">A - Xuất sắc</span>
                                            </c:when>
                                            <c:when test="${diem.xepLoai == 'B'}">
                                                <span class="badge badge-grade" style="background-color: #cfe2ff; color: #084298;">B - Khá</span>
                                            </c:when>
                                            <c:when test="${diem.xepLoai == 'C'}">
                                                <span class="badge badge-grade" style="background-color: #fff3cd; color: #664d03;">C - Trung bình</span>
                                            </c:when>
                                            <c:when test="${diem.xepLoai == 'D'}">
                                                <span class="badge badge-grade" style="background-color: #f8d7da; color: #842029;">D - Yếu</span>
                                            </c:when>
                                            <c:when test="${diem.xepLoai == 'F'}">
                                                <span class="badge badge-grade" style="background-color: #e2e3e5; color: #383d41;">F - Không đạt</span>
                                            </c:when>
                                        </c:choose>
                                    </td>
                                    <td style="text-align: center;">
                                        <div class="action-buttons">
                                            <a href="${pageContext.request.contextPath}/diem-sinh-vien?action=edit&id=${diem.id}" style="color: #0066cc; text-decoration: none; font-size: 13px;">
                                                <i class="bi bi-pencil-square"></i> Sửa
                                            </a>
                                            <span style="color: #ddd;">|</span>
                                            <a href="${pageContext.request.contextPath}/diem-sinh-vien?action=delete&id=${diem.id}" style="color: #dc3545; text-decoration: none; font-size: 13px;" onclick="return confirm('Bạn chắc chắn muốn xóa?')">
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

        <!-- Pagination -->
        <div style="padding: 20px; border-top: 1px solid #f0f0f0; text-align: center;">
            <div class="pagination-container" style="justify-content: center;">
                <% 
                    Integer currentPage = (Integer) request.getAttribute("currentPage");
                    Integer totalPages = (Integer) request.getAttribute("totalPages");
                    if (currentPage != null && totalPages != null) {
                        for (int i = 1; i <= totalPages; i++) {
                            if (i == currentPage) {
                %>
                    <span class="active"><%= i %></span>
                <% 
                            } else {
                %>
                    <a href="${pageContext.request.contextPath}/diem-sinh-vien?page=<%= i %>"><%= i %></a>
                <% 
                            }
                        }
                    }
                %>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
