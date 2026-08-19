<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Form Sản phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body { background: #f8f9fa; padding: 20px 0; }
        .form-card { background: white; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.08); }
        .form-header { background: linear-gradient(135deg, #dc3545 0%, #c82333 100%); color: white; padding: 20px; border-radius: 12px 12px 0 0; }
        .form-header h3 { margin: 0; font-weight: 600; }
        .form-body { padding: 30px; }
        .form-label { font-weight: 500; color: #333; margin-bottom: 8px; }
        .form-control { border-radius: 8px; border: 1px solid #e0e0e0; padding: 10px 15px; }
        .form-control:focus { border-color: #dc3545; box-shadow: 0 0 0 0.2rem rgba(220, 53, 69, 0.25); }
        .btn-submit { background: linear-gradient(135deg, #dc3545 0%, #c82333 100%); border: none; font-weight: 600; }
        .btn-submit:hover { background: linear-gradient(135deg, #c82333 0%, #dc3545 100%); }
        .btn-cancel { background: #6c757d; border: none; }
    </style>
</head>
<body style="background: #f8f9fa;">
<div class="container" style="margin-top: 30px; margin-bottom: 30px;">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="form-card">
                <div class="form-header">
                    <h3><i class="bi bi-bag-check"></i> <c:if test="${empty sp.id}">Thêm</c:if><c:if test="${not empty sp.id}">Cập nhật</c:if> Sản phẩm</h3>
                </div>
                <div class="form-body">
                    <% if (request.getAttribute("error") != null) { %>
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <strong>Lỗi!</strong> <%= request.getAttribute("error") %>
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    <% } %>
                    
                    <form method="post" action="${pageContext.request.contextPath}/san-pham">
                        <input type="hidden" name="id" value="${sp.id}">
                        
                        <div class="mb-3">
                            <label class="form-label">Mã</label>
                            <input type="text" class="form-control" name="ma" value="${sp.ma}" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Tên</label>
                            <input type="text" class="form-control" name="ten" value="${sp.ten}" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Mô tả</label>
                            <input type="text" class="form-control" name="moTa" value="${sp.moTa}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Giá <small class="text-muted">(phải &gt; 0)</small></label>
                            <input type="number" class="form-control" name="gia" value="${sp.gia}" step="0.01" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Số lượng <small class="text-muted">(phải ≥ 0)</small></label>
                            <input type="number" class="form-control" name="soLuong" value="${sp.soLuong}" required>
                        </div>
                        
                        <div class="d-grid gap-2 d-sm-flex">
                            <button type="submit" class="btn btn-submit text-white flex-sm-fill"><i class="bi bi-save"></i> Lưu</button>
                            <button type="button" class="btn btn-cancel text-white" onclick="window.location.href='${pageContext.request.contextPath}/san-pham'"><i class="bi bi-x-circle"></i> Hủy</button>
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
