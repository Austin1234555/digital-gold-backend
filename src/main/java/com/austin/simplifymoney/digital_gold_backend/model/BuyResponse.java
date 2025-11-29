package com.austin.simplifymoney.digital_gold_backend.model;

import lombok.Data;

@Data
public class BuyResponse {

    private Payment payment; // ⬅ changed from PaymentResponse to Payment
    private PartnerAllotmentResponse gold;
    private double walletBalance;
}
