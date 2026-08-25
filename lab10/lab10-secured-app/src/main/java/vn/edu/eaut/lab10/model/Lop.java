package vn.edu.eaut.lab10.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lop_hoc")
public class Lop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_lop", nullable = false, unique = true, length = 20)
    private String maLop;

    @Column(name = "ten_lop", nullable = false, length = 100)
    private String tenLop;

    @Column(length = 50)
    private String khoa;

    @Column(name = "si_so")
    private Integer siSo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "giao_vien_id")
    private GiaoVien giaoVien;

    private boolean active = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Lop() {}

    public Lop(String maLop, String tenLop, String khoa) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.khoa = khoa;
        this.active = true;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getMaLop() { return maLop; }
    public void setMaLop(String maLop) { this.maLop = maLop; }
    public String getTenLop() { return tenLop; }
    public void setTenLop(String tenLop) { this.tenLop = tenLop; }
    public String getKhoa() { return khoa; }
    public void setKhoa(String khoa) { this.khoa = khoa; }
    public Integer getSiSo() { return siSo; }
    public void setSiSo(Integer siSo) { this.siSo = siSo; }
    public GiaoVien getGiaoVien() { return giaoVien; }
    public void setGiaoVien(GiaoVien giaoVien) { this.giaoVien = giaoVien; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
