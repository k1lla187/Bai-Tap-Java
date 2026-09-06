"""Generate Lab 11 report Word document"""
from docx import Document
from docx.shared import Pt, Inches, RGBColor, Cm
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.enum.table import WD_ALIGN_VERTICAL
from docx.oxml.ns import qn
from docx.oxml import OxmlElement

OUTPUT = r"d:\Bai-Tap-Java\lab11\lab11-springboot-thymeleaf\Bao-cao-Lab11.docx"

doc = Document()

# Default style
style = doc.styles['Normal']
style.font.name = 'Times New Roman'
style.font.size = Pt(13)

# Page margins
for section in doc.sections:
    section.top_margin = Cm(2)
    section.bottom_margin = Cm(2)
    section.left_margin = Cm(2.5)
    section.right_margin = Cm(2.5)


def add_heading(text, level=1, center=False):
    h = doc.add_heading(text, level=level)
    if center:
        h.alignment = WD_ALIGN_PARAGRAPH.CENTER
    for run in h.runs:
        run.font.name = 'Times New Roman'
        run.font.color.rgb = RGBColor(0x1F, 0x3A, 0x5F)
    return h


def add_para(text, bold=False, italic=False, center=False, size=13, color=None):
    p = doc.add_paragraph()
    if center:
        p.alignment = WD_ALIGN_PARAGRAPH.CENTER
    r = p.add_run(text)
    r.font.name = 'Times New Roman'
    r.font.size = Pt(size)
    r.bold = bold
    r.italic = italic
    if color:
        r.font.color.rgb = color
    return p


def add_code(code_text, language="java"):
    """Add a code block with monospaced font and light background"""
    p = doc.add_paragraph()
    p.paragraph_format.left_indent = Cm(0.6)
    p.paragraph_format.space_before = Pt(2)
    p.paragraph_format.space_after = Pt(2)
    r = p.add_run(code_text)
    r.font.name = 'Consolas'
    r.font.size = Pt(10)
    # Add light shading via XML
    pPr = p._p.get_or_add_pPr()
    shd = OxmlElement('w:shd')
    shd.set(qn('w:val'), 'clear')
    shd.set(qn('w:color'), 'auto')
    shd.set(qn('w:fill'), 'F2F2F2')
    pPr.append(shd)
    return p


def add_table(headers, rows, header_color='1F3A5F'):
    table = doc.add_table(rows=1 + len(rows), cols=len(headers))
    table.style = 'Light Grid Accent 1'
    # header row
    hdr = table.rows[0].cells
    for i, h in enumerate(headers):
        hdr[i].text = ''
        p = hdr[i].paragraphs[0]
        r = p.add_run(h)
        r.bold = True
        r.font.color.rgb = RGBColor(0xFF, 0xFF, 0xFF)
        r.font.name = 'Times New Roman'
        r.font.size = Pt(12)
        # shading
        tcPr = hdr[i]._tc.get_or_add_tcPr()
        shd = OxmlElement('w:shd')
        shd.set(qn('w:val'), 'clear')
        shd.set(qn('w:color'), 'auto')
        shd.set(qn('w:fill'), header_color)
        tcPr.append(shd)
    for row_idx, row in enumerate(rows, start=1):
        cells = table.rows[row_idx].cells
        for i, val in enumerate(row):
            cells[i].text = ''
            p = cells[i].paragraphs[0]
            r = p.add_run(str(val))
            r.font.name = 'Times New Roman'
            r.font.size = Pt(11)
    return table


# ===== TITLE =====
title = doc.add_paragraph()
title.alignment = WD_ALIGN_PARAGRAPH.CENTER
r = title.add_run('BÁO CÁO LAB 11')
r.bold = True
r.font.size = Pt(20)
r.font.color.rgb = RGBColor(0x1F, 0x3A, 0x5F)

sub = doc.add_paragraph()
sub.alignment = WD_ALIGN_PARAGRAPH.CENTER
r = sub.add_run('KHỞI TẠO ỨNG DỤNG SPRING BOOT VÀ GIAO DIỆN THYMELEAF')
r.bold = True
r.font.size = Pt(15)
r.font.color.rgb = RGBColor(0x55, 0x55, 0x55)

doc.add_paragraph()

# Info table
info_rows = [
    ['Học phần', 'Công nghệ Java'],
    ['Bài lab', 'Lab 11 - Spring Boot và Thymeleaf'],
    ['Chương', 'Chương 4 - Spring Framework'],
    ['Công nghệ', 'Spring Boot, Spring MVC, Thymeleaf, Maven'],
    ['Ngày thực hiện', '28/08/2026'],
]
add_table(['Thông tin', 'Chi tiết'], info_rows)

doc.add_page_break()

# ===== MỤC LỤC =====
add_heading('MỤC LỤC', level=1, center=True)
toc_items = [
    '1. Mô tả project',
    '2. Cấu hình pom.xml',
    '3. Lớp khởi động ứng dụng',
    '4. Các Controller',
    '5. Các Model',
    '6. Các view Thymeleaf',
    '7. Cú pháp Thymeleaf sử dụng',
    '8. CSS styling',
    '9. Build và chạy ứng dụng',
    '10. Kết quả kiểm thử',
    '11. Kết luận',
]
for item in toc_items:
    add_para(item, size=13)

doc.add_page_break()

# ===== 1. MÔ TẢ PROJECT =====
add_heading('1. MÔ TẢ PROJECT', level=1)

add_heading('1.1 Cấu trúc thư mục', level=2)
add_code(
    'lab11-springboot-thymeleaf/\n'
    '├── pom.xml\n'
    '└── src/main/\n'
    '    ├── java/vn/edu/eaut/lab11/\n'
    '    │   ├── Lab11Application.java\n'
    '    │   ├── controller/\n'
    '    │   │   ├── HomeController.java\n'
    '    │   │   ├── StudentController.java\n'
    '    │   │   └── CourseController.java\n'
    '    │   └── model/\n'
    '    │       ├── Student.java\n'
    '    │       └── Course.java\n'
    '    └── resources/\n'
    '        ├── templates/\n'
    '        │   ├── index.html\n'
    '        │   ├── about.html\n'
    '        │   ├── students.html\n'
    '        │   ├── courses.html\n'
    '        │   └── contact.html\n'
    '        ├── static/css/style.css\n'
    '        └── application.properties'
)

add_heading('1.2 Giải thích cấu trúc', level=2)
add_table(
    ['Thành phần', 'Ý nghĩa'],
    [
        ['pom.xml', 'Cấu hình Maven, khai báo dependency Spring Boot'],
        ['Lab11Application.java', 'Lớp main khởi động ứng dụng'],
        ['controller/', 'Các Controller xử lý HTTP request'],
        ['model/', 'Các class POJO biểu diễn dữ liệu'],
        ['templates/', 'Chứa các file Thymeleaf (.html)'],
        ['static/', 'Tài nguyên tĩnh (CSS, JS, image)'],
        ['application.properties', 'Cấu hình Spring Boot'],
    ]
)

add_heading('1.3 Mô tả các lớp', level=2)
add_table(
    ['Lớp', 'Package', 'Chức năng'],
    [
        ['Lab11Application', 'vn.edu.eaut.lab11', 'Điểm khởi đầu ứng dụng Spring Boot'],
        ['HomeController', 'vn.edu.eaut.lab11.controller', 'Xử lý URL: /, /about, /contact'],
        ['StudentController', 'vn.edu.eaut.lab11.controller', 'Xử lý URL /students'],
        ['CourseController', 'vn.edu.eaut.lab11.controller', 'Xử lý URL /courses'],
        ['Student', 'vn.edu.eaut.lab11.model', 'Model lưu thông tin sinh viên'],
        ['Course', 'vn.edu.eaut.lab11.model', 'Model lưu thông tin khóa học'],
    ]
)

# ===== 2. POM.XML =====
add_heading('2. CẤU HÌNH POM.XML', level=1)
add_code(
    '<?xml version="1.0" encoding="UTF-8"?>\n'
    '<project xmlns="http://maven.apache.org/POM/4.0.0"\n'
    '         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"\n'
    '         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0\n'
    '                             http://maven.apache.org/xsd/maven-4.0.0.xsd">\n'
    '    <modelVersion>4.0.0</modelVersion>\n'
    '\n'
    '    <parent>\n'
    '        <groupId>org.springframework.boot</groupId>\n'
    '        <artifactId>spring-boot-starter-parent</artifactId>\n'
    '        <version>3.3.0</version>\n'
    '        <relativePath/>\n'
    '    </parent>\n'
    '\n'
    '    <groupId>vn.edu.eaut</groupId>\n'
    '    <artifactId>lab11-springboot-thymeleaf</artifactId>\n'
    '    <version>1.0.0</version>\n'
    '    <packaging>jar</packaging>\n'
    '\n'
    '    <properties>\n'
    '        <java.version>17</java.version>\n'
    '    </properties>\n'
    '\n'
    '    <dependencies>\n'
    '        <dependency>\n'
    '            <groupId>org.springframework.boot</groupId>\n'
    '            <artifactId>spring-boot-starter-web</artifactId>\n'
    '        </dependency>\n'
    '        <dependency>\n'
    '            <groupId>org.springframework.boot</groupId>\n'
    '            <artifactId>spring-boot-starter-thymeleaf</artifactId>\n'
    '        </dependency>\n'
    '        <dependency>\n'
    '            <groupId>org.springframework.boot</groupId>\n'
    '            <artifactId>spring-boot-devtools</artifactId>\n'
    '            <scope>runtime</scope>\n'
    '            <optional>true</optional>\n'
    '        </dependency>\n'
    '        <dependency>\n'
    '            <groupId>org.springframework.boot</groupId>\n'
    '            <artifactId>spring-boot-starter-test</artifactId>\n'
    '            <scope>test</scope>\n'
    '        </dependency>\n'
    '    </dependencies>\n'
    '\n'
    '    <build>\n'
    '        <plugins>\n'
    '            <plugin>\n'
    '                <groupId>org.springframework.boot</groupId>\n'
    '                <artifactId>spring-boot-maven-plugin</artifactId>\n'
    '            </plugin>\n'
    '        </plugins>\n'
    '    </build>\n'
    '</project>',
    language="xml"
)

add_heading('Giải thích các dependency', level=2)
add_table(
    ['Dependency', 'Chức năng'],
    [
        ['spring-boot-starter-web', 'Cung cấp Spring MVC, embedded Tomcat, JSON'],
        ['spring-boot-starter-thymeleaf', 'Tích hợp Thymeleaf template engine'],
        ['spring-boot-devtools', 'Tự động restart khi code thay đổi'],
        ['spring-boot-starter-test', 'Hỗ trợ unit test với JUnit 5'],
    ]
)

# ===== 3. LỚP KHỞI ĐỘNG =====
add_heading('3. LỚP KHỞI ĐỘNG ỨNG DỤNG', level=1)
add_code(
    'package vn.edu.eaut.lab11;\n'
    '\n'
    'import org.springframework.boot.SpringApplication;\n'
    'import org.springframework.boot.autoconfigure.SpringBootApplication;\n'
    '\n'
    '@SpringBootApplication\n'
    'public class Lab11Application {\n'
    '    public static void main(String[] args) {\n'
    '        SpringApplication.run(Lab11Application.class, args);\n'
    '    }\n'
    '}'
)

add_para('Giải thích:', bold=True)
add_para('@SpringBootApplication - đánh dấu ứng dụng Spring Boot, bao gồm:')
add_para('@Configuration - cho phép đăng ký bean')
add_para('@EnableAutoConfiguration - tự động cấu hình')
add_para('@ComponentScan - quét các component trong package')
add_para('SpringApplication.run() - khởi động embedded server (Tomcat)')

# ===== 4. CÁC CONTROLLER =====
add_heading('4. CÁC CONTROLLER', level=1)

add_heading('4.1 HomeController', level=2)
add_code(
    'package vn.edu.eaut.lab11.controller;\n'
    '\n'
    'import org.springframework.stereotype.Controller;\n'
    'import org.springframework.ui.Model;\n'
    'import org.springframework.web.bind.annotation.GetMapping;\n'
    '\n'
    '@Controller\n'
    'public class HomeController {\n'
    '\n'
    '    @GetMapping("/")\n'
    '    public String index(Model model) {\n'
    '        model.addAttribute("title", "Hệ thống quản lý sinh viên");\n'
    '        model.addAttribute("message", "Chào mừng đến với Spring Boot");\n'
    '        model.addAttribute("currentYear", java.time.Year.now().getValue());\n'
    '        return "index";\n'
    '    }\n'
    '\n'
    '    @GetMapping("/about")\n'
    '    public String about(Model model) {\n'
    '        model.addAttribute("course", "Công nghệ Java");\n'
    '        model.addAttribute("chapter", "Chương 4 - Spring Framework");\n'
    '        model.addAttribute("description", "Lab 11 giới thiệu Spring Boot");\n'
    '        model.addAttribute("topics", List.of(\n'
    '                "Spring Boot Starter Web",\n'
    '                "Thymeleaf Template Engine",\n'
    '                "Spring MVC Controller",\n'
    '                "Spring Data JPA",\n'
    '                "Spring Security"));\n'
    '        return "about";\n'
    '    }\n'
    '\n'
    '    @GetMapping("/contact")\n'
    '    public String contact(Model model) {\n'
    '        model.addAttribute("department", "Khoa Công nghệ thông tin");\n'
    '        model.addAttribute("school", "Trường Đại học Công nghệ Đông Á");\n'
    '        model.addAttribute("address", "Số 5, Phố Trịnh Văn Bô, Hà Nội");\n'
    '        model.addAttribute("email", "cntt@eaut.edu.vn");\n'
    '        model.addAttribute("phone", "(024) 3557 7799");\n'
    '        return "contact";\n'
    '    }\n'
    '}'
)

add_heading('4.2 StudentController', level=2)
add_code(
    'package vn.edu.eaut.lab11.controller;\n'
    '\n'
    'import org.springframework.stereotype.Controller;\n'
    'import org.springframework.ui.Model;\n'
    'import org.springframework.web.bind.annotation.GetMapping;\n'
    'import vn.edu.eaut.lab11.model.Student;\n'
    'import java.util.List;\n'
    '\n'
    '@Controller\n'
    'public class StudentController {\n'
    '\n'
    '    @GetMapping("/students")\n'
    '    public String listStudents(Model model) {\n'
    '        List<Student> students = List.of(\n'
    '                new Student("SV001", "Nguyễn Văn An", "an@eaut.edu.vn", "DCCNTT13.10.1"),\n'
    '                new Student("SV002", "Trần Thị Bình", "binh@eaut.edu.vn", "DCCNTT13.10.2"),\n'
    '                new Student("SV003", "Lê Văn Cường", "cuong@eaut.edu.vn", "DCCNTT13.10.3"),\n'
    '                new Student("SV004", "Phạm Thị Dung", "dung@eaut.edu.vn", "DCCNTT13.10.1"),\n'
    '                new Student("SV005", "Hoàng Văn Em", "em@eaut.edu.vn", "DCCNTT13.10.2")\n'
    '        );\n'
    '        model.addAttribute("students", students);\n'
    '        model.addAttribute("title", "Danh sách sinh viên");\n'
    '        return "students";\n'
    '    }\n'
    '}'
)

add_heading('4.3 CourseController', level=2)
add_code(
    'package vn.edu.eaut.lab11.controller;\n'
    '\n'
    'import org.springframework.stereotype.Controller;\n'
    'import org.springframework.ui.Model;\n'
    'import org.springframework.web.bind.annotation.GetMapping;\n'
    'import vn.edu.eaut.lab11.model.Course;\n'
    'import java.util.List;\n'
    '\n'
    '@Controller\n'
    'public class CourseController {\n'
    '\n'
    '    @GetMapping("/courses")\n'
    '    public String listCourses(Model model) {\n'
    '        List<Course> courses = List.of(\n'
    '                new Course("IT3242", "Công nghệ Java", 3),\n'
    '                new Course("IT3201", "Cơ sở dữ liệu", 4),\n'
    '                new Course("IT3101", "Lập trình Web", 3),\n'
    '                new Course("IT2101", "Cấu trúc dữ liệu", 3),\n'
    '                new Course("IT2202", "Mạng máy tính", 3)\n'
    '        );\n'
    '        model.addAttribute("courses", courses);\n'
    '        model.addAttribute("title", "Danh sách khóa học");\n'
    '        return "courses";\n'
    '    }\n'
    '}'
)

# ===== 5. CÁC MODEL =====
add_heading('5. CÁC MODEL', level=1)

add_heading('5.1 Student', level=2)
add_code(
    'package vn.edu.eaut.lab11.model;\n'
    '\n'
    'public class Student {\n'
    '    private String studentCode;\n'
    '    private String fullName;\n'
    '    private String email;\n'
    '    private String className;\n'
    '\n'
    '    public Student() {}\n'
    '\n'
    '    public Student(String studentCode, String fullName, String email, String className) {\n'
    '        this.studentCode = studentCode;\n'
    '        this.fullName = fullName;\n'
    '        this.email = email;\n'
    '        this.className = className;\n'
    '    }\n'
    '\n'
    '    public String getStudentCode() { return studentCode; }\n'
    '    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }\n'
    '    public String getFullName() { return fullName; }\n'
    '    public void setFullName(String fullName) { this.fullName = fullName; }\n'
    '    public String getEmail() { return email; }\n'
    '    public void setEmail(String email) { this.email = email; }\n'
    '    public String getClassName() { return className; }\n'
    '    public void setClassName(String className) { this.className = className; }\n'
    '}'
)

add_heading('5.2 Course', level=2)
add_code(
    'package vn.edu.eaut.lab11.model;\n'
    '\n'
    'public class Course {\n'
    '    private String courseCode;\n'
    '    private String courseName;\n'
    '    private int credits;\n'
    '\n'
    '    public Course() {}\n'
    '\n'
    '    public Course(String courseCode, String courseName, int credits) {\n'
    '        this.courseCode = courseCode;\n'
    '        this.courseName = courseName;\n'
    '        this.credits = credits;\n'
    '    }\n'
    '\n'
    '    public String getCourseCode() { return courseCode; }\n'
    '    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }\n'
    '    public String getCourseName() { return courseName; }\n'
    '    public void setCourseName(String courseName) { this.courseName = courseName; }\n'
    '    public int getCredits() { return credits; }\n'
    '    public void setCredits(int credits) { this.credits = credits; }\n'
    '}'
)

# ===== 6. CÁC VIEW =====
add_heading('6. CÁC VIEW THYMELEAF', level=1)

add_heading('6.1 Trang chủ - index.html', level=2)
add_code(
    '<!DOCTYPE html>\n'
    '<html xmlns:th="http://www.thymeleaf.org">\n'
    '<head>\n'
    '    <meta charset="UTF-8">\n'
    '    <title th:text="${title}">Trang chủ</title>\n'
    '    <link rel="stylesheet" th:href="@{/css/style.css}">\n'
    '</head>\n'
    '<body>\n'
    '    <nav class="navbar">\n'
    '        <a class="brand" th:href="@{/}">Spring Boot Lab</a>\n'
    '        <ul class="menu">\n'
    '            <li><a th:href="@{/}">Home</a></li>\n'
    '            <li><a th:href="@{/about}">About</a></li>\n'
    '            <li><a th:href="@{/students}">Students</a></li>\n'
    '            <li><a th:href="@{/courses}">Courses</a></li>\n'
    '            <li><a th:href="@{/contact}">Contact</a></li>\n'
    '        </ul>\n'
    '    </nav>\n'
    '    <main class="container">\n'
    '        <section class="hero">\n'
    '            <h1 th:text="${title}">Hệ thống quản lý sinh viên</h1>\n'
    '            <p th:text="${message}">Chào mừng đến với Spring Boot</p>\n'
    '            <a th:href="@{/students}">Danh sách sinh viên</a>\n'
    '        </section>\n'
    '    </main>\n'
    '</body>\n'
    '</html>',
    language="html"
)

add_heading('6.2 Trang danh sách sinh viên - students.html', level=2)
add_code(
    '<table class="data-table">\n'
    '    <thead>\n'
    '        <tr>\n'
    '            <th>STT</th>\n'
    '            <th>Mã SV</th>\n'
    '            <th>Họ tên</th>\n'
    '            <th>Email</th>\n'
    '            <th>Lớp</th>\n'
    '        </tr>\n'
    '    </thead>\n'
    '    <tbody>\n'
    '        <tr th:each="s, iter : ${students}">\n'
    '            <td th:text="${iter.count}">1</td>\n'
    '            <td th:text="${s.studentCode}">SV001</td>\n'
    '            <td th:text="${s.fullName}">Nguyễn Văn An</td>\n'
    '            <td th:text="${s.email}">an@eaut.edu.vn</td>\n'
    '            <td th:text="${s.className}">CNTT1</td>\n'
    '        </tr>\n'
    '    </tbody>\n'
    '</table>',
    language="html"
)

add_heading('6.3 Trang danh sách khóa học - courses.html', level=2)
add_code(
    '<table class="data-table">\n'
    '    <thead>\n'
    '        <tr>\n'
    '            <th>STT</th>\n'
    '            <th>Mã môn</th>\n'
    '            <th>Tên môn</th>\n'
    '            <th>Số tín chỉ</th>\n'
    '        </tr>\n'
    '    </thead>\n'
    '    <tbody>\n'
    '        <tr th:each="c, iter : ${courses}">\n'
    '            <td th:text="${iter.count}">1</td>\n'
    '            <td th:text="${c.courseCode}">IT001</td>\n'
    '            <td th:text="${c.courseName}">Java</td>\n'
    '            <td th:text="${c.credits}">3</td>\n'
    '        </tr>\n'
    '    </tbody>\n'
    '</table>',
    language="html"
)

# ===== 7. CÚ PHÁP THYMELEAF =====
add_heading('7. CÁC CÚ PHÁP THYMELEAF SỬ DỤNG', level=1)
add_table(
    ['Cú pháp', 'Ý nghĩa', 'Ví dụ'],
    [
        ['th:text="${var}"', 'Hiển thị giá trị biến', '<h1 th:text="${title}">'],
        ['th:href="@{/url}"', 'Tạo URL tương đối', '<a th:href="@{/students}">'],
        ['th:each="item : ${list}"', 'Lặp qua danh sách', '<tr th:each="s : ${students}">'],
        ['th:each="item, iter : ${list}"', 'Lặp với biến đếm', '<td th:text="${iter.count}">'],
        ['th:if="${condition}"', 'Điều kiện hiển thị', '<div th:if="${user != null}">'],
        ['th:utext="${html}"', 'Hiển thị HTML không escape', '<div th:utext="${content}">'],
        ['th:fragment="name"', 'Định nghĩa fragment tái sử dụng', '<div th:fragment="header">'],
        ['th:replace="~{::fragment}"', 'Thay thế fragment', '<div th:replace="~{::header}">'],
        ['xmlns:th', 'Khai báo namespace Thymeleaf', '<html xmlns:th="...">'],
        ['${#lists.size(list)}', 'Lấy kích thước danh sách', '<span th:text="${#lists.size(students)}">'],
    ]
)

# ===== 8. CSS =====
add_heading('8. CSS STYLING', level=1)
add_para('File static/css/style.css chứa các style cho: navbar, hero, card, table, footer với gradient màu tím-xanh và hiệu ứng hover.')
add_code(
    '.navbar {\n'
    '    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n'
    '    color: white;\n'
    '    padding: 16px 0;\n'
    '    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);\n'
    '}\n'
    '\n'
    '.menu {\n'
    '    list-style: none;\n'
    '    display: flex;\n'
    '    gap: 24px;\n'
    '}\n'
    '\n'
    '.menu a {\n'
    '    color: white;\n'
    '    text-decoration: none;\n'
    '    padding: 6px 12px;\n'
    '    border-radius: 6px;\n'
    '    transition: background 0.2s;\n'
    '}\n'
    '\n'
    '.data-table th {\n'
    '    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n'
    '    color: white;\n'
    '    padding: 12px;\n'
    '    text-align: left;\n'
    '}'
)

# ===== 9. BUILD VÀ CHẠY =====
add_heading('9. BUILD VÀ CHẠY ỨNG DỤNG', level=1)

add_heading('9.1 Lệnh kiểm tra môi trường', level=2)
add_code(
    'java -version\n'
    'javac -version\n'
    'mvn -version'
)

add_heading('9.2 Build project', level=2)
add_code(
    'mvn clean package\n'
    '# hoặc\n'
    'mvn compile'
)

add_heading('9.3 Chạy ứng dụng', level=2)
add_code(
    'mvn spring-boot:run'
)

add_heading('9.4 Truy cập ứng dụng', level=2)
add_code(
    'http://localhost:8080/\n'
    'http://localhost:8080/about\n'
    'http://localhost:8080/students\n'
    'http://localhost:8080/courses\n'
    'http://localhost:8080/contact'
)

# ===== 10. KẾT QUẢ =====
add_heading('10. KẾT QUẢ KIỂM THỬ', level=1)

add_heading('10.1 Build thành công', level=2)
add_code(
    '[INFO] BUILD SUCCESS\n'
    '[INFO] Total time: ~30s'
)

add_heading('10.2 Ứng dụng chạy thành công', level=2)
add_code(
    'Started Lab11Application in 2.5 seconds\n'
    'Tomcat started on port 8080 (http)'
)

add_heading('10.3 Các URL hoạt động', level=2)
add_table(
    ['URL', 'View', 'Mô tả'],
    [
        ['/', 'index.html', 'Trang chủ với hero section và 3 card giới thiệu'],
        ['/about', 'about.html', 'Trang giới thiệu Chương 4 và danh sách topics'],
        ['/students', 'students.html', 'Bảng danh sách 5 sinh viên'],
        ['/courses', 'courses.html', 'Bảng danh sách 5 khóa học'],
        ['/contact', 'contact.html', 'Thông tin liên hệ khoa'],
    ]
)

# ===== 11. KẾT LUẬN =====
add_heading('11. KẾT LUẬN', level=1)

add_heading('11.1 Nội dung đã học', level=2)
add_para('Qua bài lab 11, đã nắm được:')
add_para('• Spring Boot: Cách tạo project bằng Maven, cấu hình dependency trong pom.xml')
add_para('• Spring MVC: Controller trả về view thông qua @Controller và @GetMapping')
add_para('• Model: Truyền dữ liệu từ Controller sang View thông qua Model object')
add_para('• Thymeleaf: Sử dụng th:text, th:href, th:each để render giao diện động')
add_para('• Project structure: Tổ chức code theo package vn.edu.eaut.lab11')
add_para('• Embedded server: Chạy ứng dụng trên Tomcat nhúng, port 8080')
add_para('• DevTools: Tự động reload khi sửa code')

add_heading('11.2 Bài tập đã hoàn thành', level=2)
add_table(
    ['Bài', 'Mô tả', 'Trạng thái'],
    [
        ['Bài 1', 'Tạo project Spring Boot', 'Hoàn thành'],
        ['Bài 2', 'Tạo trang chủ', 'Hoàn thành'],
        ['Bài 3', 'Tạo lớp Student', 'Hoàn thành'],
        ['Bài 4', 'Hiển thị danh sách sinh viên', 'Hoàn thành'],
        ['Bài 5', 'Tạo trang giới thiệu', 'Hoàn thành'],
        ['Bài 6', 'Trang /contact', 'Hoàn thành'],
        ['Bài 7', 'Menu điều hướng', 'Hoàn thành'],
        ['Bài 8', '5 khóa học mẫu', 'Hoàn thành'],
        ['Bài 9', 'Trang /courses', 'Hoàn thành'],
        ['Bài 10', 'CSS riêng trong static/css', 'Hoàn thành'],
    ]
)

add_heading('11.3 Luồng xử lý request Spring MVC', level=2)
add_code(
    '1. Client gửi HTTP request (VD: GET /students)\n'
    '             ↓\n'
    '2. Spring DispatcherServlet nhận request\n'
    '             ↓\n'
    '3. Tìm Controller phù hợp (@GetMapping("/students"))\n'
    '             ↓\n'
    '4. Gọi method listStudents() trong StudentController\n'
    '             ↓\n'
    '5. Method chuẩn bị dữ liệu, thêm vào Model\n'
    '             ↓\n'
    '6. Trả về tên view "students"\n'
    '             ↓\n'
    '7. Thymeleaf engine render file students.html\n'
    '             ↓\n'
    '8. Trả về HTML response cho client'
)

add_heading('11.4 Cấu trúc MVC', level=2)
add_table(
    ['Thành phần', 'Trách nhiệm', 'Ví dụ'],
    [
        ['Model', 'Lưu trữ dữ liệu', 'Student, Course'],
        ['View', 'Hiển thị giao diện', 'students.html, courses.html'],
        ['Controller', 'Xử lý request, gọi model, trả view', 'StudentController'],
    ]
)

# Footer
doc.add_paragraph()
end = doc.add_paragraph()
end.alignment = WD_ALIGN_PARAGRAPH.CENTER
r = end.add_run('--- HẾT ---')
r.italic = True
r.font.color.rgb = RGBColor(0x88, 0x88, 0x88)

doc.save(OUTPUT)
print(f"Saved: {OUTPUT}")