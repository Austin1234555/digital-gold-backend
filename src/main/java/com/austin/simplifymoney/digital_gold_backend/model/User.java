package com.austin.simplifymoney.digital_gold_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "users") // avoid reserved SQL keyword
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)   // Email must be unique and not null
    private String email;

    @Column(nullable = false)                  // Password cannot be null
    private String password;

    @Column(nullable = false)
    private double wallet = 0.0;               // Default 0 gold for every new account

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // Timestamp auto-generated
}
