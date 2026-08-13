package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.ThongKeDAL;
import vn.edu.eaut.lab5.model.HoaDon;
import vn.edu.eaut.lab5.model.SanPham;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ThongKeBUS {
    private final ThongKeDAL dal = new ThongKeDAL();

    public BigDecimal tinhDoanhThu(LocalDate tuNgay, LocalDate denNgay) throws SQLException {
        return dal.tinhDoanhThu(tuNgay, denNgay);
    }

    public HoaDon findHoaDonCaoNhat() throws SQLException {
        return dal.findHoaDonCaoNhat();
    }

    public SanPham findSanPhamBanChayNhat() throws SQLException {
        return dal.findSanPhamBanChayNhat();
    }

    public List<HoaDon> findTopHoaDon(int limit) throws SQLException {
        return dal.findTopHoaDon(limit);
    }
}
