// src/main/java/com/austin/simplifymoney/digital_gold_backend/controller/PaymentController.java
package com.austin.simplifymoney.digital_gold_backend.controller;

import com.austin.simplifymoney.digital_gold_backend.model.BuyRequest;
import com.austin.simplifymoney.digital_gold_backend.model.BuyResponse;
import com.austin.simplifymoney.digital_gold_backend.model.RazorpayConfirmRequest;
import com.austin.simplifymoney.digital_gold_backend.model.RazorpayOrderResponse;
import com.austin.simplifymoney.digital_gold_backend.service.PaymentGatewayService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentGatewayService paymentGatewayService;

    public PaymentController(PaymentGatewayService paymentGatewayService) {
        this.paymentGatewayService = paymentGatewayService;
    }

    // STEP 1 — Frontend requests orderId + keyId
    @PostMapping("/razorpay-order")
    public RazorpayOrderResponse createOrder(@RequestBody BuyRequest request) {
        return paymentGatewayService.createRazorpayOrder(request);
    }

    // STEP 2 — Razorpay payment success → verify + add gold
    @PostMapping("/razorpay-confirm")
    public BuyResponse confirmPayment(@RequestBody RazorpayConfirmRequest request) {
        return paymentGatewayService.confirmRazorpayAndBuy(request);
    }
}
