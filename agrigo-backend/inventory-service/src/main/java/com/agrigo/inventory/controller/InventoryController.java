package com.agrigo.inventory.controller;

import com.agrigo.common.dto.ApiResponse;
import com.agrigo.inventory.dto.InventoryItemDTO;
import com.agrigo.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    
    private final InventoryService inventoryService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<InventoryItemDTO>> createItem(@RequestBody InventoryItemDTO itemDTO) {
        InventoryItemDTO created = inventoryService.createItem(itemDTO);
        return ResponseEntity.ok(ApiResponse.success(created));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InventoryItemDTO>> getItem(@PathVariable Long id) {
        InventoryItemDTO item = inventoryService.getItem(id);
        return ResponseEntity.ok(ApiResponse.success(item));
    }
    
    @GetMapping("/product/{productId}/{productType}")
    public ResponseEntity<ApiResponse<InventoryItemDTO>> getItemByProduct(
            @PathVariable Long productId,
            @PathVariable String productType) {
        InventoryItemDTO item = inventoryService.getItemByProductId(productId, productType);
        return ResponseEntity.ok(ApiResponse.success(item));
    }
    
    @GetMapping("/low-stock")
    public ResponseEntity<ApiResponse<List<InventoryItemDTO>>> getLowStockItems() {
        List<InventoryItemDTO> items = inventoryService.getLowStockItems();
        return ResponseEntity.ok(ApiResponse.success(items));
    }
    
    @PutMapping("/{id}/quantity")
    public ResponseEntity<ApiResponse<InventoryItemDTO>> updateQuantity(
            @PathVariable Long id,
            @RequestParam Integer quantity) {
        InventoryItemDTO updated = inventoryService.updateQuantity(id, quantity);
        return ResponseEntity.ok(ApiResponse.success(updated));
    }
    
    @PostMapping("/reduce")
    public ResponseEntity<ApiResponse<InventoryItemDTO>> reduceQuantity(
            @RequestParam Long productId,
            @RequestParam String productType,
            @RequestParam Integer quantity) {
        InventoryItemDTO updated = inventoryService.reduceQuantity(productId, productType, quantity);
        return ResponseEntity.ok(ApiResponse.success(updated));
    }
}
