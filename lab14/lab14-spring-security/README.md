# Lab 14 - Bảo mật ứng dụng với Spring Security

## 1. Mục tiêu đã thực hiện

- Tích hợp `spring-boot-starter-security` và `thymeleaf-extras-springsecurity6`.
- Cung cấp form đăng nhập tùy chỉnh tại `/login`.
- Cấu hình hai người dùng trong bộ nhớ.
- Phân quyền URL theo vai trò `ADMIN` và `USER`.
- Ẩn/hiện menu, nút thêm, sửa, xóa bằng Thymeleaf Security.
- Cấu hình trang báo lỗi 403 tại `/error/403`.
- Bảo vệ toàn bộ chức năng quản lý môn học tại `/courses/**` cho ADMIN.
- Bảo vệ tạo, sửa, lưu và xóa sinh viên cho ADMIN; USER chỉ xem danh sách.

## 2. Tài khoản kiểm thử

| Vai trò | Tên đăng nhập | Mật khẩu | Quyền |
|---|---|---|---|
| ADMIN | `admin` | `123456` | Xem/thêm/sửa/xóa sinh viên; quản lý môn học |
| USER | `user` | `123456` | Chỉ xem và tìm kiếm sinh viên |

## 3. Phân quyền URL

| URL | Quyền truy cập |
|---|---|
| `/`, `/about`, `/login`, `/css/**` | Công khai |
| `/students` | ADMIN hoặc USER |
| `/students/create`, `/students/edit/**`, `/students/save`, `/students/delete/**` | ADMIN |
| `/courses/**` | ADMIN |
| Các URL khác | Yêu cầu đăng nhập |

## 4. Chạy ứng dụng

Yêu cầu: JDK 17+ và Maven 3.x.

```powershell
cd D:\Bai-Tap-Java\lab14\lab14-spring-security
java -version
javac -version
mvn -version
mvn clean package
mvn spring-boot:run
```

Mở trình duyệt: `http://localhost:8080`.

## 5. Kịch bản kiểm thử và ảnh minh chứng cần chụp

1. Mở `/login`, đăng nhập `admin / 123456`; chụp trang sinh viên có menu Môn học và nút Thêm/Sửa/Xóa.
2. Đăng xuất, đăng nhập `user / 123456`; chụp trang sinh viên không có menu Môn học và các nút quản lý.
3. Khi đang đăng nhập USER, mở `http://localhost:8080/courses`; chụp trang lỗi 403.
4. Khi đang đăng nhập ADMIN, tạo hoặc xóa một sinh viên; chụp thông báo thành công.

## 6. Ghi chú kỹ thuật

Dữ liệu dùng H2 in-memory nên được khởi tạo lại mỗi lần ứng dụng khởi động. `DataInitializer` tạo sẵn hai sinh viên và hai môn học để hỗ trợ kiểm thử. Các biểu mẫu POST giữ CSRF mặc định của Spring Security; Thymeleaf tự chèn CSRF token vào form có `th:action`.
