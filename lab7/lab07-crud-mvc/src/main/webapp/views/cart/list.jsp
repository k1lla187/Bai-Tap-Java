<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Giỏ hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body { background: #f8f9fa; padding: 20px 0; }
        .container { margin-top: 30px; margin-bottom: 30px; }
        .cart-card { background: white; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.08); }
        .cart-header { background: linear-gradient(135deg, #6f42c1 0%, #5a32a3 100%); color: white; padding: 20px; border-radius: 12px 12px 0 0; }
        .cart-header h3 { margin: 0; font-weight: 600; }
        .cart-body { padding: 20px; }
        .table th { background: #f8f9fa; font-weight: 600; color: #333; border-bottom: 2px solid #e0e0e0; }
        .table tbody tr { border-bottom: 1px solid #e0e0e0; }
        .table tbody tr:hover { background: #f8f9fa; }
        .qty-input { width: 70px; }
        .total-section { padding: 20px; background: #f8f9fa; border-top: 2px solid #e0e0e0; border-radius: 0 0 12px 12px; }
        .total-row { display: flex; justify-content: space-between; align-items: center; margin: 10px 0; font-size: 16px; }
        .total-amount { font-size: 24px; font-weight: 700; color: #6f42c1; }
        .btn-clear { background: #dc3545; border: none; }
        .btn-clear:hover { background: #c82333; }
        .action-btn-update { background: #0066cc; color: white; border: none; padding: 6px 12px; border-radius: 4px; font-size: 13px; }
        .action-btn-update:hover { background: #0052a3; }
        .action-btn-remove { color: #dc3545; text-decoration: none; font-size: 13px; }
    </style>
</head>
<body style="background: #f8f9fa;">
<div class="container">
    <!-- Breadcrumb -->
    <div style="margin-bottom: 20px;">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb" style="background: transparent; margin: 0;">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/" style="text-decoration: none;"><i class="bi bi-house"></i> Trang chủ</a></li>
                <li class="breadcrumb-item active">Giỏ hàng</li>
            </ol>
        </nav>
    </div>

    <!-- Cart Section -->
    <div class="cart-card">
        <div class="cart-header">
            <h3><i class="bi bi-bag-check"></i> Giỏ hàng</h3>
        </div>

        <div class="cart-body">
            <% 
                java.util.List<?> cart = (java.util.List<?>) session.getAttribute("cart");
                if (cart != null && !cart.isEmpty()) {
            %>

            <!-- Cart Table -->
            <div style="overflow-x: auto; margin-bottom: 20px;">
                <table class="table table-hover mb-0">
                    <thead>
                        <tr>
                            <th>Mã</th>
                            <th>Tên sản phẩm</th>
                            <th>Giá</th>
                            <th>Số lượng</th>
                            <th>Tổng tiền</th>
                            <th style="width: 100px; text-align: center;">Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                            double tongCong = 0;
                            for (Object item : cart) {
                                vn.edu.eaut.lab7.model.CartItem cartItem = (vn.edu.eaut.lab7.model.CartItem) item;
                                double tongTien = cartItem.getTongTien();
                                tongCong += tongTien;
                        %>
                        <tr>
                            <td><span class="badge bg-light text-dark"><%= cartItem.getSanPham().getMa() %></span></td>
                            <td><strong><%= cartItem.getSanPham().getTen() %></strong></td>
                            <td><fmt:formatNumber value="<%= cartItem.getSanPham().getGia() %>" type="currency" currencySymbol="đ"/></td>
                            <td>
                                <form method="get" action="${pageContext.request.contextPath}/gio-hang" style="display: inline; display: flex; gap: 8px;">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="id" value="<%= cartItem.getSanPham().getId() %>">
                                    <input type="number" name="qty" value="<%= cartItem.getSoLuong() %>" min="1" class="form-control qty-input">
                                    <button type="submit" class="action-btn-update"><i class="bi bi-arrow-repeat"></i> Cập nhật</button>
                                </form>
                            </td>
                            <td><strong><fmt:formatNumber value="<%= tongTien %>" type="currency" currencySymbol="đ"/></strong></td>
                            <td style="text-align: center;">
                                <a href="${pageContext.request.contextPath}/gio-hang?action=remove&id=<%= cartItem.getSanPham().getId() %>" class="action-btn-remove">
                                    <i class="bi bi-trash"></i> Xóa
                                </a>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>

            <!-- Total Section -->
            <div class="total-section">
                <div class="total-row">
                    <span>Tổng cộng:</span>
                    <span class="total-amount"><fmt:formatNumber value="<%= tongCong %>" type="currency" currencySymbol="đ"/></span>
                </div>
                <div style="margin-top: 20px; text-align: right;">
                    <form method="get" action="${pageContext.request.contextPath}/gio-hang" style="display: inline;">
                        <input type="hidden" name="action" value="clear">
                        <button type="submit" class="btn btn-clear btn-sm text-white">
                            <i class="bi bi-trash"></i> Xóa hết
                        </button>
                    </form>
                </div>
            </div>

            <% } else { %>

            <!-- Empty Cart Message -->
            <div style="text-align: center; padding: 60px 20px;">
                <div style="font-size: 4rem; color: #ccc; margin-bottom: 20px;"><i class="bi bi-bag"></i></div>
                <p style="color: #999; font-size: 16px; margin-bottom: 20px;">Giỏ hàng của bạn đang trống</p>
                <a href="${pageContext.request.contextPath}/" class="btn btn-primary">
                    <i class="bi bi-arrow-left"></i> Tiếp tục mua sắm
                </a>
            </div>

            <% } %>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

</body>
</html>
