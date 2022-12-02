package com.progect.ui.controllers;

import com.progect.ui.rest.dto.user.UserRequestDTO;
import com.progect.ui.services.MainService;
import com.progect.ui.services.RegistrationService;
import com.progect.ui.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final MainService mainService;
    private final UserService userService;

    private final RegistrationService registrationService;

    public AuthController(MainService mainService, UserService userService, RegistrationService registrationService) {
        this.mainService = mainService;
        this.userService = userService;
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("categories", mainService.getCategoriesSet());
        return "login";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute("categories", mainService.getCategoriesSet());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@RequestParam String username, @RequestParam String email,
                               @RequestParam String password, Model model) {
        if (userService.getUserByLogin(username) == null) {
            registrationService.register(new UserRequestDTO(username, password, email));
            return "redirect:/login";
        } else {
            model.addAttribute("loginError", "Акаунт з таким логіном вже існує");
            return "redirect:/registration";
        }
    }
}
