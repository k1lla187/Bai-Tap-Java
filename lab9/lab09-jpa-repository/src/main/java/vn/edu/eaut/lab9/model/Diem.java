package vn.edu.eaut.lab9.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Entity class for Diem (Grade)
 */
@Entity
@Table(name = "diem")
public class Diem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sinh_vien_id", nullable = false)
    private SinhVien sinhVien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mon_hoc_id", nullable = false)
    private MonHoc monHoc;

    @Column(name = "diem_giua_ky")
    private BigDecimal diemGiuaKy;

    @Column(name = "diem_cuoi_ky")
    private BigDecimal diemCuoiKy;

    @Column(name = "diem_tong_ket")
    private BigDecimal diemTongKet;

    @Column(length = 20)
    private String xepLoai;

    public Diem() {
    }

    public Diem(SinhVien sinhVien, MonHoc monHoc) {
        this.sinhVien = sinhVien;
        this.monHoc = monHoc;
    }

    /**
     * Calculate the final grade based on midterm and final exam scores
     * Formula: 30% midterm + 70% final
     */
    public void tinhDiemTongKet() {
        if (diemGiuaKy != null && diemCuoiKy != null) {
            BigDecimal diem = diemGiuaKy.multiply(new BigDecimal("0.3"))
                    .add(diemCuoiKy.multiply(new BigDecimal("0.7")));
            this.diemTongKet = diem.setScale(2, java.math.RoundingMode.HALF_UP);
            this.xepLoai = tinhXepLoai(this.diemTongKet);
        }
    }

    private String tinhXepLoai(BigDecimal diem) {
        if (diem == null) return "Chua co diem";
        double d = diem.doubleValue();
        if (d >= 8.5) return "Gioi";
        if (d >= 7.0) return "Kha";
        if (d >= 5.5) return "Trung binh";
        if (d >= 4.0) return "Yeu";
        return "Kem";
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SinhVien getSinhVien() {
        return sinhVien;
    }

    public void setSinhVien(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
    }

    public MonHoc getMonHoc() {
        return monHoc;
    }

    public void setMonHoc(MonHoc monHoc) {
        this.monHoc = monHoc;
    }

    public BigDecimal getDiemGiuaKy() {
        return diemGiuaKy;
    }

    public void setDiemGiuaKy(BigDecimal diemGiuaKy) {
        this.diemGiuaKy = diemGiuaKy;
    }

    public BigDecimal getDiemCuoiKy() {
        return diemCuoiKy;
    }

    public void setDiemCuoiKy(BigDecimal diemCuoiKy) {
        this.diemCuoiKy = diemCuoiKy;
    }

    public BigDecimal getDiemTongKet() {
        return diemTongKet;
    }

    public void setDiemTongKet(BigDecimal diemTongKet) {
        this.diemTongKet = diemTongKet;
    }

    public String getXepLoai() {
        return xepLoai;
    }

    public void setXepLoai(String xepLoai) {
        this.xepLoai = xepLoai;
    }
}
