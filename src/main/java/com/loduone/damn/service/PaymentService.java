package com.loduone.damn.service;

import java.util.Map;

public interface PaymentService {
    public Map<String, String> createOrder(int amount) throws Exception;
    public Boolean verifyPayment(String paymentId, String orderId, String signature) throws Exception;

}
