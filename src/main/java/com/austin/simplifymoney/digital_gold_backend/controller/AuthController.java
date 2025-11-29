package com.austin.simplifymoney.digital_gold_backend.controller;

import com.austin.simplifymoney.digital_gold_backend.model.User;
import com.austin.simplifymoney.digital_gold_backend.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth") // IMPORTANT
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public User signup(@RequestParam String name,
                       @RequestParam String email,
                       @RequestParam String password) {
        return authService.signup(name, email, password);
    }

    @PostMapping("/login")
    public User login(@RequestParam String email,
                      @RequestParam String password) {
        return authService.login(email, password);
    }
}
