# Các lệnh hữu ích cho Lab 13

## Maven Commands

### Build và Clean
```bash
# Clean project
mvn clean

# Compile code
mvn compile

# Package thành JAR
mvn package

# Clean và package
mvn clean package
```

### Run Application
```bash
# Chạy ứng dụng với Maven
mvn spring-boot:run

# Chạy với profile cụ thể
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Chạy file JAR đã build
java -jar target/lab13-spring-data-jpa-1.0.0.jar
```

### Kiểm tra Dependencies
```bash
# Xem dependency tree
mvn dependency:tree

# Xem dependencies có thể update
mvn versions:display-dependency-updates
```

## Kiểm tra môi trường

```bash
# Kiểm tra Java version
java -version

# Kiểm tra Maven version
mvn -version

# Kiểm tra biến môi trường JAVA_HOME
echo %JAVA_HOME%         # Windows
echo $JAVA_HOME          # Linux/Mac
```

## Git Commands (optional)

```bash
# Initialize git repository
git init

# Add files
git add .

# Commit
git commit -m "Complete Lab 13 - Spring Data JPA"

# Create gitignore
# (đã tạo sẵn file .gitignore)
```

## H2 Database Commands

### Truy cập H2 Console
1. Mở browser: http://localhost:8080/h2-console
2. Nhập thông tin:
   - JDBC URL: `jdbc:h2:mem:eautdb`
   - Username: `sa`
   - Password: (để trống)
3. Click "Connect"

### SQL Queries trong H2
```sql
-- Xem tất cả tables
SHOW TABLES;

-- Xem cấu trúc bảng
SHOW COLUMNS FROM STUDENTS;
SHOW COLUMNS FROM COURSES;

-- Query data
SELECT * FROM STUDENTS;
SELECT * FROM COURSES;

-- Count records
SELECT COUNT(*) FROM STUDENTS;
SELECT COUNT(*) FROM COURSES;

-- Tìm kiếm
SELECT * FROM STUDENTS WHERE FULL_NAME LIKE '%Nguyễn%';
SELECT * FROM COURSES WHERE CREDITS = 3;

-- Insert data manually
INSERT INTO STUDENTS (STUDENT_CODE, FULL_NAME, EMAIL, CLASS_NAME) 
VALUES ('SV006', 'Trần Văn F', 'tranvanf@eaut.edu.vn', 'KTPM02');

-- Update data
UPDATE STUDENTS SET EMAIL = 'new.email@eaut.edu.vn' WHERE STUDENT_CODE = 'SV001';

-- Delete data
DELETE FROM STUDENTS WHERE STUDENT_CODE = 'SV006';

-- Drop tables (cẩn thận!)
DROP TABLE STUDENTS;
DROP TABLE COURSES;
```

## MySQL Commands (Bài 10)

### Kết nối MySQL
```bash
# Windows
mysql -u root -p

# Chỉ định host và port
mysql -h localhost -P 3306 -u root -p
```

### Database Operations
```sql
-- Tạo database
CREATE DATABASE eautdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Chọn database
USE eautdb;

-- Xem databases
SHOW DATABASES;

-- Xem tables
SHOW TABLES;

-- Xem cấu trúc bảng
DESCRIBE students;
DESC courses;
SHOW CREATE TABLE students;

-- Export database
mysqldump -u root -p eautdb > backup.sql

-- Import database
mysql -u root -p eautdb < backup.sql

-- Drop database
DROP DATABASE eautdb;
```

## Kiểm thử API với curl

```bash
# Test trang chủ
curl http://localhost:8080/

# Test danh sách sinh viên
curl http://localhost:8080/students

# Test danh sách môn học
curl http://localhost:8080/courses

# Test tìm kiếm
curl "http://localhost:8080/students?keyword=Nguyen"
```

## Xem logs

```bash
# Xem logs trong console khi chạy mvn spring-boot:run
# Log level có thể điều chỉnh trong application.properties

# Xem Hibernate SQL
# Đã bật: spring.jpa.show-sql=true
# Đã bật format: spring.jpa.properties.hibernate.format_sql=true
```

## Debug Application

### Trong IntelliJ IDEA
1. Đặt breakpoint trong code
2. Run → Debug 'Lab13Application'
3. Hoặc: Debug Maven goal: `spring-boot:run`

### Trong VS Code
1. Cài extension: "Debugger for Java"
2. Đặt breakpoint
3. F5 để debug

### Trong Eclipse
1. Đặt breakpoint
2. Right-click project → Debug As → Spring Boot App

## Port đang sử dụng

- **8080**: Ứng dụng web chính
- **35729**: LiveReload (hot reload cho development)

Kiểm tra port có đang sử dụng không:
```bash
# Windows
netstat -ano | findstr :8080

# Linux/Mac
lsof -i :8080
```

## Hot Reload (DevTools)

Khi có thay đổi code:
1. Lưu file (Ctrl+S)
2. DevTools tự động restart application
3. Refresh browser để thấy thay đổi

Disable hot reload (nếu không muốn):
```properties
spring.devtools.restart.enabled=false
```

## Thymeleaf Cache

Trong development, cache đã tắt:
```properties
spring.thymeleaf.cache=false
```

Điều này giúp thay đổi HTML được phản ánh ngay lập tức.

## Common Issues & Solutions

### Port 8080 đã sử dụng
```bash
# Tìm process đang dùng port 8080
netstat -ano | findstr :8080

# Kill process (Windows)
taskkill /PID <PID> /F

# Hoặc đổi port trong application.properties
server.port=8081
```

### Maven build failed
```bash
# Clean Maven cache
mvn clean

# Force update dependencies
mvn clean install -U

# Skip tests
mvn clean package -DskipTests
```

### H2 Console không truy cập được
Kiểm tra application.properties:
```properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### Hibernate không tạo tables
Kiểm tra:
```properties
spring.jpa.hibernate.ddl-auto=update
```

Các giá trị có thể: `none`, `validate`, `update`, `create`, `create-drop`

## Performance Tips

```properties
# Connection pool size
spring.datasource.hikari.maximum-pool-size=10

# JPA batch size
spring.jpa.properties.hibernate.jdbc.batch_size=20

# Query cache
spring.jpa.properties.hibernate.cache.use_second_level_cache=true
```

## Useful URLs

- **Application**: http://localhost:8080
- **Students**: http://localhost:8080/students
- **Courses**: http://localhost:8080/courses
- **H2 Console**: http://localhost:8080/h2-console
- **Spring Boot Actuator** (nếu thêm): http://localhost:8080/actuator
