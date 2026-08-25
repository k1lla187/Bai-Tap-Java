<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${empty diemDanh ? 'Them' : 'Sua'} Diem Danh</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f4f6f9; min-height: 100vh; display: flex; align-items: center; justify-content: center; }
        .form-card { background: white; padding: 40px; border-radius: 16px; box-shadow: 0 8px 30px rgba(0,0,0,0.1); width: 500px; max-width: 95%; }
        h2 { color: #333; margin-bottom: 24px; }
        .form-group { margin-bottom: 16px; }
        .form-group label { display: block; margin-bottom: 6px; color: #555; font-weight: 500; font-size: 14px; }
        .form-group input, .form-group select { width: 100%; padding: 11px 14px; border: 2px solid #e0e0e0; border-radius: 8px; font-size: 14px; }
        .form-group input:focus, .form-group select:focus { outline: none; border-color: #0288d1; }
        .btn-row { display: flex; gap: 12px; margin-top: 24px; }
        .btn { padding: 12px 24px; border: none; border-radius: 8px; cursor: pointer; font-size: 15px; font-weight: 600; text-decoration: none; display: inline-block; }
        .btn-primary { background: #0288d1; color: white; }
        .btn-secondary { background: #6c757d; color: white; }
        .error { background: #ffe6e6; color: #dc3545; padding: 12px 16px; border-radius: 8px; margin-bottom: 16px; font-size: 14px; }
        .top-bar { position: fixed; top: 0; left: 0; right: 0; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); padding: 12px 40px; display: flex; align-items: center; justify-content: space-between; }
        .top-bar .brand { color: white; font-weight: 700; font-size: 18px; }
        .top-bar a { color: rgba(255,255,255,0.8); text-decoration: none; font-size: 14px; }
        .wrapper { padding-top: 60px; width: 100%; display: flex; justify-content: center; }
    </style>
</head>
<body>
    <div class="top-bar">
        <span class="brand">${empty diemDanh ? 'Them' : 'Sua'} Diem Danh</span>
        <a href="${pageContext.request.contextPath}/staff/diemdanh">&larr; Quay ve danh sach</a>
    </div>
    <div class="wrapper">
        <div class="form-card">
            <h2>${empty diemDanh ? 'Them diem danh moi' : 'Sua diem danh'}</h2>
            <c:if test="${not empty error}"><div class="error">${error}</div></c:if>
            <form method="post" action="${pageContext.request.contextPath}/staff/diemdanh">
                <c:if test="${not empty diemDanh}"><input type="hidden" name="id" value="${diemDanh.id}"></c:if>
                <div class="form-group">
                    <label>Sinh Vien *</label>
                    <select name="sinhVienId" required>
                        <option value="">-- Chon sinh vien --</option>
                        <c:forEach var="sv" items="${dsSinhVien}">
                            <option value="${sv.id}" ${diemDanh.sinhVien != null && diemDanh.sinhVien.id == sv.id ? 'selected' : ''}>${sv.hoTen} (${sv.maSV})</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Mon Hoc *</label>
                    <select name="monHocId" required>
                        <option value="">-- Chon mon hoc --</option>
                        <c:forEach var="mh" items="${dsMonHoc}">
                            <option value="${mh.id}" ${diemDanh.monHoc != null && diemDanh.monHoc.id == mh.id ? 'selected' : ''}>${mh.tenMon} (${mh.maMon})</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Ngay Diem Danh *</label>
                    <input type="date" name="ngayDiemDanh" value="${diemDanh.ngayDiemDanh}" required>
                </div>
                <div class="form-group">
                    <label>Buoi hoc</label>
                    <select name="buoiHoc">
                        <option value="">-- Chon buoi --</option>
                        <option value="Sang" ${diemDanh.buoiHoc == 'Sang' ? 'selected' : ''}>Sang</option>
                        <option value="Chieu" ${diemDanh.buoiHoc == 'Chieu' ? 'selected' : ''}>Chieu</option>
                        <option value="Toi" ${diemDanh.buoiHoc == 'Toi' ? 'selected' : ''}>Toi</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Trang Thai *</label>
                    <select name="trangThai" required>
                        <option value="">-- Chon trang thai --</option>
                        <option value="CO_MAT" ${diemDanh.trangThai == 'CO_MAT' ? 'selected' : ''}>Co mat</option>
                        <option value="VANG" ${diemDanh.trangThai == 'VANG' ? 'selected' : ''}>Vang</option>
                        <option value="DI_LATE" ${diemDanh.trangThai == 'DI_LATE' ? 'selected' : ''}>Di muon</option>
                        <option value="PHEP" ${diemDanh.trangThai == 'PHEP' ? 'selected' : ''}>Co phep</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Ghi chu</label>
                    <input type="text" name="ghiChu" value="${diemDanh.ghiChu}" placeholder="VD: Co phep cua lop truong">
                </div>
                <div class="btn-row">
                    <button type="submit" class="btn btn-primary">${empty diemDanh ? 'Them' : 'Luu thay doi'}</button>
                    <a href="${pageContext.request.contextPath}/staff/diemdanh" class="btn btn-secondary">Huy</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
