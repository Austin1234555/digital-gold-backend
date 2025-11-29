package com.austin.simplifymoney.digital_gold_backend.service;

import com.austin.simplifymoney.digital_gold_backend.model.RazorpayOrderResponse;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class RazorpayService {

    private final RazorpayClient client;
    private final String keyId;
    private final String keySecret;

    public RazorpayService() throws RazorpayException {
        // ⚠️ In real app keep these in application.properties
        this.keyId = "rzp_test_RlRNVoG5KsYkc4";
        this.keySecret = "6gRRbg9uWCdunIVPABdBV9AR";
        this.client = new RazorpayClient(keyId, keySecret);
    }

    // STEP 1: create Razorpay Order
    public RazorpayOrderResponse createOrder(double amountInRupees, String receipt) throws RazorpayException {

        int amountInPaise = (int) Math.round(amountInRupees * 100);  // Razorpay works in paise

        JSONObject request = new JSONObject();
        request.put("amount", amountInPaise);
        request.put("currency", "INR");
        request.put("receipt", receipt);
        request.put("payment_capture", 1);

        // ✅ FIXED: use client.orders (small 'o'), not client.Orders
        Order order = client.orders.create(request);

        RazorpayOrderResponse response = new RazorpayOrderResponse();
        response.setKeyId(keyId);
        response.setOrderId(order.get("id"));
        response.setAmountInPaise(amountInPaise);
        return response;
    }

    // STEP 2: verify signature after payment success
    public void verifySignature(String orderId, String paymentId, String signature) throws RazorpayException {
        JSONObject options = new JSONObject();
        options.put("razorpay_order_id", orderId);
        options.put("razorpay_payment_id", paymentId);
        options.put("razorpay_signature", signature);

        // throws RazorpayException if signature is invalid
        Utils.verifyPaymentSignature(options, keySecret);
    }
}
