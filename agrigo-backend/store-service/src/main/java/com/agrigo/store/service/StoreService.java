package com.agrigo.store.service;

import com.agrigo.store.dto.StoreDTO;
import com.agrigo.store.dto.StoreProductDTO;
import com.agrigo.store.entity.Store;
import com.agrigo.store.entity.StoreProduct;
import com.agrigo.store.repository.StoreProductRepository;
import com.agrigo.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreProductRepository productRepository;

    // ─── Store ────────────────────────────────────────────────────────────────

    public StoreDTO createStore(StoreDTO dto) {
        Store store = new Store();
        store.setUserId(dto.getUserId());
        store.setStoreName(dto.getStoreName());
        store.setDescription(dto.getDescription());
        store.setLocation(dto.getLocation());
        store.setPhone(dto.getPhone());
        store.setEmail(dto.getEmail());
        store.setAddress(dto.getAddress());
        return toDTO(storeRepository.save(store));
    }

    public StoreDTO getStoreById(Long id) {
        return toDTO(storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Store not found")));
    }

    public StoreDTO getStoreByUserId(Long userId) {
        return toDTO(storeRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Store not found for user " + userId)));
    }

    public List<StoreDTO> getAllStores() {
        return storeRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    // ─── Products / Inputs ────────────────────────────────────────────────────

    public List<StoreProductDTO> getAllInputs() {
        return productRepository.findAll().stream().map(this::toProductDTO).collect(Collectors.toList());
    }

    public StoreProductDTO getProductById(Long id) {
        return toProductDTO(productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found")));
    }

    public StoreProductDTO createInputDirect(StoreProductDTO dto) {
        StoreProduct p = new StoreProduct();
        p.setStoreId(dto.getStoreId() != null ? dto.getStoreId() : 1L);
        p.setProductName(dto.getProductName() != null ? dto.getProductName() : "");
        p.setDescription(dto.getDescription());
        p.setPrice(dto.getPrice() != null ? dto.getPrice() : BigDecimal.ZERO);
        p.setStock(dto.getStock() != null ? dto.getStock() : 0);
        p.setCategory(dto.getCategory() != null ? dto.getCategory() : "General");
        p.setBrand(dto.getBrand());
        p.setImageUrl(dto.getImageUrl());
        p.setProductType(dto.getProductType() != null ? dto.getProductType() : "Input");
        return toProductDTO(productRepository.save(p));
    }

    public StoreProductDTO createProduct(Long storeId, StoreProductDTO dto) {
        dto.setStoreId(storeId);
        return createInputDirect(dto);
    }

    public StoreProductDTO updateProduct(Long id, StoreProductDTO dto) {
        StoreProduct p = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if (dto.getProductName() != null) p.setProductName(dto.getProductName());
        if (dto.getDescription() != null) p.setDescription(dto.getDescription());
        if (dto.getPrice() != null) p.setPrice(dto.getPrice());
        if (dto.getStock() != null) p.setStock(dto.getStock());
        if (dto.getCategory() != null) p.setCategory(dto.getCategory());
        if (dto.getBrand() != null) p.setBrand(dto.getBrand());
        if (dto.getImageUrl() != null) p.setImageUrl(dto.getImageUrl());
        if (dto.getProductType() != null) p.setProductType(dto.getProductType());
        return toProductDTO(productRepository.save(p));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<StoreProductDTO> getStoreProducts(Long storeId) {
        return productRepository.findByStoreId(storeId).stream()
                .map(this::toProductDTO).collect(Collectors.toList());
    }

    public List<StoreProductDTO> searchProducts(String name) {
        return productRepository.findByProductNameContainingIgnoreCase(name).stream()
                .map(this::toProductDTO).collect(Collectors.toList());
    }

    public Map<String, Object> compareInput(Long inputId) {
        StoreProduct product = productRepository.findById(inputId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        List<StoreProduct> similar = productRepository
                .findByProductNameContainingIgnoreCase(product.getProductName());
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("inputId", inputId);
        result.put("inputName", product.getProductName());
        result.put("prices", similar.stream().map(p -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("storeId", p.getStoreId());
            m.put("price", p.getPrice());
            m.put("stock", p.getStock());
            return m;
        }).collect(Collectors.toList()));
        return result;
    }

    // ─── Mappers ─────────────────────────────────────────────────────────────

    private StoreDTO toDTO(Store s) {
        return new StoreDTO(s.getId(), s.getUserId(), s.getStoreName(), s.getDescription(),
                s.getLocation(), s.getPhone(), s.getEmail(), s.getAddress());
    }

    private StoreProductDTO toProductDTO(StoreProduct p) {
        return new StoreProductDTO(p.getId(), p.getStoreId(), p.getProductName(), p.getDescription(),
                p.getPrice(), p.getStock(), p.getCategory(), p.getBrand(), p.getImageUrl(), p.getProductType());
    }
}
