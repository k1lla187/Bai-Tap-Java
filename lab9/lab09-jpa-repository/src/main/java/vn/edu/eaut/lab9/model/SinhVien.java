package vn.edu.eaut.lab9.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entity class for SinhVien (Student)
 */
@Entity
@Table(name = "sinh_vien")
public class SinhVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_sinh_vien", nullable = false, unique = true, length = 20)
    private String maSinhVien;

    @Column(name = "ho_ten", nullable = false, length = 100)
    private String hoTen;

    private String email;

    private String lop;

    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lop_hoc_id")
    private LopHoc lopHoc;

    public SinhVien() {
    }

    public SinhVien(String maSinhVien, String hoTen, String email, String lop) {
        this.maSinhVien = maSinhVien;
        this.hoTen = hoTen;
        this.email = email;
        this.lop = lop;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaSinhVien() {
        return maSinhVien;
    }

    public void setMaSinhVien(String maSinhVien) {
        this.maSinhVien = maSinhVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public LopHoc getLopHoc() {
        return lopHoc;
    }

    public void setLopHoc(LopHoc lopHoc) {
        this.lopHoc = lopHoc;
    }
}
