package vn.edu.eaut.lab7.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.eaut.lab7.model.CartItem;
import vn.edu.eaut.lab7.model.SanPham;
import vn.edu.eaut.lab7.repository.SanPhamRepository;

public class CartController extends HttpServlet {
    private final SanPhamRepository repo = new SanPhamRepository();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("add".equals(action)) {
            int productId = Integer.parseInt(req.getParameter("id"));
            int quantity = Integer.parseInt(req.getParameter("qty"));
            SanPham sp = repo.findById(productId);

            if (sp != null) {
                HttpSession session = req.getSession();
                @SuppressWarnings("unchecked")
                List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
                if (cart == null) {
                    cart = new ArrayList<>();
                    session.setAttribute("cart", cart);
                }

                CartItem existing = cart.stream()
                        .filter(c -> c.getSanPham().getId() == productId)
                        .findFirst()
                        .orElse(null);
                if (existing != null) {
                    existing.setSoLuong(existing.getSoLuong() + quantity);
                } else {
                    cart.add(new CartItem(sp, quantity));
                }
            }
            resp.sendRedirect(req.getContextPath() + "/gio-hang");
            return;
        }

        if ("remove".equals(action)) {
            int productId = Integer.parseInt(req.getParameter("id"));
            HttpSession session = req.getSession();
            @SuppressWarnings("unchecked")
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
            if (cart != null) {
                cart.removeIf(c -> c.getSanPham().getId() == productId);
            }
            resp.sendRedirect(req.getContextPath() + "/gio-hang");
            return;
        }

        if ("update".equals(action)) {
            int productId = Integer.parseInt(req.getParameter("id"));
            int quantity = Integer.parseInt(req.getParameter("qty"));
            HttpSession session = req.getSession();
            @SuppressWarnings("unchecked")
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
            if (cart != null) {
                for (CartItem item : cart) {
                    if (item.getSanPham().getId() == productId) {
                        item.setSoLuong(quantity);
                        break;
                    }
                }
            }
            resp.sendRedirect(req.getContextPath() + "/gio-hang");
            return;
        }

        if ("clear".equals(action)) {
            HttpSession session = req.getSession();
            session.removeAttribute("cart");
            resp.sendRedirect(req.getContextPath() + "/gio-hang");
            return;
        }

        req.setAttribute("dsSanPham", repo.findAll());
        req.getRequestDispatcher("/views/cart/list.jsp").forward(req, resp);
    }
}
