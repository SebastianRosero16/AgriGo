package com.agrigo.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationResponse {
    private String explanation;   // Main answer — what frontend reads
    private String cropType;
    private String aiInsight;
    // Legacy fields kept for compatibility
    private Object fertilizer;
    private Object pesticide;
}
