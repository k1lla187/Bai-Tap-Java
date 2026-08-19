<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Form Lớp học</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body { background: #f8f9fa; padding: 20px 0; }
        .form-card { background: white; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.08); }
        .form-header { background: linear-gradient(135deg, #ffc107 0%, #ff8c00 100%); color: #333; padding: 20px; border-radius: 12px 12px 0 0; }
        .form-header h3 { margin: 0; font-weight: 600; }
        .form-body { padding: 30px; }
        .form-label { font-weight: 500; color: #333; margin-bottom: 8px; }
        .form-control { border-radius: 8px; border: 1px solid #e0e0e0; padding: 10px 15px; }
        .form-control:focus { border-color: #ffc107; box-shadow: 0 0 0 0.2rem rgba(255, 193, 7, 0.25); }
        .btn-submit { background: linear-gradient(135deg, #ffc107 0%, #ff8c00 100%); border: none; font-weight: 600; color: #333; }
        .btn-submit:hover { background: linear-gradient(135deg, #ff8c00 0%, #ffc107 100%); }
        .btn-cancel { background: #6c757d; border: none; color: white; }
    </style>
</head>
<body style="background: #f8f9fa;">
<div class="container" style="margin-top: 30px; margin-bottom: 30px;">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="form-card">
                <div class="form-header">
                    <h3><i class="bi bi-building"></i> <c:if test="${empty lop.id}">Thêm</c:if><c:if test="${not empty lop.id}">Cập nhật</c:if> Lớp học</h3>
                </div>
                <div class="form-body">
                    <form method="post" action="${pageContext.request.contextPath}/lop-hoc">
                        <input type="hidden" name="id" value="${lop.id}">
                        
                        <div class="mb-3">
                            <label class="form-label">Mã lớp</label>
                            <input type="text" class="form-control" name="maLop" value="${lop.maLop}" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Tên lớp</label>
                            <input type="text" class="form-control" name="tenLop" value="${lop.tenLop}" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Cố vấn học tập</label>
                            <input type="text" class="form-control" name="coVanHocTap" value="${lop.coVanHocTap}" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Số lượng sinh viên</label>
                            <input type="number" class="form-control" name="soLuongSinhVien" value="${lop.soLuongSinhVien}" required>
                        </div>
                        
                        <div class="d-grid gap-2 d-sm-flex">
                            <button type="submit" class="btn btn-submit flex-sm-fill"><i class="bi bi-save"></i> Lưu</button>
                            <button type="button" class="btn btn-cancel" onclick="window.location.href='${pageContext.request.contextPath}/lop-hoc'"><i class="bi bi-x-circle"></i> Hủy</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
