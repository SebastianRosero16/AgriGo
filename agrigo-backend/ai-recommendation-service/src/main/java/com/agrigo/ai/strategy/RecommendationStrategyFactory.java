package com.agrigo.ai.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RecommendationStrategyFactory {
    
    private final List<RecommendationStrategy> strategies;
    
    public RecommendationStrategy getStrategy(String cropType) {
        return strategies.stream()
                .filter(strategy -> strategy.getCropType().equalsIgnoreCase(cropType))
                .findFirst()
                .orElse(strategies.stream()
                        .filter(strategy -> strategy.getCropType().equals("DEFAULT"))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("Default strategy not found")));
    }
}
