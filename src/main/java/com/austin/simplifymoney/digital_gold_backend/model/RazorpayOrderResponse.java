package com.austin.simplifymoney.digital_gold_backend.model;

import lombok.Data;

@Data
public class RazorpayOrderResponse {
    private String keyId;
    private String orderId;
    private int amountInPaise;
}
