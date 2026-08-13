# BÁO CÁO LAB 5 - MINISHOP SWING JDBC

**Học phần:** Công nghệ Java
**Ngày:** 13/08/2026

---

## 1. CẤU TRÚC PROJECT

```
lab05-minishop-swing-jdbc/
├── pom.xml
├── database/
│   └── minishop_db.sql
└── src/main/java/vn/edu/eaut/lab5/
    ├── App.java
    ├── config/
    │   └── DBHelper.java
    ├── model/
    │   ├── SanPham.java
    │   ├── KhachHang.java
    │   ├── HoaDon.java
    │   └── ChiTietHoaDon.java
    ├── dal/
    │   ├── SanPhamDAL.java
    │   ├── KhachHangDAL.java
    │   ├── HoaDonDAL.java
    │   └── ThongKeDAL.java
    ├── bus/
    │   ├── SanPhamBUS.java
    │   ├── KhachHangBUS.java
    │   ├── HoaDonBUS.java
    │   └── ThongKeBUS.java
    ├── ui/
    │   ├── MainFrame.java
    │   ├── SanPhamPanel.java
    │   ├── KhachHangPanel.java
    │   ├── HoaDonPanel.java
    │   └── ThongKePanel.java
    └── util/
        └── MessageUtil.java
```

---

## 2. BẢNG TỔNG HỢP BÀI TẬP

| Bài | Tên | Mô tả | Trạng thái |
|-----|-----|-------|-----------|
| 1 | Kết nối CSDL | DBHelper, kiểm tra kết nối MySQL bằng JDBC | ✅ Hoàn thành |
| 2 | Quản lý sản phẩm | CRUD + tìm kiếm + hiển thị JTable + SwingWorker | ✅ Hoàn thành |
| 3 | Quản lý khách hàng | CRUD + validate SDT bằng DocumentFilter (10 số) | ✅ Hoàn thành |
| 4 | Lập hóa đơn | Chọn KH, chọn SP, nhập số lượng, giỏ hàng tạm, lưu transaction | ✅ Hoàn thành |
| 5 | Thống kê SwingWorker | Doanh thu, HD cao nhất, SP bán chạy, Top 5 HD | ✅ Hoàn thành |
| 6 | Quản lý danh mục | Tự triển khai thêm | 🔜 Tự làm |
| 7 | Quản lý tồn kho | Kiểm tra tồn kho, trừ kho, cảnh báo | 🔜 Tự làm |
| 8 | Xuất hóa đơn file | Xuất TXT/CSV | 🔜 Tự làm |
| 9 | Tìm kiếm nâng cao | Lọc nhiều điều kiện, phân trang | 🔜 Tự làm |
| 10 | Đăng nhập, phân quyền | LoginFrame, vai trò Admin/NhanVien/KeToan | 🔜 Tự làm |

---

## 3. KIẾN TRÚC 3 LỚP

```
┌─────────────────────────────────────────────────┐
│                  UI Layer (GUI)                  │
│  MainFrame, SanPhamPanel, KhachHangPanel, ...   │
│  - Hiển thị dữ liệu lên JTable, JComboBox      │
│  - Xử lý sự kiện người dùng                    │
│  - Gọi BUS, KHÔNG gọi DAL trực tiếp             │
└──────────────────────┬──────────────────────────┘
                       │ gọi phương thức
┌──────────────────────▼──────────────────────────┐
│               Business Layer (BUS)                │
│  SanPhamBUS, KhachHangBUS, HoaDonBUS, ...      │
│  - Validate dữ liệu                             │
│  - Xử lý nghiệp vụ                             │
│  - Gọi DAL, KHÔNG gọi trực tiếp GUI            │
└──────────────────────┬──────────────────────────┘
                       │ gọi SQL
┌──────────────────────▼──────────────────────────┐
│              Data Access Layer (DAL)              │
│  SanPhamDAL, KhachHangDAL, HoaDonDAL, ...      │
│  - Thực thi SQL bằng PreparedStatement          │
│  - Trả về List<Model> cho BUS                   │
└──────────────────────┬──────────────────────────┘
                       │ JDBC
┌──────────────────────▼──────────────────────────┐
│                   MySQL Database                  │
│  minishop_db: san_pham, khach_hang,             │
│  hoa_don, chi_tiet_hoa_don                      │
└─────────────────────────────────────────────────┘
```

---

## 4. CƠ SỞ DỮ LIỆU

### 4.1 Các bảng

| Bảng | Khóa chính | Khóa ngoài |
|------|-----------|-----------|
| `san_pham` | `ma_sp` | — |
| `khach_hang` | `ma_kh` | — |
| `hoa_don` | `ma_hd` | `ma_kh` → `khach_hang` |
| `chi_tiet_hoa_don` | `(ma_hd, ma_sp)` | `ma_hd` → `hoa_don`, `ma_sp` → `san_pham` |

### 4.2 Lệnh chạy SQL

```sql
-- Chạy file SQL trước khi chạy ứng dụng
mysql -u root -p < database/minishop_db.sql
```

---

## 5. JDBC — ĐIỂM CHÍNH

### 5.1 Kết nối (DBHelper.java)

```java
private static final String URL =
    "jdbc:mysql://localhost:3306/minishop_db?useUnicode=true&characterEncoding=UTF-8";

public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASSWORD);
}
```

### 5.2 PreparedStatement (chống SQL Injection)

```java
// Thay vì Statement (dễ SQL Injection):
// "SELECT * FROM san_pham WHERE ten_sp LIKE '%" + keyword + "%'"

// Dùng PreparedStatement (an toàn):
String sql = "SELECT * FROM san_pham WHERE ten_sp LIKE ?";
ps.setString(1, "%" + keyword + "%");
```

### 5.3 Transaction cho hóa đơn

```java
conn.setAutoCommit(false);
// INSERT hoa_don
// INSERT chi_tiet_hoa_don (batch)
conn.commit();
// Nếu lỗi: conn.rollback()
```

---

## 6. CÁC THÀNH PHẦN SWING ĐÃ DÙNG

| Nhóm | Components |
|------|-----------|
| Container | JFrame, JPanel, JTabbedPane |
| Input | JTextField |
| Display | JLabel, JTable, JProgressBar |
| Control | JButton, JComboBox |
| Scroll | JScrollPane |
| Dialog | JOptionPane (MessageUtil) |

---

## 7. SWINGWORKER TRONG LAB 5

| Vị trí | Tác vụ | Phương thức |
|--------|--------|-------------|
| `SanPhamPanel` | Load danh sách sản phẩm | `doInBackground()` → `findAll()` |
| `SanPhamPanel` | Tìm kiếm sản phẩm | `doInBackground()` → `searchByName()` |
| `KhachHangPanel` | Load danh sách khách hàng | `doInBackground()` → `findAll()` |
| `KhachHangPanel` | Tìm kiếm khách hàng | `doInBackground()` → `searchByName()` |
| `HoaDonPanel` | Load danh sách hóa đơn | `doInBackground()` → `findAll()` |
| `HoaDonPanel` | Lưu hóa đơn | `doInBackground()` → `saveHoaDon()` |
| `ThongKePanel` | Tính doanh thu | `doInBackground()` → `tinhDoanhThu()` |
| `ThongKePanel` | Tìm HD cao nhất, SP bán chạy | `doInBackground()` → BUS methods |
| `ThongKePanel` | Top 5 hóa đơn | `doInBackground()` → `findTopHoaDon()` |

> Không truy vấn dữ liệu trực tiếp trên EDT — tất cả truy vấn SQL đều chạy trong `SwingWorker.doInBackground()`.

---

## 8. CÁC LỆNH CHẠY

```powershell
# Build
cd "d:/Bai-Tap-Java/lab5/lab05-minishop-swing-jdbc"
mvn clean compile

# Chạy
mvn exec:java "-Dexec.mainClass=vn.edu.eaut.lab5.App"

# Đóng gói
mvn clean package
java -jar target/lab05-minishop-swing-jdbc-1.0-SNAPSHOT.jar
```

---

## 9. HƯỚNG DẪN CHẠY

1. **Cài MySQL** (nếu chưa có) và đảm bảo MySQL service đang chạy.
2. **Tạo database**: chạy file `database/minishop_db.sql` trong MySQL Workbench hoặc cmd:
   ```sql
   SOURCE d:/Bai-Tap-Java/lab5/lab05-minishop-swing-jdbc/database/minishop_db.sql;
   ```
3. **Kiểm tra kết nối**: chạy `mvn exec:java` — nếu thấy "Ket noi CSDL thanh cong!" là OK.
4. **Sử dụng app**: giao diện 4 tab — Sản phẩm, Khách hàng, Hóa đơn, Thống kê.

---

## 10. KẾT LUẬN

Lab 5 đã hoàn thành các yêu cầu cốt lõi:

- ✅ Kiến trúc 3 lớp DAL-BUS-GUI rõ ràng, dễ bảo trì.
- ✅ Kết nối MySQL bằng JDBC với PreparedStatement.
- ✅ CRUD sản phẩm, khách hàng với tìm kiếm.
- ✅ Validate dữ liệu ở BUS và GUI (DocumentFilter cho SDT).
- ✅ Lập hóa đơn với transaction đảm bảo tính nhất quán.
- ✅ Thống kê doanh thu, HD cao nhất, SP bán chạy.
- ✅ SwingWorker cho mọi thao tác truy vấn CSDL — không treo EDT.
- ✅ Mô hình đối tượng: SanPham, KhachHang, HoaDon, ChiTietHoaDon.