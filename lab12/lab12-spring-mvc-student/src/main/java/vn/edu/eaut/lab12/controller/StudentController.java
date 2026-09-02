package vn.edu.eaut.lab12.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eaut.lab12.model.Student;
import vn.edu.eaut.lab12.service.StudentService;

import java.util.Optional;

/**
 * StudentController - Handles HTTP requests for student management
 * Implements Spring MVC pattern with GET/POST handling.
 * 
 * URL Mapping:
 * - GET  /students          - List all students
 * - GET  /students/{id}     - View student details
 * - GET  /students/create   - Show create form
 * - GET  /students/edit/{id} - Show edit form
 * - POST /students/save      - Save new or update student
 * - POST /students/delete/{id} - Delete student
 * - GET  /students/search   - Search by name
 */
@Controller
@RequestMapping("/students")
public class StudentController {
    
    private final StudentService studentService;
    
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    
    /**
     * Bai 3 & 9: Hien thi danh sach sinh vien
     * URL: GET /students
     * URL: GET /students?search=keyword
     */
    @GetMapping
    public String list(
            @RequestParam(required = false) String search,
            Model model) {
        
        if (search != null && !search.trim().isEmpty()) {
            // Search mode - Tim kiem theo ten
            model.addAttribute("students", studentService.findByName(search));
            model.addAttribute("searchKeyword", search);
        } else {
            // Normal mode - Hien thi tat ca
            model.addAttribute("students", studentService.findAll());
        }
        
        return "students/list";
    }
    
    /**
     * Bai 6: Xem chi tiet sinh vien theo id
     * URL: GET /students/{id}
     */
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Optional<Student> student = studentService.findById(id);
        
        if (student.isPresent()) {
            model.addAttribute("student", student.get());
            return "students/detail";
        } else {
            model.addAttribute("errorMessage", "Không tìm thấy sinh viên với mã: " + id);
            return "redirect:/students";
        }
    }
    
    /**
     * Bai 4: Hien thi form them sinh vien moi
     * URL: GET /students/create
     */
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("isEdit", false);
        return "students/form";
    }
    
    /**
     * Bai 7: Hien thi form chinh sua sinh vien
     * URL: GET /students/edit/{id}
     */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Optional<Student> student = studentService.findById(id);
        
        if (student.isPresent()) {
            model.addAttribute("student", student.get());
            model.addAttribute("isEdit", true);
            return "students/form";
        } else {
            model.addAttribute("errorMessage", "Không tìm thấy sinh viên với mã: " + id);
            return "redirect:/students";
        }
    }
    
    /**
     * Bai 4 & 7: Xu ly luu sinh vien (tao moi hoac cap nhat)
     * URL: POST /students/save
     * 
     * Bai 5: Validation voi @Valid va BindingResult
     * Bai 10: Validation khong trung ma sinh vien
     */
    @PostMapping("/save")
    public String save(
            @Valid @ModelAttribute Student student,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        // Check for validation errors from annotations
        if (result.hasErrors()) {
            model.addAttribute("isEdit", student.getId() != null);
            return "students/form";
        }
        
        // Bai 10: Kiem tra ma sinh vien khong trung
        Long excludeId = student.getId();
        if (studentService.isStudentCodeExists(student.getStudentCode(), excludeId)) {
            result.rejectValue("studentCode", "duplicate", 
                    "Mã sinh viên đã tồn tại trong hệ thống");
            model.addAttribute("isEdit", excludeId != null);
            return "students/form";
        }
        
        // Save student
        studentService.save(student);
        
        // Add success message
        String message = excludeId == null 
                ? "Thêm sinh viên thành công!" 
                : "Cập nhật sinh viên thành công!";
        redirectAttributes.addFlashAttribute("successMessage", message);
        
        return "redirect:/students";
    }
    
    /**
     * Bai 8: Xoa sinh vien
     * URL: POST /students/delete/{id}
     */
    @PostMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes,
            Model model) {
        
        Optional<Student> student = studentService.findById(id);
        
        if (student.isPresent()) {
            studentService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", 
                    "Xóa sinh viên '" + student.get().getFullName() + "' thành công!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", 
                    "Không tìm thấy sinh viên cần xóa!");
        }
        
        return "redirect:/students";
    }
    
    // ==================== REST API Controllers ====================
    // (Optional) JSON API endpoints for AJAX requests
    
    /**
     * REST API: Get all students as JSON
     * URL: GET /api/students
     */
    @GetMapping("/api/all")
    @ResponseBody
    public ResponseEntity<?> getAllStudents() {
        return ResponseEntity.ok(studentService.findAll());
    }
    
    /**
     * REST API: Get student by ID as JSON
     * URL: GET /api/students/{id}
     */
    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<?> getStudent(@PathVariable Long id) {
        return studentService.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\": \"Không tìm thấy sinh viên\"}"));
    }
}
