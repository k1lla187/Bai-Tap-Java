package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.service.UserManagementService;
import java.io.IOException;

@WebServlet(name = "ProfileController", urlPatterns = {"/user/profile"})
public class ProfileController extends HttpServlet {

    private final UserManagementService service = new UserManagementService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/user/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User currentUser = (User) session.getAttribute("currentUser");
        User updated = service.findById(currentUser.getId());
        if (updated == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        if ("updateProfile".equals(action)) {
            handleUpdateProfile(request, response, updated, session);
        } else if ("changePassword".equals(action)) {
            handleChangePassword(request, response, updated, session);
        } else {
            request.getRequestDispatcher("/user/profile.jsp").forward(request, response);
        }
    }

    private void handleUpdateProfile(HttpServletRequest request, HttpServletResponse response,
                                     User user, HttpSession session) throws IOException, ServletException {
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");

        if (fullName == null || fullName.trim().isEmpty()) {
            request.setAttribute("error", "Ho ten khong duoc trong");
            request.setAttribute("user", user);
            request.getRequestDispatcher("/user/profile.jsp").forward(request, response);
            return;
        }

        user.setFullName(fullName.trim());
        user.setPhone(phone != null ? phone.trim() : null);

        String error = service.update(user);
        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/user/profile.jsp").forward(request, response);
            return;
        }

        session.setAttribute("currentUser", user);
        session.setAttribute("userName", user.getFullName());
        request.setAttribute("success", "Cap nhat ho so thanh cong");
        request.setAttribute("user", user);
        request.getRequestDispatcher("/user/profile.jsp").forward(request, response);
    }

    private void handleChangePassword(HttpServletRequest request, HttpServletResponse response,
                                      User user, HttpSession session) throws IOException, ServletException {
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        String error = service.changePassword(user.getId(), oldPassword, newPassword, confirmPassword);
        if (error != null) {
            request.setAttribute("passwordError", error);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/user/profile.jsp").forward(request, response);
            return;
        }

        request.setAttribute("passwordSuccess", "Doi mat khau thanh cong");
        request.setAttribute("user", user);
        request.getRequestDispatcher("/user/profile.jsp").forward(request, response);
    }
}
