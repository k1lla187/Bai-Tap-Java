# Lab 13 - Spring Data JPA - Hướng dẫn nhanh

## 🚀 Khởi động nhanh

### 1. Chạy ứng dụng
```bash
cd d:\Bai-Tap-Java\lab13-spring-data-jpa
mvn spring-boot:run
```

### 2. Truy cập ứng dụng
Mở trình duyệt và truy cập:
- **Trang chủ**: http://localhost:8080
- **Quản lý sinh viên**: http://localhost:8080/students
- **Quản lý môn học**: http://localhost:8080/courses
- **H2 Console**: http://localhost:8080/h2-console

### 3. Đăng nhập H2 Console
- JDBC URL: `jdbc:h2:mem:eautdb`
- Username: `sa`
- Password: (để trống)

---

## 📚 Dữ liệu mẫu có sẵn

Ứng dụng tự động tạo dữ liệu mẫu khi khởi động lần đầu:

### Sinh viên (5 records)
- SV001 - Nguyễn Văn A - CNTT01
- SV002 - Trần Thị B - CNTT01
- SV003 - Lê Văn C - CNTT02
- SV004 - Phạm Thị D - CNTT02
- SV005 - Hoàng Văn E - KTPM01

### Môn học (5 records)
- JAVA101 - Lập trình Java cơ bản (3 TC)
- WEB201 - Lập trình Web (4 TC)
- DB301 - Cơ sở dữ liệu (3 TC)
- SE401 - Công nghệ phần mềm (3 TC)
- NET501 - Lập trình .NET (4 TC)

---

## ✅ Các chức năng đã hoàn thành

### Quản lý Sinh viên
✅ Xem danh sách sinh viên  
✅ Thêm sinh viên mới  
✅ Sửa thông tin sinh viên  
✅ Xóa sinh viên (có xác nhận)  
✅ Tìm kiếm sinh viên theo tên  

### Quản lý Môn học
✅ Xem danh sách môn học  
✅ Thêm môn học mới  
✅ Sửa thông tin môn học  
✅ Xóa môn học (có xác nhận)  
✅ Tìm kiếm môn học theo tên  

---

## 📁 Cấu trúc project

```
lab13-spring-data-jpa/
├── src/main/java/vn/edu/eaut/lab13/
│   ├── Lab13Application.java              # Main class
│   ├── DataInitializer.java               # Dữ liệu mẫu
│   ├── controller/
│   │   ├── HomeController.java            # Trang chủ
│   │   ├── StudentController.java         # CRUD sinh viên
│   │   └── CourseController.java          # CRUD môn học
│   ├── entity/
│   │   ├── Student.java                   # Entity sinh viên
│   │   └── Course.java                    # Entity môn học
│   ├── repository/
│   │   ├── StudentRepository.java         # JPA Repository
│   │   └── CourseRepository.java          # JPA Repository
│   └── service/
│       ├── StudentService.java            # Business logic
│       └── CourseService.java             # Business logic
├── src/main/resources/
│   ├── application.properties             # Cấu hình
│   └── templates/
│       ├── students/
│       │   ├── list.html                  # Danh sách SV
│       │   └── form.html                  # Form SV
│       └── courses/
│           ├── list.html                  # Danh sách MH
│           └── form.html                  # Form MH
├── pom.xml                                # Maven config
├── README.md                              # Báo cáo chi tiết
├── MYSQL_MIGRATION.md                     # Hướng dẫn MySQL
├── COMMANDS.md                            # Lệnh hữu ích
├── CHECKLIST.md                           # Checklist hoàn thành
└── QUICKSTART.md                          # File này
```

---

## 🎯 Các bài tập đã làm

| Bài | Nội dung | Trạng thái |
|-----|----------|------------|
| 1 | Thêm dependency JPA và H2 | ✅ |
| 2 | Tạo Entity Student | ✅ |
| 3 | Tạo Repository | ✅ |
| 4 | Tạo Service | ✅ |
| 5 | Controller CRUD | ✅ |
| 6 | Chức năng sửa sinh viên | ✅ |
| 7 | Tìm kiếm sinh viên | ✅ |
| 8 | Thêm Entity Course | ✅ |
| 9 | CRUD cho Course | ✅ |
| 10 | Chuyển sang MySQL | ⚠️ Có hướng dẫn |

---

## 🔍 Test nhanh các chức năng

### 1. Test thêm sinh viên
1. Truy cập: http://localhost:8080/students
2. Click "Thêm sinh viên"
3. Nhập thông tin:
   - Mã SV: SV999
   - Họ tên: Test User
   - Email: test@eaut.edu.vn
   - Lớp: TEST01
4. Click "Thêm mới"
5. ✅ Sinh viên xuất hiện trong danh sách

### 2. Test tìm kiếm
1. Ở trang danh sách sinh viên
2. Nhập "Nguyễn" vào ô tìm kiếm
3. Click "Tìm"
4. ✅ Chỉ hiển thị sinh viên có tên chứa "Nguyễn"

### 3. Test H2 Console
1. Truy cập: http://localhost:8080/h2-console
2. Đăng nhập (sa / không password)
3. Chạy query: `SELECT * FROM STUDENTS;`
4. ✅ Hiển thị tất cả sinh viên

### 4. Test sửa sinh viên
1. Ở danh sách, click "Sửa" một sinh viên
2. Thay đổi email
3. Click "Cập nhật"
4. ✅ Thông tin được cập nhật

### 5. Test xóa sinh viên
1. Click "Xóa" một sinh viên
2. Confirm dialog xuất hiện
3. Click OK
4. ✅ Sinh viên bị xóa

---

## 📊 Kiểm tra trong H2 Console

Sau khi đăng nhập H2 Console, chạy các query sau:

```sql
-- Xem tất cả sinh viên
SELECT * FROM STUDENTS;

-- Xem tất cả môn học
SELECT * FROM COURSES;

-- Đếm số sinh viên
SELECT COUNT(*) FROM STUDENTS;

-- Tìm sinh viên theo lớp
SELECT * FROM STUDENTS WHERE CLASS_NAME = 'CNTT01';

-- Xem môn học 3 tín chỉ
SELECT * FROM COURSES WHERE CREDITS = 3;

-- Xem cấu trúc bảng
SHOW COLUMNS FROM STUDENTS;
SHOW COLUMNS FROM COURSES;
```

---

## 🎨 Giao diện

Ứng dụng sử dụng **modern dark theme** với:
- Background tối (#0F172A)
- Card nền (#1E293B)
- Accent màu cam (#F97316) cho nút chính
- Accent màu xanh (#38BDF8) cho nút phụ
- Font Inter, clean và professional
- Hover effects mượt mà
- Responsive layout

---

## ⚡ Performance

- **H2 In-Memory Database**: Cực kỳ nhanh (dữ liệu trong RAM)
- **HikariCP Connection Pool**: Connection pooling hiệu quả
- **JPA Query Cache**: Cache queries để tăng tốc
- **Thymeleaf**: Template rendering nhanh

---

## 🛠️ Troubleshooting

### Lỗi: Port 8080 đã sử dụng
```bash
# Tìm process
netstat -ano | findstr :8080

# Kill process
taskkill /PID <PID> /F
```

### Lỗi: Maven build failed
```bash
mvn clean install -U
```

### Lỗi: H2 Console không truy cập
Kiểm tra `application.properties`:
```properties
spring.h2.console.enabled=true
```

---

## 📖 Đọc thêm

- **README.md**: Báo cáo chi tiết, giải thích kiến trúc
- **MYSQL_MIGRATION.md**: Hướng dẫn chuyển sang MySQL (Bài 10)
- **COMMANDS.md**: Tổng hợp các lệnh hữu ích
- **CHECKLIST.md**: Checklist hoàn thành và chuẩn bị nộp bài

---

## 📞 Hỗ trợ

Nếu gặp vấn đề:
1. Đọc section Troubleshooting ở trên
2. Xem COMMANDS.md cho các lệnh debug
3. Kiểm tra logs trong console
4. Xem H2 Console để kiểm tra dữ liệu

---

## ✨ Tính năng nổi bật

🔥 **Auto-initialize sample data** - Dữ liệu mẫu tự động  
🎨 **Modern dark UI** - Giao diện tối chuyên nghiệp  
⚡ **Fast H2 database** - Database trong RAM cực nhanh  
🔍 **Smart search** - Tìm kiếm không phân biệt hoa/thường  
✅ **Full CRUD** - Đầy đủ Create, Read, Update, Delete  
🔐 **Form validation** - Validation cơ bản  
💬 **Flash messages** - Thông báo sau mỗi action  
🚀 **Hot reload** - DevTools auto-restart khi code thay đổi  

---

**Chúc bạn hoàn thành tốt Lab 13! 🎉**
