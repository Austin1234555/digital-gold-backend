package com.austin.simplifymoney.digital_gold_backend.service;

import com.austin.simplifymoney.digital_gold_backend.model.User;
import com.austin.simplifymoney.digital_gold_backend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    // 👇 encoder injected using constructor (recommended)
    public AuthService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User signup(String name, String email, String password) {

        // If email already exists
        if (userRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encoder.encode(password)); // encrypt password
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new IllegalArgumentException("Email not found");
        }

        if (!encoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Incorrect password");
        }

        return user; // successful login
    }
}
