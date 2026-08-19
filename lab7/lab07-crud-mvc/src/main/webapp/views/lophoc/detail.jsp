<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi tiết Lớp học</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body { background: #f8f9fa; padding: 20px 0; }
        .detail-card { background: white; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.08); }
        .detail-header { background: linear-gradient(135deg, #ffc107 0%, #ff8c00 100%); color: #333; padding: 20px; border-radius: 12px 12px 0 0; }
        .detail-header h3 { margin: 0; font-weight: 600; }
        .detail-body { padding: 30px; }
        .detail-row { display: flex; align-items: center; padding: 12px 0; border-bottom: 1px solid #f0f0f0; }
        .detail-row:last-child { border-bottom: none; }
        .detail-label { font-weight: 600; width: 180px; color: #666; }
        .detail-value { flex: 1; color: #333; }
        .btn-edit { background: linear-gradient(135deg, #ffc107 0%, #ff8c00 100%); border: none; color: #333; }
        .btn-edit:hover { background: linear-gradient(135deg, #ff8c00 0%, #ffc107 100%); }
        .btn-back { background: #6c757d; border: none; }
    </style>
</head>
<body style="background: #f8f9fa;">
<div class="container" style="margin-top: 30px; margin-bottom: 30px;">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="detail-card">
                <div class="detail-header">
                    <h3><i class="bi bi-building"></i> Chi tiết Lớp học</h3>
                </div>
                <div class="detail-body">
                    <div class="detail-row">
                        <div class="detail-label">ID:</div>
                        <div class="detail-value"><span class="badge bg-light text-dark">${lop.id}</span></div>
                    </div>
                    <div class="detail-row">
                        <div class="detail-label">Mã lớp:</div>
                        <div class="detail-value"><strong>${lop.maLop}</strong></div>
                    </div>
                    <div class="detail-row">
                        <div class="detail-label">Tên lớp:</div>
                        <div class="detail-value">${lop.tenLop}</div>
                    </div>
                    <div class="detail-row">
                        <div class="detail-label">Cố vấn học tập:</div>
                        <div class="detail-value">${lop.coVanHocTap}</div>
                    </div>
                    <div class="detail-row">
                        <div class="detail-label">Số lượng SV:</div>
                        <div class="detail-value"><span class="badge bg-secondary">${lop.soLuongSinhVien}</span></div>
                    </div>
                    
                    <div style="margin-top: 25px;">
                        <div class="d-grid gap-2 d-sm-flex">
                            <a href="${pageContext.request.contextPath}/lop-hoc?action=edit&id=${lop.id}" class="btn btn-edit btn-sm flex-sm-fill"><i class="bi bi-pencil-square"></i> Sửa</a>
                            <a href="${pageContext.request.contextPath}/lop-hoc" class="btn btn-back btn-sm text-white flex-sm-fill"><i class="bi bi-arrow-left"></i> Quay lại</a>
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
    <hr>
    <div>
        <a href="${pageContext.request.contextPath}/lop-hoc?action=edit&id=${lop.id}">✏️ Sửa</a> |
        <a href="${pageContext.request.contextPath}/lop-hoc">← Quay lại</a>
    </div>
</div>
</body>
</html>
