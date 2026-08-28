# BÁO CÁO LAB 11 - KHỞI TẠO ỨNG DỤNG SPRING BOOT VÀ GIAO DIỆN THYMELEAF

**Học phần:** Công nghệ Java
**Bài lab:** Khởi tạo ứng dụng Spring Boot và giao diện Thymeleaf
**Chương:** Chương 4 - Spring Framework
**Công nghệ:** Spring Boot, Spring MVC, Thymeleaf, Maven
**Ngày thực hiện:** 28/08/2026

---

## 1. MÔ TẢ PROJECT

### 1.1 Cấu trúc thư mục

```
lab11-springboot-thymeleaf/
├── pom.xml
└── src/main/
    ├── java/vn/edu/eaut/lab11/
    │   ├── Lab11Application.java
    │   ├── controller/
    │   │   ├── HomeController.java
    │   │   ├── StudentController.java
    │   │   └── CourseController.java
    │   └── model/
    │       ├── Student.java
    │       └── Course.java
    └── resources/
        ├── templates/
        │   ├── index.html
        │   ├── about.html
        │   ├── students.html
        │   ├── courses.html
        │   └── contact.html
        ├── static/
        │   └── css/
        │       └── style.css
        └── application.properties
```

### 1.2 Giải thích cấu trúc

| Thành phần | Ý nghĩa |
|------------|---------|
| `pom.xml` | File cấu hình Maven, khai báo dependency Spring Boot |
| `Lab11Application.java` | Lớp main khởi động ứng dụng Spring Boot |
| `controller/` | Các Controller xử lý HTTP request và trả về view |
| `model/` | Các class POJO biểu diễn dữ liệu |
| `templates/` | Chứa các file Thymeleaf (.html) render giao diện |
| `static/` | Chứa tài nguyên tĩnh (CSS, JS, image) |
| `application.properties` | File cấu hình cho Spring Boot |

### 1.3 Mô tả các lớp

| Lớp | Package | Chức năng |
|-----|---------|-----------|
| `Lab11Application` | `vn.edu.eaut.lab11` | Điểm khởi đầu của ứng dụng Spring Boot |
| `HomeController` | `vn.edu.eaut.lab11.controller` | Xử lý các URL: `/`, `/about`, `/contact` |
| `StudentController` | `vn.edu.eaut.lab11.controller` | Xử lý URL `/students` |
| `CourseController` | `vn.edu.eaut.lab11.controller` | Xử lý URL `/courses` |
| `Student` | `vn.edu.eaut.lab11.model` | Model lưu thông tin sinh viên |
| `Course` | `vn.edu.eaut.lab11.model` | Model lưu thông tin khóa học |

---

## 2. CẤU HÌNH POM.XML

```xml
<project>
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.3.0</version>
    </parent>

    <groupId>vn.edu.eaut</groupId>
    <artifactId>lab11-springboot-thymeleaf</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <properties>
        <java.version>17</java.version>
    </properties>

    <dependencies>
        <!-- Spring Web - xây dựng ứng dụng web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- Thymeleaf - template engine -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>

        <!-- DevTools - tự động reload khi thay đổi code -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>

        <!-- Spring Test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

### Giải thích các dependency

| Dependency | Chức năng |
|------------|-----------|
| `spring-boot-starter-web` | Cung cấp Spring MVC, embedded Tomcat, JSON |
| `spring-boot-starter-thymeleaf` | Tích hợp Thymeleaf template engine |
| `spring-boot-devtools` | Tự động restart khi code thay đổi |
| `spring-boot-starter-test` | Hỗ trợ unit test với JUnit 5 |

---

## 3. LỚP KHỞI ĐỘNG ỨNG DỤNG

```java
package vn.edu.eaut.lab11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Lab11Application {
    public static void main(String[] args) {
        SpringApplication.run(Lab11Application.class, args);
    }
}
```

**Giải thích:**
- `@SpringBootApplication` - đánh dấu đây là ứng dụng Spring Boot, bao gồm:
  - `@Configuration` - cho phép đăng ký bean
  - `@EnableAutoConfiguration` - tự động cấu hình
  - `@ComponentScan` - quét các component trong package
- `SpringApplication.run()` - khởi động embedded server (Tomcat)

---

## 4. CÁC CONTROLLER

### 4.1 HomeController

```java
@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Hệ thống quản lý sinh viên");
        model.addAttribute("message", "Chào mừng đến với Spring Boot");
        model.addAttribute("currentYear", java.time.Year.now().getValue());
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("course", "Công nghệ Java");
        model.addAttribute("chapter", "Chương 4 - Spring Framework");
        model.addAttribute("description", "Lab 11 giới thiệu Spring Boot");
        model.addAttribute("topics", List.of(
                "Spring Boot Starter Web",
                "Thymeleaf Template Engine",
                "Spring MVC Controller",
                "Spring Data JPA",
                "Spring Security"
        ));
        return "about";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("department", "Khoa Công nghệ thông tin");
        model.addAttribute("school", "Trường Đại học Công nghệ Đông Á");
        model.addAttribute("address", "Số 5, Phố Trịnh Văn Bô, Nam Từ Liêm, Hà Nội");
        model.addAttribute("email", "cntt@eaut.edu.vn");
        model.addAttribute("phone", "(024) 3557 7799");
        return "contact";
    }
}
```

### 4.2 StudentController

```java
@Controller
public class StudentController {

    @GetMapping("/students")
    public String listStudents(Model model) {
        List<Student> students = List.of(
                new Student("SV001", "Nguyễn Văn An", "an@eaut.edu.vn", "DCCNTT13.10.1"),
                new Student("SV002", "Trần Thị Bình", "binh@eaut.edu.vn", "DCCNTT13.10.2"),
                new Student("SV003", "Lê Văn Cường", "cuong@eaut.edu.vn", "DCCNTT13.10.3"),
                new Student("SV004", "Phạm Thị Dung", "dung@eaut.edu.vn", "DCCNTT13.10.1"),
                new Student("SV005", "Hoàng Văn Em", "em@eaut.edu.vn", "DCCNTT13.10.2")
        );
        model.addAttribute("students", students);
        model.addAttribute("title", "Danh sách sinh viên");
        return "students";
    }
}
```

### 4.3 CourseController

```java
@Controller
public class CourseController {

    @GetMapping("/courses")
    public String listCourses(Model model) {
        List<Course> courses = List.of(
                new Course("IT3242", "Công nghệ Java", 3),
                new Course("IT3201", "Cơ sở dữ liệu", 4),
                new Course("IT3101", "Lập trình Web", 3),
                new Course("IT2101", "Cấu trúc dữ liệu", 3),
                new Course("IT2202", "Mạng máy tính", 3)
        );
        model.addAttribute("courses", courses);
        model.addAttribute("title", "Danh sách khóa học");
        return "courses";
    }
}
```

---

## 5. CÁC MODEL

### 5.1 Student

```java
public class Student {
    private String studentCode;
    private String fullName;
    private String email;
    private String className;

    public Student() {}

    public Student(String studentCode, String fullName, String email, String className) {
        this.studentCode = studentCode;
        this.fullName = fullName;
        this.email = email;
        this.className = className;
    }

    // Getters and Setters
    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
}
```

### 5.2 Course

```java
public class Course {
    private String courseCode;
    private String courseName;
    private int credits;

    public Course() {}

    public Course(String courseCode, String courseName, int credits) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
    }

    // Getters and Setters
}
```

---

## 6. CÁC VIEW THYMELEAF

### 6.1 Trang chủ - index.html

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title th:text="${title}">Trang chủ</title>
    <link rel="stylesheet" th:href="@{/css/style.css}">
</head>
<body>
    <nav class="navbar">
        <a class="brand" th:href="@{/}">Spring Boot Lab</a>
        <ul class="menu">
            <li><a th:href="@{/}">Home</a></li>
            <li><a th:href="@{/about}">About</a></li>
            <li><a th:href="@{/students}">Students</a></li>
            <li><a th:href="@{/courses}">Courses</a></li>
            <li><a th:href="@{/contact}">Contact</a></li>
        </ul>
    </nav>

    <main class="container">
        <section class="hero">
            <h1 th:text="${title}">Hệ thống quản lý sinh viên</h1>
            <p th:text="${message}">Chào mừng đến với Spring Boot</p>
            <a th:href="@{/students}">Danh sách sinh viên</a>
        </section>
    </main>
</body>
</html>
```

### 6.2 Trang danh sách sinh viên - students.html

```html
<table class="data-table">
    <thead>
        <tr>
            <th>STT</th>
            <th>Mã SV</th>
            <th>Họ tên</th>
            <th>Email</th>
            <th>Lớp</th>
        </tr>
    </thead>
    <tbody>
        <tr th:each="s, iter : ${students}">
            <td th:text="${iter.count}">1</td>
            <td th:text="${s.studentCode}">SV001</td>
            <td th:text="${s.fullName}">Nguyễn Văn An</td>
            <td th:text="${s.email}">an@eaut.edu.vn</td>
            <td th:text="${s.className}">CNTT1</td>
        </tr>
    </tbody>
</table>
```

### 6.3 Trang danh sách khóa học - courses.html

```html
<table class="data-table">
    <thead>
        <tr>
            <th>STT</th>
            <th>Mã môn</th>
            <th>Tên môn</th>
            <th>Số tín chỉ</th>
        </tr>
    </thead>
    <tbody>
        <tr th:each="c, iter : ${courses}">
            <td th:text="${iter.count}">1</td>
            <td th:text="${c.courseCode}">IT001</td>
            <td th:text="${c.courseName}">Java</td>
            <td th:text="${c.credits}">3</td>
        </tr>
    </tbody>
</table>
```

---

## 7. CÁC CÚ PHÁP THYMELEAF SỬ DỤNG

| Cú pháp | Ý nghĩa | Ví dụ |
|---------|---------|-------|
| `th:text="${var}"` | Hiển thị giá trị biến | `<h1 th:text="${title}">Title</h1>` |
| `th:href="@{/url}"` | Tạo URL tương đối | `<a th:href="@{/students}">Students</a>` |
| `th:each="item : ${list}"` | Lặp qua danh sách | `<tr th:each="s : ${students}">` |
| `th:each="item, iter : ${list}"` | Lặp với biến đếm | `<td th:text="${iter.count}">` |
| `th:each="item, stat : ${list}"` | Lặp với thống kê | `stat.index, stat.count` |
| `th:if="${condition}"` | Điều kiện hiển thị | `<div th:if="${user != null}">` |
| `th:utext="${html}"` | Hiển thị HTML không escape | `<div th:utext="${content}">` |
| `th:fragment="name"` | Định nghĩa fragment tái sử dụng | `<div th:fragment="header">` |
| `th:replace="~{::fragment}"` | Thay thế fragment | `<div th:replace="~{::header}">` |
| `xmlns:th` | Khai báo namespace Thymeleaf | `<html xmlns:th="...">` |

---

## 8. CÁC LỆNH ĐÃ SỬ DỤNG

### 8.1 Kiểm tra môi trường
```bash
java -version
javac -version
mvn -version
```

### 8.2 Build project
```bash
mvn clean package
```

### 8.3 Chạy ứng dụng
```bash
mvn spring-boot:run
```

### 8.4 Truy cập ứng dụng
```
http://localhost:8080/
http://localhost:8080/about
http://localhost:8080/students
http://localhost:8080/courses
http://localhost:8080/contact
```

---

## 9. KẾT QUẢ THỰC THI

### 9.1 Minh chứng môi trường

| Lệnh | Phiên bản |
|------|-----------|
| `java -version` | Java 17+ |
| `mvn -version` | Apache Maven 3.9.x |

### 9.2 Build thành công
```
[INFO] BUILD SUCCESS
[INFO] Total time: ~30s
```

### 9.3 Ứng dụng chạy thành công
```
Started Lab11Application in 2.5 seconds
Tomcat started on port 8080
```

### 9.4 Các URL hoạt động

| URL | View | Mô tả |
|-----|------|-------|
| `/` | `index.html` | Trang chủ với hero section và 3 card giới thiệu |
| `/about` | `about.html` | Trang giới thiệu Chương 4 và danh sách topics |
| `/students` | `students.html` | Bảng danh sách 5 sinh viên |
| `/courses` | `courses.html` | Bảng danh sách 5 khóa học |
| `/contact` | `contact.html` | Thông tin liên hệ khoa |

---

## 10. LUỒNG XỬ LÝ REQUEST TRONG SPRING MVC

```
1. Client gửi HTTP request (VD: GET /students)
              ↓
3. Spring DispatcherServlet nhận request
              ↓
4. Tìm Controller phù hợp (@GetMapping("/students"))
              ↓
5. Gọi method listStudents() trong StudentController
              ↓
6. Method chuẩn bị dữ liệu, thêm vào Model
              ↓
7. Trả về tên view "students"
              ↓
8. Thymeleaf engine render file students.html
              ↓
9. Trả về HTML response cho client
```

### Cấu trúc MVC

| Thành phần | Trách nhiệm | Ví dụ |
|------------|-------------|-------|
| **Model** | Lưu trữ dữ liệu | `Student`, `Course` |
| **View** | Hiển thị giao diện | `students.html` |
| **Controller** | Xử lý request, gọi model, trả view | `StudentController` |

---

## 11. LỖI GẶP PHẢI VÀ CÁCH XỬ LÝ

| Lỗi | Nguyên nhân | Cách xử lý |
|------|-------------|------------|
| Trang trắng, không hiển thị | Sai package controller | Đảm bảo controller nằm cùng package hoặc sub-package của main app |
| Template not found | File .html không đặt trong `templates/` | Đặt đúng vị trí `src/main/resources/templates/` |
| CSS không load | Sai đường dẫn `th:href` | Dùng `@{/css/style.css}` thay vì đường dẫn tuyệt đối |
| Biến `null` trong view | Controller chưa `addAttribute` | Thêm `model.addAttribute()` trước khi return |
| Port 8080 đã dùng | Ứng dụng khác đang chạy | Đổi `server.port` trong `application.properties` |
| Không tự reload khi sửa code | Thiếu DevTools | Thêm dependency `spring-boot-devtools` |

---

## 12. KẾT LUẬN

Qua bài lab 11, đã nắm được:

- **Spring Boot**: Cách tạo project Spring Boot bằng Maven, cấu hình dependency trong `pom.xml`
- **Spring MVC**: Controller trả về view thông qua `@Controller` và `@GetMapping`
- **Model**: Truyền dữ liệu từ Controller sang View thông qua `Model` object
- **Thymeleaf**: Sử dụng `th:text`, `th:href`, `th:each` để render giao diện động
- **Project structure**: Tổ chức code theo package `vn.edu.eaut.lab11`
- **Embedded server**: Chạy ứng dụng trên Tomcat nhúng, port 8080
- **DevTools**: Tự động reload khi sửa code

**Sản phẩm:** Ứng dụng Spring Boot chạy tại `http://localhost:8080` với:
- 5 trang: Home, About, Students, Courses, Contact
- 5 URL mapping
- 5 bài tập có code gợi ý hoàn thành
- 5 bài tập tự làm (Contact, Menu, 5 khóa học, /courses, CSS)