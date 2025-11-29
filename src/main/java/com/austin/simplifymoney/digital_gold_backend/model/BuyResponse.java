package com.austin.simplifymoney.digital_gold_backend.model;

import lombok.Data;

@Data
public class BuyResponse {
    private Payment payment;
    private double goldInGrams;     // gold purchased in grams
    private double walletBalance;   // updated wallet after purchase
}
