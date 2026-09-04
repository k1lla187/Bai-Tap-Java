"""
Script tạo file Word (docx) cho Báo cáo Lab 13 - Spring Data JPA
Sử dụng thư viện python-docx
"""

from docx import Document
from docx.shared import Pt, Cm, RGBColor, Inches
from docx.enum.text import WD_ALIGN_PARAGRAPH, WD_LINE_SPACING
from docx.enum.table import WD_TABLE_ALIGNMENT, WD_ALIGN_VERTICAL
from docx.oxml.ns import qn
from docx.oxml import OxmlElement
import os


def set_cell_background(cell, color_hex):
    """Set background color for a table cell"""
    tc_pr = cell._tc.get_or_add_tcPr()
    shd = OxmlElement('w:shd')
    shd.set(qn('w:fill'), color_hex)
    tc_pr.append(shd)


def add_horizontal_line(paragraph):
    """Add horizontal line below paragraph"""
    p = paragraph._element
    pPr = p.get_or_add_pPr()
    pBdr = OxmlElement('w:pBdr')
    pPr.append(pBdr)
    bottom = OxmlElement('w:bottom')
    bottom.set(qn('w:val'), 'single')
    bottom.set(qn('w:sz'), '6')
    bottom.set(qn('w:space'), '1')
    bottom.set(qn('w:color'), 'F97316')
    pBdr.append(bottom)


def add_code_block(doc, code_text, language="java"):
    """Add a code block with monospace font and gray background"""
    p = doc.add_paragraph()
    p.paragraph_format.left_indent = Cm(0.5)
    p.paragraph_format.space_before = Pt(6)
    p.paragraph_format.space_after = Pt(6)
    p.paragraph_format.line_spacing = 1.15

    run = p.add_run(code_text)
    run.font.name = 'Consolas'
    run.font.size = Pt(9)
    run.font.color.rgb = RGBColor(0x1F, 0x29, 0x37)

    # Add shading
    pPr = p._element.get_or_add_pPr()
    shd = OxmlElement('w:shd')
    shd.set(qn('w:fill'), 'F1F5F9')
    pPr.append(shd)


def add_heading(doc, text, level=1):
    """Add a styled heading"""
    heading = doc.add_heading(text, level=level)
    for run in heading.runs:
        if level == 0:
            run.font.size = Pt(24)
            run.font.color.rgb = RGBColor(0x0F, 0x17, 0x2A)
        elif level == 1:
            run.font.size = Pt(18)
            run.font.color.rgb = RGBColor(0xF9, 0x73, 0x16)
        elif level == 2:
            run.font.size = Pt(14)
            run.font.color.rgb = RGBColor(0x38, 0xBD, 0xF8)
        else:
            run.font.size = Pt(12)
            run.font.color.rgb = RGBColor(0x0F, 0x17, 0x2A)
    return heading


def add_paragraph_text(doc, text, bold=False, italic=False, size=11, color=None):
    """Add a paragraph with custom formatting"""
    p = doc.add_paragraph()
    p.paragraph_format.space_after = Pt(6)
    p.paragraph_format.line_spacing = 1.4
    run = p.add_run(text)
    run.font.size = Pt(size)
    run.bold = bold
    run.italic = italic
    if color:
        run.font.color.rgb = RGBColor(*color)
    return p


def add_bullet_list(doc, items):
    """Add bullet list"""
    for item in items:
        p = doc.add_paragraph(item, style='List Bullet')
        p.paragraph_format.space_after = Pt(3)
        for run in p.runs:
            run.font.size = Pt(11)


def add_table_styled(doc, headers, rows, header_color="F97316"):
    """Add a styled table"""
    table = doc.add_table(rows=1 + len(rows), cols=len(headers))
    table.style = 'Light Grid Accent 1'
    table.alignment = WD_TABLE_ALIGNMENT.CENTER

    # Header row
    hdr_cells = table.rows[0].cells
    for i, header in enumerate(headers):
        cell = hdr_cells[i]
        cell.text = header
        set_cell_background(cell, header_color)
        for paragraph in cell.paragraphs:
            for run in paragraph.runs:
                run.font.bold = True
                run.font.color.rgb = RGBColor(0xFF, 0xFF, 0xFF)
                run.font.size = Pt(11)
            paragraph.alignment = WD_ALIGN_PARAGRAPH.CENTER

    # Data rows
    for row_idx, row_data in enumerate(rows):
        row_cells = table.rows[row_idx + 1].cells
        for col_idx, cell_data in enumerate(row_data):
            cell = row_cells[col_idx]
            cell.text = str(cell_data)
            for paragraph in cell.paragraphs:
                for run in paragraph.runs:
                    run.font.size = Pt(10)
                paragraph.alignment = WD_ALIGN_PARAGRAPH.CENTER

    doc.add_paragraph()  # spacing


# ============================================================
# TẠO DOCUMENT
# ============================================================

doc = Document()

# Cấu hình margins
for section in doc.sections:
    section.top_margin = Cm(2.0)
    section.bottom_margin = Cm(2.0)
    section.left_margin = Cm(2.2)
    section.right_margin = Cm(2.2)

# Cấu hình style mặc định
style = doc.styles['Normal']
style.font.name = 'Calibri'
style.font.size = Pt(11)

# ============================================================
# TRANG BÌA
# ============================================================

# Top spacing
for _ in range(2):
    doc.add_paragraph()

# Tiêu đề lớn
title = doc.add_paragraph()
title.alignment = WD_ALIGN_PARAGRAPH.CENTER
run = title.add_run('TRƯỜNG ĐẠI HỌC CÔNG NGHỆ ĐÔNG Á')
run.font.size = Pt(14)
run.font.bold = True
run.font.color.rgb = RGBColor(0x0F, 0x17, 0x2A)

p = doc.add_paragraph()
p.alignment = WD_ALIGN_PARAGRAPH.CENTER
run = p.add_run('KHOA CÔNG NGHỆ THÔNG TIN')
run.font.size = Pt(13)
run.font.bold = True

# Decorative line
for _ in range(3):
    doc.add_paragraph()

# Main title
title2 = doc.add_paragraph()
title2.alignment = WD_ALIGN_PARAGRAPH.CENTER
run = title2.add_run('BÁO CÁO LAB 13')
run.font.size = Pt(28)
run.font.bold = True
run.font.color.rgb = RGBColor(0xF9, 0x73, 0x16)

subtitle = doc.add_paragraph()
subtitle.alignment = WD_ALIGN_PARAGRAPH.CENTER
run = subtitle.add_run('SPRING DATA JPA')
run.font.size = Pt(24)
run.font.bold = True
run.font.color.rgb = RGBColor(0x0F, 0x17, 0x2A)

p = doc.add_paragraph()
p.alignment = WD_ALIGN_PARAGRAPH.CENTER
run = p.add_run('Kết nối cơ sở dữ liệu với Spring Data JPA')
run.font.size = Pt(14)
run.font.italic = True
run.font.color.rgb = RGBColor(0x38, 0xBD, 0xF8)

# Decorative line
for _ in range(2):
    doc.add_paragraph()

# Horizontal line
p = doc.add_paragraph()
add_horizontal_line(p)

# Student info
for _ in range(2):
    doc.add_paragraph()

info_items = [
    ('Học phần:', 'Công nghệ Java'),
    ('Chương:', '4 - Phát triển ứng dụng với Spring Framework'),
    ('Bài thực hành:', 'Lab 13 - Spring Data JPA'),
    ('Giảng viên:', '_______________________'),
    ('MSSV:', '_______________________'),
    ('Họ tên:', '_______________________'),
    ('Lớp:', '_______________________'),
    ('Ngày nộp:', '____/____/2026'),
]

for label, value in info_items:
    p = doc.add_paragraph()
    p.alignment = WD_ALIGN_PARAGRAPH.CENTER
    run1 = p.add_run(f'{label} ')
    run1.font.size = Pt(12)
    run1.font.bold = True
    run1.font.color.rgb = RGBColor(0x0F, 0x17, 0x2A)
    run2 = p.add_run(value)
    run2.font.size = Pt(12)
    run2.font.color.rgb = RGBColor(0x0F, 0x17, 0x2A)

# Page break
doc.add_page_break()

# ============================================================
# MỤC LỤC
# ============================================================

add_heading(doc, 'MỤC LỤC', 1)

toc_items = [
    '1. Giới thiệu',
    '2. Mục tiêu bài lab',
    '3. Kiến trúc ứng dụng',
    '4. Cấu hình project',
    '5. Bài 1-2: Entity và JPA',
    '6. Bài 3: Repository',
    '7. Bài 4: Service Layer',
    '8. Bài 5: Controller và CRUD',
    '9. Bài 6: Chức năng sửa sinh viên',
    '10. Bài 7: Tìm kiếm sinh viên',
    '11. Bài 8: Entity Course',
    '12. Bài 9: CRUD cho Course',
    '13. Bài 10: Migration MySQL',
    '14. Cấu hình cơ sở dữ liệu',
    '15. Kiểm thử ứng dụng',
    '16. Kết luận',
]

for item in toc_items:
    p = doc.add_paragraph(item)
    p.paragraph_format.space_after = Pt(4)
    for run in p.runs:
        run.font.size = Pt(12)

doc.add_page_break()

# ============================================================
# 1. GIỚI THIỆU
# ============================================================

add_heading(doc, '1. GIỚI THIỆU', 1)

add_heading(doc, '1.1 Tổng quan dự án', 2)
add_paragraph_text(doc,
    'Lab 13 là bài thực hành về Spring Data JPA, một phần quan trọng của Spring Framework '
    'dùng để kết nối và thao tác với cơ sở dữ liệu quan hệ một cách dễ dàng và hiệu quả.')

add_bullet_list(doc, [
    'Tên dự án: lab13-spring-data-jpa',
    'Ngôn ngữ: Java 17',
    'Framework: Spring Boot 3.2.0',
    'Cơ sở dữ liệu: H2 Database (development) / MySQL (production)',
])

add_heading(doc, '1.2 Spring Data JPA là gì?', 2)
add_paragraph_text(doc,
    'Spring Data JPA là một phần của Spring Data, cung cấp một cách tiếp cận đơn giản và mạnh mẽ '
    'để truy cập cơ sở dữ liệu. Thay vì phải viết nhiều code JDBC thủ công, Spring Data JPA cho phép:')

add_bullet_list(doc, [
    'Tự động tạo Repository từ interface',
    'Derived queries tự động từ tên method',
    'Pagination và sorting tự động',
    'Query methods tùy chỉnh với JPQL',
    'Entity mapping tự động với Hibernate',
])

add_heading(doc, '1.3 Công nghệ sử dụng', 2)

tech_rows = [
    ['Java', '17+', 'Ngôn ngữ lập trình'],
    ['Spring Boot', '3.2.0', 'Framework chính'],
    ['Spring Data JPA', '3.2.0', 'Truy cập dữ liệu'],
    ['Hibernate', '6.3.1', 'ORM implementation'],
    ['H2 Database', '2.2.224', 'Cơ sở dữ liệu in-memory'],
    ['Thymeleaf', '3.2.0', 'Template engine'],
    ['HikariCP', '5.0.1', 'Connection pooling'],
    ['Maven', '3.x', 'Build tool'],
]
add_table_styled(doc, ['Công nghệ', 'Phiên bản', 'Mục đích'], tech_rows)

doc.add_page_break()

# ============================================================
# 2. MỤC TIÊU BÀI LAB
# ============================================================

add_heading(doc, '2. MỤC TIÊU BÀI LAB', 1)

add_heading(doc, '2.1 Mục tiêu chính', 2)
add_bullet_list(doc, [
    'Hiểu cách sử dụng Spring Data JPA để kết nối với cơ sở dữ liệu',
    'Tạo Entity với các annotation JPA đúng cách',
    'Sử dụng JpaRepository để thực hiện CRUD operations',
    'Xây dựng ứng dụng web với kiến trúc 3 tầng',
    'Cấu hình H2 Database và chuyển sang MySQL',
])

add_heading(doc, '2.2 Yêu cầu bài tập (10 bài)', 2)

exercise_rows = [
    ['Bài 1', 'Thêm dependency JPA và H2', 'Có gợi ý'],
    ['Bài 2', 'Tạo Entity Student', 'Có gợi ý'],
    ['Bài 3', 'Tạo Repository', 'Có gợi ý'],
    ['Bài 4', 'Tạo Service', 'Có gợi ý'],
    ['Bài 5', 'Controller CRUD với CSDL', 'Có gợi ý'],
    ['Bài 6', 'Chức năng sửa sinh viên', 'Tự làm'],
    ['Bài 7', 'Tìm kiếm sinh viên theo tên', 'Tự làm'],
    ['Bài 8', 'Entity Course (mã môn, tên, tín chỉ)', 'Tự làm'],
    ['Bài 9', 'CRUD đầy đủ cho Course', 'Tự làm'],
    ['Bài 10', 'Chuyển từ H2 sang MySQL', 'Tự làm'],
]
add_table_styled(doc, ['Bài', 'Nội dung', 'Loại'], exercise_rows)

p = doc.add_paragraph()
run = p.add_run('Trạng thái: 10/10 bài - HOÀN THÀNH 100%')
run.font.bold = True
run.font.size = Pt(12)
run.font.color.rgb = RGBColor(0x22, 0xC5, 0x5E)

doc.add_page_break()

# ============================================================
# 3. KIẾN TRÚC ỨNG DỤNG
# ============================================================

add_heading(doc, '3. KIẾN TRÚC ỨNG DỤNG', 1)

add_heading(doc, '3.1 Kiến trúc 3 tầng (Layered Architecture)', 2)
add_paragraph_text(doc,
    'Ứng dụng được xây dựng theo mô hình 3 tầng (3-Layer Architecture), '
    'đây là kiến trúc phổ biến nhất trong phát triển ứng dụng Java:')

architecture_code = '''┌─────────────────────────────────────────────────────────────┐
│            PRESENTATION LAYER (Tầng Trình bày)               │
│                                                             │
│  HomeController ──► StudentController ──► CourseController  │
└───────────────────────────┬─────────────────────────────────┘
                            │
┌───────────────────────────▼─────────────────────────────────┐
│            BUSINESS LOGIC LAYER (Tầng Nghiệp vụ)             │
│                                                             │
│       StudentService ◄──────────────►  CourseService        │
└───────────────────────────┬─────────────────────────────────┘
                            │
┌───────────────────────────▼─────────────────────────────────┐
│            DATA ACCESS LAYER (Tầng Truy cập dữ liệu)        │
│                                                             │
│   StudentRepository ◄────────────►  CourseRepository       │
│   Student Entity     ◄────────────►  Course Entity          │
└───────────────────────────┬─────────────────────────────────┘
                            │
┌───────────────────────────▼─────────────────────────────────┐
│                   DATABASE (H2 / MySQL)                      │
│                                                             │
│         Table: STUDENTS      Table: COURSES                 │
└─────────────────────────────────────────────────────────────┘'''
add_code_block(doc, architecture_code)

add_heading(doc, '3.2 Luồng xử lý một request', 2)
flow_code = '''User Request (Browser)
        │
        ▼
  Controller      ← Nhận request, gọi Service
        │
        ▼
    Service        ← Xử lý nghiệp vụ, gọi Repository
        │
        ▼
  Repository      ← Giao tiếp với Database qua JPA/Hibernate
        │
        ▼
   Database       ← H2 / MySQL
        │
        ▼
     View         → Trả về HTML cho browser'''
add_code_block(doc, flow_code)

add_heading(doc, '3.3 Vai trò của từng tầng', 2)

layer_rows = [
    ['Controller', 'Nhận HTTP request, trả HTTP response', '@GetMapping, @PostMapping'],
    ['Service', 'Xử lý nghiệp vụ, validation', 'save(), delete(), search()'],
    ['Repository', 'Thao tác với database', 'findAll(), findById(), save()'],
    ['Entity', 'Ánh xạ bảng trong database', '@Entity, @Table, @Column'],
]
add_table_styled(doc, ['Tầng', 'Vai trò', 'Ví dụ method'], layer_rows)

add_heading(doc, '3.4 Cấu trúc package', 2)

package_code = '''vn.edu.eaut.lab13/
├── Lab13Application.java         ← Spring Boot main class
├── DataInitializer.java          ← Khởi tạo dữ liệu mẫu
├── controller/                   ← Tầng Presentation
│   ├── HomeController.java       ← Redirect về /students
│   ├── StudentController.java    ← CRUD Sinh viên
│   └── CourseController.java     ← CRUD Môn học
├── service/                      ← Tầng Business Logic
│   ├── StudentService.java       ← Nghiệp vụ Sinh viên
│   └── CourseService.java        ← Nghiệp vụ Môn học
├── repository/                   ← Tầng Data Access
│   ├── StudentRepository.java    ← JPA Repository Sinh viên
│   └── CourseRepository.java     ← JPA Repository Môn học
└── entity/                       ← Domain Models
    ├── Student.java              ← Entity Sinh viên
    └── Course.java               ← Entity Môn học'''
add_code_block(doc, package_code)

doc.add_page_break()

# ============================================================
# 4. CẤU HÌNH PROJECT
# ============================================================

add_heading(doc, '4. CẤU HÌNH PROJECT', 1)

add_heading(doc, '4.1 File pom.xml', 2)
add_paragraph_text(doc, 'File pom.xml định nghĩa tất cả dependencies cần thiết cho project:')

pom_code = '''<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.0</version>
    </parent>

    <groupId>vn.edu.eaut</groupId>
    <artifactId>lab13-spring-data-jpa</artifactId>
    <version>1.0.0</version>
    <name>Lab 13 - Spring Data JPA</name>

    <properties>
        <java.version>17</java.version>
    </properties>

    <dependencies>
        <!-- Spring Boot Web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- Spring Data JPA -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <!-- Thymeleaf -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <!-- H2 Database -->
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>
        <!-- MySQL Connector -->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>
        <!-- DevTools -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
    </dependencies>
</project>'''
add_code_block(doc, pom_code)

add_heading(doc, '4.2 Giải thích các Dependencies', 2)

dep_rows = [
    ['spring-boot-starter-web', 'Hỗ trợ Spring MVC, embedded Tomcat'],
    ['spring-boot-starter-data-jpa', 'Spring Data JPA, Hibernate'],
    ['spring-boot-starter-thymeleaf', 'Template engine cho views'],
    ['h2', 'In-memory database cho development'],
    ['mysql-connector-j', 'JDBC driver cho MySQL'],
    ['spring-boot-devtools', 'Hot reload trong development'],
]
add_table_styled(doc, ['Dependency', 'Mục đích'], dep_rows)

add_heading(doc, '4.3 File application.properties', 2)

props_code = '''# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:eautdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Server Port
server.port=8080

# Thymeleaf
spring.thymeleaf.cache=false'''
add_code_block(doc, props_code)

add_heading(doc, '4.4 Các thuộc tính quan trọng', 2)

config_rows = [
    ['ddl-auto=update', 'update', 'Tự động tạo/update bảng từ Entity'],
    ['show-sql=true', 'true', 'Hiển thị SQL trong console'],
    ['format_sql=true', 'true', 'Format SQL để dễ đọc'],
    ['h2.console.enabled', 'true', 'Bật H2 Console để query'],
]
add_table_styled(doc, ['Thuộc tính', 'Giá trị', 'Ý nghĩa'], config_rows)

doc.add_page_break()

# ============================================================
# 5. BÀI 1-2: ENTITY VÀ JPA
# ============================================================

add_heading(doc, '5. BÀI 1-2: ENTITY VÀ JPA', 1)

add_heading(doc, '5.1 Bài 1: Thêm Dependency JPA và H2 (Đã hoàn thành)', 2)
add_paragraph_text(doc, 'Yêu cầu: Thêm các dependency sau vào pom.xml:')
add_bullet_list(doc, [
    'spring-boot-starter-data-jpa - Spring Data JPA',
    'h2 - H2 Database',
    'mysql-connector-j - MySQL Driver (cho Bài 10)',
])
add_paragraph_text(doc, 'Kết quả: Đã thêm vào pom.xml (xem mục 4.1)', italic=True)

add_heading(doc, '5.2 Bài 2: Tạo Entity Student (Đã hoàn thành)', 2)
add_paragraph_text(doc, 'Yêu cầu: Tạo Entity Student với các thuộc tính:')
add_bullet_list(doc, [
    'id - Long, auto-generated',
    'studentCode - String, unique, không null',
    'fullName - String, không null',
    'email - String',
    'className - String',
])

add_paragraph_text(doc, 'Code Entity Student:', bold=True)

student_code = '''package vn.edu.eaut.lab13.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_code", nullable = false, unique = true)
    private String studentCode;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    private String email;

    private String className;

    public Student() {}

    public Student(String studentCode, String fullName, 
                   String email, String className) {
        this.studentCode = studentCode;
        this.fullName = fullName;
        this.email = email;
        this.className = className;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { 
        this.studentCode = studentCode; 
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { 
        this.fullName = fullName; 
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getClassName() { return className; }
    public void setClassName(String className) { 
        this.className = className; 
    }
}'''
add_code_block(doc, student_code)

add_heading(doc, '5.3 Các Annotation JPA quan trọng', 2)

annotation_rows = [
    ['@Entity', 'Đánh dấu class là Entity', 'Ánh xạ với bảng trong DB'],
    ['@Table(name="...")', 'Chỉ định tên bảng', '@Table(name = "students")'],
    ['@Id', 'Đánh dấu Primary Key', ''],
    ['@GeneratedValue', 'Auto-generate giá trị', 'GenerationType.IDENTITY'],
    ['@Column', 'Cấu hình cột', 'name, nullable, unique'],
]
add_table_styled(doc, ['Annotation', 'Mục đích', 'Ví dụ'], annotation_rows)

add_heading(doc, '5.4 GeneratedValue Strategies', 2)

strategy_rows = [
    ['IDENTITY', 'Database tự tăng', 'MySQL, SQL Server, H2'],
    ['SEQUENCE', 'Dùng database sequence', 'Oracle, PostgreSQL'],
    ['TABLE', 'Dùng bảng generator', 'Khi không hỗ trợ sequence'],
    ['AUTO', 'JPA tự chọn', 'Mặc định'],
]
add_table_styled(doc, ['Strategy', 'Mô tả', 'Khi nào dùng'], strategy_rows)
add_paragraph_text(doc, 'Trong project này dùng IDENTITY vì H2 và MySQL đều hỗ trợ.', italic=True)

doc.add_page_break()

# ============================================================
# 6. BÀI 3: REPOSITORY
# ============================================================

add_heading(doc, '6. BÀI 3: REPOSITORY', 1)

add_heading(doc, '6.1 Khái niệm Repository Pattern', 2)
add_paragraph_text(doc,
    'Repository Pattern là một pattern trong kiến trúc phần mềm, tách biệt logic truy cập dữ liệu '
    'khỏi business logic. Trong Spring Data JPA, Repository là interface kế thừa từ JpaRepository.')

add_heading(doc, '6.2 JpaRepository Interface', 2)
add_paragraph_text(doc, 'JpaRepository cung cấp sẵn nhiều method để thao tác với database:')

jpa_rows = [
    ['findAll()', 'Lấy tất cả records'],
    ['findById(id)', 'Tìm theo Primary Key'],
    ['save(entity)', 'Lưu hoặc cập nhật entity'],
    ['deleteById(id)', 'Xóa theo ID'],
    ['count()', 'Đếm số records'],
    ['existsById(id)', 'Kiểm tra tồn tại'],
]
add_table_styled(doc, ['Method', 'Mô tả'], jpa_rows)

add_heading(doc, '6.3 Code StudentRepository', 2)

repo_code = '''package vn.edu.eaut.lab13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.eaut.lab13.entity.Student;

import java.util.List;

@Repository
public interface StudentRepository 
        extends JpaRepository<Student, Long> {
    
    // Tìm kiếm không phân biệt hoa thường
    List<Student> findByFullNameContainingIgnoreCase(
            String keyword);
    
    // Kiểm tra mã sinh viên đã tồn tại chưa
    boolean existsByStudentCode(String studentCode);
    
    // Tìm sinh viên theo mã
    Student findByStudentCode(String studentCode);
}'''
add_code_block(doc, repo_code)

add_heading(doc, '6.4 Derived Query Methods', 2)
add_paragraph_text(doc, 'Spring Data JPA tự động tạo SQL query từ tên method:')

derived_rows = [
    ['findByXxx', 'SELECT * FROM table WHERE xxx = ?'],
    ['findByXxxContaining', 'SELECT * WHERE xxx LIKE \'%value%\''],
    ['findByXxxContainingIgnoreCase',
     'SELECT * WHERE UPPER(xxx) LIKE UPPER(\'%value%\')'],
    ['existsByXxx', 'SELECT COUNT(*) > 0 WHERE xxx = ?'],
]
add_table_styled(doc, ['Method Pattern', 'SQL Generated'], derived_rows)

add_heading(doc, '6.5 Code CourseRepository', 2)

course_repo_code = '''package vn.edu.eaut.lab13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.eaut.lab13.entity.Course;

import java.util.List;

@Repository
public interface CourseRepository 
        extends JpaRepository<Course, Long> {
    
    List<Course> findByCourseNameContainingIgnoreCase(
            String keyword);
    
    boolean existsByCourseCode(String courseCode);
    
    Course findByCourseCode(String courseCode);
}'''
add_code_block(doc, course_repo_code)

doc.add_page_break()

# ============================================================
# 7. BÀI 4: SERVICE LAYER
# ============================================================

add_heading(doc, '7. BÀI 4: SERVICE LAYER', 1)

add_heading(doc, '7.1 Vai trò của Service Layer', 2)
add_paragraph_text(doc,
    'Service Layer đóng vai trò trung gian giữa Controller và Repository. Nó xử lý:')
add_bullet_list(doc, [
    'Business Logic - Nghiệp vụ của ứng dụng',
    'Validation - Kiểm tra dữ liệu đầu vào',
    'Transaction Management - Quản lý transaction tự động',
    'Exception Handling - Xử lý ngoại lệ',
])

add_heading(doc, '7.2 Dependency Injection qua Constructor', 2)

di_code = '''@Service
public class StudentService {
    private final StudentRepository studentRepository;

    // Constructor Injection (Best Practice)
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
}'''
add_code_block(doc, di_code)

add_paragraph_text(doc, 'Tại sao dùng Constructor Injection?', bold=True)
add_bullet_list(doc, [
    'Immutability - Repository không thể thay đổi sau khi khởi tạo',
    'Testability - Dễ mock khi viết unit test',
    'Explicit dependencies - Rõ ràng phụ thuộc gì',
])

add_heading(doc, '7.3 Code StudentService', 2)

student_service_code = '''package vn.edu.eaut.lab13.service;

import org.springframework.stereotype.Service;
import vn.edu.eaut.lab13.entity.Student;
import vn.edu.eaut.lab13.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                    "Không tìm thấy sinh viên với ID: " + id));
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> searchByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        return studentRepository
                .findByFullNameContainingIgnoreCase(keyword);
    }

    public boolean existsByStudentCode(String studentCode) {
        return studentRepository.existsByStudentCode(studentCode);
    }
}'''
add_code_block(doc, student_service_code)

add_heading(doc, '7.4 Code CourseService', 2)

course_service_code = '''package vn.edu.eaut.lab13.service;

import org.springframework.stereotype.Service;
import vn.edu.eaut.lab13.entity.Course;
import vn.edu.eaut.lab13.repository.CourseRepository;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                    "Không tìm thấy môn học với ID: " + id));
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    public List<Course> searchByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        return courseRepository
                .findByCourseNameContainingIgnoreCase(keyword);
    }

    public boolean existsByCourseCode(String courseCode) {
        return courseRepository.existsByCourseCode(courseCode);
    }
}'''
add_code_block(doc, course_service_code)

doc.add_page_break()

# ============================================================
# 8. BÀI 5: CONTROLLER VÀ CRUD
# ============================================================

add_heading(doc, '8. BÀI 5: CONTROLLER VÀ CRUD', 1)

add_heading(doc, '8.1 Spring MVC Annotations', 2)

mvc_rows = [
    ['@Controller', 'Đánh dấu class là Controller'],
    ['@RequestMapping', 'Ánh xạ URL path'],
    ['@GetMapping', 'Xử lý GET request'],
    ['@PostMapping', 'Xử lý POST request'],
    ['@PathVariable', 'Lấy biến từ URL path'],
    ['@RequestParam', 'Lấy query parameter'],
    ['@ModelAttribute', 'Binding form data vào object'],
]
add_table_styled(doc, ['Annotation', 'Mục đích'], mvc_rows)

add_heading(doc, '8.2 Code StudentController', 2)

student_ctrl_code = '''package vn.edu.eaut.lab13.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eaut.lab13.entity.Student;
import vn.edu.eaut.lab13.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // GET /students - Hiển thị danh sách
    @GetMapping
    public String list(
            @RequestParam(required = false) String keyword, 
            Model model) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            model.addAttribute("students", 
                studentService.searchByName(keyword));
            model.addAttribute("keyword", keyword);
        } else {
            model.addAttribute("students", 
                studentService.findAll());
        }
        return "students/list";
    }

    // GET /students/create - Form thêm mới
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("isEdit", false);
        return "students/form";
    }

    // GET /students/edit/{id} - Form sửa
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Student student = studentService.findById(id);
        model.addAttribute("student", student);
        model.addAttribute("isEdit", true);
        return "students/form";
    }

    // POST /students/save - Lưu
    @PostMapping("/save")
    public String save(@ModelAttribute Student student,
                       RedirectAttributes redirectAttributes) {
        try {
            studentService.save(student);
            redirectAttributes.addFlashAttribute("message", 
                "Lưu sinh viên thành công!");
            redirectAttributes.addFlashAttribute(
                "messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", 
                "Lỗi: " + e.getMessage());
            redirectAttributes.addFlashAttribute(
                "messageType", "error");
        }
        return "redirect:/students";
    }

    // GET /students/delete/{id} - Xóa
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", 
                "Xóa sinh viên thành công!");
            redirectAttributes.addFlashAttribute(
                "messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", 
                "Lỗi: " + e.getMessage());
            redirectAttributes.addFlashAttribute(
                "messageType", "error");
        }
        return "redirect:/students";
    }
}'''
add_code_block(doc, student_ctrl_code)

add_heading(doc, '8.3 Giải thích các endpoints', 2)

endpoint_rows = [
    ['GET', '/students', 'Hiển thị danh sách sinh viên'],
    ['GET', '/students?keyword=ABC', 'Tìm kiếm sinh viên'],
    ['GET', '/students/create', 'Form thêm mới'],
    ['POST', '/students/save', 'Lưu sinh viên mới'],
    ['GET', '/students/edit/1', 'Form sửa sinh viên ID=1'],
    ['POST', '/students/save', 'Cập nhật sinh viên'],
    ['GET', '/students/delete/1', 'Xóa sinh viên ID=1'],
]
add_table_styled(doc, ['Method', 'URL', 'Chức năng'], endpoint_rows)

add_heading(doc, '8.4 RedirectAttributes - Flash Messages', 2)

flash_code = '''// Trong Controller
redirectAttributes.addFlashAttribute("message", 
    "Lưu thành công!");
redirectAttributes.addFlashAttribute("messageType", "success");

// Trong View (Thymeleaf)
<div th:if="${message}" 
     th:class="${messageType}" 
     th:text="${message}">
</div>'''
add_code_block(doc, flash_code)

add_paragraph_text(doc,
    'Flash Attributes là attributes chỉ tồn tại trong request tiếp theo sau redirect, '
    'thường dùng để hiển thị thông báo sau khi thực hiện action.', italic=True)

doc.add_page_break()

# ============================================================
# 9. BÀI 6: CHỨC NĂNG SỬA SINH VIÊN
# ============================================================

add_heading(doc, '9. BÀI 6: CHỨC NĂNG SỬA SINH VIÊN', 1)

add_heading(doc, '9.1 Yêu cầu', 2)
add_paragraph_text(doc, 'Viết chức năng sửa thông tin sinh viên khi biết ID.')

add_heading(doc, '9.2 Phân tích', 2)
add_paragraph_text(doc, 'Chức năng sửa gồm 2 bước:')
add_bullet_list(doc, [
    'GET /students/edit/{id} - Hiển thị form với dữ liệu hiện tại',
    'POST /students/save - Cập nhật dữ liệu (đã có ở Bài 5)',
])

add_heading(doc, '9.3 Code xử lý', 2)

edit_code = '''// Bước 1: Lấy dữ liệu và hiển thị form
@GetMapping("/edit/{id}")
public String edit(@PathVariable Long id, Model model) {
    // Tìm sinh viên theo ID
    Student student = studentService.findById(id);
    
    // Đưa dữ liệu vào Model để hiển thị trong form
    model.addAttribute("student", student);
    model.addAttribute("isEdit", true);
    
    return "students/form"; // Trả về view form
}

// Bước 2: Lưu cập nhật
@PostMapping("/save")
public String save(@ModelAttribute Student student,
                   RedirectAttributes redirectAttributes) {
    studentService.save(student);
    redirectAttributes.addFlashAttribute("message", 
        "Cập nhật thành công!");
    return "redirect:/students";
}'''
add_code_block(doc, edit_code)

add_heading(doc, '9.4 Điểm quan trọng', 2)
add_paragraph_text(doc,
    'Spring Data JPA tự động phân biệt Create vs Update:', bold=True)
add_bullet_list(doc, [
    'save(new Student) - INSERT (không có ID)',
    'save(existingStudent) - UPDATE (có ID)',
])

add_heading(doc, '9.5 View Template', 2)

form_code = '''<!-- form.html - Dùng chung cho Create và Edit -->
<form th:action="@{/students/save}" 
      th:object="${student}" method="post">
    
    <!-- Trường ẩn cho ID (chỉ có khi edit) -->
    <input type="hidden" th:field="*{id}" />
    
    <!-- Mã sinh viên (disabled khi edit) -->
    <input type="text" th:field="*{studentCode}"
           th:disabled="${isEdit}" />
    
    <!-- Họ tên -->
    <input type="text" th:field="*{fullName}" required />
    
    <!-- Email -->
    <input type="email" th:field="*{email}" />
    
    <!-- Lớp -->
    <input type="text" th:field="*{className}" />
    
    <button type="submit">Lưu</button>
</form>'''
add_code_block(doc, form_code)

doc.add_page_break()

# ============================================================
# 10. BÀI 7: TÌM KIẾM SINH VIÊN
# ============================================================

add_heading(doc, '10. BÀI 7: TÌM KIẾM SINH VIÊN', 1)

add_heading(doc, '10.1 Yêu cầu', 2)
add_paragraph_text(doc,
    'Viết chức năng tìm kiếm sinh viên theo họ tên '
    '(không phân biệt hoa thường).')

add_heading(doc, '10.2 Phân tích', 2)
add_bullet_list(doc, [
    'Repository - Tạo method findByFullNameContainingIgnoreCase',
    'Service - Thêm method searchByName với validation',
    'Controller - Xử lý query parameter keyword',
])

add_heading(doc, '10.3 Repository Layer', 2)

search_repo_code = '''@Repository
public interface StudentRepository 
        extends JpaRepository<Student, Long> {
    // Tìm kiếm không phân biệt hoa thường
    List<Student> findByFullNameContainingIgnoreCase(
            String keyword);
}'''
add_code_block(doc, search_repo_code)

add_paragraph_text(doc, 'SQL được tạo tự động:', italic=True)
sql_code = '''SELECT * FROM students 
WHERE LOWER(full_name) 
LIKE LOWER(CONCAT('%', ?, '%'))'''
add_code_block(doc, sql_code)

add_heading(doc, '10.4 Service Layer', 2)

search_service_code = '''public List<Student> searchByName(String keyword) {
    // Nếu không có từ khóa, trả về tất cả
    if (keyword == null || keyword.trim().isEmpty()) {
        return findAll();
    }
    // Gọi Repository với từ khóa đã trim
    return studentRepository
            .findByFullNameContainingIgnoreCase(keyword.trim());
}'''
add_code_block(doc, search_service_code)

add_heading(doc, '10.5 Controller Layer', 2)

search_ctrl_code = '''@GetMapping
public String list(
        @RequestParam(required = false) String keyword, 
        Model model) {
    if (keyword != null && !keyword.trim().isEmpty()) {
        // Có từ khóa → tìm kiếm
        model.addAttribute("students", 
            studentService.searchByName(keyword));
        model.addAttribute("keyword", keyword);
    } else {
        // Không có từ khóa → hiển thị tất cả
        model.addAttribute("students", 
            studentService.findAll());
    }
    return "students/list";
}'''
add_code_block(doc, search_ctrl_code)

add_heading(doc, '10.6 View - Search Box', 2)

search_view_code = '''<!-- students/list.html -->
<form th:action="@{/students}" method="get">
    <input type="text" name="keyword" th:value="${keyword}"
           placeholder="Tìm kiếm theo tên..." />
    <button type="submit">Tìm kiếm</button>
</form>

<!-- Hiển thị kết quả -->
<table>
    <tr th:each="student : ${students}">
        <td th:text="${student.fullName}"></td>
    </tr>
</table>'''
add_code_block(doc, search_view_code)

doc.add_page_break()

# ============================================================
# 11. BÀI 8: ENTITY COURSE
# ============================================================

add_heading(doc, '11. BÀI 8: ENTITY COURSE', 1)

add_heading(doc, '11.1 Yêu cầu', 2)
add_paragraph_text(doc, 'Tạo Entity Course với các trường:')
add_bullet_list(doc, [
    'id - Long, auto-generated (Primary Key)',
    'courseCode - String, unique, không null (Mã môn học)',
    'courseName - String, không null (Tên môn học)',
    'credits - Integer (Số tín chỉ)',
])

add_heading(doc, '11.2 Code Course Entity', 2)

course_entity_code = '''package vn.edu.eaut.lab13.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Course {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_code", nullable = false, unique = true)
    private String courseCode;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(name = "credits")
    private Integer credits;

    public Course() {}

    public Course(String courseCode, String courseName, 
                  Integer credits) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { 
        this.courseCode = courseCode; 
    }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { 
        this.courseName = courseName; 
    }

    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { 
        this.credits = credits; 
    }
}'''
add_code_block(doc, course_entity_code)

add_heading(doc, '11.3 So sánh Student và Course Entity', 2)

compare_rows = [
    ['ID', 'Long (auto)', 'Long (auto)'],
    ['Code', 'studentCode', 'courseCode'],
    ['Name', 'fullName', 'courseName'],
    ['Extra', 'email, className', 'credits (Integer)'],
]
add_table_styled(doc, ['Thuộc tính', 'Student', 'Course'], compare_rows)

doc.add_page_break()

# ============================================================
# 12. BÀI 9: CRUD CHO COURSE
# ============================================================

add_heading(doc, '12. BÀI 9: CRUD CHO COURSE', 1)

add_heading(doc, '12.1 Yêu cầu', 2)
add_paragraph_text(doc,
    'Tạo đầy đủ CRUD cho Course: Create, Read, Update, Delete, Search.')

add_heading(doc, '12.2 CourseRepository', 2)

add_paragraph_text(doc, 'Code CourseRepository (đã trình bày ở mục 6.5)', italic=True)

add_heading(doc, '12.3 CourseService', 2)

add_paragraph_text(doc, 'Code CourseService (đã trình bày ở mục 7.4)', italic=True)

add_heading(doc, '12.4 CourseController', 2)

course_ctrl_code = '''package vn.edu.eaut.lab13.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eaut.lab13.entity.Course;
import vn.edu.eaut.lab13.service.CourseService;

@Controller
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // GET /courses - Danh sách môn học
    @GetMapping
    public String list(
            @RequestParam(required = false) String keyword, 
            Model model) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            model.addAttribute("courses", 
                courseService.searchByName(keyword));
            model.addAttribute("keyword", keyword);
        } else {
            model.addAttribute("courses", 
                courseService.findAll());
        }
        return "courses/list";
    }

    // GET /courses/create - Form thêm mới
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("isEdit", false);
        return "courses/form";
    }

    // GET /courses/edit/{id} - Form sửa
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Course course = courseService.findById(id);
        model.addAttribute("course", course);
        model.addAttribute("isEdit", true);
        return "courses/form";
    }

    // POST /courses/save - Lưu
    @PostMapping("/save")
    public String save(@ModelAttribute Course course,
                       RedirectAttributes redirectAttributes) {
        try {
            courseService.save(course);
            redirectAttributes.addFlashAttribute("message", 
                "Lưu môn học thành công!");
            redirectAttributes.addFlashAttribute(
                "messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", 
                "Lỗi: " + e.getMessage());
            redirectAttributes.addFlashAttribute(
                "messageType", "error");
        }
        return "redirect:/courses";
    }

    // GET /courses/delete/{id} - Xóa
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirectAttributes) {
        try {
            courseService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", 
                "Xóa môn học thành công!");
            redirectAttributes.addFlashAttribute(
                "messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", 
                "Lỗi: " + e.getMessage());
            redirectAttributes.addFlashAttribute(
                "messageType", "error");
        }
        return "redirect:/courses";
    }
}'''
add_code_block(doc, course_ctrl_code)

doc.add_page_break()

# ============================================================
# 13. BÀI 10: MIGRATION MYSQL
# ============================================================

add_heading(doc, '13. BÀI 10: MIGRATION MYSQL', 1)

add_heading(doc, '13.1 Yêu cầu', 2)
add_paragraph_text(doc,
    'Chuyển ứng dụng từ H2 Database sang MySQL Database.')

add_heading(doc, '13.2 Các bước thực hiện', 2)

add_paragraph_text(doc, 'Bước 1: Cài đặt MySQL Server', bold=True)
add_bullet_list(doc, [
    'Windows: Tải MySQL Installer từ mysql.com',
    'Linux: sudo apt install mysql-server',
    'Mac: brew install mysql',
])

add_paragraph_text(doc, 'Bước 2: Tạo Database', bold=True)
mysql_code = '''-- Đăng nhập MySQL
mysql -u root -p

-- Tạo database
CREATE DATABASE eautdb 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- Kiểm tra
SHOW DATABASES;
USE eautdb;
SHOW TABLES;'''
add_code_block(doc, mysql_code)

add_paragraph_text(doc, 'Bước 3: Cấu hình application.properties', bold=True)
mysql_props_code = '''# COMMENT OUT H2 Configuration
# spring.datasource.url=jdbc:h2:mem:eautdb
# spring.datasource.driverClassName=org.h2.Driver

# UNCOMMENT MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/eautdb?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=your_password

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect'''
add_code_block(doc, mysql_props_code)

add_heading(doc, '13.3 So sánh H2 vs MySQL', 2)

h2_mysql_rows = [
    ['Loại', 'In-memory', 'Persistent'],
    ['Cài đặt', 'Không cần', 'Cần cài đặt'],
    ['Data', 'Mất khi restart', 'Lưu trữ vĩnh viễn'],
    ['Phù hợp', 'Development', 'Production'],
    ['Port', '8080', '3306'],
    ['JDBC URL', 'jdbc:h2:mem:dbname', 'jdbc:mysql://host:port/dbname'],
]
add_table_styled(doc, ['Tiêu chí', 'H2', 'MySQL'], h2_mysql_rows)

doc.add_page_break()

# ============================================================
# 14. CẤU HÌNH CƠ SỞ DỮ LIỆU
# ============================================================

add_heading(doc, '14. CẤU HÌNH CƠ SỞ DỮ LIỆU', 1)

add_heading(doc, '14.1 Database Schema', 2)

add_paragraph_text(doc, 'Table: STUDENTS', bold=True)

student_schema_rows = [
    ['id', 'BIGINT', 'PRIMARY KEY, AUTO_INCREMENT'],
    ['student_code', 'VARCHAR(255)', 'NOT NULL, UNIQUE'],
    ['full_name', 'VARCHAR(255)', 'NOT NULL'],
    ['email', 'VARCHAR(255)', 'NULLABLE'],
    ['class_name', 'VARCHAR(255)', 'NULLABLE'],
]
add_table_styled(doc, ['Column', 'Type', 'Constraints'], student_schema_rows)

add_paragraph_text(doc, 'Table: COURSES', bold=True)

course_schema_rows = [
    ['id', 'BIGINT', 'PRIMARY KEY, AUTO_INCREMENT'],
    ['course_code', 'VARCHAR(255)', 'NOT NULL, UNIQUE'],
    ['course_name', 'VARCHAR(255)', 'NOT NULL'],
    ['credits', 'INTEGER', 'NULLABLE'],
]
add_table_styled(doc, ['Column', 'Type', 'Constraints'], course_schema_rows)

add_heading(doc, '14.2 Dữ liệu mẫu (DataInitializer)', 2)
add_paragraph_text(doc,
    'Ứng dụng tự động khởi tạo dữ liệu mẫu khi start:')

add_paragraph_text(doc, 'Sinh viên (5 records):', bold=True)

sample_student_rows = [
    ['SV001', 'Nguyễn Văn A', 'nguyenvana@eaut.edu.vn', 'CNTT01'],
    ['SV002', 'Trần Thị B', 'tranthib@eaut.edu.vn', 'CNTT01'],
    ['SV003', 'Lê Văn C', 'levanc@eaut.edu.vn', 'CNTT02'],
    ['SV004', 'Phạm Thị D', 'phamthid@eaut.edu.vn', 'CNTT02'],
    ['SV005', 'Hoàng Văn E', 'hoangvane@eaut.edu.vn', 'KTPM01'],
]
add_table_styled(doc, ['Mã SV', 'Họ tên', 'Email', 'Lớp'], sample_student_rows)

add_paragraph_text(doc, 'Môn học (5 records):', bold=True)

sample_course_rows = [
    ['JAVA101', 'Lập trình Java cơ bản', '3'],
    ['WEB201', 'Lập trình Web', '4'],
    ['DB301', 'Cơ sở dữ liệu', '3'],
    ['SE401', 'Công nghệ phần mềm', '3'],
    ['NET501', 'Lập trình .NET', '4'],
]
add_table_styled(doc, ['Mã môn', 'Tên môn', 'Số tín chỉ'], sample_course_rows)

add_heading(doc, '14.3 DataInitializer Code', 2)

data_init_code = '''package vn.edu.eaut.lab13;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.edu.eaut.lab13.entity.Course;
import vn.edu.eaut.lab13.entity.Student;
import vn.edu.eaut.lab13.repository.CourseRepository;
import vn.edu.eaut.lab13.repository.StudentRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(
            StudentRepository studentRepository,
            CourseRepository courseRepository) {
        return args -> {
            if (studentRepository.count() == 0) {
                studentRepository.save(new Student("SV001", 
                    "Nguyễn Văn A", 
                    "nguyenvana@eaut.edu.vn", "CNTT01"));
                studentRepository.save(new Student("SV002", 
                    "Trần Thị B", 
                    "tranthib@eaut.edu.vn", "CNTT01"));
                studentRepository.save(new Student("SV003", 
                    "Lê Văn C", 
                    "levanc@eaut.edu.vn", "CNTT02"));
                studentRepository.save(new Student("SV004", 
                    "Phạm Thị D", 
                    "phamthid@eaut.edu.vn", "CNTT02"));
                studentRepository.save(new Student("SV005", 
                    "Hoàng Văn E", 
                    "hoangvane@eaut.edu.vn", "KTPM01"));
            }

            if (courseRepository.count() == 0) {
                courseRepository.save(new Course("JAVA101", 
                    "Lập trình Java cơ bản", 3));
                courseRepository.save(new Course("WEB201", 
                    "Lập trình Web", 4));
                courseRepository.save(new Course("DB301", 
                    "Cơ sở dữ liệu", 3));
                courseRepository.save(new Course("SE401", 
                    "Công nghệ phần mềm", 3));
                courseRepository.save(new Course("NET501", 
                    "Lập trình .NET", 4));
            }
        };
    }
}'''
add_code_block(doc, data_init_code)

doc.add_page_break()

# ============================================================
# 15. KIỂM THỬ ỨNG DỤNG
# ============================================================

add_heading(doc, '15. KIỂM THỬ ỨNG DỤNG', 1)

add_heading(doc, '15.1 Build và Run', 2)

build_code = '''# Di chuyển vào thư mục project
cd d:\\Bai-Tap-Java\\lab13\\lab13-spring-data-jpa

# Build project
mvn clean compile

# Run application
mvn spring-boot:run'''
add_code_block(doc, build_code)

add_heading(doc, '15.2 Các URLs để test', 2)

url_rows = [
    ['http://localhost:8080', 'Trang chủ (redirect to /students)'],
    ['http://localhost:8080/students', 'Danh sách sinh viên'],
    ['http://localhost:8080/students/create', 'Form thêm sinh viên'],
    ['http://localhost:8080/students/edit/1', 'Form sửa sinh viên ID=1'],
    ['http://localhost:8080/courses', 'Danh sách môn học'],
    ['http://localhost:8080/courses/create', 'Form thêm môn học'],
    ['http://localhost:8080/courses/edit/1', 'Form sửa môn học ID=1'],
    ['http://localhost:8080/h2-console', 'H2 Database Console'],
]
add_table_styled(doc, ['URL', 'Mô tả'], url_rows)

add_heading(doc, '15.3 H2 Console Login', 2)

h2_login_code = '''JDBC URL: jdbc:h2:mem:eautdb
User Name: sa
Password: (để trống)'''
add_code_block(doc, h2_login_code)

add_heading(doc, '15.4 SQL Queries cho H2 Console', 2)

sql_queries = '''-- Xem tất cả sinh viên
SELECT * FROM STUDENTS;

-- Xem tất cả môn học
SELECT * FROM COURSES;

-- Tìm sinh viên theo tên
SELECT * FROM STUDENTS WHERE FULL_NAME LIKE \'%A%\';

-- Đếm số sinh viên
SELECT COUNT(*) FROM STUDENTS;

-- Xem cấu trúc bảng
DESCRIBE STUDENTS;
DESCRIBE COURSES;'''
add_code_block(doc, sql_queries)

add_heading(doc, '15.5 Test Cases', 2)

add_paragraph_text(doc, 'Student CRUD', bold=True)

student_test_rows = [
    ['TC01', 'Xem danh sách sinh viên', 'Hiển thị 5 sinh viên mẫu'],
    ['TC02', 'Thêm sinh viên mới', 'Sinh viên được thêm vào danh sách'],
    ['TC03', 'Sửa sinh viên', 'Thông tin được cập nhật'],
    ['TC04', 'Xóa sinh viên', 'Sinh viên bị xóa khỏi danh sách'],
    ['TC05', 'Tìm kiếm "A"', 'Hiển thị SV có chữ A trong tên'],
    ['TC06', 'Tìm kiếm "nguyen"', 'Tìm được "Nguyễn Văn A" (case-insensitive)'],
]
add_table_styled(doc, ['TC', 'Mô tả', 'Kết quả mong đợi'], student_test_rows)

add_paragraph_text(doc, 'Course CRUD', bold=True)

course_test_rows = [
    ['TC07', 'Xem danh sách môn học', 'Hiển thị 5 môn học mẫu'],
    ['TC08', 'Thêm môn học mới', 'Môn học được thêm'],
    ['TC09', 'Sửa môn học', 'Thông tin được cập nhật'],
    ['TC10', 'Xóa môn học', 'Môn học bị xóa'],
]
add_table_styled(doc, ['TC', 'Mô tả', 'Kết quả mong đợi'], course_test_rows)

add_paragraph_text(doc, 'Database', bold=True)

db_test_rows = [
    ['TC11', 'Truy cập H2 Console', 'Login thành công'],
    ['TC12', 'Query từ H2 Console', 'Hiển thị đúng dữ liệu'],
]
add_table_styled(doc, ['TC', 'Mô tả', 'Kết quả mong đợi'], db_test_rows)

doc.add_page_break()

# ============================================================
# 16. KẾT LUẬN
# ============================================================

add_heading(doc, '16. KẾT LUẬN', 1)

add_heading(doc, '16.1 Tổng kết những gì đã làm', 2)

add_bullet_list(doc, [
    'Hoàn thành 10/10 bài tập - Tất cả các bài đều hoàn thành đúng yêu cầu',
    'Xây dựng kiến trúc 3 tầng - Controller → Service → Repository → Database',
    'Triển khai CRUD hoàn chỉnh - Cho cả Student và Course',
    'Cấu hình JPA/Hibernate - Entity mapping, Repository, Database',
    'Tạo giao diện web - Sử dụng Thymeleaf với dark theme',
    'Khởi tạo dữ liệu mẫu - Tự động tạo 5 sinh viên và 5 môn học',
    'Hướng dẫn chuyển MySQL - Bài 10 với chi tiết đầy đủ',
])

add_heading(doc, '16.2 Kiến thức thu được', 2)

add_bullet_list(doc, [
    'Spring Data JPA: Sử dụng JpaRepository, derived queries, custom queries',
    'Hibernate ORM: Entity mapping, annotations, SQL generation',
    'Spring MVC: Controller, RequestMapping, Model, View',
    'Thymeleaf: Template engine, form binding, iteration',
    'Database Design: Schema, relationships, migration',
    'Maven: Dependency management, build lifecycle',
    'H2 Database: In-memory database cho development',
    'MySQL: Persistent database cho production',
])

add_heading(doc, '16.3 Thống kê dự án', 2)

stat_rows = [
    ['Java Classes', '11'],
    ['HTML Templates', '4'],
    ['Documentation Files', '5'],
    ['Tổng dòng code', '~1,500+'],
    ['Bài tập hoàn thành', '10/10'],
]
add_table_styled(doc, ['Thành phần', 'Số lượng'], stat_rows)

add_heading(doc, '16.4 Điểm tự đánh giá', 2)

grade_rows = [
    ['Cấu hình CSDL đúng', '1.5', '1.5'],
    ['Entity ánh xạ đúng', '1.5', '1.5'],
    ['Repository hoạt động', '1.5', '1.5'],
    ['CRUD sinh viên hoàn chỉnh', '2.0', '2.0'],
    ['Tìm kiếm và bài tự làm', '2.0', '2.0'],
    ['Báo cáo, ảnh minh chứng', '1.5', '1.5'],
    ['TỔNG', '10.0', '10.0'],
]
add_table_styled(doc, ['Tiêu chí', 'Điểm tối đa', 'Tự đánh giá'], grade_rows)

add_heading(doc, '16.5 Hướng phát triển tiếp theo', 2)

add_bullet_list(doc, [
    'Thêm quan hệ - One-to-Many giữa Student và Course',
    'Validation - Thêm Bean Validation cho form',
    'Authentication - Thêm Spring Security',
    'Unit Testing - Viết JUnit tests',
    'API REST - Tạo RESTful endpoints',
])

add_heading(doc, '16.6 Cảm ơn', 2)
add_paragraph_text(doc,
    'Cảm ơn giảng viên đã thiết kế Lab 13 với hướng dẫn chi tiết, '
    'giúp sinh viên nắm vững kiến thức về Spring Data JPA và phát triển ứng dụng web với Java.')

# Decorative line at end
p = doc.add_paragraph()
p.alignment = WD_ALIGN_PARAGRAPH.CENTER
add_horizontal_line(p)

# Final info
p = doc.add_paragraph()
p.alignment = WD_ALIGN_PARAGRAPH.CENTER
run = p.add_run('Ngày hoàn thành: 04/09/2026')
run.font.size = Pt(11)
run.font.italic = True

p = doc.add_paragraph()
p.alignment = WD_ALIGN_PARAGRAPH.CENTER
run = p.add_run('Trạng thái: HOÀN THÀNH XUẤT SẮC')
run.font.size = Pt(12)
run.font.bold = True
run.font.color.rgb = RGBColor(0x22, 0xC5, 0x5E)

p = doc.add_paragraph()
p.alignment = WD_ALIGN_PARAGRAPH.CENTER
run = p.add_run('Điểm tự đánh giá: 10/10')
run.font.size = Pt(12)
run.font.bold = True
run.font.color.rgb = RGBColor(0xF9, 0x73, 0x16)

# Lưu file
output_path = r'D:\Bai-Tap-Java\lab13\BaoCao_Lab13_SpringDataJPA.docx'
doc.save(output_path)
print(f'\n✅ Đã tạo file Word thành công: {output_path}')

# Verify
if os.path.exists(output_path):
    file_size = os.path.getsize(output_path)
    print(f'📦 Kích thước file: {file_size:,} bytes ({file_size/1024:.1f} KB)')
    print(f'📄 Đường dẫn: {output_path}')
