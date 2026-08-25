package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab10.model.Lop;
import vn.edu.eaut.lab10.model.GiaoVien;
import vn.edu.eaut.lab10.repository.LopRepository;
import vn.edu.eaut.lab10.repository.GiaoVienRepository;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "LopController", urlPatterns = {"/staff/lop"})
public class LopController extends HttpServlet {

    private final LopRepository repo = new LopRepository();
    private final GiaoVienRepository giaoVienRepo = new GiaoVienRepository();

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
        String maLop = request.getParameter("maLop");
        String tenLop = request.getParameter("tenLop");
        String khoa = request.getParameter("khoa");
        String siSoStr = request.getParameter("siSo");
        String giaoVienIdStr = request.getParameter("giaoVienId");

        Lop lop;
        boolean isNew = (idStr == null || idStr.isEmpty());
        if (isNew) {
            lop = new Lop();
        } else {
            lop = repo.findById(Integer.parseInt(idStr));
        }

        lop.setMaLop(maLop != null ? maLop.trim() : null);
        lop.setTenLop(tenLop != null ? tenLop.trim() : null);
        lop.setKhoa(khoa != null ? khoa.trim() : null);
        if (siSoStr != null && !siSoStr.isEmpty()) {
            lop.setSiSo(Integer.parseInt(siSoStr));
        }
        if (giaoVienIdStr != null && !giaoVienIdStr.isEmpty()) {
            GiaoVien gv = giaoVienRepo.findById(Integer.parseInt(giaoVienIdStr));
            lop.setGiaoVien(gv);
        }

        String error = validate(lop, isNew);
        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("lop", lop);
            loadComboData(request);
            request.getRequestDispatcher("/staff/lop-form.jsp").forward(request, response);
            return;
        }

        if (isNew) repo.save(lop);
        else repo.update(lop);

        response.sendRedirect(request.getContextPath() + "/staff/lop");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String id = request.getParameter("id");
        if (id != null) repo.delete(Integer.parseInt(id));
        response.sendRedirect(request.getContextPath() + "/staff/lop");
    }

    private void list(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Lop> ds;
        if (keyword != null && !keyword.trim().isEmpty()) {
            ds = repo.search(keyword.trim());
            request.setAttribute("keyword", keyword);
        } else {
            ds = repo.findAll();
        }
        request.setAttribute("dsLop", ds);
        request.getRequestDispatcher("/staff/lop-list.jsp").forward(request, response);
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        Lop lop = repo.findById(Integer.parseInt(id));
        request.setAttribute("lop", lop);
        loadComboData(request);
        request.getRequestDispatcher("/staff/lop-form.jsp").forward(request, response);
    }

    private void loadComboData(HttpServletRequest request) {
        List<GiaoVien> dsGV = giaoVienRepo.findAllActive();
        request.setAttribute("dsGiaoVien", dsGV);
    }

    private String validate(Lop lop, boolean isNew) {
        if (lop.getMaLop() == null || lop.getMaLop().trim().isEmpty()) return "Ma lop khong duoc trong";
        if (lop.getTenLop() == null || lop.getTenLop().trim().isEmpty()) return "Ten lop khong duoc trong";
        if (isNew && repo.existsByMaLop(lop.getMaLop())) return "Ma lop da ton tai";
        return null;
    }
}
