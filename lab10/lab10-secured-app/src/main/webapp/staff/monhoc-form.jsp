<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${empty monHoc ? 'Them' : 'Sua'} Mon Hoc</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f4f6f9; min-height: 100vh; display: flex; align-items: center; justify-content: center; }
        .form-card { background: white; padding: 40px; border-radius: 16px; box-shadow: 0 8px 30px rgba(0,0,0,0.1); width: 500px; max-width: 95%; }
        h2 { color: #333; margin-bottom: 24px; }
        .form-group { margin-bottom: 16px; }
        .form-group label { display: block; margin-bottom: 6px; color: #555; font-weight: 500; font-size: 14px; }
        .form-group input { width: 100%; padding: 11px 14px; border: 2px solid #e0e0e0; border-radius: 8px; font-size: 14px; }
        .form-group input:focus { outline: none; border-color: #667eea; }
        .btn-row { display: flex; gap: 12px; margin-top: 24px; }
        .btn { padding: 12px 24px; border: none; border-radius: 8px; cursor: pointer; font-size: 15px; font-weight: 600; text-decoration: none; display: inline-block; }
        .btn-primary { background: #43a047; color: white; }
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
        <span class="brand">${empty monHoc ? 'Them' : 'Sua'} Mon Hoc</span>
        <a href="${pageContext.request.contextPath}/staff/monhoc">&larr; Quay ve danh sach</a>
    </div>
    <div class="wrapper">
        <div class="form-card">
            <h2>${empty monHoc ? 'Them mon hoc moi' : 'Sua thong tin mon hoc'}</h2>
            <c:if test="${not empty error}"><div class="error">${error}</div></c:if>
            <form method="post" action="${pageContext.request.contextPath}/staff/monhoc">
                <c:if test="${not empty monHoc}"><input type="hidden" name="id" value="${monHoc.id}"></c:if>
                <div class="form-group">
                    <label>Ma Mon *</label>
                    <input type="text" name="maMon" value="${monHoc.maMon}" required placeholder="VD: IT3242">
                </div>
                <div class="form-group">
                    <label>Ten Mon *</label>
                    <input type="text" name="tenMon" value="${monHoc.tenMon}" required placeholder="VD: Cong nghe Java">
                </div>
                <div class="form-group">
                    <label>So Tin Chi *</label>
                    <input type="number" name="soTinChi" value="${monHoc.soTinChi}" required min="1" placeholder="VD: 3">
                </div>
                <div class="btn-row">
                    <button type="submit" class="btn btn-primary">${empty monHoc ? 'Them' : 'Luu thay doi'}</button>
                    <a href="${pageContext.request.contextPath}/staff/monhoc" class="btn btn-secondary">Huy</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
