package com.agrigo.ai.strategy;

import com.agrigo.ai.dto.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DefaultRecommendationStrategy implements RecommendationStrategy {
    
    @Override
    public RecommendationResponse generateRecommendation(RecommendationRequest request) {
        FertilizerRecommendation fertilizer = new FertilizerRecommendation(
                "NPK 10-10-10",
                "150-200 kg/hectare",
                "Apply based on soil test",
                "Adjust based on crop requirements"
        );
        
        PesticideRecommendation pesticide = new PesticideRecommendation(
                "General purpose pesticide",
                "1-2 liters/hectare",
                "Spray application",
                "Follow label instructions"
        );
        
        return new RecommendationResponse(
                "",
                request.getCropType(),
                "",
                fertilizer,
                pesticide
        );
    }
    
    @Override
    public String getCropType() {
        return "DEFAULT";
    }
}
