package vn.edu.eaut.lab15.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import vn.edu.eaut.lab15.entity.Course;
import vn.edu.eaut.lab15.entity.Enrollment;
import vn.edu.eaut.lab15.entity.Student;
import vn.edu.eaut.lab15.repository.CourseRepository;
import vn.edu.eaut.lab15.repository.EnrollmentRepository;
import vn.edu.eaut.lab15.repository.StudentRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Khởi tạo dữ liệu mẫu khi ứng dụng khởi động.
 * Giúp giảng viên/sinh viên kiểm thử toàn diện các chức năng ngay lập tức.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public DataInitializer(StudentRepository studentRepository,
                           CourseRepository courseRepository,
                           EnrollmentRepository enrollmentRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public void run(String... args) {
        if (studentRepository.count() == 0) {
            // 1. Thêm danh sách Sinh viên mẫu
            Student s1 = new Student("SV001", "Nguyễn Văn An", LocalDate.of(2003, 5, 15));
            Student s2 = new Student("SV002", "Trần Thị Bích", LocalDate.of(2003, 8, 22));
            Student s3 = new Student("SV003", "Lê Hoàng Long", LocalDate.of(2002, 11, 10));
            Student s4 = new Student("SV004", "Phạm Minh Đức", LocalDate.of(2003, 1, 30));
            Student s5 = new Student("SV005", "Vũ Thị Hương", LocalDate.of(2004, 3, 18));
            Student s6 = new Student("SV006", "Đặng Quốc Hưng", LocalDate.of(2002, 9, 25));

            List<Student> savedStudents = studentRepository.saveAll(Arrays.asList(s1, s2, s3, s4, s5, s6));

            // 2. Thêm danh sách Khóa học / Học phần mẫu
            Course c1 = new Course("CS101", "Lập trình Java căn bản", 3);
            Course c2 = new Course("CS102", "Cấu trúc dữ liệu & Giải thuật", 4);
            Course c3 = new Course("CS103", "Hệ quản trị Cơ sở dữ liệu", 3);
            Course c4 = new Course("CS104", "Phát triển ứng dụng Spring Boot", 4);
            Course c5 = new Course("CS105", "Mạng máy tính & An toàn thông tin", 3);

            List<Course> savedCourses = courseRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5));

            // 3. Thêm các lượt Đăng ký học phần mẫu
            Enrollment e1 = new Enrollment(LocalDate.now().minusDays(5), savedStudents.get(0), savedCourses.get(0));
            Enrollment e2 = new Enrollment(LocalDate.now().minusDays(5), savedStudents.get(0), savedCourses.get(3));
            Enrollment e3 = new Enrollment(LocalDate.now().minusDays(4), savedStudents.get(1), savedCourses.get(1));
            Enrollment e4 = new Enrollment(LocalDate.now().minusDays(3), savedStudents.get(2), savedCourses.get(2));
            Enrollment e5 = new Enrollment(LocalDate.now().minusDays(2), savedStudents.get(3), savedCourses.get(3));
            Enrollment e6 = new Enrollment(LocalDate.now().minusDays(1), savedStudents.get(4), savedCourses.get(4));

            enrollmentRepository.saveAll(Arrays.asList(e1, e2, e3, e4, e5, e6));

            System.out.println(">>> Đã khởi tạo dữ liệu mẫu thành công cho Lab 15!");
        }
    }
}
