package com.agrigo.marketplace.controller;

import com.agrigo.common.dto.ApiResponse;
import com.agrigo.marketplace.dto.MarketplaceProductDTO;
import com.agrigo.marketplace.dto.OrderDTO;
import com.agrigo.marketplace.service.MarketplaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marketplace")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MarketplaceController {
    
    private final MarketplaceService marketplaceService;
    
    @PostMapping("/products")
    public ResponseEntity<ApiResponse<MarketplaceProductDTO>> createProduct(@RequestBody MarketplaceProductDTO productDTO) {
        MarketplaceProductDTO created = marketplaceService.createProduct(productDTO);
        return ResponseEntity.ok(ApiResponse.success(created));
    }
    
    @GetMapping("/products")
    public ResponseEntity<ApiResponse<List<MarketplaceProductDTO>>> getAllProducts() {
        List<MarketplaceProductDTO> products = marketplaceService.getAllProducts();
        return ResponseEntity.ok(ApiResponse.success(products));
    }
    
    @GetMapping("/products/category/{category}")
    public ResponseEntity<ApiResponse<List<MarketplaceProductDTO>>> getProductsByCategory(@PathVariable String category) {
        List<MarketplaceProductDTO> products = marketplaceService.getProductsByCategory(category);
        return ResponseEntity.ok(ApiResponse.success(products));
    }
    
    @GetMapping("/products/search")
    public ResponseEntity<ApiResponse<List<MarketplaceProductDTO>>> searchProducts(@RequestParam String query) {
        List<MarketplaceProductDTO> products = marketplaceService.searchProducts(query);
        return ResponseEntity.ok(ApiResponse.success(products));
    }
    
    @PostMapping("/orders")
    public ResponseEntity<ApiResponse<OrderDTO>> createOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO created = marketplaceService.createOrder(orderDTO);
        return ResponseEntity.ok(ApiResponse.success(created));
    }
    
    @GetMapping("/orders/buyer/{buyerId}")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getBuyerOrders(@PathVariable Long buyerId) {
        List<OrderDTO> orders = marketplaceService.getBuyerOrders(buyerId);
        return ResponseEntity.ok(ApiResponse.success(orders));
    }
}
