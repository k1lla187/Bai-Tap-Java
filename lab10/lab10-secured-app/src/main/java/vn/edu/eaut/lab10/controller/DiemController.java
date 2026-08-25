package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab10.model.Diem;
import vn.edu.eaut.lab10.model.MonHoc;
import vn.edu.eaut.lab10.model.SinhVien;
import vn.edu.eaut.lab10.repository.DiemRepository;
import vn.edu.eaut.lab10.repository.MonHocRepository;
import vn.edu.eaut.lab10.repository.SinhVienRepository;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(name = "DiemController", urlPatterns = {"/staff/diem"})
public class DiemController extends HttpServlet {

    private final DiemRepository repo = new DiemRepository();
    private final SinhVienRepository svRepo = new SinhVienRepository();
    private final MonHocRepository mhRepo = new MonHocRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("edit".equals(action)) showForm(request, response);
        else if ("delete".equals(action)) doDelete(request, response);
        else list(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idStr = request.getParameter("id");
        String svIdStr = request.getParameter("sinhVienId");
        String mhIdStr = request.getParameter("monHocId");
        String gkStr = request.getParameter("diemGK");
        String ckStr = request.getParameter("diemCK");

        Diem d;
        boolean isNew = (idStr == null || idStr.isEmpty());
        if (isNew) {
            d = new Diem();
        } else {
            d = repo.findById(Integer.parseInt(idStr));
        }

        if (svIdStr != null && !svIdStr.isEmpty()) {
            SinhVien sv = svRepo.findById(Integer.parseInt(svIdStr));
            d.setSinhVien(sv);
        }
        if (mhIdStr != null && !mhIdStr.isEmpty()) {
            MonHoc mh = mhRepo.findById(Integer.parseInt(mhIdStr));
            d.setMonHoc(mh);
        }
        if (gkStr != null && !gkStr.isEmpty()) d.setDiemGK(new BigDecimal(gkStr));
        if (ckStr != null && !ckStr.isEmpty()) d.setDiemCK(new BigDecimal(ckStr));
        d.tinhDiemTongKet();

        String error = validate(d, isNew);
        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("diem", d);
            request.setAttribute("dsSinhVien", svRepo.findAll());
            request.setAttribute("dsMonHoc", mhRepo.findAll());
            request.getRequestDispatcher("/staff/diem-form.jsp").forward(request, response);
            return;
        }

        if (isNew) repo.save(d);
        else repo.update(d);
        response.sendRedirect(request.getContextPath() + "/staff/diem");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String id = request.getParameter("id");
        if (id != null) repo.delete(Integer.parseInt(id));
        response.sendRedirect(request.getContextPath() + "/staff/diem");
    }

    private void list(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Diem> ds = repo.findAll();
        request.setAttribute("dsDiem", ds);
        request.setAttribute("dsSinhVien", svRepo.findAll());
        request.setAttribute("dsMonHoc", mhRepo.findAll());
        request.getRequestDispatcher("/staff/diem-list.jsp").forward(request, response);
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        Diem d = repo.findById(Integer.parseInt(id));
        request.setAttribute("diem", d);
        request.setAttribute("dsSinhVien", svRepo.findAll());
        request.setAttribute("dsMonHoc", mhRepo.findAll());
        request.getRequestDispatcher("/staff/diem-form.jsp").forward(request, response);
    }

    private String validate(Diem d, boolean isNew) {
        if (d.getSinhVien() == null) return "Vui long chon sinh vien";
        if (d.getMonHoc() == null) return "Vui long chon mon hoc";
        if (d.getDiemGK() == null && d.getDiemCK() == null) return "Phai nhap it nhat mot diem";
        if (d.getDiemGK() != null && (d.getDiemGK().doubleValue() < 0 || d.getDiemGK().doubleValue() > 10))
            return "Diem giua ky phai tu 0 den 10";
        if (d.getDiemCK() != null && (d.getDiemCK().doubleValue() < 0 || d.getDiemCK().doubleValue() > 10))
            return "Diem cuoi ky phai tu 0 den 10";
        return null;
    }
}
