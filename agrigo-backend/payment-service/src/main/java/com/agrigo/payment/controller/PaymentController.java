package com.agrigo.payment.controller;

import com.agrigo.common.dto.ApiResponse;
import com.agrigo.payment.dto.PaymentRequest;
import com.agrigo.payment.dto.PaymentResponse;
import com.agrigo.payment.entity.Payment;
import com.agrigo.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    
    private final PaymentService paymentService;
    
    @PostMapping("/process")
    public ResponseEntity<ApiResponse<PaymentResponse>> processPayment(@RequestBody PaymentRequest request) {
        PaymentResponse response = paymentService.processPayment(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Payment>>> getUserPayments(@PathVariable Long userId) {
        List<Payment> payments = paymentService.getUserPayments(userId);
        return ResponseEntity.ok(ApiResponse.success(payments));
    }
    
    @GetMapping("/reference/{reference}")
    public ResponseEntity<ApiResponse<Payment>> getPaymentByReference(@PathVariable String reference) {
        Payment payment = paymentService.getPaymentByReference(reference);
        return ResponseEntity.ok(ApiResponse.success(payment));
    }
}
