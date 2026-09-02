package vn.edu.eaut.lab12.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * HomeController - Handles the root URL
 * Redirects to the student list page.
 */
@Controller
public class HomeController {
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
