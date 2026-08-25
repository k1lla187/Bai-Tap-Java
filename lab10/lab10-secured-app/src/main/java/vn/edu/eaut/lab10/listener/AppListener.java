package vn.edu.eaut.lab10.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import vn.edu.eaut.lab10.config.JPAUtil;
import vn.edu.eaut.lab10.model.*;
import vn.edu.eaut.lab10.repository.*;
import java.math.BigDecimal;

@WebListener
public class AppListener implements ServletContextListener {

    private UserRepository userRepo;
    private SinhVienRepository svRepo;
    private MonHocRepository mhRepo;
    private DiemRepository diemRepo;
    private GiaoVienRepository gvRepo;
    private LopRepository lopRepo;
    private DiemDanhRepository ddRepo;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("=== Lab10 App Starting ===");
        userRepo = new UserRepository();
        svRepo = new SinhVienRepository();
        mhRepo = new MonHocRepository();
        diemRepo = new DiemRepository();
        gvRepo = new GiaoVienRepository();
        lopRepo = new LopRepository();
        ddRepo = new DiemDanhRepository();

        if (userRepo.count() == 0) {
            System.out.println("Seeding default data...");
            seedUsers();
            seedBusinessData();
            System.out.println("Seed completed.");
        }
        System.out.println("=== Lab10 App Ready ===");
    }

    private void seedUsers() {
        // Plain text passwords (for lab learning purpose)
        User admin = new User("admin@eaut.edu.vn", "admin123", "Quan Tri Vien", Role.ADMIN);
        admin.setPhone("0900000001");
        admin.setActive(true);

        User staff = new User("staff@eaut.edu.vn", "staff123", "Nhan Vien Nguyen", Role.STAFF);
        staff.setPhone("0900000002");
        staff.setActive(true);

        User user = new User("user@eaut.edu.vn", "user123", "Nguoi Dung Le", Role.USER);
        user.setPhone("0900000003");
        user.setActive(true);

        try {
            userRepo.save(admin);
            userRepo.save(staff);
            userRepo.save(user);
            System.out.println("  Default users: admin@eaut.edu.vn / admin123 (ADMIN)");
            System.out.println("               staff@eaut.edu.vn / staff123 (STAFF)");
            System.out.println("               user@eaut.edu.vn / user123 (USER)");
        } catch (Exception e) {
            System.err.println("  Error seeding users: " + e.getMessage());
        }
    }

    private void seedBusinessData() {
        try {
            // Seed GiaoVien
            GiaoVien gv1 = new GiaoVien("GV001", "TS. Nguyen Van G", "nguyenvang@eaut.edu.vn", "Cong nghe thong tin");
            gv1.setPhone("0912345001");
            GiaoVien gv2 = new GiaoVien("GV002", "PGS. Tran Thi H", "tranthih@eaut.edu.vn", "Co so du lieu");
            gv2.setPhone("0912345002");
            GiaoVien gv3 = new GiaoVien("GV003", "ThS. Le Van K", "levank@eaut.edu.vn", "Lap trinh Web");
            gv3.setPhone("0912345003");
            gvRepo.save(gv1);
            gvRepo.save(gv2);
            gvRepo.save(gv3);
            System.out.println("  Seeded 3 GiaoVien");

            SinhVien sv1 = new SinhVien("SV001", "Nguyen Van A", "nva@eaut.edu.vn", "CNTT1");
            SinhVien sv2 = new SinhVien("SV002", "Tran Thi B", "ttb@eaut.edu.vn", "CNTT1");
            SinhVien sv3 = new SinhVien("SV003", "Le Van C", "lvc@eaut.edu.vn", "CNTT2");
            SinhVien sv4 = new SinhVien("SV004", "Pham Thi D", "ptd@eaut.edu.vn", "CNTT2");
            SinhVien sv5 = new SinhVien("SV005", "Hoang Van E", "hve@eaut.edu.vn", "DTVT1");
            svRepo.save(sv1);
            svRepo.save(sv2);
            svRepo.save(sv3);
            svRepo.save(sv4);
            svRepo.save(sv5);
            System.out.println("  Seeded 5 SinhVien");

            MonHoc mh1 = new MonHoc("IT3242", "Cong nghe Java", 3);
            MonHoc mh2 = new MonHoc("IT3201", "Co so du lieu", 4);
            MonHoc mh3 = new MonHoc("IT3101", "Lap trinh Web", 3);
            MonHoc mh4 = new MonHoc("IT2101", "Cau truc du lieu", 3);
            mhRepo.save(mh1);
            mhRepo.save(mh2);
            mhRepo.save(mh3);
            mhRepo.save(mh4);
            System.out.println("  Seeded 4 MonHoc");

            // Seed Lop
            Lop lop1 = new Lop("LOP1", "Cong nghe thong tin 1", "K2022");
            lop1.setSiSo(40);
            lop1.setGiaoVien(gv1);
            Lop lop2 = new Lop("LOP2", "Cong nghe thong tin 2", "K2022");
            lop2.setSiSo(38);
            lop2.setGiaoVien(gv2);
            lopRepo.save(lop1);
            lopRepo.save(lop2);
            System.out.println("  Seeded 2 Lop");

            Diem d1 = new Diem(sv1, mh1); d1.setDiemGK(new BigDecimal("8.0")); d1.setDiemCK(new BigDecimal("9.0")); d1.tinhDiemTongKet();
            Diem d2 = new Diem(sv1, mh2); d2.setDiemGK(new BigDecimal("7.5")); d2.setDiemCK(new BigDecimal("8.0")); d2.tinhDiemTongKet();
            Diem d3 = new Diem(sv2, mh1); d3.setDiemGK(new BigDecimal("9.0")); d3.setDiemCK(new BigDecimal("8.5")); d3.tinhDiemTongKet();
            Diem d4 = new Diem(sv2, mh3); d4.setDiemGK(new BigDecimal("6.0")); d4.setDiemCK(new BigDecimal("7.0")); d4.tinhDiemTongKet();
            Diem d5 = new Diem(sv3, mh1); d5.setDiemGK(new BigDecimal("5.0")); d5.setDiemCK(new BigDecimal("6.0")); d5.tinhDiemTongKet();
            diemRepo.save(d1);
            diemRepo.save(d2);
            diemRepo.save(d3);
            diemRepo.save(d4);
            diemRepo.save(d5);
            System.out.println("  Seeded 5 Diem records");

            // Seed DiemDanh
            DiemDanh dd1 = new DiemDanh(sv1, mh1, java.time.LocalDate.now(), DiemDanh.TrangThaiDiemDanh.CO_MAT);
            DiemDanh dd2 = new DiemDanh(sv2, mh1, java.time.LocalDate.now(), DiemDanh.TrangThaiDiemDanh.CO_MAT);
            DiemDanh dd3 = new DiemDanh(sv3, mh1, java.time.LocalDate.now(), DiemDanh.TrangThaiDiemDanh.VANG);
            DiemDanh dd4 = new DiemDanh(sv4, mh2, java.time.LocalDate.now(), DiemDanh.TrangThaiDiemDanh.DI_LATE);
            DiemDanh dd5 = new DiemDanh(sv5, mh3, java.time.LocalDate.now(), DiemDanh.TrangThaiDiemDanh.PHEP);
            ddRepo.save(dd1);
            ddRepo.save(dd2);
            ddRepo.save(dd3);
            ddRepo.save(dd4);
            ddRepo.save(dd5);
            System.out.println("  Seeded 5 DiemDanh records");
        } catch (Exception e) {
            System.err.println("  Error seeding business data: " + e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            JPAUtil.getEntityManagerFactory().close();
        } catch (Exception ignored) {
        }
    }
}
