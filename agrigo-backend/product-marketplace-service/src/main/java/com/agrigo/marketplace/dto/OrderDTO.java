package com.agrigo.marketplace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private Long buyerId;
    private Long productId;
    private Integer quantity;
    private BigDecimal totalPrice;
    private String status;
    private String deliveryAddress;
}
