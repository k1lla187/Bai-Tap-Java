package vn.edu.eaut.lab15.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.eaut.lab15.entity.Enrollment;

import java.util.List;
import java.util.Optional;

/**
 * Bài 3: EnrollmentRepository
 * Quản lý các thao tác truy vấn dữ liệu cho bảng enrollments.
 */
@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    /**
     * Tìm danh sách các lượt đăng ký theo mã ID của sinh viên (Bài 8)
     */
    List<Enrollment> findByStudentId(Long studentId);

    /**
     * Kiểm tra sinh viên đã đăng ký môn học này hay chưa (Bài 4 - chống trùng)
     */
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    /**
     * Tìm lượt đăng ký theo sinh viên và môn học
     */
    Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);

    /**
     * Lấy danh sách đăng ký sắp xếp theo ngày đăng ký mới nhất (Bài 6 & 9)
     */
    List<Enrollment> findAllByOrderByEnrollDateDesc();
}
