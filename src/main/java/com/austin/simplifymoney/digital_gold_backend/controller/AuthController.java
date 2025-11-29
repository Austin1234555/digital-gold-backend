package com.austin.simplifymoney.digital_gold_backend.controller;

import com.austin.simplifymoney.digital_gold_backend.model.User;
import com.austin.simplifymoney.digital_gold_backend.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin // allow frontend
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public User signup(@RequestBody User body) {
        return authService.signup(body.getName(), body.getEmail(), body.getPassword());
    }

    @PostMapping("/login")
    public User login(@RequestBody User body) {
        return authService.login(body.getEmail(), body.getPassword());
    }
}
