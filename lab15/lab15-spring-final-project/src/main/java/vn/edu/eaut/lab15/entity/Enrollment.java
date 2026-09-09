package vn.edu.eaut.lab15.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * Bài 2: Entity Enrollment (Quan hệ N-N có thuộc tính)
 * Lưu thông tin việc một sinh viên đăng ký một học phần vào một ngày cụ thể.
 */
@Entity
@Table(
    name = "enrollments",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_student_course", columnNames = {"student_id", "course_id"})
    }
)
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Ngày đăng ký không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "enroll_date", nullable = false)
    private LocalDate enrollDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    public Enrollment() {
    }

    public Enrollment(LocalDate enrollDate, Student student, Course course) {
        this.enrollDate = enrollDate;
        this.student = student;
        this.course = course;
    }

    public Enrollment(Long id, LocalDate enrollDate, Student student, Course course) {
        this.id = id;
        this.enrollDate = enrollDate;
        this.student = student;
        this.course = course;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(LocalDate enrollDate) {
        this.enrollDate = enrollDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
