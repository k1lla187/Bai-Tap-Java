package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab10.model.MonHoc;
import vn.edu.eaut.lab10.repository.MonHocRepository;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MonHocController", urlPatterns = {"/staff/monhoc"})
public class MonHocController extends HttpServlet {

    private final MonHocRepository repo = new MonHocRepository();

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
        String maMon = request.getParameter("maMon");
        String tenMon = request.getParameter("tenMon");
        String stcStr = request.getParameter("soTinChi");

        MonHoc mh;
        boolean isNew = (idStr == null || idStr.isEmpty());
        if (isNew) mh = new MonHoc();
        else mh = repo.findById(Integer.parseInt(idStr));

        mh.setMaMon(maMon != null ? maMon.trim() : null);
        mh.setTenMon(tenMon != null ? tenMon.trim() : null);
        if (stcStr != null && !stcStr.isEmpty()) mh.setSoTinChi(Integer.parseInt(stcStr));

        String error = validate(mh, isNew);
        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("monHoc", mh);
            request.getRequestDispatcher("/staff/monhoc-form.jsp").forward(request, response);
            return;
        }

        if (isNew) repo.save(mh);
        else repo.update(mh);
        response.sendRedirect(request.getContextPath() + "/staff/monhoc");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String id = request.getParameter("id");
        if (id != null) repo.delete(Integer.parseInt(id));
        response.sendRedirect(request.getContextPath() + "/staff/monhoc");
    }

    private void list(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<MonHoc> ds;
        if (keyword != null && !keyword.trim().isEmpty()) {
            ds = repo.search(keyword.trim());
            request.setAttribute("keyword", keyword);
        } else {
            ds = repo.findAll();
        }
        request.setAttribute("dsMonHoc", ds);
        request.getRequestDispatcher("/staff/monhoc-list.jsp").forward(request, response);
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        MonHoc mh = repo.findById(Integer.parseInt(id));
        request.setAttribute("monHoc", mh);
        request.getRequestDispatcher("/staff/monhoc-form.jsp").forward(request, response);
    }

    private String validate(MonHoc mh, boolean isNew) {
        if (mh.getMaMon() == null || mh.getMaMon().trim().isEmpty()) return "Ma mon khong duoc trong";
        if (mh.getTenMon() == null || mh.getTenMon().trim().isEmpty()) return "Ten mon khong duoc trong";
        if (mh.getSoTinChi() == null || mh.getSoTinChi() < 1) return "So tin chi phai lon hon 0";
        if (isNew && repo.existsByMaMon(mh.getMaMon())) return "Ma mon hoc da ton tai";
        return null;
    }
}
