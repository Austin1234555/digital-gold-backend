package com.austin.simplifymoney.digital_gold_backend.service;

import com.austin.simplifymoney.digital_gold_backend.model.*;
import com.austin.simplifymoney.digital_gold_backend.repository.PaymentRepository;
import com.austin.simplifymoney.digital_gold_backend.repository.TransactionRepository;
import com.austin.simplifymoney.digital_gold_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentGatewayService {

    private final PaymentRepository paymentRepository;
    private final RazorpayService razorpayService;
    private final PriceService priceService;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public PaymentGatewayService(PaymentRepository paymentRepository,
                                 RazorpayService razorpayService,
                                 PriceService priceService,
                                 UserRepository userRepository,
                                 TransactionRepository transactionRepository) {

        this.paymentRepository = paymentRepository;
        this.razorpayService = razorpayService;
        this.priceService = priceService;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    // STEP 1 — create Razorpay order
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

    // STEP 2 — verify Razorpay and credit wallet
    public BuyResponse confirmRazorpayAndBuy(RazorpayConfirmRequest request) {
        try {
            // 1) verify Razorpay signature
            razorpayService.verifySignature(
                    request.getRazorpayOrderId(),
                    request.getRazorpayPaymentId(),
                    request.getRazorpaySignature()
            );

            // 2) update payment status
            Payment payment = paymentRepository.findByTransactionId(request.getRazorpayOrderId());
            if (payment == null) throw new RuntimeException("Payment record not found");
            payment.setStatus("SUCCESS");
            paymentRepository.save(payment);

            // 3) convert Rupees → grams
            double pricePerGram = priceService.getLatestPrice().getGoldPricePerGram();
            double grams = request.getAmount() / pricePerGram;

            // 4) update user wallet
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            user.setWallet(user.getWallet() + grams);
            userRepository.save(user);

            // 5) save transaction
            Transaction tx = new Transaction();
            tx.setUserId(request.getUserId());
            tx.setType("BUY");
            tx.setAmountPaid(request.getAmount());
            tx.setGoldPurchasedInGrams(grams);
            tx.setPurchasedAt(LocalDateTime.now());
            transactionRepository.save(tx);

            // 6) return response
            BuyResponse res = new BuyResponse();
            res.setPayment(payment);
            res.setGoldInGrams(grams);
            res.setWalletBalance(user.getWallet());
            return res;

        } catch (Exception e) {
            throw new RuntimeException("Razorpay payment confirmation failed", e);
        }
    }
}
