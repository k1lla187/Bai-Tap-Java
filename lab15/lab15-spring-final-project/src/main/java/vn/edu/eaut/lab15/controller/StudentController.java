package vn.edu.eaut.lab15.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eaut.lab15.entity.Student;
import vn.edu.eaut.lab15.service.StudentService;

import java.util.List;
import java.util.Optional;

/**
 * Controller quản lý CRUD sinh viên.
 * Hỗ trợ tìm kiếm, phân quyền (ADMIN được sửa/xóa, USER xem danh sách).
 */
@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Hiển thị danh sách sinh viên, có hỗ trợ tìm kiếm theo từ khóa
     */
    @GetMapping
    public String listStudents(@RequestParam(value = "keyword", required = false) String keyword,
                               Model model) {
        List<Student> students = studentService.search(keyword);
        model.addAttribute("students", students);
        model.addAttribute("keyword", keyword);
        return "students/list";
    }

    /**
     * Hiển thị form thêm sinh viên mới (ADMIN)
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        if (!model.containsAttribute("student")) {
            model.addAttribute("student", new Student());
        }
        model.addAttribute("pageTitle", "Thêm Mới Sinh Viên");
        return "students/form";
    }

    /**
     * Hiển thị form chỉnh sửa thông tin sinh viên (ADMIN)
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Student> studentOpt = studentService.findById(id);
        if (studentOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sinh viên với ID: " + id);
            return "redirect:/students";
        }
        model.addAttribute("student", studentOpt.get());
        model.addAttribute("pageTitle", "Cập Nhật Thông Tin Sinh Viên");
        return "students/form";
    }

    /**
     * Lưu thông tin sinh viên (Thêm mới hoặc Cập nhật)
     */
    @PostMapping("/save")
    public String saveStudent(@Valid @ModelAttribute("student") Student student,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        // Kiểm tra trùng mã sinh viên nếu tạo mới
        if (student.getId() == null && studentService.existsByStudentCode(student.getStudentCode())) {
            result.rejectValue("studentCode", "error.student", "Mã sinh viên đã tồn tại trên hệ thống!");
        }

        if (result.hasErrors()) {
            model.addAttribute("pageTitle", student.getId() == null ? "Thêm Mới Sinh Viên" : "Cập Nhật Thông Tin Sinh Viên");
            return "students/form";
        }

        studentService.save(student);
        String msg = student.getId() == null ? "Thêm sinh viên mới thành công!" : "Cập nhật sinh viên thành công!";
        redirectAttributes.addFlashAttribute("successMessage", msg);
        return "redirect:/students";
    }

    /**
     * Xóa sinh viên khỏi hệ thống (ADMIN)
     */
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa sinh viên thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa sinh viên do có dữ liệu liên quan!");
        }
        return "redirect:/students";
    }
}
