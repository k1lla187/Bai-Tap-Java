package vn.edu.eaut.lab10.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "sinh_vien")
public class SinhVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_sv", nullable = false, unique = true, length = 20)
    private String maSV;

    @Column(name = "ho_ten", nullable = false, length = 100)
    private String hoTen;

    @Column(length = 100)
    private String email;

    private String lop;

    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;

    private boolean active = true;

    public SinhVien() {}

    public SinhVien(String maSV, String hoTen, String email, String lop) {
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.email = email;
        this.lop = lop;
        this.active = true;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getMaSV() { return maSV; }
    public void setMaSV(String maSV) { this.maSV = maSV; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getLop() { return lop; }
    public void setLop(String lop) { this.lop = lop; }
    public LocalDate getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(LocalDate ngaySinh) { this.ngaySinh = ngaySinh; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
