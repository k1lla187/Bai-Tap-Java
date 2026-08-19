# BÁO CÁO LAB 8
## Chuyển form sang JSF, thêm validation và message

---

## 1. MỤC TIÊU BÀI LAB

| STT | Mục tiêu | Trạng thái |
|-----|-----------|------------|
| 1 | Trình bày được JSF/Jakarta Faces khác gì so với Servlet + JSP truyền thống | ✓ |
| 2 | Cấu hình được FacesServlet để chạy trang .xhtml | ✓ |
| 3 | Chuyển được một form JSP ở Lab 7 sang form JSF bằng h:form, h:inputText, h:commandButton | ✓ |
| 4 | Tạo được Managed Bean/CDI Bean để nhận dữ liệu từ giao diện JSF | ✓ |
| 5 | Thêm validation bằng Bean Validation hoặc thuộc tính required của JSF | ✓ |
| 6 | Hiển thị lỗi và thông báo thành công bằng h:messages/FacesMessage | ✓ |
| 7 | Hiển thị danh sách dữ liệu bằng h:dataTable | ✓ |
| 8 | So sánh được cách xử lý form giữa Servlet/JSP và JSF | ✓ |

---

## 2. CÔNG NGHỆ SỬ DỤNG

| Công nghệ | Phiên bản | Mục đích |
|-----------|-----------|----------|
| JDK | 17 | Biên dịch và chạy mã nguồn Java |
| Apache Tomcat | 10.1.17 | Chạy ứng dụng web |
| Jakarta Faces | 4.0.7 | Component-based UI framework |
| CDI (Weld) | 5.1.2.Final | Quản lý Managed Bean |
| Hibernate Validator | 8.0.1.Final | Bean Validation |
| Bootstrap | 5.3.0 | Giao diện người dùng |

---

## 3. CẤU TRÚC PROJECT

```
lab08-jsf-validation/
├── pom.xml                                          # Maven configuration
├── src/main/
│   ├── java/vn/edu/eaut/lab8/
│   │   ├── bean/
│   │   │   └── SinhVienBean.java                    # CDI Managed Bean
│   │   ├── model/
│   │   │   └── SinhVien.java                        # Entity với Validation
│   │   └── repository/
│   │       └── SinhVienRepository.java              # Data Access Layer
│   └── webapp/
│       ├── index.xhtml                              # Trang chủ
│       ├── sinhvien-form.xhtml                      # Form thêm sinh viên
│       ├── sinhvien-list.xhtml                      # Danh sách sinh viên
│       ├── sinhvien-edit.xhtml                      # Form sửa sinh viên
│       └── WEB-INF/
│           ├── web.xml                              # Cấu hình JSF Servlet
│           └── beans.xml                            # CDI configuration
```

---

## 4. LUỒNG XỬ LÝ JSF

### 4.1. Lifecycle JSF

```
┌─────────────────────────────────────────────────────────────┐
│                    JSF LIFECYCLE                            │
├─────────────────────────────────────────────────────────────┤
│  1. Restore View     ← Khôi phục/gạo tạo component tree   │
│  2. Apply Request    ← Gán giá trị từ form vào Bean       │
│  3. Process Valid   ← Kiểm tra validation                 │
│  4. Update Model    ← Cập nhật giá trị vào Bean          │
│  5. Invoke App      ← Gọi action method (save, delete...) │
│  6. Render Response  ← Trả HTML về cho trình duyệt         │
└─────────────────────────────────────────────────────────────┘
```

### 4.2. Luồng xử lý thêm sinh viên

```
┌──────────┐     ┌───────────────┐     ┌─────────────────┐     ┌──────────────┐
│ User     │────►│ sinhvien-form │────►│ SinhVienBean   │────►│ Repository   │
│ nhập form│     │ .xhtml        │     │ .save()        │     │ .add()       │
└──────────┘     └───────────────┘     └─────────────────┘     └──────────────┘
                           │                   │                      │
                           ▼                   ▼                      ▼
                    FacesMessage ◄──── Validation ◄─────────── Lưu vào List
```

---

## 5. CÁC CHỨC NĂNG CHÍNH

### 5.1. Form thêm sinh viên (`sinhvien-form.xhtml`)

- Sử dụng `<h:form>` bao bọc các thành phần
- `<h:inputText>` để nhập dữ liệu
- `<h:commandButton>` để submit form
- `<h:message>` hiển thị lỗi từng trường
- `<h:messages>` hiển thị thông báo toàn cục

### 5.2. Danh sách sinh viên (`sinhvien-list.xhtml`)

- `<h:dataTable>` hiển thị danh sách với các cột: ID, Mã SV, Họ tên, Email, Lớp, Thao tác
- Chức năng sửa và xóa sinh viên
- Chức năng tìm kiếm theo tên hoặc lớp

### 5.3. Form sửa sinh viên (`sinhvien-edit.xhtml`)

- Binding dữ liệu với `selectedSinhVien` từ Bean
- Cập nhật thông tin sinh viên

---

## 6. VALIDATION

### 6.1. Bean Validation (Model)

```java
@NotBlank(message = "Mã sinh viên không được để trống")
private String maSinhVien;

@NotBlank(message = "Họ tên không được để trống")
@Size(min = 5, message = "Họ tên tối thiểu 5 ký tự")
private String hoTen;

@Email(message = "Email không đúng định dạng")
private String email;

@NotBlank(message = "Lớp không được để trống")
private String lop;
```

### 6.2. JSF Validation (View)

```xml
<h:inputText required="true" requiredMessage="Mã sinh viên bắt buộc nhập" />
<h:inputText validatorMessage="Email không đúng định dạng" />
```

### 6.3. Bảng tổng hợp các ràng buộc

| Trường | Ràng buộc | Thông báo lỗi |
|--------|-----------|---------------|
| maSinhVien | @NotBlank, required | "Mã sinh viên không được để trống" |
| hoTen | @NotBlank, @Size(min=5) | "Họ tên không được để trống", "Họ tên tối thiểu 5 ký tự" |
| email | @Email | "Email không đúng định dạng" |
| lop | @NotBlank, required | "Lớp không được để trống" |

---

## 7. MESSAGE

### 7.1. FacesMessage trong Bean

```java
FacesContext.getCurrentInstance().addMessage(null,
    new FacesMessage(FacesMessage.SEVERITY_INFO, "Thành công", "Đã lưu sinh viên"));
```

### 7.2. Hiển thị trong View

```xml
<h:messages globalOnly="true" infoStyle="color: green;" errorStyle="color: red;" />
<h:message for="ma" style="color: red;" />
```

---

## 8. SO SÁNH SERVLET/JSP VÀ JSF

| Tiêu chí | Servlet + JSP | JSF (Jakarta Faces) |
|----------|--------------|---------------------|
| **Mô hình** | Request/Response | Component-based UI |
| **Xử lý form** | HttpServletRequest getParameter() | Binding trực tiếp với Bean |
| **Validation** | Manual bằng code Java | Annotation (@NotBlank, @Email...) |
| **Quản lý state** | HttpSession thủ công | Scope annotation (@SessionScoped) |
| **Message** | Session.setAttribute() | FacesMessage tự động |
| **Điều hướng** | RequestDispatcher/SendRedirect | Navigation rule |
| **Component** | HTML thuần | Tag library (h:inputText...) |
| **Tách biệt** | JSP + Servlet riêng | View (.xhtml) + Bean gắn kết |
| **Tái sử dụng** | Include/JSP fragment | Composite component |
| **Lifecycle** | Servlet lifecycle đơn giản | 6 phases phức tạp hơn |

### 8.1. Xử lý Form - Ví dụ so sánh

**Servlet/JSP (Lab 7):**
```java
@WebServlet("/sinhvien")
public class SinhVienServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, ...) {
        String maSV = request.getParameter("maSinhVien");  // Manual
        if (maSV == null || maSV.isEmpty()) {
            request.setAttribute("error", "Mã SV không được trống");  // Manual message
        }
    }
}
```

**JSF (Lab 8):**
```java
@Named("sinhVienBean")
@SessionScoped
public class SinhVienBean {
    @NotBlank(message = "Mã sinh viên không được để trống")
    private String maSinhVien;  // Auto-binding + validation

    public String save() {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Thành công", "..."));
    }
}
```

---

## 9. CẤU HÌNH QUAN TRỌNG

### 9.1. web.xml - FacesServlet

```xml
<servlet>
    <servlet-name>Faces Servlet</servlet-name>
    <servlet-class>jakarta.faces.webapp.FacesServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
    <servlet-name>Faces Servlet</servlet-name>
    <url-pattern>*.xhtml</url-pattern>
</servlet-mapping>
```

### 9.2. beans.xml - CDI

```xml
<beans bean-discovery-mode="all">
</beans>
```

---

## 10. KẾT QUẢ

### 10.1. Các trang JSF

| Trang | Đường dẫn | Chức năng |
|-------|-----------|-----------|
| Trang chủ | `/index.xhtml` | Menu điều hướng |
| Form thêm | `/sinhvien-form.xhtml` | Thêm sinh viên mới |
| Danh sách | `/sinhvien-list.xhtml` | Xem, sửa, xóa, tìm kiếm |
| Form sửa | `/sinhvien-edit.xhtml` | Cập nhật sinh viên |

### 10.2. Validation hoạt động

- ✓ Kiểm tra mã sinh viên không trống
- ✓ Kiểm tra họ tên không trống và tối thiểu 5 ký tự
- ✓ Kiểm tra email đúng định dạng
- ✓ Kiểm tra lớp không trống

### 10.3. Message hoạt động

- ✓ Thông báo lỗi theo từng trường (`<h:message>`)
- ✓ Thông báo thành công toàn cục (`<h:messages globalOnly="true">`)

---

## 11. GHI CHÚ

- Dữ liệu được lưu trong bộ nhớ (in-memory List) để tập trung vào JSF
- JPA/Database sẽ được tích hợp trong Lab 9
- Ứng dụng chạy trên Tomcat 10.1.17 với cổng 8081

---

## 12. KẾT LUẬN

Lab 8 đã hoàn thành việc chuyển đổi từ Servlet/JSP sang JSF, giúp sinh viên hiểu được:
- Component-based UI khác gì so với request/response truyền thống
- Managed Bean nhận dữ liệu tự động qua binding
- Bean Validation giúp validation đơn giản và rõ ràng hơn
- FacesMessage giúp hiển thị thông báo dễ dàng

---

*Báo cáo được viết cho Lab 8 - Công nghệ Java IT3242*
