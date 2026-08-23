package vn.edu.eaut.lab9.service;

import vn.edu.eaut.lab9.model.SinhVien;
import vn.edu.eaut.lab9.repository.SinhVienRepository;
import java.util.List;

/**
 * Service class for SinhVien business logic
 */
public class SinhVienService {

    private final SinhVienRepository repository = new SinhVienRepository();

    public List<SinhVien> findAll() {
        return repository.findAll();
    }

    public SinhVien findById(Integer id) {
        return repository.findById(id);
    }

    public String save(SinhVien sinhVien) {
        // Validate
        if (sinhVien.getMaSinhVien() == null || sinhVien.getMaSinhVien().trim().isEmpty()) {
            return "Ma sinh vien khong duoc trong";
        }
        if (sinhVien.getHoTen() == null || sinhVien.getHoTen().trim().isEmpty()) {
            return "Ho ten khong duoc trong";
        }
        
        // Check duplicate
        if (repository.findByMaSV(sinhVien.getMaSinhVien()) != null) {
            return "Ma sinh vien da ton tai";
        }
        
        repository.save(sinhVien);
        return null;
    }

    public String update(SinhVien sinhVien) {
        if (sinhVien.getHoTen() == null || sinhVien.getHoTen().trim().isEmpty()) {
            return "Ho ten khong duoc trong";
        }
        
        repository.update(sinhVien);
        return null;
    }

    public void delete(Integer id) {
        repository.delete(id);
    }

    public List<SinhVien> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return repository.findAll();
        }
        return repository.search(keyword);
    }

    public List<SinhVien> findAll(int page, int size) {
        int first = (page - 1) * size;
        return repository.findAll(first, size);
    }

    public long count() {
        return repository.count();
    }

    public List<SinhVien> findByLopHocId(Integer lopHocId) {
        return repository.findByLopHocId(lopHocId);
    }
}
