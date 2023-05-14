package ru.kata.spring.boot_security.pp.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.pp.entities.User;

@Controller
public class UserController {

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/user")
    public String showUserInfo(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "userPage";
    }
}
