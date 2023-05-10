package ru.kata.spring.boot_security.pp.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.pp.entities.ListOfUsers;
import ru.kata.spring.boot_security.pp.services.ListOfUsersService;

@Controller
public class ListOfUsersController {

    private final ListOfUsersService listOfUsersService;

    public ListOfUsersController(ListOfUsersService listOfUsersService) {
        this.listOfUsersService = listOfUsersService;
    }

    @GetMapping("/users")
    public String allUsers(Model model) {
        model.addAttribute("listofusers", listOfUsersService.getAllUsers());
        return "users";
    }

    @GetMapping(value = "/users/save")
    public String addUser(Model model) {
        model.addAttribute("listofusers", new ListOfUsers());
        return "saveUser";
    }

    @PostMapping(value = "/users/save")
    public String addUser(@ModelAttribute("listofusers") ListOfUsers listOfUsers) {
        listOfUsersService.saveUser(listOfUsers);
        return "redirect:/users";
    }

    @GetMapping(value = "users/change/{id}")
    public String editUser(ModelMap model, @PathVariable("id") long id) {
        model.addAttribute("listofusers", listOfUsersService.getUserById(id));
        return "changeUser";
    }

    @PatchMapping(value = "users/change")
    public String edit(@ModelAttribute("listofusers") ListOfUsers listOfUsers) {
        listOfUsersService.changeUser(listOfUsers);
        return "redirect:/users";
    }

    @DeleteMapping("users/{id}")
    public String deleteUserById(@PathVariable("id") long id) {
        listOfUsersService.removeUserById(id);
        return "redirect:/users";
    }
}
