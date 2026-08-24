package com.agrigo.marketplace.repository;

import com.agrigo.marketplace.entity.MarketplaceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketplaceProductRepository extends JpaRepository<MarketplaceProduct, Long> {
    List<MarketplaceProduct> findByCategory(String category);
    List<MarketplaceProduct> findByProductNameContainingIgnoreCase(String productName);
    List<MarketplaceProduct> findByStatus(String status);
}
