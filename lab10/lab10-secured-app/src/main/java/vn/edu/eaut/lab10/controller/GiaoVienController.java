package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab10.model.GiaoVien;
import vn.edu.eaut.lab10.repository.GiaoVienRepository;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GiaoVienController", urlPatterns = {"/staff/giaovien"})
public class GiaoVienController extends HttpServlet {

    private final GiaoVienRepository repo = new GiaoVienRepository();

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
        String maGV = request.getParameter("maGV");
        String hoTen = request.getParameter("hoTen");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String chuyenNganh = request.getParameter("chuyenNganh");

        GiaoVien gv;
        boolean isNew = (idStr == null || idStr.isEmpty());
        if (isNew) {
            gv = new GiaoVien();
        } else {
            gv = repo.findById(Integer.parseInt(idStr));
        }

        gv.setMaGV(maGV != null ? maGV.trim() : null);
        gv.setHoTen(hoTen != null ? hoTen.trim() : null);
        gv.setEmail(email != null ? email.trim() : null);
        gv.setPhone(phone != null ? phone.trim() : null);
        gv.setChuyenNganh(chuyenNganh != null ? chuyenNganh.trim() : null);

        String error = validate(gv, isNew);
        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("giaoVien", gv);
            request.getRequestDispatcher("/staff/giaovien-form.jsp").forward(request, response);
            return;
        }

        if (isNew) repo.save(gv);
        else repo.update(gv);

        response.sendRedirect(request.getContextPath() + "/staff/giaovien");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String id = request.getParameter("id");
        if (id != null) repo.delete(Integer.parseInt(id));
        response.sendRedirect(request.getContextPath() + "/staff/giaovien");
    }

    private void list(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<GiaoVien> ds;
        if (keyword != null && !keyword.trim().isEmpty()) {
            ds = repo.search(keyword.trim());
            request.setAttribute("keyword", keyword);
        } else {
            ds = repo.findAll();
        }
        request.setAttribute("dsGiaoVien", ds);
        request.getRequestDispatcher("/staff/giaovien-list.jsp").forward(request, response);
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        GiaoVien gv = repo.findById(Integer.parseInt(id));
        request.setAttribute("giaoVien", gv);
        request.getRequestDispatcher("/staff/giaovien-form.jsp").forward(request, response);
    }

    private String validate(GiaoVien gv, boolean isNew) {
        if (gv.getMaGV() == null || gv.getMaGV().trim().isEmpty()) return "Ma giao vien khong duoc trong";
        if (gv.getHoTen() == null || gv.getHoTen().trim().isEmpty()) return "Ho ten khong duoc trong";
        if (isNew && repo.existsByMaGV(gv.getMaGV())) return "Ma giao vien da ton tai";
        return null;
    }
}
