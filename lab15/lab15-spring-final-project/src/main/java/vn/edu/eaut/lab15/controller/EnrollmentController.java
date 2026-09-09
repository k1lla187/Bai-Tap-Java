package vn.edu.eaut.lab15.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eaut.lab15.entity.Course;
import vn.edu.eaut.lab15.entity.Enrollment;
import vn.edu.eaut.lab15.entity.Student;
import vn.edu.eaut.lab15.service.CourseService;
import vn.edu.eaut.lab15.service.EnrollmentService;
import vn.edu.eaut.lab15.service.StudentService;

import java.util.List;
import java.util.Optional;

/**
 * Controller phụ trách toàn bộ luồng nghiệp vụ Đăng ký học phần:
 * - Bài 5: Hiển thị form tạo và xử lý đăng ký
 * - Bài 6: Xem danh sách toàn bộ lượt đăng ký
 * - Bài 7: Hủy đăng ký học phần
 * - Bài 8: Xem danh sách môn học đã đăng ký theo từng sinh viên
 */
@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentController(EnrollmentService enrollmentService,
                                StudentService studentService,
                                CourseService courseService) {
        this.enrollmentService = enrollmentService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    /**
     * Bài 6: Hiển thị danh sách tất cả các lượt đăng ký học phần
     */
    @GetMapping({"", "/", "/list"})
    public String listEnrollments(Model model) {
        List<Enrollment> enrollments = enrollmentService.findAll();
        model.addAttribute("enrollments", enrollments);
        model.addAttribute("students", studentService.findAll());
        return "enrollments/list";
    }

    /**
     * Bài 5: Hiển thị form chọn sinh viên và môn học để đăng ký
     */
    @GetMapping({"/create", "/new"})
    public String create(Model model) {
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("courses", courseService.findAll());
        return "enrollments/form";
    }

    /**
     * Bài 5: Xử lý lưu đăng ký học phần (kiểm tra trùng lặp và thông báo)
     */
    @PostMapping({"/save", ""})
    public String save(@RequestParam Long studentId,
                       @RequestParam Long courseId,
                       RedirectAttributes redirectAttributes) {
        try {
            enrollmentService.enroll(studentId, courseId);
            redirectAttributes.addFlashAttribute("successMessage", "Đăng ký học phần thành công!");
            return "redirect:/enrollments";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/enrollments/create";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra trong quá trình đăng ký học phần!");
            return "redirect:/enrollments/create";
        }
    }

    /**
     * Bài 7: Chức năng hủy đăng ký học phần
     */
    @GetMapping({"/cancel/{id}", "/delete/{id}"})
    public String cancel(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            enrollmentService.cancel(id);
            redirectAttributes.addFlashAttribute("successMessage", "Hủy đăng ký học phần thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể hủy đăng ký: " + e.getMessage());
        }
        return "redirect:/enrollments";
    }

    /**
     * Bài 8: Xem danh sách môn học mà một sinh viên đã đăng ký
     */
    @GetMapping("/student/{studentId}")
    public String viewByStudent(@PathVariable("studentId") Long studentId, Model model, RedirectAttributes redirectAttributes) {
        Optional<Student> studentOpt = studentService.findById(studentId);
        if (studentOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sinh viên với ID: " + studentId);
            return "redirect:/enrollments";
        }

        Student student = studentOpt.get();
        List<Enrollment> studentEnrollments = enrollmentService.findByStudentId(studentId);

        // Tính tổng số tín chỉ mà sinh viên này đã đăng ký
        int totalCredits = studentEnrollments.stream()
                .map(Enrollment::getCourse)
                .mapToInt(Course::getCredits)
                .sum();

        model.addAttribute("student", student);
        model.addAttribute("enrollments", studentEnrollments);
        model.addAttribute("totalCredits", totalCredits);
        model.addAttribute("allStudents", studentService.findAll());

        return "enrollments/student-history";
    }
}
