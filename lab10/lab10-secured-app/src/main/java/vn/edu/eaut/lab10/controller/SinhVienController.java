package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab10.model.Role;
import vn.edu.eaut.lab10.model.SinhVien;
import vn.edu.eaut.lab10.repository.SinhVienRepository;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "SinhVienController", urlPatterns = {"/staff/sinhvien"})
public class SinhVienController extends HttpServlet {

    private final SinhVienRepository repo = new SinhVienRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("edit".equals(action)) {
            showForm(request, response);
        } else if ("delete".equals(action)) {
            doDelete(request, response);
        } else {
            list(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idStr = request.getParameter("id");
        String maSV = request.getParameter("maSV");
        String hoTen = request.getParameter("hoTen");
        String email = request.getParameter("email");
        String lop = request.getParameter("lop");
        String ngaySinhStr = request.getParameter("ngaySinh");

        SinhVien sv;
        boolean isNew = (idStr == null || idStr.isEmpty());
        if (isNew) {
            sv = new SinhVien();
        } else {
            sv = repo.findById(Integer.parseInt(idStr));
        }

        sv.setMaSV(maSV != null ? maSV.trim() : null);
        sv.setHoTen(hoTen != null ? hoTen.trim() : null);
        sv.setEmail(email != null ? email.trim() : null);
        sv.setLop(lop != null ? lop.trim() : null);
        if (ngaySinhStr != null && !ngaySinhStr.isEmpty()) {
            sv.setNgaySinh(LocalDate.parse(ngaySinhStr));
        }

        String error = validate(sv, isNew);
        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("sinhVien", sv);
            request.getRequestDispatcher("/staff/sinhvien-form.jsp").forward(request, response);
            return;
        }

        if (isNew) repo.save(sv);
        else repo.update(sv);

        response.sendRedirect(request.getContextPath() + "/staff/sinhvien");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String id = request.getParameter("id");
        if (id != null) repo.delete(Integer.parseInt(id));
        response.sendRedirect(request.getContextPath() + "/staff/sinhvien");
    }

    private void list(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<SinhVien> ds;
        if (keyword != null && !keyword.trim().isEmpty()) {
            ds = repo.search(keyword.trim());
            request.setAttribute("keyword", keyword);
        } else {
            ds = repo.findAll();
        }
        request.setAttribute("dsSinhVien", ds);
        request.getRequestDispatcher("/staff/sinhvien-list.jsp").forward(request, response);
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        SinhVien sv = repo.findById(Integer.parseInt(id));
        request.setAttribute("sinhVien", sv);
        request.getRequestDispatcher("/staff/sinhvien-form.jsp").forward(request, response);
    }

    private String validate(SinhVien sv, boolean isNew) {
        if (sv.getMaSV() == null || sv.getMaSV().trim().isEmpty()) return "Ma sinh vien khong duoc trong";
        if (sv.getHoTen() == null || sv.getHoTen().trim().isEmpty()) return "Ho ten khong duoc trong";
        if (isNew && repo.existsByMaSV(sv.getMaSV())) return "Ma sinh vien da ton tai";
        return null;
    }
}
