package com.agrigo.ai.strategy;

import com.agrigo.ai.dto.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CornRecommendationStrategy implements RecommendationStrategy {
    
    @Override
    public RecommendationResponse generateRecommendation(RecommendationRequest request) {
        FertilizerRecommendation fertilizer = new FertilizerRecommendation(
                "NPK 15-15-15",
                "200-250 kg/hectare",
                "Apply at planting and 30 days after",
                "Split application for better absorption"
        );
        
        PesticideRecommendation pesticide = new PesticideRecommendation(
                "Atrazine + Glyphosate",
                "2-3 liters/hectare",
                "Spray application",
                "Apply pre-emergence for weed control"
        );
        
        return new RecommendationResponse("", "Corn", "", fertilizer, pesticide);
    }
    
    @Override
    public String getCropType() {
        return "CORN";
    }
}
