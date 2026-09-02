"""
Lab 12 Report Generator - Spring MVC Student Management
Tao bao cao Word cho bai lab 12
"""

from docx import Document
from docx.shared import Pt, Inches, RGBColor, Cm
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.enum.table import WD_ALIGN_VERTICAL
from docx.oxml.ns import qn
from docx.oxml import OxmlElement


def set_cell_background(cell, color_hex):
    """Set background color for table cell"""
    tc_pr = cell._tc.get_or_add_tcPr()
    shd = OxmlElement('w:shd')
    shd.set(qn('w:val'), 'clear')
    shd.set(qn('w:color'), 'auto')
    shd.set(qn('w:fill'), color_hex)
    tc_pr.append(shd)


def add_heading_custom(doc, text, level=1, color=RGBColor(0x2E, 0x74, 0xB5)):
    """Add styled heading"""
    heading = doc.add_heading(text, level=level)
    for run in heading.runs:
        run.font.color.rgb = color
        run.font.name = 'Calibri'
    return heading


def add_paragraph_styled(doc, text, bold=False, italic=False, align=None, size=12):
    """Add paragraph with custom styling"""
    p = doc.add_paragraph()
    if align is not None:
        p.alignment = align
    run = p.add_run(text)
    run.font.size = Pt(size)
    run.bold = bold
    run.italic = italic
    run.font.name = 'Calibri'
    return p


def add_code_block(doc, code_text, language='java'):
    """Add a code block with monospace font"""
    p = doc.add_paragraph()
    p.paragraph_format.left_indent = Cm(0.5)
    p.paragraph_format.right_indent = Cm(0.5)
    p.paragraph_format.space_before = Pt(6)
    p.paragraph_format.space_after = Pt(6)
    run = p.add_run(code_text)
    run.font.name = 'Consolas'
    run.font.size = Pt(9)
    run.font.color.rgb = RGBColor(0x33, 0x33, 0x33)


# ====================== CREATE DOCUMENT ======================
doc = Document()

# Set default font for entire document
style = doc.styles['Normal']
style.font.name = 'Calibri'
style.font.size = Pt(12)

# Set page margins
for section in doc.sections:
    section.top_margin = Cm(2)
    section.bottom_margin = Cm(2)
    section.left_margin = Cm(2.5)
    section.right_margin = Cm(2.5)

# ====================== TITLE PAGE ======================
title = doc.add_paragraph()
title.alignment = WD_ALIGN_PARAGRAPH.CENTER
title_run = title.add_run('BÁO CÁO LAB 12')
title_run.font.size = Pt(28)
title_run.bold = True
title_run.font.color.rgb = RGBColor(0x2E, 0x74, 0xB5)

subtitle = doc.add_paragraph()
subtitle.alignment = WD_ALIGN_PARAGRAPH.CENTER
sub_run = subtitle.add_run('Phát triển ứng dụng Web với Spring MVC')
sub_run.font.size = Pt(16)
sub_run.italic = True
sub_run.font.color.rgb = RGBColor(0x44, 0x44, 0x44)

doc.add_paragraph()
doc.add_paragraph()

# Student info table
info_table = doc.add_table(rows=6, cols=2)
info_table.style = 'Light Grid Accent 1'
info_table.alignment = WD_ALIGN_PARAGRAPH.CENTER

info_data = [
    ('Học phần:', 'Công nghệ Java'),
    ('Bài lab:', 'Lab 12 - Phát triển ứng dụng Web với Spring MVC'),
    ('Chương:', 'Chương 4 - Spring Framework'),
    ('Sinh viên:', 'Vì Anh Tuấn'),
    ('MSSV:', '20230199'),
    ('Lớp:', 'CNTT2023A'),
]

for i, (label, value) in enumerate(info_data):
    row = info_table.rows[i]
    row.cells[0].text = label
    row.cells[1].text = value
    # Bold the labels
    for run in row.cells[0].paragraphs[0].runs:
        run.bold = True
    set_cell_background(row.cells[0], 'D9E2F3')

doc.add_paragraph()

# Date
date_para = doc.add_paragraph()
date_para.alignment = WD_ALIGN_PARAGRAPH.CENTER
date_run = date_para.add_run('Ngày nộp: 02/09/2026')
date_run.font.size = Pt(14)
date_run.italic = True

doc.add_page_break()

# ====================== TABLE OF CONTENTS ======================
add_heading_custom(doc, 'MỤC LỤC', level=1)
toc_items = [
    '1. Giới thiệu về Spring MVC',
    '2. Mục tiêu bài lab',
    '3. Công cụ sử dụng',
    '4. Cấu trúc dự án',
    '5. Luồng xử lý Request trong Spring MVC',
    '6. Mô tả các bài tập và cách triển khai',
    '   6.1. Bài 1 - Model Student',
    '   6.2. Bài 2 - StudentService',
    '   6.3. Bài 3 - Controller danh sách sinh viên',
    '   6.4. Bài 4 - Form thêm sinh viên',
    '   6.5. Bài 5 - Validation form',
    '   6.6. Bài 6 - Xem chi tiết sinh viên',
    '   6.7. Bài 7 - Sửa thông tin sinh viên',
    '   6.8. Bài 8 - Xóa sinh viên',
    '   6.9. Bài 9 - Tìm kiếm sinh viên',
    '   6.10. Bài 10 - Validation mã sinh viên không trùng',
    '7. Hướng dẫn cài đặt và chạy ứng dụng',
    '8. Kết quả thực hiện',
    '9. Kết luận và bài học kinh nghiệm',
    '10. Tài liệu tham khảo',
]
for item in toc_items:
    p = doc.add_paragraph(item)
    p.paragraph_format.space_after = Pt(3)

doc.add_page_break()

# ====================== 1. GIỚI THIỆU ======================
add_heading_custom(doc, '1. Giới thiệu về Spring MVC', level=1)
add_paragraph_styled(doc, 
    'Spring MVC (Model-View-Controller) là một framework thuộc hệ sinh thái Spring Framework, '
    'được sử dụng rộng rãi trong phát triển ứng dụng web Java. Spring MVC cung cấp kiến trúc '
    'phân lớp rõ ràng giúp tách biệt logic nghiệp vụ (Model), giao diện người dùng (View) và '
    'xử lý request (Controller).')
add_paragraph_styled(doc,
    'Bài lab 12 tập trung vào việc xây dựng một ứng dụng quản lý sinh viên hoàn chỉnh với '
    'các chức năng CRUD cơ bản, sử dụng Thymeleaf làm template engine và Jakarta Validation '
    'cho việc kiểm tra dữ liệu đầu vào. Đây là bước chuẩn bị quan trọng cho Lab 13 khi sẽ '
    'tích hợp cơ sở dữ liệu qua Spring Data JPA.')

# ====================== 2. MỤC TIÊU ======================
add_heading_custom(doc, '2. Mục tiêu bài lab', level=1)
add_paragraph_styled(doc, 'Sau khi hoàn thành bài lab, sinh viên có khả năng:')

objectives = [
    ('STT', 'Mục tiêu', 'Đạt'),
    ('1', 'Trình bày được luồng xử lý request trong Spring MVC', '✓'),
    ('2', 'Xây dựng được Controller xử lý GET và POST', '✓'),
    ('3', 'Tạo form Thymeleaf để thêm và sửa dữ liệu', '✓'),
    ('4', 'Sử dụng được @ModelAttribute để binding dữ liệu form', '✓'),
    ('5', 'Sử dụng validation cơ bản cho form nhập liệu', '✓'),
    ('6', 'Xây dựng CRUD sinh viên bằng danh sách trong bộ nhớ', '✓'),
]

obj_table = doc.add_table(rows=len(objectives), cols=3)
obj_table.style = 'Light Grid Accent 1'
for i, row_data in enumerate(objectives):
    for j, value in enumerate(row_data):
        cell = obj_table.cell(i, j)
        cell.text = value
        for run in cell.paragraphs[0].runs:
            run.font.size = Pt(11)
            if i == 0:
                run.bold = True
        if i == 0:
            set_cell_background(cell, '4472C4')
            for run in cell.paragraphs[0].runs:
                run.font.color.rgb = RGBColor(0xFF, 0xFF, 0xFF)

# ====================== 3. CÔNG CỤ SỬ DỤNG ======================
add_heading_custom(doc, '3. Công cụ sử dụng', level=1)
tools = [
    ('JDK 17', 'Biên dịch và chạy ứng dụng Spring Boot'),
    ('Apache Maven 3.x', 'Quản lý dependency, build và chạy project'),
    ('IntelliJ IDEA / VS Code', 'Soạn thảo mã nguồn, debug ứng dụng'),
    ('Spring Initializr', 'Khởi tạo project Spring Boot'),
    ('Trình duyệt web', 'Kiểm thử giao diện web'),
    ('Git', 'Quản lý mã nguồn và nộp bài'),
]

tools_table = doc.add_table(rows=len(tools) + 1, cols=2)
tools_table.style = 'Light Grid Accent 1'

hdr = tools_table.rows[0]
hdr.cells[0].text = 'Công cụ'
hdr.cells[1].text = 'Mục đích'
for cell in hdr.cells:
    set_cell_background(cell, '4472C4')
    for run in cell.paragraphs[0].runs:
        run.bold = True
        run.font.color.rgb = RGBColor(0xFF, 0xFF, 0xFF)

for i, (tool, purpose) in enumerate(tools, start=1):
    tools_table.cell(i, 0).text = tool
    tools_table.cell(i, 1).text = purpose

# ====================== 4. CẤU TRÚC DỰ ÁN ======================
add_heading_custom(doc, '4. Cấu trúc dự án', level=1)
add_paragraph_styled(doc, 'Tên dự án: lab12-spring-mvc-student')
add_paragraph_styled(doc, 'Package gốc: vn.edu.eaut.lab12')

structure = '''lab12-spring-mvc-student/
├── pom.xml
└── src/main/
    ├── java/vn/edu/eaut/lab12/
    │   ├── Lab12Application.java
    │   ├── controller/
    │   │   ├── HomeController.java
    │   │   └── StudentController.java
    │   ├── model/
    │   │   └── Student.java
    │   └── service/
    │       └── StudentService.java
    └── resources/
        ├── application.properties
        └── templates/
            ├── index.html
            ├── students/
            │   ├── list.html
            │   ├── form.html
            │   └── detail.html
'''
add_code_block(doc, structure, 'text')
add_paragraph_styled(doc, 
    'Cấu trúc được tổ chức theo mô hình MVC chuẩn với Controller, Model, Service và View tách biệt. '
    'Service được tách riêng để thuận tiện cho việc chuyển sang sử dụng cơ sở dữ liệu trong Lab 13.')

# ====================== 5. LUỒNG XỬ LÝ REQUEST ======================
add_heading_custom(doc, '5. Luồng xử lý Request trong Spring MVC', level=1)
add_paragraph_styled(doc, 'Spring MVC hoạt động theo luồng xử lý request như sau:')

steps = [
    ('Bước 1', 'Client gửi HTTP Request đến server (ví dụ: GET /students)'),
    ('Bước 2', 'DispatcherServlet (Front Controller) nhận request làm điểm vào duy nhất'),
    ('Bước 3', 'HandlerMapping xác định Controller phù hợp dựa trên URL'),
    ('Bước 4', 'Controller thực thi logic nghiệp vụ: gọi Service, xử lý Model'),
    ('Bước 5', 'Controller trả về tên View (ví dụ: "students/list") và dữ liệu Model'),
    ('Bước 6', 'ViewResolver phân giải tên view thành file Thymeleaf template'),
    ('Bước 7', 'Thymeleaf engine render template với dữ liệu từ Model'),
    ('Bước 8', 'Response HTML được trả về cho Client'),
]

for step, desc in steps:
    p = doc.add_paragraph()
    p.paragraph_format.left_indent = Cm(0.5)
    run_step = p.add_run(f'{step}: ')
    run_step.bold = True
    run_step.font.color.rgb = RGBColor(0x2E, 0x74, 0xB5)
    p.add_run(desc)

add_paragraph_styled(doc, '', size=8)
add_paragraph_styled(doc, 
    'Đặc biệt, đối với luồng POST có form submission:',
    bold=True)
post_steps = [
    'Client submit form (POST /students/save)',
    'DispatcherServlet nhận request và gọi HandlerMapping',
    'Controller method nhận @ModelAttribute Student student (binding form data)',
    '@Valid kích hoạt validation dựa trên annotation trong Model',
    'BindingResult chứa kết quả validation. Nếu có lỗi → trả về form',
    'Nếu hợp lệ → gọi Service.save() để lưu dữ liệu',
    'Redirect về /students để tránh duplicate submission (PRG pattern)',
]
for s in post_steps:
    p = doc.add_paragraph(f'  → {s}')
    p.paragraph_format.left_indent = Cm(0.5)

doc.add_page_break()

# ====================== 6. MÔ TẢ CÁC BÀI TẬP ======================
add_heading_custom(doc, '6. Mô tả các bài tập và cách triển khai', level=1)

# 6.1 - Model
add_heading_custom(doc, '6.1. Bài 1 - Model Student', level=2)
add_paragraph_styled(doc, 
    'Tạo model Student là POJO chứa các thuộc tính cơ bản: id, studentCode, fullName, email, '
    'className. Sử dụng các annotation của Jakarta Validation để validate dữ liệu đầu vào:')

add_code_block(doc, 
    'package vn.edu.eaut.lab12.model;\n\n'
    'import jakarta.validation.constraints.Email;\n'
    'import jakarta.validation.constraints.NotBlank;\n'
    'import jakarta.validation.constraints.Size;\n\n'
    'public class Student {\n'
    '    private Long id;\n\n'
    '    @NotBlank(message = "Mã sinh viên không được để trống")\n'
    '    @Size(min = 5, message = "Mã sinh viên tối thiểu 5 ký tự")\n'
    '    private String studentCode;\n\n'
    '    @NotBlank(message = "Họ tên không được để trống")\n'
    '    private String fullName;\n\n'
    '    @Email(message = "Email không đúng định dạng")\n'
    '    private String email;\n\n'
    '    @NotBlank(message = "Lớp không được để trống")\n'
    '    private String className;\n\n'
    '    // Constructor, getters, setters\n'
    '}')

# 6.2 - Service
add_heading_custom(doc, '6.2. Bài 2 - StudentService', level=2)
add_paragraph_styled(doc, 
    'StudentService chứa business logic, sử dụng ArrayList làm kho lưu trữ in-memory. '
    'Service được đánh dấu @Service để Spring quản lý. Có các phương thức: '
    'findAll(), findById(), save(), deleteById(), findByName(), findByStudentCode(), '
    'isStudentCodeExists().')

add_code_block(doc,
    '@Service\n'
    'public class StudentService {\n'
    '    private final List<Student> students = new ArrayList<>();\n'
    '    private long nextId = 1;\n\n'
    '    public List<Student> findAll() {\n'
    '        return new ArrayList<>(students);\n'
    '    }\n\n'
    '    public Optional<Student> findById(Long id) {\n'
    '        return students.stream()\n'
    '            .filter(s -> s.getId().equals(id))\n'
    '            .findFirst();\n'
    '    }\n\n'
    '    public void save(Student student) {\n'
    '        if (student.getId() == null) {\n'
    '            student.setId(nextId++);\n'
    '            students.add(student);\n'
    '        } else {\n'
    '            // Update existing\n'
    '            for (int i = 0; i < students.size(); i++) {\n'
    '                if (students.get(i).getId().equals(student.getId())) {\n'
    '                    students.set(i, student);\n'
    '                    break;\n'
    '                }\n'
    '            }\n'
    '        }\n'
    '    }\n'
    '}')

# 6.3 - Controller list
add_heading_custom(doc, '6.3. Bài 3 - Controller danh sách sinh viên', level=2)
add_paragraph_styled(doc, 
    'Tạo StudentController với @RequestMapping("/students") và phương thức list() xử lý GET. '
    'Controller inject StudentService thông qua constructor.')

add_code_block(doc,
    '@Controller\n'
    '@RequestMapping("/students")\n'
    'public class StudentController {\n'
    '    private final StudentService studentService;\n\n'
    '    public StudentController(StudentService studentService) {\n'
    '        this.studentService = studentService;\n'
    '    }\n\n'
    '    @GetMapping\n'
    '    public String list(\n'
    '            @RequestParam(required = false) String search,\n'
    '            Model model) {\n'
    '        if (search != null && !search.trim().isEmpty()) {\n'
    '            model.addAttribute("students", studentService.findByName(search));\n'
    '        } else {\n'
    '            model.addAttribute("students", studentService.findAll());\n'
    '        }\n'
    '        return "students/list";\n'
    '    }\n'
    '}')

# 6.4 - Form
add_heading_custom(doc, '6.4. Bài 4 - Form thêm sinh viên', level=2)
add_paragraph_styled(doc, 
    'Tạo GET /students/create trả về form trống với Student mới. '
    'Form Thymeleaf sử dụng th:object, th:field để binding dữ liệu. '
    'POST /students/save nhận @ModelAttribute để bind form data vào Student object.')

add_code_block(doc,
    '@GetMapping("/create")\n'
    'public String createForm(Model model) {\n'
    '    model.addAttribute("student", new Student());\n'
    '    model.addAttribute("isEdit", false);\n'
    '    return "students/form";\n'
    '}\n\n'
    '@PostMapping("/save")\n'
    'public String save(@ModelAttribute Student student) {\n'
    '    studentService.save(student);\n'
    '    return "redirect:/students";\n'
    '}')

add_paragraph_styled(doc, 'Form HTML sử dụng Thymeleaf:', bold=True)
add_code_block(doc,
    '<form th:action="@{/students/save}" th:object="${student}" method="post">\n'
    '    <input type="hidden" th:field="*{id}">\n'
    '    <input type="text" th:field="*{studentCode}" placeholder="Mã SV">\n'
    '    <input type="text" th:field="*{fullName}" placeholder="Họ tên">\n'
    '    <input type="email" th:field="*{email}" placeholder="Email">\n'
    '    <input type="text" th:field="*{className}" placeholder="Lớp">\n'
    '    <button type="submit">Lưu</button>\n'
    '</form>')

# 6.5 - Validation
add_heading_custom(doc, '6.5. Bài 5 - Validation form', level=2)
add_paragraph_styled(doc, 
    'Sử dụng @Valid trước @ModelAttribute để kích hoạt validation dựa trên annotation trong Model. '
    'BindingResult chứa kết quả validation, nếu hasErrors() thì quay lại form để hiển thị lỗi.')

add_code_block(doc,
    '@PostMapping("/save")\n'
    'public String save(\n'
    '        @Valid @ModelAttribute Student student,\n'
    '        BindingResult result,\n'
    '        Model model) {\n'
    '    if (result.hasErrors()) {\n'
    '        model.addAttribute("isEdit", student.getId() != null);\n'
    '        return "students/form";\n'
    '    }\n'
    '    studentService.save(student);\n'
    '    return "redirect:/students";\n'
    '}')

add_paragraph_styled(doc, 'Hiển thị lỗi trong form.html:', bold=True)
add_code_block(doc,
    '<input type="text" th:field="*{studentCode}">\n'
    '<span class="error" th:if="${#fields.hasErrors(\'studentCode\')}"\n'
    '      th:errors="*{studentCode}"></span>')

# 6.6 - Detail
add_heading_custom(doc, '6.6. Bài 6 - Xem chi tiết sinh viên', level=2)
add_paragraph_styled(doc, 
    'Sử dụng @PathVariable để nhận id từ URL. Tìm sinh viên trong service, '
    'nếu tồn tại thì hiển thị trang detail, ngược lại redirect về danh sách.')

add_code_block(doc,
    '@GetMapping("/{id}")\n'
    'public String detail(@PathVariable Long id, Model model) {\n'
    '    Optional<Student> student = studentService.findById(id);\n'
    '    if (student.isPresent()) {\n'
    '        model.addAttribute("student", student.get());\n'
    '        return "students/detail";\n'
    '    }\n'
    '    return "redirect:/students";\n'
    '}')

# 6.7 - Edit
add_heading_custom(doc, '6.7. Bài 7 - Sửa thông tin sinh viên', level=2)
add_paragraph_styled(doc, 
    'Tương tự form create, nhưng lấy Student từ database và đưa vào form. '
    'Form sẽ submit về POST /students/save với id đã có, và service sẽ cập nhật thay vì tạo mới.')

add_code_block(doc,
    '@GetMapping("/edit/{id}")\n'
    'public String editForm(@PathVariable Long id, Model model) {\n'
    '    Optional<Student> student = studentService.findById(id);\n'
    '    if (student.isPresent()) {\n'
    '        model.addAttribute("student", student.get());\n'
    '        model.addAttribute("isEdit", true);\n'
    '        return "students/form";\n'
    '    }\n'
    '    return "redirect:/students";\n'
    '}')

# 6.8 - Delete
add_heading_custom(doc, '6.8. Bài 8 - Xóa sinh viên', level=2)
add_paragraph_styled(doc, 
    'Sử dụng POST thay vì GET cho delete để bảo mật. Xác nhận trước khi xóa bằng JavaScript confirm. '
    'Sau khi xóa, redirect về danh sách với thông báo thành công.')

add_code_block(doc,
    '@PostMapping("/delete/{id}")\n'
    'public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {\n'
    '    Optional<Student> student = studentService.findById(id);\n'
    '    if (student.isPresent()) {\n'
    '        studentService.deleteById(id);\n'
    '        redirectAttributes.addFlashAttribute("successMessage",\n'
    '            "Xóa sinh viên thành công!");\n'
    '    }\n'
    '    return "redirect:/students";\n'
    '}')

# 6.9 - Search
add_heading_custom(doc, '6.9. Bài 9 - Tìm kiếm sinh viên', level=2)
add_paragraph_styled(doc, 
    'Sử dụng @RequestParam để nhận keyword tìm kiếm. Service có phương thức findByName() '
    'sử dụng stream filter để tìm các sinh viên có tên chứa keyword (case-insensitive).')

add_code_block(doc,
    '// In Service\n'
    'public List<Student> findByName(String name) {\n'
    '    if (name == null || name.trim().isEmpty()) {\n'
    '        return findAll();\n'
    '    }\n'
    '    String searchName = name.trim().toLowerCase();\n'
    '    return students.stream()\n'
    '        .filter(s -> s.getFullName().toLowerCase().contains(searchName))\n'
    '        .toList();\n'
    '}')

# 6.10 - Unique code
add_heading_custom(doc, '6.10. Bài 10 - Validation mã sinh viên không trùng', level=2)
add_paragraph_styled(doc, 
    'Trước khi lưu, kiểm tra mã sinh viên đã tồn tại hay chưa (loại trừ chính sinh viên đang sửa). '
    'Nếu trùng, sử dụng result.rejectValue() để thêm lỗi vào BindingResult.')

add_code_block(doc,
    'Long excludeId = student.getId();\n'
    'if (studentService.isStudentCodeExists(student.getStudentCode(), excludeId)) {\n'
    '    result.rejectValue("studentCode", "duplicate",\n'
    '        "Mã sinh viên đã tồn tại trong hệ thống");\n'
    '    return "students/form";\n'
    '}\n'
    'studentService.save(student);')

doc.add_page_break()

# ====================== 7. HƯỚNG DẪN CHẠY ======================
add_heading_custom(doc, '7. Hướng dẫn cài đặt và chạy ứng dụng', level=1)

add_heading_custom(doc, '7.1. Kiểm tra môi trường', level=2)
add_code_block(doc,
    'java -version\n'
    'javac -version\n'
    'mvn -version')

add_heading_custom(doc, '7.2. Build và chạy project', level=2)
add_code_block(doc,
    '# Di chuyển vào thư mục project\n'
    'cd lab12-spring-mvc-student\n\n'
    '# Build project\n'
    'mvn clean package\n\n'
    '# Chạy ứng dụng\n'
    'mvn spring-boot:run')

add_heading_custom(doc, '7.3. Truy cập ứng dụng', level=2)
add_paragraph_styled(doc, 'Sau khi ứng dụng chạy, truy cập các URL sau:')

urls = [
    ('http://localhost:8080', 'Trang chủ ứng dụng'),
    ('http://localhost:8080/students', 'Danh sách sinh viên'),
    ('http://localhost:8080/students/create', 'Form thêm sinh viên'),
    ('http://localhost:8080/students/{id}', 'Chi tiết sinh viên'),
    ('http://localhost:8080/students/edit/{id}', 'Form sửa sinh viên'),
    ('http://localhost:8080/students?search=keyword', 'Tìm kiếm sinh viên'),
]

url_table = doc.add_table(rows=len(urls) + 1, cols=2)
url_table.style = 'Light Grid Accent 1'
url_table.cell(0, 0).text = 'URL'
url_table.cell(0, 1).text = 'Chức năng'
for cell in url_table.rows[0].cells:
    set_cell_background(cell, '4472C4')
    for run in cell.paragraphs[0].runs:
        run.bold = True
        run.font.color.rgb = RGBColor(0xFF, 0xFF, 0xFF)

for i, (url, desc) in enumerate(urls, start=1):
    url_table.cell(i, 0).text = url
    url_table.cell(i, 1).text = desc

# ====================== 8. KẾT QUẢ ======================
add_heading_custom(doc, '8. Kết quả thực hiện', level=1)
add_paragraph_styled(doc, 
    'Ứng dụng Spring MVC Student Management đã được triển khai thành công với đầy đủ các chức năng '
    'theo yêu cầu của bài lab. Dưới đây là các tính năng đã hoàn thành:')

features = [
    ('✓', 'Hiển thị danh sách sinh viên với giao diện đẹp, responsive'),
    ('✓', 'Form thêm sinh viên mới với đầy đủ các trường thông tin'),
    ('✓', 'Form sửa thông tin sinh viên'),
    ('✓', 'Xem chi tiết thông tin từng sinh viên'),
    ('✓', 'Xóa sinh viên với hộp thoại xác nhận'),
    ('✓', 'Tìm kiếm sinh viên theo họ tên (real-time)'),
    ('✓', 'Validation: bắt buộc nhập mã SV, họ tên, lớp (tối thiểu 5 ký tự cho mã)'),
    ('✓', 'Validation email đúng định dạng'),
    ('✓', 'Validation không trùng mã sinh viên'),
    ('✓', 'Hiển thị thông báo thành công/lỗi sau mỗi thao tác'),
    ('✓', 'Có sẵn 3 sinh viên mẫu để test ngay'),
]

for mark, feature in features:
    p = doc.add_paragraph()
    p.paragraph_format.left_indent = Cm(0.3)
    run = p.add_run(f'{mark}  {feature}')
    run.font.size = Pt(11)

add_paragraph_styled(doc, '', size=8)
add_paragraph_styled(doc, 'Lưu ý về kiểm thử:', bold=True)
add_paragraph_styled(doc,
    'Trong quá trình phát triển, ban đầu gặp lỗi Thymeleaf TemplateInputException do sử dụng '
    'sai cú pháp fragment expression (~{layout :: layout(...)}). Đã sửa bằng cách viết lại các '
    'template với HTML đầy đủ, không sử dụng layout decoration phức tạp. CSS được inline trong '
    'mỗi template để đảm bảo độc lập giữa các trang.')

# ====================== 9. KẾT LUẬN ======================
add_heading_custom(doc, '9. Kết luận và bài học kinh nghiệm', level=1)

add_heading_custom(doc, '9.1. Kết luận', level=2)
add_paragraph_styled(doc, 
    'Qua bài lab 12, sinh viên đã nắm vững kiến thức về Spring MVC framework:')

conclusions = [
    'Hiểu rõ kiến trúc MVC và vai trò của từng thành phần Model, View, Controller.',
    'Nắm được cách thức DispatcherServlet xử lý request và định tuyến đến Controller phù hợp.',
    'Thành thạo việc sử dụng các annotation: @Controller, @GetMapping, @PostMapping, @ModelAttribute, @PathVariable, @RequestParam.',
    'Sử dụng thành thạo Thymeleaf: th:object, th:field, th:errors, th:if, th:each.',
    'Áp dụng validation với Jakarta Bean Validation (@NotBlank, @Size, @Email).',
    'Biết cách tổ chức code theo package chuẩn để dễ bảo trì và mở rộng.',
]

for c in conclusions:
    p = doc.add_paragraph(f'• {c}')
    p.paragraph_format.left_indent = Cm(0.5)

add_heading_custom(doc, '9.2. Bài học kinh nghiệm', level=2)
experiences = [
    ('Tách Service riêng:', 'Giúp code dễ test và chuyển đổi sang database thật ở Lab 13 chỉ cần thay implementation.'),
    ('Sử dụng PRG Pattern:', 'Post-Redirect-Get tránh việc submit form nhiều lần khi user refresh trang.'),
    ('Flash Attributes:', 'Dùng RedirectAttributes để truyền thông báo qua redirect mà không mất dữ liệu.'),
    ('Validation 2 lớp:', 'Annotation validation cho client-side rules, kết hợp kiểm tra logic (trùng mã SV) cho business rules.'),
    ('Sử dụng Optional:', 'Tránh NullPointerException khi tìm kiếm không có kết quả.'),
    ('In-memory storage:', 'Phù hợp cho demo, prototype nhưng cần chuyển sang database cho production.'),
]

for title, desc in experiences:
    p = doc.add_paragraph()
    p.paragraph_format.left_indent = Cm(0.5)
    run = p.add_run(f'{title} ')
    run.bold = True
    p.add_run(desc)

add_heading_custom(doc, '9.3. Hướng phát triển', level=2)
add_paragraph_styled(doc, 'Trong các bài lab tiếp theo, ứng dụng sẽ được mở rộng:')
next_steps = [
    'Lab 13: Tích hợp Spring Data JPA với H2/MySQL database',
    'Phân trang danh sách sinh viên',
    'Sắp xếp theo các trường (tên, lớp, mã SV)',
    'Upload ảnh đại diện cho sinh viên',
    'Export danh sách ra Excel/PDF',
    'Spring Security cho phân quyền admin/user',
    'REST API cho mobile app',
]
for s in next_steps:
    p = doc.add_paragraph(f'• {s}')
    p.paragraph_format.left_indent = Cm(0.5)

# ====================== 10. TÀI LIỆU THAM KHẢO ======================
add_heading_custom(doc, '10. Tài liệu tham khảo', level=1)
refs = [
    '[1] Spring Framework Documentation - https://spring.io/docs',
    '[2] Thymeleaf Documentation - https://www.thymeleaf.org/documentation.html',
    '[3] Jakarta Bean Validation Specification - https://beanvalidation.org/',
    '[4] Baeldung Spring MVC Tutorials - https://www.baeldung.com/spring-mvc-tutorial',
    '[5] Kế hoạch giảng dạy học phần Công nghệ Java - Chương 4',
]
for ref in refs:
    p = doc.add_paragraph(ref)
    p.paragraph_format.left_indent = Cm(0.5)

# ====================== FOOTER ======================
doc.add_paragraph()
doc.add_paragraph()
sign_para = doc.add_paragraph()
sign_para.alignment = WD_ALIGN_PARAGRAPH.RIGHT
sign_run = sign_para.add_run('Người viết báo cáo\n\n\n\nVì Anh Tuấn - 20230199')
sign_run.font.size = Pt(12)
sign_run.italic = True

# ====================== SAVE ======================
output_path = 'D:/Bai-Tap-Java/lab12/lab12-spring-mvc-student/Bao-cao-Lab12.docx'
doc.save(output_path)
print(f'Báo cáo đã được tạo tại: {output_path}')
