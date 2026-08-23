package vn.edu.eaut.lab9.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab9.model.SinhVien;
import vn.edu.eaut.lab9.service.SinhVienService;
import vn.edu.eaut.lab9.repository.LopHocRepository;
import java.io.IOException;
import java.util.List;

/**
 * Controller for SinhVien CRUD operations
 */
@WebServlet("/sinh-vien")
public class SinhVienController extends HttpServlet {

    private final SinhVienService service = new SinhVienService();
    private final LopHocRepository lopHocRepo = new LopHocRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("edit".equals(action)) {
            if (!isAdmin(request)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Ban khong co quyen thuc hien thao tac nay");
                return;
            }
            showEditForm(request, response);
        } else if ("delete".equals(action)) {
            if (!isAdmin(request)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Ban khong co quyen thuc hien thao tac nay");
                return;
            }
            doDelete(request, response);
        } else {
            listSinhVien(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdmin(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Ban khong co quyen thuc hien thao tac nay");
            return;
        }

        request.setCharacterEncoding("UTF-8");
        
        String idStr = request.getParameter("id");
        String maSV = request.getParameter("maSinhVien");
        String hoTen = request.getParameter("hoTen");
        String email = request.getParameter("email");
        String lop = request.getParameter("lop");
        String lopHocIdStr = request.getParameter("lopHocId");
        
        SinhVien sv = new SinhVien(maSV, hoTen, email, lop);
        
        if (idStr != null && !idStr.isEmpty()) {
            sv.setId(Integer.parseInt(idStr));
            String error = service.update(sv);
            if (error != null) {
                request.setAttribute("error", error);
                request.setAttribute("sinhVien", sv);
                request.getRequestDispatcher("/views/sinhvien/form.jsp").forward(request, response);
                return;
            }
        } else {
            String error = service.save(sv);
            if (error != null) {
                request.setAttribute("error", error);
                request.setAttribute("sinhVien", sv);
                request.setAttribute("dsLopHoc", lopHocRepo.findAll());
                request.getRequestDispatcher("/views/sinhvien/form.jsp").forward(request, response);
                return;
            }
        }
        
        response.sendRedirect(request.getContextPath() + "/sinh-vien");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdmin(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Ban khong co quyen thuc hien thao tac nay");
            return;
        }

        String id = request.getParameter("id");
        if (id != null) {
            service.delete(Integer.parseInt(id));
        }
        response.sendRedirect(request.getContextPath() + "/sinh-vien");
    }

    private void listSinhVien(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String pageStr = request.getParameter("page");
        
        List<SinhVien> dsSinhVien;
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            dsSinhVien = service.search(keyword);
            request.setAttribute("keyword", keyword);
        } else {
            int page = 1;
            int pageSize = 5;
            if (pageStr != null) {
                page = Integer.parseInt(pageStr);
            }
            
            long total = service.count();
            int totalPages = (int) Math.ceil((double) total / pageSize);
            dsSinhVien = service.findAll(page, pageSize);
            
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
        }
        
        request.setAttribute("dsSinhVien", dsSinhVien);
        request.setAttribute("dsLopHoc", lopHocRepo.findAll());
        request.getRequestDispatcher("/views/sinhvien/list.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        SinhVien sv = service.findById(Integer.parseInt(id));
        request.setAttribute("sinhVien", sv);
        request.setAttribute("dsLopHoc", lopHocRepo.findAll());
        request.getRequestDispatcher("/views/sinhvien/form.jsp").forward(request, response);
    }

    private boolean isAdmin(HttpServletRequest request) {
        return "ADMIN".equals(request.getSession(false).getAttribute("role"));
    }
}
