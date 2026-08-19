<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lab 7 - CRUD MVC</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body { background: #f8f9fa; }
        .navbar { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        .navbar-brand { font-weight: 700; font-size: 22px; }
        .hero { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 60px 20px; text-align: center; }
        .hero h1 { font-weight: 700; margin-bottom: 10px; font-size: 2.5rem; }
        .hero p { font-size: 18px; opacity: 0.9; margin-bottom: 0; }
        .modules-section { padding: 60px 20px; }
        .module-card { background: white; border: none; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.08); transition: all 0.3s ease; height: 100%; }
        .module-card:hover { transform: translateY(-5px); box-shadow: 0 8px 25px rgba(0,0,0,0.15); }
        .module-icon { font-size: 3rem; margin-bottom: 15px; }
        .module-card .card-body { padding: 25px; text-align: center; }
        .module-card h5 { font-weight: 600; color: #333; margin: 15px 0; font-size: 18px; }
        .module-card p { color: #666; font-size: 14px; margin: 10px 0; }
        .btn-module { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border: none; font-weight: 600; width: 100%; margin-top: 15px; }
        .btn-module:hover { background: linear-gradient(135deg, #764ba2 0%, #667eea 100%); }
        .user-section { padding: 15px 20px; background: white; text-align: right; }
        .user-section a { margin-left: 10px; }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">
            <i class="bi bi-diagram-3"></i> Lab 7 CRUD MVC
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <%
                        String username = (String) session.getAttribute("username");
                        if (username != null) {
                    %>
                        <span class="navbar-text" style="color: white; margin-right: 15px;">Xin chào, <strong><%= username %></strong></span>
                        <a class="nav-link" href="${pageContext.request.contextPath}/logout"><i class="bi bi-box-arrow-right"></i> Đăng xuất</a>
                    <%
                        } else {
                    %>
                        <a class="nav-link" href="${pageContext.request.contextPath}/login.jsp"><i class="bi bi-box-arrow-in-right"></i> Đăng nhập</a>
                    <%
                        }
                    %>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="hero">
    <div class="container">
        <h1>Lab 7 - CRUD MVC</h1>
        <p>Ứng dụng quản lý dữ liệu với Servlet + JSP</p>
    </div>
</div>

<div class="modules-section">
    <div class="container">
        <div class="row g-4">
            <div class="col-md-6 col-lg-4">
                <div class="module-card">
                    <div class="card-body">
                        <div class="module-icon text-primary"><i class="bi bi-person-badge"></i></div>
                        <h5>Quản lý Sinh viên</h5>
                        <p>Thêm, sửa, xóa và tìm kiếm thông tin sinh viên</p>
                        <a href="${pageContext.request.contextPath}/sinh-vien" class="btn btn-module btn-primary text-white">Mở module</a>
                    </div>
                </div>
            </div>

            <div class="col-md-6 col-lg-4">
                <div class="module-card">
                    <div class="card-body">
                        <div class="module-icon text-success"><i class="bi bi-book"></i></div>
                        <h5>Quản lý Sách</h5>
                        <p>Quản lý thư viện sách với các thông tin chi tiết</p>
                        <a href="${pageContext.request.contextPath}/sach" class="btn btn-module btn-success text-white">Mở module</a>
                    </div>
                </div>
            </div>

            <div class="col-md-6 col-lg-4">
                <div class="module-card">
                    <div class="card-body">
                        <div class="module-icon text-danger"><i class="bi bi-bag-check"></i></div>
                        <h5>Quản lý Sản phẩm</h5>
                        <p>Quản lý kho hàng với giá và số lượng</p>
                        <a href="${pageContext.request.contextPath}/san-pham" class="btn btn-module btn-danger text-white">Mở module</a>
                    </div>
                </div>
            </div>

            <div class="col-md-6 col-lg-4">
                <div class="module-card">
                    <div class="card-body">
                        <div class="module-icon text-warning"><i class="bi bi-building"></i></div>
                        <h5>Quản lý Lớp học</h5>
                        <p>Quản lý lớp học và tư vấn viên học tập</p>
                        <a href="${pageContext.request.contextPath}/lop-hoc" class="btn btn-module btn-warning text-white">Mở module</a>
                    </div>
                </div>
            </div>

            <div class="col-md-6 col-lg-4">
                <div class="module-card">
                    <div class="card-body">
                        <div class="module-icon text-info"><i class="bi bi-graph-up"></i></div>
                        <h5>Quản lý Điểm</h5>
                        <p>Quản lý điểm sinh viên với tính toán tự động</p>
                        <a href="${pageContext.request.contextPath}/diem-sinh-vien" class="btn btn-module btn-info text-white">Mở module</a>
                    </div>
                </div>
            </div>

            <div class="col-md-6 col-lg-4">
                <div class="module-card">
                    <div class="card-body">
                        <div class="module-icon text-secondary"><i class="bi bi-cart3"></i></div>
                        <h5>Giỏ hàng</h5>
                        <p>Quản lý giỏ hàng mua sắm qua session</p>
                        <a href="${pageContext.request.contextPath}/gio-hang" class="btn btn-module btn-secondary text-white">Mở module</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
