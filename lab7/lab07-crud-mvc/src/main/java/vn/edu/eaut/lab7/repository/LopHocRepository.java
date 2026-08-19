package vn.edu.eaut.lab7.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import vn.edu.eaut.lab7.model.LopHoc;

public class LopHocRepository {
    private static final List<LopHoc> data = new ArrayList<>();
    private static int autoId = 3;

    static {
        data.add(new LopHoc(1, "L001", "Lớp 12A1", "Nguyễn Văn B", 35));
        data.add(new LopHoc(2, "L002", "Lớp 12A2", "Trần Thị C", 32));
    }

    public List<LopHoc> findAll() {
        return new ArrayList<>(data);
    }

    public LopHoc findById(int id) {
        return data.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    public void add(LopHoc lop) {
        lop.setId(autoId++);
        data.add(lop);
    }

    public void update(LopHoc lop) {
        LopHoc old = findById(lop.getId());
        if (old != null) {
            old.setMaLop(lop.getMaLop());
            old.setTenLop(lop.getTenLop());
            old.setCoVanHocTap(lop.getCoVanHocTap());
            old.setSoLuongSinhVien(lop.getSoLuongSinhVien());
        }
    }

    public void delete(int id) {
        data.removeIf(x -> x.getId() == id);
    }

    public List<LopHoc> search(String key) {
        if (key == null || key.trim().isEmpty()) return new ArrayList<>(data);
        String k = key.toLowerCase();
        return data.stream()
                .filter(x -> x.getMaLop().toLowerCase().contains(k) ||
                        x.getTenLop().toLowerCase().contains(k))
                .collect(Collectors.toList());
    }
}
