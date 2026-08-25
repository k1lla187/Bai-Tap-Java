package vn.edu.eaut.lab10.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.eaut.lab10.model.Role;
import vn.edu.eaut.lab10.model.User;
import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/*", "/staff/*"})
public class AuthorizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("currentUser");
        String path = request.getRequestURI();
        boolean authorized = false;

        if (path.contains("/admin/")) {
            authorized = user.getRole() == Role.ADMIN;
        } else if (path.contains("/staff/")) {
            authorized = user.getRole() == Role.ADMIN || user.getRole() == Role.STAFF;
        }

        if (!authorized) {
            response.sendRedirect(request.getContextPath() + "/error/403.jsp");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
