package ru.kata.spring.boot_security.pp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.pp.entities.Role;
import ru.kata.spring.boot_security.pp.entities.User;
import ru.kata.spring.boot_security.pp.repositories.UserRepository;
import ru.kata.spring.boot_security.pp.services.RoleService;
import ru.kata.spring.boot_security.pp.services.UserService;

import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    private final RoleService roleService;
    @Autowired
    public UserController(UserService userService, UserRepository userRepository, RoleService roleService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleService = roleService;
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

    @GetMapping("/users")
    public String AllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping(value = "/admin/save")
    public String addUser(Model model) {
        model.addAttribute("users", new User());
        return "saveUser";
    }

    @PostMapping(value = "/admin/save")
    public String addUser(@ModelAttribute("users") User user, @RequestParam(value = "checkedRoles") String[] selectResult) {
        Set<Role> roles = new HashSet<>();
        for (String s : selectResult) {
            roles.add(roleService.getRole("ROLE_" + s));
            user.setRoles(roles);
        }
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping(value = "admin/change/{id}")
    public String editUser(ModelMap model, @PathVariable("id") long id) {
        model.addAttribute("users", userService.getUserById(id));
        return "changeUser";
    }

    @PatchMapping(value = "admin/change")
    public String edit(@ModelAttribute("users") User user, @RequestParam(value = "checkedRoles") String[] selectResult) {
        Set<Role> roles = new HashSet<>();
        for (String s : selectResult) {
            roles.add(roleService.getRole("ROLE_" + s));
            user.setRoles(roles);
        }
        userService.changeUser(user);
        return "redirect:/users";
    }

    @DeleteMapping("admin/{id}")
    public String deleteUserById(@PathVariable("id") long id) {
        userService.removeUserById(id);
        return "redirect:/users";
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userPrimary = Optional.ofNullable(userRepository.findUserByEmail(username));
        if (!userPrimary.isPresent()) {
            throw new UsernameNotFoundException(username + " not found");
        }
        return userPrimary.get();
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}
