package vn.edu.eaut.lab9.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import vn.edu.eaut.lab9.config.JPAUtil;
import vn.edu.eaut.lab9.model.LopHoc;
import vn.edu.eaut.lab9.model.MonHoc;
import vn.edu.eaut.lab9.model.Role;
import vn.edu.eaut.lab9.model.User;
import vn.edu.eaut.lab9.model.SinhVien;
import vn.edu.eaut.lab9.repository.LopHocRepository;
import vn.edu.eaut.lab9.repository.MonHocRepository;
import vn.edu.eaut.lab9.repository.RoleRepository;
import vn.edu.eaut.lab9.repository.UserRepository;
import vn.edu.eaut.lab9.repository.SinhVienRepository;
import java.time.LocalDate;

/**
 * Seed data listener - initializes sample data when application starts
 */
@WebListener
public class DataSeederListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("=== Lab 9 JPA Repository da khoi dong ===");
        
        RoleRepository roleRepo = new RoleRepository();
        LopHocRepository lopHocRepo = new LopHocRepository();
        MonHocRepository monHocRepo = new MonHocRepository();
        UserRepository userRepo = new UserRepository();
        SinhVienRepository sinhVienRepo = new SinhVienRepository();
        
        // Seed Roles if empty
        if (roleRepo.findAll().isEmpty()) {
            Role admin = new Role("ADMIN", "Quan tri he thong");
            Role teacher = new Role("TEACHER", "Giao vien");
            Role user = new Role("USER", "Nguoi dung thong thuong");
            roleRepo.save(admin);
            roleRepo.save(teacher);
            roleRepo.save(user);
            System.out.println("Da tao 3 vai tro: ADMIN, TEACHER, USER");
        }
        
        // Seed LopHoc if empty
        if (lopHocRepo.findAll().isEmpty()) {
            LopHoc lop1 = new LopHoc("CNTT1", "Nguyen Van A");
            LopHoc lop2 = new LopHoc("CNTT2", "Tran Thi B");
            LopHoc lop3 = new LopHoc("DTVT1", "Le Van C");
            lopHocRepo.save(lop1);
            lopHocRepo.save(lop2);
            lopHocRepo.save(lop3);
            System.out.println("Da tao 3 lop hoc");
        }
        
        // Seed MonHoc if empty
        if (monHocRepo.findAll().isEmpty()) {
            MonHoc mh1 = new MonHoc("IT3242", "Cong nghe Java", 3);
            MonHoc mh2 = new MonHoc("IT3201", "Co so du lieu", 4);
            MonHoc mh3 = new MonHoc("IT3101", "Lap trinh Web", 3);
            MonHoc mh4 = new MonHoc("IT2101", "Cau truc du lieu", 3);
            monHocRepo.save(mh1);
            monHocRepo.save(mh2);
            monHocRepo.save(mh3);
            monHocRepo.save(mh4);
            System.out.println("Da tao 4 mon hoc");
        }
        
        // Seed Users if empty
        if (userRepo.findAll().isEmpty()) {
            Role adminRole = roleRepo.findByName("ADMIN");
            Role userRole = roleRepo.findByName("USER");
            
            User admin = new User("admin", "123456", "Quan tri vien");
            admin.setEmail("admin@eaut.edu.vn");
            admin.setRole(adminRole);
            userRepo.save(admin);
            
            User user1 = new User("user", "123456", "Nguoi dung");
            user1.setEmail("user@eaut.edu.vn");
            user1.setRole(userRole);
            userRepo.save(user1);
            
            System.out.println("Da tao 2 tai khoan: admin/123456, user/123456");
        }
        
        // Seed SinhVien if empty
        if (sinhVienRepo.findAll().isEmpty()) {
            LopHoc lop1 = lopHocRepo.findAll().get(0);
            
            SinhVien sv1 = new SinhVien("SV001", "Nguyen Van A", "nva@eaut.edu.vn", "CNTT1");
            sv1.setLopHoc(lop1);
            sv1.setNgaySinh(LocalDate.of(2003, 5, 15));
            sinhVienRepo.save(sv1);
            
            SinhVien sv2 = new SinhVien("SV002", "Tran Thi B", "ttb@eaut.edu.vn", "CNTT1");
            sv2.setLopHoc(lop1);
            sv2.setNgaySinh(LocalDate.of(2003, 8, 20));
            sinhVienRepo.save(sv2);
            
            SinhVien sv3 = new SinhVien("SV003", "Le Van C", "lvc@eaut.edu.vn", "CNTT2");
            sv3.setLopHoc(lopHocRepo.findAll().get(1));
            sv3.setNgaySinh(LocalDate.of(2004, 1, 10));
            sinhVienRepo.save(sv3);
            
            SinhVien sv4 = new SinhVien("SV004", "Pham Thi D", "ptd@eaut.edu.vn", "CNTT2");
            sv4.setLopHoc(lopHocRepo.findAll().get(1));
            sinhVienRepo.save(sv4);
            
            SinhVien sv5 = new SinhVien("SV005", "Hoang Van E", "hve@eaut.edu.vn", "DTVT1");
            sv5.setLopHoc(lopHocRepo.findAll().get(2));
            sinhVienRepo.save(sv5);
            
            System.out.println("Da tao 5 sinh vien mau");
        }
        
        System.out.println("=== Khoi dong hoan tat ===");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("=== Lab 9 JPA Repository da dung ===");
        JPAUtil.close();
    }
}
