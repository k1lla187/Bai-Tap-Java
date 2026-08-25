<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${empty lop ? 'Them' : 'Sua'} Lop</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f4f6f9; min-height: 100vh; display: flex; align-items: center; justify-content: center; }
        .form-card { background: white; padding: 40px; border-radius: 16px; box-shadow: 0 8px 30px rgba(0,0,0,0.1); width: 500px; max-width: 95%; }
        h2 { color: #333; margin-bottom: 24px; }
        .form-group { margin-bottom: 16px; }
        .form-group label { display: block; margin-bottom: 6px; color: #555; font-weight: 500; font-size: 14px; }
        .form-group input, .form-group select { width: 100%; padding: 11px 14px; border: 2px solid #e0e0e0; border-radius: 8px; font-size: 14px; }
        .form-group input:focus, .form-group select:focus { outline: none; border-color: #7b1fa2; }
        .btn-row { display: flex; gap: 12px; margin-top: 24px; }
        .btn { padding: 12px 24px; border: none; border-radius: 8px; cursor: pointer; font-size: 15px; font-weight: 600; text-decoration: none; display: inline-block; }
        .btn-primary { background: #7b1fa2; color: white; }
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
        <span class="brand">${empty lop ? 'Them' : 'Sua'} Lop</span>
        <a href="${pageContext.request.contextPath}/staff/lop">&larr; Quay ve danh sach</a>
    </div>
    <div class="wrapper">
        <div class="form-card">
            <h2>${empty lop ? 'Them lop moi' : 'Sua thong tin lop'}</h2>
            <c:if test="${not empty error}"><div class="error">${error}</div></c:if>
            <form method="post" action="${pageContext.request.contextPath}/staff/lop">
                <c:if test="${not empty lop}"><input type="hidden" name="id" value="${lop.id}"></c:if>
                <div class="form-group">
                    <label>Ma Lop *</label>
                    <input type="text" name="maLop" value="${lop.maLop}" required placeholder="VD: Lop1">
                </div>
                <div class="form-group">
                    <label>Ten Lop *</label>
                    <input type="text" name="tenLop" value="${lop.tenLop}" required placeholder="VD: Lop Cong nghe thong tin 1">
                </div>
                <div class="form-group">
                    <label>Khoa</label>
                    <input type="text" name="khoa" value="${lop.khoa}" placeholder="VD: K2022">
                </div>
                <div class="form-group">
                    <label>Si so</label>
                    <input type="number" name="siSo" value="${lop.siSo}" placeholder="VD: 40" min="1">
                </div>
                <div class="form-group">
                    <label>Giao vien</label>
                    <select name="giaoVienId">
                        <option value="">-- Chon giao vien --</option>
                        <c:forEach var="gv" items="${dsGiaoVien}">
                            <option value="${gv.id}" ${lop.giaoVien != null && lop.giaoVien.id == gv.id ? 'selected' : ''}>${gv.hoTen} (${gv.maGV})</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="btn-row">
                    <button type="submit" class="btn btn-primary">${empty lop ? 'Them' : 'Luu thay doi'}</button>
                    <a href="${pageContext.request.contextPath}/staff/lop" class="btn btn-secondary">Huy</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
