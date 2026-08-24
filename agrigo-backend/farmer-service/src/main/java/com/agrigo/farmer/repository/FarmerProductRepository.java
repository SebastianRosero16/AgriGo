package com.agrigo.farmer.repository;

import com.agrigo.farmer.entity.FarmerProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmerProductRepository extends JpaRepository<FarmerProduct, Long> {
    List<FarmerProduct> findByFarmerId(Long farmerId);
    List<FarmerProduct> findByStatus(String status);
}
