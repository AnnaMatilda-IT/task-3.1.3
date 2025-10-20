package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kata.spring.boot_security.demo.dto.UserCreateDto;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String adminPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("userDto", new UserCreateDto());
        return "admin";
    }

    @GetMapping("/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("userDto", new UserCreateDto());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "add-user";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute UserCreateDto userDto,
                          RedirectAttributes redirectAttributes) {
        userService.saveUser(userDto);
        redirectAttributes.addFlashAttribute("successMessage",
                "User added successfully!");
        return "redirect:/admin";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute UserCreateDto userDto,
                             @RequestParam("userId") Long userId,
                             RedirectAttributes redirectAttributes) {
        userService.updateUser(userDto, userId);
        redirectAttributes.addFlashAttribute("successMessage",
                "User updated successfully!");
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("successMessage",
                "User deleted successfully!");
        return "redirect:/admin";
    }
}