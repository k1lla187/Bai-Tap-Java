package vn.edu.eaut.lab15.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.eaut.lab15.entity.Course;

import java.util.List;
import java.util.Optional;

/**
 * Bài 3: CourseRepository
 * Cung cấp các thao tác truy vấn dữ liệu cho thực thể Course.
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByCourseCode(String courseCode);

    boolean existsByCourseCode(String courseCode);

    @Query("SELECT c FROM Course c WHERE LOWER(c.courseName) LIKE LOWER(CONCAT('%', :kw, '%')) OR LOWER(c.courseCode) LIKE LOWER(CONCAT('%', :kw, '%'))")
    List<Course> searchByKeyword(@Param("kw") String keyword);
}
