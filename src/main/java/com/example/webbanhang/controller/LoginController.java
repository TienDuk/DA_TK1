package com.example.webbanhang.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class LoginController {
    @GetMapping("/showLoginPage")
    public String showLoginPage() {

        return "login";
    }
    @GetMapping("/p403")
    public String show403() {

        return "error/p403";
    }
    @GetMapping("/test")
    public String hello(Principal principal, Model model) {
        if (principal != null) {
            String username = principal.getName();
            model.addAttribute("username", username);
        }
        return "hello";
    }
}
