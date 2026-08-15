# Bao cao Lab 6 - Chương 3: Phát triển ứng dụng đa lớp trong Jakarta EE

## 1. Tổng quan

Lab 6 tập trung vào nền tảng Web trong Jakarta EE: **Servlet** xử lý request, **JSP/JSTL** hiển thị dữ liệu, **Session** lưu trạng thái đăng nhập, **Filter** kiểm soát truy cập và **Listener** ghi log vòng đời ứng dụng.

## 2. Công nghệ sử dụng

- **JDK 17**
- **Apache Maven** - Quản lý dependency
- **Apache Tomcat 10.x** - Web Container (jakarta.servlet.*)
- **Jakarta Servlet 6.0.0**
- **JSTL 3.0** - Hiển thị dữ liệu động trong JSP

## 3. Cấu trúc dự án

```
lab06-student-web/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── vn/edu/eaut/lab6/
        │       ├── controller/
        │       │   ├── HelloServlet.java
        │       │   ├── StudentServlet.java
        │       │   ├── StudentEditServlet.java
        │       │   ├── LoginServlet.java
        │       │   └── LogoutServlet.java
        │       ├── filter/
        │       │   ├── AuthFilter.java
        │       │   └── LoggingFilter.java
        │       ├── listener/
        │       │   ├── AppContextListener.java
        │       │   └── SessionLogListener.java
        │       ├── model/
        │       │   └── Student.java
        │       └── store/
        │           └── StudentStore.java
        └── webapp/
            ├── index.jsp
            ├── login.jsp
            ├── welcome.jsp
            ├── dashboard.jsp
            ├── student-form.jsp
            ├── student-list.jsp
            ├── student-edit.jsp
            ├── 403.jsp
            └── WEB-INF/
                └── web.xml
```

## 4. Nội dung các bài tập

### Bai 1: Hello Servlet
- Tạo Servlet `/hello` hiển thị thông báo "Hello, Servlet - Lab 6 Cong nghe Java"

### Bai 2: Form nhập thông tin sinh viên
- Tạo form với các trường: mã SV, họ tên, lớp, email
- Gửi dữ liệu đến Servlet bằng POST

### Bai 3: Lưu danh sách sinh viên và hiển thị bằng JSP + JSTL
- Model `Student` với các thuộc tính: id, name, className, email
- Store `StudentStore` lưu trong bộ nhớ
- Servlet xử lý GET/POST, chuyển dữ liệu sang JSP
- JSP sử dụng JSTL `c:forEach`, `c:if`, `c:choose`

### Bai 4: Chức năng đăng nhập bằng Session
- Tài khoản mẫu: `admin/123456` và `user/123456`
- Lưu username vào session khi đăng nhập thành công
- Chuyển hướng đến welcome.jsp

### Bai 5: Filter kiểm tra đăng nhập và Listener ghi log
- `AuthFilter`: Kiểm tra session trước khi truy cập trang quản trị
- `LoggingFilter`: Ghi log URI, method, user, timestamp
- `AppContextListener`: Ghi log khi ứng dụng khởi động/dừng
- `SessionLogListener`: Ghi log khi session được tạo/hủy

### Bai 6: Tìm kiếm sinh viên theo họ tên
- Bổ sung ô tìm kiếm trên trang danh sách
- Tìm không phân biệt hoa/thường

### Bai 7: Xóa sinh viên
- Nút Xóa ở mỗi dòng
- Xác nhận trước khi xóa

### Bai 8: Cập nhật thông tin sinh viên
- Form sửa hiển thị dữ liệu cũ
- Không cho sửa mã sinh viên

### Bai 9: Phân quyền Admin/User
- Admin: Thêm/Sửa/Xóa sinh viên
- User: Chỉ xem danh sách

### Bai 10: Dashboard sau đăng nhập
- Hiển thị tên user, vai trò, thời gian đăng nhập

### Bai 11: Ghi log truy cập bằng Filter
- Log URI, method, user, thời gian ra console

### Bai 12: Khởi tạo dữ liệu mẫu bằng Listener
- ServletContextListener khởi tạo 2 sinh viên mẫu ban đầu

## 5. Luồng xử lý Request/Response

```
1. User truy cap http://localhost:8080/lab06-student-web/
   |
   v
2. LoggingFilter ghi log: GET /
   |
   v
3. index.jsp hien thi menu
   |
   v
4. User click "Dang nhap" -> login.jsp
   |
   v
5. User nhap admin/123456 -> POST /login
   |
   v
6. LoginServlet kiem tra -> Luu session -> Chuyen huong /welcome.jsp
   |
   v
7. AuthFilter kiem tra session -> Cho phep truy cap
   |
   v
8. User click "Quan ly sinh vien" -> GET /students
   |
   v
9. StudentServlet lay danh sach -> forward /student-list.jsp
   |
   v
10. JSP hien thi danh sach bang JSTL c:forEach
```

## 6. Hướng dẫn chạy

### Buoc 1: Kiem tra moi truong
```bash
java -version
mvn -version
```

### Buoc 2: Build project
```bash
cd lab6/lab06-student-web
mvn clean compile
```

### Buoc 3: Deploy len Tomcat
Copy file WAR hoặc deploy trực tiếp từ IDE (IntelliJ/Eclipse)

### Buoc 4: Truy cap ung dung
```
http://localhost:8080/lab06-student-web/
```

### Tai khoan dang nhap
| Username | Password | Vai tro |
|----------|----------|---------|
| admin    | 123456   | admin   |
| user     | 123456   | user    |

## 7. Câu hỏi củng cố

### 1. Servlet khac JSP o diem nao?
Servlet là Java class xử lý logic, chạy trên server. JSP là trang HTML có thể nhúng Java code, được biên dịch thành Servlet. Servlet giỏi xử lý logic, JSP giỏi hiển thị giao diện.

### 2. Vì sao khong nen viet nhieu Java code trong JSP?
- Code khó đọc, khó bảo trì
- Vi phạm nguyên tắc tách biệt logic/giao diện (MVC)
- JSP nên dùng JSTL để hiển thị dữ liệu

### 3. request.setAttribute() khac request.getParameter() nhu the nao?
- `getParameter()`: Lấy dữ liệu từ form (String), chỉ đọc được một lần
- `setAttribute()`: Đặt object để chuyển giữa các component, dùng `getAttribute()` để đọc

### 4. Khi nao dung forward, khi nao dung sendRedirect?
- `forward()`: Chuyển tiếp request trong cùng server, URL không đổi, dữ liệu request được giữ
- `sendRedirect()`: Trình duyệt gửi request mới đến URL khác, URL thay đổi

### 5. Session dung de giai quyet van de gi?
Session lưu trạng thái người dùng giữa các request HTTP (vốn không trạng thái). Dùng để quản lý đăng nhập, giỏ hàng, preferences.

### 6. Filter co vai tro gi trong kiem tra dang nhap?
Filter hoạt động trước khi request đến Servlet, kiểm tra session và chặn truy cập trái phép, tái sử dụng được cho nhiều trang.

### 7. Listener khac Filter o diem nao?
- **Listener**: Theo dõi sự kiện vòng đời (application, session, request)
- **Filter**: Xử lý/tiền xử lý request trước khi đến Servlet

### 8. Vì sao MVC giup ung dung de bao tri hon?
MVC tách biệt:
- **Model**: Dữ liệu và logic nghiệp vụ
- **View**: Hiển thị giao diện
- **Controller**: Điều phối request

Thay đổi một phần không ảnh hưởng các phần khác.

### 9. Du lieu dang luu o dau? Nhuoc diem?
Dữ liệu đang lưu trong `StudentStore` (ArrayList trong bộ nhớ). Nhược điểm:
- Mất dữ liệu khi restart ứng dụng
- Không có cơ chế tìm kiếm, phân trang hiệu quả
- Không hỗ trợ nhiều người dùng đồng thời

### 10. Neu chuyen sang dung CSDL, can bo sung nhung lop nao?
Cần thêm:
- **DAL (Data Access Layer)**: StudentDAO để CRUD với database
- **Cấu hình kết nối**: DBHelper với JDBC
- Thay đổi Store thành gọi DAO

## 8. Kết luận

Lab 6 đã hoàn thành các yêu cầu:
- Tạo ứng dụng Web Java với Servlet, JSP, JSTL
- Hiểu luồng Request/Response
- Sử dụng Session quản lý đăng nhập
- Tạo Filter kiểm soát truy cập
- Tạo Listener ghi log vòng đời
- Tổ chức theo mô hình MVC
- Hoàn thành 12 bài tập (5 bài có gợi ý + 7 bài tự làm)
