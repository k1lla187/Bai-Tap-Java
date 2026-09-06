package vn.edu.eaut.lab14.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String login() { return "auth/login"; }

    @GetMapping("/error/403")
    public String forbidden() { return "error/403"; }
}
