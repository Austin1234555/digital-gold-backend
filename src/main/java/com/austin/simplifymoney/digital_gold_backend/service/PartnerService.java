package com.austin.simplifymoney.digital_gold_backend.service;

import com.austin.simplifymoney.digital_gold_backend.model.PartnerAllotmentResponse;
import com.austin.simplifymoney.digital_gold_backend.model.PartnerPriceResponse;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PartnerService {

    private final Random random = new Random();

    public PartnerPriceResponse getCurrentPrice() {
        double price = 5000 + random.nextDouble() * 200; // 5000–5200
        return new PartnerPriceResponse(price);
    }

    public PartnerAllotmentResponse confirmAllotment(double amount) {
        double price = 5000 + random.nextDouble() * 200;
        double grams = amount / price;
        return new PartnerAllotmentResponse(grams, price);
    }
}
