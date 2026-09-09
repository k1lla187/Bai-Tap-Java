package vn.edu.eaut.lab15.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.eaut.lab15.entity.Student;

import java.util.List;
import java.util.Optional;

/**
 * Bài 3: StudentRepository
 * Cung cấp các thao tác truy vấn dữ liệu cho thực thể Student.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByStudentCode(String studentCode);

    boolean existsByStudentCode(String studentCode);

    @Query("SELECT s FROM Student s WHERE LOWER(s.fullName) LIKE LOWER(CONCAT('%', :kw, '%')) OR LOWER(s.studentCode) LIKE LOWER(CONCAT('%', :kw, '%'))")
    List<Student> searchByKeyword(@Param("kw") String keyword);
}
