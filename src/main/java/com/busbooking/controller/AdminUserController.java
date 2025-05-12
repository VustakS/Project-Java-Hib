package com.busbooking.controller;

import com.busbooking.entity.User;
import com.busbooking.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    // Перегляд та пошук користувачів
    @GetMapping
    public String listUsers(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "role", required = false) String role,
            Model model) {
        List<User> users = userService.searchUsers(username, email, role);
        model.addAttribute("users", users);
        return "admin/users/list";
    }

    // Видалення користувача
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    // (Опціонально) Деталі користувача
    @GetMapping("/{id}")
    public String userDetails(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id).orElse(null);
        model.addAttribute("user", user);
        return "admin/users/details";
    }
}