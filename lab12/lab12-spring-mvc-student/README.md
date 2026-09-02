# Lab 12 - Spring MVC Student Management

## 📋 Mô tả

Ứng dụng web quản lý sinh viên được xây dựng với **Spring MVC**, **Thymeleaf**, và **Jakarta Validation**.

## 🎯 Yêu cầu đạt được

| STT | Mục tiêu | Trạng thái |
|-----|-----------|------------|
| 1 | Trình bày được luồng xử lý request trong Spring MVC | ✅ |
| 2 | Xây dựng được Controller xử lý GET và POST | ✅ |
| 3 | Tạo form Thymeleaf để thêm và sửa dữ liệu | ✅ |
| 4 | Sử dụng được @ModelAttribute để binding dữ liệu form | ✅ |
| 5 | Sử dụng validation cơ bản cho form nhập liệu | ✅ |
| 6 | Xây dựng CRUD sinh viên bằng danh sách trong bộ nhớ | ✅ |

## 📁 Cấu trúc Project

```
lab12-spring-mvc-student/
├── pom.xml
└── src/main/
    ├── java/vn/edu/eaut/lab12/
    │   ├── Lab12Application.java
    │   ├── controller/
    │   │   ├── HomeController.java
    │   │   └── StudentController.java
    │   └── model/
    │       └── Student.java
    │   └── service/
    │       └── StudentService.java
    └── resources/
        ├── application.properties
        └── templates/
            ├── index.html
            ├── layout.html
            └── students/
                ├── list.html
                ├── form.html
                └── detail.html
```

## 🔧 Các chức năng

### Bài 1-3: Danh sách sinh viên
- **URL**: `GET /students`
- Hiển thị danh sách tất cả sinh viên

### Bài 4: Thêm sinh viên
- **URL**: `GET /students/create` - Form thêm mới
- **URL**: `POST /students/save` - Lưu sinh viên

### Bài 5: Validation
- Sử dụng `@Valid` và `BindingResult`
- Kiểm tra: mã SV, họ tên, email, lớp

### Bài 6: Xem chi tiết
- **URL**: `GET /students/{id}`
- Hiển thị thông tin chi tiết sinh viên

### Bài 7: Sửa thông tin
- **URL**: `GET /students/edit/{id}` - Form sửa
- **URL**: `POST /students/save` - Cập nhật sinh viên

### Bài 8: Xóa sinh viên
- **URL**: `POST /students/delete/{id}`
- Xóa sinh viên khỏi danh sách

### Bài 9: Tìm kiếm
- **URL**: `GET /students?search=keyword`
- Tìm kiếm theo họ tên sinh viên

### Bài 10: Validation không trùng mã
- Kiểm tra mã sinh viên không trùng lặp

## 🚀 Chạy ứng dụng

### Kiểm tra môi trường
```bash
java -version
mvn -version
```

### Build và chạy
```bash
# Di chuyển vào thư mục project
cd lab12-spring-mvc-student

# Build project
mvn clean package

# Chạy ứng dụng
mvn spring-boot:run
```

### Truy cập
- **Trang chủ**: http://localhost:8080
- **Danh sách SV**: http://localhost:8080/students

## 📝 Luồng Request trong Spring MVC

```
1. Client gửi Request (GET/POST)
       ↓
2. DispatcherServlet nhận request
       ↓
3. HandlerMapping tìm Controller phù hợp
       ↓
4. Controller xử lý logic:
   - Gọi Service để lấy/cập nhật dữ liệu
   - Đưa dữ liệu vào Model
       ↓
5. View Resolver tìm template Thymeleaf
       ↓
6. Thymeleaf render HTML với dữ liệu từ Model
       ↓
7. Response trả về cho Client
```

## 👤 Sinh viên

- **MSSV**: 20230199
- **Họ tên**: Vi Anh Tuấn
- **Lớp**: CNTT2023A
