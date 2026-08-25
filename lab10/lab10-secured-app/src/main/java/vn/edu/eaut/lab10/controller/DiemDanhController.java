package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab10.model.DiemDanh;
import vn.edu.eaut.lab10.model.SinhVien;
import vn.edu.eaut.lab10.model.MonHoc;
import vn.edu.eaut.lab10.repository.DiemDanhRepository;
import vn.edu.eaut.lab10.repository.SinhVienRepository;
import vn.edu.eaut.lab10.repository.MonHocRepository;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "DiemDanhController", urlPatterns = {"/staff/diemdanh"})
public class DiemDanhController extends HttpServlet {

    private final DiemDanhRepository repo = new DiemDanhRepository();
    private final SinhVienRepository sinhVienRepo = new SinhVienRepository();
    private final MonHocRepository monHocRepo = new MonHocRepository();

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
        String sinhVienIdStr = request.getParameter("sinhVienId");
        String monHocIdStr = request.getParameter("monHocId");
        String ngayStr = request.getParameter("ngayDiemDanh");
        String buoiHoc = request.getParameter("buoiHoc");
        String trangThai = request.getParameter("trangThai");
        String ghiChu = request.getParameter("ghiChu");

        DiemDanh dd;
        boolean isNew = (idStr == null || idStr.isEmpty());
        if (isNew) {
            dd = new DiemDanh();
        } else {
            dd = repo.findById(Integer.parseInt(idStr));
        }

        if (sinhVienIdStr != null && !sinhVienIdStr.isEmpty()) {
            SinhVien sv = sinhVienRepo.findById(Integer.parseInt(sinhVienIdStr));
            dd.setSinhVien(sv);
        }
        if (monHocIdStr != null && !monHocIdStr.isEmpty()) {
            MonHoc mh = monHocRepo.findById(Integer.parseInt(monHocIdStr));
            dd.setMonHoc(mh);
        }
        if (ngayStr != null && !ngayStr.isEmpty()) {
            dd.setNgayDiemDanh(LocalDate.parse(ngayStr));
        }
        dd.setBuoiHoc(buoiHoc != null ? buoiHoc.trim() : null);
        if (trangThai != null && !trangThai.isEmpty()) {
            dd.setTrangThai(DiemDanh.TrangThaiDiemDanh.valueOf(trangThai));
        }
        dd.setGhiChu(ghiChu != null ? ghiChu.trim() : null);

        String error = validate(dd, isNew);
        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("diemDanh", dd);
            loadComboData(request);
            request.getRequestDispatcher("/staff/diemdanh-form.jsp").forward(request, response);
            return;
        }

        if (isNew) repo.save(dd);
        else repo.update(dd);

        response.sendRedirect(request.getContextPath() + "/staff/diemdanh");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String id = request.getParameter("id");
        if (id != null) repo.delete(Integer.parseInt(id));
        response.sendRedirect(request.getContextPath() + "/staff/diemdanh");
    }

    private void list(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<DiemDanh> ds;
        if (keyword != null && !keyword.trim().isEmpty()) {
            ds = repo.search(keyword.trim());
            request.setAttribute("keyword", keyword);
        } else {
            ds = repo.findAll();
        }
        request.setAttribute("dsDiemDanh", ds);
        loadComboData(request);
        request.getRequestDispatcher("/staff/diemdanh-list.jsp").forward(request, response);
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        DiemDanh dd = repo.findById(Integer.parseInt(id));
        request.setAttribute("diemDanh", dd);
        loadComboData(request);
        request.getRequestDispatcher("/staff/diemdanh-form.jsp").forward(request, response);
    }

    private void loadComboData(HttpServletRequest request) {
        List<SinhVien> dsSV = sinhVienRepo.findAll();
        List<MonHoc> dsMH = monHocRepo.findAll();
        request.setAttribute("dsSinhVien", dsSV);
        request.setAttribute("dsMonHoc", dsMH);
    }

    private String validate(DiemDanh dd, boolean isNew) {
        if (dd.getSinhVien() == null) return "Vui long chon sinh vien";
        if (dd.getMonHoc() == null) return "Vui long chon mon hoc";
        if (dd.getNgayDiemDanh() == null) return "Vui long chon ngay diem danh";
        return null;
    }
}
