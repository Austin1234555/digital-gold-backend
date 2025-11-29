package com.austin.simplifymoney.digital_gold_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    // "BUY" or "SELL"
    private String type;

    // For BUY = money user paid
    // For SELL = money user received back
    private double amountPaid;

    // For BUY = +ve grams
    // For SELL = -ve grams (so wallet decreases)
    private double goldPurchasedInGrams;

    private LocalDateTime purchasedAt;
}
