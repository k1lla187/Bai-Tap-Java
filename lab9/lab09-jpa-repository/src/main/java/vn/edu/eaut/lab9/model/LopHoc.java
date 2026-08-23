package vn.edu.eaut.lab9.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class for LopHoc (Class)
 */
@Entity
@Table(name = "lop_hoc")
public class LopHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ten_lop", nullable = false, length = 50)
    private String tenLop;

    @Column(name = "giao_vien_chu_nhiem", length = 100)
    private String giaoVienChuNhiem;

    @OneToMany(mappedBy = "lopHoc", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SinhVien> sinhViens = new ArrayList<>();

    public LopHoc() {
    }

    public LopHoc(String tenLop, String giaoVienChuNhiem) {
        this.tenLop = tenLop;
        this.giaoVienChuNhiem = giaoVienChuNhiem;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public String getGiaoVienChuNhiem() {
        return giaoVienChuNhiem;
    }

    public void setGiaoVienChuNhiem(String giaoVienChuNhiem) {
        this.giaoVienChuNhiem = giaoVienChuNhiem;
    }

    public List<SinhVien> getSinhViens() {
        return sinhViens;
    }

    public void setSinhViens(List<SinhVien> sinhViens) {
        this.sinhViens = sinhViens;
    }

    public void addSinhVien(SinhVien sinhVien) {
        sinhViens.add(sinhVien);
        sinhVien.setLopHoc(this);
    }

    public void removeSinhVien(SinhVien sinhVien) {
        sinhViens.remove(sinhVien);
        sinhVien.setLopHoc(null);
    }
}
