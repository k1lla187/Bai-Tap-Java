package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab10.model.Role;
import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.service.UserManagementService;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserController", urlPatterns = {"/admin/users"})
public class UserController extends HttpServlet {

    private final UserManagementService service = new UserManagementService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("edit".equals(action)) {
            showEditForm(request, response);
        } else if ("delete".equals(action)) {
            doDelete(request, response);
        } else {
            listUsers(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idStr = request.getParameter("id");
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String roleStr = request.getParameter("role");
        String activeStr = request.getParameter("active");
        String password = request.getParameter("password");

        User user;
        boolean isNew = (idStr == null || idStr.isEmpty());
        if (isNew) {
            user = new User();
            user.setPassword(password);
        } else {
            user = service.findById(Integer.parseInt(idStr));
        }

        user.setEmail(email.trim());
        user.setFullName(fullName.trim());
        if (roleStr != null) user.setRole(Role.valueOf(roleStr));
        user.setActive(!"on".equals(activeStr) ? false : true);

        String error;
        if (isNew) {
            error = service.save(user);
        } else {
            error = service.update(user);
        }

        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/admin/user-form.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/admin/users");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String id = request.getParameter("id");
        if (id != null) {
            service.delete(Integer.parseInt(id));
        }
        response.sendRedirect(request.getContextPath() + "/admin/users");
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<User> users;
        if (keyword != null && !keyword.trim().isEmpty()) {
            users = service.search(keyword);
            request.setAttribute("keyword", keyword);
        } else {
            users = service.findAll();
        }
        request.setAttribute("users", users);
        request.getRequestDispatcher("/admin/users.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        User user = service.findById(Integer.parseInt(id));
        request.setAttribute("user", user);
        request.getRequestDispatcher("/admin/user-form.jsp").forward(request, response);
    }
}
