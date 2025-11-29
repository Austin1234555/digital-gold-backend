package com.austin.simplifymoney.digital_gold_backend.controller;

import com.austin.simplifymoney.digital_gold_backend.model.Price;
import com.austin.simplifymoney.digital_gold_backend.model.Transaction;
import com.austin.simplifymoney.digital_gold_backend.service.PriceService;
import org.springframework.web.bind.annotation.*;

@RestController
public class PriceController {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    // ✅ BUY GOLD
    @PostMapping("/buy-gold")
    public Transaction buyGold(@RequestParam Long userId,
                               @RequestParam double amount) {
        return priceService.purchaseGold(userId, amount);
    }

    // ✅ SELL GOLD
    @PostMapping("/sell-gold")
    public Transaction sellGold(@RequestParam Long userId,
                                @RequestParam double grams) {
        return priceService.sellGold(userId, grams);
    }

    // ✅ WALLET
    @GetMapping("/wallet")
    public Double wallet(@RequestParam Long userId) {
        return priceService.getWalletBalance(userId);
    }

    // ✅ LATEST PRICE
    @GetMapping("/latest-price")
    public Price getLatestPrice() {
        return priceService.getLatestPrice();
    }
}
