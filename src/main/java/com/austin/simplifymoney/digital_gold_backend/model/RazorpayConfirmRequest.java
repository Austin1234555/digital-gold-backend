package com.austin.simplifymoney.digital_gold_backend.model;

import lombok.Data;

@Data
public class RazorpayConfirmRequest {
    private Long userId;
    private double amount;
    private String method;

    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;
}
