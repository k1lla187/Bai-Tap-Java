<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>500 - Loi he thong</title>
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f4f6f9; display: flex; align-items: center; justify-content: center; min-height: 100vh; }
        .card { background: white; padding: 50px; border-radius: 16px; box-shadow: 0 8px 30px rgba(0,0,0,0.12); text-align: center; max-width: 450px; }
        .code { font-size: 80px; font-weight: 800; color: #e65100; line-height: 1; }
        h2 { color: #333; margin: 16px 0 8px; }
        p { color: #777; margin-bottom: 28px; line-height: 1.6; }
        .btn { display: inline-block; padding: 12px 28px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; text-decoration: none; border-radius: 8px; font-weight: 600; transition: transform 0.2s; }
        .btn:hover { transform: translateY(-2px); }
    </style>
</head>
<body>
    <div class="card">
        <div class="code">500</div>
        <h2>Loi he thong</h2>
        <p>Da xay ra loi ben phia may chu. Vui long thu lai sau hoac lien he quan tri vien neu loi van xay ra.</p>
        <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn">Quay ve Dashboard</a>
    </div>
</body>
</html>
