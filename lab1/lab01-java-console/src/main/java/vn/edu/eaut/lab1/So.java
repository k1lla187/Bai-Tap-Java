package vn.edu.eaut.lab1;

public class So {

    // ==========================================
    // BÀI 1: TÍNH TỔNG SỐ CHẲN (s = 2 + 4 + ... + n)
    // ==========================================
    public static long tinhTongSoChan(int n) {
        if (n < 2) return 0;
        long sum = 0;
        int limit = (n % 2 == 0) ? n : n - 1; // Nếu lẻ thì lấy số chẵn lớn nhất < n
        for (int i = 2; i <= limit; i += 2) {
            sum += i;
        }
        return sum;
    }

    // ==========================================
    // BÀI 2: TÍNH TỔNG NGHỊCH ĐẢO (s = 1 + 1/2 + ... + 1/n)
    // ==========================================
    public static double tinhTongNghichDao(int n) {
        if (n <= 0) return 0;
        double sum = 0.0;
        for (int i = 1; i <= n; i++) {
            sum += 1.0 / i; // Chia số thực 1.0/i để không bị mất phần thập phân
        }
        return sum;
    }

    // ==========================================
    // BÀI 3: KIỂM TRA SỐ NGUYÊN TỐ
    // ==========================================
    public static boolean isNguyenTo(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    // ==========================================
    // BÀI 4: KIỂM TRA VÀ PHÂN LOẠI TAM GIÁC
    // ==========================================
    public static String phanLoaiTamGiac(double a, double b, double c) {
        // Kiểm tra điều kiện tồn tại tam giác
        if (a <= 0 || b <= 0 || c <= 0 || (a + b <= c) || (a + c <= b) || (b + c <= a)) {
            return "Không phải 3 cạnh của tam giác";
        }

        // Kiểm tra tam giác đều
        if (a == b && b == c) {
            return "Tam giác đều";
        }

        // Kiểm tra vuông (sử dụng sai số EPSILON để so sánh số thực double)
        double eps = 1e-6;
        boolean vuong = Math.abs(a * a + b * b - c * c) < eps ||
                Math.abs(a * a + c * c - b * b) < eps ||
                Math.abs(b * b + c * c - a * a) < eps;

        // Kiểm tra cân
        boolean can = (a == b) || (a == c) || (b == c);

        if (vuong && can) return "Tam giác vuông cân";
        if (vuong) return "Tam giác vuông";
        if (can) return "Tam giác cân";

        return "Tam giác thường";
    }

    // ==========================================
    // BÀI 5: HIỂN THỊ N SỐ FIBONACCI ĐẦU TIÊN
    // ==========================================
    public static String layDayFibonacci(int n) {
        if (n <= 0) return "";
        if (n == 1) return "0";

        StringBuilder sb = new StringBuilder("0 1");
        long f0 = 0;
        long f1 = 1;

        for (int i = 3; i <= n; i++) {
            long fn = f0 + f1;
            sb.append(" ").append(fn);
            f0 = f1;
            f1 = fn;
        }

        return sb.toString();
    }
}