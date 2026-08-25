# BÁO CÁO LAB 10 - BẢO MẬT ỨNG DỤNG WEB VỚI XÁC THỰC VÀ PHÂN QUYỀN

**Học phần:** Công nghệ Java
**Bài lab:** Xây dựng ứng dụng web bảo mật với xác thực (Authentication) và phân quyền (Authorization)
**Ngày thực hiện:** 25/08/2026

---

## 1. MÔ TẢ PROJECT

### 1.1 Cấu trúc thư mục

```
lab10-secured-app/
├── pom.xml
├── src/
│   └── main/
│       ├── java/
│       │   └── vn/edu/eaut/lab10/
│       │       ├── config/
│       │       │   └── JPAUtil.java
│       │       ├── controller/
│       │       │   ├── AuthController.java
│       │       │   ├── UserController.java
│       │       │   ├── ProfileController.java
│       │       │   ├── SinhVienController.java
│       │       │   ├── MonHocController.java
│       │       │   ├── GiaoVienController.java
│       │       │   ├── LopController.java
│       │       │   ├── DiemController.java
│       │       │   ├── DiemDanhController.java
│       │       │   └── ThongKeController.java
│       │       ├── filter/
│       │       │   ├── AuthenticationFilter.java
│       │       │   └── AuthorizationFilter.java
│       │       ├── listener/
│       │       │   └── AppListener.java
│       │       ├── model/
│       │       │   ├── User.java
│       │       │   ├── Role.java
│       │       │   ├── SinhVien.java
│       │       │   ├── MonHoc.java
│       │       │   ├── GiaoVien.java
│       │       │   ├── Lop.java
│       │       │   ├── Diem.java
│       │       │   ├── DiemDanh.java
│       │       │   └── LoginLog.java
│       │       ├── repository/
│       │       │   ├── UserRepository.java
│       │       │   ├── SinhVienRepository.java
│       │       │   ├── MonHocRepository.java
│       │       │   ├── GiaoVienRepository.java
│       │       │   ├── LopRepository.java
│       │       │   ├── DiemRepository.java
│       │       │   ├── DiemDanhRepository.java
│       │       │   └── LoginLogRepository.java
│       │       └── service/
│       │           ├── AuthService.java
│       │           └── UserManagementService.java
│       └── webapp/
│           ├── login.jsp
│           ├── dashboard.jsp
│           ├── error/
│           │   ├── 403.jsp
│           │   ├── 404.jsp
│           │   └── 500.jsp
│           ├── admin/
│           │   ├── users.jsp
│           │   └── user-form.jsp
│           ├── staff/
│           │   ├── index.jsp
│           │   ├── sinhvien-list.jsp
│           │   ├── sinhvien-form.jsp
│           │   ├── monhoc-list.jsp
│           │   ├── monhoc-form.jsp
│           │   ├── giaovien-list.jsp
│           │   ├── giaovien-form.jsp
│           │   ├── lop-list.jsp
│           │   ├── lop-form.jsp
│           │   ├── diem-list.jsp
│           │   ├── diem-form.jsp
│           │   ├── diemdanh-list.jsp
│           │   ├── diemdanh-form.jsp
│           │   └── thongke.jsp
│           ├── user/
│           │   └── profile.jsp
│           └── WEB-INF/
│               └── web.xml
```

### 1.2 Giải thích cấu trúc

| Thành phần | Ý nghĩa |
|------------|---------|
| `config/` | Cấu hình kết nối JPA/Hibernate với database |
| `controller/` | Các Servlet xử lý request theo từng module nghiệp vụ |
| `filter/` | Filter xử lý Authentication và Authorization |
| `listener/` | ServletContextListener khởi tạo dữ liệu mẫu khi ứng dụng start |
| `model/` | Các Entity class ánh xạ với bảng trong database |
| `repository/` | Các lớp truy cập dữ liệu (CRUD) |
| `service/` | Lớp xử lý nghiệp vụ logic |
| `webapp/` | Thư mục chứa JSP, CSS, HTML |

---

## 2. MÔ HÌNH PHÂN QUYỀN

### 2.1 Các vai trò (Role)

| Vai trò | Mã | Quyền hạn |
|---------|-----|-----------|
| Quản trị viên | `ADMIN` | Toàn quyền: CRUD tất cả module |
| Nhân viên | `STAFF` | Xem, Thêm, Sửa các module nghiệp vụ |
| Người dùng | `USER` | Chỉ xem và chỉnh sửa hồ sơ cá nhân |

### 2.2 Sơ đồ luồng xác thực và phân quyền

```
Client Request
      │
      ▼
┌─────────────────────┐
│ AuthenticationFilter │ ◄── Kiểm tra đã đăng nhập chưa
│   (Kiểm tra Session) │
└──────────┬──────────┘
           │
     ┌─────┴─────┐
     │  Chưa login │ ──► Redirect /login.jsp
     │  Đã login   │
     └─────┬─────┘
           ▼
┌─────────────────────┐
│ AuthorizationFilter │ ◄── Kiểm tra quyền theo URL
│  (Kiểm tra Role)    │
└──────────┬──────────┘
           │
     ┌─────┴─────┐
     │ Không đủ  │ ──► Redirect /error/403.jsp
     │ quyền     │
     │ Đủ quyền  │
     └─────┬─────┘
           ▼
    Controller xử lý
```

---

## 3. CẤU HÌNH POM.XML

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>vn.edu.eaut</groupId>
    <artifactId>lab10-secured-app</artifactId>
    <version>1.0.0</version>
    <packaging>war</packaging>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <jakartaee.version>10.0.0</jakartaee.version>
        <hibernate.version>6.4.4.Final</hibernate.version>
    </properties>

    <dependencies>
        <!-- Jakarta EE API -->
        <dependency>
            <groupId>jakarta.platform</groupId>
            <artifactId>jakarta.jakartaee-api</artifactId>
            <version>${jakartaee.version}</version>
            <scope>provided</scope>
        </dependency>

        <!-- JSTL -->
        <dependency>
            <groupId>org.glassfish.web</groupId>
            <artifactId>jakarta.servlet.jsp.jstl</artifactId>
            <version>3.0.1</version>
        </dependency>

        <!-- Hibernate ORM -->
        <dependency>
            <groupId>org.hibernate.orm</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>${hibernate.version}</version>
        </dependency>

        <!-- MySQL Driver -->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>8.3.0</version>
        </dependency>

        <!-- BCrypt for password hashing -->
        <dependency>
            <groupId>org.mindrot</groupId>
            <artifactId>jbcrypt</artifactId>
            <version>0.4</version>
        </dependency>
    </dependencies>

    <build>
        <finalName>lab10-secured-app</finalName>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-war-plugin</artifactId>
                <version>3.4.0</version>
            </plugin>
            <plugin>
                <groupId>org.eclipse.jetty</groupId>
                <artifactId>jetty-maven-plugin</artifactId>
                <version>11.0.20</version>
                <configuration>
                    <webApp>
                        <contextPath>/lab10</contextPath>
                    </webApp>
                    <httpConnector>
                        <port>8083</port>
                    </httpConnector>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

---

## 4. CÁC LỚP FILTER

### 4.1 AuthenticationFilter

Filter này kiểm tra người dùng đã đăng nhập hay chưa bằng cách kiểm tra session.

```java
@WebFilter(urlPatterns = {"/admin/*", "/staff/*", "/user/*"})
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        chain.doFilter(request, response);
    }
}
```

**Logic:**
- Áp dụng cho: `/admin/*`, `/staff/*`, `/user/*`
- Nếu chưa có session hoặc session không chứa `currentUser` → chuyển hướng về login
- Nếu đã đăng nhập → cho phép tiếp tục request

### 4.2 AuthorizationFilter

Filter này kiểm tra vai trò của người dùng có đủ quyền truy cập tài nguyên hay không.

```java
@WebFilter(urlPatterns = {"/admin/*", "/staff/*"})
public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("currentUser");

        String path = request.getRequestURI();
        boolean authorized = false;

        if (path.contains("/admin/")) {
            authorized = user.getRole() == Role.ADMIN;
        } else if (path.contains("/staff/")) {
            authorized = user.getRole() == Role.ADMIN || user.getRole() == Role.STAFF;
        }

        if (!authorized) {
            response.sendRedirect(request.getContextPath() + "/error/403.jsp");
            return;
        }

        chain.doFilter(request, response);
    }
}
```

**Logic:**
- URL `/admin/*`: Chỉ ADMIN được phép
- URL `/staff/*`: ADMIN và STAFF được phép
- Người dùng thường (`USER`) không được truy cập → trả về lỗi 403

---

## 5. CÁC LỚP MODEL

### 5.1 User Entity

```java
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;  // Lưu BCrypt hash

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Column(length = 50)
    private String phone;

    private boolean active = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        // Hash password nếu chưa được hash
        if (password != null && !password.startsWith("$2")) {
            this.password = BCrypt.hashpw(password, BCrypt.gensalt(10));
        }
    }
}
```

### 5.2 Role Enum

```java
public enum Role {
    ADMIN,   // Quản trị viên
    STAFF,   // Nhân viên
    USER     // Người dùng thường
}
```

### 5.3 LoginLog Entity (Ghi log đăng nhập)

```java
@Entity
@Table(name = "login_logs")
public class LoginLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String action;  // LOGIN, LOGOUT, LOGIN_FAILED

    private String ipAddress;

    @Column(length = 500)
    private String userAgent;

    private LocalDateTime createdAt;
}
```

---

## 6. SERVICE VÀ CONTROLLER

### 6.1 AuthService - Xử lý đăng nhập

```java
public class AuthService {

    private final UserRepository userRepository = new UserRepository();

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null || !user.isActive()) {
            return null;
        }
        // BCrypt check: hỗ trợ cả bcrypt và plain-text (cho dữ liệu seed)
        if (BCrypt.checkpw(password, user.getPassword())
                || user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(10));
    }

    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (hashedPassword.startsWith("$2")) {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        }
        return plainPassword.equals(hashedPassword);
    }
}
```

### 6.2 AuthController - Xử lý request login/logout

```java
@WebServlet(name = "AuthController", urlPatterns = {"/auth"})
public class AuthController extends HttpServlet {

    private final AuthService authService = new AuthService();
    private final LoginLogRepository logRepo = new LoginLogRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = authService.login(email, password);

        if (user == null) {
            // Ghi log đăng nhập thất bại
            logAction(null, email, "LOGIN_FAILED", getClientIP(request));
            request.setAttribute("error", "Email hoac mat khau khong dung");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        // Ghi log đăng nhập thành công
        logAction(user.getId(), email, "LOGIN", getClientIP(request));

        // Tạo session và lưu thông tin user
        HttpSession session = request.getSession(true);
        session.setAttribute("currentUser", user);
        session.setAttribute("userId", user.getId());
        session.setAttribute("userEmail", user.getEmail());
        session.setAttribute("userRole", user.getRole().name());
        session.setAttribute("userName", user.getFullName());

        response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if ("logout".equals(request.getParameter("action"))) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                User user = (User) session.getAttribute("currentUser");
                if (user != null) {
                    logAction(user.getId(), user.getEmail(), "LOGOUT",
                            getClientIP(request));
                }
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }
}
```

### 6.3 UserController - Quản lý người dùng (Chỉ ADMIN)

```java
@WebServlet(name = "UserController", urlPatterns = {"/admin/users"})
public class UserController extends HttpServlet {

    private final UserManagementService userService = new UserManagementService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("users", userService.getAllUsers());
        request.getRequestDispatcher("/admin/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String role = request.getParameter("role");

        if ("create".equals(action)) {
            userService.createUser(email, password, fullName, Role.valueOf(role));
        } else if ("update".equals(action)) {
            Integer id = Integer.parseInt(request.getParameter("id"));
            userService.updateUser(id, email, fullName, Role.valueOf(role));
        } else if ("delete".equals(action)) {
            Integer id = Integer.parseInt(request.getParameter("id"));
            userService.deleteUser(id);
        }

        response.sendRedirect(request.getContextPath() + "/admin/users");
    }
}
```

---

## 7. LISTENER - KHỞI TẠO DỮ LIỆU MẪU

```java
@WebListener
public class AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if (userRepo.count() == 0) {
            seedUsers();
            seedBusinessData();
        }
    }

    private void seedUsers() {
        User admin = new User("admin@eaut.edu.vn", "admin123", "Quan Tri Vien", Role.ADMIN);
        User staff = new User("staff@eaut.edu.vn", "staff123", "Nhan Vien Nguyen", Role.STAFF);
        User user = new User("user@eaut.edu.vn", "user123", "Nguoi Dung Le", Role.USER);
        // Lưu vào database
    }
}
```

---

## 8. CÁC TRANG JSP

### 8.1 Trang Login (login.jsp)

```jsp
<form method="post" action="${pageContext.request.contextPath}/auth">
    <div class="form-group">
        <label for="email">Email</label>
        <input type="email" id="email" name="email" required>
    </div>
    <div class="form-group">
        <label for="password">Mat khau</label>
        <input type="password" id="password" name="password" required>
    </div>
    <button type="submit" class="btn-login">Dang Nhap</button>
</form>
```

### 8.2 Dashboard (dashboard.jsp)

```jsp
<c:if test="${sessionScope.userRole == 'ADMIN'}">
    <a href="${pageContext.request.contextPath}/admin/users">Quan ly tai khoan</a>
</c:if>

<c:if test="${sessionScope.userRole == 'STAFF' || sessionScope.userRole == 'ADMIN'}">
    <a href="${pageContext.request.contextPath}/staff/index">Module nghiep vu</a>
</c:if>

<a href="${pageContext.request.contextPath}/user/profile">Ho so ca nhan</a>
```

### 8.3 Trang lỗi 403 (error/403.jsp)

```jsp
<h1>403 - Forbidden</h1>
<p>Ban khong co quyen truy cap trang nay.</p>
<a href="${pageContext.request.contextPath}/dashboard.jsp">Ve trang chu</a>
```

---

## 9. CÁC LỆNH ĐÃ SỬ DỤNG

### 9.1 Khởi động ứng dụng

```bash
mvn jetty:run
```

### 9.2 Truy cập ứng dụng

```
http://localhost:8083/lab10/login.jsp
```

### 9.3 Các tài khoản mặc định

| Email | Mật khẩu | Vai trò |
|-------|----------|---------|
| admin@eaut.edu.vn | admin123 | ADMIN |
| staff@eaut.edu.vn | staff123 | STAFF |
| user@eaut.edu.vn | user123 | USER |

---

## 10. KẾT QUẢ THỰC THI

### 10.1 Đăng nhập thành công với ADMIN

```
Đăng nhập: admin@eaut.edu.vn / admin123
→ Chuyển hướng đến dashboard.jsp
→ Hiển thị: "Quan ly tai khoan", "Module nghiep vu", "Ho so ca nhan"
```

### 10.2 Đăng nhập thành công với STAFF

```
Đăng nhập: staff@eaut.edu.vn / staff123
→ Chuyển hướng đến dashboard.jsp
→ Hiển thị: "Module nghiep vu", "Ho so ca nhan"
→ Không hiển thị "Quan ly tai khoan"
```

### 10.3 Đăng nhập thành công với USER

```
Đăng nhập: user@eaut.edu.vn / user123
→ Chuyển hướng đến dashboard.jsp
→ Hiển thị: "Ho so ca nhan"
→ Không hiển thị "Quan ly tai khoan" và "Module nghiep vu"
```

### 10.4 Truy cập trái phép

```
USER cố gắng truy cập /admin/users
→ Chuyển hướng đến /error/403.jsp
→ Hiển thị: "403 - Forbidden"

Người dùng chưa đăng nhập truy cập /staff/index
→ Chuyển hướng đến /login.jsp
```

### 10.5 Log đăng nhập

Bảng `login_logs` ghi lại:
| User ID | Email | Action | IP Address | Thời gian |
|---------|-------|--------|------------|-----------|
| 1 | admin@eaut.edu.vn | LOGIN | 127.0.0.1 | 2026-08-25 21:00 |
| null | hacker@test.com | LOGIN_FAILED | 192.168.1.1 | 2026-08-25 21:05 |

---

## 11. CÁC KHÁI NIỆM BẢO MẬT

### 11.1 Authentication (Xác thực)

| Khái niệm | Mô tả |
|-----------|-------|
| Xác thực | Kiểm tra identity của người dùng (ai đang đăng nhập) |
| Session | Lưu trữ trạng thái đăng nhập trên server |
| Cookie | Lưu trữ session ID trên browser |

### 11.2 Authorization (Phân quyền)

| Khái niệm | Mô tả |
|-----------|-------|
| Phân quyền | Kiểm tra quyền truy cập tài nguyên (có được làm không) |
| RBAC | Role-Based Access Control - Phân quyền theo vai trò |
| 403 Forbidden | HTTP status khi không có quyền truy cập |

### 11.3 Password Security

| Kỹ thuật | Mô tả |
|---------|-------|
| BCrypt | Thuật toán hash password một chiều, có salt |
| Salt | Chuỗi ngẫu nhiên thêm vào trước khi hash để tránh rainbow table |
| Hash vs Encrypt | Hash không thể đảo ngược, Encrypt có thể giải mã |

---

## 12. CÁC MODULE NGHIỆP VỤ

| Module | URL | ADMIN | STAFF | USER |
|--------|-----|-------|-------|------|
| Quản lý Users | `/admin/users` | CRUD | - | - |
| Sinh viên | `/staff/sinhvien` | CRUD | CRUD | - |
| Môn học | `/staff/monhoc` | CRUD | CRUD | - |
| Giáo viên | `/staff/giaovien` | CRUD | CRUD | - |
| Lớp học | `/staff/lop` | CRUD | CRUD | - |
| Điểm | `/staff/diem` | CRUD | CRUD | - |
| Điểm danh | `/staff/diemdanh` | CRUD | CRUD | - |
| Thống kê | `/staff/thongke` | Xem | Xem | - |
| Hồ sơ cá nhân | `/user/profile` | Xem/Sửa | Xem/Sửa | Xem/Sửa |

---

## 13. LỖI GẶP PHẢI VÀ CÁCH XỬ LÝ

| Lỗi | Nguyên nhân | Cách xử lý |
|------|-------------|------------|
| Redirect loop vô hạn | Hai Filter cùng kiểm tra session | Đảm bảo AuthenticationFilter chạy trước AuthorizationFilter |
| Lỗi 403 dùng đã đăng nhập | Role không đúng với URL | Kiểm tra logic trong AuthorizationFilter |
| Password không hash | @PrePersist không chạy | Đảm bảo EntityManager được khởi tạo đúng |
| Session null khi chuyển trang | Tạo session mới sau khi invalidate | Tạo session mới trước khi chuyển hướng |
| BCrypt check thất bại | Password plain text không match | Hỗ trợ cả plain text và hashed password |

---

## 14. KẾT LUẬN

Qua bài lab 10, đã nắm được:

- **Filter trong Servlet**: Sử dụng `@WebFilter` để xử lý cross-cutting concerns như authentication và authorization
- **Session Management**: Lưu trữ thông tin user trong HttpSession sau khi đăng nhập thành công
- **Role-Based Access Control (RBAC)**: Phân quyền dựa trên vai trò người dùng
- **Password Hashing**: Sử dụng BCrypt để bảo mật mật khẩu
- **ServletContextListener**: Khởi tạo dữ liệu mặc định khi ứng dụng start
- **Ghi log nghiệp vụ**: Lưu lại lịch sử đăng nhập/đăng xuất

**Sản phẩm:** Ứng dụng web bảo mật chạy tại `http://localhost:8083/lab10` với:
- 3 vai trò: ADMIN, STAFF, USER
- 8 module nghiệp vụ
- Hệ thống xác thực và phân quyền hoàn chỉnh
