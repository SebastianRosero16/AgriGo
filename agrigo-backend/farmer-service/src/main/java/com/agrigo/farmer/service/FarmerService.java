package com.agrigo.farmer.service;

import com.agrigo.farmer.dto.CropDTO;
import com.agrigo.farmer.dto.FarmerDTO;
import com.agrigo.farmer.dto.FarmerProductDTO;
import com.agrigo.farmer.entity.Crop;
import com.agrigo.farmer.entity.Farmer;
import com.agrigo.farmer.entity.FarmerProduct;
import com.agrigo.farmer.repository.CropRepository;
import com.agrigo.farmer.repository.FarmerProductRepository;
import com.agrigo.farmer.repository.FarmerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FarmerService {

    private final FarmerRepository farmerRepository;
    private final FarmerProductRepository productRepository;
    private final CropRepository cropRepository;

    // ─── Farmer ─────────────────────────────────────────────────────────────

    public FarmerDTO createFarmer(FarmerDTO dto) {
        Farmer farmer = new Farmer();
        farmer.setUserId(dto.getUserId());
        farmer.setName(dto.getName());
        farmer.setLocation(dto.getLocation());
        farmer.setFarmSize(dto.getFarmSize());
        farmer.setCropTypes(dto.getCropTypes());
        farmer.setSoilType(dto.getSoilType());
        farmer.setClimateZone(dto.getClimateZone());
        return toDTO(farmerRepository.save(farmer));
    }

    public FarmerDTO getFarmerById(Long id) {
        return toDTO(farmerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Farmer not found")));
    }

    public FarmerDTO getFarmerByUserId(Long userId) {
        return toDTO(farmerRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Farmer not found for user " + userId)));
    }

    public List<FarmerDTO> getAllFarmers() {
        return farmerRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public FarmerDTO updateFarmer(Long id, FarmerDTO dto) {
        Farmer farmer = farmerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Farmer not found"));
        farmer.setName(dto.getName());
        farmer.setLocation(dto.getLocation());
        farmer.setFarmSize(dto.getFarmSize());
        farmer.setCropTypes(dto.getCropTypes());
        farmer.setSoilType(dto.getSoilType());
        farmer.setClimateZone(dto.getClimateZone());
        return toDTO(farmerRepository.save(farmer));
    }

    // ─── Crops ───────────────────────────────────────────────────────────────

    public List<CropDTO> getAllCrops() {
        return cropRepository.findAll().stream().map(this::toCropDTO).collect(Collectors.toList());
    }

    public CropDTO getCropById(Long id) {
        return toCropDTO(cropRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crop not found")));
    }

    public CropDTO createCrop(CropDTO dto) {
        Crop crop = new Crop();
        crop.setFarmerId(dto.getFarmerId() != null ? dto.getFarmerId() : 1L);
        crop.setCropName(dto.getCropName());
        crop.setCropType(dto.getCropType());
        crop.setArea(dto.getArea());
        crop.setLocation(dto.getLocation());
        crop.setStage(dto.getStage());
        crop.setSoilType(dto.getSoilType());
        crop.setClimate(dto.getClimate());
        crop.setNotes(dto.getNotes());
        if (dto.getPlantingDate() != null) {
            try {
                crop.setPlantingDate(LocalDate.parse(dto.getPlantingDate().substring(0, 10)));
            } catch (Exception e) {
                crop.setPlantingDate(LocalDate.now());
            }
        }
        return toCropDTO(cropRepository.save(crop));
    }

    public CropDTO updateCrop(Long id, CropDTO dto) {
        Crop crop = cropRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crop not found"));
        if (dto.getCropName() != null) crop.setCropName(dto.getCropName());
        if (dto.getCropType() != null) crop.setCropType(dto.getCropType());
        if (dto.getArea() != null) crop.setArea(dto.getArea());
        if (dto.getLocation() != null) crop.setLocation(dto.getLocation());
        if (dto.getStage() != null) crop.setStage(dto.getStage());
        if (dto.getSoilType() != null) crop.setSoilType(dto.getSoilType());
        if (dto.getClimate() != null) crop.setClimate(dto.getClimate());
        if (dto.getNotes() != null) crop.setNotes(dto.getNotes());
        if (dto.getPlantingDate() != null) {
            try {
                crop.setPlantingDate(LocalDate.parse(dto.getPlantingDate().substring(0, 10)));
            } catch (Exception ignored) {}
        }
        return toCropDTO(cropRepository.save(crop));
    }

    public void deleteCrop(Long id) {
        cropRepository.deleteById(id);
    }

    // ─── Products ────────────────────────────────────────────────────────────

    public List<FarmerProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(this::toProductDTO).collect(Collectors.toList());
    }

    public FarmerProductDTO getProductById(Long id) {
        return toProductDTO(productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found")));
    }

    public FarmerProductDTO createProduct(Long farmerId, FarmerProductDTO dto) {
        dto.setFarmerId(farmerId);
        return createProductDirect(dto);
    }

    public FarmerProductDTO createProductDirect(FarmerProductDTO dto) {
        FarmerProduct p = new FarmerProduct();
        p.setFarmerId(dto.getFarmerId() != null ? dto.getFarmerId() : 1L);
        p.setProductName(dto.getProductName());
        p.setDescription(dto.getDescription());
        p.setPrice(dto.getPrice());
        p.setQuantity(dto.getQuantity() != null ? dto.getQuantity() : 0);
        p.setUnit(dto.getUnit());
        p.setCategory(dto.getCategory());
        p.setImageUrl(dto.getImageUrl());
        return toProductDTO(productRepository.save(p));
    }

    public FarmerProductDTO updateProduct(Long id, FarmerProductDTO dto) {
        FarmerProduct p = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if (dto.getProductName() != null) p.setProductName(dto.getProductName());
        if (dto.getDescription() != null) p.setDescription(dto.getDescription());
        if (dto.getPrice() != null) p.setPrice(dto.getPrice());
        if (dto.getQuantity() != null) p.setQuantity(dto.getQuantity());
        if (dto.getUnit() != null) p.setUnit(dto.getUnit());
        if (dto.getCategory() != null) p.setCategory(dto.getCategory());
        if (dto.getImageUrl() != null) p.setImageUrl(dto.getImageUrl());
        return toProductDTO(productRepository.save(p));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<FarmerProductDTO> getFarmerProducts(Long farmerId) {
        return productRepository.findByFarmerId(farmerId).stream()
                .map(this::toProductDTO).collect(Collectors.toList());
    }

    // ─── Mappers ─────────────────────────────────────────────────────────────

    private FarmerDTO toDTO(Farmer f) {
        return new FarmerDTO(f.getId(), f.getUserId(), f.getName(), f.getLocation(),
                f.getFarmSize(), f.getCropTypes(), f.getSoilType(), f.getClimateZone());
    }

    private CropDTO toCropDTO(Crop c) {
        CropDTO dto = new CropDTO();
        dto.setId(c.getId());
        dto.setFarmerId(c.getFarmerId());
        dto.setCropName(c.getCropName());
        dto.setCropType(c.getCropType());
        dto.setPlantingDate(c.getPlantingDate() != null ? c.getPlantingDate().toString() : null);
        dto.setArea(c.getArea());
        dto.setLocation(c.getLocation());
        dto.setStage(c.getStage());
        dto.setSoilType(c.getSoilType());
        dto.setClimate(c.getClimate());
        dto.setNotes(c.getNotes());
        dto.setCreatedAt(c.getCreatedAt() != null ? c.getCreatedAt().toString() : null);
        dto.setUpdatedAt(c.getUpdatedAt() != null ? c.getUpdatedAt().toString() : null);
        return dto;
    }

    private FarmerProductDTO toProductDTO(FarmerProduct p) {
        return new FarmerProductDTO(p.getId(), p.getFarmerId(), p.getProductName(),
                p.getDescription(), p.getPrice(), p.getQuantity(), p.getUnit(),
                p.getCategory(), p.getImageUrl(), p.getStatus());
    }
}
