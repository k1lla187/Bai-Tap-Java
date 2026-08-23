package vn.edu.eaut.lab9.service;

import vn.edu.eaut.lab9.model.Diem;
import vn.edu.eaut.lab9.model.SinhVien;
import vn.edu.eaut.lab9.model.MonHoc;
import vn.edu.eaut.lab9.repository.DiemRepository;
import vn.edu.eaut.lab9.repository.SinhVienRepository;
import vn.edu.eaut.lab9.repository.MonHocRepository;
import java.util.List;

/**
 * Service class for Diem (Grade) business logic
 */
public class DiemService {

    private final DiemRepository diemRepository = new DiemRepository();
    private final SinhVienRepository sinhVienRepository = new SinhVienRepository();
    private final MonHocRepository monHocRepository = new MonHocRepository();

    public List<Diem> findAll() {
        return diemRepository.findAll();
    }

    public Diem findById(Integer id) {
        return diemRepository.findById(id);
    }

    public List<Diem> findBySinhVienId(Integer sinhVienId) {
        return diemRepository.findBySinhVienId(sinhVienId);
    }

    public List<Diem> findByMonHocId(Integer monHocId) {
        return diemRepository.findByMonHocId(monHocId);
    }

    public String save(Diem diem) {
        // Validate
        if (diem.getSinhVien() == null || diem.getSinhVien().getId() == null) {
            return "Vui long chon sinh vien";
        }
        if (diem.getMonHoc() == null || diem.getMonHoc().getId() == null) {
            return "Vui long chon mon hoc";
        }
        
        // Load full entities
        SinhVien sv = sinhVienRepository.findById(diem.getSinhVien().getId());
        MonHoc mh = monHocRepository.findById(diem.getMonHoc().getId());
        
        if (sv == null) {
            return "Sinh vien khong ton tai";
        }
        if (mh == null) {
            return "Mon hoc khong ton tai";
        }
        
        diem.setSinhVien(sv);
        diem.setMonHoc(mh);
        diemRepository.save(diem);
        return null;
    }

    public String update(Diem diem) {
        diemRepository.update(diem);
        return null;
    }

    public void delete(Integer id) {
        diemRepository.delete(id);
    }

    /**
     * Transaction: Add student with default grades
     */
    public String addSinhVienWithDefaultDiem(SinhVien sinhVien, MonHoc monHoc) {
        if (sinhVien.getMaSinhVien() == null || sinhVien.getMaSinhVien().trim().isEmpty()) {
            return "Ma sinh vien khong duoc trong";
        }
        if (sinhVien.getHoTen() == null || sinhVien.getHoTen().trim().isEmpty()) {
            return "Ho ten khong duoc trong";
        }
        if (monHoc == null || monHoc.getId() == null) {
            return "Vui long chon mon hoc";
        }
        
        try {
            diemRepository.saveDiemVaDiemMacDinh(sinhVien, monHoc);
            return null;
        } catch (Exception e) {
            return "Loi khi tao sinh vien va diem: " + e.getMessage();
        }
    }
}
