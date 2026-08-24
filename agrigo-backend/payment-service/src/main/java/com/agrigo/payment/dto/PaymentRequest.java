package com.agrigo.payment.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private Long userId;
    private BigDecimal amount;
    private String gateway;
    private String reference;
    private String description;
    private String customerEmail;
    private String phoneNumber;
}
