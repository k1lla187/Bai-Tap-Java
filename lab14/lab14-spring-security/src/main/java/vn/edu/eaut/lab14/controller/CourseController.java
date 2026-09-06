package vn.edu.eaut.lab14.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.eaut.lab14.entity.Course;
import vn.edu.eaut.lab14.repository.CourseRepository;

@Controller
@RequestMapping("/courses")
public class CourseController {
    private final CourseRepository repository;
    public CourseController(CourseRepository repository) { this.repository = repository; }
    @GetMapping public String list(Model model) { model.addAttribute("courses", repository.findAll()); return "courses/list"; }
    @GetMapping("/create") public String create(Model model) { model.addAttribute("course", new Course()); return "courses/form"; }
    @PostMapping("/save") public String save(@ModelAttribute Course course) { repository.save(course); return "redirect:/courses"; }
    @PostMapping("/delete/{id}") public String delete(@PathVariable Long id) { repository.deleteById(id); return "redirect:/courses"; }
}
