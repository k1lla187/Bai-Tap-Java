package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.eaut.lab10.model.LoginLog;
import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.repository.LoginLogRepository;
import vn.edu.eaut.lab10.service.AuthService;
import java.io.IOException;

@WebServlet(name = "AuthController", urlPatterns = {"/auth"})
public class AuthController extends HttpServlet {

    private final AuthService authService = new AuthService();
    private final LoginLogRepository logRepo = new LoginLogRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Email va mat khau khong duoc trong");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        User user = authService.login(email.trim(), password);

        if (user == null) {
            // Log failed attempt
            logAction(null, email, "LOGIN_FAILED", getClientIP(request), request.getHeader("User-Agent"));
            request.setAttribute("error", "Email hoac mat khau khong dung");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        // Log successful login
        logAction(user.getId(), email, "LOGIN", getClientIP(request), request.getHeader("User-Agent"));

        HttpSession session = request.getSession(true);
        session.setAttribute("currentUser", user);
        session.setAttribute("userId", user.getId());
        session.setAttribute("userEmail", user.getEmail());
        session.setAttribute("userRole", user.getRole().name());
        session.setAttribute("userName", user.getFullName());

        response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String action = request.getParameter("action");

        if ("logout".equals(action)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                User user = (User) session.getAttribute("currentUser");
                if (user != null) {
                    logAction(user.getId(), user.getEmail(), "LOGOUT",
                            getClientIP(request), request.getHeader("User-Agent"));
                }
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
    }

    private void logAction(Integer userId, String email, String action, String ip, String ua) {
        try {
            LoginLog log = new LoginLog(userId, email, action, ip, ua);
            logRepo.save(log);
        } catch (Exception ignored) {
        }
    }

    private String getClientIP(HttpServletRequest request) {
        String xf = request.getHeader("X-Forwarded-For");
        if (xf != null && !xf.isEmpty()) {
            return xf.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
