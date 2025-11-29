package com.austin.simplifymoney.digital_gold_backend.controller;

import com.austin.simplifymoney.digital_gold_backend.model.User;
import com.austin.simplifymoney.digital_gold_backend.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public User signup(@RequestBody Map<String, String> body) {
        return authService.signup(
                body.get("name"),
                body.get("email"),
                body.get("password")
        );
    }

    @PostMapping("/login")
    public User login(@RequestBody Map<String, String> body) {
        return authService.login(
                body.get("email"),
                body.get("password")
        );
    }
}
