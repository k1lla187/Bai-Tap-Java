<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Form Điểm sinh viên</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body { background: #f8f9fa; padding: 20px 0; }
        .form-card { background: white; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.08); }
        .form-header { background: linear-gradient(135deg, #17a2b8 0%, #20c997 100%); color: white; padding: 20px; border-radius: 12px 12px 0 0; }
        .form-header h3 { margin: 0; font-weight: 600; }
        .form-body { padding: 30px; }
        .form-label { font-weight: 500; color: #333; margin-bottom: 8px; }
        .form-control, .form-select { border-radius: 8px; border: 1px solid #e0e0e0; padding: 10px 15px; }
        .form-control:focus, .form-select:focus { border-color: #17a2b8; box-shadow: 0 0 0 0.2rem rgba(23, 162, 184, 0.25); }
        .btn-submit { background: linear-gradient(135deg, #17a2b8 0%, #20c997 100%); border: none; font-weight: 600; }
        .btn-submit:hover { background: linear-gradient(135deg, #20c997 0%, #17a2b8 100%); }
        .btn-cancel { background: #6c757d; border: none; }
    </style>
</head>
<body style="background: #f8f9fa;">
<div class="container" style="margin-top: 30px; margin-bottom: 30px;">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="form-card">
                <div class="form-header">
                    <h3><i class="bi bi-graph-up"></i> <c:if test="${empty diem.id}">Thêm</c:if><c:if test="${not empty diem.id}">Cập nhật</c:if> Điểm</h3>
                </div>
                <div class="form-body">
                    <form method="post" action="${pageContext.request.contextPath}/diem-sinh-vien">
                        <input type="hidden" name="id" value="${diem.id}">
                        
                        <div class="mb-3">
                            <label class="form-label">Sinh viên</label>
                            <select class="form-select" name="sinhVienId" required>
                                <option value="">-- Chọn sinh viên --</option>
                                <c:forEach var="sv" items="${dsSinhVien}">
                                    <option value="${sv.id}" <c:if test="${sv.id == diem.sinhVienId}">selected</c:if>>
                                        ${sv.maSinhVien} - ${sv.hoTen}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Điểm chuyên cần</label>
                            <input type="number" class="form-control" name="diemChuyen" value="${diem.diemChuyen}" step="0.1" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Điểm giữa kỳ</label>
                            <input type="number" class="form-control" name="diemGiuaKy" value="${diem.diemGiuaKy}" step="0.1" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Điểm cuối kỳ</label>
                            <input type="number" class="form-control" name="diemCuoiKy" value="${diem.diemCuoiKy}" step="0.1" required>
                        </div>
                        
                        <div class="d-grid gap-2 d-sm-flex">
                            <button type="submit" class="btn btn-submit text-white flex-sm-fill"><i class="bi bi-save"></i> Lưu</button>
                            <button type="button" class="btn btn-cancel text-white" onclick="window.location.href='${pageContext.request.contextPath}/diem-sinh-vien'"><i class="bi bi-x-circle"></i> Hủy</button>
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
