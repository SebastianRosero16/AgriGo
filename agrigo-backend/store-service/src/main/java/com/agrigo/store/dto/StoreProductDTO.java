package com.agrigo.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreProductDTO {
    private Long id;
    private Long storeId;
    private String productName;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String category;
    private String brand;
    private String imageUrl;
    private String productType;
}
