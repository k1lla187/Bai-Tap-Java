package vn.edu.eaut.lab1;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int luaChon;

        do {
            System.out.println("\n==========================================");
            System.out.println("       CHƯƠNG TRÌNH XỬ LÝ LAB 1 JAVA     ");
            System.out.println("==========================================");
            System.out.println("1. Bài 1: Tính tổng số chẵn");
            System.out.println("2. Bài 2: Tính tổng nghịch đảo");
            System.out.println("3. Bài 3: Kiểm tra số nguyên tố");
            System.out.println("4. Bài 4: Kiểm tra và phân loại tam giác");
            System.out.println("5. Bài 5: Hiển thị dãy Fibonacci");
            System.out.println("0. Thoát chương trình");
            System.out.println("------------------------------------------");
            System.out.print("Chọn bài tập (0-5): ");

            while (!scanner.hasNextInt()) {
                System.out.print("Vui lòng nhập một số nguyên: ");
                scanner.next();
            }
            luaChon = scanner.nextInt();

            switch (luaChon) {
                case 1:
                    System.out.println("\n--- BÀI 1: TÍNH TỔNG SỐ CHẲN ---");
                    int n1 = nhapSoNguyenDuong(scanner, "Nhập n nguyên dương: ");
                    System.out.printf("Kết quả s = %d%n", So.tinhTongSoChan(n1));
                    break;

                case 2:
                    System.out.println("\n--- BÀI 2: TÍNH TỔNG NGHỊCH ĐẢO ---");
                    int n2 = nhapSoNguyenDuong(scanner, "Nhập n nguyên dương: ");
                    System.out.printf("Kết quả s = %.4f%n", So.tinhTongNghichDao(n2));
                    break;

                case 3:
                    System.out.println("\n--- BÀI 3: KIỂM TRA SỐ NGUYÊN TỐ ---");
                    System.out.print("Nhập số nguyên n: ");
                    while (!scanner.hasNextInt()) {
                        System.out.print("Vui lòng nhập số nguyên: ");
                        scanner.next();
                    }
                    int n3 = scanner.nextInt();
                    if (So.isNguyenTo(n3)) {
                        System.out.printf("n = %d -> Nguyên tố%n", n3);
                    } else {
                        System.out.printf("n = %d -> Không nguyên tố%n", n3);
                    }
                    break;

                case 4:
                    System.out.println("\n--- BÀI 4: PHÂN LOẠI TAM GIÁC ---");
                    System.out.print("Nhập cạnh a: ");
                    double a = scanner.nextDouble();
                    System.out.print("Nhập cạnh b: ");
                    double b = scanner.nextDouble();
                    System.out.print("Nhập cạnh c: ");
                    double c = scanner.nextDouble();
                    System.out.println("Kết quả: " + So.phanLoaiTamGiac(a, b, c));
                    break;

                case 5:
                    System.out.println("\n--- BÀI 5: HIỂN THỊ DÃY FIBONACCI ---");
                    int n5 = nhapSoNguyenDuong(scanner, "Nhập n nguyên dương: ");
                    System.out.printf("n = %d -> %s%n", n5, So.layDayFibonacci(n5));
                    break;

                case 0:
                    System.out.println("Đã thoát chương trình!");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại!");
            }

        } while (luaChon != 0);

        scanner.close();
    }

    // Hàm phụ trợ nhập số nguyên dương > 0
    private static int nhapSoNguyenDuong(Scanner scanner, String thongBao) {
        int n;
        do {
            System.out.print(thongBao);
            while (!scanner.hasNextInt()) {
                System.out.print("Phải nhập số nguyên! Thử lại: ");
                scanner.next();
            }
            n = scanner.nextInt();
            if (n <= 0) {
                System.out.println("n phải lớn hơn 0!");
            }
        } while (n <= 0);
        return n;
    }
}