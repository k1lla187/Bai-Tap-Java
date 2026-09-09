# 🎓 BÀI TẬP LỚN LAB 15: XÂY DỰNG ỨNG DỤNG HOÀN CHỈNH VỚI SPRING FRAMEWORK

**Học phần**: Công nghệ Java - Chương 4  
**Tên dự án**: `lab15-spring-final-project`  
**Package gốc**: `vn.edu.eaut.lab15`  
**Đơn vị đào tạo**: Khoa Công nghệ Thông tin - Trường Đại học Công nghệ Đông Á (EAUT)

---

## 📌 1. Giới Thiệu Dự Án

Dự án là một **Hệ thống Quản lý Sinh viên và Đăng ký Học phần** hoàn chỉnh được xây dựng trên nền tảng **Spring Boot 3.2.0** theo mô hình kiến trúc nhiều tầng tiêu chuẩn (**Controller - Service - Repository - Entity**).

### Công nghệ sử dụng:
- **Core**: Spring Boot 3.2.0, Java 17/21
- **Web MVC**: Spring MVC, Thymeleaf Template Engine
- **Data & Persistence**: Spring Data JPA, Hibernate ORM
- **Database**: H2 In-Memory Database (mặc định sẵn sàng chạy ngay) & Hỗ trợ chuyển đổi nhanh sang MySQL
- **Bảo mật**: Spring Security 6, Thymeleaf Security Extras (BCrypt Password Encoder)
- **Giao diện**: Bootstrap 5.3, Font Awesome 6, Custom Modern CSS

---

## 📋 2. Hoàn Thành 10 Bài Tập Yêu Cầu

| Bài tập | Nội dung | File thực thi chính |
| :---: | :--- | :--- |
| **Bài 1** | Thiết kế entity `Course` | `entity/Course.java` |
| **Bài 2** | Thiết kế entity `Enrollment` (Quan hệ N-N có thuộc tính) | `entity/Enrollment.java` |
| **Bài 3** | Tạo `EnrollmentRepository` (`findByStudentId`, `existsByStudentIdAndCourseId`) | `repository/EnrollmentRepository.java` |
| **Bài 4** | Tạo `EnrollmentService` (xử lý đăng ký, chống trùng lặp) | `service/EnrollmentService.java` |
| **Bài 5** | Tạo `EnrollmentController` (`/enrollments/create`, `/enrollments/save`) | `controller/EnrollmentController.java` |
| **Bài 6** | Xây dựng trang danh sách đăng ký học phần | `templates/enrollments/list.html` |
| **Bài 7** | Viết chức năng hủy đăng ký học phần | `service/EnrollmentService.java`, route `/enrollments/cancel/{id}` |
| **Bài 8** | Xem danh sách môn học mà 1 sinh viên đã đăng ký (tính tổng tín chỉ) | `controller/EnrollmentController.java`, `templates/enrollments/student-history.html` |
| **Bài 9** | Dashboard thống kê tổng số SV, tổng số môn học, tổng số lượt đăng ký | `controller/DashboardController.java`, `templates/dashboard.html` |
| **Bài 10** | Hoàn thiện giao diện, thông báo & Phân quyền Spring Security (ADMIN/USER) | `config/SecurityConfig.java`, `templates/fragments/layout.html`, `templates/login.html`, `403.html` |

---

## 🏛️ 3. Cấu Trúc Mã Nguồn

```
lab15-spring-final-project/
├── pom.xml
├── README.md
├── BAO_CAO_LAB15.md
└── src/
    ├── main/
    │   ├── java/vn/edu/eaut/lab15/
    │   │   ├── Lab15Application.java         # Điểm khởi chạy ứng dụng
    │   │   ├── config/
    │   │   │   ├── SecurityConfig.java       # Phân quyền Spring Security 6
    │   │   │   └── DataInitializer.java      # Nạp dữ liệu mẫu ban đầu
    │   │   ├── entity/
    │   │   │   ├── Student.java              # Entity Sinh viên
    │   │   │   ├── Course.java               # Entity Khóa học (Bài 1)
    │   │   │   └── Enrollment.java           # Entity Đăng ký (Bài 2)
    │   │   ├── repository/
    │   │   │   ├── StudentRepository.java
    │   │   │   ├── CourseRepository.java
    │   │   │   └── EnrollmentRepository.java # Repository Đăng ký (Bài 3)
    │   │   ├── service/
    │   │   │   ├── StudentService.java       # Nghiệp vụ Sinh viên
    │   │   │   ├── CourseService.java        # Nghiệp vụ Khóa học
    │   │   │   └── EnrollmentService.java    # Nghiệp vụ Đăng ký (Bài 4, 7, 8, 9)
    │   │   └── controller/
    │   │       ├── DashboardController.java  # Dashboard (Bài 9)
    │   │       ├── StudentController.java    # CRUD Sinh viên
    │   │       ├── CourseController.java     # CRUD Khóa học
    │   │       ├── EnrollmentController.java # Luồng Đăng ký (Bài 5, 6, 7, 8)
    │   │       └── AuthController.java        # Login & 403
    │   └── resources/
    │       ├── application.properties        # Cấu hình H2 / MySQL
    │       ├── static/css/style.css          # CSS giao diện
    │       └── templates/
    │           ├── fragments/layout.html     # Header, Navbar, Alert, Footer
    │           ├── dashboard.html            # Dashboard thống kê
    │           ├── login.html                # Form đăng nhập
    │           ├── students/ (list, form)    # CRUD Sinh viên
    │           ├── courses/ (list, form)     # CRUD Khóa học
    │           ├── enrollments/
    │           │   ├── list.html             # Danh sách đăng ký (Bài 6 & 7)
    │           │   ├── form.html             # Form đăng ký mới (Bài 5)
    │           │   └── student-history.html  # Lịch sử môn theo SV (Bài 8)
    │           └── error/403.html            # Trang từ chối truy cập
    └── test/
        └── java/vn/edu/eaut/lab15/
            ├── Lab15ApplicationTests.java
            └── service/EnrollmentServiceTest.java # Bộ test 5 kịch bản tự động
```

---

## 🔐 4. Thông Tin Tài Khoản Đăng Nhập

| Tài khoản | Mật khẩu | Vai trò (Role) | Quyền hạn trong hệ thống |
| :---: | :---: | :---: | :--- |
| **`admin`** | `admin123` | **`ADMIN`** | Toàn quyền: Thêm, sửa, xóa sinh viên; Thêm, sửa, xóa khóa học; Đăng ký và hủy học phần; Xem dashboard. |
| **`user`** | `user123` | **`USER`** | Quyền xem và đăng ký: Xem danh sách sinh viên, xem khóa học, thực hiện đăng ký và hủy đăng ký học phần, xem dashboard. Không được phép thêm/sửa/xóa sinh viên hay khóa học. |

---

## 🚀 5. Hướng Dẫn Chạy Ứng Dụng

### Bước 1: Mở Terminal tại thư mục dự án
```powershell
cd d:\Bai-Tap-Java\lab15\lab15-spring-final-project
```

### Bước 2: Chạy kiểm thử tự động
```powershell
mvn test
```
*(Kết quả: 6/6 bài test thành công, 0 failures, 0 errors)*

### Bước 3: Khởi động ứng dụng
```powershell
mvn spring-boot:run
```

### Bước 4: Truy cập ứng dụng
- **Trang chủ / Đăng nhập**: [http://localhost:8080](http://localhost:8080)
- **H2 Database Console**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
  - JDBC URL: `jdbc:h2:mem:lab15db`
  - User Name: `sa`
  - Password: *(để trống)*
