package com.agrigo.store.repository;

import com.agrigo.store.entity.StoreProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreProductRepository extends JpaRepository<StoreProduct, Long> {
    List<StoreProduct> findByStoreId(Long storeId);
    List<StoreProduct> findByProductNameContainingIgnoreCase(String productName);
}
