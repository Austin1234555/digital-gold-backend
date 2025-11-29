package com.austin.simplifymoney.digital_gold_backend.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PaymentResponse {
    private String transactionId;
    private String status;
    private String method;
    private double amount;
    private LocalDateTime timestamp;
}
