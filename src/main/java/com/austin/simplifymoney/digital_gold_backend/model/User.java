package com.austin.simplifymoney.digital_gold_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private double wallet = 0.0;    // 👈 Prevent null pointer issues

    private LocalDateTime createdAt = LocalDateTime.now();  // 👈 Auto timestamp
}
