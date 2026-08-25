package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab10.repository.SinhVienRepository;
import vn.edu.eaut.lab10.repository.MonHocRepository;
import vn.edu.eaut.lab10.repository.DiemRepository;
import vn.edu.eaut.lab10.repository.GiaoVienRepository;
import vn.edu.eaut.lab10.repository.LopRepository;
import vn.edu.eaut.lab10.repository.DiemDanhRepository;
import vn.edu.eaut.lab10.model.DiemDanh;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ThongKeController", urlPatterns = {"/staff/thongke", "/admin/thongke"})
public class ThongKeController extends HttpServlet {

    private final SinhVienRepository sinhVienRepo = new SinhVienRepository();
    private final MonHocRepository monHocRepo = new MonHocRepository();
    private final DiemRepository diemRepo = new DiemRepository();
    private final GiaoVienRepository giaoVienRepo = new GiaoVienRepository();
    private final LopRepository lopRepo = new LopRepository();
    private final DiemDanhRepository diemDanhRepo = new DiemDanhRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Thong ke tong quan
        Map<String, Object> thongKe = new HashMap<>();
        thongKe.put("tongSinhVien", sinhVienRepo.count());
        thongKe.put("tongMonHoc", monHocRepo.count());
        thongKe.put("tongDiem", diemRepo.count());
        thongKe.put("tongGiaoVien", giaoVienRepo.count());
        thongKe.put("tongLop", lopRepo.count());
        thongKe.put("tongDiemDanh", diemDanhRepo.count());

        // Thong ke diem
        thongKe.put("diemGioi", diemRepo.countByXepLoai("Gioi"));
        thongKe.put("diemKha", diemRepo.countByXepLoai("Kha"));
        thongKe.put("diemTrungBinh", diemRepo.countByXepLoai("Trung binh"));
        thongKe.put("diemYeu", diemRepo.countByXepLoai("Yeu"));
        thongKe.put("diemKem", diemRepo.countByXepLoai("Kem"));

        // Thong ke diem danh
        thongKe.put("ddCoMat", diemDanhRepo.countByTrangThai(DiemDanh.TrangThaiDiemDanh.CO_MAT));
        thongKe.put("ddVang", diemDanhRepo.countByTrangThai(DiemDanh.TrangThaiDiemDanh.VANG));
        thongKe.put("ddDiMuon", diemDanhRepo.countByTrangThai(DiemDanh.TrangThaiDiemDanh.DI_LATE));
        thongKe.put("ddCoPhep", diemDanhRepo.countByTrangThai(DiemDanh.TrangThaiDiemDanh.PHEP));

        request.setAttribute("thongKe", thongKe);
        request.getRequestDispatcher("/staff/thongke.jsp").forward(request, response);
    }
}
