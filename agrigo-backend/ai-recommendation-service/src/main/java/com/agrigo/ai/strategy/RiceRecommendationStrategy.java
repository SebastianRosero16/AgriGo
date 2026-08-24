package com.agrigo.ai.strategy;

import com.agrigo.ai.dto.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class RiceRecommendationStrategy implements RecommendationStrategy {
    
    @Override
    public RecommendationResponse generateRecommendation(RecommendationRequest request) {
        FertilizerRecommendation fertilizer = new FertilizerRecommendation(
                "Urea + DAP",
                "150-180 kg/hectare",
                "Apply in 3 splits: basal, tillering, panicle",
                "Ensure proper water management"
        );
        
        PesticideRecommendation pesticide = new PesticideRecommendation(
                "Carbofuran + Tricyclazole",
                "1.5-2 kg/hectare",
                "Granular and spray",
                "Control stem borer and blast disease"
        );
        
        return new RecommendationResponse("", "Rice", "", fertilizer, pesticide);
    }
    
    @Override
    public String getCropType() {
        return "RICE";
    }
}
