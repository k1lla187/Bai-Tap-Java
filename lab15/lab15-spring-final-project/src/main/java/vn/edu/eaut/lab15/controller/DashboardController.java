package vn.edu.eaut.lab15.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.edu.eaut.lab15.entity.Enrollment;
import vn.edu.eaut.lab15.service.CourseService;
import vn.edu.eaut.lab15.service.EnrollmentService;
import vn.edu.eaut.lab15.service.StudentService;

import java.util.List;

/**
 * Bài 9: DashboardController
 * Cung cấp số liệu thống kê tổng quan hệ thống:
 * - Tổng số sinh viên
 * - Tổng số môn học
 * - Tổng số lượt đăng ký học phần
 * - Các hoạt động đăng ký gần đây
 */
@Controller
public class DashboardController {

    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    public DashboardController(StudentService studentService,
                               CourseService courseService,
                               EnrollmentService enrollmentService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        long totalStudents = studentService.count();
        long totalCourses = courseService.count();
        long totalEnrollments = enrollmentService.count();

        List<Enrollment> enrollments = enrollmentService.findAll();
        List<Enrollment> recentEnrollments = enrollments.size() > 5 ? enrollments.subList(0, 5) : enrollments;

        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("totalCourses", totalCourses);
        model.addAttribute("totalEnrollments", totalEnrollments);
        model.addAttribute("recentEnrollments", recentEnrollments);

        return "dashboard";
    }
}
