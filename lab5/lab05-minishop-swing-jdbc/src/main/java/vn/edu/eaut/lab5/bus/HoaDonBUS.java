package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.HoaDonDAL;
import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import vn.edu.eaut.lab5.model.HoaDon;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class HoaDonBUS {
    private final HoaDonDAL dal = new HoaDonDAL();

    public List<HoaDon> findAll() throws SQLException {
        return dal.findAll();
    }

    public List<HoaDon> findByDateRange(LocalDate tuNgay, LocalDate denNgay) throws SQLException {
        return dal.findByDateRange(tuNgay, denNgay);
    }

    public int saveHoaDon(int maKh, List<ChiTietHoaDon> chiTietList) throws SQLException {
        if (maKh <= 0) {
            throw new IllegalArgumentException("Vui long chon khach hang");
        }
        if (chiTietList == null || chiTietList.isEmpty()) {
            throw new IllegalArgumentException("Hoa don phai co it nhat mot san pham");
        }
        return dal.insertHoaDon(maKh, chiTietList);
    }

    public List<ChiTietHoaDon> findChiTietByMaHd(int maHd) throws SQLException {
        return dal.findChiTietByMaHd(maHd);
    }
}
