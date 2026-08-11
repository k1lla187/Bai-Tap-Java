# BÁO CÁO LAB 4 - SWINGWORKER

**Học phần:** Công nghệ Java
**Ngày:** 11/08/2026

---

## 1. CẤU TRÚC PROJECT

```
lab04-swingworker/
├── pom.xml
├── Bao-cao-Lab4.md
└── src/main/java/vn/edu/eaut/lab4/
    └── FileSearchFrame.java        (Bài 7 - Tìm kiếm từ khóa)
```

---

## 2. BÀI ĐÃ HOÀN THÀNH

| Bài | Tên | SwingWorker | Components | Trạng thái |
|-----|-----|-------------|-----------|------------|
| 7   | Tìm kiếm từ khóa trong file | `publish()` / `process()` / `done()` | JLabel, JButton, JTextField, JTextArea, JScrollPane, JProgressBar, JFileChooser | ✅ Hoàn thành |

Các bài 1–6 và 8–10 đã được lên kế hoạch trong `Bao-cao-Lab4.md` (file mẫu) nhưng chưa code trong module này — module hiện chỉ chứa Bài 7 như một ví dụ tiêu biểu cho SwingWorker.

---

## 3. CÁC THÀNH PHẦN SWING ĐÃ DÙNG

| Nhóm | Components |
|------|------------|
| Container | JFrame, JPanel |
| Input | JTextField, JTextArea |
| Control | JButton |
| Display | JLabel, JProgressBar |
| Scroll | JScrollPane |
| File | JFileChooser |

---

## 4. SWINGWORKER — LÝ THUYẾT VÀ ÁP DỤNG

### 4.1 Các phương thức chính của `SwingWorker<T, V>`

| Phương thức | Chạy trên | Mục đích |
|-------------|-----------|---------|
| `doInBackground()` | Worker thread | Xử lý tác vụ nặng, trả về kết quả kiểu `T` |
| `publish(V...)` | Worker thread | Gửi dữ liệu trung gian về EDT |
| `process(List<V>)` | EDT | Nhận dữ liệu trung gian, cập nhật UI an toàn |
| `done()` | EDT | Chạy sau khi `doInBackground()` xong (kể cả khi bị cancel) |
| `setProgress(int)` | Worker thread | Cập nhật tiến độ (0–100) |
| `get()` | bất kỳ | Lấy kết quả cuối cùng (blocking) |

### 4.2 Mẫu thiết kế chuẩn

```java
SwingWorker<KếtQuả, TiếnĐộ> worker = new SwingWorker<>() {
    @Override
    protected KếtQuả doInBackground() throws Exception {
        // chạy trên worker thread
        for (...) {
            publish(progressValue);          // gửi về EDT
        }
        return result;
    }

    @Override
    protected void process(List<TiếnĐộ> chunks) {
        // chạy trên EDT — chỉ cập nhật UI ở đây
        progressBar.setValue(chunks.get(chunks.size() - 1));
    }

    @Override
    protected void done() {
        // chạy trên EDT — kết thúc
        try {
            txtResult.setText(format(get()));
        } catch (Exception ex) { ... }
        btnSearch.setEnabled(true);
    }
};
worker.execute();
```

---

## 5. EDT — EVENT DISPATCH THREAD

### 5.1 Khái niệm

- EDT là luồng duy nhất được phép thay đổi giao diện Swing.
- Mọi callback sự kiện (ActionListener, KeyListener, ...) đều chạy trên EDT.

### 5.2 Khởi động an toàn

```java
public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new FileSearchFrame().setVisible(true));
}
```

### 5.3 Vì sao cần SwingWorker?

| Vấn đề | Hậu quả |
|--------|---------|
| Đọc file lớn trên EDT | Giao diện đơ (freeze), không phản hồi |
| Tính toán nặng trên EDT | Nút bấm không nhận, thanh tiến trình đứng yên |
| Mở `JFileChooser` trên worker thread | Có thể treo dialog hoặc ném lỗi |

→ SwingWorker giải quyết bằng cách tách **tác vụ nặng** sang worker thread, chỉ trả về EDT để cập nhật UI qua `publish/process` và `done`.

---

## 6. BÀI 7 — TÌM KIẾM TỪ KHÓA TRONG FILE

### 6.1 Mô tả

Ứng dụng cho phép người dùng chọn một file văn bản, nhập từ khóa, tìm tất cả các dòng chứa từ khóa (không phân biệt hoa/thường), hiển thị kết quả kèm số dòng, có thanh tiến trình phản ánh tiến độ đọc file.

### 6.2 Giao diện

```
┌────────────────────────────────────────────────┐
│ File: [tên file]            [Chon file]       │   NORTH  (topPanel)
├────────────────────────────────────────────────┤
│ Tu khoa: [____________]    [Tim kiem]         │   NORTH của centerContainer
│ ┌────────────────────────────────────────────┐ │
│ │                                            │ │
│ │     JTextArea kết quả (JScrollPane)        │ │   CENTER của centerContainer
│ │                                            │ │
│ └────────────────────────────────────────────┘ │
├────────────────────────────────────────────────┤
│ ▓▓▓▓▓▓▓▓▓▓▓░░░░░░░░░░░ 60%                   │   SOUTH (progressBar)
└────────────────────────────────────────────────┘
```

### 6.3 Cấu trúc layout

- `JFrame` chính dùng `BorderLayout(10, 10)`.
- `topPanel` (NORTH) — `FlowLayout(LEFT)` chứa `JLabel("File:")`, `lblFile`, `btnChoose`.
- `centerContainer` (CENTER) là `JPanel(BorderLayout)`:
  - NORTH: `keywordPanel` — `FlowLayout(LEFT)` chứa `JLabel("Tu khoa:")`, `txtKeyword`, `btnSearch`.
  - CENTER: `JScrollPane(txtResult)`.
- `progressBar` (SOUTH).

> Ghi chú: nếu đặt `keywordPanel` và `scrollPane` cùng `BorderLayout.CENTER` ở `JFrame` thì `scrollPane` sẽ đè `keywordPanel` và làm ô nhập từ khóa biến mất. Bài học rút ra: **mỗi vùng BorderLayout chỉ nên giữ 1 component; nếu cần nhiều component trong cùng vùng phải bọc trong một `JPanel` trung gian.**

### 6.4 Các sự kiện

| Sự kiện | Xử lý |
|---------|--------|
| Click `btnChoose` | Mở `JFileChooser`, nếu `APPROVE_OPTION` → lưu `selectedFile`, cập nhật `lblFile` |
| Click `btnSearch` | Validate `selectedFile` và `keyword`, khởi động `SwingWorker` |

### 6.5 `SwingWorker` trong Bài 7

```java
SwingWorker<List<String>, Integer> worker = new SwingWorker<>() {
    @Override
    protected List<String> doInBackground() throws Exception {
        List<String> matchingLines = new ArrayList<>();
        long totalBytes = Files.size(selectedFile.toPath());
        long readBytes = 0;
        int lineNum = 0;

        try (BufferedReader reader = Files.newBufferedReader(
                selectedFile.toPath(), StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNum++;
                readBytes += line.getBytes(StandardCharsets.UTF_8).length + 1;
                if (line.toLowerCase().contains(keyword.toLowerCase())) {
                    matchingLines.add(lineNum + ": " + line);
                }
                int progress = (int) Math.min(100, readBytes * 100 / totalBytes);
                publish(progress);
            }
        }
        ...
        return results;
    }

    @Override
    protected void process(List<Integer> chunks) {
        progressBar.setValue(chunks.get(chunks.size() - 1));
    }

    @Override
    protected void done() {
        try {
            txtResult.setText(String.join("\n", get()));
        } catch (Exception ex) {
            txtResult.setText("Loi: " + ex.getMessage());
        }
        progressBar.setValue(100);
        btnSearch.setEnabled(true);
        btnChoose.setEnabled(true);
    }
};
worker.execute();
```

**Đặc điểm kỹ thuật:**

1. **Đọc file UTF-8** bằng `Files.newBufferedReader(path, StandardCharsets.UTF_8)` để không lệ thuộc vào `Charset.defaultCharset()`.
2. **Tính tiến độ theo byte** (`readBytes / totalBytes`) thay vì theo dòng, giúp thanh tiến trình phản ánh chính xác với file có dòng dài ngắn khác nhau.
3. **Tìm không phân biệt hoa/thường** bằng `toLowerCase()` ở cả dòng đọc và từ khóa.
4. **Không làm đơ EDT**: thân vòng lặp chạy trong worker thread; UI chỉ cập nhật trong `process()` và `done()`.
5. **Vô hiệu hóa nút** trong khi tìm kiếm (`btnSearch`, `btnChoose`) để tránh người dùng bấm chồng tác vụ.

---

## 7. CÁC LỆNH CHẠY

```powershell
# Build
cd "d:/Bai-Tap-Java/lab4/lab04-swingworker"
mvn clean compile

# Chạy Bài 7 (PowerShell cần đặt tham số trong cặp nháy)
mvn exec:java "-Dexec.mainClass=vn.edu.eaut.lab4.FileSearchFrame"

# Đóng gói jar
mvn clean package
```

> Lưu ý cho PowerShell: `-Dexec.mainClass=...` phải đặt trong cặp nháy (`"-D..."` hoặc `'-D...'`) để tránh Maven hiểu nhầm thành một lifecycle phase.

---

## 8. KIỂM THỬ THỰC TẾ

| Bước | Thao tác | Kết quả mong đợi |
|------|----------|------------------|
| 1 | Khởi động app | Cửa sổ "Bai 7 - Tim kiem tu khoa trong file" hiện ra |
| 2 | Click "Chon file" → chọn `info.txt` | `lblFile` hiện tên file, ô nhập từ khóa và nút "Tim kiem" xuất hiện |
| 3 | Gõ từ khóa → click "Tim kiem" | Thanh tiến trình chạy, kết quả hiện trong `txtResult` |
| 4 | Tìm xong | Nút "Tim kiem" và "Chon file" được bật lại |
| 5 | Chọn lại file khác rồi tìm | Kết quả cập nhật đúng với file mới |

---

## 9. KẾT LUẬN

Lab 4 đã hoàn thành các yêu cầu cốt lõi:

- ✅ Hiểu và áp dụng `SwingWorker` để xử lý tác vụ nặng không đơ giao diện.
- ✅ Sử dụng `publish()` / `process()` để cập nhật tiến độ thời gian thực.
- ✅ Sử dụng `done()` để đưa kết quả cuối cùng về EDT.
- ✅ Bài 7 — Tìm kiếm từ khóa trong file: hoạt động đúng, có thanh tiến trình, không phân biệt hoa/thường.
- ✅ Rút ra bài học về `BorderLayout`: mỗi vùng chỉ giữ 1 component, cần bọc `JPanel` trung gian nếu muốn nhiều component.

Hướng phát triển:

- Áp dụng `setProgress(int)` thay cho `publish(progress)` để tận dụng `PropertyChangeListener` (mẫu chuẩn hơn cho thanh tiến trình).
- Thêm nút "Hủy" gọi `worker.cancel(true)` và kiểm tra `isCancelled()` trong vòng lặp.
- Hỗ trợ tìm kiếm bằng biểu thức chính quy (regex).