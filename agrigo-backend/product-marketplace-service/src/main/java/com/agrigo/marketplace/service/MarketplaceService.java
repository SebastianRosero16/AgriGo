package com.agrigo.marketplace.service;

import com.agrigo.common.exception.ResourceNotFoundException;
import com.agrigo.marketplace.dto.MarketplaceProductDTO;
import com.agrigo.marketplace.dto.OrderDTO;
import com.agrigo.marketplace.entity.MarketplaceProduct;
import com.agrigo.marketplace.entity.Order;
import com.agrigo.marketplace.repository.MarketplaceProductRepository;
import com.agrigo.marketplace.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarketplaceService {
    
    private final MarketplaceProductRepository productRepository;
    private final OrderRepository orderRepository;
    
    public MarketplaceProductDTO createProduct(MarketplaceProductDTO dto) {
        MarketplaceProduct product = new MarketplaceProduct();
        product.setSellerId(dto.getSellerId());
        product.setSellerType(dto.getSellerType());
        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setUnit(dto.getUnit());
        product.setCategory(dto.getCategory());
        product.setImageUrl(dto.getImageUrl());
        
        MarketplaceProduct saved = productRepository.save(product);
        return toProductDTO(saved);
    }
    
    public List<MarketplaceProductDTO> getAllProducts() {
        return productRepository.findByStatus("ACTIVE").stream()
                .map(this::toProductDTO)
                .collect(Collectors.toList());
    }
    
    public List<MarketplaceProductDTO> getProductsByCategory(String category) {
        return productRepository.findByCategory(category).stream()
                .map(this::toProductDTO)
                .collect(Collectors.toList());
    }
    
    public List<MarketplaceProductDTO> searchProducts(String query) {
        return productRepository.findByProductNameContainingIgnoreCase(query).stream()
                .map(this::toProductDTO)
                .collect(Collectors.toList());
    }
    
    public OrderDTO createOrder(OrderDTO dto) {
        MarketplaceProduct product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        
        if (product.getQuantity() < dto.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }
        
        Order order = new Order();
        order.setBuyerId(dto.getBuyerId());
        order.setProductId(dto.getProductId());
        order.setQuantity(dto.getQuantity());
        order.setTotalPrice(dto.getTotalPrice());
        order.setDeliveryAddress(dto.getDeliveryAddress());
        
        Order saved = orderRepository.save(order);
        
        product.setQuantity(product.getQuantity() - dto.getQuantity());
        productRepository.save(product);
        
        return toOrderDTO(saved);
    }
    
    public List<OrderDTO> getBuyerOrders(Long buyerId) {
        return orderRepository.findByBuyerId(buyerId).stream()
                .map(this::toOrderDTO)
                .collect(Collectors.toList());
    }
    
    private MarketplaceProductDTO toProductDTO(MarketplaceProduct product) {
        return new MarketplaceProductDTO(
                product.getId(),
                product.getSellerId(),
                product.getSellerType(),
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getUnit(),
                product.getCategory(),
                product.getImageUrl(),
                product.getStatus()
        );
    }
    
    private OrderDTO toOrderDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getBuyerId(),
                order.getProductId(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getDeliveryAddress()
        );
    }
}
