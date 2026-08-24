package com.agrigo.farmer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmerProductDTO {
    private Long id;
    private Long farmerId;
    private String productName;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String unit;
    private String category;
    private String imageUrl;
    private String status;
}
