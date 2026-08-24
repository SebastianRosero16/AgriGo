package com.agrigo.inventory.service;

import com.agrigo.common.exception.ResourceNotFoundException;
import com.agrigo.inventory.dto.InventoryItemDTO;
import com.agrigo.inventory.entity.InventoryItem;
import com.agrigo.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {
    
    private final InventoryRepository inventoryRepository;
    
    public InventoryItemDTO createItem(InventoryItemDTO dto) {
        InventoryItem item = new InventoryItem();
        item.setProductId(dto.getProductId());
        item.setProductType(dto.getProductType());
        item.setQuantity(dto.getQuantity());
        item.setMinThreshold(dto.getMinThreshold());
        item.setStatus("IN_STOCK");
        
        InventoryItem saved = inventoryRepository.save(item);
        return toDTO(saved);
    }
    
    public InventoryItemDTO getItem(Long id) {
        InventoryItem item = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory item not found"));
        return toDTO(item);
    }
    
    public InventoryItemDTO getItemByProductId(Long productId, String productType) {
        InventoryItem item = inventoryRepository.findByProductIdAndProductType(productId, productType)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory item not found"));
        return toDTO(item);
    }
    
    public List<InventoryItemDTO> getLowStockItems() {
        return inventoryRepository.findByStatus("LOW_STOCK").stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public InventoryItemDTO updateQuantity(Long id, Integer quantity) {
        InventoryItem item = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory item not found"));
        
        item.setQuantity(quantity);
        InventoryItem updated = inventoryRepository.save(item);
        return toDTO(updated);
    }
    
    public InventoryItemDTO reduceQuantity(Long productId, String productType, Integer quantity) {
        InventoryItem item = inventoryRepository.findByProductIdAndProductType(productId, productType)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory item not found"));
        
        if (item.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient inventory");
        }
        
        item.setQuantity(item.getQuantity() - quantity);
        InventoryItem updated = inventoryRepository.save(item);
        return toDTO(updated);
    }
    
    private InventoryItemDTO toDTO(InventoryItem item) {
        return new InventoryItemDTO(
                item.getId(),
                item.getProductId(),
                item.getProductType(),
                item.getQuantity(),
                item.getMinThreshold(),
                item.getStatus()
        );
    }
}
