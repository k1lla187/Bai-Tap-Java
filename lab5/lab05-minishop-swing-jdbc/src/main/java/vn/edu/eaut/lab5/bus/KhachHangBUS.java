package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.KhachHangDAL;
import vn.edu.eaut.lab5.model.KhachHang;

import java.sql.SQLException;
import java.util.List;

public class KhachHangBUS {
    private final KhachHangDAL dal = new KhachHangDAL();

    public List<KhachHang> findAll() throws SQLException {
        return dal.findAll();
    }

    public boolean save(KhachHang kh) throws SQLException {
        validate(kh);
        if (kh.getMaKh() == 0) {
            return dal.insert(kh);
        }
        return dal.update(kh);
    }

    public boolean delete(int maKh) throws SQLException {
        if (maKh <= 0) {
            throw new IllegalArgumentException("Ma khach hang khong hop le");
        }
        return dal.delete(maKh);
    }

    public List<KhachHang> searchByName(String keyword) throws SQLException {
        return dal.searchByName(keyword);
    }

    private void validate(KhachHang kh) {
        if (kh.getTenKh() == null || kh.getTenKh().trim().isEmpty()) {
            throw new IllegalArgumentException("Ten khach hang khong duoc rong");
        }
        if (kh.getSdt() == null || !kh.getSdt().matches("\\d{1,10}")) {
            throw new IllegalArgumentException("So dien thoai chi gom so va toi da 10 ky tu");
        }
    }
}
