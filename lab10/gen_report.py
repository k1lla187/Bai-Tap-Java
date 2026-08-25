"""Generate Lab 10 report DOCX."""
from docx import Document
from docx.shared import Pt, Inches, RGBColor
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.oxml.ns import qn
from docx.oxml import OxmlElement


def add_heading(doc, text, level=1):
    h = doc.add_heading(text, level=level)
    for run in h.runs:
        run.font.name = "Times New Roman"
        run.font.color.rgb = RGBColor(0, 0, 0)
    return h


def add_paragraph(doc, text, bold=False, italic=False, size=13):
    p = doc.add_paragraph()
    run = p.add_run(text)
    run.font.name = "Times New Roman"
    run.font.size = Pt(size)
    run.bold = bold
    run.italic = italic
    return p


def add_code_block(doc, code_text):
    p = doc.add_paragraph()
    p.paragraph_format.left_indent = Inches(0.3)
    pPr = p._p.get_or_add_pPr()
    shd = OxmlElement("w:shd")
    shd.set(qn("w:val"), "clear")
    shd.set(qn("w:color"), "auto")
    shd.set(qn("w:fill"), "F4F4F4")
    pPr.append(shd)
    run = p.add_run(code_text)
    run.font.name = "Consolas"
    run.font.size = Pt(9)
    return p


doc = Document()
style = doc.styles["Normal"]
style.font.name = "Times New Roman"
style.font.size = Pt(13)

# ===== TITLE PAGE =====
for _ in range(3):
    doc.add_paragraph()

title = doc.add_paragraph()
title.alignment = WD_ALIGN_PARAGRAPH.CENTER
run = title.add_run("TRƯỜNG ĐẠI HỌC CÔNG NGHỆ ĐÔNG Á\nKHOA CÔNG NGHỆ THÔNG TIN")
run.bold = True
run.font.size = Pt(14)

doc.add_paragraph()

sub = doc.add_paragraph()
sub.alignment = WD_ALIGN_PARAGRAPH.CENTER
run = sub.add_run("BÁO CÁO BÀI THỰC HÀNH\nLẬP TRÌNH JAVA NÂNG CAO")
run.bold = True
run.font.size = Pt(16)

doc.add_paragraph()

t = doc.add_paragraph()
t.alignment = WD_ALIGN_PARAGRAPH.CENTER
run = t.add_run("LAB 10")
run.bold = True
run.font.size = Pt(24)
run.font.color.rgb = RGBColor(0x1F, 0x4E, 0x79)

sub2 = doc.add_paragraph()
sub2.alignment = WD_ALIGN_PARAGRAPH.CENTER
run = sub2.add_run("Thêm Login, Role, Bảo vệ URL và Hoàn thiện Ứng dụng")
run.bold = True
run.font.size = Pt(16)
run.font.color.rgb = RGBColor(0x1F, 0x4E, 0x79)

doc.add_paragraph()
doc.add_paragraph()

info = doc.add_paragraph()
info.alignment = WD_ALIGN_PARAGRAPH.CENTER
run = info.add_run(
    "Sinh viên thực hiện: Vũ Anh Tuấn\n"
    "Mã sinh viên: 20230199\n"
    "Lớp: CNTT\n"
    "Giảng viên hướng dẫn: ………………………\n"
)
run.font.size = Pt(13)

doc.add_paragraph()
date_p = doc.add_paragraph()
date_p.alignment = WD_ALIGN_PARAGRAPH.CENTER
run = date_p.add_run("Hà Nội, tháng 8 năm 2026")
run.italic = True
run.font.size = Pt(13)

doc.add_page_break()

# ===== MAIN CONTENT =====
add_heading(doc, "MỤC LỤC", level=1)
toc = [
    "1. Mục tiêu bài thực hành",
    "2. Yêu cầu môi trường",
    "3. Cấu trúc project",
    "4. Thiết kế vai trò và quy tắc truy cập",
    "5. Cơ sở dữ liệu (database schema)",
    "6. Tầng Model – Entity",
    "7. Tầng Repository",
    "8. Tầng Service",
    "9. Tầng Controller (Servlet)",
    "10. Tầng Filter – Authentication và Authorization",
    "11. Tầng View – JSP + JSTL",
    "12. Trang lỗi 403, 404, 500",
    "13. AppListener – Seed dữ liệu khởi tạo",
    "14. Ảnh chụp chương trình",
    "15. Hướng dẫn cài đặt và chạy",
    "16. Tài khoản test",
    "17. Tổng kết và đánh giá",
]
for item in toc:
    add_paragraph(doc, item)

doc.add_page_break()

# 1. MỤC TIÊU
add_heading(doc, "1. Mục tiêu bài thực hành", level=1)
for line in [
    "Tạo cơ chế đăng nhập/đăng xuất sử dụng dữ liệu người dùng trong CSDL.",
    "Lưu thông tin người dùng đăng nhập vào HttpSession.",
    "Phân quyền theo vai trò ADMIN, STAFF, USER.",
    "Tạo Filter bảo vệ các URL quản trị và URL yêu cầu đăng nhập.",
    "Hiển thị menu/giao diện khác nhau theo vai trò người dùng.",
    "Bổ sung trang lỗi 403, 404, 500 thân thiện.",
    "Hoàn thiện ứng dụng nhiều module có CSDL, validate và transaction.",
    "Đóng gói, nộp source code và báo cáo tổng hợp.",
]:
    add_paragraph(doc, "• " + line)

# 2. YÊU CẦU MÔI TRƯỜNG
add_heading(doc, "2. Yêu cầu môi trường", level=1)
for line in [
    "JDK 17+ (sử dụng Jakarta EE 10).",
    "Apache Maven 3.8+.",
    "MySQL Server 8.0 (cổng 3306).",
    "Eclipse Jetty Maven Plugin (jetty:run trên cổng 8083).",
    "Trình duyệt web.",
]:
    add_paragraph(doc, "• " + line)

# 3. CẤU TRÚC PROJECT
add_heading(doc, "3. Cấu trúc project", level=1)
add_code_block(
    doc,
    "lab10-secured-app/\n"
    "├── pom.xml\n"
    "├── setup-mysql.sql\n"
    "└── src/main/\n"
    "    ├── java/vn/edu/eaut/lab10/\n"
    "    │   ├── config/JPAUtil.java\n"
    "    │   ├── controller/    (AuthController, UserController, ProfileController,\n"
    "    │   │                   SinhVienController, MonHocController, DiemController)\n"
    "    │   ├── filter/        (AuthenticationFilter, AuthorizationFilter)\n"
    "    │   ├── listener/AppListener.java\n"
    "    │   ├── model/        (Role, User, LoginLog, SinhVien, MonHoc, Diem)\n"
    "    │   ├── repository/   (mỗi entity 1 repository)\n"
    "    │   └── service/      (AuthService, UserManagementService)\n"
    "    ├── resources/META-INF/persistence.xml\n"
    "    └── webapp/\n"
    "        ├── login.jsp, dashboard.jsp\n"
    "        ├── admin/        (users.jsp, user-form.jsp)\n"
    "        ├── staff/        (index.jsp, sinhvien/*, monhoc/*, diem/*)\n"
    "        ├── user/         (profile.jsp)\n"
    "        └── error/        (403.jsp, 404.jsp, 500.jsp)",
)

# 4. VAI TRÒ VÀ QUY TẮC TRUY CẬP
add_heading(doc, "4. Thiết kế vai trò và quy tắc truy cập", level=1)

roles = [
    ("ADMIN", "Quản lý toàn bộ: người dùng, sinh viên, môn học, điểm, báo cáo."),
    ("STAFF", "Thực hiện nghiệp vụ: thêm/sửa dữ liệu nghiệp vụ, không quản lý tài khoản."),
    ("USER", "Xem dữ liệu cá nhân, cập nhật hồ sơ, xem lịch sử."),
]
add_paragraph(doc, "Vai trò:", bold=True)
for name, desc in roles:
    p = doc.add_paragraph()
    r1 = p.add_run(f"• {name}: ")
    r1.bold = True
    r1.font.name = "Times New Roman"
    r1.font.size = Pt(13)
    r2 = p.add_run(desc)
    r2.font.name = "Times New Roman"
    r2.font.size = Pt(13)

add_paragraph(doc, "URL bảo vệ:", bold=True)
urls = [
    ("HTTP", "/admin/*", "Chỉ ADMIN"),
    ("HTTP", "/staff/*", "ADMIN hoặc STAFF"),
    ("HTTP", "/user/*", "Người dùng đã đăng nhập"),
    ("HTTP", "/auth", "Công khai (đăng nhập)"),
    ("CSS/JS/IMG", "/assets/*", "Công khai"),
]
for method, url, access in urls:
    p = doc.add_paragraph()
    r1 = p.add_run(f"• {url}: ")
    r1.bold = True
    r1.font.name = "Times New Roman"
    r1.font.size = Pt(13)
    r2 = p.add_run(access)
    r2.font.name = "Times New Roman"
    r2.font.size = Pt(13)

# 5. CƠ SỞ DỮ LIỆU
add_heading(doc, "5. Cơ sở dữ liệu", level=1)
add_paragraph(doc, "Database: lab10_jpa (UTF-8). Hibernate ở chế độ create-drop tự tạo bảng.")
tables = [
    ("users", "id (PK), email (unique), password, full_name, role, phone, active, created_at, updated_at"),
    ("login_logs", "id (PK), user_id (FK), email, action (LOGIN/LOGOUT/LOGIN_FAILED), ip_address, user_agent, logged_at"),
    ("sinh_vien", "id (PK), ma_sv (unique), ho_ten, email, lop, ngay_sinh, active"),
    ("mon_hoc", "id (PK), ma_mon (unique), ten_mon, so_tin_chi, active"),
    ("diem", "id (PK), sinh_vien_id (FK), mon_hoc_id (FK), diem_gk, diem_ck, diem_tong_ket, xep_loai"),
]
for name, cols in tables:
    p = doc.add_paragraph()
    r = p.add_run(f"• {name}: ")
    r.bold = True
    r.font.name = "Times New Roman"
    r.font.size = Pt(13)
    r2 = p.add_run(cols)
    r2.font.name = "Times New Roman"
    r2.font.size = Pt(13)

# 6. TẦNG MODEL
add_heading(doc, "6. Tầng Model – Entity", level=1)
add_paragraph(doc, "Role.java – enum: ADMIN, STAFF, USER")
add_code_block(
    doc,
    "@Entity\n@Table(name = \"users\")\n"
    "public class User {\n"
    "    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)\n"
    "    private Integer id;\n"
    "    @Column(nullable = false, unique = true, length = 100)\n"
    "    private String email;\n"
    "    @Column(nullable = false, length = 255)\n"
    "    private String password;\n"
    "    @Column(name = \"full_name\", nullable = false)\n"
    "    private String fullName;\n"
    "    @Enumerated(EnumType.STRING)\n"
    "    @Column(nullable = false, length = 20)\n"
    "    private Role role;\n"
    "    private boolean active = true;\n"
    "    // ... getters, setters, @PrePersist, @PreUpdate\n"
    "}",
)
add_paragraph(doc, "LoginLog.java – ghi log đăng nhập: LOGIN, LOGOUT, LOGIN_FAILED")

# 7. TẦNG REPOSITORY
add_heading(doc, "7. Tầng Repository", level=1)
add_paragraph(
    doc,
    "Mỗi entity có 1 repository với đầy đủ CRUD, JPQL query, search, pagination. "
    "Ví dụ UserRepository:",
)
add_code_block(
    doc,
    "public class UserRepository {\n"
    "    public User findByEmail(String email) { ... }\n"
    "    public User findByEmailAndPassword(String email, String password) { ... }\n"
    "    public List<User> search(String keyword) { ... }\n"
    "    public boolean existsByEmail(String email) { ... }\n"
    "    public void save(User user) { ... }\n"
    "    public void update(User user) { ... }\n"
    "    public void delete(Integer id) { ... }\n"
    "    public long count() { ... }\n"
    "}",
)

# 8. TẦNG SERVICE
add_heading(doc, "8. Tầng Service", level=1)
add_paragraph(doc, "AuthService – xử lý đăng nhập:")
add_code_block(
    doc,
    "public User login(String email, String password) {\n"
    "    if (email == null || email.trim().isEmpty()\n"
    "        || password == null || password.trim().isEmpty()) return null;\n"
    "    User user = userRepository.findByEmail(email);\n"
    "    if (user == null || !user.isActive()) return null;\n"
    "    if (!verifyPassword(password, user.getPassword())) return null;\n"
    "    return user;\n"
    "}",
)
add_paragraph(doc, "UserManagementService – CRUD người dùng, đổi mật khẩu với validation đầy đủ.")

# 9. TẦNG CONTROLLER
add_heading(doc, "9. Tầng Controller", level=1)
for name, url, desc in [
    ("AuthController", "/auth", "doPost: login; doGet: logout (action=logout)"),
    ("UserController", "/admin/users", "CRUD tài khoản, tìm kiếm (chỉ ADMIN)"),
    ("ProfileController", "/user/profile", "Cập nhật hồ sơ, đổi mật khẩu"),
    ("SinhVienController", "/staff/sinhvien", "CRUD sinh viên, tìm kiếm, phân trang"),
    ("MonHocController", "/staff/monhoc", "CRUD môn học"),
    ("DiemController", "/staff/diem", "Nhập/sửa điểm, tự động tính điểm tổng kết"),
]:
    p = doc.add_paragraph()
    r1 = p.add_run(f"• {name} ({url}): ")
    r1.bold = True
    r1.font.name = "Times New Roman"
    r1.font.size = Pt(13)
    r2 = p.add_run(desc)
    r2.font.name = "Times New Roman"
    r2.font.size = Pt(13)

# 10. FILTER
add_heading(doc, "10. Tầng Filter – Authentication và Authorization", level=1)
add_paragraph(doc, "AuthenticationFilter (@WebFilter urlPatterns = {\"/admin/*\", \"/staff/*\", \"/user/*\"}):")
add_code_block(
    doc,
    "public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {\n"
    "    HttpSession session = request.getSession(false);\n"
    "    if (session == null || session.getAttribute(\"currentUser\") == null) {\n"
    "        response.sendRedirect(request.getContextPath() + \"/login.jsp\");\n"
    "        return;\n"
    "    }\n"
    "    chain.doFilter(request, response);\n"
    "}",
)
add_paragraph(doc, "AuthorizationFilter (@WebFilter urlPatterns = {\"/admin/*\", \"/staff/*\"}):")
add_code_block(
    doc,
    "public void doFilter(...) {\n"
    "    User user = (User) session.getAttribute(\"currentUser\");\n"
    "    String path = request.getRequestURI();\n"
    "    if (path.contains(\"/admin/\") && user.getRole() != Role.ADMIN)\n"
    "        response.sendRedirect(request.getContextPath() + \"/error/403.jsp\");\n"
    "    else if (path.contains(\"/staff/\") &&\n"
    "             !(user.getRole() == Role.ADMIN || user.getRole() == Role.STAFF))\n"
    "        response.sendRedirect(request.getContextPath() + \"/error/403.jsp\");\n"
    "    else chain.doFilter(request, response);\n"
    "}",
)

# 11. VIEW
add_heading(doc, "11. Tầng View – JSP + JSTL", level=1)
for line in [
    "login.jsp – Form đăng nhập với hiển thị thông báo lỗi.",
    "dashboard.jsp – Trang chủ hiển thị menu theo vai trò (dùng <c:if test='${sessionScope.userRole == \"ADMIN\"}'>).",
    "admin/users.jsp – Danh sách tài khoản, tìm kiếm, thêm/sửa/xóa.",
    "admin/user-form.jsp – Form tạo/sửa tài khoản.",
    "staff/index.jsp – Trang chủ module nghiệp vụ (SinhVien, MonHoc, Diem).",
    "staff/sinhvien-list.jsp, sinhvien-form.jsp – CRUD sinh viên.",
    "staff/monhoc-list.jsp, monhoc-form.jsp – CRUD môn học.",
    "staff/diem-list.jsp, diem-form.jsp – Nhập điểm, tính điểm tổng kết.",
    "user/profile.jsp – Hồ sơ cá nhân + đổi mật khẩu.",
]:
    add_paragraph(doc, "• " + line)

# 12. TRANG LỖI
add_heading(doc, "12. Trang lỗi 403, 404, 500", level=1)
add_paragraph(doc, "Cấu hình trong web.xml:")
add_code_block(
    doc,
    "<error-page>\n"
    "    <error-code>403</error-code>\n"
    "    <location>/error/403.jsp</location>\n"
    "</error-page>\n"
    "<error-page>\n"
    "    <error-code>404</error-code>\n"
    "    <location>/error/404.jsp</location>\n"
    "</error-page>\n"
    "<error-page>\n"
    "    <error-code>500</error-code>\n"
    "    <location>/error/500.jsp</location>\n"
    "</error-page>",
)
for page, code, msg in [
    ("403.jsp", "403", "Bạn không có quyền truy cập"),
    ("404.jsp", "404", "Trang không tồn tại"),
    ("500.jsp", "500", "Lỗi hệ thống"),
]:
    add_paragraph(doc, f"• {page} – Mã {code}: {msg}")

# 13. APP LISTENER
add_heading(doc, "13. AppListener – Seed dữ liệu khởi tạo", level=1)
add_paragraph(
    doc,
    "AppListener implements ServletContextListener, kích hoạt khi webapp start. "
    "Kiểm tra nếu bảng users rỗng thì tự động seed dữ liệu mẫu:",
)
add_code_block(
    doc,
    "@WebListener\n"
    "public class AppListener implements ServletContextListener {\n"
    "    @Override\n"
    "    public void contextInitialized(ServletContextEvent sce) {\n"
    "        if (userRepo.count() == 0) {\n"
    "            seedUsers();       // 3 tài khoản: ADMIN, STAFF, USER\n"
    "            seedBusinessData(); // 5 SV, 4 MH, 5 điểm\n"
    "        }\n"
    "    }\n"
    "}",
)

# 14. ẢNH CHỤP
doc.add_page_break()
add_heading(doc, "14. Ảnh chụp chương trình", level=1)
add_paragraph(doc, "(Các ảnh minh họa sẽ được chụp và chèn vào báo cáo khi chạy thực tế)")
add_paragraph(doc, "Danh sách ảnh cần chụp:")
for line in [
    "Hình 1: Màn hình đăng nhập (login.jsp)",
    "Hình 2: Dashboard sau khi đăng nhập (với vai trò hiển thị)",
    "Hình 3: Danh sách tài khoản người dùng (admin/users.jsp) – ADMIN",
    "Hình 4: Form thêm tài khoản mới",
    "Hình 5: USER đăng nhập thấy giao diện hạn chế",
    "Hình 6: USER truy cập /admin/* → bị chuyển sang 403.jsp",
    "Hình 7: Trang 403 – Không có quyền truy cập",
    "Hình 8: Trang 404 – Không tìm thấy trang",
    "Hình 9: Hồ sơ cá nhân (user/profile.jsp)",
    "Hình 10: Đổi mật khẩu thành công",
    "Hình 11: Danh sách sinh viên (staff/sinhvien)",
    "Hình 12: Thêm sinh viên mới",
    "Hình 13: Danh sách môn học (staff/monhoc)",
    "Hình 14: Danh sách điểm với xếp loại",
    "Hình 15: Đăng xuất → quay về login.jsp",
]:
    add_paragraph(doc, "• " + line)

# 15. HƯỚNG DẪN CÀI ĐẶT
add_heading(doc, "15. Hướng dẫn cài đặt và chạy", level=1)
for line in [
    "Bước 1: Chạy setup-mysql.sql (với quyền root) để tạo database lab10_jpa và users.",
    "Bước 2: Cấu hình persistence.xml – sửa jdbc.user và jdbc.password phù hợp với MySQL máy bạn.",
    "Bước 3: Mở project bằng IntelliJ/Eclipse.",
    "Bước 4: Chạy lệnh: mvn clean jetty:run",
    "Bước 5: Truy cập http://localhost:8083/lab10/login.jsp",
    "Bước 6: Đăng nhập bằng 1 trong 3 tài khoản test.",
]:
    add_paragraph(doc, line)

# 16. TÀI KHOẢN TEST
add_heading(doc, "16. Tài khoản test", level=1)
for line in [
    "admin@eaut.edu.vn / admin123 → vai trò ADMIN (toàn quyền)",
    "staff@eaut.edu.vn / staff123 → vai trò STAFF (thêm/sửa nghiệp vụ)",
    "user@eaut.edu.vn / user123 → vai trò USER (chỉ xem, cập nhật hồ sơ)",
]:
    add_paragraph(doc, "• " + line)

# 17. TỔNG KẾT
add_heading(doc, "17. Tổng kết và đánh giá", level=1)
add_heading(doc, "17.1. Kết quả đạt được", level=2)
for line in [
    "Đăng nhập/đăng xuất hoạt động đúng với dữ liệu trong CSDL.",
    "HttpSession lưu currentUser, userId, userRole, userName, userEmail.",
    "3 vai trò: ADMIN, STAFF, USER với quyền truy cập phân biệt.",
    "AuthenticationFilter bảo vệ /admin/*, /staff/*, /user/*.",
    "AuthorizationFilter kiểm tra role trước khi cho phép truy cập URL.",
    "Menu dashboard hiển thị/hide theo vai trò người dùng.",
    "Trang lỗi 403, 404, 500 thân thiện có nút quay về dashboard.",
    "AppListener tự động seed 3 tài khoản + dữ liệu nghiệp vụ khi khởi động.",
    "Ghi log đăng nhập/đăng xuất/thất bại vào bảng login_logs.",
    "3 module nghiệp vụ: SinhVien, MonHoc, Diem với CRUD đầy đủ.",
    "Validate form với thông báo lỗi rõ ràng.",
    "Đổi mật khẩu với kiểm tra mật khẩu cũ.",
    "Tính điểm tổng kết tự động (30% GK + 70% CK) và xếp loại.",
]:
    add_paragraph(doc, "• " + line)

add_heading(doc, "17.2. Bài học kinh nghiệm", level=2)
for line in [
    "AuthenticationFilter vs AuthorizationFilter: Authentication xác nhận \"ai đã đăng nhập\", Authorization xác nhận \"ai có quyền gì\".",
    "Không nên khởi tạo EntityManagerFactory trong static block — nên dùng lazy singleton để tránh lỗi khi driver chưa load.",
    "Filter chạy trước khi Servlet xử lý → có thể chặn request sớm mà không cần kiểm tra trong mỗi Servlet.",
    "BCrypt hỗ trợ cả kiểm tra bcrypt hash và plain-text → cho phép migrate dần từ plain-text sang hashed.",
    "Transaction nên đóng trong finally block để đảm bảo close EntityManager.",
    "ServletContextListener là nơi lý tưởng để seed dữ liệu khởi tạo — chỉ chạy 1 lần khi webapp start.",
]:
    add_paragraph(doc, "• " + line)

add_heading(doc, "17.3. Hướng phát triển", level=2)
for line in [
    "Chuyển toàn bộ password sang BCrypt hash thay vì plain-text.",
    "Thêm tính năng phân trang cho danh sách người dùng và sinh viên.",
    "Thêm file log (thay vì chỉ DB) để dễ debug khi deploy.",
    "Tích hợp Spring Security thay thế filter thủ công.",
    "Tách front-end (React/Vue) ra khỏi back-end Java, dùng REST API.",
    "Thêm tính năng quên mật khẩu (gửi email reset).",
]:
    add_paragraph(doc, "• " + line)

add_paragraph(doc, "")
add_paragraph(doc, "")
end = doc.add_paragraph()
end.alignment = WD_ALIGN_PARAGRAPH.RIGHT
run = end.add_run("--- Hết báo cáo ---")
run.italic = True

out_path = "D:/Bai-Tap-Java/lab10/BaoCao_Lab10_Secured_App.docx"
doc.save(out_path)
print(f"Saved: {out_path}")
