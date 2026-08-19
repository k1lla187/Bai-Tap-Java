package vn.edu.eaut.lab7.model;

public class DiemSinhVien {
    private int id;
    private int sinhVienId;
    private double diemChuyen;
    private double diemGiuaKy;
    private double diemCuoiKy;
    private double diemTongKet;
    private String xepLoai;

    public DiemSinhVien() {}

    public DiemSinhVien(int id, int sinhVienId, double diemChuyen, double diemGiuaKy, double diemCuoiKy) {
        this.id = id;
        this.sinhVienId = sinhVienId;
        this.diemChuyen = diemChuyen;
        this.diemGiuaKy = diemGiuaKy;
        this.diemCuoiKy = diemCuoiKy;
        tinhTongKetVaXepLoai();
    }

    private void tinhTongKetVaXepLoai() {
        this.diemTongKet = (diemChuyen + diemGiuaKy * 2 + diemCuoiKy * 3) / 6;
        if (diemTongKet >= 8.5) this.xepLoai = "A";
        else if (diemTongKet >= 7) this.xepLoai = "B";
        else if (diemTongKet >= 5.5) this.xepLoai = "C";
        else if (diemTongKet >= 4) this.xepLoai = "D";
        else this.xepLoai = "F";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSinhVienId() { return sinhVienId; }
    public void setSinhVienId(int sinhVienId) { this.sinhVienId = sinhVienId; }

    public double getDiemChuyen() { return diemChuyen; }
    public void setDiemChuyen(double diemChuyen) { this.diemChuyen = diemChuyen; tinhTongKetVaXepLoai(); }

    public double getDiemGiuaKy() { return diemGiuaKy; }
    public void setDiemGiuaKy(double diemGiuaKy) { this.diemGiuaKy = diemGiuaKy; tinhTongKetVaXepLoai(); }

    public double getDiemCuoiKy() { return diemCuoiKy; }
    public void setDiemCuoiKy(double diemCuoiKy) { this.diemCuoiKy = diemCuoiKy; tinhTongKetVaXepLoai(); }

    public double getDiemTongKet() { return diemTongKet; }
    public String getXepLoai() { return xepLoai; }
}
