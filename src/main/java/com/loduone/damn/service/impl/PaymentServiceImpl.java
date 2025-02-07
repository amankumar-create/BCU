package com.loduone.damn.service.impl;

import com.loduone.damn.service.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${razorpay.key_id}")
    private String razorpayKeyId;

    @Value("${razorpay.key_secret}")
    private String razorpayKeySecret;



    public Map<String, String> createOrder(int amount) throws Exception {
        RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
        JSONObject options = new JSONObject();
        options.put("amount", amount * 100); // Amount in paise
        options.put("currency", "INR");
        options.put("receipt", "txn_" + System.currentTimeMillis());

        Order order = razorpayClient.orders.create(options);

        Map<String, String> response = new HashMap<>();
        response.put("orderId", order.get("id"));
        response.put("amount", String.valueOf(amount));
        return response;
    }


    public Boolean verifyPayment(String paymentId, String orderId, String signature) throws Exception {
        JSONObject options = new JSONObject();
        options.put("razorpay_payment_id", paymentId);
        options.put("razorpay_order_id", orderId);
        options.put("razorpay_signature", signature);

        return Utils.verifyPaymentSignature(options, razorpayKeySecret);
    }
}
