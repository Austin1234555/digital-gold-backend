package com.austin.simplifymoney.digital_gold_backend.model;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long userId;
    private double amount;
    private String method;
}
