package vn.edu.eaut.lab7.repository;

import java.util.ArrayList;
import java.util.List;

import vn.edu.eaut.lab7.model.DiemSinhVien;

public class DiemSinhVienRepository {
    private static final List<DiemSinhVien> data = new ArrayList<>();
    private static int autoId = 3;

    static {
        data.add(new DiemSinhVien(1, 1, 8.0, 8.5, 9.0));
        data.add(new DiemSinhVien(2, 2, 7.0, 7.5, 8.0));
    }

    public List<DiemSinhVien> findAll() {
        return new ArrayList<>(data);
    }

    public DiemSinhVien findById(int id) {
        return data.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    public DiemSinhVien findBySinhVienId(int sinhVienId) {
        return data.stream().filter(x -> x.getSinhVienId() == sinhVienId).findFirst().orElse(null);
    }

    public void add(DiemSinhVien diem) {
        diem.setId(autoId++);
        data.add(diem);
    }

    public void update(DiemSinhVien diem) {
        DiemSinhVien old = findById(diem.getId());
        if (old != null) {
            old.setDiemChuyen(diem.getDiemChuyen());
            old.setDiemGiuaKy(diem.getDiemGiuaKy());
            old.setDiemCuoiKy(diem.getDiemCuoiKy());
        }
    }

    public void delete(int id) {
        data.removeIf(x -> x.getId() == id);
    }

    public List<DiemSinhVien> findAll_WithPaging(int page, int pageSize) {
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, data.size());
        return new ArrayList<>(data.subList(start, end));
    }
}
