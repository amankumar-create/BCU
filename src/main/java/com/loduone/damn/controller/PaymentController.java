package com.loduone.damn.controller;

import com.loduone.damn.service.impl.PaymentServiceImpl;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private PaymentServiceImpl paymentService;

    @PostMapping("/create-order")
    public ResponseEntity<Map<String, String>> createOrder(@RequestBody Map<String, Object> data) {
        try {
            int amount = (int) data.get("amount");
            Map<String, String> orderDetails = paymentService.createOrder(amount);
            return ResponseEntity.ok(orderDetails);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = Collections.singletonMap("error", "Internal server error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/verify-payment")
    public ResponseEntity<Map<String, Object>> verifyPayment(@RequestBody Map<String, Object> data) {
        try {

            String paymentId = data.get("razorpay_payment_id").toString();
            String orderId = data.get("razorpay_order_id").toString();
            String signature = data.get("razorpay_signature").toString();

            boolean isValid = paymentService.verifyPayment(paymentId, orderId, signature);

            if (isValid) {
                Map<String, Object> response = Collections.singletonMap("status", "Payment verified successfully");
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> error = Collections.singletonMap("error", "Invalid signature");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

}
