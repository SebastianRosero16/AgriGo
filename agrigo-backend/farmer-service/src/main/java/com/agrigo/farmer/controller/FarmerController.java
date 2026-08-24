package com.agrigo.farmer.controller;

import com.agrigo.farmer.dto.CropDTO;
import com.agrigo.farmer.dto.FarmerDTO;
import com.agrigo.farmer.dto.FarmerProductDTO;
import com.agrigo.farmer.service.FarmerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FarmerController {

    private final FarmerService farmerService;

    // ─── Farmer profile ──────────────────────────────────────────────────────

    @PostMapping("/farmers")
    public ResponseEntity<FarmerDTO> createFarmer(@RequestBody FarmerDTO dto) {
        return ResponseEntity.ok(farmerService.createFarmer(dto));
    }

    @GetMapping("/farmers/{id}")
    public ResponseEntity<FarmerDTO> getFarmer(@PathVariable Long id) {
        return ResponseEntity.ok(farmerService.getFarmerById(id));
    }

    @GetMapping("/farmers/user/{userId}")
    public ResponseEntity<FarmerDTO> getFarmerByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(farmerService.getFarmerByUserId(userId));
    }

    @GetMapping("/farmers")
    public ResponseEntity<List<FarmerDTO>> getAllFarmers() {
        return ResponseEntity.ok(farmerService.getAllFarmers());
    }

    @PutMapping("/farmers/{id}")
    public ResponseEntity<FarmerDTO> updateFarmer(@PathVariable Long id, @RequestBody FarmerDTO dto) {
        return ResponseEntity.ok(farmerService.updateFarmer(id, dto));
    }

    // ─── Crops (what the frontend uses) ─────────────────────────────────────

    @GetMapping("/farmers/crops")
    public ResponseEntity<List<CropDTO>> getCrops(@RequestHeader(value = "Authorization", required = false) String token) {
        // In a real app extract farmerId from JWT; for now return all crops
        return ResponseEntity.ok(farmerService.getAllCrops());
    }

    @GetMapping("/farmers/crops/{id}")
    public ResponseEntity<CropDTO> getCropById(@PathVariable Long id) {
        return ResponseEntity.ok(farmerService.getCropById(id));
    }

    @PostMapping("/farmers/crops")
    public ResponseEntity<CropDTO> createCrop(@RequestBody CropDTO dto,
            @RequestHeader(value = "Authorization", required = false) String token) {
        return ResponseEntity.ok(farmerService.createCrop(dto));
    }

    @PutMapping("/farmers/crops/{id}")
    public ResponseEntity<CropDTO> updateCrop(@PathVariable Long id, @RequestBody CropDTO dto) {
        return ResponseEntity.ok(farmerService.updateCrop(id, dto));
    }

    @DeleteMapping("/farmers/crops/{id}")
    public ResponseEntity<Void> deleteCrop(@PathVariable Long id) {
        farmerService.deleteCrop(id);
        return ResponseEntity.noContent().build();
    }

    // ─── Farmer Products ──────────────────────────────────────────────────────

    @GetMapping("/farmers/products")
    public ResponseEntity<List<FarmerProductDTO>> getMyProducts(
            @RequestHeader(value = "Authorization", required = false) String token) {
        return ResponseEntity.ok(farmerService.getAllProducts());
    }

    @GetMapping("/farmers/products/{id}")
    public ResponseEntity<FarmerProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(farmerService.getProductById(id));
    }

    @PostMapping("/farmers/products")
    public ResponseEntity<FarmerProductDTO> createProduct(@RequestBody FarmerProductDTO dto) {
        return ResponseEntity.ok(farmerService.createProductDirect(dto));
    }

    @PutMapping("/farmers/products/{id}")
    public ResponseEntity<FarmerProductDTO> updateProduct(@PathVariable Long id, @RequestBody FarmerProductDTO dto) {
        return ResponseEntity.ok(farmerService.updateProduct(id, dto));
    }

    @DeleteMapping("/farmers/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        farmerService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
