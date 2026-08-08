# BÁO CÁO LAB 3 - JAVA SWING

**Học phần:** Công nghệ Java
**Ngày:** 08/08/2026

---

## 1. CẤU TRÚC PROJECT

```
lab03-java-swing/
├── pom.xml
└── src/main/java/vn/edu/eaut/lab3/
    ├── Bai01HelloSwing.java
    ├── Bai02TongHaiSo.java
    ├── Bai03PhuongTrinhBacNhat.java
    ├── Bai04TamGiacSwing.java
    ├── Bai05FibonacciSwing.java
    ├── Bai06LoginForm.java
    ├── Bai07MayTinhMini.java
    ├── Bai08QuanLySinhVien.java
    └── Student.java
```

---

## 2. BẢNG TỔNG HỢP BÀI TẬP

| Bài | Tên | Components Swing | Chức năng |
|-----|-----|-----------------|-----------|
| 1 | Chào người dùng | JTextField, JButton | Hiển thị "Xin chào, [Tên]" |
| 2 | Tính tổng hai số | JTextField, JButton | Tính a + b, xử lý lỗi |
| 3 | PT bậc nhất | JTextField, JButton | Giải ax + b = 0 |
| 4 | Tam giác | JTextField, JButton | Kiểm tra & phân loại tam giác |
| 5 | Fibonacci | JTextField, JTextArea, JScrollPane | Hiển thị dãy Fibonacci |
| 6 | Đăng nhập | JTextField, JPasswordField, JComboBox, JCheckBox | Form login |
| 7 | Máy tính | JTextField, JButton, JTextArea | +, -, *, / + lịch sử |
| 8 | QL Sinh viên | JTable, JTextField, JButton | CRUD + xếp loại |

---

## 3. THÀNH PHẦN SWING ĐÃ DÙNG

| Nhóm | Components |
|------|------------|
| Container | JFrame, JPanel |
| Input | JTextField, JTextArea, JPasswordField |
| Output | JLabel, JTable |
| Control | JButton, JComboBox, JCheckBox |
| Scroll | JScrollPane |

---

## 4. LAYOUT MANAGERS

| Layout | Ứng dụng |
|--------|----------|
| FlowLayout | Bài 1, 5 |
| GridLayout | Bài 2, 3, 4 |
| BorderLayout | Bài 3, 4, 5, 8 |
| GridBagLayout | Bài 6, 8 |

---

## 5. CÁC LỆNH CHẠY

```bash
# Build
mvn clean compile

# Chạy từng bài
mvn exec:java -Dexec.mainClass="vn.edu.eaut.lab3.Bai01HelloSwing"
mvn exec:java -Dexec.mainClass="vn.edu.eaut.lab3.Bai08QuanLySinhVien"

# Đóng gói
mvn clean package
java -jar target/lab03-java-swing-1.0-SNAPSHOT.jar
```

---

## 6. KẾT QUẢ

```
[INFO] BUILD SUCCESS
[INFO] Compiling 9 source files
```

---

## 7. CÔNG THỨC XẾP LOẠI (Bài 8)

```
Điểm TB ≥ 8.5 → Giỏi
Điểm TB ≥ 7.0 → Khá
Điểm TB ≥ 5.0 → Trung bình
Điểm TB < 5.0 → Yếu
```

---

## 8. TÀI KHOẢN ĐĂNG NHẬP (Bài 6)

| Username | Password | Vai trò |
|----------|----------|---------|
| admin | 123456 | Admin |
| user | 123456 | User |

---

## 9. KẾT LUẬN

Lab 3 đã hoàn thành các yêu cầu:
- ✅ Tạo ứng dụng JFrame chạy trên Java SE
- ✅ Sử dụng đúng components Swing
- ✅ Bố trí giao diện bằng Layout Manager
- ✅ Xử lý sự kiện bằng ActionListener/lambda
- ✅ Khởi động giao diện bằng `SwingUtilities.invokeLater()`
- ✅ Kiểm tra và xử lý lỗi nhập liệu
- ✅ Tách lớp Model (Student) và View (Bai08)
