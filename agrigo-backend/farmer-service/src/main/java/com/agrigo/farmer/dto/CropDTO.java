package com.agrigo.farmer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CropDTO {
    private Long id;
    private Long farmerId;
    private String cropName;
    private String cropType;
    private String plantingDate;
    private Double area;
    private String location;
    private String stage;
    private String soilType;
    private String climate;
    private String notes;
    private String createdAt;
    private String updatedAt;
}
