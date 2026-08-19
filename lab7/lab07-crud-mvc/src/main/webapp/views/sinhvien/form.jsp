<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Form Sinh viên</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body { background: #f8f9fa; padding: 20px 0; }
        .form-card { background: white; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.08); }
        .form-header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 20px; border-radius: 12px 12px 0 0; }
        .form-header h3 { margin: 0; font-weight: 600; }
        .form-body { padding: 30px; }
        .form-label { font-weight: 500; color: #333; margin-bottom: 8px; }
        .form-control { border-radius: 8px; border: 1px solid #e0e0e0; padding: 10px 15px; }
        .form-control:focus { border-color: #667eea; box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25); }
        .btn-submit { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border: none; font-weight: 600; }
        .btn-submit:hover { background: linear-gradient(135deg, #764ba2 0%, #667eea 100%); }
        .btn-cancel { background: #6c757d; border: none; }
        .btn-cancel:hover { background: #5a6268; }
    </style>
</head>
<body style="background: #f8f9fa;">
<div class="container" style="margin-top: 30px; margin-bottom: 30px;">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="form-card">
                <div class="form-header">
                    <h3><i class="bi bi-person-badge"></i> <c:if test="${empty sv.id}">Thêm</c:if><c:if test="${not empty sv.id}">Cập nhật</c:if> Sinh viên</h3>
                </div>
                <div class="form-body">
                    <form method="post" action="${pageContext.request.contextPath}/sinh-vien">
                        <input type="hidden" name="id" value="${sv.id}">
                        
                        <div class="mb-3">
                            <label class="form-label">Mã Sinh viên</label>
                            <input type="text" class="form-control" name="maSinhVien" value="${sv.maSinhVien}" required>
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label">Họ tên</label>
                            <input type="text" class="form-control" name="hoTen" value="${sv.hoTen}" required>
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" class="form-control" name="email" value="${sv.email}">
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label">Lớp</label>
                            <input type="text" class="form-control" name="lop" value="${sv.lop}">
                        </div>
                        
                        <div class="d-grid gap-2 d-sm-flex">
                            <button type="submit" class="btn btn-submit text-white flex-sm-fill"><i class="bi bi-save"></i> Lưu</button>
                            <button type="button" class="btn btn-cancel text-white" onclick="window.location.href='${pageContext.request.contextPath}/sinh-vien'"><i class="bi bi-x-circle"></i> Hủy</button>
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
