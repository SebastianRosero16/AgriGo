package com.agrigo.payment.adapter;

import com.agrigo.payment.dto.PaymentRequest;
import com.agrigo.payment.dto.PaymentResponse;

public interface PaymentGatewayAdapter {
    PaymentResponse processPayment(PaymentRequest request);
    String getGatewayName();
}
