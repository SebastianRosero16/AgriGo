package com.agrigo.store.controller;

import com.agrigo.store.dto.StoreDTO;
import com.agrigo.store.dto.StoreProductDTO;
import com.agrigo.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StoreController {

    private final StoreService storeService;

    // ─── Store CRUD ───────────────────────────────────────────────────────────

    @PostMapping("/stores")
    public ResponseEntity<StoreDTO> createStore(@RequestBody StoreDTO dto) {
        return ResponseEntity.ok(storeService.createStore(dto));
    }

    @GetMapping("/stores/{id}")
    public ResponseEntity<StoreDTO> getStore(@PathVariable Long id) {
        return ResponseEntity.ok(storeService.getStoreById(id));
    }

    @GetMapping("/stores")
    public ResponseEntity<List<StoreDTO>> getAllStores() {
        return ResponseEntity.ok(storeService.getAllStores());
    }

    @GetMapping("/stores/user/{userId}")
    public ResponseEntity<StoreDTO> getStoreByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(storeService.getStoreByUserId(userId));
    }

    // ─── Inputs (what the frontend calls) ────────────────────────────────────

    @GetMapping("/stores/inputs")
    public ResponseEntity<List<StoreProductDTO>> getMyInputs(
            @RequestHeader(value = "Authorization", required = false) String token) {
        return ResponseEntity.ok(storeService.getAllInputs());
    }

    @GetMapping("/stores/inputs/public")
    public ResponseEntity<List<StoreProductDTO>> getPublicInputs() {
        return ResponseEntity.ok(storeService.getAllInputs());
    }

    @GetMapping("/stores/inputs/store/{storeId}")
    public ResponseEntity<List<StoreProductDTO>> getInputsByStore(@PathVariable Long storeId) {
        return ResponseEntity.ok(storeService.getStoreProducts(storeId));
    }

    @GetMapping("/stores/inputs/{id}")
    public ResponseEntity<StoreProductDTO> getInputById(@PathVariable Long id) {
        return ResponseEntity.ok(storeService.getProductById(id));
    }

    @PostMapping("/stores/inputs")
    public ResponseEntity<StoreProductDTO> createInput(@RequestBody StoreProductDTO dto) {
        return ResponseEntity.ok(storeService.createInputDirect(dto));
    }

    @PutMapping("/stores/inputs/{id}")
    public ResponseEntity<StoreProductDTO> updateInput(@PathVariable Long id, @RequestBody StoreProductDTO dto) {
        return ResponseEntity.ok(storeService.updateProduct(id, dto));
    }

    @DeleteMapping("/stores/inputs/{id}")
    public ResponseEntity<Void> deleteInput(@PathVariable Long id) {
        storeService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // ─── Legacy endpoints ─────────────────────────────────────────────────────

    @PostMapping("/stores/{storeId}/products")
    public ResponseEntity<StoreProductDTO> createProduct(@PathVariable Long storeId, @RequestBody StoreProductDTO dto) {
        return ResponseEntity.ok(storeService.createProduct(storeId, dto));
    }

    @GetMapping("/stores/{storeId}/products")
    public ResponseEntity<List<StoreProductDTO>> getStoreProducts(@PathVariable Long storeId) {
        return ResponseEntity.ok(storeService.getStoreProducts(storeId));
    }

    @GetMapping("/stores/products/search")
    public ResponseEntity<List<StoreProductDTO>> searchProducts(@RequestParam String productName) {
        return ResponseEntity.ok(storeService.searchProducts(productName));
    }

    // ─── Price comparator ─────────────────────────────────────────────────────

    @GetMapping("/price-comparator/all")
    public ResponseEntity<List<StoreProductDTO>> getAllForComparison() {
        return ResponseEntity.ok(storeService.getAllInputs());
    }

    @GetMapping("/price-comparator/compare/{inputId}")
    public ResponseEntity<Map<String, Object>> compareInput(@PathVariable Long inputId) {
        return ResponseEntity.ok(storeService.compareInput(inputId));
    }
}
