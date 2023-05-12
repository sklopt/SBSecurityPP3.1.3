package ru.kata.spring.boot_security.pp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.pp.entities.User;
import ru.kata.spring.boot_security.pp.services.UserService;

import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String infoAboutUser(Model model, Principal principal) {
        User user = userService.findUserByName(principal.getName());
        model.addAttribute("user", userService.findUserByName(user.getUsername()));
        return "user";
    }

    @GetMapping("/admin")
    public String infoAboutAdmin(Model model, Principal principal) {
        User admin = userService.findUserByName(principal.getName());
        model.addAttribute("admin", userService.findUserByName(admin.getUsername()));
        return "admin";
    }
}
