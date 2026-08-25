package vn.edu.eaut.lab10.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "diem")
public class Diem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sinh_vien_id")
    private SinhVien sinhVien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mon_hoc_id")
    private MonHoc monHoc;

    @Column(name = "diem_gk")
    private BigDecimal diemGK;

    @Column(name = "diem_ck")
    private BigDecimal diemCK;

    @Column(name = "diem_tong_ket")
    private BigDecimal diemTongKet;

    @Column(length = 20)
    private String xepLoai;

    public Diem() {}

    public Diem(SinhVien sinhVien, MonHoc monHoc) {
        this.sinhVien = sinhVien;
        this.monHoc = monHoc;
    }

    public void tinhDiemTongKet() {
        if (diemGK != null && diemCK != null) {
            BigDecimal d = diemGK.multiply(new BigDecimal("0.3"))
                    .add(diemCK.multiply(new BigDecimal("0.7")));
            this.diemTongKet = d.setScale(2, java.math.RoundingMode.HALF_UP);
            this.xepLoai = tinhXepLoai(this.diemTongKet);
        }
    }

    private String tinhXepLoai(BigDecimal d) {
        if (d == null) return "Chua co diem";
        double v = d.doubleValue();
        if (v >= 8.5) return "Gioi";
        if (v >= 7.0) return "Kha";
        if (v >= 5.5) return "Trung binh";
        if (v >= 4.0) return "Yeu";
        return "Kem";
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public SinhVien getSinhVien() { return sinhVien; }
    public void setSinhVien(SinhVien sinhVien) { this.sinhVien = sinhVien; }
    public MonHoc getMonHoc() { return monHoc; }
    public void setMonHoc(MonHoc monHoc) { this.monHoc = monHoc; }
    public BigDecimal getDiemGK() { return diemGK; }
    public void setDiemGK(BigDecimal diemGK) { this.diemGK = diemGK; }
    public BigDecimal getDiemCK() { return diemCK; }
    public void setDiemCK(BigDecimal diemCK) { this.diemCK = diemCK; }
    public BigDecimal getDiemTongKet() { return diemTongKet; }
    public void setDiemTongKet(BigDecimal diemTongKet) { this.diemTongKet = diemTongKet; }
    public String getXepLoai() { return xepLoai; }
    public void setXepLoai(String xepLoai) { this.xepLoai = xepLoai; }
}
