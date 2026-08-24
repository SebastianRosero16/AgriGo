package com.agrigo.inventory.repository;

import com.agrigo.inventory.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {
    Optional<InventoryItem> findByProductIdAndProductType(Long productId, String productType);
    List<InventoryItem> findByStatus(String status);
}
