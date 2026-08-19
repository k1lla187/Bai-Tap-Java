package vn.edu.eaut.lab7.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import vn.edu.eaut.lab7.model.SanPham;

public class SanPhamRepository {
    private static final List<SanPham> data = new ArrayList<>();
    private static int autoId = 4;

    static {
        data.add(new SanPham(1, "SP001", "Laptop Dell", "Laptop gaming", 15000000, 5));
        data.add(new SanPham(2, "SP002", "Mouse Logitech", "Chuột không dây", 500000, 20));
        data.add(new SanPham(3, "SP003", "Keyboard Mechanical", "Bàn phím cơ", 2000000, 10));
    }

    public List<SanPham> findAll() {
        return new ArrayList<>(data);
    }

    public SanPham findById(int id) {
        return data.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    public void add(SanPham sp) {
        if (sp.getGia() <= 0 || sp.getSoLuong() < 0) {
            throw new IllegalArgumentException("Giá phải > 0 và số lượng >= 0");
        }
        sp.setId(autoId++);
        data.add(sp);
    }

    public void update(SanPham sp) {
        if (sp.getGia() <= 0 || sp.getSoLuong() < 0) {
            throw new IllegalArgumentException("Giá phải > 0 và số lượng >= 0");
        }
        SanPham old = findById(sp.getId());
        if (old != null) {
            old.setMa(sp.getMa());
            old.setTen(sp.getTen());
            old.setMoTa(sp.getMoTa());
            old.setGia(sp.getGia());
            old.setSoLuong(sp.getSoLuong());
        }
    }

    public void delete(int id) {
        data.removeIf(x -> x.getId() == id);
    }

    public List<SanPham> search(String key) {
        if (key == null || key.trim().isEmpty()) return new ArrayList<>(data);
        String k = key.toLowerCase();
        return data.stream()
                .filter(x -> x.getTen().toLowerCase().contains(k))
                .collect(Collectors.toList());
    }
}
