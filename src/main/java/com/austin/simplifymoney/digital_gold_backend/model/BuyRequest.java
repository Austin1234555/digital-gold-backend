package com.austin.simplifymoney.digital_gold_backend.model;

import lombok.Data;

@Data
public class BuyRequest {
    private Long userId;
    private double amount;
    private String method; // UPI, CARD, NETBANKING
}
