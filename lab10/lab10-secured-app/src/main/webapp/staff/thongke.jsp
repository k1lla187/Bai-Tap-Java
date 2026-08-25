<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thong ke Bao cao</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f4f6f9; }
        .navbar { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); padding: 0 40px; display: flex; align-items: center; justify-content: space-between; }
        .navbar-brand { color: white; font-size: 22px; font-weight: 700; padding: 18px 0; }
        .navbar-right { display: flex; align-items: center; gap: 16px; color: white; }
        .navbar-right a { color: white; text-decoration: none; font-size: 14px; padding: 6px 14px; border: 1px solid rgba(255,255,255,0.4); border-radius: 6px; }
        .container { max-width: 1200px; margin: 30px auto; padding: 0 20px; }
        h1 { color: #333; font-size: 26px; margin-bottom: 24px; }
        .section-title { color: #555; font-size: 18px; font-weight: 600; margin: 30px 0 16px; padding-bottom: 8px; border-bottom: 2px solid #667eea; }
        .stats-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 20px; margin-bottom: 30px; }
        .stat-card { background: white; padding: 24px; border-radius: 12px; box-shadow: 0 2px 8px rgba(0,0,0,0.06); text-align: center; }
        .stat-card .number { font-size: 36px; font-weight: 700; color: #667eea; }
        .stat-card .label { font-size: 14px; color: #777; margin-top: 8px; }
        .chart-container { display: grid; grid-template-columns: 1fr 1fr; gap: 24px; margin-top: 24px; }
        .chart-card { background: white; padding: 24px; border-radius: 12px; box-shadow: 0 2px 8px rgba(0,0,0,0.06); }
        .chart-card h3 { color: #333; margin-bottom: 20px; font-size: 16px; }
        .bar-chart { display: flex; flex-direction: column; gap: 12px; }
        .bar-row { display: flex; align-items: center; gap: 12px; }
        .bar-label { width: 100px; font-size: 13px; color: #555; }
        .bar-container { flex: 1; height: 24px; background: #f0f0f0; border-radius: 12px; overflow: hidden; }
        .bar-fill { height: 100%; border-radius: 12px; display: flex; align-items: center; padding-left: 10px; font-size: 12px; color: white; font-weight: 600; min-width: 40px; }
        .bar-value { width: 50px; text-align: right; font-size: 13px; color: #555; font-weight: 600; }
        .bar-gioi { background: linear-gradient(90deg, #43a047, #66bb6a); }
        .bar-kha { background: linear-gradient(90deg, #1976d2, #42a5f5); }
        .bar-tb { background: linear-gradient(90deg, #f57c00, #ffa726); }
        .bar-yeu { background: linear-gradient(90deg, #7b1fa2, #ba68c8); }
        .bar-kem { background: linear-gradient(90deg, #c62828, #ef5350); }
        .bar-comat { background: linear-gradient(90deg, #2e7d32, #4caf50); }
        .bar-vang { background: linear-gradient(90deg, #c62828, #ef5350); }
        .bar-muon { background: linear-gradient(90deg, #e65100, #ff9800); }
        .bar-phep { background: linear-gradient(90deg, #1565c0, #42a5f5); }
    </style>
</head>
<body>
    <nav class="navbar">
        <div class="navbar-brand">Thong ke Bao cao</div>
        <div class="navbar-right">
            <span>${sessionScope.userName} (${sessionScope.userRole})</span>
            <a href="${pageContext.request.contextPath}/dashboard.jsp">Trang chu</a>
            <a href="${pageContext.request.contextPath}/staff/index.jsp">Module</a>
            <a href="${pageContext.request.contextPath}/auth?action=logout">Dang xuat</a>
        </div>
    </nav>
    <div class="container">
        <h1>Thong ke Tong quan</h1>
        <div class="stats-grid">
            <div class="stat-card">
                <div class="number">${thongKe.tongSinhVien}</div>
                <div class="label">Tong Sinh Vien</div>
            </div>
            <div class="stat-card">
                <div class="number">${thongKe.tongMonHoc}</div>
                <div class="label">Tong Mon Hoc</div>
            </div>
            <div class="stat-card">
                <div class="number">${thongKe.tongDiem}</div>
                <div class="label">Tong Diem</div>
            </div>
            <div class="stat-card">
                <div class="number">${thongKe.tongGiaoVien}</div>
                <div class="label">Tong Giao Vien</div>
            </div>
            <div class="stat-card">
                <div class="number">${thongKe.tongLop}</div>
                <div class="label">Tong Lop</div>
            </div>
            <div class="stat-card">
                <div class="number">${thongKe.tongDiemDanh}</div>
                <div class="label">Tong Diem Danh</div>
            </div>
        </div>

        <h2 class="section-title">Thong ke Xep loai Diem</h2>
        <div class="chart-container">
            <div class="chart-card">
                <h3>Xep loai hoc luc</h3>
                <div class="bar-chart">
                    <div class="bar-row">
                        <div class="bar-label">Gioi</div>
                        <div class="bar-container"><div class="bar-fill bar-gioi" style="width: ${thongKe.tongDiem > 0 ? (thongKe.diemGioi * 100 / thongKe.tongDiem) : 0}%;">${thongKe.diemGioi}</div></div>
                    </div>
                    <div class="bar-row">
                        <div class="bar-label">Kha</div>
                        <div class="bar-container"><div class="bar-fill bar-kha" style="width: ${thongKe.tongDiem > 0 ? (thongKe.diemKha * 100 / thongKe.tongDiem) : 0}%;">${thongKe.diemKha}</div></div>
                    </div>
                    <div class="bar-row">
                        <div class="bar-label">Trung binh</div>
                        <div class="bar-container"><div class="bar-fill bar-tb" style="width: ${thongKe.tongDiem > 0 ? (thongKe.diemTrungBinh * 100 / thongKe.tongDiem) : 0}%;">${thongKe.diemTrungBinh}</div></div>
                    </div>
                    <div class="bar-row">
                        <div class="bar-label">Yeu</div>
                        <div class="bar-container"><div class="bar-fill bar-yeu" style="width: ${thongKe.tongDiem > 0 ? (thongKe.diemYeu * 100 / thongKe.tongDiem) : 0}%;">${thongKe.diemYeu}</div></div>
                    </div>
                    <div class="bar-row">
                        <div class="bar-label">Kem</div>
                        <div class="bar-container"><div class="bar-fill bar-kem" style="width: ${thongKe.tongDiem > 0 ? (thongKe.diemKem * 100 / thongKe.tongDiem) : 0}%;">${thongKe.diemKem}</div></div>
                    </div>
                </div>
            </div>

            <div class="chart-card">
                <h3>Thong ke Diem Danh</h3>
                <div class="bar-chart">
                    <div class="bar-row">
                        <div class="bar-label">Co mat</div>
                        <div class="bar-container"><div class="bar-fill bar-comat" style="width: ${thongKe.tongDiemDanh > 0 ? (thongKe.ddCoMat * 100 / thongKe.tongDiemDanh) : 0}%;">${thongKe.ddCoMat}</div></div>
                    </div>
                    <div class="bar-row">
                        <div class="bar-label">Vang</div>
                        <div class="bar-container"><div class="bar-fill bar-vang" style="width: ${thongKe.tongDiemDanh > 0 ? (thongKe.ddVang * 100 / thongKe.tongDiemDanh) : 0}%;">${thongKe.ddVang}</div></div>
                    </div>
                    <div class="bar-row">
                        <div class="bar-label">Di muon</div>
                        <div class="bar-container"><div class="bar-fill bar-muon" style="width: ${thongKe.tongDiemDanh > 0 ? (thongKe.ddDiMuon * 100 / thongKe.tongDiemDanh) : 0}%;">${thongKe.ddDiMuon}</div></div>
                    </div>
                    <div class="bar-row">
                        <div class="bar-label">Co phep</div>
                        <div class="bar-container"><div class="bar-fill bar-phep" style="width: ${thongKe.tongDiemDanh > 0 ? (thongKe.ddCoPhep * 100 / thongKe.tongDiemDanh) : 0}%;">${thongKe.ddCoPhep}</div></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
