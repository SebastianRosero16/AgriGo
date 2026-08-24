package com.agrigo.payment.service;

import com.agrigo.payment.dto.PaymentRequest;
import com.agrigo.payment.dto.PaymentResponse;
import com.agrigo.payment.entity.Payment;
import com.agrigo.payment.factory.PaymentGatewayFactory;
import com.agrigo.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    
    private final PaymentGatewayFactory gatewayFactory;
    private final PaymentRepository paymentRepository;
    
    public PaymentResponse processPayment(PaymentRequest request) {
        PaymentResponse response = gatewayFactory
                .getAdapter(request.getGateway())
                .processPayment(request);
        
        Payment payment = new Payment();
        payment.setUserId(request.getUserId());
        payment.setAmount(request.getAmount());
        payment.setGateway(request.getGateway());
        payment.setReference(request.getReference());
        payment.setStatus(response.getStatus());
        payment.setTransactionId(response.getTransactionId());
        payment.setDescription(request.getDescription());
        
        paymentRepository.save(payment);
        
        return response;
    }
    
    public List<Payment> getUserPayments(Long userId) {
        return paymentRepository.findByUserId(userId);
    }
    
    public Payment getPaymentByReference(String reference) {
        return paymentRepository.findByReference(reference)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }
}
