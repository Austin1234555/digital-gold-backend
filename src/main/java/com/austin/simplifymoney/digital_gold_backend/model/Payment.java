package com.austin.simplifymoney.digital_gold_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;

    private Long userId;
    private double amount;
    private String method;    // UPI / CARD / NETBANKING
    private String status;    // PENDING / SUCCESS / FAILED

    private LocalDateTime createdAt;
}
