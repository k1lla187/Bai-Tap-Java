# BÁO CÁO NGẮN LAB 14

## Bảo mật ứng dụng với Spring Security

**Học phần:** Công nghệ Java  
**Chương:** Chương 4 - Phát triển ứng dụng với Spring Framework  
**Tên lab:** Lab 14 - Bảo mật ứng dụng với Spring Security  
**Project:** `lab14-spring-security`

## 1. Mục tiêu

Bài lab xây dựng ứng dụng Spring Boot có đăng nhập, đăng xuất và phân quyền theo vai trò. Ứng dụng sử dụng Spring Security để xác thực người dùng và quyết định quyền truy cập từng URL, đồng thời dùng Thymeleaf Security để hiển thị giao diện phù hợp với quyền hiện tại.

## 2. Công nghệ sử dụng

- Java 17.
- Spring Boot 3.2.0.
- Spring MVC và Thymeleaf.
- Spring Security 6.
- Thymeleaf Extras Spring Security 6.
- Spring Data JPA và cơ sở dữ liệu H2 in-memory.
- Maven.

## 3. Authentication (xác thực)

Authentication là quá trình kiểm tra người dùng có đúng là người họ khai báo hay không. Ứng dụng cấu hình hai tài khoản trong bộ nhớ qua `InMemoryUserDetailsManager`:

- `admin / 123456`, role `ADMIN`.
- `user / 123456`, role `USER`.

Mật khẩu không lưu dạng văn bản thuần trong đối tượng người dùng, mà được mã hóa bằng `BCryptPasswordEncoder`. Khi người dùng gửi form tại `/login`, Spring Security nhận `username` và `password`, sau đó so sánh mật khẩu đã mã hóa để xác thực.

## 4. Authorization (phân quyền)

Authorization là quá trình xác định người dùng đã đăng nhập được làm gì. `SecurityFilterChain` phân quyền các URL như sau:

- Các URL `/`, `/about`, `/login`, `/css/**` được truy cập công khai.
- `/students/**` được phép cho ADMIN và USER, nhưng các URL thêm, sửa, lưu, xóa sinh viên chỉ dành cho ADMIN.
- `/courses/**` chỉ dành cho ADMIN.
- Các yêu cầu chưa được khai báo phải đăng nhập.

Nếu USER cố truy cập URL của ADMIN, Spring Security chuyển người dùng đến `/error/403`, là trang báo không đủ quyền.

## 5. Thymeleaf Security

Các view khai báo namespace `sec` của Thymeleaf Security. Các biểu thức chính là:

- `sec:authorize="hasRole('ADMIN')"`: chỉ hiển thị menu Môn học và nút thêm/sửa/xóa cho ADMIN.
- `sec:authorize="isAuthenticated()"`: hiển thị tên đăng nhập và nút đăng xuất khi đã xác thực.
- `sec:authorize="isAnonymous()"`: hiển thị nút đăng nhập khi chưa xác thực.
- `sec:authentication="name"`: hiển thị username của phiên đăng nhập hiện tại.

Việc ẩn nút chỉ cải thiện giao diện; bảo vệ thực sự vẫn nằm trong cấu hình URL ở Spring Security.

## 6. Kết quả

Ứng dụng đáp ứng các yêu cầu: tích hợp Spring Security, form login/logout, hai người dùng trong bộ nhớ, phân quyền ADMIN/USER, bảo vệ thao tác thêm-sửa-xóa sinh viên, bảo vệ `/courses/**`, hiển thị người dùng đăng nhập và có trang lỗi 403.

## 7. Hướng phát triển

Bài 10 có thể thực hiện bằng cách thay `InMemoryUserDetailsManager` bằng `UserDetailsService` đọc tài khoản, mật khẩu mã hóa và role từ cơ sở dữ liệu. H2 có thể được thay bằng MySQL khi triển khai lâu dài.
