package com.agrigo.marketplace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketplaceProductDTO {
    private Long id;
    private Long sellerId;
    private String sellerType;
    private String productName;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String unit;
    private String category;
    private String imageUrl;
    private String status;
}
