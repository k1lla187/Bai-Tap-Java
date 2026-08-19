package vn.edu.eaut.lab7.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class LoginFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String requestPath = req.getRequestURI().substring(req.getContextPath().length());

        // Allow login page, login servlet, and static resources (CSS/JS/images)
        if (isLoginOrPublicResource(requestPath)) {
            chain.doFilter(request, response);
            return;
        }

        // Check session for all other resources
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        // User is logged in, proceed to next filter/resource
        chain.doFilter(request, response);
    }

    /**
     * Check if the request path is for login page, login servlet, or static resources
     * @param requestPath Request URI path (without context path)
     * @return true if the path is public (no session required)
     */
    private boolean isLoginOrPublicResource(String requestPath) {
        // Allow login page and login servlet
        if (requestPath.equals("/login.jsp") || requestPath.equals("/login")) {
            return true;
        }

        // Allow static resources (CSS, JS, images, fonts, Bootstrap CDN paths)
        if (requestPath.startsWith("/css/") ||
            requestPath.startsWith("/js/") ||
            requestPath.startsWith("/images/") ||
            requestPath.startsWith("/fonts/") ||
            requestPath.startsWith("/assets/") ||
            requestPath.endsWith(".css") ||
            requestPath.endsWith(".js") ||
            requestPath.endsWith(".png") ||
            requestPath.endsWith(".jpg") ||
            requestPath.endsWith(".jpeg") ||
            requestPath.endsWith(".gif") ||
            requestPath.endsWith(".svg") ||
            requestPath.endsWith(".woff") ||
            requestPath.endsWith(".woff2") ||
            requestPath.endsWith(".ttf") ||
            requestPath.endsWith(".eot")) {
            return true;
        }

        return false;
    }
}
