package com.agrigo.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryItemDTO {
    private Long id;
    private Long productId;
    private String productType;
    private Integer quantity;
    private Integer minThreshold;
    private String status;
}
