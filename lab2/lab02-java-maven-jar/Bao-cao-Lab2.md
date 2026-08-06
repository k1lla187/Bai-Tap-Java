# BÁO CÁO LAB 2 - MAVEN PROJECT VÀ ĐÓNG GÓI ỨNG DỤNG JAVA

**Học phần:** Công nghệ Java
**Bài lab:** Thiết lập môi trường Java, tạo Maven project và đóng gói JAR
**Ngày thực hiện:** 06/08/2026

---

## 1. MÔ TẢ PROJECT

### 1.1 Cấu trúc thư mục

```
lab02-java-maven-jar/
├── pom.xml
└── src/
    ├── main/
    │   └── java/
    │       └── vn/
    │           └── edu/
    │               └── eaut/
    │                   └── lab2/
    │                       ├── App.java
    │                       ├── Student.java
    │                       └── GradeCalculator.java
    └── test/
        └── java/
            └── vn/
                └── edu/
                    └── eaut/
                        └── AppTest.java
```

### 1.2 Giải thích cấu trúc

| Thành phần | Ý nghĩa |
|------------|---------|
| `pom.xml` | File cấu hình Maven, khai báo dependencies và plugins |
| `src/main/java` | Chứa mã nguồn chính của ứng dụng |
| `src/test/java` | Chứa mã nguồn unit test |
| `target/` | Thư mục chứa kết quả build (không cần nộp) |

### 1.3 Mô tả các lớp

| Lớp | Package | Chức năng |
|-----|---------|-----------|
| `App` | `vn.edu.eaut.lab2` | Điểm khởi đầu chương trình, xử lý nhập/xuất console |
| `Student` | `vn.edu.eaut.lab2` | Lưu trữ thông tin sinh viên (mã SV, họ tên, điểm) |
| `GradeCalculator` | `vn.edu.eaut.lab2` | Tính điểm tổng kết và xếp loại kết quả |

---

## 2. CẤU HÌNH POM.XML

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>vn.edu.eaut</groupId>
    <artifactId>lab02-java-maven-jar</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
    </properties>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>3.4.2</version>
                <configuration>
                    <archive>
                        <manifest>
                            <mainClass>vn.edu.eaut.lab2.App</mainClass>
                        </manifest>
                    </archive>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

---

## 3. CÁC LỆNH ĐÃ SỬ DỤNG

### 3.1 Tạo project mới
```bash
mvn archetype:generate -DgroupId=vn.edu.eaut ^
  -DartifactId=lab02-java-maven-jar ^
  -DarchetypeArtifactId=maven-archetype-quickstart ^
  -DinteractiveMode=false
```

### 3.2 Build và đóng gói
```bash
mvn clean package
```

### 3.3 Chạy ứng dụng
```bash
java -jar target/lab02-java-maven-jar-1.0-SNAPSHOT.jar
```

### 3.4 Chạy unit tests
```bash
mvn test
```

---

## 4. KẾT QUẢ THỰC THI

### 4.1 Minh chứng môi trường

| Lệnh | Phiên bản |
|------|-----------|
| `java -version` | Java 26.0.2 |
| `javac -version` | javac 26.0.2 |
| `mvn -version` | Apache Maven 3.9.16 |
| `JAVA_HOME` | `C:\Program Files\Java\jdk-26.0.2` |

### 4.2 Build thành công
```
[INFO] BUILD SUCCESS
[INFO] Total time: 18.8 s
```

### 4.3 Chạy ứng dụng

```
===== LAB 2 - MAVEN PROJECT VA DONG GOI JAR =====
Nhap ma sinh vien: SV001
Nhap ho ten sinh vien: Nguyen Van A
Nhap diem chuyen can: 8.5
Nhap diem giua ky: 7.0
Nhap diem cuoi ky: 9.0

----- KET QUA HOC PHAN -----
Ma SV: SV001
Ho ten: Nguyen Van A
Diem tong ket: 8.35
Xep loai: B
```

---

## 5. CÔNG THỨC TÍNH ĐIỂM

```
Điểm tổng kết = Điểm chuyên cần × 10% + Điểm giữa kỳ × 30% + Điểm cuối kỳ × 60%
```

**Xếp loại:**
| Điểm | Xếp loại |
|------|----------|
| ≥ 8.5 | A |
| ≥ 7.0 | B |
| ≥ 5.5 | C |
| ≥ 4.0 | D |
| < 4.0 | F |

---

## 6. LỖI GẶP PHẢI VÀ CÁCH XỬ LÝ

| Lỗi | Nguyên nhân | Cách xử lý |
|------|-------------|------------|
| Lỗi encoding UTF-8 | Console không hỗ trợ tiếng Việt | Thêm `<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>` |
| JAR không chạy được bằng `java -jar` | Chưa khai báo mainClass | Thêm `maven-jar-plugin` với `<mainClass>vn.edu.eaut.lab2.App</mainClass>` |
| AppTest lỗi | Dùng JUnit 4 thay vì JUnit 5 | Đổi import từ `junit.framework.*` sang `org.junit.jupiter.api.*` |

---

## 7. KẾT LUẬN

Qua bài lab 2, đã nắm được:
- Cách tạo Maven project theo cấu trúc chuẩn
- Cách cấu hình `pom.xml` với compiler và JAR plugin
- Quy trình build: `mvn clean package`
- Cách chạy ứng dụng đã đóng gói: `java -jar`
- Tổ chức mã nguồn theo package có ý nghĩa

**Sản phẩm:** File JAR chạy được tại `target/lab02-java-maven-jar-1.0-SNAPSHOT.jar`
