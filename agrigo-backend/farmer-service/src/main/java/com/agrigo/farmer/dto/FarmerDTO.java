package com.agrigo.farmer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmerDTO {
    private Long id;
    private Long userId;
    private String name;
    private String location;
    private String farmSize;
    private String cropTypes;
    private String soilType;
    private String climateZone;
}
