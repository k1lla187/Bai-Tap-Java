"""Generate Lab 9 report DOCX."""
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


def add_paragraph(doc, text, bold=False, italic=False, align=None, size=13):
    p = doc.add_paragraph()
    if align is not None:
        p.alignment = align
    run = p.add_run(text)
    run.font.name = "Times New Roman"
    run.font.size = Pt(size)
    run.bold = bold
    run.italic = italic
    return p


def add_code_block(doc, code_text):
    p = doc.add_paragraph()
    p.paragraph_format.left_indent = Inches(0.3)
    p.paragraph_format.space_before = Pt(4)
    p.paragraph_format.space_after = Pt(4)
    # Set background shading
    pPr = p._p.get_or_add_pPr()
    shd = OxmlElement("w:shd")
    shd.set(qn("w:val"), "clear")
    shd.set(qn("w:color"), "auto")
    shd.set(qn("w:fill"), "F4F4F4")
    pPr.append(shd)
    run = p.add_run(code_text)
    run.font.name = "Consolas"
    run.font.size = Pt(10)
    return p


doc = Document()

# Set default style font
style = doc.styles["Normal"]
style.font.name = "Times New Roman"
style.font.size = Pt(13)

# ===== TITLE PAGE =====
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
run = t.add_run("LAB 09: JPA REPOSITORY")
run.bold = True
run.font.size = Pt(20)
run.font.color.rgb = RGBColor(0x1F, 0x4E, 0x79)

doc.add_paragraph()

# Student info block
info = doc.add_paragraph()
info.alignment = WD_ALIGN_PARAGRAPH.CENTER
run = info.add_run(
    "\nSinh viên thực hiện: Vũ Anh Tuấn\n"
    "Mã sinh viên: 20230199\n"
    "Lớp: CNTT\n"
    "Giảng viên hướng dẫn: ………………………\n"
)
run.font.size = Pt(13)

doc.add_paragraph()
doc.add_paragraph()

date_p = doc.add_paragraph()
date_p.alignment = WD_ALIGN_PARAGRAPH.CENTER
run = date_p.add_run("Hà Nội, tháng 8 năm 2026")
run.italic = True
run.font.size = Pt(13)

doc.add_page_break()

# ===== MAIN CONTENT =====
add_heading(doc, "MỤC LỤC", level=1)

toc_items = [
    "1. Mục tiêu bài thực hành",
    "2. Yêu cầu môi trường",
    "3. Cơ sở dữ liệu",
    "   3.1. Script tạo database và phân quyền MySQL",
    "   3.2. Cấu trúc các bảng (sinh_vien, lop_hoc, mon_hoc, diem, users, roles)",
    "   3.3. Dữ liệu mẫu được seed tự động",
    "4. Cấu trúc project Maven (lab09-jpa-repository)",
    "5. Thiết kế tầng Model – Entity với JPA Annotations",
    "6. Tầng Repository – trung gian giữa Service và EntityManager",
    "7. Tầng Service – xử lý nghiệp vụ",
    "8. Tầng Controller – Servlet điều phối HTTP",
    "9. Tầng View – JSP + JSTL",
    "10. Bảo mật: AuthFilter, phân quyền ADMIN/USER",
    "11. Cấu hình JPA (persistence.xml)",
    "12. Khởi tạo dữ liệu mẫu bằng Listener",
    "13. Hướng dẫn cài đặt và chạy",
    "14. Kết quả thực hiện (ảnh chụp)",
    "15. Tổng kết và đánh giá",
]
for item in toc_items:
    add_paragraph(doc, item)

doc.add_page_break()

# ===== 1. MỤC TIÊU =====
add_heading(doc, "1. Mục tiêu bài thực hành", level=1)
add_paragraph(
    doc,
    "Bài thực hành Lab 09 giúp sinh viên làm quen với JPA (Java Persistence API) và mô hình "
    "Repository Pattern trong việc xây dựng ứng dụng web Java. Cụ thể:",
)
for line in [
    "Hiểu và sử dụng các Annotation JPA (@Entity, @Table, @Id, @Column, @ManyToOne, @OneToMany …) để ánh xạ đối tượng Java sang bảng quan hệ.",
    "Cấu hình Hibernate làm JPA Provider thông qua file persistence.xml.",
    "Thiết kế tầng Repository làm lớp trung gian giữa Service và EntityManager.",
    "Quản lý giao dịch (transaction) bằng EntityTransaction trong từng thao tác CRUD.",
    "Sử dụng JPQL để truy vấn hướng đối tượng.",
    "Kết hợp JPA với Servlet/JSP để xây dựng ứng dụng web hoàn chỉnh.",
    "Phân quyền người dùng theo vai trò (ADMIN/USER) với Filter.",
    "Seed dữ liệu mẫu bằng ServletContextListener.",
]:
    add_paragraph(doc, "• " + line)

# ===== 2. YÊU CẦU MÔI TRƯỜNG =====
add_heading(doc, "2. Yêu cầu môi trường", level=1)
for line in [
    "JDK 17 trở lên (sử dụng cú pháp Jakarta EE 9+/10).",
    "Apache Maven 3.8+.",
    "MySQL Server 8.0 (cổng mặc định 3306).",
    "Eclipse Jetty Maven Plugin (đã cấu hình trong pom.xml) để chạy trực tiếp webapp.",
    "Trình duyệt web để truy cập giao diện.",
]:
    add_paragraph(doc, "• " + line)

# ===== 3. CƠ SỞ DỮ LIỆU =====
add_heading(doc, "3. Cơ sở dữ liệu", level=1)

add_heading(doc, "3.1. Script tạo database và phân quyền MySQL", level=2)
add_paragraph(doc, "File setup-mysql.sql được chạy một lần với quyền root để chuẩn bị môi trường:")
add_code_block(
    doc,
    "-- Tạo database\n"
    "CREATE DATABASE IF NOT EXISTS lab09_jpa\n"
    "  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;\n\n"
    "-- User chỉ đọc (minh chứng nguyên tắc least privilege)\n"
    "DROP USER IF EXISTS 'lab09user'@'localhost';\n"
    "CREATE USER 'lab09user'@'localhost' IDENTIFIED BY '';\n"
    "GRANT SELECT ON lab09_jpa.* TO 'lab09user'@'localhost';\n\n"
    "-- Admin toàn quyền (dùng để tạo schema + seed)\n"
    "DROP USER IF EXISTS 'lab09admin'@'localhost';\n"
    "CREATE USER 'lab09admin'@'localhost' IDENTIFIED BY '';\n"
    "GRANT ALL PRIVILEGES ON lab09_jpa.* TO 'lab09admin'@'localhost';\n\n"
    "FLUSH PRIVILEGES;",
)

add_heading(doc, "3.2. Cấu trúc các bảng", level=2)
add_paragraph(
    doc,
    "Hibernate ở chế độ create-drop tự động tạo 6 bảng dựa trên các Entity:",
)

tables = [
    ("sinh_vien", "id (PK), ma_sinh_vien (unique), ho_ten, email, lop, ngay_sinh, lop_hoc_id (FK)"),
    ("lop_hoc", "id (PK), ten_lop, giao_vien_chu_nhiem"),
    ("mon_hoc", "id (PK), ma_mon (unique), ten_mon, so_tin_chi"),
    ("diem", "id (PK), sinh_vien_id (FK), mon_hoc_id (FK), diem_giua_ky, diem_cuoi_ky, diem_tong_ket, xepLoai"),
    ("users", "id (PK), username (unique), password, ho_ten, email, role_id (FK), is_active"),
    ("roles", "id (PK), name (unique), description"),
]
for name, cols in tables:
    p = doc.add_paragraph()
    run = p.add_run(f"• {name}: ")
    run.bold = True
    run.font.name = "Times New Roman"
    run.font.size = Pt(13)
    run2 = p.add_run(cols)
    run2.font.name = "Times New Roman"
    run2.font.size = Pt(13)

add_heading(doc, "3.3. Dữ liệu mẫu (Seed)", level=2)
for line in [
    "3 vai trò: ADMIN, TEACHER, USER.",
    "3 lớp học: CNTT1, CNTT2, DTVT1.",
    "4 môn học: IT3242, IT3201, IT3101, IT2101.",
    "2 tài khoản mặc định: admin / 123456, user / 123456.",
    "5 sinh viên mẫu: SV001 – SV005.",
]:
    add_paragraph(doc, "• " + line)

# ===== 4. CẤU TRÚC PROJECT =====
add_heading(doc, "4. Cấu trúc project Maven (lab09-jpa-repository)", level=1)
add_code_block(
    doc,
    "lab09-jpa-repository/\n"
    "├── pom.xml\n"
    "├── setup-mysql.sql\n"
    "└── src/main/\n"
    "    ├── java/vn/edu/eaut/lab9/\n"
    "    │   ├── config/JPAUtil.java\n"
    "    │   ├── controller/        (LoginController, LogoutController, SinhVienController)\n"
    "    │   ├── filter/AuthFilter.java\n"
    "    │   ├── listener/DataSeederListener.java\n"
    "    │   ├── model/             (SinhVien, LopHoc, MonHoc, Diem, User, Role)\n"
    "    │   ├── repository/        (CRUD cho mỗi entity)\n"
    "    │   └── service/           (UserService, SinhVienService, DiemService)\n"
    "    ├── resources/\n"
    "    │   ├── META-INF/persistence.xml\n"
    "    │   └── schema.sql\n"
    "    └── webapp/\n"
    "        ├── index.jsp\n"
    "        ├── login.jsp\n"
    "        └── views/sinhvien/\n"
    "            ├── list.jsp\n"
    "            └── form.jsp",
)

# ===== 5. TẦNG MODEL =====
add_heading(doc, "5. Thiết kế tầng Model – Entity với JPA Annotations", level=1)
add_paragraph(
    doc,
    "Mỗi bảng trong cơ sở dữ liệu được ánh xạ bằng một class Java sử dụng các annotation JPA. "
    "Ví dụ với entity SinhVien:",
)
add_code_block(
    doc,
    "@Entity\n"
    "@Table(name = \"sinh_vien\")\n"
    "public class SinhVien {\n"
    "    @Id\n"
    "    @GeneratedValue(strategy = GenerationType.IDENTITY)\n"
    "    private Integer id;\n"
    "\n"
    "    @Column(name = \"ma_sinh_vien\", nullable = false, unique = true, length = 20)\n"
    "    private String maSinhVien;\n"
    "\n"
    "    @Column(name = \"ho_ten\", nullable = false, length = 100)\n"
    "    private String hoTen;\n"
    "\n"
    "    @ManyToOne(fetch = FetchType.LAZY)\n"
    "    @JoinColumn(name = \"lop_hoc_id\")\n"
    "    private LopHoc lopHoc;\n"
    "    // ... getter / setter\n"
    "}",
)
add_paragraph(
    doc,
    "Quan hệ giữa các entity được biểu diễn rõ ràng: LopHoc 1 – N SinhVien, "
    "SinhVien N – 1 LopHoc, Diem N – 1 SinhVien và N – 1 MonHoc, User N – 1 Role.",
)

# ===== 6. TẦNG REPOSITORY =====
add_heading(doc, "6. Tầng Repository – trung gian giữa Service và EntityManager", level=1)
add_paragraph(
    doc,
    "Mỗi entity có một lớp Repository tương ứng cung cấp các phương thức CRUD và truy vấn JPQL. "
    "RepositoryPattern giúp tách biệt logic nghiệp vụ (Service) khỏi logic truy cập dữ liệu.",
)
add_code_block(
    doc,
    "public class SinhVienRepository {\n"
    "    private EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();\n"
    "\n"
    "    public List<SinhVien> findAll() {\n"
    "        EntityManager em = emf.createEntityManager();\n"
    "        try {\n"
    "            return em.createQuery(\n"
    "                \"SELECT s FROM SinhVien s ORDER BY s.id DESC\",\n"
    "                SinhVien.class).getResultList();\n"
    "        } finally {\n"
    "            em.close();\n"
    "        }\n"
    "    }\n"
    "\n"
    "    public void save(SinhVien sv) {\n"
    "        EntityManager em = emf.createEntityManager();\n"
    "        try {\n"
    "            em.getTransaction().begin();\n"
    "            em.persist(sv);\n"
    "            em.getTransaction().commit();\n"
    "        } finally { em.close(); }\n"
    "    }\n"
    "}",
)
add_paragraph(
    doc,
    "Tương tự có UserRepository, RoleRepository, LopHocRepository, MonHocRepository, DiemRepository "
    "với đầy đủ findById, save, update, delete, existsBy… cho từng entity.",
)

# ===== 7. TẦNG SERVICE =====
add_heading(doc, "7. Tầng Service – xử lý nghiệp vụ", level=1)
add_paragraph(
    doc,
    "Service chứa logic nghiệp vụ và validation. Ví dụ UserService thực hiện đăng nhập và đăng ký:",
)
add_code_block(
    doc,
    "public User login(String username, String password) {\n"
    "    if (username == null || username.trim().isEmpty()\n"
    "        || password == null || password.trim().isEmpty()) return null;\n"
    "    return userRepository.findByUsernameAndPassword(username, password);\n"
    "}\n"
    "\n"
    "public String register(User user) {\n"
    "    if (user.getUsername() == null || user.getUsername().trim().isEmpty())\n"
    "        return \"Username khong duoc trong\";\n"
    "    if (user.getPassword() == null || user.getPassword().length() < 6)\n"
    "        return \"Mat khau phai co it nhat 6 ky tu\";\n"
    "    // … kiểm tra trùng username/email …\n"
    "    userRepository.save(user);\n"
    "    return null;\n"
    "}",
)
add_paragraph(
    doc,
    "SinhVienService bổ sung validate mã sinh viên, họ tên, kiểm tra trùng mã; "
    "DiemService chứa logic tính điểm tổng kết và xếp loại (30% giữa kỳ + 70% cuối kỳ).",
)

# ===== 8. TẦNG CONTROLLER =====
add_heading(doc, "8. Tầng Controller – Servlet điều phối HTTP", level=1)
add_paragraph(
    doc,
    "Các Servlet sử dụng annotation @WebServlet để map URL, điều phối request đến Service và forward "
    "sang JSP tương ứng. Ví dụ LoginController:",
)
add_code_block(
    doc,
    "@WebServlet(\"/login\")\n"
    "public class LoginController extends HttpServlet {\n"
    "    private final UserService userService = new UserService();\n"
    "\n"
    "    @Override\n"
    "    protected void doPost(HttpServletRequest req, HttpServletResponse resp)\n"
    "            throws ServletException, IOException {\n"
    "        String u = req.getParameter(\"username\");\n"
    "        String p = req.getParameter(\"password\");\n"
    "        User user = userService.login(u, p);\n"
    "        if (user != null) {\n"
    "            HttpSession s = req.getSession();\n"
    "            s.setAttribute(\"user\", user);\n"
    "            s.setAttribute(\"username\", user.getUsername());\n"
    "            s.setAttribute(\"role\", user.getRole().getName());\n"
    "            resp.sendRedirect(req.getContextPath() + \"/\");\n"
    "        } else {\n"
    "            req.setAttribute(\"error\", \"Sai tai khoan hoac mat khau\");\n"
    "            req.getRequestDispatcher(\"/login.jsp\").forward(req, resp);\n"
    "        }\n"
    "    }\n"
    "}",
)
add_paragraph(
    doc,
    "SinhVienController xử lý đầy đủ CRUD với phân trang 5 record/trang, tìm kiếm theo tên/lớp. "
    "Các thao tác thêm/sửa/xóa chỉ cho phép với role ADMIN; USER thường chỉ xem được.",
)

# ===== 9. TẦNG VIEW =====
add_heading(doc, "9. Tầng View – JSP + JSTL", level=1)
add_paragraph(
    doc,
    "Các file JSP sử dụng JSTL core để hiển thị dữ liệu từ Servlet. Layout đơn giản với CSS thuần, "
    "danh sách sinh viên dạng bảng, có form tìm kiếm, phân trang và nút Sửa/Xóa (chỉ hiển thị cho ADMIN).",
)
add_paragraph(
    doc,
    "Các URL truy cập:",
)
for line in [
    "/login              – Trang đăng nhập.",
    "/                   – Trang chủ (yêu cầu đăng nhập).",
    "/sinh-vien          – Danh sách sinh viên (có phân trang, tìm kiếm).",
    "/sinh-vien?action=edit&id=N   – Form sửa (chỉ ADMIN).",
    "/sinh-vien?action=delete&id=N – Xóa (chỉ ADMIN).",
    "/logout             – Đăng xuất, hủy session.",
]:
    add_paragraph(doc, "• " + line)

# ===== 10. BẢO MẬT =====
add_heading(doc, "10. Bảo mật: AuthFilter và phân quyền ADMIN/USER", level=1)
add_paragraph(
    doc,
    "AuthFilter chặn mọi truy cập đến /sinh-vien, /, /index.jsp, /views/* nếu chưa đăng nhập "
    "(không có session hoặc session không chứa attribute \"user\"):",
)
add_code_block(
    doc,
    "@WebFilter(urlPatterns = {\"/sinh-vien\", \"/\", \"/index.jsp\", \"/views/*\"})\n"
    "public class AuthFilter implements Filter {\n"
    "    @Override\n"
    "    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {\n"
    "        HttpServletRequest request = (HttpServletRequest) req;\n"
    "        HttpServletResponse response = (HttpServletResponse) res;\n"
    "        HttpSession s = request.getSession(false);\n"
    "        boolean loggedIn = s != null && s.getAttribute(\"user\") != null;\n"
    "        if (loggedIn) chain.doFilter(req, res);\n"
    "        else response.sendRedirect(request.getContextPath() + \"/login\");\n"
    "    }\n"
    "}",
)
add_paragraph(
    doc,
    "Trong SinhVienController, các action edit / delete và POST đều kiểm tra sessionScope.role == 'ADMIN'. "
    "Nếu không phải ADMIN sẽ trả về HTTP 403 Forbidden. User thường chỉ có thể xem và tìm kiếm.",
)

# ===== 11. CẤU HÌNH JPA =====
add_heading(doc, "11. Cấu hình JPA (persistence.xml)", level=1)
add_paragraph(
    doc,
    "File persistence.xml khai báo persistence-unit, các entity, JDBC URL, user/password MySQL, "
    "và thuộc tính Hibernate:",
)
add_code_block(
    doc,
    "<persistence-unit name=\"lab09PU\" transaction-type=\"RESOURCE_LOCAL\">\n"
    "    <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>\n"
    "    <class>vn.edu.eaut.lab9.model.SinhVien</class>\n"
    "    <class>vn.edu.eaut.lab9.model.LopHoc</class>\n"
    "    <class>vn.edu.eaut.lab9.model.MonHoc</class>\n"
    "    <class>vn.edu.eaut.lab9.model.Diem</class>\n"
    "    <class>vn.edu.eaut.lab9.model.User</class>\n"
    "    <class>vn.edu.eaut.lab9.model.Role</class>\n"
    "    <properties>\n"
    "        <property name=\"jakarta.persistence.jdbc.driver\"\n"
    "                  value=\"com.mysql.cj.jdbc.Driver\"/>\n"
    "        <property name=\"jakarta.persistence.jdbc.url\"\n"
    "                  value=\"jdbc:mysql://localhost:3306/lab09_jpa\n"
    "                  ?useUnicode=true&amp;characterEncoding=UTF-8\n"
    "                  &amp;serverTimezone=Asia/Ho_Chi_Minh\n"
    "                  &amp;allowPublicKeyRetrieval=true&amp;useSSL=false\"/>\n"
    "        <property name=\"jakarta.persistence.jdbc.user\" value=\"lab09admin\"/>\n"
    "        <property name=\"jakarta.persistence.jdbc.password\" value=\"\"/>\n"
    "        <property name=\"hibernate.hbm2ddl.auto\" value=\"create-drop\"/>\n"
    "        <property name=\"hibernate.show_sql\" value=\"true\"/>\n"
    "        <property name=\"hibernate.format_sql\" value=\"true\"/>\n"
    "    </properties>\n"
    "</persistence-unit>",
)
add_paragraph(
    doc,
    "JPAUtil.getEntityManagerFactory() được giữ ở dạng singleton để tránh tạo lại EMF nhiều lần.",
)

# ===== 12. SEEDER LISTENER =====
add_heading(doc, "12. Khởi tạo dữ liệu mẫu bằng Listener", level=1)
add_paragraph(
    doc,
    "DataSeederListener implements ServletContextListener, được kích hoạt khi context webapp start. "
    "Nó kiểm tra nếu bảng rỗng sẽ chèn dữ liệu mẫu, đảm bảo ứng dụng luôn có sẵn dữ liệu để demo:",
)
add_code_block(
    doc,
    "@WebListener\n"
    "public class DataSeederListener implements ServletContextListener {\n"
    "    @Override\n"
    "    public void contextInitialized(ServletContextEvent sce) {\n"
    "        // Roles, LopHoc, MonHoc, Users, SinhVien\n"
    "        if (roleRepo.findAll().isEmpty()) {\n"
    "            roleRepo.save(new Role(\"ADMIN\", \"Quan tri he thong\"));\n"
    "            roleRepo.save(new Role(\"TEACHER\", \"Giao vien\"));\n"
    "            roleRepo.save(new Role(\"USER\", \"Nguoi dung thong thuong\"));\n"
    "        }\n"
    "        // ... seed các bảng còn lại tương tự\n"
    "    }\n"
    "}",
)

# ===== 13. HƯỚNG DẪN CÀI ĐẶT =====
add_heading(doc, "13. Hướng dẫn cài đặt và chạy", level=1)
for line in [
    "Bước 1: Cài MySQL 8, tạo user lab09admin (password rỗng) và lab09user (read-only).",
    "Bước 2: Chạy file setup-mysql.sql với quyền root để tạo database lab09_jpa.",
    "Bước 3: Mở project bằng IDE (IntelliJ / Eclipse) có hỗ trợ Maven.",
    "Bước 4: Cấu hình persistence.xml với user lab09admin (full quyền để Hibernate create-drop và seed).",
    "Bước 5: Chạy lệnh mvn clean jetty:run để khởi động webapp.",
    "Bước 6: Truy cập http://localhost:8082/lab09-jpa-repository/login.",
    "Bước 7: Đăng nhập bằng admin / 123456 hoặc user / 123456.",
]:
    add_paragraph(doc, line)

# ===== 14. KẾT QUẢ =====
doc.add_page_break()
add_heading(doc, "14. Kết quả thực hiện (ảnh chụp màn hình)", level=1)
add_paragraph(
    doc,
    "Các ảnh minh họa dưới đây được chụp trong quá trình chạy thực tế ứng dụng trên Jetty Maven Plugin:",
)

images = [
    ("login.png", "Hình 1: Màn hình đăng nhập"),
    ("trang chu.png", "Hình 2: Trang chủ sau khi đăng nhập thành công"),
    ("danh sach.png", "Hình 3: Danh sách sinh viên có phân trang"),
    ("them sv.png", "Hình 4: Form thêm sinh viên mới"),
    ("sua sv.png", "Hình 5: Form sửa thông tin sinh viên"),
    ("sua ok.png", "Hình 6: Kết quả sửa sinh viên thành công"),
    ("xoa.png", "Hình 7: Xác nhận xóa sinh viên"),
    ("xoa ok.png", "Hình 8: Kết quả xóa sinh viên thành công"),
    ("tim kiem.png", "Hình 9: Kết quả tìm kiếm sinh viên theo từ khóa"),
    ("user.png", "Hình 10: Đăng nhập bằng tài khoản user thường"),
    ("user ko them sua xoa dc.png", "Hình 11: User thường không thấy nút Thêm/Sửa/Xóa"),
]
import os
for fname, caption in images:
    img_path = os.path.join("D:/Bai-Tap-Java/lab9", fname)
    if os.path.exists(img_path):
        p = doc.add_paragraph()
        p.alignment = WD_ALIGN_PARAGRAPH.CENTER
        run = p.add_run()
        run.add_picture(img_path, width=Inches(5.5))
        cap = doc.add_paragraph()
        cap.alignment = WD_ALIGN_PARAGRAPH.CENTER
        cap_run = cap.add_run(caption)
        cap_run.italic = True
        cap_run.font.size = Pt(12)

# ===== 15. TỔNG KẾT =====
doc.add_page_break()
add_heading(doc, "15. Tổng kết và đánh giá", level=1)
add_heading(doc, "15.1. Kết quả đạt được", level=2)
for line in [
    "Cấu hình thành công JPA với Hibernate 6.4 làm provider trên MySQL 8.",
    "Ánh xạ 6 entity Java sang 6 bảng quan hệ với các mối quan hệ @ManyToOne, @OneToMany.",
    "Tầng Repository hoàn chỉnh, tách biệt hoàn toàn với tầng Service và Controller.",
    "Ứng dụng web với 3 controller (Login, Logout, SinhVien) thực hiện CRUD và tìm kiếm.",
    "Phân quyền hoạt động đúng: ADMIN thêm/sửa/xóa; USER chỉ xem.",
    "AuthFilter chặn truy cập trái phép vào tài nguyên được bảo vệ.",
    "DataSeederListener tự động chèn dữ liệu mẫu khi ứng dụng khởi động lần đầu.",
    "Giao diện JSP + JSTL có phân trang, tìm kiếm, hiển thị thông báo lỗi/thành công.",
]:
    add_paragraph(doc, "• " + line)

add_heading(doc, "15.2. Khó khăn và bài học kinh nghiệm", level=2)
for line in [
    "Quyền MySQL: lab09user chỉ có SELECT nên không thể để Hibernate dùng user này với create-drop. Đã chuyển sang lab09admin (ALL PRIVILEGES) để vừa tạo schema vừa seed dữ liệu.",
    "Encoding UTF-8 cho cả request (req.setCharacterEncoding) và entity (@Column length phù hợp) để hiển thị tiếng Việt đúng.",
    "Sử dụng FetchType.LAZY cho quan hệ @ManyToOne để tránh N+1 query, nhưng cần chú ý mở session khi truy cập ngoài transaction.",
    "Tách role vào bảng riêng giúp dễ mở rộng phân quyền (ADMIN, TEACHER, USER) mà không phải sửa code.",
    "Pattern Repository giúp code dễ test, dễ thay thế bằng implementation khác (JDBC thuần, Spring Data) mà không ảnh hưởng Service.",
]:
    add_paragraph(doc, "• " + line)

add_heading(doc, "15.3. Hướng phát triển", level=2)
for line in [
    "Mã hóa mật khẩu bằng BCrypt thay vì lưu plain-text.",
    "Thay thế create-drop bằng validate (hoặc Flyway / Liquibase) để quản lý schema migration.",
    "Tách phần view ra Thymeleaf hoặc React để có giao diện tách biệt hơn với backend.",
    "Bổ sung REST API cho phép front-end khác (mobile app) gọi đến.",
    "Tích hợp Spring Boot để giảm cấu hình tay.",
]:
    add_paragraph(doc, "• " + line)

add_paragraph(doc, "")
add_paragraph(doc, "")
end = doc.add_paragraph()
end.alignment = WD_ALIGN_PARAGRAPH.RIGHT
run = end.add_run("--- Hết báo cáo ---")
run.italic = True

out_path = "D:/Bai-Tap-Java/lab9/BaoCao_Lab09_JPA_Repository.docx"
doc.save(out_path)
print(f"Saved: {out_path}")
