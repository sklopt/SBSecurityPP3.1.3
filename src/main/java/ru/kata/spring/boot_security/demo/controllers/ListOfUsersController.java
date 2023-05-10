package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.ListOfUsers;
import ru.kata.spring.boot_security.demo.services.ListOfUsersService;

@Controller
public class ListOfUsersController {

    private ListOfUsersService listOfUsersService;

    public ListOfUsersController(ListOfUsersService listOfUsersService) {
        this.listOfUsersService = listOfUsersService;
    }

    @GetMapping("/users")
    public String allUsers(Model model) {
        model.addAttribute("users", listOfUsersService.getAllUsers());
        return "users";
    }

    @GetMapping(value = "/users/save")
    public String addUser(Model model) {
        model.addAttribute("user", new ListOfUsers());
        return "saveUser";
    }

    @PostMapping(value = "/users/save")
    public String addUser(@ModelAttribute("user") ListOfUsers user) {
        listOfUsersService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping(value = "users/change/{id}")
    public String editUser(ModelMap model, @PathVariable("id") long id) {
        model.addAttribute("user", listOfUsersService.getUserById(id));
        return "changeUser";
    }

    @PatchMapping(value = "users/change")
    public String edit(@ModelAttribute("user") ListOfUsers user) {
        listOfUsersService.changeUser(user);
        return "redirect:/users";
    }

    @DeleteMapping("users/{id}")
    public String deleteUserById(@PathVariable("id") long id) {
        listOfUsersService.removeUserById(id);
        return "redirect:/users";
    }
}
