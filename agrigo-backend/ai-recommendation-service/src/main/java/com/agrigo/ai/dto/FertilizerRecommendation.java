package com.agrigo.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FertilizerRecommendation {
    private String type;
    private String quantity;
    private String frequency;
    private String notes;
}
