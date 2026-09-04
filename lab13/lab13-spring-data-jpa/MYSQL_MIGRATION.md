# Hướng dẫn chuyển sang MySQL Database

## Bước 1: Cài đặt MySQL Server

### Windows
1. Tải MySQL Installer từ https://dev.mysql.com/downloads/installer/
2. Chạy file cài đặt và chọn "MySQL Server"
3. Thiết lập root password trong quá trình cài đặt
4. Hoàn tất cài đặt

### Linux/macOS
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install mysql-server

# macOS với Homebrew
brew install mysql
```

## Bước 2: Tạo Database

Mở MySQL Command Line hoặc MySQL Workbench và chạy:

```sql
-- Tạo database
CREATE DATABASE eautdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Tạo user riêng (tùy chọn, khuyến nghị cho production)
CREATE USER 'eautuser'@'localhost' IDENTIFIED BY 'eautpass123';
GRANT ALL PRIVILEGES ON eautdb.* TO 'eautuser'@'localhost';
FLUSH PRIVILEGES;

-- Kiểm tra database đã tạo
SHOW DATABASES;
USE eautdb;
```

## Bước 3: Cấu hình application.properties

Mở file `src/main/resources/application.properties` và thay đổi:

```properties
# Comment out H2 configuration
#spring.datasource.url=jdbc:h2:mem:eautdb
#spring.datasource.driverClassName=org.h2.Driver
#spring.datasource.username=sa
#spring.datasource.password=
#spring.h2.console.enabled=true
#spring.h2.console.path=/h2-console

# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/eautdb?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=your_mysql_password_here

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Server Port
server.port=8080

# Thymeleaf Configuration
spring.thymeleaf.cache=false
```

**Lưu ý**: Thay `your_mysql_password_here` bằng password MySQL của bạn.

## Bước 4: Restart ứng dụng

```bash
# Stop ứng dụng nếu đang chạy (Ctrl+C)

# Clean và rebuild
mvn clean package

# Chạy lại
mvn spring-boot:run
```

## Bước 5: Kiểm tra bảng dữ liệu

### Sử dụng MySQL Command Line
```sql
-- Kết nối MySQL
mysql -u root -p

-- Chọn database
USE eautdb;

-- Xem các bảng
SHOW TABLES;

-- Xem cấu trúc bảng students
DESCRIBE students;

-- Xem cấu trúc bảng courses
DESCRIBE courses;

-- Xem dữ liệu
SELECT * FROM students;
SELECT * FROM courses;

-- Đếm số lượng records
SELECT COUNT(*) FROM students;
SELECT COUNT(*) FROM courses;
```

### Sử dụng MySQL Workbench
1. Mở MySQL Workbench
2. Kết nối đến localhost
3. Chọn database `eautdb` ở sidebar trái
4. Click vào Tables để xem các bảng
5. Right-click vào bảng → "Select Rows - Limit 1000" để xem dữ liệu
6. Chụp ảnh màn hình này để nộp bài

## Bước 6: Thêm dữ liệu mẫu (optional)

Nếu muốn thêm dữ liệu mẫu vào MySQL:

```sql
USE eautdb;

-- Thêm sinh viên mẫu
INSERT INTO students (student_code, full_name, email, class_name) VALUES
('SV001', 'Nguyễn Văn A', 'nguyenvana@eaut.edu.vn', 'CNTT01'),
('SV002', 'Trần Thị B', 'tranthib@eaut.edu.vn', 'CNTT01'),
('SV003', 'Lê Văn C', 'levanc@eaut.edu.vn', 'CNTT02'),
('SV004', 'Phạm Thị D', 'phamthid@eaut.edu.vn', 'CNTT02'),
('SV005', 'Hoàng Văn E', 'hoangvane@eaut.edu.vn', 'KTPM01');

-- Thêm môn học mẫu
INSERT INTO courses (course_code, course_name, credits) VALUES
('JAVA101', 'Lập trình Java cơ bản', 3),
('WEB201', 'Lập trình Web', 4),
('DB301', 'Cơ sở dữ liệu', 3),
('SE401', 'Công nghệ phần mềm', 3),
('NET501', 'Lập trình .NET', 4);

-- Kiểm tra
SELECT * FROM students;
SELECT * FROM courses;
```

## So sánh H2 và MySQL

| Tiêu chí | H2 Database | MySQL |
|----------|-------------|-------|
| Loại | In-memory | Persistent |
| Lưu trữ | RAM (mất khi restart) | Disk (lưu vĩnh viễn) |
| Tốc độ | Rất nhanh | Nhanh |
| Sử dụng | Development, Testing | Production |
| Console | H2 Console (web) | MySQL Workbench, CLI |
| Cài đặt | Không cần (embedded) | Cần cài MySQL Server |

## Khắc phục sự cố

### Lỗi: Access denied for user
```
Error: Access denied for user 'root'@'localhost'
```
**Giải pháp**: 
- Kiểm tra lại username và password trong application.properties
- Reset MySQL root password nếu quên

### Lỗi: Unknown database 'eautdb'
```
Error: Unknown database 'eautdb'
```
**Giải pháp**: 
- Chạy lại lệnh `CREATE DATABASE eautdb;`
- Hoặc thêm `createDatabaseIfNotExist=true` vào JDBC URL

### Lỗi: Communications link failure
```
Error: Communications link failure
```
**Giải pháp**: 
- Kiểm tra MySQL Server đang chạy: `sudo service mysql status`
- Start MySQL: `sudo service mysql start`
- Kiểm tra port 3306 không bị chặn

### Lỗi: Public Key Retrieval is not allowed
```
Error: Public Key Retrieval is not allowed
```
**Giải pháp**: 
- Thêm `allowPublicKeyRetrieval=true` vào JDBC URL

## Ảnh chụp cần nộp cho Bài 10

1. **Ảnh MySQL Workbench hoặc Command Line**:
   - Hiển thị danh sách tables (students, courses)
   - Hiển thị dữ liệu trong bảng students
   - Hiển thị dữ liệu trong bảng courses

2. **Ảnh trình duyệt**:
   - Trang danh sách sinh viên đang chạy
   - Trang danh sách môn học đang chạy
   - Chứng minh dữ liệu được lưu persistent (restart app vẫn còn)

3. **Ảnh console/log**:
   - Hiển thị SQL queries được Hibernate generate
   - Hiển thị kết nối MySQL thành công

## Backup và Restore Database

### Backup
```bash
# Backup toàn bộ database
mysqldump -u root -p eautdb > eautdb_backup.sql

# Backup chỉ cấu trúc
mysqldump -u root -p --no-data eautdb > eautdb_structure.sql

# Backup chỉ dữ liệu
mysqldump -u root -p --no-create-info eautdb > eautdb_data.sql
```

### Restore
```bash
# Restore database
mysql -u root -p eautdb < eautdb_backup.sql
```

---

**Hoàn thành Bài 10**: Sau khi chuyển sang MySQL thành công và có ảnh chụp, bạn đã hoàn thành đầy đủ Lab 13!
