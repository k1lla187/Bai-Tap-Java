package vn.edu.eaut.lab11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Hệ thống quản lý sinh viên");
        model.addAttribute("message", "Chào mừng đến với Spring Boot");
        model.addAttribute("currentYear", java.time.Year.now().getValue());
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("course", "Công nghệ Java");
        model.addAttribute("chapter", "Chương 4 - Spring Framework");
        model.addAttribute("description", "Lab 11 giới thiệu Spring Boot, Spring MVC cơ bản và Thymeleaf");
        model.addAttribute("topics", java.util.List.of(
                "Spring Boot Starter Web",
                "Thymeleaf Template Engine",
                "Spring MVC Controller",
                "Spring Data JPA",
                "Spring Security"
        ));
        return "about";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("department", "Khoa Công nghệ thông tin");
        model.addAttribute("school", "Trường Đại học Công nghệ Đông Á");
        model.addAttribute("address", "Số 5, Phố Trịnh Văn Bô, Nam Từ Liêm, Hà Nội");
        model.addAttribute("email", "cntt@eaut.edu.vn");
        model.addAttribute("phone", "(024) 3557 7799");
        return "contact";
    }
}