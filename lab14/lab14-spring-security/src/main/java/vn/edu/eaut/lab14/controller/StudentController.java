package vn.edu.eaut.lab14.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eaut.lab14.entity.Student;
import vn.edu.eaut.lab14.repository.StudentRepository;

@Controller
@RequestMapping("/students")
public class StudentController {
    private final StudentRepository repository;
    public StudentController(StudentRepository repository) { this.repository = repository; }

    @GetMapping
    public String list(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("students", keyword == null || keyword.isBlank()
                ? repository.findAll() : repository.findByFullNameContainingIgnoreCase(keyword));
        model.addAttribute("keyword", keyword);
        return "students/list";
    }

    @GetMapping("/create")
    public String create(Model model) { model.addAttribute("student", new Student()); return "students/form"; }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("student", repository.findById(id).orElseThrow()); return "students/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Student student, RedirectAttributes redirect) {
        repository.save(student); redirect.addFlashAttribute("message", "Lưu sinh viên thành công."); return "redirect:/students";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        repository.deleteById(id); redirect.addFlashAttribute("message", "Xóa sinh viên thành công."); return "redirect:/students";
    }
}
