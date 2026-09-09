package vn.edu.eaut.lab15.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.eaut.lab15.entity.Course;
import vn.edu.eaut.lab15.entity.Enrollment;
import vn.edu.eaut.lab15.entity.Student;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class EnrollmentServiceTest {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Test
    @DisplayName("Bài 4: Kiểm tra đăng ký học phần thành công")
    void testEnrollSuccess() {
        Student student = studentService.save(new Student("SV999", "Nguyễn Test", LocalDate.of(2003, 1, 1)));
        Course course = courseService.save(new Course("TEST101", "Môn Test", 3));

        Enrollment enrollment = enrollmentService.enroll(student.getId(), course.getId());

        assertNotNull(enrollment.getId());
        assertEquals("SV999", enrollment.getStudent().getStudentCode());
        assertEquals("TEST101", enrollment.getCourse().getCourseCode());
    }

    @Test
    @DisplayName("Bài 4: Kiểm tra chống đăng ký trùng lặp (ném Exception)")
    void testEnrollDuplicateThrowsException() {
        Student student = studentService.save(new Student("SV888", "Trần Trùng", LocalDate.of(2003, 2, 2)));
        Course course = courseService.save(new Course("TEST102", "Môn Trùng", 4));

        enrollmentService.enroll(student.getId(), course.getId());

        // Đăng ký lại cùng môn cho sinh viên này -> phải ném IllegalArgumentException
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> enrollmentService.enroll(student.getId(), course.getId())
        );
        assertTrue(exception.getMessage().contains("đã đăng ký"));
    }

    @Test
    @DisplayName("Bài 7: Kiểm tra hủy đăng ký học phần")
    void testCancelEnrollment() {
        Student student = studentService.save(new Student("SV777", "Lê Hủy", LocalDate.of(2003, 3, 3)));
        Course course = courseService.save(new Course("TEST103", "Môn Hủy", 3));

        Enrollment enrollment = enrollmentService.enroll(student.getId(), course.getId());
        Long enrollmentId = enrollment.getId();

        enrollmentService.cancel(enrollmentId);

        assertTrue(enrollmentService.findById(enrollmentId).isEmpty());
    }

    @Test
    @DisplayName("Bài 8: Kiểm tra lấy danh sách môn học đã đăng ký của 1 sinh viên")
    void testGetEnrollmentsByStudent() {
        Student student = studentService.save(new Student("SV666", "Phạm Lịch Sử", LocalDate.of(2003, 4, 4)));
        Course c1 = courseService.save(new Course("TEST104", "Môn 1", 3));
        Course c2 = courseService.save(new Course("TEST105", "Môn 2", 4));

        enrollmentService.enroll(student.getId(), c1.getId());
        enrollmentService.enroll(student.getId(), c2.getId());

        List<Enrollment> list = enrollmentService.findByStudentId(student.getId());
        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("Bài 9: Kiểm tra thống kê tổng số lượt đăng ký")
    void testCountEnrollments() {
        long count = enrollmentService.count();
        assertTrue(count >= 6, "Dữ liệu mẫu ban đầu có ít nhất 6 lượt đăng ký");
    }
}
