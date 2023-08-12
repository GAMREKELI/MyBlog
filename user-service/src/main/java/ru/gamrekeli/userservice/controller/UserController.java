package ru.gamrekeli.userservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gamrekeli.userservice.model.User;
import ru.gamrekeli.userservice.service.UserService;

@Controller
@AllArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "showUsers/showAll";
    }

    @GetMapping("/{userId}")
    public String showUserById(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("user", userService.findUserById(userId).get());
        return "showUsers/showUser";
    }

    @GetMapping("/add")
    public String addUser(@ModelAttribute("user") User user) {
        return "addUser/addUser";
    }
    @PostMapping("/add")
    public String save(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/api/v1";
    }
}
