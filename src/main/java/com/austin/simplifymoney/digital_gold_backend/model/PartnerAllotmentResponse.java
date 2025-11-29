package com.austin.simplifymoney.digital_gold_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PartnerAllotmentResponse {
    private double goldAllottedInGrams;
    private double pricePerGram;
}
