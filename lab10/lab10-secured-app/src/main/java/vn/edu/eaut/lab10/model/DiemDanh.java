package vn.edu.eaut.lab10.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "diem_danh")
public class DiemDanh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sinh_vien_id", nullable = false)
    private SinhVien sinhVien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mon_hoc_id", nullable = false)
    private MonHoc monHoc;

    @Column(name = "ngay_dd", nullable = false)
    private LocalDate ngayDiemDanh;

    @Column(name = "buoi_hoc", length = 50)
    private String buoiHoc;

    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai", length = 20)
    private TrangThaiDiemDanh trangThai;

    @Column(length = 200)
    private String ghiChu;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public enum TrangThaiDiemDanh {
        CO_MAT("Co mat"),
        VANG("Vang"),
        DI_LATE("Di muon"),
        PHEP("Co phep");

        private final String label;
        TrangThaiDiemDanh(String label) { this.label = label; }
        public String getLabel() { return label; }
    }

    public DiemDanh() {}

    public DiemDanh(SinhVien sinhVien, MonHoc monHoc, LocalDate ngayDiemDanh, TrangThaiDiemDanh trangThai) {
        this.sinhVien = sinhVien;
        this.monHoc = monHoc;
        this.ngayDiemDanh = ngayDiemDanh;
        this.trangThai = trangThai;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public SinhVien getSinhVien() { return sinhVien; }
    public void setSinhVien(SinhVien sinhVien) { this.sinhVien = sinhVien; }
    public MonHoc getMonHoc() { return monHoc; }
    public void setMonHoc(MonHoc monHoc) { this.monHoc = monHoc; }
    public LocalDate getNgayDiemDanh() { return ngayDiemDanh; }
    public void setNgayDiemDanh(LocalDate ngayDiemDanh) { this.ngayDiemDanh = ngayDiemDanh; }
    public String getBuoiHoc() { return buoiHoc; }
    public void setBuoiHoc(String buoiHoc) { this.buoiHoc = buoiHoc; }
    public TrangThaiDiemDanh getTrangThai() { return trangThai; }
    public void setTrangThai(TrangThaiDiemDanh trangThai) { this.trangThai = trangThai; }
    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
