package vn.edu.eaut.lab15.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eaut.lab15.entity.Course;
import vn.edu.eaut.lab15.service.CourseService;

import java.util.List;
import java.util.Optional;

/**
 * Controller quản lý CRUD khóa học / học phần.
 * Hỗ trợ tìm kiếm, phân quyền (ADMIN được sửa/xóa, USER xem danh sách).
 */
@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Hiển thị danh sách khóa học, có hỗ trợ tìm kiếm
     */
    @GetMapping
    public String listCourses(@RequestParam(value = "keyword", required = false) String keyword,
                              Model model) {
        List<Course> courses = courseService.search(keyword);
        model.addAttribute("courses", courses);
        model.addAttribute("keyword", keyword);
        return "courses/list";
    }

    /**
     * Hiển thị form thêm khóa học mới (ADMIN)
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        if (!model.containsAttribute("course")) {
            model.addAttribute("course", new Course());
        }
        model.addAttribute("pageTitle", "Thêm Mới Khóa Học");
        return "courses/form";
    }

    /**
     * Hiển thị form chỉnh sửa khóa học (ADMIN)
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Course> courseOpt = courseService.findById(id);
        if (courseOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy khóa học với ID: " + id);
            return "redirect:/courses";
        }
        model.addAttribute("course", courseOpt.get());
        model.addAttribute("pageTitle", "Cập Nhật Thông Tin Khóa Học");
        return "courses/form";
    }

    /**
     * Lưu thông tin khóa học (Thêm mới hoặc Cập nhật)
     */
    @PostMapping("/save")
    public String saveCourse(@Valid @ModelAttribute("course") Course course,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        // Kiểm tra trùng mã học phần nếu tạo mới
        if (course.getId() == null && courseService.existsByCourseCode(course.getCourseCode())) {
            result.rejectValue("courseCode", "error.course", "Mã khóa học đã tồn tại trên hệ thống!");
        }

        if (result.hasErrors()) {
            model.addAttribute("pageTitle", course.getId() == null ? "Thêm Mới Khóa Học" : "Cập Nhật Thông Tin Khóa Học");
            return "courses/form";
        }

        courseService.save(course);
        String msg = course.getId() == null ? "Thêm khóa học mới thành công!" : "Cập nhật khóa học thành công!";
        redirectAttributes.addFlashAttribute("successMessage", msg);
        return "redirect:/courses";
    }

    /**
     * Xóa khóa học khỏi hệ thống (ADMIN)
     */
    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            courseService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa khóa học thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa khóa học do đã có sinh viên đăng ký!");
        }
        return "redirect:/courses";
    }
}
