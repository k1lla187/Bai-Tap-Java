<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Module Nghiep vu - Staff</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f4f6f9; }
        .navbar { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); padding: 0 40px; display: flex; align-items: center; justify-content: space-between; box-shadow: 0 2px 10px rgba(0,0,0,0.15); }
        .navbar-brand { color: white; font-size: 22px; font-weight: 700; padding: 18px 0; }
        .navbar-right { display: flex; align-items: center; gap: 20px; color: white; }
        .navbar-right .user-info { font-size: 14px; }
        .navbar-right .role-badge { background: rgba(255,255,255,0.25); padding: 4px 12px; border-radius: 20px; font-size: 12px; font-weight: 600; }
        .navbar-right a { color: white; text-decoration: none; font-size: 14px; padding: 6px 16px; border: 1px solid rgba(255,255,255,0.4); border-radius: 6px; }
        .container { max-width: 1200px; margin: 40px auto; padding: 0 20px; }
        h1 { color: #333; margin-bottom: 8px; font-size: 28px; }
        .subtitle { color: #777; margin-bottom: 30px; }
        .grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(260px, 1fr)); gap: 20px; }
        .card { background: white; border-radius: 12px; padding: 28px; box-shadow: 0 2px 8px rgba(0,0,0,0.08); transition: transform 0.2s, box-shadow 0.2s; }
        .card:hover { transform: translateY(-4px); box-shadow: 0 8px 24px rgba(0,0,0,0.12); }
        .card h3 { color: #333; margin-bottom: 10px; font-size: 18px; }
        .card p { color: #777; font-size: 14px; margin-bottom: 16px; line-height: 1.6; }
        .card-icon { font-size: 42px; margin-bottom: 12px; }
        .btn-module { display: inline-block; padding: 8px 18px; background: #43a047; color: white; text-decoration: none; border-radius: 6px; font-size: 14px; font-weight: 600; }
        .btn-module:hover { background: #388e3c; }
        .info-box { background: #e8f5e9; border: 1px solid #c8e6c9; border-radius: 12px; padding: 20px; margin-top: 30px; color: #2e7d32; }
        .info-box strong { font-size: 16px; }
    </style>
</head>
<body>
    <nav class="navbar">
        <div class="navbar-brand">Lab 10 - Module Nghiep vu</div>
        <div class="navbar-right">
            <span class="user-info">${sessionScope.userName}</span>
            <span class="role-badge">${sessionScope.userRole}</span>
            <a href="${pageContext.request.contextPath}/dashboard.jsp">Trang chu</a>
            <a href="${pageContext.request.contextPath}/auth?action=logout">Dang xuat</a>
        </div>
    </nav>

    <div class="container">
        <h1>Module Nghiep vu</h1>
        <p class="subtitle">Chao mung ban, ${sessionScope.userName}! Ban co vai tro: ${sessionScope.userRole}</p>

        <div class="grid">
            <div class="card">
                <div class="card-icon">&#128100;</div>
                <h3>Quan ly Sinh Vien</h3>
                <p>Xem, them, sua danh sach sinh vien. ADMIN co the xoa, STAFF co the them/sua.</p>
                <a href="${pageContext.request.contextPath}/staff/sinhvien" class="btn-module">Truy cap</a>
            </div>

            <div class="card">
                <div class="card-icon">&#128218;</div>
                <h3>Quan ly Mon Hoc</h3>
                <p>Quan ly danh sach mon hoc va thong tin so tin chi.</p>
                <a href="${pageContext.request.contextPath}/staff/monhoc" class="btn-module">Truy cap</a>
            </div>

            <div class="card">
                <div class="card-icon">&#9742;</div>
                <h3>Quan ly Diem</h3>
                <p>Nhap diem giua ky, cuoi ky va tinh diem tong ket.</p>
                <a href="${pageContext.request.contextPath}/staff/diem" class="btn-module">Truy cap</a>
            </div>

            <div class="card">
                <div class="card-icon">&#128104;</div>
                <h3>Quan ly Giao Vien</h3>
                <p>Quan ly danh sach giao vien, thong tin chuyen nganh va lien he.</p>
                <a href="${pageContext.request.contextPath}/staff/giaovien" class="btn-module" style="background:#e65100;">Truy cap</a>
            </div>

            <div class="card">
                <div class="card-icon">&#127973;</div>
                <h3>Quan ly Lop</h3>
                <p>Quan ly danh sach lop hoc, phan cong giao vien chu nhiem.</p>
                <a href="${pageContext.request.contextPath}/staff/lop" class="btn-module" style="background:#7b1fa2;">Truy cap</a>
            </div>

            <div class="card">
                <div class="card-icon">&#9989;</div>
                <h3>Diem Danh</h3>
                <p>Diem danh sinh vien theo buoi hoc, theo doi trang thai di hoc.</p>
                <a href="${pageContext.request.contextPath}/staff/diemdanh" class="btn-module" style="background:#0288d1;">Truy cap</a>
            </div>

            <div class="card" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white;">
                <div class="card-icon">&#128202;</div>
                <h3 style="color: white;">Thong ke Bao cao</h3>
                <p style="color: rgba(255,255,255,0.9);">Xem thong ke tong quan, xep loai hoc luc va ti le diem danh.</p>
                <a href="${pageContext.request.contextPath}/staff/thongke" class="btn-module" style="background: white; color: #667eea;">Truy cap</a>
            </div>
        </div>

        <div class="info-box">
            <strong>Huong dan quyen truy cap:</strong><br><br>
            <strong>ADMIN:</strong> Toan quyen - xem, them, sua, xoa tat ca du lieu.<br>
            <strong>STAFF:</strong> Xem va them/sua du lieu nghiep vu. Khong the xoa hoac quan ly tai khoan.<br>
            <strong>USER:</strong> Chi xem va cap nhat ho so ca nhan.
        </div>
    </div>
</body>
</html>
