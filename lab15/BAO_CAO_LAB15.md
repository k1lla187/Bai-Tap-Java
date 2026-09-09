# BÁO CÁO BÀI TẬP LỚN LAB 15: PHÁT TRIỂN ỨNG DỤNG VỚI SPRING FRAMEWORK

**Học phần**: Công nghệ Java  
**Chương 4**: Phát triển ứng dụng với Spring Framework  
**Tên bài tập**: Xây dựng ứng dụng hoàn chỉnh với Spring Framework  
**Tên project**: `lab15-spring-final-project`  
**Package gốc**: `vn.edu.eaut.lab15`  
**Trường**: Đại học Công nghệ Đông Á (EAUT)

---

## 1. MỤC TIÊU VÀ YÊU CẦU ĐỀ TÀI

### 1.1. Mục tiêu
1. Tích hợp toàn diện các kiến thức trọng tâm đã học trong Chương 4: Spring Boot, Spring MVC, Thymeleaf, Spring Data JPA và Spring Security.
2. Thiết kế và cài đặt ứng dụng theo kiến trúc chuẩn nhiều tầng: **Controller - Service - Repository - Entity**.
3. Xây dựng trọn vẹn nghiệp vụ CRUD cho nhiều Entity và xử lý mối quan hệ nhiều - nhiều (N - N) có thuộc tính phụ.
4. Áp dụng cơ chế xác thực người dùng (Authentication) và phân quyền chức năng (Authorization) chặt chẽ bằng Spring Security 6.
5. Tạo giao diện người dùng trực quan, responsive bằng Bootstrap 5, thông báo trạng thái thao tác rõ ràng.

### 1.2. Công nghệ sử dụng
- **Ngôn ngữ & Môi trường**: Java 17 / JDK 21, Apache Maven 3.9.x
- **Framework**: Spring Boot 3.2.0
- **Tầng Web / MVC**: Spring MVC, Thymeleaf Template Engine, Thymeleaf Spring Security 6 dialect
- **Tầng Dữ liệu**: Spring Data JPA, Hibernate ORM
- **Cơ sở dữ liệu**: H2 Database In-Memory (có sẵn cấu hình MySQL linh hoạt)
- **Bảo mật**: Spring Security 6 (BCrypt Password Encoder)
- **Giao diện**: HTML5, Vanilla CSS, Bootstrap 5.3.3, Font Awesome 6.5.1

---

## 2. THIẾT KẾ CƠ SỞ DỮ LIỆU & QUAN HỆ N-N

Mô hình dữ liệu giải quyết bài toán Đăng ký học phần với mối quan hệ Nhiều - Nhiều (N - N) giữa **Sinh viên (Student)** và **Môn học (Course)** thông qua thực thể kết hợp **Lượt đăng ký (Enrollment)**:

### 2.1. Bảng `students` (Sinh viên)
| Cột | Kiểu dữ liệu | Ràng buộc | Mô tả |
| :--- | :--- | :--- | :--- |
| `id` | `BIGINT` | PRIMARY KEY, AUTO_INCREMENT | Khóa chính |
| `student_code` | `VARCHAR(20)` | NOT NULL, UNIQUE | Mã sinh viên (VD: SV001) |
| `full_name` | `VARCHAR(100)` | NOT NULL | Họ và tên sinh viên |
| `date_of_birth` | `DATE` | NOT NULL | Ngày sinh |

### 2.2. Bảng `courses` (Môn học / Học phần)
| Cột | Kiểu dữ liệu | Ràng buộc | Mô tả |
| :--- | :--- | :--- | :--- |
| `id` | `BIGINT` | PRIMARY KEY, AUTO_INCREMENT | Khóa chính |
| `course_code` | `VARCHAR(20)` | NOT NULL, UNIQUE | Mã học phần (VD: CS101) |
| `course_name` | `VARCHAR(150)` | NOT NULL | Tên môn học |
| `credits` | `INT` | NOT NULL | Số tín chỉ đào tạo |

### 2.3. Bảng `enrollments` (Lượt đăng ký học phần)
| Cột | Kiểu dữ liệu | Ràng buộc | Mô tả |
| :--- | :--- | :--- | :--- |
| `id` | `BIGINT` | PRIMARY KEY, AUTO_INCREMENT | Khóa chính |
| `enroll_date` | `DATE` | NOT NULL | Ngày thực hiện đăng ký |
| `student_id` | `BIGINT` | NOT NULL, FOREIGN KEY -> `students(id)` | Khóa ngoại trỏ đến Sinh viên |
| `course_id` | `BIGINT` | NOT NULL, FOREIGN KEY -> `courses(id)` | Khóa ngoại trỏ đến Môn học |
| **Ràng buộc duy nhất** | `UNIQUE(student_id, course_id)` | Ngăn chặn sinh viên đăng ký trùng 1 môn học |

---

## 3. BÁO CÁO THỰC HIỆN CHI TIẾT 10 BÀI TẬP

### 🔹 Bài 1: Thiết kế Entity Course
- File: `vn.edu.eaut.lab15.entity.Course`
- Khai báo các thuộc tính `id`, `courseCode`, `courseName`, `credits` kèm các annotation JPA `@Entity`, `@Table(name = "courses")`, `@Id`, `@GeneratedValue`.
- Bổ sung Bean Validation: `@NotBlank`, `@Size`, `@Min(1)`.
- Thiết lập liên kết `@OneToMany(mappedBy = "course")` tới `Enrollment`.

### 🔹 Bài 2: Thiết kế Entity Enrollment
- File: `vn.edu.eaut.lab15.entity.Enrollment`
- Đại diện cho bảng trung gian có thuộc tính ngày đăng ký `enrollDate`.
- Thiết lập liên kết `@ManyToOne` với `Student` qua `@JoinColumn(name = "student_id")`.
- Thiết lập liên kết `@ManyToOne` với `Course` qua `@JoinColumn(name = "course_id")`.
- Thiết lập ràng buộc độc nhất `@UniqueConstraint(columnNames = {"student_id", "course_id"})`.

### 🔹 Bài 3: Tạo Repository cho đăng ký học phần
- File: `vn.edu.eaut.lab15.repository.EnrollmentRepository`
- Kế thừa `JpaRepository<Enrollment, Long>`.
- Khai báo các phương thức truy vấn chuẩn Spring Data:
  - `List<Enrollment> findByStudentId(Long studentId);`: Tìm tất cả môn đã đăng ký của 1 sinh viên.
  - `boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);`: Kiểm tra sinh viên đã đăng ký môn hay chưa.
  - `List<Enrollment> findAllByOrderByEnrollDateDesc();`: Lấy toàn bộ danh sách xếp theo ngày mới nhất.

### 🔹 Bài 4: Tạo Service đăng ký học phần
- File: `vn.edu.eaut.lab15.service.EnrollmentService`
- Phương thức `enroll(Long studentId, Long courseId)` thực hiện:
  1. Gọi `enrollmentRepository.existsByStudentIdAndCourseId()` để kiểm tra trùng. Nếu đã đăng ký thì ném `IllegalArgumentException("Sinh viên đã đăng ký môn học này trước đó!")`.
  2. Tìm thực thể `Student` và `Course` từ ID tương ứng.
  3. Tạo đối tượng `Enrollment`, gán ngày hiện tại `LocalDate.now()` và lưu vào CSDL.

### 🔹 Bài 5: Controller đăng ký học phần
- File: `vn.edu.eaut.lab15.controller.EnrollmentController`
- `@GetMapping("/create")`: Lấy toàn bộ danh sách `students` và `courses` truyền vào `Model` để render form dropdown.
- `@PostMapping("/save")`: Nhận `studentId` và `courseId` từ request form, gọi service đăng ký, dùng `RedirectAttributes.addFlashAttribute` để hiển thị thông báo thành công hoặc lỗi trên giao diện.

### 🔹 Bài 6: Xây dựng trang danh sách đăng ký học phần
- View: `src/main/resources/templates/enrollments/list.html`
- Hiển thị bảng danh sách đầy đủ: STT, Tên & Mã sinh viên, Tên & Mã môn học, Số tín chỉ, Ngày đăng ký.
- Cung cấp tính năng lọc nhanh theo từng sinh viên.

### 🔹 Bài 7: Chức năng hủy đăng ký học phần
- Service: `enrollmentService.cancel(Long id)` kiểm tra tồn tại và xóa bản ghi khỏi bảng `enrollments`.
- Controller: Endpoint `GET /enrollments/cancel/{id}` gọi service hủy và chuyển hướng về danh sách kèm flash alert thông báo hủy thành công.
- View: Nút "Hủy ĐK" gắn hộp thoại JavaScript xác nhận `confirm()` bảo vệ thao tác.

### 🔹 Bài 8: Xem danh sách môn học mà một sinh viên đã đăng ký
- Controller: Endpoint `GET /enrollments/student/{studentId}`.
- View: `src/main/resources/templates/enrollments/student-history.html`.
- Hiển thị:
  - Card thông tin sinh viên (Mã SV, họ tên, ngày sinh).
  - Thống kê tổng số môn và **tính tổng số tín chỉ tích lũy** (`totalCredits`) bằng Java Stream API.
  - Bảng danh sách các môn đã đăng ký thành công kèm nút hủy môn trực tiếp.

### 🔹 Bài 9: Dashboard thống kê tổng số
- Controller: `vn.edu.eaut.lab15.controller.DashboardController` tại URL `/dashboard` và `/`.
- View: `src/main/resources/templates/dashboard.html`.
- Thống kê thời gian thực:
  - **Tổng số sinh viên**: `studentService.count()`
  - **Tổng số môn học**: `courseService.count()`
  - **Tổng số lượt đăng ký**: `enrollmentService.count()`
  - **Danh sách 5 lượt đăng ký gần nhất** kèm bảng phím tắt thao tác nhanh (Quick Actions).

### 🔹 Bài 10: Hoàn thiện giao diện, thông báo & Phân quyền bảo mật
- Cấu hình: `vn.edu.eaut.lab15.config.SecurityConfig` với Spring Security 6.
- Định nghĩa 2 tài khoản:
  - `admin` (mật khẩu `admin123`, quyền `ROLE_ADMIN`).
  - `user` (mật khẩu `user123`, quyền `ROLE_USER`).
- Phân quyền URL:
  - Các route thêm, sửa, xóa sinh viên và khóa học (`/students/new`, `/students/edit/**`, `/students/delete/**`, `/courses/new`, ...) yêu cầu quyền `ADMIN`.
  - Các route xem danh sách, đăng ký và hủy môn học dành cho cả `ADMIN` và `USER`.
  - Truy cập trái phép tự động điều hướng sang trang `error/403.html`.
- Giao diện:
  - Layout dùng chung `layout.html` tích hợp Navbar responsive, Badge hiển thị tên người dùng và vai trò hiện tại, Nút đăng xuất (Logout).
  - Form đăng nhập tùy biến `login.html` với thẻ hướng dẫn tài khoản demo.
  - Thẻ thông báo Toast/Alert (Success & Error) hiển thị trực quan.

---

## 4. MA TRẬN PHÂN QUYỀN HỆ THỐNG

| Chức năng / URL | Khách (Chưa Login) | Người dùng (USER) | Quản trị viên (ADMIN) |
| :--- | :---: | :---: | :---: |
| Trang đăng nhập (`/login`) | ✅ Cho phép | ✅ Cho phép | ✅ Cho phép |
| Dashboard (`/dashboard`) | ❌ Chuyển sang login | ✅ Truy cập | ✅ Truy cập |
| Xem danh sách sinh viên (`/students`) | ❌ Chuyển sang login | ✅ Xem | ✅ Xem |
| Thêm/Sửa/Xóa sinh viên (`/students/new`, `edit`, `delete`) | ❌ Chuyển sang login | ⛔ 403 Forbidden | ✅ Toàn quyền |
| Xem danh mục khóa học (`/courses`) | ❌ Chuyển sang login | ✅ Xem | ✅ Xem |
| Thêm/Sửa/Xóa khóa học (`/courses/new`, `edit`, `delete`) | ❌ Chuyển sang login | ⛔ 403 Forbidden | ✅ Toàn quyền |
| Form đăng ký học phần (`/enrollments/create`) | ❌ Chuyển sang login | ✅ Đăng ký | ✅ Đăng ký |
| Danh sách đăng ký (`/enrollments`) | ❌ Chuyển sang login | ✅ Xem | ✅ Xem |
| Hủy đăng ký học phần (`/enrollments/cancel/{id}`) | ❌ Chuyển sang login | ✅ Thực hiện | ✅ Thực hiện |
| Xem môn đã đăng ký của SV (`/enrollments/student/{id}`) | ❌ Chuyển sang login | ✅ Xem | ✅ Xem |

---

## 5. KẾT QUẢ KIỂM THỬ VÀ ĐÁNH GIÁ

### 5.1. Kiểm thử tự động (Unit & Integration Tests)
- Toàn bộ 6 bài kiểm thử tự động trong `Lab15ApplicationTests` và `EnrollmentServiceTest` đều chạy thành công:
  ```
  [INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
  [INFO] BUILD SUCCESS
  [INFO] Total time: 20.718 s
  ```
- Các ca kiểm thử bao gồm: Khởi động context, Thao tác đăng ký thành công, Bắt lỗi đăng ký trùng lặp, Thao tác hủy đăng ký, Tra cứu theo mã sinh viên, và Thống kê tổng số lượng.

### 5.2. Khởi tạo dữ liệu mẫu (DataInitializer)
- Hệ thống tự động nạp 6 sinh viên mẫu (`SV001` - `SV006`), 5 khóa học mẫu (`CS101` - `CS105`), và 6 lượt đăng ký ban đầu ngay khi chạy lần đầu.

---

## 6. KẾT LUẬN

Dự án bài tập lớn **Lab 15** đã được hoàn thành **100%** theo đúng tất cả các yêu cầu:
1. Đầy đủ mã nguồn, đúng cấu trúc chuẩn MVC nhiều tầng.
2. Xử lý chính xác mối quan hệ N - N với Entity kết hợp `Enrollment`.
3. Bảo mật toàn diện với Spring Security 6 và phân quyền `ADMIN` / `USER`.
4. Giao diện người dùng hiện đại, tinh gọn với Bootstrap 5 và Thymeleaf.
5. Sẵn sàng chạy và nộp bài theo quy định.
