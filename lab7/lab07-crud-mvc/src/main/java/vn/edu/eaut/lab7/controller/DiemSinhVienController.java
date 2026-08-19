package vn.edu.eaut.lab7.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab7.model.DiemSinhVien;
import vn.edu.eaut.lab7.repository.DiemSinhVienRepository;
import vn.edu.eaut.lab7.repository.SinhVienRepository;

public class DiemSinhVienController extends HttpServlet {
    private final DiemSinhVienRepository repo = new DiemSinhVienRepository();
    private final SinhVienRepository svRepo = new SinhVienRepository();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        String pageParam = req.getParameter("page");
        int page = pageParam != null ? Integer.parseInt(pageParam) : 1;

        if ("new".equals(action)) {
            req.setAttribute("dsSinhVien", svRepo.findAll());
            req.getRequestDispatcher("/views/diem/form.jsp").forward(req, resp);
            return;
        }
        if ("edit".equals(action)) {
            req.setAttribute("diem", repo.findById(Integer.parseInt(req.getParameter("id"))));
            req.setAttribute("dsSinhVien", svRepo.findAll());
            req.getRequestDispatcher("/views/diem/form.jsp").forward(req, resp);
            return;
        }
        if ("detail".equals(action)) {
            req.setAttribute("diem", repo.findById(Integer.parseInt(req.getParameter("id"))));
            req.getRequestDispatcher("/views/diem/detail.jsp").forward(req, resp);
            return;
        }
        if ("delete".equals(action)) {
            repo.delete(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + "/diem-sinh-vien");
            return;
        }

        int pageSize = 5;
        req.setAttribute("dsDiem", repo.findAll_WithPaging(page, pageSize));
        req.setAttribute("currentPage", page);
        req.setAttribute("totalDiem", repo.findAll().size());
        req.setAttribute("totalPages", (repo.findAll().size() + pageSize - 1) / pageSize);
        req.getRequestDispatcher("/views/diem/list.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        DiemSinhVien diem = new DiemSinhVien(
                id == null || id.isBlank() ? 0 : Integer.parseInt(id),
                Integer.parseInt(req.getParameter("sinhVienId")),
                Double.parseDouble(req.getParameter("diemChuyen")),
                Double.parseDouble(req.getParameter("diemGiuaKy")),
                Double.parseDouble(req.getParameter("diemCuoiKy"))
        );

        if (diem.getId() == 0) repo.add(diem);
        else repo.update(diem);

        resp.sendRedirect(req.getContextPath() + "/diem-sinh-vien");
    }
}
