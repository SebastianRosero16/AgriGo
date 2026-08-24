package com.agrigo.ai.strategy;

import com.agrigo.ai.dto.RecommendationRequest;
import com.agrigo.ai.dto.RecommendationResponse;

public interface RecommendationStrategy {
    RecommendationResponse generateRecommendation(RecommendationRequest request);
    String getCropType();
}
