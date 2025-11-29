// src/main/java/com/austin/simplifymoney/digital_gold_backend/service/PaymentGatewayService.java
package com.austin.simplifymoney.digital_gold_backend.service;

import com.austin.simplifymoney.digital_gold_backend.model.*;
import com.austin.simplifymoney.digital_gold_backend.repository.PaymentRepository;
import com.austin.simplifymoney.digital_gold_backend.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentGatewayService {

    private final PaymentRepository paymentRepository;
    private final RazorpayService razorpayService;
    private final PartnerService partnerService;
    private final TransactionRepository transactionRepository;

    public PaymentGatewayService(PaymentRepository paymentRepository,
                                 RazorpayService razorpayService,
                                 PartnerService partnerService,
                                 TransactionRepository transactionRepository) {
        this.paymentRepository = paymentRepository;
        this.razorpayService = razorpayService;
        this.partnerService = partnerService;
        this.transactionRepository = transactionRepository;
    }

    // STEP 1 — create Razorpay order and save local Payment(status=CREATED)
    public RazorpayOrderResponse createRazorpayOrder(BuyRequest request) {
        try {
            String receipt = "rcpt_" + request.getUserId() + "_" + System.currentTimeMillis();

            RazorpayOrderResponse order = razorpayService.createOrder(request.getAmount(), receipt);

            Payment payment = new Payment();
            payment.setTransactionId(order.getOrderId());
            payment.setUserId(request.getUserId());
            payment.setAmount(request.getAmount());
            payment.setMethod(request.getMethod());
            payment.setStatus("CREATED");
            payment.setCreatedAt(LocalDateTime.now());
            paymentRepository.save(payment);

            return order;

        } catch (Exception e) {
            throw new RuntimeException("Failed to create Razorpay order", e);
        }
    }

    // STEP 2 — verify Razorpay → add gold to wallet → return BuyResponse
    public BuyResponse confirmRazorpayAndBuy(RazorpayConfirmRequest request) {
        try {
            // 1) verify Razorpay signature
            razorpayService.verifySignature(
                    request.getRazorpayOrderId(),
                    request.getRazorpayPaymentId(),
                    request.getRazorpaySignature()
            );

            // 2) update local payment
            Payment payment = paymentRepository.findByTransactionId(request.getRazorpayOrderId());
            if (payment == null) throw new RuntimeException("Payment record not found");

            payment.setStatus("SUCCESS");
            paymentRepository.save(payment);

            // 3) get gold from partner
            PartnerAllotmentResponse allotment =
                    partnerService.confirmAllotment(request.getAmount());

            // 4) save transaction (adds gold to wallet)
            Transaction tx = new Transaction();
            tx.setUserId(request.getUserId());
            tx.setType("BUY");
            tx.setAmountPaid(request.getAmount());
            tx.setGoldPurchasedInGrams(allotment.getGoldAllottedInGrams());
            tx.setPurchasedAt(LocalDateTime.now());
            transactionRepository.save(tx);

            // 5) build response
            BuyResponse response = new BuyResponse();
            response.setPayment(payment);
            response.setGold(allotment);
            response.setWalletBalance(
                    transactionRepository.getTotalGoldByUser(request.getUserId())
            );

            return response;

        } catch (Exception e) {
            throw new RuntimeException("Razorpay payment confirmation failed", e);
        }
    }
}
