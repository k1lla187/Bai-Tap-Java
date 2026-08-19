<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi tiết Điểm sinh viên</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body { background: #f8f9fa; padding: 20px 0; }
        .detail-card { background: white; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.08); }
        .detail-header { background: linear-gradient(135deg, #17a2b8 0%, #20c997 100%); color: white; padding: 20px; border-radius: 12px 12px 0 0; }
        .detail-header h3 { margin: 0; font-weight: 600; }
        .detail-body { padding: 30px; }
        .detail-row { display: flex; align-items: center; padding: 12px 0; border-bottom: 1px solid #f0f0f0; }
        .detail-row:last-child { border-bottom: none; }
        .detail-label { font-weight: 600; width: 180px; color: #666; }
        .detail-value { flex: 1; color: #333; }
        .btn-edit { background: linear-gradient(135deg, #17a2b8 0%, #20c997 100%); border: none; }
        .btn-edit:hover { background: linear-gradient(135deg, #20c997 0%, #17a2b8 100%); }
        .btn-back { background: #6c757d; border: none; }
    </style>
</head>
<body style="background: #f8f9fa;">
<div class="container" style="margin-top: 30px; margin-bottom: 30px;">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="detail-card">
                <div class="detail-header">
                    <h3><i class="bi bi-graph-up"></i> Chi tiết Điểm</h3>
                </div>
                <div class="detail-body">
                    <div class="detail-row">
                        <div class="detail-label">ID:</div>
                        <div class="detail-value"><span class="badge bg-light text-dark">${diem.id}</span></div>
                    </div>
                    <div class="detail-row">
                        <div class="detail-label">Sinh viên ID:</div>
                        <div class="detail-value"><strong>${diem.sinhVienId}</strong></div>
                    </div>
                    <div class="detail-row">
                        <div class="detail-label">Điểm chuyên:</div>
                        <div class="detail-value">${diem.diemChuyen}</div>
                    </div>
                    <div class="detail-row">
                        <div class="detail-label">Điểm giữa kỳ:</div>
                        <div class="detail-value">${diem.diemGiuaKy}</div>
                    </div>
                    <div class="detail-row">
                        <div class="detail-label">Điểm cuối kỳ:</div>
                        <div class="detail-value">${diem.diemCuoiKy}</div>
                    </div>
                    <div class="detail-row">
                        <div class="detail-label">Tổng kết:</div>
                        <div class="detail-value"><strong>${String.format("%.2f", diem.diemTongKet)}</strong></div>
                    </div>
                    <div class="detail-row">
                        <div class="detail-label">Xếp loại:</div>
                        <div class="detail-value">
                            <c:choose>
                                <c:when test="${diem.xepLoai == 'A'}">
                                    <span class="badge" style="background-color: #d4edda; color: #155724; font-size: 13px; padding: 6px 12px;">A - Xuất sắc</span>
                                </c:when>
                                <c:when test="${diem.xepLoai == 'B'}">
                                    <span class="badge" style="background-color: #cfe2ff; color: #084298; font-size: 13px; padding: 6px 12px;">B - Khá</span>
                                </c:when>
                                <c:when test="${diem.xepLoai == 'C'}">
                                    <span class="badge" style="background-color: #fff3cd; color: #664d03; font-size: 13px; padding: 6px 12px;">C - Trung bình</span>
                                </c:when>
                                <c:when test="${diem.xepLoai == 'D'}">
                                    <span class="badge" style="background-color: #f8d7da; color: #842029; font-size: 13px; padding: 6px 12px;">D - Yếu</span>
                                </c:when>
                                <c:when test="${diem.xepLoai == 'F'}">
                                    <span class="badge" style="background-color: #e2e3e5; color: #383d41; font-size: 13px; padding: 6px 12px;">F - Không đạt</span>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>
                    
                    <div style="margin-top: 25px;">
                        <div class="d-grid gap-2 d-sm-flex">
                            <a href="${pageContext.request.contextPath}/diem-sinh-vien?action=edit&id=${diem.id}" class="btn btn-edit btn-sm text-white flex-sm-fill"><i class="bi bi-pencil-square"></i> Sửa</a>
                            <a href="${pageContext.request.contextPath}/diem-sinh-vien" class="btn btn-back btn-sm text-white flex-sm-fill"><i class="bi bi-arrow-left"></i> Quay lại</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
</html>
