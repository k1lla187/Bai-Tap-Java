package vn.edu.eaut.lab15;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Lớp khởi chạy ứng dụng Spring Boot - Lab 15:
 * Hệ thống Quản lý sinh viên và Đăng ký học phần.
 */
@SpringBootApplication
public class Lab15Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab15Application.class, args);
        System.out.println("===============================================================");
        System.out.println("   ỨNG DỤNG LAB 15 ĐÃ KHỞI ĐỘNG THÀNH CÔNG TẠI:");
        System.out.println("   -> URL chính:       http://localhost:8080");
        System.out.println("   -> H2 Console:      http://localhost:8080/h2-console");
        System.out.println("   -> Tài khoản ADMIN: admin / admin123");
        System.out.println("   -> Tài khoản USER:  user  / user123");
        System.out.println("===============================================================");
    }
}
