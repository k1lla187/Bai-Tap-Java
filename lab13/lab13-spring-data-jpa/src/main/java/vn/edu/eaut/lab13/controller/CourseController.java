package vn.edu.eaut.lab13.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eaut.lab13.entity.Course;
import vn.edu.eaut.lab13.service.CourseService;

@Controller
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String keyword, Model model) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            model.addAttribute("courses", courseService.searchByName(keyword));
            model.addAttribute("keyword", keyword);
        } else {
            model.addAttribute("courses", courseService.findAll());
        }
        return "courses/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("isEdit", false);
        return "courses/form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Course course = courseService.findById(id);
        model.addAttribute("course", course);
        model.addAttribute("isEdit", true);
        return "courses/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Course course, RedirectAttributes redirectAttributes) {
        try {
            courseService.save(course);
            redirectAttributes.addFlashAttribute("message", "Lưu môn học thành công!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Lỗi: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/courses";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            courseService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Xóa môn học thành công!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Lỗi: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/courses";
    }
}
