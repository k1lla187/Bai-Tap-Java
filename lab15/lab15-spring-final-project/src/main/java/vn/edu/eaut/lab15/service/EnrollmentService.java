package vn.edu.eaut.lab15.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.eaut.lab15.entity.Course;
import vn.edu.eaut.lab15.entity.Enrollment;
import vn.edu.eaut.lab15.entity.Student;
import vn.edu.eaut.lab15.repository.CourseRepository;
import vn.edu.eaut.lab15.repository.EnrollmentRepository;
import vn.edu.eaut.lab15.repository.StudentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Bài 4, 7, 8, 9: Service xử lý nghiệp vụ Đăng ký học phần.
 */
@Service
@Transactional
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             StudentRepository studentRepository,
                             CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    /**
     * Bài 4: Nghiệp vụ đăng ký học phần, kiểm tra sinh viên/môn học tồn tại và không đăng ký trùng.
     */
    public Enrollment enroll(Long studentId, Long courseId) {
        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new IllegalArgumentException("Sinh viên đã đăng ký môn học này trước đó!");
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sinh viên với ID: " + studentId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy môn học với ID: " + courseId));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollDate(LocalDate.now());

        return enrollmentRepository.save(enrollment);
    }

    /**
     * Bài 6: Lấy danh sách toàn bộ đăng ký học phần
     */
    public List<Enrollment> findAll() {
        return enrollmentRepository.findAllByOrderByEnrollDateDesc();
    }

    public Optional<Enrollment> findById(Long id) {
        return enrollmentRepository.findById(id);
    }

    /**
     * Bài 7: Chức năng hủy đăng ký học phần
     */
    public void cancel(Long id) {
        if (!enrollmentRepository.existsById(id)) {
            throw new IllegalArgumentException("Không tìm thấy lượt đăng ký để hủy với ID: " + id);
        }
        enrollmentRepository.deleteById(id);
    }

    /**
     * Bài 8: Lấy danh sách môn học mà một sinh viên đã đăng ký
     */
    public List<Enrollment> findByStudentId(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    /**
     * Bài 9: Thống kê tổng số lượt đăng ký
     */
    public long count() {
        return enrollmentRepository.count();
    }
}
