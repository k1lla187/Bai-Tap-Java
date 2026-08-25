package vn.edu.eaut.lab10.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "giao_vien")
public class GiaoVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_gv", nullable = false, unique = true, length = 20)
    private String maGV;

    @Column(name = "ho_ten", nullable = false, length = 100)
    private String hoTen;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column(name = "chuyen_nganh", length = 100)
    private String chuyenNganh;

    private boolean active = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public GiaoVien() {}

    public GiaoVien(String maGV, String hoTen, String email, String chuyenNganh) {
        this.maGV = maGV;
        this.hoTen = hoTen;
        this.email = email;
        this.chuyenNganh = chuyenNganh;
        this.active = true;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getMaGV() { return maGV; }
    public void setMaGV(String maGV) { this.maGV = maGV; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getChuyenNganh() { return chuyenNganh; }
    public void setChuyenNganh(String chuyenNganh) { this.chuyenNganh = chuyenNganh; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
