# BAO CAO LAB 4 - SWINGWORKER

**Hoc phan:** Cong nghe Java
**Ngay:** 11/08/2026

---

## 1. CAU TRUC PROJECT

```
lab04-swingworker/
├── pom.xml
└── src/main/java/vn/edu/eaut/lab4/
    ├── App.java
    ├── MenuFrame.java
    ├── Bai01/02/03/04/05 (co goi y)
    └── Bai06/07/08/09/10 (tu lam)
```

---

## 2. BANG TONG HOP BAI TAP

| Bai | Ten | SwingWorker | Components |
|-----|-----|------------|-----------|
| 1 | Dem nguoc | publish/process | JTextField, JLabel |
| 2 | Tai du lieu | setProgress | JProgressBar |
| 3 | Tong so nguyen to | setProgress | JTextField, JProgressBar |
| 4 | Fibonacci | BigInteger memo | JTextField, BigInteger |
| 5 | Dem dong file | setProgress | JFileChooser |
| 6 | Huy tac vu | cancel(true) | JButton |
| 7 | Tim kiem tu khoa | List results | JTextArea |
| 8 | Doc CSV diem SV | List data | JTable |
| 9 | Tai san pham | List data | JTable |
| 10 | Quan ly SP CSV | CRUD + CSV | JTable, JFileChooser |

---

## 3. CAC THANH PHAN SWING DA DUNG

| Nhom | Components |
|------|-----------|
| Container | JFrame, JPanel |
| Input | JTextField, JTextArea |
| Control | JButton, JComboBox |
| Display | JLabel, JTable, JProgressBar |
| File | JFileChooser |

---

## 4. SWINGWORKER

### 4.1 Phuong thuc chinh

| Phuong thuc | Muc dich |
|------------|---------|
| doInBackground() | Xu ly tac vu nang trong thread rieng |
| done() | Cap nhat giao dien sau khi xong |
| publish(V...) | Gui du lieu trung gian ve EDT |
| process(V...) | Nhan du lieu trung gian |
| setProgress() | Dat gia tri tien do |
| cancel(true) | Huy tac vu |

### 4.2 Vi du

```java
SwingWorker<Void, Integer> worker = new SwingWorker<>() {
    @Override
    protected Void doInBackground() throws Exception {
        for (int i = 0; i <= 100; i += 10) {
            setProgress(i);
            Thread.sleep(1000);
        }
        return null;
    }

    @Override
    protected void done() {
        btnLoad.setEnabled(true);
    }
};

worker.addPropertyChangeListener(evt -> {
    if ("progress".equals(evt.getPropertyName())) {
        progressBar.setValue((int) evt.getNewValue());
    }
});
worker.execute();
```

---

## 5. EDT - EVENT DISPATCH THREAD

### 5.1 Khai niem
- EDT la luong duy nhat xu ly giao dien Swing
- Tat ca thay doi giao dien phai chay tren EDT

### 5.2 Khoi dong an toan
```java
SwingUtilities.invokeLater(() -> {
    new MyFrame().setVisible(true);
});
```

### 5.3 Tai sao can SwingWorker?
- Tac vu lau (doc file, tinh toan nang) lam treo EDT
- SwingWorker chay tac vu trong thread rieng
- Ket qua duoc tra ve EDT an toan qua done()

---

## 6. CAC LENH BUILD

```bash
# Build
mvn clean compile

# Chay menu
mvn exec:java -Dexec.mainClass="vn.edu.eaut.lab4.App"

# Chay tung bai
mvn exec:java -Dexec.mainClass="vn.edu.eaut.lab4.CountdownFrame"
mvn exec:java -Dexec.mainClass="vn.edu.eaut.lab4.ProgressDemoFrame"
```

---

## 7. CHUC NANG BAI 10

| Chuc nang | Mo ta |
|-----------|-------|
| Them SP | Nhap ma, ten, don gia |
| Sua SP | Chon dong trong table, cap nhat |
| Xoa SP | Xoa san pham da chon |
| Luu CSV | Luu danh sach ra file CSV |
| Mo CSV | Doc du lieu tu file CSV |

---

## 8. KET LUAN

Lab 4 da hoan thanh cac yeu cau:
- ✅ Hieu va ap dung SwingWorker
- ✅ Xu ly tac vu nang trong nền
- ✅ Cap nhat giao dien qua publish/process, setProgress
- ✅ Xu ly huy tac vu (Bai 6)
- ✅ Doc/ghi file CSV (Bai 10)
- ✅ Khong lam treo giao dien khi xu ly tac vu lau
